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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(13) NOT NULL,
  `password` varchar(13) DEFAULT '123',
  `userType` int(11) DEFAULT '30',
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
  `isConnected` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('barak2','123',30,10,9,12,1,3907,10005206,99999,'Guild1','Duty1',3,210,0,0,0,-1,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,14,2,6000000,12,10,10,10,0),('barak3','123',30,10,20,12,1,0,0,0,'Guild2','Duty2',0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,1,5,0,12,0,0,0,NULL),('barak4','123',30,10,20,12,1,0,0,0,'',NULL,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,12,0,0,0,1),('baraklevy','123',10,10,9,12,1,5760,7660,0,'Guild3','Duty3',32,1015,14,15,0,-1,-1,0,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,13,0,12,0,0,0,1);
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

-- Dump completed on 2017-04-01 21:51:18
