-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 22, 2024 at 12:08 PM
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `gamedetails`
--

INSERT INTO `gamedetails` (`playerID`, `lobbyID`, `totalPoints`) VALUES
(1, 101, 50),
(2, 102, 75);

-- --------------------------------------------------------

--
-- Table structure for table `leaderboards`
--

DROP TABLE IF EXISTS `leaderboards`;
CREATE TABLE IF NOT EXISTS `leaderboards` (
  `leaderBoardID` int NOT NULL,
  `userID` int DEFAULT NULL,
  `totalPoints` int DEFAULT NULL,
  PRIMARY KEY (`leaderBoardID`),
  KEY `userID` (`userID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `leaderboards`
--

INSERT INTO `leaderboards` (`leaderBoardID`, `userID`, `totalPoints`) VALUES
(1, 1, 100),
(2, 2, 150);

-- --------------------------------------------------------

--
-- Table structure for table `lobby`
--

DROP TABLE IF EXISTS `lobby`;
CREATE TABLE IF NOT EXISTS `lobby` (
  `lobbyID` int NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `winner` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`lobbyID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `lobby`
--

INSERT INTO `lobby` (`lobbyID`, `createdBy`, `winner`) VALUES
(101, 'Marven', 'Luis'),
(102, 'Luis', 'Marven');

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
CREATE TABLE IF NOT EXISTS `player` (
  `playerID` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `lastLogin` date DEFAULT NULL,
  PRIMARY KEY (`playerID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`playerID`, `name`, `password`, `lastLogin`) VALUES
(1, 'Marven', 'password123', '2024-04-21'),
(2, 'Luis', 'password456', '2024-04-20');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
