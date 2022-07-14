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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `voterID` int NOT NULL,
  `password` varchar(45) NOT NULL,
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
  PRIMARY KEY (`voterID`),
  UNIQUE KEY `username_UNIQUE` (`voterID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table to hold all users \n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'iowa1','owner','Dillon','Africa',0,'',NULL,'','',0,'','','',0,NULL,NULL),(2,'this','is','a','test',0,'',NULL,'','',0,'','','',0,NULL,NULL),(3,'iowa2','owner','Mike','Dobroski',0,'',NULL,'','',0,'','','',0,NULL,NULL),(4,'this','is','a','test',0,'',NULL,'','',0,'','','',0,NULL,NULL),(5,'this','is','a','test',0,'',NULL,'','',0,'','','',0,NULL,NULL),(1358,'Fihoaenrf30490349','user','h','h',89,'h','','h','IA',3940,'023940293','sojf@gkswefk.com','Fihoaenrf30490349',0,NULL,NULL),(2829,'Hjdfnoben95039420934','user','b','b',89,'b','','b','IA',34853,'023940293','b@b.com','Hjdfnoben95039420934',0,NULL,NULL),(3427,'Fuckyou1','user','new','entry',69,'h','h','h','IA',40583,'938458394','fuk@you.com','Fuckyou1',0,NULL,NULL),(3496,'Fivjboshndofi495893845','user','mr','poopy',69420,'nah','nah','ye','IA',40593,'240910394','fuck@me.com','Fivjboshndofi495893845',0,NULL,NULL),(4040,'Yoooooooooooo309493030','user','n','n',19,'n','n','n','NY',93485,'952483958','fuck@you.org','Yoooooooooooo309493030',0,NULL,NULL),(4607,'Whoa439308493849384','user','p','p',89,'p','','p','IL',94859,'493492849','h@h.com','Whoa439308493849384',0,NULL,NULL),(4696,'Whoadude5894859485','user','h','h',79,'h','h','h','IA',50943,'309450394','dobroskimichael@gmail.com','Whoadude5894859485',0,NULL,NULL),(5368,'Fuckthispussybo1','user','newfname','newlname',79,'newadr','newapt','newcity','NY',69696,'696969696','fuck@you.com','Fuckthispussybo1',0,NULL,NULL),(6773,'Fuckyou1','user','f','f',29,'f','d','f','IA',94583,'294593845','fuck@you.com','Fuckyou1',0,NULL,NULL),(7143,'Fuckme594859485','user','h','h',67,'h','','h','IL',29384,'293849283','what@no.com','Fuckme594859485',0,NULL,NULL),(8356,'Fuckyou1','user','h','h',19,'h','h','h','IA',2348,'098345830','fuck@you.com','Fuckyou1',0,NULL,NULL),(9140,'Ficbhnwoirn9384928349834','user','h','h',89,'h','','h','IA',42343,'942839849','hehe@michaeljackson.com','Ficbhnwoirn9384928349834',0,NULL,NULL);
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

-- Dump completed on 2022-03-25 16:11:24
