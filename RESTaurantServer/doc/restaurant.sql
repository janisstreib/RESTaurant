-- phpMyAdmin SQL Dump
-- version 4.5.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 04, 2015 at 12:52 PM
-- Server version: 5.6.25-4
-- PHP Version: 5.6.14-1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `restaurant`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `timeBegin` bigint(20) NOT NULL,
  `table` int(11) NOT NULL,
  `restaurant` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `restaurants`
--

CREATE TABLE `restaurants` (
  `id` int(11) NOT NULL,
  `name` varchar(200) COLLATE utf8_bin NOT NULL,
  `owner` varchar(200) COLLATE utf8_bin NOT NULL,
  `accessible` tinyint(1) NOT NULL,
  `eattype` enum('BADISCH','BBQ','CHINESISCH','INDISCH','TÜRKISCH') COLLATE utf8_bin NOT NULL,
  `parking_space` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `restaurants`
--

INSERT INTO `restaurants` (`id`, `name`, `owner`, `accessible`, `eattype`, `parking_space`) VALUES
(1, 'Maria Bar', 'Maria', 1, 'BBQ', 0),
(2, 'Zum Drachen', 'Herr Drache', 0, 'CHINESISCH', 5),
(3, 'Zum Löwen', 'Familie Löwe', 0, 'BADISCH', 10),
(4, 'Zum kleinen Riesen', 'Herr Riese', 1, 'TÜRKISCH', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tables`
--

CREATE TABLE `tables` (
  `id` int(11) NOT NULL,
  `seats` int(11) NOT NULL,
  `atwindow` tinyint(1) NOT NULL,
  `restaurant` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tables`
--

INSERT INTO `tables` (`id`, `seats`, `atwindow`, `restaurant`) VALUES
(7, 4, 1, 1),
(8, 12, 0, 1),
(9, 3, 1, 2),
(10, 8, 0, 2),
(11, 1, 1, 3),
(12, 2, 1, 3),
(13, 4, 0, 4),
(14, 3, 0, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `restaurants`
--
ALTER TABLE `restaurants`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tables`
--
ALTER TABLE `tables`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `restaurants`
--
ALTER TABLE `restaurants`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tables`
--
ALTER TABLE `tables`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
