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
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card` (
  `card_unique_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(12) DEFAULT NULL,
  `card_index` int(11) DEFAULT '-1',
  `card_id` int(11) DEFAULT '-1',
  `card_premium_days` int(11) DEFAULT '-1',
  `card_level` int(11) DEFAULT '-1',
  `card_skill` int(11) DEFAULT '-1',
  PRIMARY KEY (`card_unique_id`),
  KEY `fk_card_username_idx` (`username`),
  CONSTRAINT `fk_card_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1452 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card`
--

LOCK TABLES `card` WRITE;
/*!40000 ALTER TABLE `card` DISABLE KEYS */;
INSERT INTO `card` VALUES (193,'barak2',0,1111,0,8,0),(194,'barak2',1,1122,365,8,100200000),(195,'barak2',2,1121,791,8,100111000),(196,'barak2',3,2150,365,0,0),(197,'barak2',4,2016,6000,0,0),(198,'barak2',5,2015,6000,0,0),(199,'barak2',6,2016,6000,0,0),(200,'barak2',7,2015,6000,0,0),(201,'barak2',8,2017,40,0,0),(202,'barak2',9,2004,6000,0,0),(203,'barak2',10,2005,6000,0,0),(204,'barak2',11,2016,6000,10,0),(205,'barak2',12,2016,6000,10,0),(206,'barak2',13,2005,6000,0,0),(207,'barak2',14,1224,0,3,100200000),(208,'barak2',15,1234,30,0,0),(209,'barak2',16,1224,0,3,100200000),(210,'barak2',17,2009,6000,9,0),(211,'barak2',18,1224,0,3,100200000),(212,'barak2',19,1122,365,8,100200000),(213,'barak2',20,2005,6000,0,0),(214,'barak2',21,1224,0,3,100200000),(215,'barak2',22,2001,365,0,0),(216,'barak2',23,-1,-1,-1,-1),(217,'barak2',24,2005,6000,0,0),(218,'barak2',25,5001,6000,0,0),(219,'barak2',26,2005,6000,0,0),(220,'barak2',27,2004,6000,0,0),(221,'barak2',28,2018,6000,1,0),(222,'barak2',29,2019,6000,1,0),(223,'barak2',30,2020,6000,1,0),(224,'barak2',31,2009,6000,15,0),(225,'barak2',32,2013,6000,2,0),(226,'barak2',33,2009,6000,25,0),(227,'barak2',34,2014,6000,2,0),(228,'barak2',35,1234,365,8,100200000),(229,'barak2',36,1234,365,8,100200000),(230,'barak2',37,-1,-1,-1,-1),(231,'barak2',38,-1,-1,-1,-1),(232,'barak2',39,-1,-1,-1,-1),(233,'barak2',40,-1,-1,-1,-1),(234,'barak2',41,-1,-1,-1,-1),(235,'barak2',42,-1,-1,-1,-1),(236,'barak2',43,-1,-1,-1,-1),(237,'barak2',44,-1,-1,-1,-1),(238,'barak2',45,-1,-1,-1,-1),(239,'barak2',46,-1,-1,-1,-1),(240,'barak2',47,-1,-1,-1,-1),(241,'barak2',48,-1,-1,-1,-1),(242,'barak2',49,-1,-1,-1,-1),(243,'barak2',50,-1,-1,-1,-1),(244,'barak2',51,-1,-1,-1,-1),(245,'barak2',52,-1,-1,-1,-1),(246,'barak2',53,-1,-1,-1,-1),(247,'barak2',54,-1,-1,-1,-1),(248,'barak2',55,-1,-1,-1,-1),(249,'barak2',56,-1,-1,-1,-1),(250,'barak2',57,-1,-1,-1,-1),(251,'barak2',58,-1,-1,-1,-1),(252,'barak2',59,-1,-1,-1,-1),(253,'barak2',60,-1,-1,-1,-1),(254,'barak2',61,-1,-1,-1,-1),(255,'barak2',62,-1,-1,-1,-1),(256,'barak2',63,-1,-1,-1,-1),(257,'barak2',64,-1,-1,-1,-1),(258,'barak2',65,-1,-1,-1,-1),(259,'barak2',66,-1,-1,-1,-1),(260,'barak2',67,-1,-1,-1,-1),(261,'barak2',68,-1,-1,-1,-1),(262,'barak2',69,-1,-1,-1,-1),(263,'barak2',70,-1,-1,-1,-1),(264,'barak2',71,-1,-1,-1,-1),(265,'barak2',72,-1,-1,-1,-1),(266,'barak2',73,-1,-1,-1,-1),(267,'barak2',74,-1,-1,-1,-1),(268,'barak2',75,-1,-1,-1,-1),(269,'barak2',76,-1,-1,-1,-1),(270,'barak2',77,-1,-1,-1,-1),(271,'barak2',78,-1,-1,-1,-1),(272,'barak2',79,-1,-1,-1,-1),(273,'barak2',80,-1,-1,-1,-1),(274,'barak2',81,-1,-1,-1,-1),(275,'barak2',82,-1,-1,-1,-1),(276,'barak2',83,-1,-1,-1,-1),(277,'barak2',84,-1,-1,-1,-1),(278,'barak2',85,-1,-1,-1,-1),(279,'barak2',86,-1,-1,-1,-1),(280,'barak2',87,-1,-1,-1,-1),(281,'barak2',88,-1,-1,-1,-1),(282,'barak2',89,-1,-1,-1,-1),(283,'barak2',90,-1,-1,-1,-1),(284,'barak2',91,-1,-1,-1,-1),(285,'barak2',92,-1,-1,-1,-1),(286,'barak2',93,-1,-1,-1,-1),(287,'barak2',94,-1,-1,-1,-1),(288,'barak2',95,-1,-1,-1,-1),(289,'barak3',0,-1,-1,-1,-1),(290,'barak3',1,-1,-1,-1,-1),(291,'barak3',2,-1,-1,-1,-1),(292,'barak3',3,-1,-1,-1,-1),(293,'barak3',4,-1,-1,-1,-1),(294,'barak3',5,-1,-1,-1,-1),(295,'barak3',6,-1,-1,-1,-1),(296,'barak3',7,-1,-1,-1,-1),(297,'barak3',8,-1,-1,-1,-1),(298,'barak3',9,-1,-1,-1,-1),(299,'barak3',10,-1,-1,-1,-1),(300,'barak3',11,-1,-1,-1,-1),(301,'barak3',12,-1,-1,-1,-1),(302,'barak3',13,-1,-1,-1,-1),(303,'barak3',14,-1,-1,-1,-1),(304,'barak3',15,-1,-1,-1,-1),(305,'barak3',16,-1,-1,-1,-1),(306,'barak3',17,-1,-1,-1,-1),(307,'barak3',18,-1,-1,-1,-1),(308,'barak3',19,-1,-1,-1,-1),(309,'barak3',20,-1,-1,-1,-1),(310,'barak3',21,-1,-1,-1,-1),(311,'barak3',22,-1,-1,-1,-1),(312,'barak3',23,-1,-1,-1,-1),(313,'barak3',24,-1,-1,-1,-1),(314,'barak3',25,-1,-1,-1,-1),(315,'barak3',26,-1,-1,-1,-1),(316,'barak3',27,-1,-1,-1,-1),(317,'barak3',28,-1,-1,-1,-1),(318,'barak3',29,-1,-1,-1,-1),(319,'barak3',30,-1,-1,-1,-1),(320,'barak3',31,-1,-1,-1,-1),(321,'barak3',32,-1,-1,-1,-1),(322,'barak3',33,-1,-1,-1,-1),(323,'barak3',34,-1,-1,-1,-1),(324,'barak3',35,-1,-1,-1,-1),(325,'barak3',36,-1,-1,-1,-1),(326,'barak3',37,-1,-1,-1,-1),(327,'barak3',38,-1,-1,-1,-1),(328,'barak3',39,-1,-1,-1,-1),(329,'barak3',40,-1,-1,-1,-1),(330,'barak3',41,-1,-1,-1,-1),(331,'barak3',42,-1,-1,-1,-1),(332,'barak3',43,-1,-1,-1,-1),(333,'barak3',44,-1,-1,-1,-1),(334,'barak3',45,-1,-1,-1,-1),(335,'barak3',46,-1,-1,-1,-1),(336,'barak3',47,-1,-1,-1,-1),(337,'barak3',48,-1,-1,-1,-1),(338,'barak3',49,-1,-1,-1,-1),(339,'barak3',50,-1,-1,-1,-1),(340,'barak3',51,-1,-1,-1,-1),(341,'barak3',52,-1,-1,-1,-1),(342,'barak3',53,-1,-1,-1,-1),(343,'barak3',54,-1,-1,-1,-1),(344,'barak3',55,-1,-1,-1,-1),(345,'barak3',56,-1,-1,-1,-1),(346,'barak3',57,-1,-1,-1,-1),(347,'barak3',58,-1,-1,-1,-1),(348,'barak3',59,-1,-1,-1,-1),(349,'barak3',60,-1,-1,-1,-1),(350,'barak3',61,-1,-1,-1,-1),(351,'barak3',62,-1,-1,-1,-1),(352,'barak3',63,-1,-1,-1,-1),(353,'barak3',64,-1,-1,-1,-1),(354,'barak3',65,-1,-1,-1,-1),(355,'barak3',66,-1,-1,-1,-1),(356,'barak3',67,-1,-1,-1,-1),(357,'barak3',68,-1,-1,-1,-1),(358,'barak3',69,-1,-1,-1,-1),(359,'barak3',70,-1,-1,-1,-1),(360,'barak3',71,-1,-1,-1,-1),(361,'barak3',72,-1,-1,-1,-1),(362,'barak3',73,-1,-1,-1,-1),(363,'barak3',74,-1,-1,-1,-1),(364,'barak3',75,-1,-1,-1,-1),(365,'barak3',76,-1,-1,-1,-1),(366,'barak3',77,-1,-1,-1,-1),(367,'barak3',78,-1,-1,-1,-1),(368,'barak3',79,-1,-1,-1,-1),(369,'barak3',80,-1,-1,-1,-1),(370,'barak3',81,-1,-1,-1,-1),(371,'barak3',82,-1,-1,-1,-1),(372,'barak3',83,-1,-1,-1,-1),(373,'barak3',84,-1,-1,-1,-1),(374,'barak3',85,-1,-1,-1,-1),(375,'barak3',86,-1,-1,-1,-1),(376,'barak3',87,-1,-1,-1,-1),(377,'barak3',88,-1,-1,-1,-1),(378,'barak3',89,-1,-1,-1,-1),(379,'barak3',90,-1,-1,-1,-1),(380,'barak3',91,-1,-1,-1,-1),(381,'barak3',92,-1,-1,-1,-1),(382,'barak3',93,-1,-1,-1,-1),(383,'barak3',94,-1,-1,-1,-1),(384,'barak3',95,-1,-1,-1,-1),(385,'barak4',0,-1,-1,-1,-1),(386,'barak4',1,-1,-1,-1,-1),(387,'barak4',2,-1,-1,-1,-1),(388,'barak4',3,-1,-1,-1,-1),(389,'barak4',4,-1,-1,-1,-1),(390,'barak4',5,-1,-1,-1,-1),(391,'barak4',6,-1,-1,-1,-1),(392,'barak4',7,-1,-1,-1,-1),(393,'barak4',8,-1,-1,-1,-1),(394,'barak4',9,-1,-1,-1,-1),(395,'barak4',10,-1,-1,-1,-1),(396,'barak4',11,-1,-1,-1,-1),(397,'barak4',12,-1,-1,-1,-1),(398,'barak4',13,-1,-1,-1,-1),(399,'barak4',14,-1,-1,-1,-1),(400,'barak4',15,-1,-1,-1,-1),(401,'barak4',16,-1,-1,-1,-1),(402,'barak4',17,-1,-1,-1,-1),(403,'barak4',18,-1,-1,-1,-1),(404,'barak4',19,-1,-1,-1,-1),(405,'barak4',20,-1,-1,-1,-1),(406,'barak4',21,-1,-1,-1,-1),(407,'barak4',22,-1,-1,-1,-1),(408,'barak4',23,-1,-1,-1,-1),(409,'barak4',24,-1,-1,-1,-1),(410,'barak4',25,-1,-1,-1,-1),(411,'barak4',26,-1,-1,-1,-1),(412,'barak4',27,-1,-1,-1,-1),(413,'barak4',28,-1,-1,-1,-1),(414,'barak4',29,-1,-1,-1,-1),(415,'barak4',30,-1,-1,-1,-1),(416,'barak4',31,-1,-1,-1,-1),(417,'barak4',32,-1,-1,-1,-1),(418,'barak4',33,-1,-1,-1,-1),(419,'barak4',34,-1,-1,-1,-1),(420,'barak4',35,-1,-1,-1,-1),(421,'barak4',36,-1,-1,-1,-1),(422,'barak4',37,-1,-1,-1,-1),(423,'barak4',38,-1,-1,-1,-1),(424,'barak4',39,-1,-1,-1,-1),(425,'barak4',40,-1,-1,-1,-1),(426,'barak4',41,-1,-1,-1,-1),(427,'barak4',42,-1,-1,-1,-1),(428,'barak4',43,-1,-1,-1,-1),(429,'barak4',44,-1,-1,-1,-1),(430,'barak4',45,-1,-1,-1,-1),(431,'barak4',46,-1,-1,-1,-1),(432,'barak4',47,-1,-1,-1,-1),(433,'barak4',48,-1,-1,-1,-1),(434,'barak4',49,-1,-1,-1,-1),(435,'barak4',50,-1,-1,-1,-1),(436,'barak4',51,-1,-1,-1,-1),(437,'barak4',52,-1,-1,-1,-1),(438,'barak4',53,-1,-1,-1,-1),(439,'barak4',54,-1,-1,-1,-1),(440,'barak4',55,-1,-1,-1,-1),(441,'barak4',56,-1,-1,-1,-1),(442,'barak4',57,-1,-1,-1,-1),(443,'barak4',58,-1,-1,-1,-1),(444,'barak4',59,-1,-1,-1,-1),(445,'barak4',60,-1,-1,-1,-1),(446,'barak4',61,-1,-1,-1,-1),(447,'barak4',62,-1,-1,-1,-1),(448,'barak4',63,-1,-1,-1,-1),(449,'barak4',64,-1,-1,-1,-1),(450,'barak4',65,-1,-1,-1,-1),(451,'barak4',66,-1,-1,-1,-1),(452,'barak4',67,-1,-1,-1,-1),(453,'barak4',68,-1,-1,-1,-1),(454,'barak4',69,-1,-1,-1,-1),(455,'barak4',70,-1,-1,-1,-1),(456,'barak4',71,-1,-1,-1,-1),(457,'barak4',72,-1,-1,-1,-1),(458,'barak4',73,-1,-1,-1,-1),(459,'barak4',74,-1,-1,-1,-1),(460,'barak4',75,-1,-1,-1,-1),(461,'barak4',76,-1,-1,-1,-1),(462,'barak4',77,-1,-1,-1,-1),(463,'barak4',78,-1,-1,-1,-1),(464,'barak4',79,-1,-1,-1,-1),(465,'barak4',80,-1,-1,-1,-1),(466,'barak4',81,-1,-1,-1,-1),(467,'barak4',82,-1,-1,-1,-1),(468,'barak4',83,-1,-1,-1,-1),(469,'barak4',84,-1,-1,-1,-1),(470,'barak4',85,-1,-1,-1,-1),(471,'barak4',86,-1,-1,-1,-1),(472,'barak4',87,-1,-1,-1,-1),(473,'barak4',88,-1,-1,-1,-1),(474,'barak4',89,-1,-1,-1,-1),(475,'barak4',90,-1,-1,-1,-1),(476,'barak4',91,-1,-1,-1,-1),(477,'barak4',92,-1,-1,-1,-1),(478,'barak4',93,-1,-1,-1,-1),(479,'barak4',94,-1,-1,-1,-1),(480,'barak4',95,-1,-1,-1,-1),(481,'barak5',0,-1,-1,-1,-1),(482,'barak5',1,-1,-1,-1,-1),(483,'barak5',2,-1,-1,-1,-1),(484,'barak5',3,-1,-1,-1,-1),(485,'barak5',4,-1,-1,-1,-1),(486,'barak5',5,-1,-1,-1,-1),(487,'barak5',6,-1,-1,-1,-1),(488,'barak5',7,-1,-1,-1,-1),(489,'barak5',8,-1,-1,-1,-1),(490,'barak5',9,-1,-1,-1,-1),(491,'barak5',10,-1,-1,-1,-1),(492,'barak5',11,-1,-1,-1,-1),(493,'barak5',12,-1,-1,-1,-1),(494,'barak5',13,-1,-1,-1,-1),(495,'barak5',14,-1,-1,-1,-1),(496,'barak5',15,-1,-1,-1,-1),(497,'barak5',16,-1,-1,-1,-1),(498,'barak5',17,-1,-1,-1,-1),(499,'barak5',18,-1,-1,-1,-1),(500,'barak5',19,-1,-1,-1,-1),(501,'barak5',20,-1,-1,-1,-1),(502,'barak5',21,-1,-1,-1,-1),(503,'barak5',22,-1,-1,-1,-1),(504,'barak5',23,-1,-1,-1,-1),(505,'barak5',24,-1,-1,-1,-1),(506,'barak5',25,-1,-1,-1,-1),(507,'barak5',26,-1,-1,-1,-1),(508,'barak5',27,-1,-1,-1,-1),(509,'barak5',28,-1,-1,-1,-1),(510,'barak5',29,-1,-1,-1,-1),(511,'barak5',30,-1,-1,-1,-1),(512,'barak5',31,-1,-1,-1,-1),(513,'barak5',32,-1,-1,-1,-1),(514,'barak5',33,-1,-1,-1,-1),(515,'barak5',34,-1,-1,-1,-1),(516,'barak5',35,-1,-1,-1,-1),(517,'barak5',36,-1,-1,-1,-1),(518,'barak5',37,-1,-1,-1,-1),(519,'barak5',38,-1,-1,-1,-1),(520,'barak5',39,-1,-1,-1,-1),(521,'barak5',40,-1,-1,-1,-1),(522,'barak5',41,-1,-1,-1,-1),(523,'barak5',42,-1,-1,-1,-1),(524,'barak5',43,-1,-1,-1,-1),(525,'barak5',44,-1,-1,-1,-1),(526,'barak5',45,-1,-1,-1,-1),(527,'barak5',46,-1,-1,-1,-1),(528,'barak5',47,-1,-1,-1,-1),(529,'barak5',48,-1,-1,-1,-1),(530,'barak5',49,-1,-1,-1,-1),(531,'barak5',50,-1,-1,-1,-1),(532,'barak5',51,-1,-1,-1,-1),(533,'barak5',52,-1,-1,-1,-1),(534,'barak5',53,-1,-1,-1,-1),(535,'barak5',54,-1,-1,-1,-1),(536,'barak5',55,-1,-1,-1,-1),(537,'barak5',56,-1,-1,-1,-1),(538,'barak5',57,-1,-1,-1,-1),(539,'barak5',58,-1,-1,-1,-1),(540,'barak5',59,-1,-1,-1,-1),(541,'barak5',60,-1,-1,-1,-1),(542,'barak5',61,-1,-1,-1,-1),(543,'barak5',62,-1,-1,-1,-1),(544,'barak5',63,-1,-1,-1,-1),(545,'barak5',64,-1,-1,-1,-1),(546,'barak5',65,-1,-1,-1,-1),(547,'barak5',66,-1,-1,-1,-1),(548,'barak5',67,-1,-1,-1,-1),(549,'barak5',68,-1,-1,-1,-1),(550,'barak5',69,-1,-1,-1,-1),(551,'barak5',70,-1,-1,-1,-1),(552,'barak5',71,-1,-1,-1,-1),(553,'barak5',72,-1,-1,-1,-1),(554,'barak5',73,-1,-1,-1,-1),(555,'barak5',74,-1,-1,-1,-1),(556,'barak5',75,-1,-1,-1,-1),(557,'barak5',76,-1,-1,-1,-1),(558,'barak5',77,-1,-1,-1,-1),(559,'barak5',78,-1,-1,-1,-1),(560,'barak5',79,-1,-1,-1,-1),(561,'barak5',80,-1,-1,-1,-1),(562,'barak5',81,-1,-1,-1,-1),(563,'barak5',82,-1,-1,-1,-1),(564,'barak5',83,-1,-1,-1,-1),(565,'barak5',84,-1,-1,-1,-1),(566,'barak5',85,-1,-1,-1,-1),(567,'barak5',86,-1,-1,-1,-1),(568,'barak5',87,-1,-1,-1,-1),(569,'barak5',88,-1,-1,-1,-1),(570,'barak5',89,-1,-1,-1,-1),(571,'barak5',90,-1,-1,-1,-1),(572,'barak5',91,-1,-1,-1,-1),(573,'barak5',92,-1,-1,-1,-1),(574,'barak5',93,-1,-1,-1,-1),(575,'barak5',94,-1,-1,-1,-1),(576,'barak5',95,-1,-1,-1,-1),(577,'baraklevy',0,2003,730,0,0),(578,'baraklevy',1,1321,0,2,100406326),(579,'baraklevy',2,5001,6000,0,0),(580,'baraklevy',3,2900,365,0,0),(581,'baraklevy',4,2009,6000,14,0),(582,'baraklevy',5,3017,0,0,0),(583,'baraklevy',6,2005,6000,0,0),(584,'baraklevy',7,1224,0,8,35112351),(585,'baraklevy',8,5014,6000,0,0),(586,'baraklevy',9,2005,6000,0,0),(587,'baraklevy',10,5000,6000,0,0),(588,'baraklevy',11,2005,6000,0,0),(589,'baraklevy',12,1331,365,8,28346320),(590,'baraklevy',13,2002,365,0,0),(591,'baraklevy',14,2001,365,0,0),(592,'baraklevy',15,2900,365,0,0),(593,'baraklevy',16,2008,365,0,0),(594,'baraklevy',17,2506,6000,0,0),(595,'baraklevy',18,1123,0,3,0),(596,'baraklevy',19,2009,6000,2,0),(597,'baraklevy',20,1122,0,6,15136419),(598,'baraklevy',21,1322,365,8,100200000),(599,'baraklevy',22,1224,365,8,100112352),(600,'baraklevy',23,2009,6000,15,0),(601,'baraklevy',24,1223,0,1,0),(602,'baraklevy',25,1223,0,7,20149312),(603,'baraklevy',26,1222,0,0,100200364),(604,'baraklevy',27,2105,365,0,0),(605,'baraklevy',28,2503,6000,0,0),(606,'baraklevy',29,2012,365,0,0),(607,'baraklevy',30,1121,0,0,100169000),(608,'baraklevy',31,2101,365,0,0),(609,'baraklevy',32,2103,365,0,0),(610,'baraklevy',33,2103,365,0,0),(611,'baraklevy',34,3013,0,0,0),(612,'baraklevy',35,1244,365,8,100111371),(613,'baraklevy',36,2010,365,0,0),(614,'baraklevy',37,2015,6000,4,0),(615,'baraklevy',38,2016,6000,5,0),(616,'baraklevy',39,2500,6000,0,0),(617,'baraklevy',40,2015,6000,10,0),(618,'baraklevy',41,2016,6000,10,0),(619,'baraklevy',42,1321,0,0,100435435),(620,'baraklevy',43,2507,6000,0,0),(621,'baraklevy',44,2507,6000,0,0),(622,'baraklevy',45,2506,6000,0,0),(623,'baraklevy',46,2500,6000,0,0),(624,'baraklevy',47,2501,6000,0,0),(625,'baraklevy',48,2502,6000,0,0),(626,'baraklevy',49,2503,6000,0,0),(627,'baraklevy',50,2504,6000,0,0),(628,'baraklevy',51,2501,6000,0,0),(629,'baraklevy',52,2015,6000,25,0),(630,'baraklevy',53,2018,6000,1,0),(631,'baraklevy',54,2019,6000,1,0),(632,'baraklevy',55,1232,0,0,0),(633,'baraklevy',56,2005,6000,0,0),(634,'baraklevy',57,2005,6000,0,0),(635,'baraklevy',58,2004,6000,0,0),(636,'baraklevy',59,2018,6000,1,0),(637,'baraklevy',60,2019,6000,1,0),(638,'baraklevy',61,2020,6000,1,0),(639,'baraklevy',62,2005,6000,0,0),(640,'baraklevy',63,2004,6000,0,0),(641,'baraklevy',64,2015,6000,1,0),(642,'baraklevy',65,2016,6000,1,0),(643,'baraklevy',66,2009,6000,25,0),(644,'baraklevy',67,2014,6000,1,0),(645,'baraklevy',68,2009,6000,15,0),(646,'baraklevy',69,2005,6000,0,0),(647,'baraklevy',70,2016,6000,16,0),(648,'baraklevy',71,2015,6000,1,0),(649,'baraklevy',72,2016,6000,25,0),(650,'baraklevy',73,2018,6000,1,0),(651,'baraklevy',74,2019,6000,1,0),(652,'baraklevy',75,2020,6000,1,0),(653,'baraklevy',76,2015,6000,16,0),(654,'baraklevy',77,2016,6000,1,0),(655,'baraklevy',78,2501,6000,0,0),(656,'baraklevy',79,2016,6000,16,0),(657,'baraklevy',80,2015,6000,1,0),(658,'baraklevy',81,2015,6000,16,0),(659,'baraklevy',82,2016,6000,1,0),(660,'baraklevy',83,2501,6000,0,0),(661,'baraklevy',84,2015,6000,16,0),(662,'baraklevy',85,2016,6000,1,0),(663,'baraklevy',86,2005,6000,0,0),(664,'baraklevy',87,2004,6000,0,0),(665,'baraklevy',88,2018,6000,1,0),(666,'baraklevy',89,2019,6000,1,0),(667,'baraklevy',90,2020,6000,1,0),(668,'baraklevy',91,-1,-1,-1,-1),(669,'baraklevy',92,-1,-1,-1,-1),(670,'baraklevy',93,-1,-1,-1,-1),(671,'baraklevy',94,-1,-1,-1,-1),(672,'baraklevy',95,-1,-1,-1,-1);
/*!40000 ALTER TABLE `card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(12) DEFAULT NULL,
  `friend` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_username_idx` (`username`),
  KEY `fk_friend_idx` (`friend`),
  CONSTRAINT `fk_friend` FOREIGN KEY (`friend`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend`
--

LOCK TABLES `friend` WRITE;
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
INSERT INTO `friend` VALUES (1,'barak2','baraklevy'),(2,'barak3','baraklevy'),(3,'baraklevy','barak2'),(4,'baraklevy','barak4');
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;
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
  PRIMARY KEY (`gift_id`),
  KEY `fk_to_username_idx` (`to_username`),
  KEY `fk_from_username_idx` (`from_username`),
  CONSTRAINT `fk_from_username` FOREIGN KEY (`from_username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_to_username` FOREIGN KEY (`to_username`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
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
  `guild_id` int(11) NOT NULL AUTO_INCREMENT,
  `guild_name` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`guild_id`),
  KEY `guild_name_index` (`guild_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild`
--

LOCK TABLES `guild` WRITE;
/*!40000 ALTER TABLE `guild` DISABLE KEYS */;
INSERT INTO `guild` VALUES (3,'Guild2'),(1,'Guild3'),(2,'GuildNew');
/*!40000 ALTER TABLE `guild` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_member`
--

DROP TABLE IF EXISTS `guild_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `guild_name` varchar(12) DEFAULT NULL,
  `username` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_guild_member_username_idx` (`username`),
  KEY `fk_guild_member_guild_name_idx` (`guild_name`),
  CONSTRAINT `fk_guild_member_guild_name` FOREIGN KEY (`guild_name`) REFERENCES `guild` (`guild_name`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_guild_member_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_member`
--

LOCK TABLES `guild_member` WRITE;
/*!40000 ALTER TABLE `guild_member` DISABLE KEYS */;
INSERT INTO `guild_member` VALUES (1,'Guild3','barak2'),(2,'Guild3','baraklevy');
/*!40000 ALTER TABLE `guild_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_score`
--

DROP TABLE IF EXISTS `guild_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guild_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_id` int(11) DEFAULT NULL,
  `guild_name` varchar(12) DEFAULT NULL,
  `guild_score` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_guild_score_guild_name_idx` (`guild_name`),
  KEY `fk_guild_score_server_if_idx` (`server_id`),
  CONSTRAINT `fk_guild_score_guild_name` FOREIGN KEY (`guild_name`) REFERENCES `guild` (`guild_name`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_guild_score_server_if` FOREIGN KEY (`server_id`) REFERENCES `server` (`server_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_score`
--

LOCK TABLES `guild_score` WRITE;
/*!40000 ALTER TABLE `guild_score` DISABLE KEYS */;
INSERT INTO `guild_score` VALUES (1,3,'Guild3',50000),(10,2,'GuildNew',300),(11,2,'Guild2',500),(12,2,'Guild3',0);
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
  PRIMARY KEY (`memo_id`),
  KEY `fk_memo_from_username_idx` (`from_username`),
  KEY `fk_memo_to_username_idx` (`to_username`),
  CONSTRAINT `fk_memo_from_username` FOREIGN KEY (`from_username`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_memo_to_username` FOREIGN KEY (`to_username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memo`
--

LOCK TABLES `memo` WRITE;
/*!40000 ALTER TABLE `memo` DISABLE KEYS */;
/*!40000 ALTER TABLE `memo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `server`
--

DROP TABLE IF EXISTS `server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server` (
  `server_id` int(11) NOT NULL AUTO_INCREMENT,
  `hostname` varchar(100) DEFAULT '127.0.0.1',
  `port` int(11) DEFAULT '21000',
  `name` varchar(29) DEFAULT 'No-Name Server',
  `channel_type` int(11) DEFAULT '0',
  `server_index` int(11) DEFAULT '0',
  `population` int(11) DEFAULT '0',
  `max_population` int(11) DEFAULT '0',
  PRIMARY KEY (`server_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `server`
--

LOCK TABLES `server` WRITE;
/*!40000 ALTER TABLE `server` DISABLE KEYS */;
INSERT INTO `server` VALUES (1,'10.0.0.50',21001,'Newbies',0,0,0,200),(2,'10.0.0.50',21002,'Whatever',1,0,0,200),(3,'10.0.0.50',21003,'Lookin\' Good',2,0,0,200);
/*!40000 ALTER TABLE `server` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(13) NOT NULL,
  `password` varchar(12) DEFAULT '123',
  `main_character` int(11) DEFAULT '10',
  `level` int(11) DEFAULT '0',
  `usuable_character_count` int(11) DEFAULT '12',
  `age_Restriction` int(11) DEFAULT '1',
  `points` bigint(20) DEFAULT '0',
  `code` bigint(20) DEFAULT '0',
  `coins` bigint(20) DEFAULT '0',
  `guild_name` varchar(12) DEFAULT '',
  `guild_duty` varchar(12) DEFAULT NULL,
  `water` int(11) DEFAULT '0',
  `fire` int(11) DEFAULT '0',
  `earth` int(11) DEFAULT '0',
  `wind` int(11) DEFAULT '0',
  `is_male` tinyint(4) DEFAULT '0',
  `magic_index` int(11) DEFAULT '-1',
  `weapon_index` int(11) DEFAULT '-1',
  `accessory_index` int(11) DEFAULT '-1',
  `pet_index` int(11) DEFAULT '-1',
  `foot_index` int(11) DEFAULT '-1',
  `body_index` int(11) DEFAULT '-1',
  `hand1_index` int(11) DEFAULT '-1',
  `hand2_index` int(11) DEFAULT '-1',
  `face_index` int(11) DEFAULT '-1',
  `hair_index` int(11) DEFAULT '-1',
  `head_index` int(11) DEFAULT '-1',
  `wins` int(11) DEFAULT '0',
  `loses` int(11) DEFAULT '0',
  `kills` int(11) DEFAULT '0',
  `downs` int(11) DEFAULT '0',
  `cash` bigint(20) DEFAULT '0',
  `inventory_slots` int(11) DEFAULT '12',
  `scroll0` int(11) DEFAULT '0',
  `scroll1` int(11) DEFAULT '0',
  `scroll2` int(11) DEFAULT '0',
  `mission_level` int(11) DEFAULT '1',
  `server_id` int(11) DEFAULT NULL,
  `is_connected` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`username`),
  KEY `fk_server_id_idx` (`server_id`),
  KEY `fk_user_guild_name_idx` (`guild_name`),
  CONSTRAINT `fk_server_id` FOREIGN KEY (`server_id`) REFERENCES `server` (`server_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('barak2','123',90,17,12,1,1383321,299545,100117,'Guild3','Duty1',261,161,108,51,1,-1,0,15,25,-1,-1,-1,-1,-1,-1,-1,1,64,49,104,6000000,96,10,10,10,2,NULL,0),('barak3','123',10,17,12,1,1065368,916,0,'Guild3',NULL,0,0,0,0,1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,7,5,30,6000000,96,0,0,0,1,NULL,0),('barak4','123',10,17,12,1,1064452,0,0,'Guild3',NULL,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,2,0,1,6000000,96,0,0,0,1,NULL,0),('barak5','123',10,0,12,1,0,0,0,'Guild3',NULL,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,12,0,0,0,1,NULL,0),('baraklevy','123',30,18,12,1,1969400,9985461898,296,'Guild3',NULL,905284,905124,932204,988564,1,12,18,7,2,-1,-1,-1,-1,-1,-1,-1,4,172,206,24,6000000,90,14,14,15,302,NULL,0),('barakobkhi','123',90,1,12,1,0,1010,0,'Guild3',NULL,0,0,0,0,0,-1,0,1,2,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,12,0,0,0,1,NULL,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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
  PRIMARY KEY (`shop_id`),
  KEY `fk_user_shop_username_idx` (`username`),
  CONSTRAINT `fk_user_shop_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_shop`
--

LOCK TABLES `user_shop` WRITE;
/*!40000 ALTER TABLE `user_shop` DISABLE KEYS */;
INSERT INTO `user_shop` VALUES (1,'barak2',1221,0,8,100200000,123),(2,'baraklevy',2108,365,0,0,123),(3,'barak2',3013,0,0,0,22),(4,'barak2',4703,6000,0,0,4),(7,'barak2',1112,5,7,0,12),(8,'baraklevy',1124,0,8,100200000,123),(9,'baraklevy',1124,0,8,100200000,123),(10,'baraklevy',1124,0,8,100200000,123),(11,'baraklevy',1124,0,8,100200000,123),(12,'baraklevy',1124,0,8,100200000,123),(13,'baraklevy',1124,0,8,100200000,123),(14,'baraklevy',1124,0,8,100200000,123),(15,'baraklevy',1124,0,8,100200000,123),(16,'baraklevy',1124,0,8,100200000,123),(17,'baraklevy',1124,0,8,100200000,123),(18,'baraklevy',1124,0,8,100200000,123),(19,'baraklevy',1124,0,8,100200000,123),(20,'baraklevy',1124,0,8,100200000,123),(21,'baraklevy',1124,0,8,100200000,123),(22,'baraklevy',1124,0,8,100200000,123),(23,'baraklevy',1124,0,8,100200000,123),(24,'baraklevy',1124,0,8,100200000,123),(25,'baraklevy',1124,0,8,100200000,123),(26,'baraklevy',1124,0,8,100200000,123),(27,'baraklevy',1124,0,8,100200000,123),(28,'baraklevy',1124,0,8,100200000,123),(29,'baraklevy',1124,0,8,100200000,123),(30,'baraklevy',1124,0,8,100200000,123),(31,'baraklevy',1124,0,8,100200000,123),(32,'baraklevy',1124,0,8,100200000,123),(33,'baraklevy',1124,0,8,100200000,123),(34,'baraklevy',1124,0,8,100200000,123),(35,'baraklevy',1124,0,8,100200000,123),(36,'baraklevy',1124,0,8,100200000,123),(37,'baraklevy',1124,0,8,100200000,123),(38,'baraklevy',1124,0,8,100200000,123),(39,'baraklevy',1124,0,8,100200000,123),(40,'baraklevy',1124,0,8,100200000,123),(41,'baraklevy',1124,0,8,100200000,123),(42,'baraklevy',1124,0,8,100200000,123),(43,'baraklevy',1124,0,8,100200000,123),(44,'baraklevy',1124,0,8,100200000,123),(45,'baraklevy',1124,0,8,100200000,123),(46,'baraklevy',1124,0,8,100200000,123),(47,'baraklevy',1124,0,8,100200000,123),(48,'baraklevy',1124,0,8,100200000,123),(49,'baraklevy',1124,0,8,100200000,123),(50,'baraklevy',1124,0,8,100200000,123),(51,'baraklevy',1124,0,8,100200000,123),(52,'baraklevy',1124,0,8,100200000,123),(53,'baraklevy',1124,0,8,100200000,123),(54,'baraklevy',1124,0,8,100200000,123),(55,'baraklevy',1124,0,8,100200000,123),(56,'baraklevy',1124,0,8,100200000,123),(108,'baraklevy',1223,0,8,10200000,12345),(109,'baraklevy',0,0,0,0,0),(110,'baraklevy',0,0,0,0,0),(111,'baraklevy',0,0,0,0,0),(112,'baraklevy',1224,0,8,100200000,1234),(115,'baraklevy',1221,0,8,100200000,123),(116,'baraklevy',1133,365,6,100200000,1),(117,'baraklevy',1133,365,6,100200000,123),(118,'baraklevy',2000,365,0,0,11),(119,'baraklevy',2007,365,0,0,1),(120,'baraklevy',2007,365,0,0,12),(121,'baraklevy',2008,365,0,0,3),(122,'baraklevy',2012,365,0,0,3),(123,'baraklevy',2012,365,0,0,55),(124,'baraklevy',2012,365,0,0,44),(126,'baraklevy',1122,365,8,100138201,1000),(127,'baraklevy',2021,30,0,0,1000),(128,'baraklevy',2005,6000,0,0,123),(129,'baraklevy',3010,0,0,0,156),(130,'baraklevy',1214,0,0,0,123),(131,'baraklevy',1214,0,0,0,123),(132,'baraklevy',1214,0,0,0,123);
/*!40000 ALTER TABLE `user_shop` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-30 17:43:19
