/* Author : Jinyang Li

content:
Create Tables and Constrains */

/*
DROP TABLE CLASS;
DROP TABLE ENROLLMENT;
DROP TABLE FAMILYPACKAGE;
DROP TABLE INSTRUCTOR;
DROP TABLE RECCENTERMEMBER;
DROP TABLE TYPE;
*/

/*
By default, if we do not declare a key with 'NOT NULL', it will automatically setup
implicit 'NULL' constraint.
*/
CREATE TABLE RecCenterMember 
(
ID Int not null PRIMARY KEY,
F_name VARCHAR(20) NOT NULL,
L_name VARCHAR(20) NOT NULL,
Dob DATE NOT NULL,

Family_id INT NULL

);


CREATE TABLE Class
(
ID Int not null PRIMARY KEY,
Title VARCHAR(20) NOT NULL,
Type VARCHAR(20) NOT NULL,
Instructor Int NOT NULL,
season varchar(20) not null CHECK (season IN ('Spring','Summer','Fall','Winter')),
Year INT NOT NULL
);

CREATE TABLE Instructor
(
ID Int not null PRIMARY KEY ,
F_name VARCHAR(20) NOT NULL,
L_name VARCHAR(20) NOT NULL,
Member_id int null
);

CREATE TABLE Type
(
type VARCHAR(20) not null PRIMARY KEY,
description varchar(100) NOT NULL
);

CREATE TABLE FamilyPackage
(
id INT NOT NULL PRIMARY KEY,
address varchar(100) NOT NULL,  
phone varchar(20) NOT NULL UNIQUE
);

CREATE TABLE Enrollment
(
Class_id INT NOT NULL,
Member_id INT NOT NULL,
cost int NOT NULL,
PRIMARY KEY(CLASS_ID, MEMBER_ID)
);

/*
Add foreign key(s) to tables 

Since I havn't modify the order of tables, the foreign keys cannot be 
setup IN-LINE if there is no reference keys exist in the referece tables. 

However, after the tables established, the fks can setup without any troubles.
*/

alter table RecCenterMember
ADD CONSTRAINT FK_TOFAMILY
FOREIGN KEY (FAMILY_ID)
REFERENCES FamilyPackage(ID);

alter table class
ADD CONSTRAINT FK_TOTYPE
FOREIGN KEY (TYPE)
REFERENCES Type(type);

alter table class
ADD CONSTRAINT FK_TOINSTRUCTOR
FOREIGN KEY (instructor)
REFERENCES INSTRUCTOR (ID);

ALTER TABLE INSTRUCTOR 
ADD CONSTRAINT FK_INTORCM
FOREIGN KEY (MEMBER_ID)
REFERENCES RECCENTERMEMBER(ID);

ALTER TABLE ENROLLMENT
ADD CONSTRAINT FK_TOCLASS
FOREIGN KEY (CLASS_ID)
REFERENCES CLASS(ID);

ALTER TABLE ENROLLMENT
ADD CONSTRAINT FK_TORCM
FOREIGN KEY (MEMBER_ID)
REFERENCES RECCENTERMEMBER(ID);





