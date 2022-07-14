-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: project
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `candidates`
--

DROP TABLE IF EXISTS `candidates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidates` (
  `name` varchar(45) NOT NULL,
  `bio` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidates`
--

LOCK TABLES `candidates` WRITE;
/*!40000 ALTER TABLE `candidates` DISABLE KEYS */;
INSERT INTO `candidates` VALUES ('Dillon','ECE Major Looking For Cheap Beer, Vote For Me');
/*!40000 ALTER TABLE `candidates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `election_precincts`
--

DROP TABLE IF EXISTS `election_precincts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `election_precincts` (
  `election_id` int NOT NULL,
  `precinct_id` int NOT NULL,
  `inc` int NOT NULL AUTO_INCREMENT,
  `ballot_active` int NOT NULL,
  PRIMARY KEY (`inc`),
  UNIQUE KEY `inc_UNIQUE` (`inc`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `election_precincts`
--

LOCK TABLES `election_precincts` WRITE;
/*!40000 ALTER TABLE `election_precincts` DISABLE KEYS */;
INSERT INTO `election_precincts` VALUES (0,1,1,0),(0,2,2,0),(0,3,3,0),(4,1,4,0),(4,2,5,0),(4,3,6,0),(4,4,7,0),(4,6,8,0),(5,1,9,0),(5,2,10,0),(5,3,11,0),(5,6,12,0),(6,2,13,0),(6,3,14,0),(6,5,15,0),(6,6,16,0),(7,1,17,0),(7,3,18,0),(7,4,19,0),(7,6,20,0),(8,1,21,0),(8,2,22,0),(8,3,23,0),(8,4,24,0),(8,5,25,0),(9,3,26,0),(9,4,27,0),(9,5,28,0),(9,6,29,0),(10,2,30,0),(10,4,31,0),(10,5,32,0),(10,6,33,0),(10,7,34,0),(11,1,35,0),(11,2,36,0),(11,4,37,0),(11,7,38,0),(11,8,39,0),(12,1,40,1),(12,2,41,0),(12,4,42,0),(12,5,43,0),(12,6,44,0),(12,8,45,0),(12,10,46,0),(13,1,47,0),(13,2,48,0),(13,3,49,0),(13,7,50,0),(13,8,51,0),(13,9,52,0),(13,10,53,0),(14,1,54,0),(14,2,55,0),(14,3,56,0),(14,6,57,0),(14,7,58,0),(14,8,59,0),(14,11,60,0);
/*!40000 ALTER TABLE `election_precincts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `elections`
--

DROP TABLE IF EXISTS `elections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `elections` (
  `election_id` int NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `polling_date` varchar(45) DEFAULT NULL,
  `start_time` varchar(45) DEFAULT NULL,
  `end_time` varchar(45) DEFAULT NULL,
  `complete` int NOT NULL,
  `in_progress` int NOT NULL,
  PRIMARY KEY (`election_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `elections`
--

LOCK TABLES `elections` WRITE;
/*!40000 ALTER TABLE `elections` DISABLE KEYS */;
INSERT INTO `elections` VALUES (0,'boob','2022-04-05','08:06','16:20',0,0),(1,'boob2','2022-04-05','10:26','15:19',0,0),(2,'Fake news','2022-04-15','04:11','07:09',0,0),(3,'Fake news','2022-04-15','04:11','07:09',0,0),(4,'Test Test','2022-04-18','02:05','16:05',0,0),(5,'Test Test Again','2022-04-20','04:20','16:20',0,0),(6,'Blaze it','2022-04-20','04:20','16:20',0,0),(7,'Gang','2022-04-18','02:21','04:21',0,0),(8,'I\'m Doing 1 More Test','2022-04-18','02:40','05:40',0,0),(9,'Last Test I Promise','2022-04-21','03:50','12:50',0,0),(10,'Test Demo','2022-04-20','16:16','19:16',0,0),(11,'Hello','2022-04-18','15:19','17:22',0,0),(12,'General Election','2022-04-20','04:20','16:20',0,0),(13,'Test Demo','2022-04-22','19:49','21:49',0,0),(14,'General','2022-04-19','21:12','22:12',0,0);
/*!40000 ALTER TABLE `elections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_log`
--

DROP TABLE IF EXISTS `login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_log` (
  `inc` int NOT NULL AUTO_INCREMENT,
  `login_ID` int NOT NULL,
  `login_success` int DEFAULT NULL,
  `login_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`inc`,`login_ID`),
  UNIQUE KEY `inc_UNIQUE` (`inc`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_log`
--

LOCK TABLES `login_log` WRITE;
/*!40000 ALTER TABLE `login_log` DISABLE KEYS */;
INSERT INTO `login_log` VALUES (1,10,0,'2022-03-28 02:47:31'),(2,10,0,'2022-03-28 02:57:22'),(3,2829,0,'2022-03-28 02:57:47'),(4,2829,0,'2022-03-28 02:59:27'),(5,1358,0,'2022-03-28 03:00:45'),(6,1358,0,'2022-03-28 03:01:46'),(7,1358,1,'2022-03-28 03:03:16'),(8,1358,0,'2022-03-28 03:05:05'),(9,1358,0,'2022-03-28 03:05:19'),(10,1358,0,'2022-03-28 03:06:57'),(11,1358,0,'2022-03-28 03:08:00'),(12,1358,0,'2022-03-28 03:08:42'),(13,1358,0,'2022-03-28 03:09:49'),(14,1358,0,'2022-03-28 03:10:17'),(15,1358,0,'2022-03-28 03:10:26'),(16,1358,0,'2022-03-28 03:10:37'),(17,1358,0,'2022-03-28 03:11:28'),(18,1358,1,'2022-03-28 03:12:20'),(19,1358,1,'2022-03-28 03:44:25'),(20,1358,1,'2022-03-28 16:09:10'),(21,1358,1,'2022-03-28 16:26:22'),(23,1358,1,'2022-03-28 17:39:46'),(24,1358,1,'2022-03-28 17:42:09'),(25,1358,0,'2022-03-28 18:47:28'),(26,1358,0,'2022-03-28 18:47:51'),(27,1358,1,'2022-03-28 18:53:18'),(28,1358,1,'2022-03-28 18:55:11'),(29,1358,1,'2022-03-28 19:14:22'),(30,1358,1,'2022-03-28 19:16:00'),(31,1358,1,'2022-03-28 19:20:44'),(32,1358,1,'2022-03-28 19:22:33'),(33,1358,1,'2022-03-28 19:25:55'),(34,1358,1,'2022-03-28 19:50:27'),(35,1358,1,'2022-03-28 20:03:36'),(36,1358,1,'2022-03-28 20:06:53'),(37,1358,1,'2022-03-28 20:16:08'),(38,1358,1,'2022-03-28 20:25:14'),(39,1358,1,'2022-03-28 20:27:32'),(40,1358,1,'2022-03-28 20:32:19'),(41,1358,1,'2022-03-28 20:40:35'),(42,1358,1,'2022-03-28 20:54:57'),(43,1358,1,'2022-03-28 21:07:54'),(44,1358,1,'2022-03-28 21:10:25'),(45,1277,1,'2022-04-04 21:59:30'),(46,9468,1,'2022-04-11 03:03:09'),(47,9468,1,'2022-04-11 15:27:02'),(48,9468,1,'2022-04-11 15:30:50'),(49,9468,1,'2022-04-11 16:38:31'),(50,9468,1,'2022-04-15 18:41:33'),(51,9468,1,'2022-04-16 00:24:24'),(52,9468,1,'2022-04-16 00:37:27'),(53,9468,1,'2022-04-18 05:00:31'),(54,9468,1,'2022-04-18 05:05:14'),(55,9468,1,'2022-04-18 05:12:29'),(56,9468,1,'2022-04-18 05:14:04'),(57,9468,1,'2022-04-18 05:21:25'),(58,9468,1,'2022-04-18 05:39:53'),(59,9468,1,'2022-04-18 05:50:20'),(60,9468,1,'2022-04-18 14:46:35'),(61,9468,1,'2022-04-18 14:49:11'),(62,9468,1,'2022-04-18 14:52:30'),(63,9468,1,'2022-04-18 16:56:44'),(64,9468,1,'2022-04-18 16:59:25'),(65,9468,1,'2022-04-18 17:13:28'),(66,9468,1,'2022-04-18 17:17:21'),(67,9468,1,'2022-04-18 18:14:10'),(68,9468,1,'2022-04-18 18:18:52'),(69,9468,0,'2022-04-18 18:29:41'),(70,9468,1,'2022-04-18 18:29:59'),(71,9468,1,'2022-04-19 13:17:55'),(72,9468,1,'2022-04-19 14:10:05'),(73,9468,1,'2022-04-19 14:36:07'),(74,9468,1,'2022-04-19 15:23:25'),(75,9468,1,'2022-04-19 15:25:46'),(76,9468,1,'2022-04-19 15:29:05'),(77,9468,1,'2022-04-19 15:30:59'),(78,9468,1,'2022-04-19 21:43:25'),(79,9468,1,'2022-04-19 21:59:58'),(80,6950,1,'2022-04-19 22:02:33'),(81,9468,1,'2022-04-19 22:03:08'),(82,9468,1,'2022-04-25 15:38:38'),(83,9468,1,'2022-04-25 16:10:43'),(84,9468,0,'2022-04-25 16:56:27'),(85,9468,1,'2022-04-25 16:57:12'),(86,9468,1,'2022-04-25 17:40:22'),(87,9468,1,'2022-04-25 22:07:52'),(88,9468,1,'2022-04-26 00:06:20'),(89,9468,1,'2022-04-26 00:14:13'),(90,9468,1,'2022-04-26 00:50:22'),(91,9468,1,'2022-04-26 01:22:38'),(92,9468,1,'2022-04-26 01:24:50'),(93,9468,1,'2022-04-26 01:28:35'),(94,9468,1,'2022-04-26 02:30:59'),(95,9468,1,'2022-04-26 02:31:57'),(96,9468,1,'2022-04-26 02:56:59'),(97,9468,1,'2022-04-26 03:03:34'),(98,9468,1,'2022-04-26 03:12:57'),(99,9468,1,'2022-04-26 03:41:00'),(100,9468,1,'2022-04-26 04:18:37'),(101,9468,1,'2022-04-26 04:22:08'),(102,9468,1,'2022-04-26 04:28:39'),(103,9468,1,'2022-04-26 04:31:19'),(104,9468,1,'2022-04-26 04:33:52'),(105,9468,1,'2022-04-26 04:36:23'),(106,9468,1,'2022-04-26 05:02:43'),(107,9468,1,'2022-04-26 05:34:11'),(108,9468,1,'2022-04-26 05:36:21'),(109,9468,1,'2022-04-26 05:57:43'),(110,9468,1,'2022-04-26 06:07:04'),(111,9468,1,'2022-05-01 19:59:54'),(112,9468,1,'2022-05-01 20:55:36'),(113,9468,1,'2022-05-01 22:04:18'),(114,9468,1,'2022-05-01 22:34:31'),(115,9468,1,'2022-05-01 23:56:43'),(116,9468,1,'2022-05-02 00:29:51'),(117,9468,1,'2022-05-02 01:10:36'),(118,2962,1,'2022-05-02 01:17:51'),(119,1789,1,'2022-05-02 01:20:05'),(120,1789,1,'2022-05-02 01:40:52'),(121,1789,1,'2022-05-02 01:41:37'),(122,1789,1,'2022-05-02 01:43:10'),(123,1789,1,'2022-05-02 01:43:56'),(124,1789,1,'2022-05-02 01:56:47'),(125,9468,1,'2022-05-02 02:05:27'),(126,1789,1,'2022-05-02 02:27:53'),(127,1789,1,'2022-05-02 02:35:36'),(128,1789,1,'2022-05-02 02:37:31'),(129,1789,1,'2022-05-02 02:40:10'),(130,9468,1,'2022-05-02 02:41:42'),(131,1789,1,'2022-05-02 02:43:52'),(132,1789,1,'2022-05-02 02:55:32'),(133,1789,1,'2022-05-02 04:52:30'),(134,1789,1,'2022-05-02 04:55:17'),(135,1789,1,'2022-05-02 04:57:44'),(136,1789,1,'2022-05-02 05:01:20'),(137,1789,1,'2022-05-02 05:04:23'),(138,1789,1,'2022-05-02 05:06:29'),(139,1789,1,'2022-05-02 05:10:21'),(140,1789,1,'2022-05-02 05:12:22'),(141,1789,1,'2022-05-02 05:15:54'),(142,1780,1,'2022-05-02 05:18:52');
/*!40000 ALTER TABLE `login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `polling_managers`
--

DROP TABLE IF EXISTS `polling_managers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `polling_managers` (
  `voterID` int NOT NULL,
  `precinct_id` int NOT NULL,
  PRIMARY KEY (`voterID`),
  UNIQUE KEY `voterID_UNIQUE` (`voterID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `polling_managers`
--

LOCK TABLES `polling_managers` WRITE;
/*!40000 ALTER TABLE `polling_managers` DISABLE KEYS */;
INSERT INTO `polling_managers` VALUES (1789,1),(1999,1),(2000,2);
/*!40000 ALTER TABLE `polling_managers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `precincts`
--

DROP TABLE IF EXISTS `precincts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `precincts` (
  `precinct_id` int NOT NULL AUTO_INCREMENT,
  `location` varchar(45) NOT NULL,
  `pooling_manager_id` varchar(100) DEFAULT NULL,
  `state_election_office_email` varchar(45) DEFAULT NULL,
  `zipcode` int NOT NULL,
  `zip_4_start` int NOT NULL,
  `zip_4_end` int NOT NULL,
  PRIMARY KEY (`precinct_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `precincts`
--

LOCK TABLES `precincts` WRITE;
/*!40000 ALTER TABLE `precincts` DISABLE KEYS */;
INSERT INTO `precincts` VALUES (1,'IA','1789,1999,','x@x.com',52240,0,3500),(2,'IA','2000,','y@y.com',52240,3501,9999),(3,'MI','None','z@z.com',66678,6000,9999),(4,'IA','None','k@i.com',52245,0,4999),(5,'IA','None','k@i.com',52245,0,4999),(6,'NY','None','k@i.com',67679,0,9999),(7,'Iowa City','None','k@i.com',55555,0,4999),(8,'New IC','None','k@i.com',55555,5000,6500),(9,'NY','None','k@i.com',55555,6501,7999),(10,'New IC','None','k@i.com',50000,0,3000),(11,'Maclean Hall','None','k@i.com',90000,1000,5000);
/*!40000 ALTER TABLE `precincts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `race_precincts`
--

DROP TABLE IF EXISTS `race_precincts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `race_precincts` (
  `inc` int NOT NULL AUTO_INCREMENT,
  `race_id` int NOT NULL,
  `precinct_id` int NOT NULL,
  PRIMARY KEY (`inc`),
  UNIQUE KEY `inc_UNIQUE` (`inc`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `race_precincts`
--

LOCK TABLES `race_precincts` WRITE;
/*!40000 ALTER TABLE `race_precincts` DISABLE KEYS */;
INSERT INTO `race_precincts` VALUES (2,0,1),(3,0,3),(4,1,1),(5,1,3),(6,2,2),(7,2,3),(8,2,4),(9,3,4),(10,3,5),(11,4,3),(12,4,4),(13,4,5),(14,5,2),(15,5,4),(16,5,5),(17,6,2),(18,6,4),(19,6,7),(20,7,1),(21,7,2),(22,7,4),(23,7,5),(24,8,4),(25,8,5),(26,8,6),(27,8,8),(28,9,1),(29,9,2),(30,9,10),(31,10,3),(32,10,7),(33,10,8),(34,11,1),(35,11,2),(36,11,3),(37,11,7),(38,11,8),(39,11,9),(40,11,10),(41,12,1),(42,12,2),(43,12,3),(44,12,7);
/*!40000 ALTER TABLE `race_precincts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `races`
--

DROP TABLE IF EXISTS `races`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `races` (
  `race_id` int NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `term` varchar(45) DEFAULT NULL,
  `cand1` varchar(45) DEFAULT NULL,
  `cand2` varchar(45) DEFAULT NULL,
  `cand3` varchar(45) DEFAULT NULL,
  `cand4` varchar(45) DEFAULT NULL,
  `cand5` varchar(45) DEFAULT NULL,
  `election_id` int NOT NULL,
  PRIMARY KEY (`race_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `races`
--

LOCK TABLES `races` WRITE;
/*!40000 ALTER TABLE `races` DISABLE KEYS */;
INSERT INTO `races` VALUES (0,'ECE Goat','2022-2100','John Doe','Yolo','Joe Biron',NULL,NULL,7),(1,'ECE Goat','2022-2100','John Doe','Yolo','Joe Biron',NULL,NULL,7),(2,'Nope','2022-2023','Nope','Yup','Maybe',NULL,NULL,8),(3,'Iowa','2022-2100','Herky','Nope','Fun Guy',NULL,NULL,9),(4,'New One','2022-2023','Herky Again','Whats','Up yo',NULL,NULL,9),(5,'Senate','2022-2023','John','Smith','Nemo',NULL,NULL,10),(6,'Test','2022-2100','Joe','Smith','Nope',NULL,NULL,11),(7,'Gov','2022-2025','John Doe','Raman','Junnan','Team 1',NULL,12),(8,'Senate','2022-2024','Dillon','Mike',NULL,NULL,NULL,12),(9,'Senate','2022-2024','Connor','Nihaal','Dillon',NULL,NULL,12),(10,'Govenor','2022-2024','Connor','Dillon','Nihaal','Mike',NULL,13),(11,'Assistant','2022-2025','John','Doe','Cat','Dog',NULL,13),(12,'Gov','2022-2024','John','Doe','Mike',NULL,NULL,14);
/*!40000 ALTER TABLE `races` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `voterID` int NOT NULL,
  `password` varchar(64) NOT NULL,
  `user_type` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `age` int NOT NULL,
  `address` varchar(45) NOT NULL,
  `address2` varchar(45) DEFAULT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(45) NOT NULL,
  `zipcode` varchar(10) NOT NULL,
  `driver_license_num` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `pwd` varchar(45) NOT NULL,
  `verified` tinyint NOT NULL DEFAULT '0',
  `access_code` int DEFAULT NULL,
  `valid_code` varchar(45) DEFAULT NULL,
  `incorrect_attempts` int(10) unsigned zerofill NOT NULL,
  `last_login` timestamp NOT NULL,
  PRIMARY KEY (`voterID`),
  UNIQUE KEY `username_UNIQUE` (`voterID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table to hold all users \n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1277,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','admin','Mike','Dobroski',69,'x','','x','IA','52240-2000','999999999','mdobroskii@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-04 21:59:30'),(1777,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','voter','Jack','Clause',20,'666 Newtown Ln',NULL,'North Liberty','IA','52240-2100','111111111','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-19 13:17:55'),(1780,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','voter','Jack','Clause',20,'666 Newtown Ln',NULL,'North Liberty','IA','52240-2005','111111111','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-05-02 05:18:52'),(1789,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','pm','Jack','Clause',20,'666 Newtown Ln',NULL,'North Liberty','IA','52240-2002','111111111','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-05-02 05:15:54'),(1999,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','pm','Dan','TheMan',18,'919 Yolo St',NULL,'North Liberty','IA','52240-2003','111111112','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-19 13:17:55'),(2000,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','pm','Yo','Gotti',25,'49 SixtyNine St',NULL,'Iowa City','IA','52240-2005','000000000','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-19 13:17:55'),(2111,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','pm','Ra','Man',55,'616 Grove St',NULL,'Coralville','IA','52240-2009','909090909','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-19 13:17:55'),(2962,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','voter','Dillon','Africa',33,'919 Aspen Court','','Iowa City','IA','52240-2220','101010101','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-05-02 01:17:51'),(3445,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','admin','Nihaal','Gill',69,'x','','x','IA','52240-2300','999999999','emailnihaal@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-04 21:50:43'),(6677,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','voter','Jack','Clause',20,'666 Newtown Ln',NULL,'North Liberty','IA','52240-2400','111111111','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-19 13:17:55'),(6950,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','voter','Dillon','Africa',25,'919 Aspen Court','','Iowa City','IA','52240-2500','989708909','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-19 22:02:33'),(7694,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','pm','Jack','Clause',20,'666 Newtown Ln',NULL,'North Liberty','IA','52241-1000','111111111','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-19 13:17:55'),(8697,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','voter','Jack','Clause',20,'666 Newtown Ln',NULL,'North Liberty','IA','52240-5690','111111111','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-19 13:17:55'),(9019,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','voter','Jack','Clause',20,'666 Newtown Ln',NULL,'North Liberty','IA','52240-3002','111111111','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-19 13:17:55'),(9357,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','admin','Connor','Hoeger',69,'x','','x','IA','52240-7000','999999999','cmhoeger@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-04 21:41:20'),(9468,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','admin','Dillon','Africa',69,'229 S Dubuque St, 337','337','Iowa City','IA','52240-9000','999999999','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-05-02 02:41:42'),(9671,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','pm','Jack','Clause',20,'666 Newtown Ln',NULL,'North Liberty','IA','52240-8002','111111111','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-19 13:17:55'),(9803,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','pm','Jack','Clue',20,'666 Newtown Ln',NULL,'North Liberty','IA','52240-9999','111111111','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-19 13:17:55');
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

-- Dump completed on 2022-05-02  0:20:12
