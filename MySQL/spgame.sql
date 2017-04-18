-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: spgame
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `cards`
--

DROP TABLE IF EXISTS `cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cards` (
  `username` varchar(13) NOT NULL,
  `index` int(11) NOT NULL,
  `id` int(11) DEFAULT NULL,
  `premiumDays` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `skill` int(11) DEFAULT NULL,
  PRIMARY KEY (`username`,`index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cards`
--

LOCK TABLES `cards` WRITE;
/*!40000 ALTER TABLE `cards` DISABLE KEYS */;
INSERT INTO `cards` VALUES ('barak123',0,-1,-1,-1,-1),('barak123',1,-1,-1,-1,-1),('barak123',2,-1,-1,-1,-1),('barak123',3,-1,-1,-1,-1),('barak123',4,-1,-1,-1,-1),('barak123',5,-1,-1,-1,-1),('barak123',6,-1,-1,-1,-1),('barak123',7,-1,-1,-1,-1),('barak123',8,-1,-1,-1,-1),('barak123',9,-1,-1,-1,-1),('barak123',10,-1,-1,-1,-1),('barak123',11,-1,-1,-1,-1),('barak123',12,-1,-1,-1,-1),('barak123',13,-1,-1,-1,-1),('barak123',14,-1,-1,-1,-1),('barak123',15,-1,-1,-1,-1),('barak123',16,-1,-1,-1,-1),('barak123',17,-1,-1,-1,-1),('barak123',18,-1,-1,-1,-1),('barak123',19,-1,-1,-1,-1),('barak123',20,-1,-1,-1,-1),('barak123',21,-1,-1,-1,-1),('barak123',22,-1,-1,-1,-1),('barak123',23,-1,-1,-1,-1),('barak123',24,-1,-1,-1,-1),('barak123',25,-1,-1,-1,-1),('barak123',26,-1,-1,-1,-1),('barak123',27,-1,-1,-1,-1),('barak123',28,-1,-1,-1,-1),('barak123',29,-1,-1,-1,-1),('barak123',30,-1,-1,-1,-1),('barak123',31,-1,-1,-1,-1),('barak123',32,-1,-1,-1,-1),('barak123',33,-1,-1,-1,-1),('barak123',34,-1,-1,-1,-1),('barak123',35,-1,-1,-1,-1),('barak123',36,-1,-1,-1,-1),('barak123',37,-1,-1,-1,-1),('barak123',38,-1,-1,-1,-1),('barak123',39,-1,-1,-1,-1),('barak123',40,-1,-1,-1,-1),('barak123',41,-1,-1,-1,-1),('barak123',42,-1,-1,-1,-1),('barak123',43,-1,-1,-1,-1),('barak123',44,-1,-1,-1,-1),('barak123',45,-1,-1,-1,-1),('barak123',46,-1,-1,-1,-1),('barak123',47,-1,-1,-1,-1),('barak123',48,-1,-1,-1,-1),('barak123',49,-1,-1,-1,-1),('barak123',50,-1,-1,-1,-1),('barak123',51,-1,-1,-1,-1),('barak123',52,-1,-1,-1,-1),('barak123',53,-1,-1,-1,-1),('barak123',54,-1,-1,-1,-1),('barak123',55,-1,-1,-1,-1),('barak123',56,-1,-1,-1,-1),('barak123',57,-1,-1,-1,-1),('barak123',58,-1,-1,-1,-1),('barak123',59,-1,-1,-1,-1),('barak123',60,-1,-1,-1,-1),('barak123',61,-1,-1,-1,-1),('barak123',62,-1,-1,-1,-1),('barak123',63,-1,-1,-1,-1),('barak123',64,-1,-1,-1,-1),('barak123',65,-1,-1,-1,-1),('barak123',66,-1,-1,-1,-1),('barak123',67,-1,-1,-1,-1),('barak123',68,-1,-1,-1,-1),('barak123',69,-1,-1,-1,-1),('barak123',70,-1,-1,-1,-1),('barak123',71,-1,-1,-1,-1),('barak123',72,-1,-1,-1,-1),('barak123',73,-1,-1,-1,-1),('barak123',74,-1,-1,-1,-1),('barak123',75,-1,-1,-1,-1),('barak123',76,-1,-1,-1,-1),('barak123',77,-1,-1,-1,-1),('barak123',78,-1,-1,-1,-1),('barak123',79,-1,-1,-1,-1),('barak123',80,-1,-1,-1,-1),('barak123',81,-1,-1,-1,-1),('barak123',82,-1,-1,-1,-1),('barak123',83,-1,-1,-1,-1),('barak123',84,-1,-1,-1,-1),('barak123',85,-1,-1,-1,-1),('barak123',86,-1,-1,-1,-1),('barak123',87,-1,-1,-1,-1),('barak123',88,-1,-1,-1,-1),('barak123',89,-1,-1,-1,-1),('barak123',90,-1,-1,-1,-1),('barak123',91,-1,-1,-1,-1),('barak123',92,-1,-1,-1,-1),('barak123',93,-1,-1,-1,-1),('barak123',94,-1,-1,-1,-1),('barak123',95,-1,-1,-1,-1),('barak124',0,-1,-1,-1,-1),('barak124',1,-1,-1,-1,-1),('barak124',2,-1,-1,-1,-1),('barak124',3,-1,-1,-1,-1),('barak124',4,-1,-1,-1,-1),('barak124',5,-1,-1,-1,-1),('barak124',6,-1,-1,-1,-1),('barak124',7,-1,-1,-1,-1),('barak124',8,-1,-1,-1,-1),('barak124',9,-1,-1,-1,-1),('barak124',10,-1,-1,-1,-1),('barak124',11,-1,-1,-1,-1),('barak124',12,-1,-1,-1,-1),('barak124',13,-1,-1,-1,-1),('barak124',14,-1,-1,-1,-1),('barak124',15,-1,-1,-1,-1),('barak124',16,-1,-1,-1,-1),('barak124',17,-1,-1,-1,-1),('barak124',18,-1,-1,-1,-1),('barak124',19,-1,-1,-1,-1),('barak124',20,-1,-1,-1,-1),('barak124',21,-1,-1,-1,-1),('barak124',22,-1,-1,-1,-1),('barak124',23,-1,-1,-1,-1),('barak124',24,-1,-1,-1,-1),('barak124',25,-1,-1,-1,-1),('barak124',26,-1,-1,-1,-1),('barak124',27,-1,-1,-1,-1),('barak124',28,-1,-1,-1,-1),('barak124',29,-1,-1,-1,-1),('barak124',30,-1,-1,-1,-1),('barak124',31,-1,-1,-1,-1),('barak124',32,-1,-1,-1,-1),('barak124',33,-1,-1,-1,-1),('barak124',34,-1,-1,-1,-1),('barak124',35,-1,-1,-1,-1),('barak124',36,-1,-1,-1,-1),('barak124',37,-1,-1,-1,-1),('barak124',38,-1,-1,-1,-1),('barak124',39,-1,-1,-1,-1),('barak124',40,-1,-1,-1,-1),('barak124',41,-1,-1,-1,-1),('barak124',42,-1,-1,-1,-1),('barak124',43,-1,-1,-1,-1),('barak124',44,-1,-1,-1,-1),('barak124',45,-1,-1,-1,-1),('barak124',46,-1,-1,-1,-1),('barak124',47,-1,-1,-1,-1),('barak124',48,-1,-1,-1,-1),('barak124',49,-1,-1,-1,-1),('barak124',50,-1,-1,-1,-1),('barak124',51,-1,-1,-1,-1),('barak124',52,-1,-1,-1,-1),('barak124',53,-1,-1,-1,-1),('barak124',54,-1,-1,-1,-1),('barak124',55,-1,-1,-1,-1),('barak124',56,-1,-1,-1,-1),('barak124',57,-1,-1,-1,-1),('barak124',58,-1,-1,-1,-1),('barak124',59,-1,-1,-1,-1),('barak124',60,-1,-1,-1,-1),('barak124',61,-1,-1,-1,-1),('barak124',62,-1,-1,-1,-1),('barak124',63,-1,-1,-1,-1),('barak124',64,-1,-1,-1,-1),('barak124',65,-1,-1,-1,-1),('barak124',66,-1,-1,-1,-1),('barak124',67,-1,-1,-1,-1),('barak124',68,-1,-1,-1,-1),('barak124',69,-1,-1,-1,-1),('barak124',70,-1,-1,-1,-1),('barak124',71,-1,-1,-1,-1),('barak124',72,-1,-1,-1,-1),('barak124',73,-1,-1,-1,-1),('barak124',74,-1,-1,-1,-1),('barak124',75,-1,-1,-1,-1),('barak124',76,-1,-1,-1,-1),('barak124',77,-1,-1,-1,-1),('barak124',78,-1,-1,-1,-1),('barak124',79,-1,-1,-1,-1),('barak124',80,-1,-1,-1,-1),('barak124',81,-1,-1,-1,-1),('barak124',82,-1,-1,-1,-1),('barak124',83,-1,-1,-1,-1),('barak124',84,-1,-1,-1,-1),('barak124',85,-1,-1,-1,-1),('barak124',86,-1,-1,-1,-1),('barak124',87,-1,-1,-1,-1),('barak124',88,-1,-1,-1,-1),('barak124',89,-1,-1,-1,-1),('barak124',90,-1,-1,-1,-1),('barak124',91,-1,-1,-1,-1),('barak124',92,-1,-1,-1,-1),('barak124',93,-1,-1,-1,-1),('barak124',94,-1,-1,-1,-1),('barak124',95,-1,-1,-1,-1),('barak2',0,1111,0,8,0),('barak2',1,1122,365,8,100200000),('barak2',2,2900,100,-1,-1),('barak2',3,2150,365,0,0),('barak2',4,2016,6000,0,0),('barak2',5,2015,6000,0,0),('barak2',6,2016,6000,0,0),('barak2',7,2015,6000,0,0),('barak2',8,2017,40,0,0),('barak2',9,2004,6000,0,0),('barak2',10,2005,6000,0,0),('barak2',11,2016,6000,10,0),('barak2',12,2016,6000,10,0),('barak2',13,2900,365,0,0),('barak2',14,1224,0,3,100200000),('barak2',15,1234,30,0,0),('barak2',16,1224,0,3,100200000),('barak2',17,2009,6000,10,0),('barak2',18,1224,0,3,100200000),('barak2',19,1122,365,8,100200000),('barak2',20,2005,6000,0,0),('barak2',21,1224,0,3,100200000),('barak2',22,-1,-1,-1,-1),('barak2',23,-1,-1,-1,-1),('barak2',24,-1,-1,-1,-1),('barak2',25,-1,-1,-1,-1),('barak2',26,-1,-1,-1,-1),('barak2',27,-1,-1,-1,-1),('barak2',28,-1,-1,-1,-1),('barak2',29,-1,-1,-1,-1),('barak2',30,-1,-1,-1,-1),('barak2',31,-1,-1,-1,-1),('barak2',32,-1,-1,-1,-1),('barak2',33,-1,-1,-1,-1),('barak2',34,-1,-1,-1,-1),('barak2',35,-1,-1,-1,-1),('barak2',36,-1,-1,-1,-1),('barak2',37,-1,-1,-1,-1),('barak2',38,-1,-1,-1,-1),('barak2',39,-1,-1,-1,-1),('barak2',40,-1,-1,-1,-1),('barak2',41,-1,-1,-1,-1),('barak2',42,-1,-1,-1,-1),('barak2',43,-1,-1,-1,-1),('barak2',44,-1,-1,-1,-1),('barak2',45,-1,-1,-1,-1),('barak2',46,-1,-1,-1,-1),('barak2',47,-1,-1,-1,-1),('barak2',48,-1,-1,-1,-1),('barak2',49,-1,-1,-1,-1),('barak2',50,-1,-1,-1,-1),('barak2',51,-1,-1,-1,-1),('barak2',52,-1,-1,-1,-1),('barak2',53,-1,-1,-1,-1),('barak2',54,-1,-1,-1,-1),('barak2',55,-1,-1,-1,-1),('barak2',56,-1,-1,-1,-1),('barak2',57,-1,-1,-1,-1),('barak2',58,-1,-1,-1,-1),('barak2',59,-1,-1,-1,-1),('barak2',60,-1,-1,-1,-1),('barak2',61,-1,-1,-1,-1),('barak2',62,-1,-1,-1,-1),('barak2',63,-1,-1,-1,-1),('barak2',64,-1,-1,-1,-1),('barak2',65,-1,-1,-1,-1),('barak2',66,-1,-1,-1,-1),('barak2',67,-1,-1,-1,-1),('barak2',68,-1,-1,-1,-1),('barak2',69,-1,-1,-1,-1),('barak2',70,-1,-1,-1,-1),('barak2',71,-1,-1,-1,-1),('barak2',72,-1,-1,-1,-1),('barak2',73,-1,-1,-1,-1),('barak2',74,-1,-1,-1,-1),('barak2',75,-1,-1,-1,-1),('barak2',76,-1,-1,-1,-1),('barak2',77,-1,-1,-1,-1),('barak2',78,-1,-1,-1,-1),('barak2',79,-1,-1,-1,-1),('barak2',80,-1,-1,-1,-1),('barak2',81,-1,-1,-1,-1),('barak2',82,-1,-1,-1,-1),('barak2',83,-1,-1,-1,-1),('barak2',84,-1,-1,-1,-1),('barak2',85,-1,-1,-1,-1),('barak2',86,-1,-1,-1,-1),('barak2',87,-1,-1,-1,-1),('barak2',88,-1,-1,-1,-1),('barak2',89,-1,-1,-1,-1),('barak2',90,-1,-1,-1,-1),('barak2',91,-1,-1,-1,-1),('barak2',92,-1,-1,-1,-1),('barak2',93,-1,-1,-1,-1),('barak2',94,-1,-1,-1,-1),('barak2',95,-1,-1,-1,-1),('barak3',0,-1,-1,-1,-1),('barak3',1,-1,-1,-1,-1),('barak3',2,-1,-1,-1,-1),('barak3',3,-1,-1,-1,-1),('barak3',4,-1,-1,-1,-1),('barak3',5,-1,-1,-1,-1),('barak3',6,-1,-1,-1,-1),('barak3',7,-1,-1,-1,-1),('barak3',8,-1,-1,-1,-1),('barak3',9,-1,-1,-1,-1),('barak3',10,-1,-1,-1,-1),('barak3',11,-1,-1,-1,-1),('barak3',12,-1,-1,-1,-1),('barak3',13,-1,-1,-1,-1),('barak3',14,-1,-1,-1,-1),('barak3',15,-1,-1,-1,-1),('barak3',16,-1,-1,-1,-1),('barak3',17,-1,-1,-1,-1),('barak3',18,-1,-1,-1,-1),('barak3',19,-1,-1,-1,-1),('barak3',20,-1,-1,-1,-1),('barak3',21,-1,-1,-1,-1),('barak3',22,-1,-1,-1,-1),('barak3',23,-1,-1,-1,-1),('barak3',24,-1,-1,-1,-1),('barak3',25,-1,-1,-1,-1),('barak3',26,-1,-1,-1,-1),('barak3',27,-1,-1,-1,-1),('barak3',28,-1,-1,-1,-1),('barak3',29,-1,-1,-1,-1),('barak3',30,-1,-1,-1,-1),('barak3',31,-1,-1,-1,-1),('barak3',32,-1,-1,-1,-1),('barak3',33,-1,-1,-1,-1),('barak3',34,-1,-1,-1,-1),('barak3',35,-1,-1,-1,-1),('barak3',36,-1,-1,-1,-1),('barak3',37,-1,-1,-1,-1),('barak3',38,-1,-1,-1,-1),('barak3',39,-1,-1,-1,-1),('barak3',40,-1,-1,-1,-1),('barak3',41,-1,-1,-1,-1),('barak3',42,-1,-1,-1,-1),('barak3',43,-1,-1,-1,-1),('barak3',44,-1,-1,-1,-1),('barak3',45,-1,-1,-1,-1),('barak3',46,-1,-1,-1,-1),('barak3',47,-1,-1,-1,-1),('barak3',48,-1,-1,-1,-1),('barak3',49,-1,-1,-1,-1),('barak3',50,-1,-1,-1,-1),('barak3',51,-1,-1,-1,-1),('barak3',52,-1,-1,-1,-1),('barak3',53,-1,-1,-1,-1),('barak3',54,-1,-1,-1,-1),('barak3',55,-1,-1,-1,-1),('barak3',56,-1,-1,-1,-1),('barak3',57,-1,-1,-1,-1),('barak3',58,-1,-1,-1,-1),('barak3',59,-1,-1,-1,-1),('barak3',60,-1,-1,-1,-1),('barak3',61,-1,-1,-1,-1),('barak3',62,-1,-1,-1,-1),('barak3',63,-1,-1,-1,-1),('barak3',64,-1,-1,-1,-1),('barak3',65,-1,-1,-1,-1),('barak3',66,-1,-1,-1,-1),('barak3',67,-1,-1,-1,-1),('barak3',68,-1,-1,-1,-1),('barak3',69,-1,-1,-1,-1),('barak3',70,-1,-1,-1,-1),('barak3',71,-1,-1,-1,-1),('barak3',72,-1,-1,-1,-1),('barak3',73,-1,-1,-1,-1),('barak3',74,-1,-1,-1,-1),('barak3',75,-1,-1,-1,-1),('barak3',76,-1,-1,-1,-1),('barak3',77,-1,-1,-1,-1),('barak3',78,-1,-1,-1,-1),('barak3',79,-1,-1,-1,-1),('barak3',80,-1,-1,-1,-1),('barak3',81,-1,-1,-1,-1),('barak3',82,-1,-1,-1,-1),('barak3',83,-1,-1,-1,-1),('barak3',84,-1,-1,-1,-1),('barak3',85,-1,-1,-1,-1),('barak3',86,-1,-1,-1,-1),('barak3',87,-1,-1,-1,-1),('barak3',88,-1,-1,-1,-1),('barak3',89,-1,-1,-1,-1),('barak3',90,-1,-1,-1,-1),('barak3',91,-1,-1,-1,-1),('barak3',92,-1,-1,-1,-1),('barak3',93,-1,-1,-1,-1),('barak3',94,-1,-1,-1,-1),('barak3',95,-1,-1,-1,-1),('barak4',0,-1,-1,-1,-1),('barak4',1,-1,-1,-1,-1),('barak4',2,-1,-1,-1,-1),('barak4',3,-1,-1,-1,-1),('barak4',4,-1,-1,-1,-1),('barak4',5,-1,-1,-1,-1),('barak4',6,-1,-1,-1,-1),('barak4',7,-1,-1,-1,-1),('barak4',8,-1,-1,-1,-1),('barak4',9,-1,-1,-1,-1),('barak4',10,-1,-1,-1,-1),('barak4',11,-1,-1,-1,-1),('barak4',12,-1,-1,-1,-1),('barak4',13,-1,-1,-1,-1),('barak4',14,-1,-1,-1,-1),('barak4',15,-1,-1,-1,-1),('barak4',16,-1,-1,-1,-1),('barak4',17,-1,-1,-1,-1),('barak4',18,-1,-1,-1,-1),('barak4',19,-1,-1,-1,-1),('barak4',20,-1,-1,-1,-1),('barak4',21,-1,-1,-1,-1),('barak4',22,-1,-1,-1,-1),('barak4',23,-1,-1,-1,-1),('barak4',24,-1,-1,-1,-1),('barak4',25,-1,-1,-1,-1),('barak4',26,-1,-1,-1,-1),('barak4',27,-1,-1,-1,-1),('barak4',28,-1,-1,-1,-1),('barak4',29,-1,-1,-1,-1),('barak4',30,-1,-1,-1,-1),('barak4',31,-1,-1,-1,-1),('barak4',32,-1,-1,-1,-1),('barak4',33,-1,-1,-1,-1),('barak4',34,-1,-1,-1,-1),('barak4',35,-1,-1,-1,-1),('barak4',36,-1,-1,-1,-1),('barak4',37,-1,-1,-1,-1),('barak4',38,-1,-1,-1,-1),('barak4',39,-1,-1,-1,-1),('barak4',40,-1,-1,-1,-1),('barak4',41,-1,-1,-1,-1),('barak4',42,-1,-1,-1,-1),('barak4',43,-1,-1,-1,-1),('barak4',44,-1,-1,-1,-1),('barak4',45,-1,-1,-1,-1),('barak4',46,-1,-1,-1,-1),('barak4',47,-1,-1,-1,-1),('barak4',48,-1,-1,-1,-1),('barak4',49,-1,-1,-1,-1),('barak4',50,-1,-1,-1,-1),('barak4',51,-1,-1,-1,-1),('barak4',52,-1,-1,-1,-1),('barak4',53,-1,-1,-1,-1),('barak4',54,-1,-1,-1,-1),('barak4',55,-1,-1,-1,-1),('barak4',56,-1,-1,-1,-1),('barak4',57,-1,-1,-1,-1),('barak4',58,-1,-1,-1,-1),('barak4',59,-1,-1,-1,-1),('barak4',60,-1,-1,-1,-1),('barak4',61,-1,-1,-1,-1),('barak4',62,-1,-1,-1,-1),('barak4',63,-1,-1,-1,-1),('barak4',64,-1,-1,-1,-1),('barak4',65,-1,-1,-1,-1),('barak4',66,-1,-1,-1,-1),('barak4',67,-1,-1,-1,-1),('barak4',68,-1,-1,-1,-1),('barak4',69,-1,-1,-1,-1),('barak4',70,-1,-1,-1,-1),('barak4',71,-1,-1,-1,-1),('barak4',72,-1,-1,-1,-1),('barak4',73,-1,-1,-1,-1),('barak4',74,-1,-1,-1,-1),('barak4',75,-1,-1,-1,-1),('barak4',76,-1,-1,-1,-1),('barak4',77,-1,-1,-1,-1),('barak4',78,-1,-1,-1,-1),('barak4',79,-1,-1,-1,-1),('barak4',80,-1,-1,-1,-1),('barak4',81,-1,-1,-1,-1),('barak4',82,-1,-1,-1,-1),('barak4',83,-1,-1,-1,-1),('barak4',84,-1,-1,-1,-1),('barak4',85,-1,-1,-1,-1),('barak4',86,-1,-1,-1,-1),('barak4',87,-1,-1,-1,-1),('barak4',88,-1,-1,-1,-1),('barak4',89,-1,-1,-1,-1),('barak4',90,-1,-1,-1,-1),('barak4',91,-1,-1,-1,-1),('barak4',92,-1,-1,-1,-1),('barak4',93,-1,-1,-1,-1),('barak4',94,-1,-1,-1,-1),('barak4',95,-1,-1,-1,-1),('baraklevy',0,2003,730,0,0),('baraklevy',1,1321,0,1,0),('baraklevy',2,1121,791,8,100200000),('baraklevy',3,2900,365,0,0),('baraklevy',4,2005,6000,0,0),('baraklevy',5,2005,6000,0,0),('baraklevy',6,2005,6000,0,0),('baraklevy',7,1224,0,0,0),('baraklevy',8,2005,6000,0,0),('baraklevy',9,2005,6000,0,0),('baraklevy',10,2005,6000,0,0),('baraklevy',11,2005,6000,0,0),('baraklevy',12,1331,365,8,100200000),('baraklevy',13,2002,365,0,0),('baraklevy',14,2001,365,0,0),('baraklevy',15,5001,6000,0,0),('baraklevy',16,2008,365,0,0),('baraklevy',17,2506,6000,0,0),('baraklevy',18,2021,30,0,0),('baraklevy',19,2009,6000,6,0),('baraklevy',20,1122,365,8,100200000),('baraklevy',21,1322,365,8,100200000),('baraklevy',22,1224,365,8,100200000),('baraklevy',23,3010,0,0,0),('baraklevy',24,1223,0,0,0),('baraklevy',25,1223,0,0,0),('baraklevy',26,1222,0,0,0),('baraklevy',27,-1,-1,-1,-1),('baraklevy',28,-1,-1,-1,-1),('baraklevy',29,-1,-1,-1,-1),('baraklevy',30,-1,-1,-1,-1),('baraklevy',31,-1,-1,-1,-1),('baraklevy',32,-1,-1,-1,-1),('baraklevy',33,-1,-1,-1,-1),('baraklevy',34,-1,-1,-1,-1),('baraklevy',35,-1,-1,-1,-1),('baraklevy',36,-1,-1,-1,-1),('baraklevy',37,-1,-1,-1,-1),('baraklevy',38,-1,-1,-1,-1),('baraklevy',39,-1,-1,-1,-1),('baraklevy',40,-1,-1,-1,-1),('baraklevy',41,-1,-1,-1,-1),('baraklevy',42,-1,-1,-1,-1),('baraklevy',43,-1,-1,-1,-1),('baraklevy',44,-1,-1,-1,-1),('baraklevy',45,-1,-1,-1,-1),('baraklevy',46,-1,-1,-1,-1),('baraklevy',47,-1,-1,-1,-1),('baraklevy',48,-1,-1,-1,-1),('baraklevy',49,-1,-1,-1,-1),('baraklevy',50,-1,-1,-1,-1),('baraklevy',51,-1,-1,-1,-1),('baraklevy',52,-1,-1,-1,-1),('baraklevy',53,-1,-1,-1,-1),('baraklevy',54,-1,-1,-1,-1),('baraklevy',55,-1,-1,-1,-1),('baraklevy',56,-1,-1,-1,-1),('baraklevy',57,-1,-1,-1,-1),('baraklevy',58,-1,-1,-1,-1),('baraklevy',59,-1,-1,-1,-1),('baraklevy',60,-1,-1,-1,-1),('baraklevy',61,-1,-1,-1,-1),('baraklevy',62,-1,-1,-1,-1),('baraklevy',63,-1,-1,-1,-1),('baraklevy',64,-1,-1,-1,-1),('baraklevy',65,-1,-1,-1,-1),('baraklevy',66,-1,-1,-1,-1),('baraklevy',67,-1,-1,-1,-1),('baraklevy',68,-1,-1,-1,-1),('baraklevy',69,-1,-1,-1,-1),('baraklevy',70,-1,-1,-1,-1),('baraklevy',71,-1,-1,-1,-1),('baraklevy',72,-1,-1,-1,-1),('baraklevy',73,-1,-1,-1,-1),('baraklevy',74,-1,-1,-1,-1),('baraklevy',75,-1,-1,-1,-1),('baraklevy',76,-1,-1,-1,-1),('baraklevy',77,-1,-1,-1,-1),('baraklevy',78,-1,-1,-1,-1),('baraklevy',79,-1,-1,-1,-1),('baraklevy',80,-1,-1,-1,-1),('baraklevy',81,-1,-1,-1,-1),('baraklevy',82,-1,-1,-1,-1),('baraklevy',83,-1,-1,-1,-1),('baraklevy',84,-1,-1,-1,-1),('baraklevy',85,-1,-1,-1,-1),('baraklevy',86,-1,-1,-1,-1),('baraklevy',87,-1,-1,-1,-1),('baraklevy',88,-1,-1,-1,-1),('baraklevy',89,-1,-1,-1,-1),('baraklevy',90,-1,-1,-1,-1),('baraklevy',91,-1,-1,-1,-1),('baraklevy',92,-1,-1,-1,-1),('baraklevy',93,-1,-1,-1,-1),('baraklevy',94,-1,-1,-1,-1),('baraklevy',95,-1,-1,-1,-1),('baraklevya',0,-1,-1,-1,-1),('baraklevya',1,-1,-1,-1,-1),('baraklevya',2,-1,-1,-1,-1),('baraklevya',3,-1,-1,-1,-1),('baraklevya',4,-1,-1,-1,-1),('baraklevya',5,-1,-1,-1,-1),('baraklevya',6,-1,-1,-1,-1),('baraklevya',7,-1,-1,-1,-1),('baraklevya',8,-1,-1,-1,-1),('baraklevya',9,-1,-1,-1,-1),('baraklevya',10,-1,-1,-1,-1),('baraklevya',11,-1,-1,-1,-1),('baraklevya',12,-1,-1,-1,-1),('baraklevya',13,-1,-1,-1,-1),('baraklevya',14,-1,-1,-1,-1),('baraklevya',15,-1,-1,-1,-1),('baraklevya',16,-1,-1,-1,-1),('baraklevya',17,-1,-1,-1,-1),('baraklevya',18,-1,-1,-1,-1),('baraklevya',19,-1,-1,-1,-1),('baraklevya',20,-1,-1,-1,-1),('baraklevya',21,-1,-1,-1,-1),('baraklevya',22,-1,-1,-1,-1),('baraklevya',23,-1,-1,-1,-1),('baraklevya',24,-1,-1,-1,-1),('baraklevya',25,-1,-1,-1,-1),('baraklevya',26,-1,-1,-1,-1),('baraklevya',27,-1,-1,-1,-1),('baraklevya',28,-1,-1,-1,-1),('baraklevya',29,-1,-1,-1,-1),('baraklevya',30,-1,-1,-1,-1),('baraklevya',31,-1,-1,-1,-1),('baraklevya',32,-1,-1,-1,-1),('baraklevya',33,-1,-1,-1,-1),('baraklevya',34,-1,-1,-1,-1),('baraklevya',35,-1,-1,-1,-1),('baraklevya',36,-1,-1,-1,-1),('baraklevya',37,-1,-1,-1,-1),('baraklevya',38,-1,-1,-1,-1),('baraklevya',39,-1,-1,-1,-1),('baraklevya',40,-1,-1,-1,-1),('baraklevya',41,-1,-1,-1,-1),('baraklevya',42,-1,-1,-1,-1),('baraklevya',43,-1,-1,-1,-1),('baraklevya',44,-1,-1,-1,-1),('baraklevya',45,-1,-1,-1,-1),('baraklevya',46,-1,-1,-1,-1),('baraklevya',47,-1,-1,-1,-1),('baraklevya',48,-1,-1,-1,-1),('baraklevya',49,-1,-1,-1,-1),('baraklevya',50,-1,-1,-1,-1),('baraklevya',51,-1,-1,-1,-1),('baraklevya',52,-1,-1,-1,-1),('baraklevya',53,-1,-1,-1,-1),('baraklevya',54,-1,-1,-1,-1),('baraklevya',55,-1,-1,-1,-1),('baraklevya',56,-1,-1,-1,-1),('baraklevya',57,-1,-1,-1,-1),('baraklevya',58,-1,-1,-1,-1),('baraklevya',59,-1,-1,-1,-1),('baraklevya',60,-1,-1,-1,-1),('baraklevya',61,-1,-1,-1,-1),('baraklevya',62,-1,-1,-1,-1),('baraklevya',63,-1,-1,-1,-1),('baraklevya',64,-1,-1,-1,-1),('baraklevya',65,-1,-1,-1,-1),('baraklevya',66,-1,-1,-1,-1),('baraklevya',67,-1,-1,-1,-1),('baraklevya',68,-1,-1,-1,-1),('baraklevya',69,-1,-1,-1,-1),('baraklevya',70,-1,-1,-1,-1),('baraklevya',71,-1,-1,-1,-1),('baraklevya',72,-1,-1,-1,-1),('baraklevya',73,-1,-1,-1,-1),('baraklevya',74,-1,-1,-1,-1),('baraklevya',75,-1,-1,-1,-1),('baraklevya',76,-1,-1,-1,-1),('baraklevya',77,-1,-1,-1,-1),('baraklevya',78,-1,-1,-1,-1),('baraklevya',79,-1,-1,-1,-1),('baraklevya',80,-1,-1,-1,-1),('baraklevya',81,-1,-1,-1,-1),('baraklevya',82,-1,-1,-1,-1),('baraklevya',83,-1,-1,-1,-1),('baraklevya',84,-1,-1,-1,-1),('baraklevya',85,-1,-1,-1,-1),('baraklevya',86,-1,-1,-1,-1),('baraklevya',87,-1,-1,-1,-1),('baraklevya',88,-1,-1,-1,-1),('baraklevya',89,-1,-1,-1,-1),('baraklevya',90,-1,-1,-1,-1),('baraklevya',91,-1,-1,-1,-1),('baraklevya',92,-1,-1,-1,-1),('baraklevya',93,-1,-1,-1,-1),('baraklevya',94,-1,-1,-1,-1),('baraklevya',95,-1,-1,-1,-1),('baraklevyaaa',0,-1,-1,-1,-1),('baraklevyaaa',1,-1,-1,-1,-1),('baraklevyaaa',2,-1,-1,-1,-1),('baraklevyaaa',3,-1,-1,-1,-1),('baraklevyaaa',4,-1,-1,-1,-1),('baraklevyaaa',5,-1,-1,-1,-1),('baraklevyaaa',6,-1,-1,-1,-1),('baraklevyaaa',7,-1,-1,-1,-1),('baraklevyaaa',8,-1,-1,-1,-1),('baraklevyaaa',9,-1,-1,-1,-1),('baraklevyaaa',10,-1,-1,-1,-1),('baraklevyaaa',11,-1,-1,-1,-1),('baraklevyaaa',12,-1,-1,-1,-1),('baraklevyaaa',13,-1,-1,-1,-1),('baraklevyaaa',14,-1,-1,-1,-1),('baraklevyaaa',15,-1,-1,-1,-1),('baraklevyaaa',16,-1,-1,-1,-1),('baraklevyaaa',17,-1,-1,-1,-1),('baraklevyaaa',18,-1,-1,-1,-1),('baraklevyaaa',19,-1,-1,-1,-1),('baraklevyaaa',20,-1,-1,-1,-1),('baraklevyaaa',21,-1,-1,-1,-1),('baraklevyaaa',22,-1,-1,-1,-1),('baraklevyaaa',23,-1,-1,-1,-1),('baraklevyaaa',24,-1,-1,-1,-1),('baraklevyaaa',25,-1,-1,-1,-1),('baraklevyaaa',26,-1,-1,-1,-1),('baraklevyaaa',27,-1,-1,-1,-1),('baraklevyaaa',28,-1,-1,-1,-1),('baraklevyaaa',29,-1,-1,-1,-1),('baraklevyaaa',30,-1,-1,-1,-1),('baraklevyaaa',31,-1,-1,-1,-1),('baraklevyaaa',32,-1,-1,-1,-1),('baraklevyaaa',33,-1,-1,-1,-1),('baraklevyaaa',34,-1,-1,-1,-1),('baraklevyaaa',35,-1,-1,-1,-1),('baraklevyaaa',36,-1,-1,-1,-1),('baraklevyaaa',37,-1,-1,-1,-1),('baraklevyaaa',38,-1,-1,-1,-1),('baraklevyaaa',39,-1,-1,-1,-1),('baraklevyaaa',40,-1,-1,-1,-1),('baraklevyaaa',41,-1,-1,-1,-1),('baraklevyaaa',42,-1,-1,-1,-1),('baraklevyaaa',43,-1,-1,-1,-1),('baraklevyaaa',44,-1,-1,-1,-1),('baraklevyaaa',45,-1,-1,-1,-1),('baraklevyaaa',46,-1,-1,-1,-1),('baraklevyaaa',47,-1,-1,-1,-1),('baraklevyaaa',48,-1,-1,-1,-1),('baraklevyaaa',49,-1,-1,-1,-1),('baraklevyaaa',50,-1,-1,-1,-1),('baraklevyaaa',51,-1,-1,-1,-1),('baraklevyaaa',52,-1,-1,-1,-1),('baraklevyaaa',53,-1,-1,-1,-1),('baraklevyaaa',54,-1,-1,-1,-1),('baraklevyaaa',55,-1,-1,-1,-1),('baraklevyaaa',56,-1,-1,-1,-1),('baraklevyaaa',57,-1,-1,-1,-1),('baraklevyaaa',58,-1,-1,-1,-1),('baraklevyaaa',59,-1,-1,-1,-1),('baraklevyaaa',60,-1,-1,-1,-1),('baraklevyaaa',61,-1,-1,-1,-1),('baraklevyaaa',62,-1,-1,-1,-1),('baraklevyaaa',63,-1,-1,-1,-1),('baraklevyaaa',64,-1,-1,-1,-1),('baraklevyaaa',65,-1,-1,-1,-1),('baraklevyaaa',66,-1,-1,-1,-1),('baraklevyaaa',67,-1,-1,-1,-1),('baraklevyaaa',68,-1,-1,-1,-1),('baraklevyaaa',69,-1,-1,-1,-1),('baraklevyaaa',70,-1,-1,-1,-1),('baraklevyaaa',71,-1,-1,-1,-1),('baraklevyaaa',72,-1,-1,-1,-1),('baraklevyaaa',73,-1,-1,-1,-1),('baraklevyaaa',74,-1,-1,-1,-1),('baraklevyaaa',75,-1,-1,-1,-1),('baraklevyaaa',76,-1,-1,-1,-1),('baraklevyaaa',77,-1,-1,-1,-1),('baraklevyaaa',78,-1,-1,-1,-1),('baraklevyaaa',79,-1,-1,-1,-1),('baraklevyaaa',80,-1,-1,-1,-1),('baraklevyaaa',81,-1,-1,-1,-1),('baraklevyaaa',82,-1,-1,-1,-1),('baraklevyaaa',83,-1,-1,-1,-1),('baraklevyaaa',84,-1,-1,-1,-1),('baraklevyaaa',85,-1,-1,-1,-1),('baraklevyaaa',86,-1,-1,-1,-1),('baraklevyaaa',87,-1,-1,-1,-1),('baraklevyaaa',88,-1,-1,-1,-1),('baraklevyaaa',89,-1,-1,-1,-1),('baraklevyaaa',90,-1,-1,-1,-1),('baraklevyaaa',91,-1,-1,-1,-1),('baraklevyaaa',92,-1,-1,-1,-1),('baraklevyaaa',93,-1,-1,-1,-1),('baraklevyaaa',94,-1,-1,-1,-1),('baraklevyaaa',95,-1,-1,-1,-1);
/*!40000 ALTER TABLE `cards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends` (
  `username` varchar(13) NOT NULL,
  `friend` varchar(13) NOT NULL,
  PRIMARY KEY (`username`,`friend`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` VALUES ('barak2','baraklevy'),('barak3','baraklevy'),('baraklevy','barak2'),('baraklevy','barak3'),('baraklevy','barak4');
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gift`
--

DROP TABLE IF EXISTS `gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gift` (
  `gift_id` int(11) NOT NULL AUTO_INCREMENT,
  `from_username` varchar(12) DEFAULT NULL,
  `to_username` varchar(12) DEFAULT NULL,
  `gift_type` int(11) DEFAULT NULL,
  `amount` bigint(20) DEFAULT NULL,
  `card_id` int(11) DEFAULT NULL,
  `card_premium_days` int(11) DEFAULT NULL,
  `card_level` int(11) DEFAULT NULL,
  `card_skill` int(11) DEFAULT NULL,
  PRIMARY KEY (`gift_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gift`
--

LOCK TABLES `gift` WRITE;
/*!40000 ALTER TABLE `gift` DISABLE KEYS */;
/*!40000 ALTER TABLE `gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild`
--

DROP TABLE IF EXISTS `guild`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild` (
  `guildName` varchar(13) NOT NULL,
  PRIMARY KEY (`guildName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild`
--

LOCK TABLES `guild` WRITE;
/*!40000 ALTER TABLE `guild` DISABLE KEYS */;
INSERT INTO `guild` VALUES ('Guild3'),('GuildNew');
/*!40000 ALTER TABLE `guild` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_member`
--

DROP TABLE IF EXISTS `guild_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_member` (
  `guildName` varchar(13) NOT NULL,
  `username` varchar(13) NOT NULL,
  PRIMARY KEY (`guildName`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_member`
--

LOCK TABLES `guild_member` WRITE;
/*!40000 ALTER TABLE `guild_member` DISABLE KEYS */;
INSERT INTO `guild_member` VALUES ('Guild3','barak2'),('Guild3','baraklevy');
/*!40000 ALTER TABLE `guild_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_score`
--

DROP TABLE IF EXISTS `guild_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_score` (
  `server_hostname` varchar(100) NOT NULL,
  `server_port` int(11) NOT NULL,
  `guild_name` varchar(13) NOT NULL,
  `guild_score` int(11) DEFAULT '0',
  PRIMARY KEY (`server_hostname`,`guild_name`,`server_port`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_score`
--

LOCK TABLES `guild_score` WRITE;
/*!40000 ALTER TABLE `guild_score` DISABLE KEYS */;
INSERT INTO `guild_score` VALUES ('10.0.0.50',21001,'Guild1',50000),('10.0.0.50',21001,'Guild3',334965),('10.0.0.50',21001,'GuildNew',300);
/*!40000 ALTER TABLE `guild_score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memo`
--

DROP TABLE IF EXISTS `memo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `memo` (
  `memo_id` int(11) NOT NULL AUTO_INCREMENT,
  `from_username` varchar(12) DEFAULT NULL,
  `to_username` varchar(12) DEFAULT NULL,
  `message_type` int(11) DEFAULT NULL,
  `level_and_gender` int(11) DEFAULT NULL,
  `unknown2` int(11) DEFAULT NULL,
  `message_text` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`memo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memo`
--

LOCK TABLES `memo` WRITE;
/*!40000 ALTER TABLE `memo` DISABLE KEYS */;
/*!40000 ALTER TABLE `memo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servers`
--

DROP TABLE IF EXISTS `servers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servers` (
  `hostname` varchar(100) NOT NULL DEFAULT '127.0.0.1',
  `port` int(11) NOT NULL DEFAULT '21000',
  `name` varchar(29) DEFAULT 'No-Name Server',
  `channelType` int(11) DEFAULT '0',
  `serverID` int(11) DEFAULT '0',
  `population` int(11) DEFAULT '0',
  `maxPopulation` int(11) DEFAULT '0',
  PRIMARY KEY (`hostname`,`port`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servers`
--

LOCK TABLES `servers` WRITE;
/*!40000 ALTER TABLE `servers` DISABLE KEYS */;
INSERT INTO `servers` VALUES ('10.0.0.50',21001,'Barak\'s server',2,0,2,200);
/*!40000 ALTER TABLE `servers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_shop`
--

DROP TABLE IF EXISTS `user_shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_shop` (
  `shop_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(12) DEFAULT NULL,
  `card_id` int(11) DEFAULT '0',
  `card_premium_days` int(11) DEFAULT '0',
  `card_level` int(11) DEFAULT '0',
  `card_skill` int(11) DEFAULT '0',
  `code` bigint(20) DEFAULT '0',
  PRIMARY KEY (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_shop`
--

LOCK TABLES `user_shop` WRITE;
/*!40000 ALTER TABLE `user_shop` DISABLE KEYS */;
INSERT INTO `user_shop` VALUES (1,'barak2',1221,0,8,100200000,123),(2,'baraklevy',2108,365,0,0,123),(3,'barak2',3013,0,0,0,22),(4,'barak2',4703,6000,0,0,4),(5,'baraklevy',20100,0,0,0,1),(6,'baraklevy',20100,0,0,0,1),(7,'barak2',1112,5,7,0,12),(8,'baraklevy',1124,0,8,100200000,123),(9,'baraklevy',1124,0,8,100200000,123),(10,'baraklevy',1124,0,8,100200000,123),(11,'baraklevy',1124,0,8,100200000,123),(12,'baraklevy',1124,0,8,100200000,123),(13,'baraklevy',1124,0,8,100200000,123),(14,'baraklevy',1124,0,8,100200000,123),(15,'baraklevy',1124,0,8,100200000,123),(16,'baraklevy',1124,0,8,100200000,123),(17,'baraklevy',1124,0,8,100200000,123),(18,'baraklevy',1124,0,8,100200000,123),(19,'baraklevy',1124,0,8,100200000,123),(20,'baraklevy',1124,0,8,100200000,123),(21,'baraklevy',1124,0,8,100200000,123),(22,'baraklevy',1124,0,8,100200000,123),(23,'baraklevy',1124,0,8,100200000,123),(24,'baraklevy',1124,0,8,100200000,123),(25,'baraklevy',1124,0,8,100200000,123),(26,'baraklevy',1124,0,8,100200000,123),(27,'baraklevy',1124,0,8,100200000,123),(28,'baraklevy',1124,0,8,100200000,123),(29,'baraklevy',1124,0,8,100200000,123),(30,'baraklevy',1124,0,8,100200000,123),(31,'baraklevy',1124,0,8,100200000,123),(32,'baraklevy',1124,0,8,100200000,123),(33,'baraklevy',1124,0,8,100200000,123),(34,'baraklevy',1124,0,8,100200000,123),(35,'baraklevy',1124,0,8,100200000,123),(36,'baraklevy',1124,0,8,100200000,123),(37,'baraklevy',1124,0,8,100200000,123),(38,'baraklevy',1124,0,8,100200000,123),(39,'baraklevy',1124,0,8,100200000,123),(40,'baraklevy',1124,0,8,100200000,123),(41,'baraklevy',1124,0,8,100200000,123),(42,'baraklevy',1124,0,8,100200000,123),(43,'baraklevy',1124,0,8,100200000,123),(44,'baraklevy',1124,0,8,100200000,123),(45,'baraklevy',1124,0,8,100200000,123),(46,'baraklevy',1124,0,8,100200000,123),(47,'baraklevy',1124,0,8,100200000,123),(48,'baraklevy',1124,0,8,100200000,123),(49,'baraklevy',1124,0,8,100200000,123),(50,'baraklevy',1124,0,8,100200000,123),(51,'baraklevy',1124,0,8,100200000,123),(52,'baraklevy',1124,0,8,100200000,123),(53,'baraklevy',1124,0,8,100200000,123),(54,'baraklevy',1124,0,8,100200000,123),(55,'baraklevy',1124,0,8,100200000,123),(56,'baraklevy',1124,0,8,100200000,123),(58,'baraklevy0',1222,0,8,100200000,12345),(59,'baraklevy1',1222,0,8,100200000,12345),(60,'baraklevy2',1222,0,8,100200000,12345),(61,'baraklevy3',1222,0,8,100200000,12345),(62,'baraklevy4',1222,0,8,100200000,12345),(63,'baraklevy5',1222,0,8,100200000,12345),(64,'baraklevy6',1222,0,8,100200000,12345),(65,'baraklevy7',1222,0,8,100200000,12345),(66,'baraklevy8',1222,0,8,100200000,12345),(67,'baraklevy9',1222,0,8,100200000,12345),(68,'baraklevy10',1222,0,8,100200000,12345),(69,'baraklevy11',1222,0,8,100200000,12345),(70,'baraklevy12',1222,0,8,100200000,12345),(71,'baraklevy13',1222,0,8,100200000,12345),(72,'baraklevy14',1222,0,8,100200000,12345),(73,'baraklevy15',1222,0,8,100200000,12345),(74,'baraklevy16',1222,0,8,100200000,12345),(75,'baraklevy17',1222,0,8,100200000,12345),(76,'baraklevy18',1222,0,8,100200000,12345),(77,'baraklevy19',1222,0,8,100200000,12345),(78,'baraklevy20',1222,0,8,100200000,12345),(79,'baraklevy21',1222,0,8,100200000,12345),(80,'baraklevy22',1222,0,8,100200000,12345),(81,'baraklevy23',1222,0,8,100200000,12345),(82,'baraklevy24',1222,0,8,100200000,12345),(83,'baraklevy25',1222,0,8,100200000,12345),(84,'baraklevy26',1222,0,8,100200000,12345),(85,'baraklevy27',1222,0,8,100200000,12345),(86,'baraklevy28',1222,0,8,100200000,12345),(87,'baraklevy29',1222,0,8,100200000,12345),(88,'baraklevy30',1222,0,8,100200000,12345),(89,'baraklevy31',1222,0,8,100200000,12345),(90,'baraklevy32',1222,0,8,100200000,12345),(91,'baraklevy33',1222,0,8,100200000,12345),(92,'baraklevy34',1222,0,8,100200000,12345),(93,'baraklevy35',1222,0,8,100200000,12345),(94,'baraklevy36',1222,0,8,100200000,12345),(95,'baraklevy37',1222,0,8,100200000,12345),(96,'baraklevy38',1222,0,8,100200000,12345),(97,'baraklevy39',1222,0,8,100200000,12345),(98,'baraklevy40',1222,0,8,100200000,12345),(99,'baraklevy41',1222,0,8,100200000,12345),(100,'baraklevy42',1222,0,8,100200000,12345),(101,'baraklevy43',1222,0,8,100200000,12345),(102,'baraklevy44',1222,0,8,100200000,12345),(103,'baraklevy45',1222,0,8,100200000,12345),(104,'baraklevy46',1222,0,8,100200000,12345),(105,'baraklevy47',1222,0,8,100200000,12345),(106,'baraklevy48',1222,0,8,100200000,12345),(107,'baraklevy49',1222,0,8,100200000,12345),(108,'baraklevy',1223,0,8,10200000,12345),(109,'baraklevy',0,0,0,0,0),(110,'baraklevy',0,0,0,0,0),(111,'baraklevy',0,0,0,0,0),(112,'baraklevy',1224,0,8,100200000,1234),(113,'baraklevy',1234,365,8,100200000,1234),(114,'baraklevy',1244,365,7,100200000,123),(115,'baraklevy',1221,0,8,100200000,123),(116,'baraklevy',1133,365,6,100200000,1),(117,'baraklevy',1133,365,6,100200000,123),(118,'baraklevy',2000,365,0,0,11),(119,'baraklevy',2007,365,0,0,1),(120,'baraklevy',2007,365,0,0,12),(121,'baraklevy',2008,365,0,0,3),(122,'baraklevy',2012,365,0,0,3),(123,'baraklevy',2012,365,0,0,55),(124,'baraklevy',2012,365,0,0,44);
/*!40000 ALTER TABLE `user_shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(13) NOT NULL,
  `password` varchar(13) DEFAULT '123',
  `mainCharacter` int(11) DEFAULT '10',
  `playerLevel` int(11) DEFAULT '20',
  `usuableCharacterCount` int(11) DEFAULT '12',
  `ageRestriction` int(11) DEFAULT '1',
  `experience` bigint(20) DEFAULT '0',
  `code` bigint(20) DEFAULT '0',
  `avatarMoney` bigint(20) DEFAULT '0',
  `guildName` varchar(13) DEFAULT '',
  `guildDuty` varchar(13) DEFAULT NULL,
  `waterElements` int(11) DEFAULT '0',
  `fireElements` int(11) DEFAULT '0',
  `earthElements` int(11) DEFAULT '0',
  `windElements` int(11) DEFAULT '0',
  `isMale` tinyint(4) DEFAULT '0',
  `magicIndex` int(11) DEFAULT '-1',
  `weaponIndex` int(11) DEFAULT '-1',
  `accessoryIndex` int(11) DEFAULT '-1',
  `petIndex` int(11) DEFAULT '-1',
  `footIndex` int(11) DEFAULT '-1',
  `bodyIndex` int(11) DEFAULT '-1',
  `hand1Index` int(11) DEFAULT '-1',
  `hand2Index` int(11) DEFAULT '-1',
  `faceIndex` int(11) DEFAULT '-1',
  `hairIndex` int(11) DEFAULT '-1',
  `headIndex` int(11) DEFAULT '-1',
  `winCount` int(11) DEFAULT '0',
  `loseCount` int(11) DEFAULT '0',
  `koCount` int(11) DEFAULT '0',
  `downCount` int(11) DEFAULT '0',
  `cash` bigint(20) DEFAULT '0',
  `inventorySlots` int(11) DEFAULT '12',
  `scroll0` int(11) DEFAULT '0',
  `scroll1` int(11) DEFAULT '0',
  `scroll2` int(11) DEFAULT '0',
  `missionLevel` int(11) DEFAULT '1',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('barak123','123',10,20,12,1,0,0,0,'',NULL,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,12,0,0,0,1),('barak124','123',10,20,12,1,0,0,0,'',NULL,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,12,0,0,0,1),('barak2','123',10,17,12,1,1063631,2347,99999,'Guild3','Duty1',37,1258,13,14,1,-1,0,15,-1,-1,-1,-1,-1,-1,-1,-1,0,48,29,40,6000000,96,0,0,0,1),('barak3','123',10,17,12,1,1065252,800,0,NULL,NULL,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,5,5,26,6000000,96,0,0,0,1),('barak4','123',10,20,12,1,1064452,0,0,'',NULL,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,6000000,96,0,0,0,1),('baraklevy','123',20,17,12,1,1382156,925627999,0,'Guild3','Duty3',1000144,1000084,933375,1000123,1,1,20,22,15,-1,-1,-1,-1,-1,-1,-1,2,122,112,18,6000000,90,0,14,10,14),('baraklevya','123',10,20,12,1,0,0,0,'',NULL,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,12,0,0,0,1),('baraklevyaaa','123',10,20,12,1,0,0,0,'',NULL,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,12,0,0,0,1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-18 11:41:22
