-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 25, 2018 at 04:21 AM
-- Server version: 10.1.36-MariaDB
-- PHP Version: 5.6.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ContactManager`
--

-- --------------------------------------------------------

--
-- Table structure for table `Person`
--

CREATE TABLE `Person` (
  `FName` varchar(255) NOT NULL,
  `MName` varchar(255) NOT NULL,
  `LName` varchar(255) NOT NULL,
  `SSN` varchar(11) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `State_ID` int(11) NOT NULL,
  `Birthdate` date NOT NULL,
  `Sex` varchar(10) NOT NULL,
  `Salary` decimal(20,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Person`
--

INSERT INTO `Person` (`FName`, `MName`, `LName`, `SSN`, `Address`, `State_ID`, `Birthdate`, `Sex`, `Salary`) VALUES
('Shahreen', 'H', 'Psyche', '12312312312', 'aaa', 1, '2018-10-24', 'Female', '20000000.00'),
('Forhan', '', 'Noor', '12345678123', 'Belt Line', 6, '2018-10-24', 'Male', '20000000.00');

-- --------------------------------------------------------

--
-- Stand-in structure for view `show`
-- (See below for the actual view)
--
CREATE TABLE `show` (
`FName` varchar(255)
,`MName` varchar(255)
,`LName` varchar(255)
,`SSN` varchar(11)
,`Address` varchar(255)
,`State_Name` varchar(255)
,`Birthdate` date
,`Sex` varchar(10)
,`Salary` decimal(20,2)
);

-- --------------------------------------------------------

--
-- Table structure for table `State`
--

CREATE TABLE `State` (
  `State_ID` int(11) NOT NULL,
  `State_Name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `State`
--

INSERT INTO `State` (`State_ID`, `State_Name`) VALUES
(0, 'Texas'),
(1, 'California'),
(2, 'Virginia'),
(3, 'Michigan'),
(4, 'Ohio'),
(5, 'Oregon'),
(6, 'Indiana');

-- --------------------------------------------------------

--
-- Structure for view `show`
--
DROP TABLE IF EXISTS `show`;

CREATE ALGORITHM=MERGE DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `contactmanager`.`show`  AS  select `contactmanager`.`person`.`FName` AS `FName`,`contactmanager`.`person`.`MName` AS `MName`,`contactmanager`.`person`.`LName` AS `LName`,`contactmanager`.`person`.`SSN` AS `SSN`,`contactmanager`.`person`.`Address` AS `Address`,`contactmanager`.`state`.`State_Name` AS `State_Name`,`contactmanager`.`person`.`Birthdate` AS `Birthdate`,`contactmanager`.`person`.`Sex` AS `Sex`,`contactmanager`.`person`.`Salary` AS `Salary` from (`contactmanager`.`person` join `contactmanager`.`state` on((`contactmanager`.`state`.`State_ID` = `contactmanager`.`person`.`State_ID`))) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Person`
--
ALTER TABLE `Person`
  ADD PRIMARY KEY (`SSN`),
  ADD KEY `State_Constraint` (`State_ID`),
  ADD KEY `Index_SSN` (`SSN`);

--
-- Indexes for table `State`
--
ALTER TABLE `State`
  ADD PRIMARY KEY (`State_ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Person`
--
ALTER TABLE `Person`
  ADD CONSTRAINT `State_Constraint` FOREIGN KEY (`State_ID`) REFERENCES `State` (`State_ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
