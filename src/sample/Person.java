package sample;

public class Person {



    public String First_Name;
    public String Middle_Initial;
    public String Last_Name;
    public String Address;
    public String Birth_Date;
    public String Gender;
    public String SSN;
    public String Salary;
    public String State;

    public Person()
    {

    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getMiddle_Initial() {
        return Middle_Initial;
    }

    public void setMiddle_Initial(String middle_Initial) {
        Middle_Initial = middle_Initial;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getBirth_Date() {
        return Birth_Date;
    }

    public void setBirth_Date(String birth_Date) {
        Birth_Date = birth_Date;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public void setState(String state) {State = state;}

    public String getState() {return State;}
}
