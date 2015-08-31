CREATE TABLE IF NOT EXISTS `Branch` (
  `branch_id` INTEGER(10) NOT NULL AUTO_INCREMENT,
  `branch_Name` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`branch_id`)
);

CREATE TABLE IF NOT EXISTS `Customer` (
  `customer_id` INTEGER(10) NOT NULL AUTO_INCREMENT,
  `first_Name` VARCHAR(25) NOT NULL,
  `surname` VARCHAR(25) NOT NULL,
  `house_number` VARCHAR(25) NOT NULL,
  `street_name` VARCHAR(120) NOT NULL,
  `city` VARCHAR(25) NOT NULL,
  `country` VARCHAR(25) NOT NULL,
  `eir_code` VARCHAR(25) NOT NULL,
  `branch_id` INTEGER(10)   NOT NULL,
  PRIMARY KEY (`customer_id`),
  KEY `FK_CUST_BRANCH` (`branch_id`),
  CONSTRAINT `FK_CUST_BRANCH` FOREIGN KEY (`branch_id`) REFERENCES `Branch` (`branch_id`)
);

CREATE TABLE IF NOT EXISTS `Account` (
  `account_id` INTEGER(10) NOT NULL AUTO_INCREMENT,
  `account_type` VARCHAR(25) NOT NULL,
  `interest_rate` DOUBLE NOT NULL,
  `account_balance` DOUBLE NOT NULL,
  `customer_id` INTEGER(10)   NOT NULL,
  PRIMARY KEY (`account_id`),
  KEY `FK_ACC_CUST` (`customer_id`),
  CONSTRAINT `FK_ACC_CUST` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`customer_id`)
);

INSERT INTO `Branch` (`branch_id`, `branch_Name`) VALUES
  (1, 'Carlow'),
  (2, 'Clane'),
  (3, 'Dublin');

INSERT INTO `Customer` (`customer_id`, `first_Name`, `surname`, `house_number`, `street_name`, `city`, `country`, `eir_code`, `branch_id`) VALUES
  (1, 'Niall', 'Kelly', '32', 'The Avenue', 'Clane', 'Ireland', '123KE', 2),
  (2, 'Aileen', 'Kane', '32', 'The Avenue', 'Clane', 'Ireland', '123KE', 2),
  (3, 'Joe', 'Schmidt', '4', 'Lansdowne Road', 'Dublin', 'Ireland', '8574DUB', 3),
  (4, 'Martin', 'O Neill', '16', 'Aviva Stadium', 'Dublin', 'Ireland', '4547DUB', 3),
  (5, 'Paul', 'Byrne', '14', 'Kernanstown', 'Carlow', 'Ireland', '123CW', 1);

INSERT INTO `Account` (`account_id`, `account_type`, `interest_rate`, `account_balance`, `customer_id`) VALUES
  (1, 'Current', 2.4, 1256.42, 1),
  (2, 'Savings', 5.1, 100510.63, 1),
  (3, 'Investment', 7.3, 56000, 1),
  (4, 'Current', 2.21, 36.20, 2),
  (5, 'Savings', 3.76, 4523.64, 2),
  (6, 'Investment', 7.66, 9885.23, 2),
  (7, 'Current', 1.05, 766.54, 3),
  (8, 'Current', 1.8, 5456.29, 4),
  (9, 'Savings', 5.1, 11236.54, 4),
  (10, 'Savings', 5.1, 200156.33, 5),
  (11, 'Current', 3.2, 7500, 5);