package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;


public class Controller{
    public TextField FName;
    public TextField MName;
    public TextField LName;
    public TextField Address;
    public TextField SSN;
    public DatePicker Birthday;
    public TextField Salary;
    public TableView<Person> Output;
    public TableColumn<Person, String> First_Output;
    public TableColumn<Person, String> MI_Output;
    public TableColumn<Person, String> Last_Output;
    public TableColumn<Person, String> SSN_Output;
    public TableColumn<Person, String> Sex_Output;
    public TableColumn<Person, String> Birth_Output;
    public TableColumn<Person, String> Address_Output;
    public TableColumn<Person, String> st;

    @FXML public ChoiceBox Sex;

    public ObservableList<String> options = FXCollections.observableArrayList("Male","Female","Others");




    //check conditions before sending to Database




    public TextField State;

    static Connection conn = null;
    static String dbname = "ContactManager";
    static String url = "jdbc:mysql://localhost:3306/"+dbname;
    static String user  = "root";
    static String pass = "";
    static Statement statement = null;

    //initializing the UI with some pre settings

    @FXML
    public void initialize()
    {

        Birthday.setValue(LocalDate.now());
        Sex.setValue("Male");
        Sex.setItems(options);

        First_Output.setCellValueFactory(new PropertyValueFactory<Person, String>("First_Name"));
        MI_Output.setCellValueFactory(new PropertyValueFactory<Person, String>("Middle_Initial"));
        Last_Output.setCellValueFactory(new PropertyValueFactory<Person, String>("Last_Name"));
        SSN_Output.setCellValueFactory(new PropertyValueFactory<Person, String>("SSN"));
        Sex_Output.setCellValueFactory(new PropertyValueFactory<Person, String>("Gender"));
        Birth_Output.setCellValueFactory(new PropertyValueFactory<Person, String>("Birth_Date"));
        Address_Output.setCellValueFactory(new PropertyValueFactory<Person, String>("Address"));
        st.setCellValueFactory(new PropertyValueFactory<Person, String>("State"));


        //ObservableList<Person> P = FXCollections.observableArrayList(parsePersonList());
        List<Person> L= parsePersonList();

        Output.getItems().setAll(L);
        //Output.setItems(P);


    }





    //Edit Records Here


    public void Edit(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        try
        {
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected");


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        LocalDate i = Birthday.getValue();

        int decision =0;
        //before inputting any value we need to check the data first. whether it is valid or not.
        String SSN_edit = SSN.getText();
        try{




            String query = "select * from Person join State on Person.State_ID = State.State_ID";
            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);



            while(rs.next())
            {

                if(rs.getString("SSN").equals(SSN_edit)){
                    decision = 1;
                }




            }

        }
        catch (Exception e){

            Alert alert = GetAlert("Oops!Something went WRONG!");
            alert.showAndWait();
            e.printStackTrace();

        }


        if(decision == 1) {
            boolean status = checkInputs(LName.getText(), i, SSN.getText(), Address.getText(), State.getText().toLowerCase());


            if (status == true) {

                try {

                    java.sql.Date birth = java.sql.Date.valueOf(i);
                    int State_id = checkState(State.getText().toLowerCase());


                    String query = " update Person set FName = ?, LName = ?, MName = ?, Address = ?, State_ID = ?, Birthdate = ?, Sex = ?, Salary = ? where SSN = ?";
                    PreparedStatement preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setString(1, FName.getText());
                    preparedStmt.setString(2, LName.getText());
                    preparedStmt.setString(3, MName.getText());
                    preparedStmt.setString(4, Address.getText());
                    preparedStmt.setInt(5, State_id);
                    preparedStmt.setDate(6, birth);
                    preparedStmt.setString(7, Sex.getValue().toString());
                    preparedStmt.setDouble(8, Double.parseDouble(Salary.getText()));
                    preparedStmt.setString(9 , SSN_edit);

                    preparedStmt.execute();

                    conn.close();


                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Confirmation");
                    a.setHeaderText(null);
                    a.setContentText("Update Successful");
                    a.showAndWait();

                    initialize();

                } catch (Exception e) {
                    Alert alert = GetAlert("Oops!Something went WRONG!");
                    alert.showAndWait();
                    e.printStackTrace();
                }


            } else {
                Alert alert = GetAlert("Oops!Something went WRONG!");
                alert.showAndWait();
            }

        }

        else
        {
            Alert alert = GetAlert("Record Does Not exist");
            alert.showAndWait();

        }







    }

    //Add records here

    public void Add(ActionEvent actionEvent) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class.forName("com.mysql.jdbc.Driver");
        try
        {
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected");


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        LocalDate i = Birthday.getValue();


        //before inputting any value we need to check the data first. whether it is valid or not.

        boolean status = checkInputs(LName.getText(), i, SSN.getText(), Address.getText(), State.getText().toLowerCase());

        if(status==true) {

            try{

                java.sql.Date birth = java.sql.Date.valueOf(i);
                int State_id = checkState(State.getText().toLowerCase());

                String query = " insert into Person (FName, LName, MName, SSN, Address, State_ID, Birthdate, Sex, Salary)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString (1, FName.getText());
                preparedStmt.setString (2, LName.getText());
                preparedStmt.setString (3, MName.getText());
                preparedStmt.setString (4, SSN.getText());
                preparedStmt.setString (5, Address.getText());
                preparedStmt.setInt (6, State_id);
                preparedStmt.setDate(7,birth);
                preparedStmt.setString(8,Sex.getValue().toString());
                preparedStmt.setDouble(9, Double.parseDouble(Salary.getText()));

                preparedStmt.execute();

                conn.close();



                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Confirmation");
                a.setHeaderText(null);
                a.setContentText("Insertion Successful");
                a.showAndWait();

                initialize();

            }

            catch(Exception e)
            {
                Alert alert = GetAlert("Oops!Something went WRONG!");
                alert.showAndWait();
                e.printStackTrace();
            }







        }

        else
        {
            Alert alert = GetAlert("Oops!Something went WRONG!");
            alert.showAndWait();
        }




        //System.out.println(Sex.getValue().toString());
        //System.out.println(LName.getText());

    }


    public void Delete(ActionEvent actionEvent) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try
        {
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected");


        }
        catch(Exception e)
        {
            Alert alert = GetAlert("Oops!Something went WRONG!");
            alert.showAndWait();
            e.printStackTrace();
        }
        String SSN_Del = SSN.getText().toString();
        try{




            String query = "select * from Person join State on Person.State_ID = State.State_ID";
            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);

            int flag = 0;

            while(rs.next())
            {

                if(rs.getString("SSN").equals(SSN_Del)){
                    flag = 1;
            }











            }

            if(flag==1) {
                query = "delete from Person where SSN = ?";
                PreparedStatement pre = conn.prepareStatement(query);
                pre.setString(1, SSN.getText());
                pre.execute();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Confirmation");
                a.setHeaderText(null);
                a.setContentText("Delete Successful");
                a.showAndWait();
            }
            else
            {
                Alert alert = GetAlert("Record not Found");
                alert.showAndWait();
            }


            initialize();
            conn.close();






        }

        catch(Exception e)
        {
            Alert alert = GetAlert("Oops!Something went WRONG!");
            alert.showAndWait();
            e.printStackTrace();
        }



    }



    public boolean checkInputs(String LName, LocalDate Birthdate, String SSN, String Address, String State){

        boolean status = false;

        if(LName == null || LName.isEmpty())
        {
            Alert alert = GetAlert("Last Name cannot be empty");
            alert.showAndWait();
            return status;
        }

        if(SSN.length()!=11 || SSN.isEmpty())
        {
            Alert alert = GetAlert("Invalid SSN");
            alert.showAndWait();
            return status;
        }

        if(Address == null || Address.isEmpty())
        {
            Alert alert = GetAlert("Address cannot be empty");
            alert.showAndWait();
            return status;
        }

        if(State == null || State.isEmpty() || checkState(State) == -1)
        {
            Alert alert = GetAlert("Invalid State Type");
            alert.showAndWait();
            return status;

        }



        status = true;




        return status;
    }


    public Alert GetAlert(String AlertType)
    {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(AlertType);
        return alert;


    }

    public int checkState(String State)
    {
        String [] S = {"texas","california", "virginia","michigan","ohio","oregon" , "indiana"};
        for(int i = 0 ;  i<S.length; i++)
        {
            System.out.println(S[i]+" " +State);

            if(State.equals(S[i]))
            {
                System.out.println(i);
                return i;
            }

        }
        return -1;
    }

    public List<Person> parsePersonList()
    {


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try
        {
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected");


        }
        catch(Exception e)
        {
            Alert alert = GetAlert("Oops!Something went WRONG!");
            alert.showAndWait();
            e.printStackTrace();
        }
        List<Person> l = new ArrayList<Person>(50);
        try{




            String query = "select * from Person join State on Person.State_ID = State.State_ID";
            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);



            while(rs.next())
            {
                Person p = new Person();
                p.First_Name = rs.getString("Person.FName");
                p.Middle_Initial = rs.getString("Person.MName");
                p.Last_Name = rs.getString("Person.LName");
                p.SSN = rs.getString("Person.SSN");
                p.Address = rs.getString("Person.Address");
                p.State = rs.getString("State.State_Name");
                p.Birth_Date = String.valueOf(rs.getDate("Person.Birthdate"));
                p.Salary = String.valueOf(rs.getDouble("Person.Salary"));
                p.Gender = rs.getString("Person.Sex");

                l.add(p);








            }





            conn.close();




            ;

        }

        catch(Exception e)
        {
            Alert alert = GetAlert("Oops!Something went WRONG!");
            alert.showAndWait();
            e.printStackTrace();
        }

        return l;



    }



}
