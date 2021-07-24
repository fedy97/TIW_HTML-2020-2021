-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: ecommerce
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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `category` varchar(45) NOT NULL,
  `photo` varchar(100) NOT NULL,
  `insr_ts` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'Coprivolante alcantara M Sport','Proteggi il tuo volante da graffi, sbiadimento e usura.Migliora la tua esperienza di guida! Bellissima fattura, migliora l\'aspetto del vecchio o volanti sporchi, rendendo l\'interno della tua auto chic e perfetto.','Accessori auto','https://m.media-amazon.com/images/I/61IbuxGbexS._AC_SL1500_.jpg','2021-07-10 13:41:41'),(2,'art2','Proteggi il tuo volante da graffi, sbiadimento e usura.Migliora la tua esperienza di guida! Bellissima fattura, migliora l\'aspetto del vecchio o volanti sporchi, rendendo l\'interno della tua auto chic e perfetto.','cat2','https://m.media-amazon.com/images/I/61IbuxGbexS._AC_SL1500_.jpg','2021-07-10 13:41:41'),(5,'Samsung SMART TV','Immagini superiori e colori brillanti con le tecnologie Quantum Dot e Dual Led','Elettronica','https://m.media-amazon.com/images/I/61vmdmh8FRL._AC_SL1307_.jpg','2021-07-24 15:26:35'),(6,'SONAX - Detergente per gomma','Rinnovatore di gomme da 100 ml','Accessori auto','https://m.media-amazon.com/images/I/71DVXMOId4L._AC_SL1500_.jpg','2021-07-24 15:26:35'),(7,'Pellicola di Protezione','Dimensioni: 300cm * 30cm; Materiale: realizzato in PVC di alta qualità; Colore: trasparente; Il nostro film adesivo di alta qualità mantiene l\'auto lontana dai graffi','Accessori auto','https://m.media-amazon.com/images/I/51-8ae4w5YL._AC_SL1001_.jpg','2021-07-24 15:26:35'),(8,'Pellicola Adesiva per Auto','Questo adesivo lascia un aspetto nuovo e brillante. Fai in modo che la tua auto abbia un nuovo aspetto.','Accessori auto','https://m.media-amazon.com/images/I/71SHEF3wzYL._AC_SL1500_.jpg','2021-07-24 15:39:23'),(9,'Huawei P Smart S ','Sistema Operativo: Android 10 Open Source1 ed EMUI 10.1. Fotocamera: 48 MP f/1.8, ultra grandangolare 8 MP f/2.4, effetto Bokeh da 2 MP f/2.4.','Elettronica','https://m.media-amazon.com/images/I/71iGyIYkTRL._AC_SL1500_.jpg','2021-07-24 15:45:05'),(10,'HUAWEI MateBook 13 ','Laptop, FullView Display 2K da 13 Pollici PC Portatile, AMD Ryzen 7 3700U, 16GB RAM, 512GB SSD, Windows 10, Huawei share, Fingerprint Unlock, Fast Charging, Layout Italiano, Grey','Elettronica','https://m.media-amazon.com/images/I/51Jc7QWN2CL._AC_SL1000_.jpg','2021-07-24 15:46:31');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `seller_id` int NOT NULL,
  `user_id` int NOT NULL,
  `price_articles` float DEFAULT NULL,
  `price_shipment` float DEFAULT NULL,
  `shipment_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_user_id_idx` (`user_id`),
  KEY `fk_seller_id_idx` (`seller_id`),
  CONSTRAINT `fk_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `seller` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,1,1,20,5,'2021-07-10 13:41:41','2021-07-10 13:41:41'),(2,2,1,30,0,'2021-07-10 13:41:41','2021-07-10 13:41:41'),(3,1,1,6.2,5,'2021-07-16 10:11:42','2021-07-16 10:11:42'),(4,1,1,21.7,0,'2021-07-16 12:10:08','2021-07-16 12:10:08'),(5,1,1,10.6,5,'2021-07-16 12:36:09','2021-07-16 12:36:09'),(6,1,1,10.6,5,'2021-07-16 12:36:40','2021-07-16 12:36:40'),(7,2,1,8,4,'2021-07-17 11:11:40','2021-07-17 11:11:40'),(8,2,1,4,4,'2021-07-17 11:24:57','2021-07-17 11:24:57'),(9,1,1,5.3,5,'2021-07-17 11:26:10','2021-07-17 11:26:10'),(10,1,1,3.1,5,'2021-07-17 11:26:21','2021-07-17 11:26:21'),(11,2,1,4,4,'2021-07-17 11:26:33','2021-07-17 11:26:33'),(12,1,1,3.1,5,'2021-07-17 11:29:12','2021-07-17 11:29:12'),(13,2,1,4,4,'2021-07-17 11:29:32','2021-07-17 11:29:32'),(14,1,1,5.3,5,'2021-07-17 11:32:09','2021-07-17 11:32:09'),(15,1,1,42.4,0,'2021-07-17 15:04:16','2021-07-17 15:04:16'),(16,1,1,55,5,'2021-07-17 15:14:29','2021-07-17 15:14:29'),(17,1,1,220,0,'2021-07-22 12:10:21','2021-07-22 12:10:21'),(18,1,1,55,5,'2021-07-22 12:35:58','2021-07-22 12:35:58'),(19,1,1,110,0,'2021-07-22 15:28:52','2021-07-22 15:28:52'),(20,1,1,220,0,'2021-07-23 19:16:48','2021-07-23 19:16:48'),(21,1,1,110,0,'2021-07-23 19:17:10','2021-07-23 19:17:10'),(22,2,1,20,0,'2021-07-23 19:21:46','2021-07-23 19:21:46'),(23,1,1,12.4,0,'2021-07-24 14:59:19','2021-07-24 14:59:19'),(24,1,1,110,0,'2021-07-24 15:04:34','2021-07-24 15:04:34'),(25,1,1,900.99,0,'2021-07-24 15:54:59','2021-07-24 15:54:59');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_article`
--

DROP TABLE IF EXISTS `order_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_article` (
  `order_id` int NOT NULL,
  `article_id` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`order_id`,`article_id`),
  KEY `fk2_article_id_idx` (`article_id`),
  CONSTRAINT `fk2_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk2_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_article`
--

LOCK TABLES `order_article` WRITE;
/*!40000 ALTER TABLE `order_article` DISABLE KEYS */;
INSERT INTO `order_article` VALUES (1,1,2),(1,2,3),(2,1,2),(3,1,2),(4,1,7),(5,2,2),(6,2,2),(7,1,2),(8,1,1),(9,2,1),(10,1,1),(11,1,1),(12,1,1),(13,1,1),(14,2,1),(15,2,8),(16,2,1),(17,2,4),(18,2,1),(19,2,2),(20,2,4),(21,2,2),(22,1,5),(23,1,4),(24,2,2),(25,5,1),(25,10,1);
/*!40000 ALTER TABLE `order_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller`
--

DROP TABLE IF EXISTS `seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seller` (
  `id` int NOT NULL AUTO_INCREMENT,
  `seller_name` varchar(45) NOT NULL,
  `seller_rating` float NOT NULL,
  `price_threshold` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller`
--

LOCK TABLES `seller` WRITE;
/*!40000 ALTER TABLE `seller` DISABLE KEYS */;
INSERT INTO `seller` VALUES (1,'seller1',3,100),(2,'seller2',4,50);
/*!40000 ALTER TABLE `seller` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_article`
--

DROP TABLE IF EXISTS `seller_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seller_article` (
  `seller_id` int NOT NULL,
  `article_id` int NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`seller_id`,`article_id`),
  KEY `fk3_article_id_idx` (`article_id`),
  CONSTRAINT `fk3_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk3_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `seller` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_article`
--

LOCK TABLES `seller_article` WRITE;
/*!40000 ALTER TABLE `seller_article` DISABLE KEYS */;
INSERT INTO `seller_article` VALUES (1,1,3.1),(1,2,55),(1,5,400.99),(1,6,15),(1,7,6),(1,8,20),(1,10,500),(2,1,4),(2,5,499),(2,6,10),(2,7,5),(2,9,120.15),(2,10,480);
/*!40000 ALTER TABLE `seller_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping_policy`
--

DROP TABLE IF EXISTS `shipping_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_policy` (
  `id` int NOT NULL AUTO_INCREMENT,
  `seller_id` int NOT NULL,
  `min_item` int NOT NULL,
  `max_item` int DEFAULT NULL,
  `ship_cost` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_seller_id_idx` (`seller_id`),
  CONSTRAINT `fk2_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `seller` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_policy`
--

LOCK TABLES `shipping_policy` WRITE;
/*!40000 ALTER TABLE `shipping_policy` DISABLE KEYS */;
INSERT INTO `shipping_policy` VALUES (1,1,1,3,5),(2,1,4,NULL,0),(3,2,1,3,4),(4,2,4,NULL,0);
/*!40000 ALTER TABLE `shipping_policy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `psw_hash` varchar(45) NOT NULL,
  `shipment_addr` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'name1','surname1','email1@mail.com','polistore1','via lol 1'),(2,'name2','surname2','email2@mail.com','polistore1','via lol 2');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_article`
--

DROP TABLE IF EXISTS `user_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_article` (
  `user_id` int NOT NULL,
  `article_id` int NOT NULL,
  `view_ts` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`article_id`),
  KEY `fk4_article_id_idx` (`article_id`),
  CONSTRAINT `fk4_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk4_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_article`
--

LOCK TABLES `user_article` WRITE;
/*!40000 ALTER TABLE `user_article` DISABLE KEYS */;
INSERT INTO `user_article` VALUES (1,1,'2021-07-16 12:17:29'),(1,5,'2021-07-24 15:42:25'),(1,6,'2021-07-24 15:42:10'),(1,9,'2021-07-24 15:48:15'),(1,10,'2021-07-24 15:48:17');
/*!40000 ALTER TABLE `user_article` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-24 15:56:36
