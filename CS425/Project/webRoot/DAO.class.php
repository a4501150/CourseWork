<?php 

require_once('config/config.php');

  class DAO {
	  
	  public $con = null;
	  public $stmt = '';
	  
	  function __construct () {
		  
		  $this->con = oci_connect( DB_USER, DB_PASS, DB_HOST);
		  if (!$this->con) {
			$e = oci_error();   // For oci_connect errors do not pass a handle
			trigger_error(htmlentities($e['message']), E_USER_ERROR);
		  }
		  
		  $this->stmt = "very simple";
		  
	  }
	  
	  function __destruct () {
		  
		  //oci_close($this->con);
		  
	  }
	  
	function fetchMovieName($mID) {
		
		$stmt= oci_parse($this->con,"select TITLE from MOVIE WHERE ID = $mID");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		   foreach($result as $rows){
           echo "";
           foreach($rows as $col_values){
           return $col_values;   
           }
		   
		   }
		
		
	}
	
		function fetchTheatreName($tID) {
		
		$stmt= oci_parse($this->con,"select NAME from THEATRE WHERE ID = $tID");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		   foreach($result as $rows){
           echo "";
				foreach($rows as $col_values){
				return $col_values;   
				}
		   
           }
		
		
		}
	  
		function fetchPassWord($uID) {
		
		$stmt= oci_parse($this->con,'select PASSWORD from REGULARMEMBER WHERE USERID like \''.$uID.'\'');
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
					//echo $col_values;
				return $col_values;   
				}
		   
        }
		
		}
		
		function getLastMemberID () {
			
		$stmt= oci_parse($this->con,'select max(ID) from MEMBER');
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
		}
		
		function registerRegularMember($id, $userID, $userPass, $address, $cexp) {
			
			$query = "insert into REGULARMEMBER values (:id,:userID,:userPass, :address, :cexp, 0, 1)";  //sql
			$stid = oci_parse($this->con, $query);  //编译

			oci_bind_by_name($stid, ':id', $id);
			oci_bind_by_name($stid, ':userID', $userID);
			oci_bind_by_name($stid, ':userPass', $userPass);
			oci_bind_by_name($stid, ':address', $address);
			oci_bind_by_name($stid, ':cexp', $cexp);

			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
		}
		
	function registerMember($id, $name, $phone, $email, $Card, $isRegular) {
						
			$query = "insert into MEMBER values (:id,:name,:phone, :email, :Card, :isRegular)";  //sql
			$stid = oci_parse($this->con, $query);  //编译

			oci_bind_by_name($stid, ':id', $id);
			oci_bind_by_name($stid, ':name', $name);
			oci_bind_by_name($stid, ':phone', $phone);
			oci_bind_by_name($stid, ':email', $email);
			oci_bind_by_name($stid, ':Card', $Card);
			oci_bind_by_name($stid, ':isRegular', $isRegular);
			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
			
	}
		
	function fetchMovieDetail($mid) {
			
		$stmt= oci_parse($this->con,"select * from movie where id = $mid ");
	


		OCIDefineByName($stmt,"DIRECTOR",$DCT);

		OCIDefineByName($stmt,"STAR",$STAR);
	
		OCIDefineByName($stmt,"DESCRPITION",$DES);
	

	
		oci_execute($stmt,OCI_DEFAULT);
	
	
	
		while (oci_fetch($stmt)) {
			
		$str = 'DIRECTOR: &nbsp'.$DCT.'&nbsp type:'.$this->getMovieType($mid).'&nbsp STAR:'.$STAR.'&nbsp.<br><br>DETAIL: <br><br>'.$DES;
		echo wordwrap($str,150,"<br>\n",false);
    
		}
			
		}
		
		function getMovieType($mid) {
		
		$stmt= oci_parse($this->con,"select typename from movietype where id = (select typeID from type_in where movieID = $mid)");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
	  
		}
		
		function fetchReviewCount($mid) {
			
			
		$stmt= oci_parse($this->con,"select count(id) from review where MOVIEID = $mid");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
		}
		
		function fetchReview($mid) {
						
		$stmt= oci_parse($this->con,"select * from review where movieid = $mid order by id");

		OCI_define_by_name($stmt,"ID",$ID);
		
		OCI_define_by_name($stmt,"LIKES",$likes);
		
		OCI_define_by_name($stmt,"MOVIEID",$MOVIEID);

		OCI_define_by_name($stmt,"THEATREID",$THEATREID);
	
		OCI_define_by_name($stmt,"AUTHORUSERID",$userID);
		
		OCI_define_by_name($stmt,"CONTENT",$content);
		
		oci_execute($stmt,OCI_DEFAULT);
	
	
	
		while (oci_fetch($stmt)) {
		
		echo '-------------------------------------------------<br>';
		
		$str = 'Author : &nbsp'.$this->fetchUserName($userID).'&nbsp likes: '.$likes;
		
		$likeact = "<a href=\"likes.php?rID=$ID&act=add&mID=$mid\">".'&nbsp useful!'.'</a>';
		
		$dislikeact = "<a href=\"likes.php?rID=$ID&act=del&mID=$mid\">".'&nbsp useless!'.'</a>'.'<br><br>';
		
		echo $str.$likeact.$dislikeact;
		
		echo wordwrap($content,150,"<br>\n",false);
		
		echo '<br><br>';
    
		}
			
			
			
		}
		
		function fetchUserName($userID) {
			
		$stmt= oci_parse($this->con,"select name from Member where id = $userID");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
			
			
		}
		
		function addLikes($rID) {
			
			$query = "UPDATE REVIEW SET LIKES = :LIKES where id = $rID";  //sql
			$stid = oci_parse($this->con, $query);  //编译
			
			$likes = ($this->getLikes($rID)) + 1 ;
			
			oci_bind_by_name($stid, ':LIKES', $likes);

			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
			
		}
		
		function delLikes($rID){
			
			$query = "UPDATE REVIEW SET LIKES = :LIKES where id = $rID";  //sql
			$stid = oci_parse($this->con, $query);  //编译
			
			$likes = ($this->getLikes($rID)) - 1 ;
			
			oci_bind_by_name($stid, ':LIKES', $likes);

			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
			
		}
			
			
		
		
		function getLikes($rID) {
			
		$stmt= oci_parse($this->con,"select LIKES from review where id = $rID");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
			
		}
		
		function makeOrder($sID, $mID){
			
			$query = "insert into ORDERS values (:ID,:MEMBERID,:SCREENROOMID)";  //sql
			$stid = oci_parse($this->con, $query);  //编译

			$ID = ( $this->getLastOrderID() + 1 );
			
			oci_bind_by_name($stid, ':ID', $ID);
			oci_bind_by_name($stid, ':MEMBERID', $mID);
			oci_bind_by_name($stid, ':SCREENROOMID', $sID);


			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
			return $ID;
			
		}
		
		function getLastOrderID() {
			
		$stmt= oci_parse($this->con,'select max(ID) from ORDERS');
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
		}
		
		function fetchUserIDByUserName($username) {
			
		$stmt= oci_parse($this->con,'select ID from REGULARMEMBER WHERE USERID like \''.$username.'\'');
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
		}
		
		
		function insertRewview($content, $mid, $userName) {
			
			$query = "insert into REVIEW values (:ID,:CONTENT,:LIKES,:AUTHORUSERID,:THEATREID,:MOVIEID)";  //sql
			$stid = oci_parse($this->con, $query);  //编译

			$ID = ( $this->getLastReviewID() + 1 );
			
			$uID = $this->fetchUserIDByUserName($userName);
			
			$likes = 0;
			
			$tID = null;
			
			oci_bind_by_name($stid, ':ID', $ID);
			oci_bind_by_name($stid, ':CONTENT', $content);
			oci_bind_by_name($stid, ':LIKES', $likes);
			oci_bind_by_name($stid, ':AUTHORUSERID', $uID);
			oci_bind_by_name($stid, ':THEATREID', $tID);
			oci_bind_by_name($stid, ':MOVIEID', $mid);


			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
			return $ID;
			
		}
		
		function getLastReviewID(){
			
		$stmt= oci_parse($this->con,'select max(ID) from REVIEW');
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
		}
		
		function fetchUserPoint($ID){
			
		$stmt= oci_parse($this->con,"select POINTS from REGULARMEMBER where id = $ID");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
						
		}
		
		function fetchUserStatus($ID){
			
		$sid = $this->fetchUserStatusID($ID);	
			
		$stmt= oci_parse($this->con,"select STATUSNAME from STATUS_POINTS_RATE where id = $sid");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
						
		}
		
		function fetchUserStatusID($ID) {
		
		$stmt= oci_parse($this->con,"select STATUSID from REGULARMEMBER where id = $ID");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
		
		}
		//return scroomID array;
		function fetchUserOrders($ID) {
						
		$stmt= oci_parse($this->con,"select SCREENROOMID from ORDERS where MEMBERID = $ID");
		
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		return $result;
			
			
			
		}
		
		function fetchTheatreNameByScroomID($sID){
			
		$stmt= oci_parse($this->con,"select THEATREID from SCREENROOM where id = $sID");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $this->fetchTheatreName($col_values);   
				}
		   
        }
			
		}
		
		function fetchStartTime($sID) {
			
		$stmt= oci_parse($this->con,"select TO_CHAR(TIMESTART, 'MM-DD-YYYY HH24:MI') AS TS from SCREENROOM where id = $sID");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
			
			
		}
		
		function fetchRewiewIDbyUserName($username){
			
			$uid = $this -> fetchUserIDByUserName($username);
			
			$stmt= oci_parse($this->con,"select ID from REVIEW where AUTHORUSERID = $uid");
		
			oci_execute($stmt,OCI_DEFAULT);
			oci_fetch_all($stmt,$result);
		
			return $result;
			
			
		}

	  	function fetchThreadNameForMovie() {

		$stmt= oci_parse($this->con,"select * from THREAD WHERE movieID is not null AND PARENTTHREADID IS NULL");

		OCI_define_by_name($stmt,"ID",$ID);
		
		OCI_define_by_name($stmt,"TITLE",$TITLE);
		
		OCI_define_by_name($stmt,"CONTENT",$CONTENT);

		OCI_define_by_name($stmt,"AUTHORUSERID",$AUTHORUSERID);
	
		OCI_define_by_name($stmt,"MOVIEID",$MOVIEID);
		
		OCI_define_by_name($stmt,"THEATREID",$THEATREID);
		
		oci_execute($stmt,OCI_DEFAULT);
	
		echo '<table border=0>';
	
		while (oci_fetch($stmt)) {
		
		echo '<tr>';
		
		echo '<td>'."<a href=thread.php?tid=$ID&uid=$AUTHORUSERID&mid=$MOVIEID>".$TITLE."</a></td> <td>Author :".$this->fetchUserName($AUTHORUSERID).'</td><td> &nbsp About movie &nbsp'.$this->fetchMovieName($MOVIEID).'</td>';
		
		echo '</tr>';
		
		}

		echo '</table>';


		}
		
		function fetchThreadNameForTheatre() {

		$stmt= oci_parse($this->con,"select * from THREAD WHERE movieID is null AND PARENTTHREADID IS NULL");

		OCI_define_by_name($stmt,"ID",$ID);
		
		OCI_define_by_name($stmt,"TITLE",$TITLE);
		
		OCI_define_by_name($stmt,"CONTENT",$CONTENT);

		OCI_define_by_name($stmt,"AUTHORUSERID",$AUTHORUSERID);
	
		OCI_define_by_name($stmt,"MOVIEID",$MOVIEID);
		
		OCI_define_by_name($stmt,"THEATREID",$THEATREID);
		
		oci_execute($stmt,OCI_DEFAULT);
	
		echo '<table border=0>';
	
		while (oci_fetch($stmt)) {
		
		echo '<tr>';
		
		echo '<td>'."<a href=thread.php?tid=$ID&uid=$AUTHORUSERID&ttid=$THEATREID>".$TITLE."</a></td><td> Author :".$this->fetchUserName($AUTHORUSERID).'</td><td> &nbsp About THEATRE '.$this->fetchTheatreName($THEATREID).'</td>';
		
		echo '</tr>';
		
		}

		echo '</table>';


		}
		
		function fetchMovieIDs(){
			
			
			$stmt= oci_parse($this->con,"select ID from MOVIE");
		
			oci_execute($stmt,OCI_DEFAULT);
			oci_fetch_all($stmt,$result);
		
			return $result;
			
			
		}
		
		function fetchTheatreIDs(){
			
			
			$stmt= oci_parse($this->con,"select ID from THEATRE");
		
			oci_execute($stmt,OCI_DEFAULT);
			oci_fetch_all($stmt,$result);
		
			return $result;
			
			
		}
		
		function insertThreadTopic($title, $content, $uID, $mID, $tID) {
			
			
			
			$query = "insert into THREAD values (:ID,:PARENTTHREADID,:TITLE,:CONTENT,:AUTHORUSERID,:MOVIEID,:THEATREID)";  //sql
			$stid = oci_parse($this->con, $query);  //编译
			
			$ID = $this->fetchLastThreadID()+1;
			$PARENTTHREADID = NULL;
			
			oci_bind_by_name($stid, ':ID', $ID);
			oci_bind_by_name($stid, ':PARENTTHREADID', $PARENTTHREADID);
			oci_bind_by_name($stid, ':TITLE', $title);
			oci_bind_by_name($stid, ':CONTENT', $content);
			oci_bind_by_name($stid, ':AUTHORUSERID', $uID);
			oci_bind_by_name($stid, ':MOVIEID', $mID);
			oci_bind_by_name($stid, ':THEATREID', $tID);


			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
			//return $ID;
			
		}
		
		function fetchLastThreadID(){
			
		$stmt= oci_parse($this->con,"SELECT MAX(ID) FROM THREAD");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
		}
		
		function fetchThreadName($ID) {
			
		$stmt= oci_parse($this->con,"SELECT TITLE FROM THREAD WHERE ID = $ID");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
		}
		
		function fetchThreadContent($ID){
			
		$stmt= oci_parse($this->con,"SELECT CONTENT FROM THREAD WHERE ID = $ID");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
		}
		
		function fetchFollowUpIDs($pid){
			
			
			$stmt= oci_parse($this->con,"SELECT ID from thread where PARENTTHREADID =$pid");
		
			oci_execute($stmt,OCI_DEFAULT);
			oci_fetch_all($stmt,$result);
		
			return $result;
			
			
		}
		
		function fetchUserIDByThreadID($tid){
			
		$stmt= oci_parse($this->con,"SELECT AUTHORUSERID FROM THREAD WHERE ID = $tid");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
			
		}
		
		function insertFollowUp($PARENTTHREADID, $content, $uID, $mID, $tID) {
			
			$id = $this->fetchLastThreadID() + 1;
			$title = $this->fetchThreadName($PARENTTHREADID);
			
			$query = "insert into THREAD values (:ID,:PARENTTHREADID,:TITLE,:CONTENT,:AUTHORUSERID,:MOVIEID,:THEATREID)";  //sql
			$stid = oci_parse($this->con, $query);  //编译
			
			$ID = $this->fetchLastThreadID()+1;
			
			
			oci_bind_by_name($stid, ':ID', $ID);
			oci_bind_by_name($stid, ':PARENTTHREADID', $PARENTTHREADID);
			oci_bind_by_name($stid, ':TITLE', $title);
			oci_bind_by_name($stid, ':CONTENT', $content);
			oci_bind_by_name($stid, ':AUTHORUSERID', $uID);
			oci_bind_by_name($stid, ':MOVIEID', $mID);
			oci_bind_by_name($stid, ':THEATREID', $tID);


			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
			//return $ID;
			
			
		}
		
		function fetchTopicIDbyUserName($username){
			
			$uid = $this -> fetchUserIDByUserName($username);
			
			$stmt= oci_parse($this->con,"select ID from THREAD where AUTHORUSERID = $uid AND PARENTTHREADID IS NULL");
		
			oci_execute($stmt,OCI_DEFAULT);
			oci_fetch_all($stmt,$result);
		
			return $result;
			
			
		}
		
		function fetchFollowUpIDbyUserName($username){
			
			$uid = $this -> fetchUserIDByUserName($username);
			
			$stmt= oci_parse($this->con,"select ID from THREAD where AUTHORUSERID = $uid AND PARENTTHREADID IS NOT NULL");
		
			oci_execute($stmt,OCI_DEFAULT);
			oci_fetch_all($stmt,$result);
		
			return $result;
			
			
		}
		
		function fetchMovieIDbyReviewID ($id) {
			
		$stmt= oci_parse($this->con,"SELECT MOVIEID FROM REVIEW WHERE ID = $id");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
			
		}
		
		function fetchParentThreadID($id) {
			
		$stmt= oci_parse($this->con,"SELECT PARENTTHREADID FROM THREAD WHERE ID = $id");
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
			
			
		}
		
		function fetchUserAlerts($id) {
			
			$stmt= oci_parse($this->con,"select CONTENT from ALERTS where MEMBERID = $id");
		
			oci_execute($stmt,OCI_DEFAULT);
			oci_fetch_all($stmt,$result);
		
			return $result;
			
			
		}
		
		function fetchStaffIDbySSN($SSN){
			
		$stmt = oci_parse($this->con, "SELECT ID FROM STAFF WHERE SSN = $SSN");
			
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
		}
		
		function fetchStaffInfo($id) {
			
		$stmt = oci_parse($this->con, "SELECT * FROM STAFF WHERE ID = $id");
			
		oci_execute($stmt,OCI_DEFAULT);
		
		$row = oci_fetch_array($stmt,OCI_BOTH);
		
		
			
		return $row;
			
		}
		
		function fetchStaffAlerts($id) {
			
			$stmt= oci_parse($this->con,"select CONTENT from ALERTS where STAFFID = $id");
		
			oci_execute($stmt,OCI_DEFAULT);
			oci_fetch_all($stmt,$result);
		
			return $result;
			
			
		}
		
		function fetchStaffTypeName($id) {
			
		$stmt = oci_parse($this->con, "SELECT TYPENAME FROM STAFFTYPE WHERE ID = $id");
			
		oci_execute($stmt,OCI_DEFAULT);
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
			
		}
		
		function fetchSchedule(){
			
		$stmt = oci_parse($this->con, "SELECT * FROM SCHEDULE");
			
		oci_execute($stmt,OCI_DEFAULT);
		
		//$ROW = oci_fetch_array($stmt,OCI_BOTH);
		
		echo 'Current Schedule: <br>';
	
		echo '<table border=0>';
	
		while($ROW = oci_fetch_array($stmt, OCI_BOTH)) {
		
		$WORKERID = $ROW['WORKERID'];
		$changeLink = "<a href=\"schedule.php?cmd=cg&workerID=$WORKERID\">".'CHANGE'.'</a>';
		echo '<tr>';
		echo '<td> WORKER: '.$this->fetchStaffName($ROW['WORKERID']).'</td><td> AT  '.$this->fetchTheatreName($ROW['THEATREID']).'</td><td> FROM: '.$ROW['STARTTIME'].'</td><td> TO: '.$ROW['ENDTIME'].'</td><td> Type:'.$this->fetchStaffTypeName($this->fetchStaffType($ROW['WORKERID'])).'</td><td>'.$changeLink.'</td><td>';
		echo '</tr>';
		
		}
		echo '</table>';
			
			
		}
		
		function fetchStaffName($id) {

		$stmt = oci_parse($this->con,"SELECT NAME FROM STAFF WHERE ID = $id");
		
		oci_execute($stmt, OCI_DEFAULT);
		
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
			
			
		}
		
		function fetchManagerIDbyStaffID($id){
			
		$stmt = oci_parse($this->con, "SELECT ID FROM MANAGER WHERE STAFFID = $id");
			
		oci_execute($stmt, OCI_DEFAULT);
		
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
			
		}
		function updateSchedule($workerID, $timeS, $timeE, $theatreID, $managerID) {
			
			$query = "UPDATE SCHEDULE SET STARTTIME = to_date(:timeS, 'YYYY/MM/DD'), ENDTIME = to_date(:timeE ,'YYYY/MM/DD'), THEATREID = :TID, assignerID = :AID where WORKERID = $workerID ";  //sql
			$stid = oci_parse($this->con, $query);  //编译
						
			oci_bind_by_name($stid, ':timeS', $timeS);
			oci_bind_by_name($stid, ':timeE', $timeE);
			oci_bind_by_name($stid, ':TID', $theatreID);
			oci_bind_by_name($stid, ':AID', $managerID);



			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
			
			
			
		}
		
		function fetchNoScheduleStaff() {
			
			$stmt= oci_parse($this->con,"select ID from STAFF where ID NOT IN (SELECT WORKERID FROM SCHEDULE) AND TYPEID NOT IN (4,5)");
		
			oci_execute($stmt,OCI_DEFAULT);
			oci_fetch_all($stmt,$result);
		
			return $result;
			
			
		}
		
		function insertSchedule($workerID, $timeS, $timeE, $theatreID, $managerID) {
			
			$query = "INSERT INTO SCHEDULE VALUES(  :WORKERID, to_date(:timeS, 'YYYY/MM/DD'), to_date(:timeE ,'YYYY/MM/DD'), :TID, :AID)";  //sql
			$stid = oci_parse($this->con, $query);  //编译
			
			oci_bind_by_name($stid, ':WORKERID', $workerID);			
			oci_bind_by_name($stid, ':timeS', $timeS);
			oci_bind_by_name($stid, ':timeE', $timeE);
			oci_bind_by_name($stid, ':TID', $theatreID);
			oci_bind_by_name($stid, ':AID', $managerID);



			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
			
			
			
		}
		
		function fetchStaffType($id) {
			
		$stmt = oci_parse($this->con, "SELECT TYPEID FROM STAFF WHERE ID = $id");
			
		oci_execute($stmt, OCI_DEFAULT);
		
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
			
		}
		
		function fetchGuestList(){
			
		$stmt = oci_parse($this->con, "SELECT ID FROM REGULARMEMBER");
			
		oci_execute($stmt, OCI_DEFAULT);
		
		oci_fetch_all($stmt,$result);
		
			
		return $result; //fetchUserName	fetchUserIDByUserName 
			
			
        }
		
		function fetchLoginName($id) {
			
			
		$stmt = oci_parse($this->con, "SELECT USERID FROM REGULARMEMBER WHERE ID = $id");
			
		oci_execute($stmt, OCI_DEFAULT);
		
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
			
		}
		
		function fetchManagerIDsWithoutRights(){
			
		$stmt = oci_parse($this->con, "SELECT ID FROM STAFF WHERE TYPEID = 4 AND ID NOT IN (SELECT STAFFID FROM MOVIEUPDATE)");
			
		oci_execute($stmt, OCI_DEFAULT);
		
		oci_fetch_all($stmt,$result);
		
			
		return $result; //fetchUserName	fetchUserIDByUserName 
			
		}
		
		function fetchManagerIDsWithRights(){
			
		$stmt = oci_parse($this->con, "SELECT ID FROM STAFF WHERE TYPEID = 4 AND ID IN (SELECT STAFFID FROM MOVIEUPDATE)");
			
		oci_execute($stmt, OCI_DEFAULT);
		
		oci_fetch_all($stmt,$result);
		
			
		return $result; //fetchUserName	fetchUserIDByUserName 
			
		}
		
		function addRight($id) {
			
			$query = "INSERT INTO MOVIEUPDATE VALUES(:SID)";  //sql
			$stid = oci_parse($this->con, $query);  //编译
			
			oci_bind_by_name($stid, ':SID', $id);			

			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
			
		}
		
		function delRight($id) {
			
			$query = "DELETE FROM MOVIEUPDATE WHERE STAFFID = $id";  //sql
			$stid = oci_parse($this->con, $query);  //编译
			
			//oci_bind_by_name($stid, ':ID', $id);			

			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
		}
		
		function updateMovieSchedule($sid, $ts, $te, $tid) {
			
			$query = "UPDATE SCREENROOM SET TIMESTART = to_date(:timeS, 'YYYY/MM/DD HH24:MI'), TIMEEND = to_date(:timeE,'YYYY/MM/DD HH24:MI'), THEATREID = :tid WHERE ID = $sid ";  //sql
			$stid = oci_parse($this->con, $query);  //编译
				
						
			oci_bind_by_name($stid, ':timeS', $ts);
			oci_bind_by_name($stid, ':timeE', $te);
			oci_bind_by_name($stid, ':TID', $tid);
			
			
			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
			
			
		}
		
		function inserNewMovie($title, $director, $star, $description) {
			
			$query = "INSERT INTO MOVIE VALUES(:ID, :title, :director, :star, :description)";
			$stid = oci_parse($this->con, $query);  //编译
				
				
			$id = $this->getLastMovieID() +1;	
				
			oci_bind_by_name($stid, ':ID', $id);		
			oci_bind_by_name($stid, ':title', $title);			
			oci_bind_by_name($stid, ':director', $director);
			oci_bind_by_name($stid, ':star', $star);
			oci_bind_by_name($stid, ':description', $description);
			
			
			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
			return $id;
			
		}
		
		function insertMovieSchedule($mid, $ts, $te, $tid) {
			
			$query = "INSERT INTO SCREENROOM VALUES(:ID, to_date(:TIMESTART, 'YYYY/MM/DD HH24:MI'), to_date(:TIMEEND, 'YYYY/MM/DD HH24:MI'), :THEATREID ,:MOVIEID)";
			$stid = oci_parse($this->con, $query);  //编译
				
				
			$id = $this->getLastScreenRoomID() + 1;	
				
			oci_bind_by_name($stid, ':ID', $id);		
			oci_bind_by_name($stid, ':TIMESTART', $ts);			
			oci_bind_by_name($stid, ':TIMEEND', $te);
			oci_bind_by_name($stid, ':MOVIEID', $mid);
			oci_bind_by_name($stid, ':THEATREID', $tid);
			
			
			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
		}
		
		function getLastMovieID(){
			
		$stmt = oci_parse($this->con, "SELECT MAX(ID) FROM MOVIE");
			
		oci_execute($stmt, OCI_DEFAULT);
		
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
			
		}
		
		function getLastScreenRoomID(){
			
		$stmt = oci_parse($this->con, "SELECT MAX(ID) FROM SCREENROOM");
			
		oci_execute($stmt, OCI_DEFAULT);
		
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				return $col_values;   
				}
		   
        }
		}
		

		function fetchUserPhoneNumber($id){
			
		$stmt = oci_parse($this->con, "SELECT PHONENUMBER FROM MEMBER WHERE ID = $id");
			
		oci_execute($stmt, OCI_DEFAULT);
		
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//fetchStartTime
				return $col_values;   
				}
		   
        }
			
		}
		
		function 更新电话号码($id, $phone) {
			
			
			$query = "UPDATE MEMBER SET PHONENUMBER = $phone WHERE ID = $id ";  //sql
			$stid = oci_parse($this->con, $query);  //编译
				
						
			//oci_bind_by_name($stid, ':timeS', $ts);
			//oci_bind_by_name($stid, ':timeE', $te);
			//oci_bind_by_name($stid, ':TID', $tid);
			
			
			oci_execute($stid,OCI_DEFAULT); //执行
			
			oci_free_statement($stid); 
			
			
			oci_commit($this->con);
			
			
		}
		
		function findSafety($tm){
			
		$query = 'SELECT WORKERID FROM SCHEDULE WHERE STARTTIME <= to_date(:tm, \'MM/DD/YYYY\') AND ENDTIME >= to_date(:tm, \'MM/DD/YYYY\')';
			
		$stmt = oci_parse($this->con, $query);
			
			
		oci_bind_by_name($stmt, ':tm', $tm);
			
			
		oci_execute($stmt, OCI_DEFAULT);
		
		while (($row = oci_fetch_array($stmt, OCI_BOTH)) != false) {
		// Use the uppercase column names for the associative array indices
		
		if($this->fetchStaffType($row['WORKERID']) == 3 ) {
			
			return true;
			
		}
		
		}
			
			
			
		}
		
		function fetchStaffStartTime($id) {
			
			
		$stmt = oci_parse($this->con, "SELECT STARTTIME FROM SCHEDULE WHERE WORKERID = $id");
			
		oci_execute($stmt, OCI_DEFAULT);
		
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//fetchStartTime
				return $col_values;   
				}
		   
        }
			
			
			
		}
		
		function fetchStaffEndTime($id) {
			
			
		$stmt = oci_parse($this->con, "SELECT ENDTIME FROM SCHEDULE WHERE WORKERID = $id");
			
		oci_execute($stmt, OCI_DEFAULT);
		
		oci_fetch_all($stmt,$result);
		
		foreach($result as $rows){
				echo "";
				foreach($rows as $col_values){
				//fetchStartTime
				return $col_values;   
				}
		   
        }
			
			
			
		}
				
		
		
       
			
		
		
  }
  
  
  
 ?>