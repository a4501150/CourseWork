/* Author : Jinyang Li

content:
Create Tables and Constrains */

/*
By default, if we do not declare a key with 'NOT NULL', it will automatically setup
implicit 'NULL' constraint.
*/

CREATE TABLE Theatre 
(
ID Int not null PRIMARY KEY,
name VARCHAR(20) NOT NULL,
location VARCHAR(20) NOT NULL
);

CREATE TABLE MOVIE
(
ID INT NOT NULL PRIMARY KEY,
title VARCHAR(50) NOT NULL,
DIRECTOR VARCHAR(200) NOT NULL,
STAR INT NULL,
DESCRPiTION VARCHAR(300) NOT NULL
);

CREATe TABLE SCREENROOM
(
id int not null primary key,
timeStart date not null,
timeEnd date not null,
theatreID int not null,
movieID int not null
);

create table movieType 
(
id int not null primary key,
typeName VARCHAR(20) not null,
description VARCHAR(200) not null
);

create table type_in 
(
id int not null primary key,
typeID int not null,
movieID int not null
);

create table review
(
id int not null primary key,
content varchar2(4000) not null,
likes int null,
authorUserID int not null,
theatreID int null,
movieID int null
);

create table member
(
id int not null primary key,
name varchar(20)not null,
phoneNumber int not null,
emailID varchar(30) not null,
creditCardNumber int not null,
isRegular char(1) not null check (isRegular in ('Y' , 'N'))
);
/* 1 for true & 0 for false */

create table regularMember
(
id int not null primary key,
userID varchar(50) not null unique,
password varchar(50) not null,
address varchar(200) not null,
creditCardExp varchar(10) not null,
points int null,
statusID int not null 
);

create table ORDERS
(
id int not null primary key,
memberID int not null,
screenRoomID int not null
);

create table thread
(
id int not null primary key,
parentThreadID int null,
title varchar(50) not null,
content varchar(2000) not null,
authorUserID int not null,
movieID int null,
theatreID int null
);

create table staff
(
id int not null primary key,
name varchar(20) not null,
phoneNumber int not null,
ssn int not null,
address varchar(50) not null,
typeID int null,
managerID int null
);

create table manager
(
id int not null primary key,
theatreID int not null,
staffID int not null
);

create table staffType
(
id int not null primary key,
typeName varchar(20) not null,
privilege varchar(20) not null,
description varchar(20) not null
);

create table schedule 
(
workerID int not null primary key,
startTime date not null,
endTime date not null,
theatreID int not null,
assignerID int not null
);

CREATE TABLE STATUS_POINTS_RATE
(
ID INT NOT NULL PRIMARY KEY,
STATUSNAME VARCHAR(20) NULL CHECK ( STATUSNAME IN ('SIVLER','GOLD','PLAT','DIAMOND','CHALLENGER') ) ,
RATE INT NOT NULL
);

CREATE TABLE ALERTS
(
ID INT NOT NULL PRIMARY KEY,
CONTENT VARCHAR2(100),
MEMBERID INT NULL,
STAFFID INT NULL
);

 

ALTER TABLE screenRoom
ADD CONSTRAINT FK_scTOT
FOREIGN KEY (theatreID)
REFERENCES Theatre(ID);

ALTER TABLE screenRoom
ADD CONSTRAINT FK_scTOM
FOREIGN KEY (movieID)
REFERENCES movie(ID);

ALTER TABLE type_in
ADD CONSTRAINT FK_tiTOMT
FOREIGN KEY (typeID)
REFERENCES MovieType(ID);

ALTER TABLE type_in
ADD CONSTRAINT FK_tiTOM
FOREIGN KEY (movieID)
REFERENCES movie(ID);

ALTER TABLE review
ADD CONSTRAINT FK_reTOMe
FOREIGN KEY (authorUserID)
REFERENCES member(ID);

ALTER TABLE review
ADD CONSTRAINT FK_reTOM
FOREIGN KEY (movieID)
REFERENCES movie(ID);

ALTER TABLE review
ADD CONSTRAINT FK_reTOt
FOREIGN KEY (theatreID)
REFERENCES theatre(ID);

ALTER TABLE regularMember
ADD CONSTRAINT FK_rmTOMe
FOREIGN KEY (id)
REFERENCES member(ID);

ALTER TABLE orders
ADD CONSTRAINT FK_odTOMe
FOREIGN KEY (memberID)
REFERENCES member(ID);

ALTER TABLE orders
ADD CONSTRAINT FK_ODTOsr
FOREIGN KEY (screenRoomID)
REFERENCES screenRoom(ID);

ALTER TABLE thread
ADD CONSTRAINT FK_THTOM
FOREIGN KEY (movieID)
REFERENCES movie(ID);

ALTER TABLE thread
ADD CONSTRAINT FK_THOT
FOREIGN KEY (theatreID)
REFERENCES theatre(ID);

ALTER TABLE thread
ADD CONSTRAINT FK_THTOMe
FOREIGN KEY (authorUserID)
REFERENCES regularMember(ID);

ALTER TABLE staff
ADD CONSTRAINT FK_STOTP
FOREIGN KEY (typeID)
REFERENCES staffTYPE(ID);

ALTER TABLE staff
ADD CONSTRAINT FK_STOMA
FOREIGN KEY (managerID)
REFERENCES manager(ID);

ALTER TABLE manager
ADD CONSTRAINT FK_MAOT
FOREIGN KEY (theatreID)
REFERENCES theatre(ID);

ALTER TABLE manager
ADD CONSTRAINT FK_MAOS
FOREIGN KEY (staffID)
REFERENCES staff(ID);

ALTER TABLE schedule
ADD CONSTRAINT FK_SOW
FOREIGN KEY (workerID)
REFERENCES staff(ID);

ALTER TABLE schedule
ADD CONSTRAINT FK_SOT
FOREIGN KEY (theatreID)
REFERENCES theatre(ID);

ALTER TABLE schedule
ADD CONSTRAINT FK_SOA
FOREIGN KEY (assignerID)
REFERENCES manager(ID);

ALTER TABLE REGULARMEMBER
ADD CONSTRAINT FK_RMTOSPAR
FOREIGN KEY (STATUSID)
REFERENCES STATUS_POINTS_RATE(ID);









