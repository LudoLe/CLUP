-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: clup
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
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(250) NOT NULL DEFAULT 'no description',
  `position` varchar(100) NOT NULL,
  `image` varchar(450) DEFAULT NULL,
  `manager_id` int NOT NULL,
  `shop_capacity` int NOT NULL DEFAULT '1',
  `timeslot_minutes_duration` int NOT NULL DEFAULT '5',
  `max_entering_client_in_a_timeslot` int NOT NULL DEFAULT '5',
  PRIMARY KEY (`id`),
  KEY `manager_id_idx` (`manager_id`),
  CONSTRAINT `manager_id` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES (1,'shopName','a nice welcomy shop.','shop street','missing image',2,3,5,3);
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop_shift`
--

DROP TABLE IF EXISTS `shop_shift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_shift` (
  `id` int NOT NULL AUTO_INCREMENT,
  `shop_id` int NOT NULL,
  `opening_time` time NOT NULL,
  `closing_time` time NOT NULL,
  `day` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `related_shop_idx` (`shop_id`),
  CONSTRAINT `shop_schedule` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_shift`
--

LOCK TABLES `shop_shift` WRITE;
/*!40000 ALTER TABLE `shop_shift` DISABLE KEYS */;
INSERT INTO `shop_shift` VALUES (1,1,'08:00:00','13:00:00',1),(2,1,'14:30:00','19:00:00',1);
/*!40000 ALTER TABLE `shop_shift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `id` int NOT NULL AUTO_INCREMENT,
  `shop_id` int NOT NULL,
  `user_id` int NOT NULL,
  `status` varchar(45) NOT NULL DEFAULT 'valid',
  `enter_time` datetime DEFAULT NULL,
  `exit_time` datetime DEFAULT NULL,
  `expected_duration` datetime NOT NULL,
  `scheduled_entering_time` datetime DEFAULT NULL,
  `scheduled_exiting_time` datetime DEFAULT NULL,
  `time_to_reach_the_shop` datetime DEFAULT NULL,
  `arrival_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `relate_shop_idx` (`shop_id`),
  KEY `user_ticket_idx` (`user_id`),
  CONSTRAINT `related_shop_ticket` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`),
  CONSTRAINT `user_ticket` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(500) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `is_manager` tinyint(1) NOT NULL DEFAULT '0',
  `session_token` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user','$argon2id$v=19$m=16384,t=2,p=1$z8qc42pAlSJwBy7gSYCeMEI9fihDWkZFjPaiUVJLeleVLAgO/51n4eEaAk5a0gWPDrRy9868k3MVJf5pOCNqNA$WemmIIgWem4EymirfSYKjjR9nF3mMWHPgfPdJc3U1zsJVlpMYl+ngqqAcX3GXeVLTQ0/8iLFP9ySxeyql57aKDJa007Zq+gkfk8vdFQeLuClf7TcW975sccDS2Kiwf+T8rlR90hf4aXdLb7puBPtq2PZGzGLnnAE0eWGVNpYAJo','user@user.it','3312918253',0,'uhSrFsNwNTkvUx4WRR3upJIebKexWrE1'),(2,'manager','$argon2id$v=19$m=16384,t=2,p=1$toPpTjppTlFzIUnHt4R8SohOdceqfSShpSmWbj0qdMbuhUiWeJzcD0qHilkx/1H8v/9YeN+N4+41b15iLo7bEg$UpxU/4gRL7l6NLwiLb4F4NjObrhBxgu8t5IejkLQu4v0r6MFnYnfsXZwBp4XY810YIjtUZc/jcZXHSxfUZkHkLQikyA+Yt4mASAIxSc/zZe1yCTkkqGpLwi7h5NhNRKG3IbG34YITEpwkMhPB6ioSJfrw5oOP5dVWGl5FdWSy48','manager@manager.it','3312918253',1,'iyp5M6ifanG9cKB7ptNc9kkzZ9h1_dMB'),(20,'managertest','$argon2id$v=19$m=16384,t=2,p=1$7cEK8+AvBk3ZE+37rqDUdaFDSkV+hMI3kTcVe1ll3A+MK3HuEuDIXSEwXZgl7iE/shTRZjtImBtxWn4G6q1XuA$OMUhvsg87HvVGmp+3hZEAlZ+8kOJIu2mrkhtxS9tQJmEywNLW4PcYTYKYHOMkEdYSwSH9QOio2hv3axJP+8kOJ6eP71r9eDQwsaAS29/ln39SPZVxlonZdjrM+L26WbCCP8oa4vM2hHo4oR221EtU/AuwOIbhgS8b+E0hHtvSlY','manager@mananan','12312313',1,NULL);
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

-- Dump completed on 2021-02-15 15:25:54
