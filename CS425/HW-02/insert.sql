
INSERT INTO Theatre VALUES (1,'ACM','23 Beacon St. ');
INSERT INTO Theatre VALUES (2,'LMK','4930 Dickens Ave');
INSERT INTO Theatre VALUES (3,'HOPE','34 Maple Ln Elmhurst');

INSERT INTO MOVIE VALUES (1,'The Godfather',' Francis Ford Coppola',2,'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.');
INSERT INTO MOVIE VALUES (2,'12 Angry Men',' Sidney Lumet',1,'A dissenting juror in a murder trial slowly manages to convince the others that the case is not as obviously clear as it seemed in court.');
INSERT INTO MOVIE VALUES (3,'Schindler''s List',' Steven Spielberg',3,'In Poland during World War II, Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.');


INSERT INTO SCREENROOM VALUES (1,TO_DATE('10-10-2015 08:10','MM-DD-YYYY HH24:MI'),TO_DATE('10-10-2002 10:10','MM-DD-YYYY HH24:MI'),1,1);
INSERT INTO SCREENROOM VALUES (2,TO_DATE('10-12-2015 18:20','MM-DD-YYYY HH24:MI'),TO_DATE('10-12-2015 20:10','MM-DD-YYYY HH24:MI'),2,3);
INSERT INTO SCREENROOM VALUES (3,TO_DATE('10-13-2015 22:10','MM-DD-YYYY HH24:MI'),TO_DATE('10-13-2015 23:55','MM-DD-YYYY HH24:MI'),3,2);

INSERT INTO movieType VALUES (1,'Action','Action film is a film genre in which one or more heroes are thrust into a series of challenges');
INSERT INTO movieType VALUES (2,'Comedy','Comedy is a genre of film in which the main emphasis is on humour. ');
INSERT INTO movieType VALUES (3,'Horror','Horror is a film genre seeking to elicit a negative emotional reaction from viewers by playing on the audience''s primal fears. ');

INSERT INTO member VALUES (1,'KETTY','2133146677','KWITE@GMAIL.COM',001,'Y');
INSERT INTO member VALUES (2,'KOREL','4355603314','0152@GMAIL.COM',002,'N');
INSERT INTO member VALUES (3,'KIRS','1365480789','HELWORLD@GMAIL.COM',003,'Y');

INSERT INTO type_in VALUES (1,2,3);
INSERT INTO type_in VALUES (2,1,2);
INSERT INTO type_in VALUES (3,3,1);

INSERT INTO review VALUES (1,'Many movies come out each year and we applaud them for their screen play, orginality and whatever else we can say about a movie. But only once in a long while does one come out and you say all those nice things, but one you will also never forget. This movie is more than just something for us to watch for 3 hours and 17 minutes, it is something for us to never forget, to teach us a lesson and to remember ',1,2,3,1);
INSERT INTO review VALUES (2,'The first (and last) time I viewed this masterpiece in a theater, I could not help but notice the reaction of the audience upon their leaving. Some were crying or trying to hold back tears, some were stunned, some had completely blank faces, some showed signs of anger; but the overall atmosphere was one of utter silence. Never in the history of motion pictures has a film had such an emotional impact upon its constituents.',2,1,2,3);
INSERT INTO review VALUES (3,'THIS IS SOOOOOOO BORING',3,3,1,2);
INSERT INTO review VALUES (4,'THIS IS SsdsdsOOOO BORING',3,3,1,2);

iNSERT INTO STATUS_POINTS_RATE values (1,'SIVLER','10');
INSERT INTO STATUS_POINTS_RATE values (2,'GOLD','20');
INSERT INTO STATUS_POINTS_RATE values (3,'PLAT','30');
INSERT INTO STATUS_POINTS_RATE values (4,'DIAMOND','40');
INSERT INTO STATUS_POINTS_RATE values (5,'CHALLENGER','50');


INSERT INTO regularMember VALUES (1,'000000001','KSW20456','34 Maple Ln Elmhurst','09/28',3,1);
INSERT INTO regularMember VALUES (2,'000000002','123456','563 Harvard Ave','09/17',2,1);
INSERT INTO regularMember VALUES (3,'000000003','0098786','345 Fullerton St.','12/17',10,2);

INSERT INTO ORDERS VALUES(1,2,3);
INSERT INTO ORDERS VALUES(2,3,1);
INSERT INTO ORDERS VALUES(3,1,2);


INSERT INTO thread VALUES(1,null,'KING','KING KONG',2,null,3);
INSERT INTO thread VALUES(2,null,'THREAD','STAR WAR',1,2,null);
INSERT INTO thread VALUES(3,null,'MONY','PAPER MAN',3,null,1);


INSERT INTO staffType VALUES (1,'CLEANER','low','clean the world');
INSERT INTO staffType VALUES (2,'carshier','mid','collect money');
INSERT INTO staffType VALUES (3,'safety','mid','keep theatre safety');
INSERT INTO staffType VALUES (4,'manager','high','manager of theatre');
INSERT INTO staffType VALUES (5,'administrator','highest','web admin or owner');

INSERT INTO staff VALUES (1,'KPRER',2451427351,4572,'34 Maple Ln Elmhurst',1,null);
INSERT INTO staff VALUES (2,'LINDA',436273812,0183,'563 Harvard Ave',2,null);
INSERT INTO staff VALUES (3,'ALLEN',3455678901,0927,'345 Fullerton St',3,null);
INSERT INTO STAFF VALUES (4,'arron',23232323,0001,'345 S St',4,null);
INSERT INTO STAFF VALUES (5,'arron2',23232323,0002,'345 S St',5,null);
INSERT INTO STAFF VALUES (6,'arron3',23232323,0003,'345 S St',1,null);
INSERT INTO STAFF VALUES (7,'arron4',23232323,0004,'345 S St',2,null);
INSERT INTO STAFF VALUES (8,'arron5',23232323,0005,'345 S St',1,null);
INSERT INTO STAFF VALUES (9,'arron6',23232323,0006,'345 S St',2,null);
 
INSERT INTO manager VALUES (1,2,4);
INSERT INTO manager VALUES (2,3,6);



INSERT INTO schedule VALUES (1,TO_DATE('2000/11/02','YYYY/MM/DD'),TO_DATE('2015/11/12','YYYY/MM/DD'),1,1);
INSERT INTO schedule VALUES (2,TO_DATE('2013/11/13','YYYY/MM/DD'),TO_DATE('2015/11/11','YYYY/MM/DD'),2,1);
INSERT INTO schedule VALUES (3,TO_DATE('2013/10/01','YYYY/MM/DD'),TO_DATE('2015/11/12','YYYY/MM/DD'),3,2);







