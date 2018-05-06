<?php

require_once('header.php');

function __autoload($class_name) { 
$path = str_replace('_', '/', $class_name); 
require_once $path . '.class.php'; 
} 

session_start();

isset($_SESSION['STAFFID']) or die('u cannot access if you are not a staff nor a manager');



if(isset($_GET['cmd'])) {
	
	if($_GET['cmd']=='out') {
		
		logout();
		
	}
	
	if($_GET['cmd']=='addRight') {
		
		addRight($_GET['staffid']);
	}
	
	if($_GET['cmd']=='delRight') {
		
		delRight($_GET['staffid']);
		
	}
	
}


function logout(){
	
	
	$_SESSION = array();
	
	session_destroy();
	
	header("refresh:0; url=admin.php");
	
	
}

function addRight($id){
	
	$dao=new DAO();
	
	$dao->addRight($id);
	
}

function delRight($id){
	
	$dao=new DAO();
	
	$dao->delRight($id);
	
}

function displayAssignment() {
	
	$dao = new DAO();
	
	$ROW = $dao -> fetchSchedule();
	

	
}

function displayJobStatus() {
	
	
	$dao = new DAO();
	
	$row = $dao->fetchStaffInfo($_SESSION['STAFFID']);
	
	echo '<table border = 0>';
	
	echo '<tr>';
	
	echo '<td> name: '.$row['NAME'].'</td> <td> Phone# :'.$row['PHONENUMBER'].'</td><td> Type: '.$dao->fetchStaffTypeName($row['TYPEID']).'</td>';
	
	echo '</tr></table>';
	
	if ($row['TYPEID']==4) {
		
		$_SESSION['managerID'] = $row['ID'];
		
				
	}
	
	if ($row['TYPEID']==5) {
		
		$_SESSION['OWNER'] = TRUE;
		
		
		
	}
	
}

function displayAlerts() {
	
	$dao = new DAO();
	
	$rs = $dao -> fetchStaffAlerts($_SESSION['STAFFID']);
	

		
			foreach($rs as $rows){
				echo "";
				if ($rows == null) {
					
					echo 'No Common Alerts';
					
				}
				foreach($rows as $col_values){
				
				echo '<font color=red>'.$col_values.'! </font><br>';

				}
		   
			}
			
	$tomorrow  = mktime(0, 0, 0, date("m")  , date("d")+1, date("Y"));
	
	$tm = date("m/d/Y", $tomorrow);
	
	
	echo $tm;
	if($dao->findSafety($tm)!=true) {
		
		echo '<br><font color = \'red\'>No Safety Employee Work Tommorow!</font>';
		
	}
		

	
}

function displayStaffsNeedsToAssign() {
	
	$dao = new DAO();
	
	$rs = $dao -> fetchNoScheduleStaff();
	
	echo 'Avalible to assign Schedule: <br>';
	
	echo '<table border=0>';
	
	foreach ($rs as $row) {
		
		foreach ($row as $id) {
			
			echo '<tr>';
			$changeLink = "<a href=\"schedule.php?cmd=as&workerID=$id\">".'assign'.'</a>';
			echo '<td> STAFF: '.$dao->fetchStaffName($id).'</td><td>'.$dao->fetchStaffTypeName($dao->fetchStaffType($id)).'</td><td> '.$changeLink.'</td><td>';
			echo '</tr>';
			
		}
		
	}
	echo '</table>';
	
	
	
	
}

function displayGuestInfo(){
	
	$dao = new DAO();
	
	$rs = $dao -> fetchGuestList();
	
	echo 'Avalible Guest to View: <br>';
	
	echo '<table border=0>';
	
	foreach ($rs as $row) {
		
		foreach ($row as $id) {
			
			echo '<tr>';
			$loginName = $dao->fetchLoginName($id);
			$changeLink = "<a href=\"account.php?loginName=$loginName\">".'view'.'</a>';
			echo '<td> Guest: '.$dao->fetchUserName($id).'</td><td> '.$changeLink.'</td><td>';
			echo '</tr>';
			
		}
		
	}
	echo '</table>';
	
	
	
	
	
}

function displayManagerInfo() {
	
	
	$dao = new DAO();
	
	$rs = $dao -> fetchManagerIDsWithoutRights();
	
	echo 'Manager have not rights to update movie: <br>';
	
	echo '<table border=0>';
	
	foreach ($rs as $row) {
		
		foreach ($row as $id) {
			
			echo '<tr>';
			$changeLink = "<a href=\"staff.php?cmd=addRight&staffid=$id\">".'addRights'.'</a>';
			echo '<td> Manager: '.$dao->fetchStaffName($id).'</td><td> '.$changeLink.'</td><td>';
			echo '</tr>';
			
		}
		
	}
	echo '</table>';
	
	$rss = $dao -> fetchManagerIDsWithRights();
	
	echo 'Manager have rights to update movie: <br>';
	
	echo '<table border=0>';
	
	foreach ($rss as $row) {
		
		foreach ($row as $id) {
			
			echo '<tr>';
			$changeLink = "<a href=\"staff.php?cmd=delRight&staffid=$id\">".'delRights'.'</a>';
			echo '<td> Manager: '.$dao->fetchStaffName($id).'</td><td> '.$changeLink.'</td><td>';
			echo '</tr>';
			
		}
		
	}
	echo '</table>';
	
	
}

function displayMovieList(){
	
	$dao = new DAO();
	
		$dao = new DAO();
	
	$stmt= oci_parse($dao->con,"SELECT TO_CHAR(TIMESTART, 'MM-DD-YYYY HH24:MI') AS TS ,TO_CHAR(TIMEEND, 'MM-DD-YYYY HH24:MI') AS TE, ID, THEATREID, MOVIEID FROM SCREENROOM");
	
    OCIDefineByName($stmt,"TS",$timeS);

    OCIDefineByName($stmt,"TE",$timeE);
	
	OCIDefineByName($stmt,"ID",$scroomID);
	
	OCIDefineByName($stmt,"THEATREID",$tID);
	
	OCIDefineByName($stmt,"MOVIEID",$mID);

	
	oci_execute($stmt,OCI_DEFAULT);
	
	echo '<table border=1>';
	
	while (oci_fetch($stmt)) {
	
	echo '<tr>';
	
    // Use the uppercase column names for the associative array indices
	$modifyLink = '<a href=schedule.php?cmd=cgm&sID='.$scroomID.'>&nbsp change</a>';
	
    echo '<td> MOVIE: '.$dao->fetchMovieName($mID).'</td>'.' <td>Start: '.$timeS. ' </td><td>End: '.$timeE.' </td><td> theatre: '.$dao->fetchTheatreName($tID).'</td><td>'.$modifyLink.
	' </td>';
    
	echo '</tr>';
	
	}
	
	echo '</table>';
		
		
		
		
	
	
}

function displayAddNewMovie(){
	
	echo '<a href=schedule.php?cmd=asm> AssignNewMovie </a>';
	
	
}

function checkupdaterights($ID){
	
	$dao = new DAO();
	
	$stmt= oci_parse($dao->con,"SELECT STAFFID FROM MOVIEUPDATE WHERE STAFFID = $ID");
	
	oci_execute($stmt,OCI_DEFAULT);
	
	oci_fetch_all($stmt, $rs);
	
	
	
	foreach ($rs as $rows) {
		foreach($rows as $i) {
			
			if (sizeof($rows)>0) {
				return true;
				
			} else {
				return false;
				
			}
			
		}
		
	}
	
}

?>

<div class="pageContent">
    <div id="main">
      <div class="container">
        <h1>Online Cinema Management</h1>
       
        </div>
        <div class="container">
		<h3><a href="#">Job View </a></h3>
		<h2><?php displayJobStatus(); ?></h2>
        <div class="clear"></div>
        </div>
				
		<? if(isset($_SESSION['managerID'])) {?>
		<div class="container">
		<h3><a href="staff.php"> AssignTimeSheet </a></h3>
		<h2><?php displayAssignment(); ?></h2>
		<h2><?php displayStaffsNeedsToAssign(); ?></h2>
        <div class="clear"></div>
        </div>		
		<? } ?>
		
		<? if(isset($_SESSION['managerID']) && checkupdaterights($_SESSION['STAFFID'])) {?>
		<div class="container">
		<h3><a href="staff.php"> MovieManagement </a></h3>
		<h2><?php displayMovieList(); ?></h2>
		<h2><?php displayAddNewMovie(); ?></h2>
        <div class="clear"></div>
        </div>		
		<? } ?>
			
		<? if(isset($_SESSION['OWNER'])) {?>
		<div class="container">
		<h3><a href="staff.php"> Guest Info </a></h3>
		<h2><?php displayGuestInfo(); ?></h2>
        <div class="clear"></div>
        </div>	

		<div class="container">
		<h3><a href="staff.php"> Delegate Moive UPDATE rights to manager </a></h3>
		<h2><?php displayManagerInfo(); ?></h2>
        <div class="clear"></div>
        </div>	
		<? } ?>
		
		<div class="container">
		<h3><a href="#">Alerts </a></h3>
		<h2><?php displayAlerts() ?></h2>
        <div class="clear"></div>
        </div>
		
		<div class="container">
		<h3><a href="staff.php?cmd=out">Log Out </a></h3>
        <div class="clear"></div>
        </div>
		
 </div>
 
<?php //include_once('footer.php'); ?>