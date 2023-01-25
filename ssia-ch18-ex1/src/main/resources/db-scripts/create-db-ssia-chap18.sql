#Reset users
DROP USER IF EXISTS 'dbadmin'@'localhost';
DROP USER IF EXISTS 'dbadmin'@'%';
DROP USER IF EXISTS 'dbuser'@'localhost';
DROP USER IF EXISTS 'dbuser'@'%';

#Drop and Create DB
DROP DATABASE IF EXISTS spring;
CREATE DATABASE IF NOT EXISTS spring CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `dbadmin`@`%` IDENTIFIED WITH mysql_native_password BY 'dbadmin';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, event, TRIGGER ON `spring`.* TO `dbadmin`@`%`;
CREATE USER IF NOT EXISTS `dbuser`@`%` IDENTIFIED WITH mysql_native_password by 'dbuser';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `spring`.* TO `dbuser`@`%`;
FLUSH PRIVILEGES;
