-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: localhost    Database: clup
-- ------------------------------------------------------
-- Server version	8.0.23

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
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
  `day` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `related_shop_idx` (`shop_id`),
  CONSTRAINT `shop_schedule` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_shift`
--

LOCK TABLES `shop_shift` WRITE;
/*!40000 ALTER TABLE `shop_shift` DISABLE KEYS */;
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
  `enter_time` time DEFAULT NULL,
  `exit_time` time DEFAULT NULL,
  `expected_duration` time NOT NULL,
  `scheduled_entering_time` time DEFAULT NULL,
  `scheduled_exiting_time` time DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `relate_shop_idx` (`shop_id`),
  KEY `user_ticket_idx` (`user_id`),
  CONSTRAINT `related_shop_ticket` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`),
  CONSTRAINT `user_ticket` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `session_token` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ludole','$argon2id$v=19$m=16384,t=2,p=1$FmHxp1kiTp0yjjGVkwzSlD8CmOarq93Ko+z95LHbqhhHsfL4C3gYxTSekDK7rpiyIF5ivnRfJqLvXyKBhPNvkA$ea2rw44ymoe8wZMARRsn7kRMI19j2ccl/SNZAxs2J+Xn6gpKBYurKXJpqGA2yxggeVPBKbsAyTRHWMH0thYmGDHlU0vQ7cZYOyi/3dHt8iK+a1mh4MMFLYeZlofBJNM/P9+7wXJT3EFN+iOM33vtVBwaAwSG7bc5T3rrmkQ9zQY','ludole@ludolu.com','333',1,''),(2,'ludol3e3','$argon2id$v=19$m=16384,t=2,p=1$Hq0f2Uta+SSOVznnQHCq7E8Kkfi6Cbxryu4x60kGTJthB+egTbt6lUztq5oqgxcSJ1+gfpMvCSobaqfYNLFQMQ$+9nrz4ldsYjzTW07jCSYSd84ZfHrVmc9WiVQIQNBFLUe3llU+vDh0jy0nYvRHuSAbaEBnRlWk5oGDBrwRdxaJPpJ89BaXdMSjhqFyAzkkgy48QTu1EymVx4yDu/zmxcSunlG5izvX2mz5w1N6r401u8EHl6SagaNO26soLdl7dw','ludole@ludolu3.com','3333',1,'');
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

-- Dump completed on 2021-02-06  2:07:48
