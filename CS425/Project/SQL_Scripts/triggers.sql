CREATE OR REPLACE TRIGGER rewviewRewards
  AFTER INSERT OR UPDATE ON review
  referencing new as new old as old
  FOR EACH ROW
DECLARE

  x int;
BEGIN
  
  UPDATE REGULARMEMBER SET POINTS = POINTS + 3 where ID = :NEW.AUTHORUSERID;
  
  x := upStatus(:NEW.AUTHORUSERID);
  
END;

/

CREATE OR REPLACE TRIGGER threadRewards
  AFTER INSERT OR UPDATE ON THREAD
  referencing new as new old as old
  FOR EACH ROW
DECLARE

  x int;
BEGIN
  
  IF INSERTING THEN
  
  IF (:NEW.PARENTTHREADID IS NOT NULL) THEN
  UPDATE REGULARMEMBER SET POINTS = POINTS + 3 where ID = :NEW.AUTHORUSERID;
  END IF;
  
  IF (:NEW.PARENTTHREADID IS NULL) THEN
  UPDATE REGULARMEMBER SET POINTS = POINTS + 5 where ID = :NEW.AUTHORUSERID;
  END IF;
  
  END IF;
  x := upStatus(:NEW.AUTHORUSERID);
  
END;


/

CREATE OR REPLACE FUNCTION upStatus (memberID int) RETURN INT
IS

credits int;
s int;
BEGIN
  select points into credits from regularmember where id = memberID;
  select statusID into s from regularmember where id = memberID;
      if(credits>10 AND s < 1) THEN
  UPDATE REGULARMEMBER SET statusID = 1 where ID = memberID;
  end if;
  
  if(credits>20 AND S<2 ) THEN
  UPDATE REGULARMEMBER SET statusID = 2 where ID = memberID;
  end if;
  
  if(credits>30 AND S<3) THEN
  UPDATE REGULARMEMBER SET statusID = 3 where ID = memberID;
  end if;
  
  if(credits>40 AND S<4) THEN
  UPDATE REGULARMEMBER SET statusID = 4 where ID = memberID;
  end if;
  
  if(credits>50 AND S<5) THEN
  UPDATE REGULARMEMBER SET statusID = 5 where ID = memberID;
  end if;
  
  return 0;
END;

/

CREATE OR REPLACE TRIGGER ALERTSTOMEMBER
  AFTER  UPDATE OF STATUSID ON REGULARMEMBER
  FOR EACH ROW
DECLARE 
 MAXid int;
 Status varchar(20);
BEGIN

  SELECT MAX(ID) INTO MAXid FROM ALERTS;
  IF (MAXid IS NULL) THEN
  Maxid := 0;
  end if;
  SELECT STATUSNAME INTO Status FROM STATUS_POINTS_RATE WHERE ID = :NEW.STATUSID;
  MAXid := Maxid + 1;
  INSERT INTO ALERTS VALUES (Maxid, 'your status was changed to '||Status,:NEW.ID,NULL);
END;

/
CREATE OR REPLACE VIEW Q3
AS
SELECT COUNT(ID) AS NUM,PARENTTHREADID  pid FROM THREAD WHERE PARENTTHREADID IN (SELECT ID FROM THREAD WHERE PARENTTHREADID IS NULL) GROUP BY PARENTTHREADID;

/
CREATE OR REPLACE VIEW Q5
AS 
SELECT COUNT(ID) as num,AuthoruserID AS Userid FROM review GROUP BY authoruserid;
/
CREATE OR REPLACE VIEW Q5B
AS 
SELECT COUNT(ID) AS NUM, AUTHORUSERID AS USERID FROM THREAD WHERE PARENTTHREADID IS NOT NULL GROUP BY AUTHORUSERID;
/
CREATE OR REPLACE VIEW Q6
AS 
SELECT COUNT(ID) AS NUM, theatreid AS tid FROM screenroom GROUP BY theatreid;
/
CREATE OR REPLACE VIEW Q7
AS 
SELECT COUNT(ID) AS NUM, screenroomid AS sid FROM orders GROUP BY screenroomid ;
/
CREATE OR REPLACE function Q8 (staffid int) return int
is

mondayNum int;
ST date;
ET date;
begin

SELECT STARTTIME INTO ST FROM SCHEDULE WHERE WORKERID = STAFFID;
SELECT ENDTIME INTO ET FROM SCHEDULE WHERE WORKERID = STAFFID;

 with params as (select ST start_date, ET end_date from dual), -- mimicking parameters
   date_tab as (select start_date + level - 1 dt
                from   params
                connect by level <= end_date - start_date + 1)
select count(*) into mondayNum
from   date_tab
where  dt = trunc(dt, 'iw');
return mondayNum;
end;
