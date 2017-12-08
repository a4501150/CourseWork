/*Author: Jinyang Li */

/* q1 */
declare
type rcont is table of varchar2(2000);
reviewtable rcont;
in_moviename varchar2(100);

begin
in_moviename :='&movie_name'; --prompt user to in put name
DBMS_OUTPUT.put_line('the name u enter is: '||in_moviename);
/* order by to implement 'RECENT' review because recordes must be the newer, the greater id*/
select content bulk collect into reviewtable from review where review.movieid in (select movie.id from movie where movie.title like in_moviename) order by id desc;

for i in 1..reviewtable.count loop
DBMS_OUTPUT.put_line(reviewtable(i));
end loop;

end;
/* q2 */
declare

in_ varchar(20) := '&location';
cursor workername 
is 
select staff.name, schedule.starttime, schedule.endtime from schedule, staff 
where schedule.theatreID = (select theatre.id from theatre where theatre.location like in_)
AND staff.id = (select workerID from schedule where schedule.theatreID = 
(select theatre.id from theatre where theatre.location like in_));
   
   type wkn is table of staff.name%type;
   type wks is table of SCHEDULE.STARTTIME%type;
   type wke is table of SCHEDULE.ENDTIME%type;
   
   wkns wkn;
   wkss wks;
   wkes wke;

begin
OPEN workername;
loop
  fetch workername bulk collect into wkns, wkss, wkes;
  exit when workername%NOTFOUND;
  end loop;
close workername;  
for i in 1..wkns.count loop
DBMS_OUTPUT.put_line(wkns(i)||' '||wkss(i)||' '||wkes(i));
end loop;
  
end;
/* q3 */
declare 
in_ varchar(20) := '&Guest_Name';


pt regularmember.points%type;
st regularmember.status%type;

begin
select points into pt from regularmember where regularmember.id = (
select member.id from member where name like in_);

select status into st from regularmember where regularmember.id = (
select member.id from member where name like in_);

DBMS_OUTPUT.put_line(in_||' Credit points: '||pt||' Member status: '||st);

end;



