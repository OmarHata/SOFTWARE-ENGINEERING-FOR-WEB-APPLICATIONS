-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 28, 2022 at 01:03 AM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `organization_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `departments`
--

CREATE TABLE `departments` (
  `dep_id` int(11) NOT NULL,
  `dep_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `departments`
--

INSERT INTO `departments` (`dep_id`, `dep_name`) VALUES
(1, 'Human Resources Department'),
(2, 'Accounting Department'),
(3, 'Engineering Department'),
(4, 'Sales and Marketing Department');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `emp_id` int(11) NOT NULL,
  `emp_fname` varchar(255) NOT NULL,
  `emp_lname` varchar(255) NOT NULL,
  `emp_J_category` int(11) NOT NULL,
  `emp_J_title` varchar(255) NOT NULL,
  `emp_salary` float NOT NULL,
  `emp_rank` int(11) NOT NULL,
  `emp_dep` int(11) DEFAULT NULL,
  `login_username` varchar(255) NOT NULL,
  `login_password` varchar(255) NOT NULL,
  `emp_goals` varchar(255) NOT NULL,
  `emp_achievements` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`emp_id`, `emp_fname`, `emp_lname`, `emp_J_category`, `emp_J_title`, `emp_salary`, `emp_rank`, `emp_dep`, `login_username`, `login_password`, `emp_goals`, `emp_achievements`) VALUES
(1, 'ahmad', 'ahmad', 3, 'Officers', 538.5, 3, 2, 'ahmad', '1234', 'rthretyhrtyhrtyhrtyhr6th', 'sregwergw453rtg'),
(2, 'ahmad 2', 'ahmad 2', 3, 'Officers', 650, 3, 1, '', '', '', ''),
(3, 'bbb', 'bbbb', 2, 'HR Managers', 1500, 3, 1, 'a', 'a', '', ''),
(4, 'moham', 'moham', 1, 'HR Director', 2000, 3, 1, 'm', 'm', '', ''),
(5, 'moh', 'hasan', 3, 'General accountants', 800, 3, 2, '', '', '', ''),
(6, 'faisal', 'faisal', 2, 'Accounting Manager', 1250, 3, 2, '', '', '', ''),
(7, 'sami', 'sami', 1, 'Accounting Director', 2550, 3, 2, 's', 's', '', ''),
(8, 'ahmad 3', 'ahmad 3', 3, 'Engineer', 950, 3, 3, '', '', '', ''),
(9, 'kamel', 'kamel', 2, 'Engineering Manager', 2650, 3, 3, '', '', '', ''),
(10, 'lama', 'hasan', 3, 'Sales & Marketing Engineer', 350, 3, 4, '', '', '', ''),
(11, 'nor', 'ali', 2, 'S&M Manager', 650, 3, 4, '', '', '', ''),
(12, 'yazan', 'yazan', 1, 'Engineering Director', 3600, 3, 3, '', '', '', ''),
(13, 'yassmen', 'yassmen', 1, 'S&M Director', 1000, 3, 4, '', '', '', ''),
(14, 'hasan', 'hasan', 3, 'Officers', 980, 3, 1, 'h', 'h', '', ''),
(15, 'ddd', 'ddd', 3, 'Officers', 400, 3, 1, 'g', 'g', '', ''),
(16, 'rtyrty', 'ertert', 3, 'Officers', 555, 3, 1, 'gg', 'g', '', ''),
(17, 'asdasd', 'asdasd', 4, 'Vice President', 3000, 3, 1, 'd', 'd', '', ''),
(18, 'wwww', 'wwww', 4, 'Vice President For The Engineering and Sales & Marketing departments', 3000, 3, 3, 'e', 'e', '', ''),
(19, 'qqq', 'qqq', 5, 'President', 5000, 3, 1, 'p', 'p', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `employeecategory`
--

CREATE TABLE `employeecategory` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employeecategory`
--

INSERT INTO `employeecategory` (`category_id`, `category_name`) VALUES
(1, 'Director'),
(2, 'Manager'),
(3, 'Professional Employee'),
(4, 'Vice President'),
(5, 'President');

-- --------------------------------------------------------

--
-- Table structure for table `evaluation`
--

CREATE TABLE `evaluation` (
  `id` int(11) NOT NULL,
  `emp_id` int(11) NOT NULL,
  `evaluation_date` date NOT NULL,
  `evaluation` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `evaluation`
--

INSERT INTO `evaluation` (`id`, `emp_id`, `evaluation_date`, `evaluation`) VALUES
(1, 2, '2022-12-20', '66'),
(2, 2, '2022-12-20', '77'),
(3, 2, '2022-12-20', '77'),
(4, 2, '2022-12-20', '77'),
(5, 2, '2022-12-20', '77'),
(6, 2, '2022-12-20', '77'),
(7, 2, '2022-12-20', '77'),
(8, 1, '2022-12-20', '77');

-- --------------------------------------------------------

--
-- Table structure for table `promotion_req`
--

CREATE TABLE `promotion_req` (
  `id` int(11) NOT NULL,
  `emp_id` int(11) NOT NULL,
  `old_rank` int(11) NOT NULL,
  `new_rank` int(11) DEFAULT NULL,
  `manager_approve` varchar(255) DEFAULT NULL,
  `director_approve` varchar(11) DEFAULT NULL,
  `vp_approve` varchar(11) DEFAULT NULL,
  `presedient_approve` varchar(11) DEFAULT NULL,
  `approve_status` varchar(255) DEFAULT NULL,
  `dep_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `promotion_req`
--

INSERT INTO `promotion_req` (`id`, `emp_id`, `old_rank`, `new_rank`, `manager_approve`, `director_approve`, `vp_approve`, `presedient_approve`, `approve_status`, `dep_id`) VALUES
(16, 3, 3, NULL, 'reject', NULL, NULL, NULL, 'In Declined', 1);

-- --------------------------------------------------------

--
-- Table structure for table `transferred_req`
--

CREATE TABLE `transferred_req` (
  `id` int(11) NOT NULL,
  `manager_answer` varchar(11) DEFAULT NULL,
  `director_one_answer` varchar(11) DEFAULT NULL,
  `director_two_answer` varchar(11) DEFAULT NULL,
  `vp_answer` varchar(11) DEFAULT NULL,
  `transfeerd_status` varchar(11) NOT NULL,
  `from_dp_id` int(11) NOT NULL,
  `to_dep_id` int(11) NOT NULL,
  `emp_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transferred_req`
--

INSERT INTO `transferred_req` (`id`, `manager_answer`, `director_one_answer`, `director_two_answer`, `vp_answer`, `transfeerd_status`, `from_dp_id`, `to_dep_id`, `emp_id`) VALUES
(1, 'accept', 'accept', 'accept', 'accept', 'Approved', 1, 2, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `departments`
--
ALTER TABLE `departments`
  ADD PRIMARY KEY (`dep_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`emp_id`),
  ADD KEY `emp_cat_const` (`emp_J_category`),
  ADD KEY `emp_dep_const` (`emp_dep`);

--
-- Indexes for table `employeecategory`
--
ALTER TABLE `employeecategory`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `evaluation`
--
ALTER TABLE `evaluation`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `promotion_req`
--
ALTER TABLE `promotion_req`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transferred_req`
--
ALTER TABLE `transferred_req`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `departments`
--
ALTER TABLE `departments`
  MODIFY `dep_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `emp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `employeecategory`
--
ALTER TABLE `employeecategory`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `evaluation`
--
ALTER TABLE `evaluation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `promotion_req`
--
ALTER TABLE `promotion_req`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `transferred_req`
--
ALTER TABLE `transferred_req`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `emp_cat_const` FOREIGN KEY (`emp_J_category`) REFERENCES `employeecategory` (`category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `emp_dep_const` FOREIGN KEY (`emp_dep`) REFERENCES `departments` (`dep_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
