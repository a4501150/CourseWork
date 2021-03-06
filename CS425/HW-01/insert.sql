/* Since the foreign keys exist in tables, first insert the the tables without
fks
*/

/*TYPE*/
INSERT INTO TYPE VALUES ('Craft','Knitting, sewing, ect');
INSERT INTO TYPE VALUES ('Art','Paining, Sculpting, ect');
INSERT INTO TYPE VALUES ('Exercise','Any courses having to do with physical activity');
INSERT INTO TYPE VALUES ('Languages','Anything to do with writing, literature, or communication');
INSERT INTO TYPE VALUES ('Kids','Courses geared towards children 13 and younger');

/* FAMILYPACKAGE */
INSERT INTO FAMILYPACKAGE VALUES (1,'23 Beacon St. Hillside IL','708-555-9384');
INSERT INTO FAMILYPACKAGE VALUES (2,'4930 Dickens Ave Chicago IL','312-555-9403');
INSERT INTO FAMILYPACKAGE VALUES (3,'345 Fullerton St. Chicago IL','773-555-0032');
INSERT INTO FAMILYPACKAGE VALUES (4,'34 Maple Ln Elmhurst IL','312-555-9382');
INSERT INTO FAMILYPACKAGE VALUES (5,'563 Harvard Ave Lisle IL','630-555-9321');

/*RecCenterMember */
INSERT INTO RECCENTERMEMBER VALUES (1,'Abby','Smith',to_date('05/21/1983','MM/DD/YYYY'),1);
INSERT INTO RECCENTERMEMBER VALUES (2,'Mike','O’Shea',to_date('7/4/1968','MM/DD/YYYY'),2);
INSERT INTO RECCENTERMEMBER VALUES (3,'April','O’Shea',to_date('6/23/1954','MM/DD/YYYY'),2);
INSERT INTO RECCENTERMEMBER VALUES (4,'Vjay',' Gupta',to_date('8/1/1945','MM/DD/YYYY'),null);
INSERT INTO RECCENTERMEMBER VALUES (5,'Lisa','Tang',to_date('11/5/2000','MM/DD/YYYY'),3);
INSERT INTO RECCENTERMEMBER VALUES (6,'Harry','Smith',to_date('2/3/1972','MM/DD/YYYY'),null);
INSERT INTO RECCENTERMEMBER VALUES (7,'Justin','Smith',to_date('2/2/1983','MM/DD/YYYY'),1);
INSERT INTO RECCENTERMEMBER VALUES (8,'Lisa','Brown',to_date('12/28/1959','MM/DD/YYYY'),null);
INSERT INTO RECCENTERMEMBER VALUES (9,'Harry','Tang',to_date('4/3/1948','MM/DD/YYYY'),3);
INSERT INTO RECCENTERMEMBER VALUES (10,'DongMei','Tang',to_date('6/2/1942','MM/DD/YYYY'),3);
INSERT INTO RECCENTERMEMBER VALUES (11,'Laura','Dickinson',to_date('11/11/1998','MM/DD/YYYY'),null);
INSERT INTO RECCENTERMEMBER VALUES (12,'Victor','Garcia',to_date('4/5/2006','MM/DD/YYYY'),5);
INSERT INTO RECCENTERMEMBER VALUES (13,'Emily','Citrin',to_date('5/4/1993','MM/DD/YYYY'),null);
INSERT INTO RECCENTERMEMBER VALUES (14,'Maria','Garcia',to_date('7/7/2007','MM/DD/YYYY'),5);
INSERT INTO RECCENTERMEMBER VALUES (15,'Cassie','O’Shea',to_date('6/2/1988','MM/DD/YYYY'),2);
INSERT INTO RECCENTERMEMBER VALUES (16,'Cassandra','McDonald',to_date('7/1/1990','MM/DD/YYYY'),null);
INSERT INTO RECCENTERMEMBER VALUES (17,'Jessie','Knapp',to_date('9/12/1981','MM/DD/YYYY'),4);
INSERT INTO RECCENTERMEMBER VALUES (18,'Monica','Knapp',to_date('9/17/1982','MM/DD/YYYY'),4);
INSERT INTO RECCENTERMEMBER VALUES (19,'Leslie','Blackburn',to_date('1/19/1986','MM/DD/YYYY'),null);
INSERT INTO RECCENTERMEMBER VALUES (20,'Sandra','Svoboda',to_date('9/9/1999','MM/DD/YYYY'),null);

/*Instructor table */
INSERT INTO INSTRUCTOR VALUES (1,'Annie','Heard',null);
INSERT INTO INSTRUCTOR VALUES (2,'Monica','Knapp',18);
INSERT INTO INSTRUCTOR VALUES (3,'James','Robertson',null);
INSERT INTO INSTRUCTOR VALUES (4,'April','O’Shea',3);
INSERT INTO INSTRUCTOR VALUES (5,'Harry','Tang',9);

/*Class Table */
INSERT INTO CLASS VALUES(1,'Needle points','Craft',2,'Spring',2010);
INSERT INTO CLASS VALUES(2,'Photography','Art',1,'Fall',2008);
INSERT INTO CLASS VALUES(3,'Woodworking','Craft',4,'Spring',2009);
INSERT INTO CLASS VALUES(4,'Chinese (Intro.)','Languages',1,'Winter',2008);
INSERT INTO CLASS VALUES(5,'Team games','Kids',1,'Summer',2008);
INSERT INTO CLASS VALUES(6,'Yoga (Intro.)','Exercise',2,'Fall',2009);
INSERT INTO CLASS VALUES(7,'Origami (Adv.)','Craft',4,'Fall',2009);
INSERT INTO CLASS VALUES(8,'Oil painting','Art',3,'Spring',2009);
INSERT INTO CLASS VALUES(9,'Yoga (Adv.)','Exercise',1,'Spring',2008);
INSERT INTO CLASS VALUES(10,'Chinese (Intro.)','Exercise',3,'Spring',2009);

/*ENROLLMENT TABLE*/

INSERT INTO ENROLLMENT VALUES(3,3,20);
INSERT INTO ENROLLMENT VALUES(1,9,15);
INSERT INTO ENROLLMENT VALUES(2,9,20);
INSERT INTO ENROLLMENT VALUES(4,10,30);
INSERT INTO ENROLLMENT VALUES(3,10,10);
INSERT INTO ENROLLMENT VALUES(5,5,10);
INSERT INTO ENROLLMENT VALUES(4,9,30);
INSERT INTO ENROLLMENT VALUES(1,11,25);
INSERT INTO ENROLLMENT VALUES(2,19,40);
INSERT INTO ENROLLMENT VALUES(7,14,10);
INSERT INTO ENROLLMENT VALUES(8,12,5);
INSERT INTO ENROLLMENT VALUES(1,1,30);
INSERT INTO ENROLLMENT VALUES(6,1,15);
INSERT INTO ENROLLMENT VALUES(9,1,20);
INSERT INTO ENROLLMENT VALUES(8,1,25);
INSERT INTO ENROLLMENT VALUES(1,13,18);
INSERT INTO ENROLLMENT VALUES(2,20,9);
INSERT INTO ENROLLMENT VALUES(10,4,15);
INSERT INTO ENROLLMENT VALUES(1,2,3);










