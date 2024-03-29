DROP DATABASE IF EXISTS library;
CREATE DATABASE library;
USE library;

DROP TABLE IF EXISTS books;
CREATE TABLE books(
	id INT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(100) NOT NULL,
	author VARCHAR(100)
);

DROP TABLE IF EXISTS members;
CREATE TABLE members(
	id VARCHAR(9) PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS rents;
CREATE TABLE rents(
	uuid VARCHAR(150) PRIMARY KEY,
	id_book INT,
	id_member VARCHAR(9),
	beginning DATETIME NOT NULL,
	ending DATETIME NULL,

	FOREIGN KEY(id_book) REFERENCES books(id),
	FOREIGN KEY(id_member) REFERENCES members(id)
);