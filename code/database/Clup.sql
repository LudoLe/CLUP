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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES (1,'string','string','string','string',3,0,0,0),(2,'new shop','string','string','string',7,0,0,0),(3,'new shop','string','string','string',7,0,0,0),(4,'str0ing','string','string','string',4,0,-5,0),(5,'string','string','string','string',4,3,3,3),(6,'string','string','string','string',4,3,3,34),(7,'string','string','string','string',4,10,10,10),(8,'shoppi','shoppi ','ssss','string',4,5,5,5);
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_shift`
--

LOCK TABLES `shop_shift` WRITE;
/*!40000 ALTER TABLE `shop_shift` DISABLE KEYS */;
INSERT INTO `shop_shift` VALUES (1,5,'13:44:50','13:44:50',0),(2,5,'13:50:43','13:50:43',0),(3,5,'13:50:43','13:50:43',0),(4,5,'13:50:43','13:50:43',0),(5,4,'16:17:16','16:17:16',0);
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
  `time_to_reach_the_shop` time DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `relate_shop_idx` (`shop_id`),
  KEY `user_ticket_idx` (`user_id`),
  CONSTRAINT `related_shop_ticket` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`),
  CONSTRAINT `user_ticket` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (21,5,4,'VALID','13:50:43','13:50:43','00:00:45','13:50:43','13:50:43','00:00:12'),(22,5,4,'INVALID','13:50:43','13:50:43','00:00:56','13:50:43','13:50:43','00:00:54'),(23,4,5,'invalid',NULL,NULL,'00:00:55',NULL,NULL,'19:00:01'),(24,5,5,'invalid',NULL,NULL,'00:14:01',NULL,NULL,'00:14:01'),(25,5,5,'invalid',NULL,NULL,'20:00:06',NULL,NULL,'20:00:06'),(26,5,5,'invalid',NULL,NULL,'20:00:14',NULL,NULL,'20:00:14'),(27,5,5,'invalid',NULL,NULL,'20:00:34',NULL,NULL,'20:00:34'),(28,5,4,'invalid',NULL,NULL,'00:08:10',NULL,NULL,'15:00:10'),(29,5,4,'invalid',NULL,NULL,'00:11:49',NULL,NULL,'00:11:49'),(30,5,4,'invalid',NULL,NULL,'00:16:52',NULL,NULL,'00:16:52'),(31,5,4,'invalid',NULL,NULL,'00:24:28',NULL,NULL,'00:24:28');
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ludole','$argon2id$v=19$m=16384,t=2,p=1$FmHxp1kiTp0yjjGVkwzSlD8CmOarq93Ko+z95LHbqhhHsfL4C3gYxTSekDK7rpiyIF5ivnRfJqLvXyKBhPNvkA$ea2rw44ymoe8wZMARRsn7kRMI19j2ccl/SNZAxs2J+Xn6gpKBYurKXJpqGA2yxggeVPBKbsAyTRHWMH0thYmGDHlU0vQ7cZYOyi/3dHt8iK+a1mh4MMFLYeZlofBJNM/P9+7wXJT3EFN+iOM33vtVBwaAwSG7bc5T3rrmkQ9zQY','ludole@ludolu.com','333',1,''),(2,'ludol3e3','$argon2id$v=19$m=16384,t=2,p=1$Hq0f2Uta+SSOVznnQHCq7E8Kkfi6Cbxryu4x60kGTJthB+egTbt6lUztq5oqgxcSJ1+gfpMvCSobaqfYNLFQMQ$+9nrz4ldsYjzTW07jCSYSd84ZfHrVmc9WiVQIQNBFLUe3llU+vDh0jy0nYvRHuSAbaEBnRlWk5oGDBrwRdxaJPpJ89BaXdMSjhqFyAzkkgy48QTu1EymVx4yDu/zmxcSunlG5izvX2mz5w1N6r401u8EHl6SagaNO26soLdl7dw','ludole@ludolu3.com','3333',1,''),(3,'string','$argon2id$v=19$m=16384,t=2,p=1$+iiZUHDZoZMt0jWTo3hoLSIbZlJv9kyjv/WRDysPBjVv75STfwZKCbon4m2l5gMTRDb5LCi5jtah/J9UJwUlzg$F7nHu6DNE56MtbK2vOpdjaEFEjUn1Sw8IxEnZ5Cihaj4X6WRSwvr1jITRIE23FEPHT0SZqPEwlup4CjhEiKxvOldrfzFy2YUTkh+D+EBdhM8wI3vF9tXx9XweiSSiV0748T1irA6EuEQHZQv5eXyaCXpTpCQkMsB9Sqeh7eQW04','string','string',0,'R3JoHSJP0833x-6nn33WsgZCoU4myxI5'),(4,'string3','$argon2id$v=19$m=16384,t=2,p=1$dinDHhsDX+VkJyZ4Fv5B1qynHcglapyflKwk/KmoQj5cC3d7KAWPhkKVAphXOK4UfvhU+MLQAeEsKX9Z7/gZ/Q$vIV/LAlIIcQh8lrlVwR23clLAUTx7gA1lu77sX5wShI1kW41JVTqVD2gs0tPt42VOGD1sNcWk5OUjkNxtVgrSpfq2wB6nG31eDzkwf2/xOvlDcBu5W3Gy7rqiNQ3/xaRjI6viAWl5JSRB/yM0e/kElEguhTDfmEKyNMSRQm1UIw','string2','string',1,'gIz5BnTMRpmPalmgzRmznI4CrdtITlSc'),(5,'string4','$argon2id$v=19$m=16384,t=2,p=1$9cKqu6woTXkmFuHcVZnkfkJKmEbEvs9mtyQGXJ8c5STW9anSBI35Q+jYt1WZY/WZ2gEN7zGFChM7OtKkWV+2AA$KZ4GHdFVFunADm4xT9jFGJzl1CSpvLXUNJdJGDFpQIzbDHdoRGJmHSaKVAMA/fmpJeFVlAYv3jQfuoyqe9u6ole2ZhtapwSkQSEYhPTNjr7XGby0tDNiHm1Q777gtsFPKDdABrdGHjawD8vJxGIceFHJDZEibZcIfYElYquwgRo','string5','string',1,'YXixs50V7AISz_D4ToEGITqcbgSysURo'),(6,'string5','$argon2id$v=19$m=16384,t=2,p=1$ho9Co61t1yst+S7j3Y4g4yOEgDefGzV+xztYtccs9XkSqfHlsy+SUWxWLtkSwwklYodS7lrBI9ER8z1O+HEnMA$37BwgfU9OFzbnSDQdhKqyMIpEQ96JtL+TDS9fg5qcga8zBchlDgbiQ2kTVToFIj+WG7AIFQ3UApYg2qTKtLnpC5QpH0KRA+77RR/T+XcOealwj29U2OSAPC0bjc5c+4/BPZQTk1tuSnNJTDUml5kkoOnY8GJ3Qm3gEGa1h0DL1s','string55','string',1,''),(7,'string0','$argon2id$v=19$m=16384,t=2,p=1$4/1Yrs+o/SYx57Xl0yPloTAadSrLI4OcUf5P5hVxFxM7fKj8SPfFjhvMLyjAaiaEFZ7fR6rgSm9fs5TnsX7ncw$gEMPZchukR3GoKgH2Kh3OSV8Ubg1FUja/asj6uSyUOFDNk0LTSviah91mj9I+zJDhcdx5iSlsQRTpskE8C0eaVpxwfXsqqV7IkJ0kOQTzvSqJGT709xg3Crul1WzRFgn9eha/cS27Pb955rJGsQXNhs5oH61n9VSoZmKA2pPE/M','string12','string',1,NULL),(8,'lotto','$argon2id$v=19$m=16384,t=2,p=1$klKu6Bkikqs1Icp0ElBGI20HUEqccMeX3tI2M0bwkXUoMRTia2WkC3s9/mKRljQUYWQ3s6C008P02OwXvi28Tg$OsoxpIJmFDoIUi8m/CyuVokRRlJGqTuH8Fe2De8O7g6A1LaMCJNJwHS7kWPsJjY1XamOKA06zjzz8MJXiG+RQsqd/dGbSoIqPhrbZ7ILIbFd37UNJj5mDGGtI0Vhj9Smd51H9SRqdfHkTyx5IeVWbbyG3/g+qCoNS/UChdva928','slottog','string',0,'4iLjLW9oDV3EZSv3yyv3IrQbt_kxENZJ');
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

-- Dump completed on 2021-02-08 19:26:57
