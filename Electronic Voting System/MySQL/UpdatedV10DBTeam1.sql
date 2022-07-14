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
  `race1_id` int DEFAULT NULL,
  `race2_id` int DEFAULT NULL,
  `race3_id` int DEFAULT NULL,
  `race4_id` int DEFAULT NULL,
  `race5_id` int DEFAULT NULL,
  `prec1_id` int DEFAULT NULL,
  `prec2_id` int DEFAULT NULL,
  `prec3_id` int DEFAULT NULL,
  `prec4_id` int DEFAULT NULL,
  `prec5_id` int DEFAULT NULL,
  `prec6_id` int DEFAULT NULL,
  `prec7_id` int DEFAULT NULL,
  `prec8_id` int DEFAULT NULL,
  `prec9_id` int DEFAULT NULL,
  `prec10_id` int DEFAULT NULL,
  PRIMARY KEY (`election_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `elections`
--

LOCK TABLES `elections` WRITE;
/*!40000 ALTER TABLE `elections` DISABLE KEYS */;
INSERT INTO `elections` VALUES (0,'boob','2022-04-05','08:06','16:20',NULL,NULL,NULL,NULL,NULL,2,5,6,8,11,NULL,NULL,NULL,NULL,NULL),(1,'boob2','2022-04-05','10:26','15:19',NULL,NULL,NULL,NULL,NULL,3,4,5,6,8,10,11,12,NULL,NULL);
/*!40000 ALTER TABLE `elections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `example`
--

DROP TABLE IF EXISTS `example`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `example` (
  `id` int DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `example`
--

LOCK TABLES `example` WRITE;
/*!40000 ALTER TABLE `example` DISABLE KEYS */;
INSERT INTO `example` VALUES (1,'Anthony'),(1,'Billy'),(1,'Dillon'),(1,'Nihaal'),(1,'Dillon'),(1,'Nihaal'),(1,'Connor'),(1,'Connor'),(1,'Mike'),(1,'Mike'),(1,'Mike'),(1,'Mike'),(1,'Jim');
/*!40000 ALTER TABLE `example` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_log`
--

LOCK TABLES `login_log` WRITE;
/*!40000 ALTER TABLE `login_log` DISABLE KEYS */;
INSERT INTO `login_log` VALUES (1,10,0,'2022-03-28 02:47:31'),(2,10,0,'2022-03-28 02:57:22'),(3,2829,0,'2022-03-28 02:57:47'),(4,2829,0,'2022-03-28 02:59:27'),(5,1358,0,'2022-03-28 03:00:45'),(6,1358,0,'2022-03-28 03:01:46'),(7,1358,1,'2022-03-28 03:03:16'),(8,1358,0,'2022-03-28 03:05:05'),(9,1358,0,'2022-03-28 03:05:19'),(10,1358,0,'2022-03-28 03:06:57'),(11,1358,0,'2022-03-28 03:08:00'),(12,1358,0,'2022-03-28 03:08:42'),(13,1358,0,'2022-03-28 03:09:49'),(14,1358,0,'2022-03-28 03:10:17'),(15,1358,0,'2022-03-28 03:10:26'),(16,1358,0,'2022-03-28 03:10:37'),(17,1358,0,'2022-03-28 03:11:28'),(18,1358,1,'2022-03-28 03:12:20'),(19,1358,1,'2022-03-28 03:44:25'),(20,1358,1,'2022-03-28 16:09:10'),(21,1358,1,'2022-03-28 16:26:22'),(23,1358,1,'2022-03-28 17:39:46'),(24,1358,1,'2022-03-28 17:42:09'),(25,1358,0,'2022-03-28 18:47:28'),(26,1358,0,'2022-03-28 18:47:51'),(27,1358,1,'2022-03-28 18:53:18'),(28,1358,1,'2022-03-28 18:55:11'),(29,1358,1,'2022-03-28 19:14:22'),(30,1358,1,'2022-03-28 19:16:00'),(31,1358,1,'2022-03-28 19:20:44'),(32,1358,1,'2022-03-28 19:22:33'),(33,1358,1,'2022-03-28 19:25:55'),(34,1358,1,'2022-03-28 19:50:27'),(35,1358,1,'2022-03-28 20:03:36'),(36,1358,1,'2022-03-28 20:06:53'),(37,1358,1,'2022-03-28 20:16:08'),(38,1358,1,'2022-03-28 20:25:14'),(39,1358,1,'2022-03-28 20:27:32'),(40,1358,1,'2022-03-28 20:32:19'),(41,1358,1,'2022-03-28 20:40:35'),(42,1358,1,'2022-03-28 20:54:57'),(43,1358,1,'2022-03-28 21:07:54'),(44,1358,1,'2022-03-28 21:10:25'),(45,1277,1,'2022-04-04 21:59:30');
/*!40000 ALTER TABLE `login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `precincts`
--

DROP TABLE IF EXISTS `precincts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `precincts` (
  `precinct_id` int NOT NULL,
  `location` varchar(45) DEFAULT NULL,
  `pooling_manager_id` int DEFAULT NULL,
  `state_election_office_email` varchar(45) DEFAULT NULL,
  `zip_start` varchar(45) DEFAULT NULL,
  `zip_end` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`precinct_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `precincts`
--

LOCK TABLES `precincts` WRITE;
/*!40000 ALTER TABLE `precincts` DISABLE KEYS */;
INSERT INTO `precincts` VALUES (1,'hell',1,'x@x.com','1000','2000'),(2,'boobtown',2,'y@y.com','3000','4000'),(3,'michigan',3,'z@z.com','5000','6000'),(4,NULL,NULL,NULL,NULL,NULL),(5,NULL,NULL,NULL,NULL,NULL),(6,NULL,NULL,NULL,NULL,NULL),(7,NULL,NULL,NULL,NULL,NULL),(8,NULL,NULL,NULL,NULL,NULL),(9,NULL,NULL,NULL,NULL,NULL),(10,NULL,NULL,NULL,NULL,NULL),(11,NULL,NULL,NULL,NULL,NULL),(12,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `precincts` ENABLE KEYS */;
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
  `prec1_id` int DEFAULT NULL,
  `prec2_id` int DEFAULT NULL,
  `prec3_id` int DEFAULT NULL,
  `prec4_id` int DEFAULT NULL,
  `prec5_id` int DEFAULT NULL,
  `prec6_id` int DEFAULT NULL,
  `prec7_id` int DEFAULT NULL,
  `prec8_id` int DEFAULT NULL,
  `prec9_id` int DEFAULT NULL,
  `prec10_id` int DEFAULT NULL,
  PRIMARY KEY (`race_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `races`
--

LOCK TABLES `races` WRITE;
/*!40000 ALTER TABLE `races` DISABLE KEYS */;
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
  `zipcode` int NOT NULL,
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
INSERT INTO `users` VALUES (1277,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','admin','Mike','Dobroski',69,'x','','x','IA',99999,'999999999','mdobroskii@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-04 21:59:30'),(3445,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','admin','Nihaal','Gill',69,'x','','x','IA',52240,'999999999','emailnihaal@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-04 21:50:43'),(9357,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','admin','Connor','Hoeger',69,'x','','x','IA',52240,'999999999','cmhoeger@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-04 21:41:20'),(9468,'19513fdc9da4fb72a4a05eb66917548d3c90ff94d5419e1f2363eea89dfee1dd','admin','Dillon','Africa',69,'x','','x','IA',52240,'999999999','africa23.da@gmail.com','Password1',1,NULL,NULL,0000000000,'2022-04-04 21:35:40');
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

-- Dump completed on 2022-04-05 19:17:31
