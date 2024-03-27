create schema `gostop`;

create table `gostop`.`ACCOUNT`
(
   `ID`       INT PRIMARY KEY AUTO_INCREMENT,
    `EMAIL`    varchar(255) null,
    `NICKNAME` varchar(255) null,
    `PASSWORD` varchar(255) null
);