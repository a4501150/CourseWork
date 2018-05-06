CREATE DATABASE  IF NOT EXISTS `csp584_project` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `csp584_project`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: csp584_project
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer_profile`
--

DROP TABLE IF EXISTS `customer_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_profile` (
  `seq_no` int(255) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `state` varchar(2) DEFAULT NULL,
  `zip` varchar(5) DEFAULT NULL,
  `cc_num` varchar(16) DEFAULT NULL,
  `cc_exp` varchar(5) DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_profile`
--

LOCK TABLES `customer_profile` WRITE;
/*!40000 ALTER TABLE `customer_profile` DISABLE KEYS */;
INSERT INTO `customer_profile` VALUES (1,'Tai','Nguyen','tnguye52@hawk.iit.edu','3124159202','IIT','Chicago','IL','60616','2344446678991645','05/18',0);
/*!40000 ALTER TABLE `customer_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hotel` (
  `seq_no` int(11) NOT NULL AUTO_INCREMENT,
  `location` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `description` text,
  `image_link` text,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel`
--

LOCK TABLES `hotel` WRITE;
/*!40000 ALTER TABLE `hotel` DISABLE KEYS */;
INSERT INTO `hotel` VALUES (1,1,'Hampton Inn Majestic Chicago Theatre District','22 W. Monroe','The Hampton Majestic experience is unlike any other, attached to CIBC Theatre and steps from State Street, museums, Millennium Park and Willis Tower.','1.jpg,2.jpg,3.jpg,4.jpg,5.jpg,6.jpg,7.jpg,8.jpg,9.jpg,10.jpg,',0),(2,2,'theWit Chicago - a DoubleTree by Hilton Hotel','201 N. State Street','Award-winning downtown hotel and rooftop bar offers a dramatic sensory experience, blending the amenities of a resort with the flair of a boutique hotel','1.jpg,2.jpg,3.jpg,4.jpg,5.jpg,6.jpg,7.jpg,8.jpg,9.jpg,10.jpg,11.jpg,12.jpg,',0),(3,1,'Palmer House - A Hilton Hotel','17 East Monroe Street','AAA Four Diamond, historic hotel located in the heart of the Loop. Steps from theatre, shopping, the Art Institute of Chicago and Millennium Park.','1.jpg,2.jpg,3.jpg,4.jpg,5.jpg,6.jpg,7.jpg,8.jpg,9.jpg,10.jpg,11.jpg,12.jpg,',0),(4,4,'DoubleTree Suites by Hilton Hotel New York City - Times Square','1568 Broadway','All-suite hotel surrounded by Broadway theaters and only steps from Radio City Music Hall, Rockefeller Center, TKTS ticket booth and Central Park.','RA7AD2SIBKJHYHMFUP.jpg,ERFJ3NG27JXKMR4FTO.jpg,32D0XHGR8XIH2R838Q.jpg,Q83L2K7R1ONTEHKNC5.jpg,RWL7QC5CMFKU7WEDWQ.jpg,KQD2TRYLVORK4N618S.jpg,BR5AEBZSGRMKIE4GWM.jpg,V2CAR2M0HNMVAKSP00.jpg,HJ6TC5DUVQYQC7IL41.jpg,I7PH0J328DD2N1VSS7.jpg,30EWVMRGR180SKZ08G.jpg,EOYIIO9C9MOILCUL45.jpg,',0),(5,5,'Conrad Miami','1395 Brickell Avenue','A contemporary luxury hotel overlooking the stunning city and Biscayne Bay, boasting a beautiful spa, rooftop pool, and signature restaurant and lounge.','ISSQX2VNEKGXAJQIZ4.jpg,JXQHW938GXY6HHLKMJ.jpg,4AN61WP8Y42SZCI5KD.jpg,13SPAN2JUV5PHM67Y9.jpg,I6MO5AN4NRH1U95A26.jpg,CQ1PUVKCDTUWMDLB17.jpg,R1L2SDNX1F2RSVIN6K.jpg,NXAWAQTP7CVMKC7OYU.jpg,H8K8K77BFFVMU80A1G.jpg,BFT1IWTXZTGZF2ZQJM.jpg,CHZB4XDHGVD0ACAH08.jpg,',0);
/*!40000 ALTER TABLE `hotel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `seq_no` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(45) DEFAULT NULL,
  `state` varchar(10) DEFAULT NULL,
  `zip` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Chicago','IL','60603'),(2,'Chicago','IL','60601'),(4,'New York','NY','10036'),(5,'Miami','FL','33131');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `seq_no` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) DEFAULT NULL,
  `customer` int(11) DEFAULT NULL,
  `hotel` int(11) DEFAULT NULL,
  `room` int(11) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `check_in` datetime DEFAULT NULL,
  `check_out` datetime DEFAULT NULL,
  `price` double DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,3,1,2,3,'2017-11-29','2017-11-29 14:00:00','2017-12-05 12:00:00',2940,'CHECKED_OUT',0);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_assign`
--

DROP TABLE IF EXISTS `room_assign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room_assign` (
  `seq_no` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) DEFAULT NULL,
  `room_type` int(11) DEFAULT NULL,
  `room_num` int(11) DEFAULT NULL,
  `check_in` timestamp NULL DEFAULT NULL,
  `check_out` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_assign`
--

LOCK TABLES `room_assign` WRITE;
/*!40000 ALTER TABLE `room_assign` DISABLE KEYS */;
INSERT INTO `room_assign` VALUES (1,3,1,203,'2017-11-29 20:00:00','2017-11-30 05:07:42');
/*!40000 ALTER TABLE `room_assign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_type`
--

DROP TABLE IF EXISTS `room_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room_type` (
  `seq_no` int(11) NOT NULL AUTO_INCREMENT,
  `hotel` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `bed_type` varchar(255) DEFAULT NULL,
  `bed_amount` int(11) DEFAULT NULL,
  `people_no` int(11) DEFAULT NULL,
  `view` varchar(45) DEFAULT NULL,
  `is_wifi` tinyint(4) DEFAULT NULL,
  `is_tv` tinyint(4) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `room_list` text,
  `image` text,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_type`
--

LOCK TABLES `room_type` WRITE;
/*!40000 ALTER TABLE `room_type` DISABLE KEYS */;
INSERT INTO `room_type` VALUES (1,1,'1 King Bed','king_bed',1,2,'City',1,1,139,3,'523,312','1.jpg',0),(2,1,'2 Queen Bed','queen_bed',2,4,'City',1,1,199,4,'101,102','1.jpg',0),(3,2,'CLASSIC KING ROOM NONSMOKING','king_bed',1,2,'City',1,1,499,9,'201,203,204,','SVZUMMYDD72WIE0BG5.jpg',0),(4,2,'CLASSIC TWO QUEEN ROOM NONSMOKING','queen_bed',2,4,'City',1,1,429,9,'300,','QHRVADLZXHEV3JS4FF.jpg',0),(5,4,'1 KG BED STD SUITE-LIV RM SOFA BED-NON SMK','king_bed',1,2,'City',1,1,549,11,'100,101,102,103,104,','GNA03CZTRZHVR5JJZ0.jpg',0),(6,4,'1 QN BED PREM SUITE-LIV RM SOFA BED-CITY VW','queen_bed',1,2,'City',1,1,549,11,'201,203,204,','U8I1GAYFC7A527OBRE.jpg',0),(7,5,'CITY VIEW ROOM WITH 1 KING BED','king_bed',1,1,'City',1,1,299,0,'100,101,102,103,104','GNA03CZTRZHVR5JJZ0.jpg',0),(8,5,'BAY VIEW ROOM WITH KING SIZE BED','king_bed',2,4,'Bay',1,1,319,7,'201,203,204,','1FWBIH3PXHRIBMKBLK.jpg',0);
/*!40000 ALTER TABLE `room_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `uid` int(255) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `role` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'manager','manager','manager@website','manager'),(2,'staff','staff','staff@website','staff'),(3,'customer','cust','customer@','customer');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-30 15:07:21
