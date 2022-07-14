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
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_log`
--

LOCK TABLES `login_log` WRITE;
/*!40000 ALTER TABLE `login_log` DISABLE KEYS */;
INSERT INTO `login_log` VALUES (1,10,0,'2022-03-28 02:47:31'),(2,10,0,'2022-03-28 02:57:22'),(3,2829,0,'2022-03-28 02:57:47'),(4,2829,0,'2022-03-28 02:59:27'),(5,1358,0,'2022-03-28 03:00:45'),(6,1358,0,'2022-03-28 03:01:46'),(7,1358,1,'2022-03-28 03:03:16'),(8,1358,0,'2022-03-28 03:05:05'),(9,1358,0,'2022-03-28 03:05:19'),(10,1358,0,'2022-03-28 03:06:57'),(11,1358,0,'2022-03-28 03:08:00'),(12,1358,0,'2022-03-28 03:08:42'),(13,1358,0,'2022-03-28 03:09:49'),(14,1358,0,'2022-03-28 03:10:17'),(15,1358,0,'2022-03-28 03:10:26'),(16,1358,0,'2022-03-28 03:10:37'),(17,1358,0,'2022-03-28 03:11:28'),(18,1358,1,'2022-03-28 03:12:20'),(19,1358,1,'2022-03-28 03:44:25'),(20,1358,1,'2022-03-28 16:09:10'),(21,1358,1,'2022-03-28 16:26:22'),(23,1358,1,'2022-03-28 17:39:46'),(24,1358,1,'2022-03-28 17:42:09'),(25,1358,0,'2022-03-28 18:47:28'),(26,1358,0,'2022-03-28 18:47:51'),(27,1358,1,'2022-03-28 18:53:18'),(28,1358,1,'2022-03-28 18:55:11'),(29,1358,1,'2022-03-28 19:14:22'),(30,1358,1,'2022-03-28 19:16:00'),(31,1358,1,'2022-03-28 19:20:44'),(32,1358,1,'2022-03-28 19:22:33'),(33,1358,1,'2022-03-28 19:25:55'),(34,1358,1,'2022-03-28 19:50:27'),(35,1358,1,'2022-03-28 20:03:36'),(36,1358,1,'2022-03-28 20:06:53'),(37,1358,1,'2022-03-28 20:16:08'),(38,1358,1,'2022-03-28 20:25:14'),(39,1358,1,'2022-03-28 20:27:32'),(40,1358,1,'2022-03-28 20:32:19'),(41,1358,1,'2022-03-28 20:40:35'),(42,1358,1,'2022-03-28 20:54:57'),(43,1358,1,'2022-03-28 21:07:54'),(44,1358,1,'2022-03-28 21:10:25');
/*!40000 ALTER TABLE `login_log` ENABLE KEYS */;
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
INSERT INTO `users` VALUES (1,'e66db66dc28c5cbf70e03db94501a6ecb3181a4028bbe1a50fbe5f380c28de67','owner','Dillon','Africa',0,'',NULL,'','',0,'','','',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(2,'1eb79602411ef02cf6fe117897015fff89f80face4eccd50425c45149b148408','is','a','test',0,'',NULL,'','',0,'','','',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(3,'414d6fe1f183031ebd55ceba5d65822496b7ceb8fd39a32058390d1f123526c6','owner','Mike','Dobroski',0,'',NULL,'','',0,'','','',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(4,'1eb79602411ef02cf6fe117897015fff89f80face4eccd50425c45149b148408','is','a','test',0,'',NULL,'','',0,'','','',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(5,'1eb79602411ef02cf6fe117897015fff89f80face4eccd50425c45149b148408','is','a','test',0,'',NULL,'','',0,'','','',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(1358,'2394ebde7c70751c8236409fcaeee80ac54a46515ceb575a875aa02f7e795f88','user','h','h',89,'h','','h','IA',3940,'023940293','africa23.da@gmail.com','Fihoaenrf30490349',1,0,NULL,0000000000,'2022-03-28 21:10:25'),(2829,'11d67061b356239a78c7b35331271b76a8c36c82116cecce5d720f344b5a8985','user','b','b',89,'b','','b','IA',34853,'023940293','b@b.com','Hjdfnoben95039420934',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(3427,'7828c44da536d1b1b410581ed1914ddfdcca0e441622a5eda3d6f3276ef252ef','user','new','entry',69,'h','h','h','IA',40583,'938458394','fuk@you.com','Fuckyou1',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(3496,'32bfdb89393e7179ed91d61b26749344850529382d234b2e232895cc6ed285ba','user','mr','poopy',69420,'nah','nah','ye','IA',40593,'240910394','fuck@me.com','Fivjboshndofi495893845',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(4040,'46a2eba14806966c4fff72a70946351245bbb8839aa75c2dbdfba98a08c3a6ab','user','n','n',19,'n','n','n','NY',93485,'952483958','fuck@you.org','Yoooooooooooo309493030',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(4607,'e55a9869ecce8b132ac4e6e99eaba9887e89e8e6ea4941682a2d328303585dc2','user','p','p',89,'p','','p','IL',94859,'493492849','h@h.com','Whoa439308493849384',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(4696,'001409103a345fd15ffbd6f99993d774bd9ae9dba1977b48a9ed5af70d4de76c','user','h','h',79,'h','h','h','IA',50943,'309450394','dobroskimichael@gmail.com','Whoadude5894859485',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(5368,'602b41e5151af267c6df2a55762f50a6216d51a46a279d6a5f88ff2644ef245a','user','newfname','newlname',79,'newadr','newapt','newcity','NY',69696,'696969696','fuck@you.com','Fuckthispussybo1',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(6773,'7828c44da536d1b1b410581ed1914ddfdcca0e441622a5eda3d6f3276ef252ef','user','f','f',29,'f','d','f','IA',94583,'294593845','fuck@you.com','Fuckyou1',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(7143,'1df9d5108834def56bebb9cb11048b30a5d6dce4994c0915661182f878f72ad0','user','h','h',67,'h','','h','IL',29384,'293849283','what@no.com','Fuckme594859485',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(8356,'7828c44da536d1b1b410581ed1914ddfdcca0e441622a5eda3d6f3276ef252ef','user','h','h',19,'h','h','h','IA',2348,'098345830','fuck@you.com','Fuckyou1',0,NULL,NULL,0000000000,'0000-00-00 00:00:00'),(9140,'4665aa509c4f90ca30f7d929491b6a725176efd0a7aed07a012942ded914ffe5','user','h','h',89,'h','','h','IA',42343,'942839849','hehe@michaeljackson.com','Ficbhnwoirn9384928349834',0,NULL,NULL,0000000000,'0000-00-00 00:00:00');
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

-- Dump completed on 2022-03-28 16:14:41
