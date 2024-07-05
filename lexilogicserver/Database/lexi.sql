-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 21, 2024 at 06:29 PM
-- Server version: 8.2.0
-- PHP Version: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lexi`
--

-- --------------------------------------------------------

--
-- Table structure for table `gamedetails`
--

DROP TABLE IF EXISTS `gamedetails`;
CREATE TABLE IF NOT EXISTS `gamedetails` (
  `playerID` int NOT NULL,
  `lobbyID` int NOT NULL,
  `totalPoints` int DEFAULT NULL,
  PRIMARY KEY (`playerID`,`lobbyID`),
  KEY `lobbyID` (`lobbyID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `gamedetails`
--

INSERT INTO `gamedetails` (`playerID`, `lobbyID`, `totalPoints`) VALUES
(5, 130, 240),
(4, 127, 640),
(2, 126, 250),
(3, 126, 470),
(1, 125, 640),
(2, 125, 510),
(3, 127, 590),
(6, 128, 530),
(6, 129, 200),
(3, 130, 690),
(4, 128, 650),
(5, 129, 520);

-- --------------------------------------------------------

--
-- Table structure for table `leaderboards`
--

DROP TABLE IF EXISTS `leaderboards`;
CREATE TABLE IF NOT EXISTS `leaderboards` (
  `leaderBoardID` int NOT NULL AUTO_INCREMENT,
  `userID` int DEFAULT NULL,
  `totalPoints` int DEFAULT NULL,
  PRIMARY KEY (`leaderBoardID`),
  KEY `userID` (`userID`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `leaderboards`
--

INSERT INTO `leaderboards` (`leaderBoardID`, `userID`, `totalPoints`) VALUES
(31, 6, 730),
(30, 5, 760),
(29, 4, 1290),
(28, 3, 1750),
(27, 2, 760),
(26, 1, 640);

-- --------------------------------------------------------

--
-- Table structure for table `lobby`
--

DROP TABLE IF EXISTS `lobby`;
CREATE TABLE IF NOT EXISTS `lobby` (
  `lobbyID` int NOT NULL AUTO_INCREMENT,
  `winner` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`lobbyID`)
) ENGINE=MyISAM AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lobby`
--

INSERT INTO `lobby` (`lobbyID`, `winner`) VALUES
(130, 'Lou'),
(129, 'Geo'),
(128, 'Gebreyl'),
(127, 'Lenar'),
(126, 'Lou'),
(125, 'Mark');

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
CREATE TABLE IF NOT EXISTS `player` (
  `playerID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `lastLogin` date DEFAULT NULL,
  `forDeletion` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`playerID`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`playerID`, `name`, `password`, `lastLogin`, `forDeletion`) VALUES
(1, 'Marven', 'pass123', '2024-05-18', NULL),
(2, 'Mark', 'pass123', '2024-05-20', NULL),
(3, 'Lou', 'pass123', '2024-05-06', NULL),
(4, 'Lenar', 'pass123', '2024-05-16', NULL),
(5, 'Geo', 'pass123', '2024-05-19', NULL),
(6, 'Gebreyl', 'pass123', '2024-05-12', NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
