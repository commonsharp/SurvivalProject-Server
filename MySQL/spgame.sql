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
INSERT INTO `cards` VALUES ('barak2',0,1111,0,8,0),('barak2',1,1112,5,7,0),('barak2',2,2900,100,-1,-1),('barak2',3,2150,365,0,0),('barak2',4,2016,6000,0,0),('barak2',5,2015,6000,0,0),('barak2',6,2016,6000,0,0),('barak2',7,2015,6000,0,0),('barak2',8,2017,40,0,0),('barak2',9,2004,6000,0,0),('barak2',10,2005,6000,0,0),('barak2',11,2016,6000,10,0),('barak2',12,2016,6000,10,0),('barak2',13,2900,365,0,0),('barak2',14,3013,0,0,0),('barak2',15,1234,30,0,0),('barak2',16,-1,-1,-1,-1),('barak2',17,-1,-1,-1,-1),('barak2',18,-1,-1,-1,-1),('barak2',19,-1,-1,-1,-1),('barak2',20,-1,-1,-1,-1),('barak2',21,-1,-1,-1,-1),('barak2',22,-1,-1,-1,-1),('barak2',23,-1,-1,-1,-1),('barak2',24,-1,-1,-1,-1),('barak2',25,-1,-1,-1,-1),('barak2',26,-1,-1,-1,-1),('barak2',27,-1,-1,-1,-1),('barak2',28,-1,-1,-1,-1),('barak2',29,-1,-1,-1,-1),('barak2',30,-1,-1,-1,-1),('barak2',31,-1,-1,-1,-1),('barak2',32,-1,-1,-1,-1),('barak2',33,-1,-1,-1,-1),('barak2',34,-1,-1,-1,-1),('barak2',35,-1,-1,-1,-1),('barak2',36,-1,-1,-1,-1),('barak2',37,-1,-1,-1,-1),('barak2',38,-1,-1,-1,-1),('barak2',39,-1,-1,-1,-1),('barak2',40,-1,-1,-1,-1),('barak2',41,-1,-1,-1,-1),('barak2',42,-1,-1,-1,-1),('barak2',43,-1,-1,-1,-1),('barak2',44,-1,-1,-1,-1),('barak2',45,-1,-1,-1,-1),('barak2',46,-1,-1,-1,-1),('barak2',47,-1,-1,-1,-1),('barak2',48,-1,-1,-1,-1),('barak2',49,-1,-1,-1,-1),('barak2',50,-1,-1,-1,-1),('barak2',51,-1,-1,-1,-1),('barak2',52,-1,-1,-1,-1),('barak2',53,-1,-1,-1,-1),('barak2',54,-1,-1,-1,-1),('barak2',55,-1,-1,-1,-1),('barak2',56,-1,-1,-1,-1),('barak2',57,-1,-1,-1,-1),('barak2',58,-1,-1,-1,-1),('barak2',59,-1,-1,-1,-1),('barak2',60,-1,-1,-1,-1),('barak2',61,-1,-1,-1,-1),('barak2',62,-1,-1,-1,-1),('barak2',63,-1,-1,-1,-1),('barak2',64,-1,-1,-1,-1),('barak2',65,-1,-1,-1,-1),('barak2',66,-1,-1,-1,-1),('barak2',67,-1,-1,-1,-1),('barak2',68,-1,-1,-1,-1),('barak2',69,-1,-1,-1,-1),('barak2',70,-1,-1,-1,-1),('barak2',71,-1,-1,-1,-1),('barak2',72,-1,-1,-1,-1),('barak2',73,-1,-1,-1,-1),('barak2',74,-1,-1,-1,-1),('barak2',75,-1,-1,-1,-1),('barak2',76,-1,-1,-1,-1),('barak2',77,-1,-1,-1,-1),('barak2',78,-1,-1,-1,-1),('barak2',79,-1,-1,-1,-1),('barak2',80,-1,-1,-1,-1),('barak2',81,-1,-1,-1,-1),('barak2',82,-1,-1,-1,-1),('barak2',83,-1,-1,-1,-1),('barak2',84,-1,-1,-1,-1),('barak2',85,-1,-1,-1,-1),('barak2',86,-1,-1,-1,-1),('barak2',87,-1,-1,-1,-1),('barak2',88,-1,-1,-1,-1),('barak2',89,-1,-1,-1,-1),('barak2',90,-1,-1,-1,-1),('barak2',91,-1,-1,-1,-1),('barak2',92,-1,-1,-1,-1),('barak2',93,-1,-1,-1,-1),('barak2',94,-1,-1,-1,-1),('barak2',95,-1,-1,-1,-1),('barak3',0,-1,-1,-1,-1),('barak3',1,-1,-1,-1,-1),('barak3',2,-1,-1,-1,-1),('barak3',3,-1,-1,-1,-1),('barak3',4,-1,-1,-1,-1),('barak3',5,-1,-1,-1,-1),('barak3',6,-1,-1,-1,-1),('barak3',7,-1,-1,-1,-1),('barak3',8,-1,-1,-1,-1),('barak3',9,-1,-1,-1,-1),('barak3',10,-1,-1,-1,-1),('barak3',11,-1,-1,-1,-1),('barak3',12,-1,-1,-1,-1),('barak3',13,-1,-1,-1,-1),('barak3',14,-1,-1,-1,-1),('barak3',15,-1,-1,-1,-1),('barak3',16,-1,-1,-1,-1),('barak3',17,-1,-1,-1,-1),('barak3',18,-1,-1,-1,-1),('barak3',19,-1,-1,-1,-1),('barak3',20,-1,-1,-1,-1),('barak3',21,-1,-1,-1,-1),('barak3',22,-1,-1,-1,-1),('barak3',23,-1,-1,-1,-1),('barak3',24,-1,-1,-1,-1),('barak3',25,-1,-1,-1,-1),('barak3',26,-1,-1,-1,-1),('barak3',27,-1,-1,-1,-1),('barak3',28,-1,-1,-1,-1),('barak3',29,-1,-1,-1,-1),('barak3',30,-1,-1,-1,-1),('barak3',31,-1,-1,-1,-1),('barak3',32,-1,-1,-1,-1),('barak3',33,-1,-1,-1,-1),('barak3',34,-1,-1,-1,-1),('barak3',35,-1,-1,-1,-1),('barak3',36,-1,-1,-1,-1),('barak3',37,-1,-1,-1,-1),('barak3',38,-1,-1,-1,-1),('barak3',39,-1,-1,-1,-1),('barak3',40,-1,-1,-1,-1),('barak3',41,-1,-1,-1,-1),('barak3',42,-1,-1,-1,-1),('barak3',43,-1,-1,-1,-1),('barak3',44,-1,-1,-1,-1),('barak3',45,-1,-1,-1,-1),('barak3',46,-1,-1,-1,-1),('barak3',47,-1,-1,-1,-1),('barak3',48,-1,-1,-1,-1),('barak3',49,-1,-1,-1,-1),('barak3',50,-1,-1,-1,-1),('barak3',51,-1,-1,-1,-1),('barak3',52,-1,-1,-1,-1),('barak3',53,-1,-1,-1,-1),('barak3',54,-1,-1,-1,-1),('barak3',55,-1,-1,-1,-1),('barak3',56,-1,-1,-1,-1),('barak3',57,-1,-1,-1,-1),('barak3',58,-1,-1,-1,-1),('barak3',59,-1,-1,-1,-1),('barak3',60,-1,-1,-1,-1),('barak3',61,-1,-1,-1,-1),('barak3',62,-1,-1,-1,-1),('barak3',63,-1,-1,-1,-1),('barak3',64,-1,-1,-1,-1),('barak3',65,-1,-1,-1,-1),('barak3',66,-1,-1,-1,-1),('barak3',67,-1,-1,-1,-1),('barak3',68,-1,-1,-1,-1),('barak3',69,-1,-1,-1,-1),('barak3',70,-1,-1,-1,-1),('barak3',71,-1,-1,-1,-1),('barak3',72,-1,-1,-1,-1),('barak3',73,-1,-1,-1,-1),('barak3',74,-1,-1,-1,-1),('barak3',75,-1,-1,-1,-1),('barak3',76,-1,-1,-1,-1),('barak3',77,-1,-1,-1,-1),('barak3',78,-1,-1,-1,-1),('barak3',79,-1,-1,-1,-1),('barak3',80,-1,-1,-1,-1),('barak3',81,-1,-1,-1,-1),('barak3',82,-1,-1,-1,-1),('barak3',83,-1,-1,-1,-1),('barak3',84,-1,-1,-1,-1),('barak3',85,-1,-1,-1,-1),('barak3',86,-1,-1,-1,-1),('barak3',87,-1,-1,-1,-1),('barak3',88,-1,-1,-1,-1),('barak3',89,-1,-1,-1,-1),('barak3',90,-1,-1,-1,-1),('barak3',91,-1,-1,-1,-1),('barak3',92,-1,-1,-1,-1),('barak3',93,-1,-1,-1,-1),('barak3',94,-1,-1,-1,-1),('barak3',95,-1,-1,-1,-1),('barak4',0,-1,-1,-1,-1),('barak4',1,-1,-1,-1,-1),('barak4',2,-1,-1,-1,-1),('barak4',3,-1,-1,-1,-1),('barak4',4,-1,-1,-1,-1),('barak4',5,-1,-1,-1,-1),('barak4',6,-1,-1,-1,-1),('barak4',7,-1,-1,-1,-1),('barak4',8,-1,-1,-1,-1),('barak4',9,-1,-1,-1,-1),('barak4',10,-1,-1,-1,-1),('barak4',11,-1,-1,-1,-1),('barak4',12,-1,-1,-1,-1),('barak4',13,-1,-1,-1,-1),('barak4',14,-1,-1,-1,-1),('barak4',15,-1,-1,-1,-1),('barak4',16,-1,-1,-1,-1),('barak4',17,-1,-1,-1,-1),('barak4',18,-1,-1,-1,-1),('barak4',19,-1,-1,-1,-1),('barak4',20,-1,-1,-1,-1),('barak4',21,-1,-1,-1,-1),('barak4',22,-1,-1,-1,-1),('barak4',23,-1,-1,-1,-1),('barak4',24,-1,-1,-1,-1),('barak4',25,-1,-1,-1,-1),('barak4',26,-1,-1,-1,-1),('barak4',27,-1,-1,-1,-1),('barak4',28,-1,-1,-1,-1),('barak4',29,-1,-1,-1,-1),('barak4',30,-1,-1,-1,-1),('barak4',31,-1,-1,-1,-1),('barak4',32,-1,-1,-1,-1),('barak4',33,-1,-1,-1,-1),('barak4',34,-1,-1,-1,-1),('barak4',35,-1,-1,-1,-1),('barak4',36,-1,-1,-1,-1),('barak4',37,-1,-1,-1,-1),('barak4',38,-1,-1,-1,-1),('barak4',39,-1,-1,-1,-1),('barak4',40,-1,-1,-1,-1),('barak4',41,-1,-1,-1,-1),('barak4',42,-1,-1,-1,-1),('barak4',43,-1,-1,-1,-1),('barak4',44,-1,-1,-1,-1),('barak4',45,-1,-1,-1,-1),('barak4',46,-1,-1,-1,-1),('barak4',47,-1,-1,-1,-1),('barak4',48,-1,-1,-1,-1),('barak4',49,-1,-1,-1,-1),('barak4',50,-1,-1,-1,-1),('barak4',51,-1,-1,-1,-1),('barak4',52,-1,-1,-1,-1),('barak4',53,-1,-1,-1,-1),('barak4',54,-1,-1,-1,-1),('barak4',55,-1,-1,-1,-1),('barak4',56,-1,-1,-1,-1),('barak4',57,-1,-1,-1,-1),('barak4',58,-1,-1,-1,-1),('barak4',59,-1,-1,-1,-1),('barak4',60,-1,-1,-1,-1),('barak4',61,-1,-1,-1,-1),('barak4',62,-1,-1,-1,-1),('barak4',63,-1,-1,-1,-1),('barak4',64,-1,-1,-1,-1),('barak4',65,-1,-1,-1,-1),('barak4',66,-1,-1,-1,-1),('barak4',67,-1,-1,-1,-1),('barak4',68,-1,-1,-1,-1),('barak4',69,-1,-1,-1,-1),('barak4',70,-1,-1,-1,-1),('barak4',71,-1,-1,-1,-1),('barak4',72,-1,-1,-1,-1),('barak4',73,-1,-1,-1,-1),('barak4',74,-1,-1,-1,-1),('barak4',75,-1,-1,-1,-1),('barak4',76,-1,-1,-1,-1),('barak4',77,-1,-1,-1,-1),('barak4',78,-1,-1,-1,-1),('barak4',79,-1,-1,-1,-1),('barak4',80,-1,-1,-1,-1),('barak4',81,-1,-1,-1,-1),('barak4',82,-1,-1,-1,-1),('barak4',83,-1,-1,-1,-1),('barak4',84,-1,-1,-1,-1),('barak4',85,-1,-1,-1,-1),('barak4',86,-1,-1,-1,-1),('barak4',87,-1,-1,-1,-1),('barak4',88,-1,-1,-1,-1),('barak4',89,-1,-1,-1,-1),('barak4',90,-1,-1,-1,-1),('barak4',91,-1,-1,-1,-1),('barak4',92,-1,-1,-1,-1),('barak4',93,-1,-1,-1,-1),('barak4',94,-1,-1,-1,-1),('barak4',95,-1,-1,-1,-1),('baraklevy',0,1223,0,8,10200000),('baraklevy',1,1321,0,1,0),('baraklevy',2,1121,0,8,100200000),('baraklevy',3,1124,0,8,100200000),('baraklevy',4,1224,0,8,100200000),('baraklevy',5,1221,0,8,100200000),('baraklevy',6,1222,0,8,100200000),('baraklevy',7,1224,0,3,100200000),('baraklevy',8,2000,365,0,0),('baraklevy',9,-1,-1,-1,-1),('baraklevy',10,-1,-1,-1,-1),('baraklevy',11,-1,-1,-1,-1),('baraklevy',12,-1,-1,-1,-1),('baraklevy',13,-1,-1,-1,-1),('baraklevy',14,-1,-1,-1,-1),('baraklevy',15,-1,-1,-1,-1),('baraklevy',16,-1,-1,-1,-1),('baraklevy',17,-1,-1,-1,-1),('baraklevy',18,-1,-1,-1,-1),('baraklevy',19,-1,-1,-1,-1),('baraklevy',20,-1,-1,-1,-1),('baraklevy',21,-1,-1,-1,-1),('baraklevy',22,-1,-1,-1,-1),('baraklevy',23,-1,-1,-1,-1),('baraklevy',24,-1,-1,-1,-1),('baraklevy',25,-1,-1,-1,-1),('baraklevy',26,-1,-1,-1,-1),('baraklevy',27,-1,-1,-1,-1),('baraklevy',28,-1,-1,-1,-1),('baraklevy',29,-1,-1,-1,-1),('baraklevy',30,-1,-1,-1,-1),('baraklevy',31,-1,-1,-1,-1),('baraklevy',32,-1,-1,-1,-1),('baraklevy',33,-1,-1,-1,-1),('baraklevy',34,-1,-1,-1,-1),('baraklevy',35,-1,-1,-1,-1),('baraklevy',36,-1,-1,-1,-1),('baraklevy',37,-1,-1,-1,-1),('baraklevy',38,-1,-1,-1,-1),('baraklevy',39,-1,-1,-1,-1),('baraklevy',40,-1,-1,-1,-1),('baraklevy',41,-1,-1,-1,-1),('baraklevy',42,-1,-1,-1,-1),('baraklevy',43,-1,-1,-1,-1),('baraklevy',44,-1,-1,-1,-1),('baraklevy',45,-1,-1,-1,-1),('baraklevy',46,-1,-1,-1,-1),('baraklevy',47,-1,-1,-1,-1),('baraklevy',48,-1,-1,-1,-1),('baraklevy',49,-1,-1,-1,-1),('baraklevy',50,-1,-1,-1,-1),('baraklevy',51,-1,-1,-1,-1),('baraklevy',52,-1,-1,-1,-1),('baraklevy',53,-1,-1,-1,-1),('baraklevy',54,-1,-1,-1,-1),('baraklevy',55,-1,-1,-1,-1),('baraklevy',56,-1,-1,-1,-1),('baraklevy',57,-1,-1,-1,-1),('baraklevy',58,-1,-1,-1,-1),('baraklevy',59,-1,-1,-1,-1),('baraklevy',60,-1,-1,-1,-1),('baraklevy',61,-1,-1,-1,-1),('baraklevy',62,-1,-1,-1,-1),('baraklevy',63,-1,-1,-1,-1),('baraklevy',64,-1,-1,-1,-1),('baraklevy',65,-1,-1,-1,-1),('baraklevy',66,-1,-1,-1,-1),('baraklevy',67,-1,-1,-1,-1),('baraklevy',68,-1,-1,-1,-1),('baraklevy',69,-1,-1,-1,-1),('baraklevy',70,-1,-1,-1,-1),('baraklevy',71,-1,-1,-1,-1),('baraklevy',72,-1,-1,-1,-1),('baraklevy',73,-1,-1,-1,-1),('baraklevy',74,-1,-1,-1,-1),('baraklevy',75,-1,-1,-1,-1),('baraklevy',76,-1,-1,-1,-1),('baraklevy',77,-1,-1,-1,-1),('baraklevy',78,-1,-1,-1,-1),('baraklevy',79,-1,-1,-1,-1),('baraklevy',80,-1,-1,-1,-1),('baraklevy',81,-1,-1,-1,-1),('baraklevy',82,-1,-1,-1,-1),('baraklevy',83,-1,-1,-1,-1),('baraklevy',84,-1,-1,-1,-1),('baraklevy',85,-1,-1,-1,-1),('baraklevy',86,-1,-1,-1,-1),('baraklevy',87,-1,-1,-1,-1),('baraklevy',88,-1,-1,-1,-1),('baraklevy',89,-1,-1,-1,-1),('baraklevy',90,-1,-1,-1,-1),('baraklevy',91,-1,-1,-1,-1),('baraklevy',92,-1,-1,-1,-1),('baraklevy',93,-1,-1,-1,-1),('baraklevy',94,-1,-1,-1,-1),('baraklevy',95,-1,-1,-1,-1);
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
INSERT INTO `friends` VALUES ('barak3','baraklevy'),('baraklevy','barak3'),('baraklevy','barak4');
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
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
INSERT INTO `guild_score` VALUES ('10.0.0.50',21001,'Guild1',50000),('10.0.0.50',21001,'Guild3',5000),('10.0.0.50',21001,'GuildNew',300);
/*!40000 ALTER TABLE `guild_score` ENABLE KEYS */;
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
INSERT INTO `users` VALUES ('barak2','123',10,17,12,1,1063231,10080424,99999,'Guild3','Duty1',37,256,13,14,1,-1,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,15,22,25,6000000,12,0,11,0,1),('barak3','123',10,30,12,1,0,0,0,NULL,NULL,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,3,18,6000000,12,0,0,0,1),('barak4','123',10,20,12,1,0,0,0,'',NULL,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,6000000,12,0,0,0,1),('baraklevy','123',10,17,12,1,1064352,967817813,0,'Guild3','Duty3',84,19581,69,80,1,1,2,7,-1,-1,-1,-1,-1,-1,-1,-1,0,17,35,30,6000000,12,0,0,0,2);
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

-- Dump completed on 2017-04-10 11:49:57
