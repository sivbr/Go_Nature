-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: go_nature
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoices` (
  `invoiceNumber` int NOT NULL,
  `kind` varchar(100) DEFAULT NULL,
  `parkName` varchar(50) DEFAULT NULL,
  `hour` int DEFAULT NULL,
  `minute` int DEFAULT NULL,
  `numOfVisitors` int DEFAULT NULL,
  `originalPrice` int DEFAULT NULL,
  `parkDiscount` double DEFAULT NULL,
  `isMember` int DEFAULT NULL,
  `isPaid` int DEFAULT NULL,
  `isOrganized` int DEFAULT NULL,
  `orderNumber` int DEFAULT NULL,
  `totalPrice` double DEFAULT NULL,
  PRIMARY KEY (`invoiceNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `ID` varchar(10) NOT NULL,
  `firstName` varchar(20) DEFAULT NULL,
  `lastName` varchar(20) DEFAULT NULL,
  `mail` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `numberOFamilyfMembers` int DEFAULT NULL,
  `payType` varchar(10) DEFAULT NULL,
  `memberNumber` varchar(10) DEFAULT NULL,
  `organizationGroup` varchar(5) DEFAULT NULL,
  `online` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES ('123412521','gabi','cohen','gae@gmail.com','0500433521',2,'cash','12588','no',0),('123456788','UIUY','IYUIU','HJKHJK@HFGH.COM','0501234567',1,'cash','12589','no',0),('206230522','shay','shay','shay@gmail.com','0501231231',2,'cash','12587','yes',0),('369','Avi','Gal','avisk@gm.com','064562456',5,'cash','12586','yes',0),('777','Sivan','Brecher','brechersivan@gmail.com','0521234567',3,'cash','10546','yes',0),('888','Sapir','Baron','sapirb665@gmail.com','041234567',1,'credit','10875','no',0);
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderNumber` int NOT NULL,
  `personID` varchar(10) DEFAULT NULL,
  `park` varchar(20) DEFAULT NULL,
  `visitDay` int DEFAULT NULL,
  `visitMonth` int DEFAULT NULL,
  `visitYear` int DEFAULT NULL,
  `visitTime` int DEFAULT NULL,
  `numOfVisitors` int DEFAULT NULL,
  `totalPayment` double DEFAULT NULL,
  `organizationGroup` int DEFAULT NULL,
  `paid` int DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `notificationHour` int DEFAULT NULL,
  `notificationMinute` int DEFAULT NULL,
  `enterTime` int DEFAULT NULL,
  `exitTime` int DEFAULT NULL,
  PRIMARY KEY (`orderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (98564,'777','Carmel',2,1,2021,12,3,120,0,0,'cancelled',18,22,1612,1955),(98565,'888','HaiPark',21,1,2021,12,1,12.65,0,0,'Cancelled',10,10,1555,1845),(98566,'888','Carmel',31,1,2021,12,1,12.92,0,0,'Ordered',10,10,1555,1845),(98567,'888','Carmel',31,1,2021,16,1,12.92,0,0,'Ordered',10,10,1555,1845),(98568,'888','Carmel',31,1,2021,12,1,12.92,0,0,'Ordered',10,10,1555,1845),(98569,'888','Carmel',21,1,2021,12,1,12.92,0,0,'Ordered',10,10,1555,1845),(98570,'888','Carmel',21,1,2021,12,1,12.92,0,0,'Cancelled',10,10,1555,1845),(98571,'888','Sharon',21,1,2021,12,1,12.44,0,0,'Ordered',10,10,1555,1845),(98572,'888','Sharon',21,1,2021,16,1,12.44,0,0,'Cancelled',10,10,1555,1845),(98573,'777','Carmel',31,1,2021,12,1,13.6,0,0,'Cancelled',10,10,1555,1845),(98574,'888','Carmel',31,1,2021,12,1,12.92,0,0,'Fulfilled',10,10,1555,1845),(98575,'888','Carmel',31,1,2021,12,1,12.92,0,0,'Cancelled',10,10,1555,1845),(98576,'222','Carmel',31,1,2021,12,1,16.15,0,0,'Cancelled',10,10,1555,1845),(98577,'222','Carmel',31,1,2021,12,1,16.15,0,0,'Cancelled',10,10,1555,1845),(98578,'888','Carmel',31,1,2021,12,1,12.92,1,0,'Fulfilled',10,10,1555,1845),(98579,'888','Carmel',31,1,2021,12,1,12.92,0,0,'Cancelled',10,10,1555,1845),(98580,'888','Carmel',31,1,2021,12,1,12.92,0,0,'Cancelled',10,10,1555,1845),(98581,'888','Carmel',31,1,2021,12,1,12.92,0,0,'Fulfilled',10,10,1555,1845),(98582,'888','Carmel',31,1,2021,12,6,81.6,0,0,'Cancelled',22,7,1555,1845),(98583,'777','Carmel',31,1,2021,12,8,103.36,0,0,'Cancelled',10,10,1555,1845),(98584,'369','Carmel',31,1,2021,12,5,68,0,0,'Cancelled',10,10,1555,1845),(98585,'369','Carmel',31,1,2021,16,6,81.6,0,0,'Cancelled',10,10,1555,1845),(98586,'369','Carmel',31,1,2021,12,7,95.2,0,0,'WaitingList',10,10,1555,1845),(98587,'369','Carmel',31,1,2021,8,3,40.8,0,0,'Ordered',0,59,1555,1845),(98588,'222','Carmel',31,1,2021,8,8,129.2,0,0,'Cancelled',10,10,1555,1845),(98589,'888','Carmel',21,1,2021,12,1,12.92,0,0,'Fulfilled',10,10,1555,1845),(98590,'111','HaiPark',16,1,2021,8,1,15.81,1,0,'Fulfilled',10,10,1555,1845),(98591,'111','HaiPark',7,1,2021,12,1,15.81,0,0,'VisitConfirmed',16,33,1555,1845),(98592,'888','Carmel',31,1,2021,16,1,12.92,0,0,'Ordered',10,10,1555,1845),(98593,'888','HaiPark',7,1,2021,8,1,12.65,0,0,'VisitConfirmed',10,10,1555,1845),(98594,'888','HaiPark',7,1,2021,8,1,12.65,0,0,'NotifiedTom',3,20,1555,1845),(98595,'222','HaiPark',7,1,2021,8,1,15.81,0,0,'Fulfilled',10,10,1555,1845),(98596,'111','HaiPark',7,1,2021,8,1,15.81,0,0,'NotifiedTom',16,33,1555,1845),(98597,'111','HaiPark',7,1,2021,8,1,15.81,0,0,'NotifiedTom',16,33,1555,1845),(98598,'369','HaiPark',18,1,2021,12,1,12.65,0,0,'Ordered',10,10,1555,1845),(98599,'369','HaiPark',7,1,2021,8,1,12.65,0,0,'Tomorrow',10,10,1555,1845),(98600,'111','HaiPark',7,1,2021,8,1,15.81,0,0,'NotifiedTom',16,33,1555,1845),(98601,'888','HaiPark',20,1,2021,12,1,12.65,0,0,'Ordered',10,10,1555,1845),(98602,'888','HaiPark',7,1,2021,8,1,12.65,0,0,'NotifiedTom',3,20,1555,1845),(98603,'111','HaiPark',7,1,2021,8,1,15.81,0,0,'NotifiedTom',16,33,1555,1845),(98604,'369','HaiPark',7,1,2021,8,1,12.65,0,0,'Tomorrow',10,10,1555,1845),(98605,'222','HaiPark',7,1,2021,8,1,15.81,0,0,'VisitConfirmed',10,10,1555,1845),(98606,'888','HaiPark',7,1,2021,8,1,12.65,0,0,'NotifiedTom',19,48,1555,1845),(98607,'888','Sharon',7,1,2021,16,1,12.44,0,0,'Fulfilled',19,48,1555,1845),(98608,'222','HaiPark',21,1,2021,12,8,126.48,0,0,'Ordered',10,10,1555,1845),(98609,'111','Sharon',18,5,2021,12,10,155.55,0,0,'Ordered',10,10,1555,1845),(98610,'777','Sharon',11,5,2021,8,10,124.44,0,0,'Ordered',10,10,1555,1845),(98611,'777','Sharon',11,5,2021,8,10,136,0,0,'Cancelled',1,24,1555,1845),(98612,'777','Sharon',11,5,2021,8,10,124.44,0,0,'Ordered',10,10,1555,1845),(98613,'888','HaiPark',2,2,2021,8,7,88.54,0,0,'Cancelled',10,10,1555,1845),(98614,'111','HaiPark',4,2,2021,8,7,110.67,0,0,'Cancelled',10,10,1555,1845),(98615,'222','Sharon',2,2,2021,12,3,46.67,0,0,'Cancelled',10,10,1555,1845),(98616,'111','HaiPark',7,1,2021,16,1,15.81,0,0,'VisitConfirmed',10,10,1555,1845),(98617,'111','HaiPark',16,1,2021,8,5,79.05,0,0,'Cancelled',10,10,1555,1845),(98618,'111','HaiPark',28,1,2021,8,7,110.67,0,0,'Cancelled',10,10,1555,1845),(98619,'222','Sharon',14,1,2021,16,7,108.88,0,0,'Fulfilled',10,10,1555,1845),(98620,'111','Sharon',11,5,2021,8,12,186.66,0,0,'Cancelled',10,10,1555,1845),(98621,'222','HaiPark',4,2,2021,8,15,255,0,0,'WaitingList',10,10,1555,1845),(98622,'222','HaiPark',4,2,2021,8,15,237.15,0,0,'Fulfilled',10,10,1555,1845),(98623,'111','HaiPark',4,2,2021,12,15,237.15,0,0,'Ordered',10,10,1555,1845),(98624,'111','HaiPark',19,1,2021,12,3,37.49,0,0,'Cancelled',10,10,1555,1845),(98625,'111','HaiPark',4,2,2021,12,1,12.5,0,0,'Ordered',10,10,1555,1845),(98626,'111','Sharon',21,1,2021,12,1,12.1,0,0,'Ordered',10,10,1555,1845),(98627,'222','HaiPark',11,1,2021,12,15,187.47,0,0,'Ordered',10,10,1555,1845),(98628,'222','HaiPark',11,1,2021,12,1,12.5,0,0,'Ordered',10,10,1555,1845),(98629,'888','Sharon',14,1,2021,12,1,7.74,0,0,'Ordered',10,10,1555,1845),(98630,'111','Carmel',13,1,2021,16,15,195.62,0,0,'Ordered',10,10,1555,1845),(98631,'206230522','Carmel',13,1,2021,12,15,204,1,0,'WaitingList',10,10,1555,1845),(98632,'206230522','Carmel',31,1,2021,12,15,225,1,0,'WaitingList',10,10,1555,1845);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parks`
--

DROP TABLE IF EXISTS `parks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parks` (
  `name` varchar(20) NOT NULL,
  `city` varchar(20) DEFAULT NULL,
  `maxCapacity` int DEFAULT NULL,
  `occasionalVisitors` int DEFAULT NULL,
  `currentAllVisitors` int DEFAULT NULL,
  `currentOccasionalVisitors` int DEFAULT NULL,
  `discount` double DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parks`
--

LOCK TABLES `parks` WRITE;
/*!40000 ALTER TABLE `parks` DISABLE KEYS */;
INSERT INTO `parks` VALUES ('Carmel','Haifa',30,10,0,0,5),('HaiPark','Kiryat-Bialik',50,2,0,0,7),('Sharon','Tel-Aviv',100,7,0,0,8.5);
/*!40000 ALTER TABLE `parks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_details`
--

DROP TABLE IF EXISTS `payment_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_details` (
  `memberID` varchar(10) NOT NULL,
  `cardNumber` varchar(30) DEFAULT NULL,
  `cardID` varchar(10) DEFAULT NULL,
  `cardValid` varchar(10) DEFAULT NULL,
  `cardCVC` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`memberID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_details`
--

LOCK TABLES `payment_details` WRITE;
/*!40000 ALTER TABLE `payment_details` DISABLE KEYS */;
INSERT INTO `payment_details` VALUES ('888','1234567887654321','4567896','02/26','789');
/*!40000 ALTER TABLE `payment_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `possible_dates`
--

DROP TABLE IF EXISTS `possible_dates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `possible_dates` (
  `date_parkName` varchar(60) NOT NULL,
  `date` varchar(20) DEFAULT NULL,
  `parkName` varchar(45) DEFAULT NULL,
  `time1` int DEFAULT NULL,
  `time2` int DEFAULT NULL,
  `time3` int DEFAULT NULL,
  `maxCapacity` int DEFAULT NULL,
  `available` int DEFAULT NULL,
  PRIMARY KEY (`date_parkName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `possible_dates`
--

LOCK TABLES `possible_dates` WRITE;
/*!40000 ALTER TABLE `possible_dates` DISABLE KEYS */;
INSERT INTO `possible_dates` VALUES ('2021-01-07_HaiPark','2021-01-07','HaiPark',0,1,1,0,1),('2021-01-11_HaiPark','2021-01-11','HaiPark',0,16,0,0,1),('2021-01-13_Carmel','2021-01-13','Carmel',0,0,15,0,1),('2021-01-14_Sharon','2021-01-14','Sharon',0,1,0,0,1),('2021-01-16_HaiPark','2021-01-16','HaiPark',0,0,0,0,1),('2021-01-18_HaiPark','2021-01-18','HaiPark',0,1,0,0,1),('2021-01-21_Sharon','2021-01-21','Sharon',0,1,0,0,1),('2021-01-22_Carmel','2021-01-22','Carmel',7,5,6,20,0),('2021-01-28_HaiPark','2021-01-28','HaiPark',0,0,0,0,1),('2021-01-31_Carmel','2021-01-31','Carmel',2,3,1,1,1),('2021-02-02_Sharon','2021-02-02','Sharon',0,0,0,0,1),('2021-02-04_HaiPark','2021-02-04','HaiPark',29,16,18,0,1),('2021-05-11_Sharon','2021-05-11','Sharon',20,0,0,0,1);
/*!40000 ALTER TABLE `possible_dates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requests` (
  `requestID` int NOT NULL,
  `parkName` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `startDate` varchar(50) DEFAULT NULL,
  `endDate` varchar(45) DEFAULT NULL,
  `request` varchar(50) DEFAULT NULL,
  `requestDate` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`requestID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requests`
--

LOCK TABLES `requests` WRITE;
/*!40000 ALTER TABLE `requests` DISABLE KEYS */;
INSERT INTO `requests` VALUES (1000,'Carmel','UpdateCapcity','----------','----------','45','10/01/2021','Approved'),(1001,'Carmel','OccasionalCapcity','----------','---------','12','10/01/2021','Rejected'),(1002,'Carmel','UpdateDiscount','2021-01-13','2021-01-20','12','10/01/2021','Approved'),(1003,'HaiPark','UpdateDiscount','2021-01-06','2021-01-12','45','10/01/2021','Rejected'),(1004,'HaiPark','UpdateCapcity','----------','----------','45','10/01/2021','Rejected'),(1005,'HaiPark','OccasionalCapcity','----------','---------','3','10/01/2021','Waiting'),(1006,'Sharon','UpdateCapcity','----------','----------','12','10/01/2021','Waiting'),(1007,'Sharon','UpdateDiscount','2021-01-07','2021-01-20','45','10/01/2021','Waiting'),(1008,'All-Parks','VisitsReport','2021-1-1','2021-31-1','0','2021-01-10','Ready'),(1009,'All-Parks','CancelReport','2021-1-1','2021-31-1','0','2021-01-10','Ready'),(1010,'Sharon','CancelReport','2021-1-1','2021-24-1','0','2021-01-10','Ready'),(1011,'HaiPark','VisitsReport','2021-1-1','2021-25-1','0','2021-01-10','Ready'),(1012,'HaiPark','CancelReport','2021-1-1','2021-18-1','0','2021-01-10','Ready'),(1013,'Carmel','VisitsReport','2021-1-1','2021-18-1','0','2021-01-10','Ready'),(1014,'Sharon','VisitorsReport','2021-01-01','0','1','2021-01-10','Ready'),(1015,'Sharon','OccupancyReport','2021-01-01','0','1','2021-01-10','Ready'),(1016,'Sharon','IncomeReport','2021-01-01','0','1','2021-01-10','Ready');
/*!40000 ALTER TABLE `requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `summary_days`
--

DROP TABLE IF EXISTS `summary_days`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `summary_days` (
  `date_park` varchar(50) NOT NULL,
  `parkName` varchar(45) DEFAULT NULL,
  `maxCapacity` int DEFAULT NULL,
  `totalVisitors` int DEFAULT NULL,
  `visitors` int DEFAULT NULL,
  `members` int DEFAULT NULL,
  `peopleFromOrg` int DEFAULT NULL,
  `income` int DEFAULT NULL,
  `time1` int DEFAULT NULL,
  `time2` int DEFAULT NULL,
  `time3` int DEFAULT NULL,
  `day` int DEFAULT NULL,
  `month` int DEFAULT NULL,
  `year` int DEFAULT NULL,
  PRIMARY KEY (`date_park`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `summary_days`
--

LOCK TABLES `summary_days` WRITE;
/*!40000 ALTER TABLE `summary_days` DISABLE KEYS */;
INSERT INTO `summary_days` VALUES ('01/01/2018_Carmel','Carmel',45,25,5,10,10,1547,1,1,1,1,1,2018),('02/01/2018_Carmel','Carmel',45,12,2,5,5,451,1,1,1,2,1,2018),('03/01/2018_Carmel','Carmel',55,18,6,6,6,1254,1,1,1,3,1,2018),('04/01/2018_Carmel','Carmel',45,36,18,8,10,2547,1,1,1,4,1,2018),('05/01/2018_Carmel','Carmel',45,45,25,10,10,3548,1,1,1,5,1,2018),('07/01/2021_Carmel','Carmel',45,45,15,5,2,1450,1,1,1,7,1,2021),('07/01/2021_HaiPark','Sharon',50,50,20,5,10,2548,1,1,1,8,1,2018),('07/01/2021_Sharon','Sharon',65,20,15,2,3,1547,1,1,1,7,1,2018),('2021-01-09_Carmel','Carmel',30,413,378,35,0,2119,0,0,58,9,1,2021),('2021-01-09_HaiPark','HaiPark',50,51,29,22,0,171,0,0,6,9,1,2021),('2021-01-09_Sharon','Sharon',100,203,153,50,0,634,0,0,16,9,1,2021);
/*!40000 ALTER TABLE `summary_days` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitors`
--

DROP TABLE IF EXISTS `visitors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visitors` (
  `ID` varchar(10) NOT NULL,
  `firstName` varchar(20) DEFAULT NULL,
  `lastName` varchar(20) DEFAULT NULL,
  `mail` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `online` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitors`
--

LOCK TABLES `visitors` WRITE;
/*!40000 ALTER TABLE `visitors` DISABLE KEYS */;
INSERT INTO `visitors` VALUES ('111','Lior','Saghi','liorsaghi@gmail.com','0501234567',0),('111111111','fgfd','dfgfd','ghjgh@fgh.com','0501234567',1),('123456123','shay','shay','ased@asd.com','0521231231',0),('123456789','uyiuy','yuiyu','jhkjh@gfdg.com','0501234567',1),('222','Tomer','Dabun','tomer_max@hotmail.com','0541237894',0),('222222222','dfgdfg','dfgdfg','fghfg@dfgdf.com','0501234567',1),('333333333','rtjtyj','ghjgh','cvbncvnvb@hjk.com','0501234567',1),('987654321','jghjjgh','ghjghj','hfghjf@fgh.com','0501234567',1),('test45654','test2','test3','test4','test5',1);
/*!40000 ALTER TABLE `visitors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `waiting_lists`
--

DROP TABLE IF EXISTS `waiting_lists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `waiting_lists` (
  `orderNumber` int NOT NULL,
  `parkName` varchar(20) DEFAULT NULL,
  `visitDay` int DEFAULT NULL,
  `visitMonth` int DEFAULT NULL,
  `visitYear` int DEFAULT NULL,
  `visitTime` int DEFAULT NULL,
  `numOfVisitors` int DEFAULT NULL,
  PRIMARY KEY (`orderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `waiting_lists`
--

LOCK TABLES `waiting_lists` WRITE;
/*!40000 ALTER TABLE `waiting_lists` DISABLE KEYS */;
INSERT INTO `waiting_lists` VALUES (98621,'HaiPark',4,2,2021,8,15),(98631,'Carmel',13,1,2021,12,15),(98632,'Carmel',31,1,2021,12,15);
/*!40000 ALTER TABLE `waiting_lists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workers`
--

DROP TABLE IF EXISTS `workers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workers` (
  `workerNumber` varchar(10) NOT NULL,
  `ID` varchar(10) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `firstName` varchar(20) DEFAULT NULL,
  `lastName` varchar(20) DEFAULT NULL,
  `mail` varchar(50) DEFAULT NULL,
  `role` varchar(30) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `park` varchar(50) DEFAULT NULL,
  `online` int DEFAULT NULL,
  PRIMARY KEY (`workerNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workers`
--

LOCK TABLES `workers` WRITE;
/*!40000 ALTER TABLE `workers` DISABLE KEYS */;
INSERT INTO `workers` VALUES ('222','100','222','Bar','Yosef','bar@gmail.com','DepartmentManager','0587456912',NULL,0),('333','1001','333','Sapir','Baron','sapir@gmail.com','ParkManager','0526408795','Sharon',0),('444','1002','444','Coral','Harel','coralh27795@gmail.com','ParkManager','0548435671','Carmel',0),('555','1003','555','Sivan','Gal','sivan@gmail.com','ParkManager','0505486512','HaiPark',0),('666','1004','666','Haim','Bar','haim@gmailcom','CustomerService','0524786145','Sharon',0),('777','1005','777','Gil','Dan','gil@gmail.com','CustomerService','0547893254','Carmel',0),('888','1006','888','Fadi','Amar','fadi@gmail.com','CustomerService','0514786354','HaiPark',0),('991','1008','991','Fani','Ziv','fani@gmail.com','ParkEmployee','0547123547','Carmel',0),('992','1009','992','Din','Ron','gil@gmail.com','ParkEmployee','0524786314','HaiPark',0),('999','1007','999','Dolve','Tal','dolve@gmail.com','ParkEmployee','0541523647','Sharon',0);
/*!40000 ALTER TABLE `workers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-10  6:14:14
