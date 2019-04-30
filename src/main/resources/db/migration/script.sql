# ************************************************************
# Host: 127.0.0.1 (MySQL 5.5.5-10.1.10-MariaDB)
# Database: upccollector
# Generation Time: 2019-04-30 10:25:40 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table activity
# ------------------------------------------------------------

DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `id` bigint(20) NOT NULL,
  `createdDate` datetime NOT NULL,
  `updatedDate` datetime DEFAULT NULL,
  `bottomFacing` tinyint(1) DEFAULT '0',
  `frontFacing` tinyint(1) DEFAULT '0',
  `leftFacing` tinyint(1) DEFAULT '0',
  `priceImage` tinyint(1) DEFAULT '0',
  `rearFacing` tinyint(1) DEFAULT '0',
  `rightFacing` tinyint(1) DEFAULT '0',
  `topFacing` tinyint(1) DEFAULT '0',
  `totalPhotosCount` varchar(255) DEFAULT NULL,
  `product_upc` varchar(255) DEFAULT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqb8efm21vaayjb04k2y0mtg0y` (`product_upc`),
  KEY `FKsxwly3o46n6dkl863ej2c8l72` (`userId`),
  CONSTRAINT `FKqb8efm21vaayjb04k2y0mtg0y` FOREIGN KEY (`product_upc`) REFERENCES `product` (`upc`),
  CONSTRAINT `FKsxwly3o46n6dkl863ej2c8l72` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table hibernate_sequence
# ------------------------------------------------------------

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;

INSERT INTO `hibernate_sequence` (`next_val`)
VALUES
	(1),
	(1);

/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `upc` varchar(255) NOT NULL,
  `createdDate` datetime NOT NULL,
  `updatedDate` datetime DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `categorySubType` varchar(255) DEFAULT NULL,
  `categoryType` varchar(255) DEFAULT NULL,
  `longName` varchar(512) DEFAULT NULL,
  `mfg` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `shortName` varchar(512) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `activityId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`upc`),
  KEY `FK2vx63cv7ja7ca447vevne9pw5` (`activityId`),
  CONSTRAINT `FK2vx63cv7ja7ca447vevne9pw5` FOREIGN KEY (`activityId`) REFERENCES `activity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nb4h0p6txrmfc0xbrd1kglp9t` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;

INSERT INTO `roles` (`id`, `name`)
VALUES
	(2,'ROLE_ADMIN'),
	(1,'ROLE_USER');

/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `createdDate` datetime NOT NULL,
  `updatedDate` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table userRoles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `userRoles`;

CREATE TABLE `userRoles` (
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`),
  KEY `FKne7e7xkwg8jw19u3f79bxuguv` (`roleId`),
  CONSTRAINT `FKg5a6curv4vstaao5xgm2teali` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `FKne7e7xkwg8jw19u3f79bxuguv` FOREIGN KEY (`roleId`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
