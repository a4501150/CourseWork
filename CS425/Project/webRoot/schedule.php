<?php

require_once('header.php');

function __autoload($class_name) { 
$path = str_replace('_', '/', $class_name); 
require_once $path . '.class.php'; 
} 

session_start();

isset($_SESSION['STAFFID']) or die('u cannot access if you are not a staff nor a manager');

if($_GET['cmd']=='cg'&&isset($_REQUEST['st'])) {
	
	updateSchedule();
	
	
}

if($_GET['cmd']=='cgm'&&isset($_REQUEST['sID'])&&isset($_REQUEST['st'])) {
	
	updateMovieSchedule();
	
	
}

if($_GET['cmd']=='asm'&&isset($_REQUEST['mn'])) {
	
	inserNewMovieSchedule();
	
	
}

if ($_GET['cmd']=='as'&&isset($_REQUEST['st'])) {
	
	insertSchedule();
	
}
function inserNewMovieSchedule() {
	
	$dao = new DAO();
	
	$title = $_REQUEST['mn'];
	$director = $_REQUEST['dt'];
	$star = $_REQUEST['STAR'];
	$description = $_REQUEST['DSC'];
	
	$tid = $_REQUEST['tselect'];
	
	$ts = $_REQUEST['st'];
	
	$te = $_REQUEST['et'];
	
	$mid = $dao -> inserNewMovie($title, $director, $star, $description);
	
	//老子不想做了 太麻烦了就这样把 - -  Mark 正确逻辑应该为 添加电影库存 然后assign这个schedule.
	$dao -> insertMovieSchedule($mid, $ts, $te, $tid);
	
	
}
function updateMovieSchedule(){
	
	$dao = new DAO();
	
	$sid = $_GET['sID'];
	
	$tid = $_REQUEST['tselect'];
	
	$ts = $_REQUEST['st'];
	
	$te = $_REQUEST['et'];
	
	$dao -> updateMovieSchedule($sid, $ts, $te, $tid);
	

	header("refresh:0; url=staff.php");
	
}

function updateSchedule(){
	
	$dao = new DAO();
	
	$wid = $_GET['workerID'];
	
	$tid = $_REQUEST['tselect'];
	
	$ts = $_REQUEST['st'];
	
	$te = $_REQUEST['et'];
	
	$dao -> updateSchedule($wid, $ts, $te, $tid, $dao->fetchManagerIDbyStaffID($_SESSION['managerID']));
	

	header("refresh:0; url=staff.php");
	
}

function insertSchedule(){
	
	$dao = new DAO();
	
	$wid = $_GET['workerID'];
	
	$tid = $_REQUEST['tselect'];
	
	$ts = $_REQUEST['st'];
	
	$te = $_REQUEST['et'];
	
	$dao -> insertSchedule($wid, $ts, $te, $tid, $dao->fetchManagerIDbyStaffID($_SESSION['managerID']));
	

	header("refresh:0; url=staff.php");
	
}

function fetchTheatreOptions(){
	
	$dao = new DAO();
	
	return $dao -> fetchTheatreIDs();
	
}

function getTheatreName($id){
	
	$dao = new DAO();
	
	return $dao -> fetchTheatreName($id);
	
}

function displayChangeTimeSheet() {
	
	
	$workerID = $_GET['workerID'];
	
	$dao = new DAO();
	

	
     echo '  
		<div class="container">
		
		<h3><a href="#">Job Assign </a></h3>
		
		<form action="" method="Post" name="loginform">
    	<br><h1>Staff Schedule Change</h1><br>
		<fieldset>
		
		<div class="field">
			<label>NEW START TIME (YYYY/MM/DD) #: </label>
			<input type="text" name="st" class="text" value /required>
		</div>
		<br><br>
		<div class="field">
			<label>NEW END TIME (YYYY/MM/DD)#: </label>
			<input type="text" name="et" class="text" value /required>
		</div>
		<br><br>

		<div class="field">
		Pick a Theatre <select name="tselect" required> ';
		echo '<option value =\'\' selected=\'selected\' >----must select one---</option>';
				foreach (fetchTheatreOptions() as $rows) {					
					foreach($rows as $value) {						
						echo "<option value =$value >".getTheatreName($value).'</option>';						
					}										
				}				

		
		
		echo '
		</div>
		<br>

		<label><input type=submit name="submit" id="r-submit" class="newbutton" value="Submit"></label>
		<input type="hidden" name="type" value="create">
		</fieldset>
		</form>
		
		
        <div class="clear"></div>
		
        </div> '; 

}

function displayChangeMovieTimeSheet() {
	
	
	$scroomID = $_GET['sID'];
	
	$dao = new DAO();
	

	
     echo '  
		<div class="container">
		
		<h3><a href="#">Movie Time Assign </a></h3>
		
		<form action="" method="Post" name="loginform">
    	<br><h1>Staff Schedule Change</h1><br>
		<fieldset>
		
		<div class="field">
			<label>NEW START TIME YYYY/MM/DD HH24:MI#: </label>
			<input type="text" name="st" class="text" value /required>
		</div>
		<br><br>
		<div class="field">
			<label>NEW END TIME YYYY/MM/DD HH24:MI #: </label>
			<input type="text" name="et" class="text" value /required>
		</div>
		<br><br>


		<div class="field">
		Pick a Theatre <select name="tselect" required> ';
		echo '<option value =\'\' selected=\'selected\' >----must select one---</option>';
				foreach (fetchTheatreOptions() as $rows) {					
					foreach($rows as $value) {						
						echo "<option value =$value >".getTheatreName($value).'</option>';						
					}										
				}				

		
		
		echo '
		</div>
		<br>

		<label><input type=submit name="submit" id="r-submit" class="newbutton" value="Submit"></label>
		<input type="hidden" name="type" value="create">
		</fieldset>
		</form>
		
		
        <div class="clear"></div>
		
        </div> '; 

}

function displayInsertNewMovieTimeSheet(){
	
	//$scroomID = $_GET['sID'];
	
	$dao = new DAO();
	

	
     echo '  
		<div class="container">
		
		<h3><a href="#"> NEW Movie Time Assign </a></h3>
		
		<form action="" method="Post" name="loginform">
    	<br><h1>Staff Schedule Change</h1><br>
		<fieldset>
		
		<div class="field">
			<label>NEW MOVIE NAME: </label>
			<input type="text" name="mn" class="text" value /required>
		</div>
		<br><br>
		
		<div class="field">
			<label>NEW MOVIE DIRECTOR: </label>
			<input type="text" name="dt" class="text" value /required>
		</div>
		<br><br>
		
		<div class="field">
			<label>STAR: </label>
			<input type="text" name="STAR" class="text" value /required>
		</div>
		<br><br>
		
		<div class="field">
			<label>description: </label>
			<input type="text" name="DSC" class="text" value /required>
		</div>
		<br><br>
		
		<div class="field">
			<label>NEW START TIME YYYY/MM/DD HH24:MI#: </label>
			<input type="text" name="st" class="text" value /required>
		</div>
		<br><br>
		<div class="field">
			<label>NEW END TIME YYYY/MM/DD HH24:MI #: </label>
			<input type="text" name="et" class="text" value /required>
		</div>
		<br><br>


		<div class="field">
		Pick a Theatre <select name="tselect" required> ';
		echo '<option value =\'\' selected=\'selected\' >----must select one---</option>';
				foreach (fetchTheatreOptions() as $rows) {					
					foreach($rows as $value) {						
						echo "<option value =$value >".getTheatreName($value).'</option>';						
					}										
				}				

		
		
		echo '
		</div>
		<br>

		<label><input type=submit name="submit" id="r-submit" class="newbutton" value="Submit"></label>
		<input type="hidden" name="type" value="create">
		</fieldset>
		</form>
		
		
        <div class="clear"></div>
		
        </div> '; 
	
	
}


?>

<div class="pageContent">
    <div id="main">
      <div class="container">
        <h1>Online Cinema Management</h1>
       
        </div>
		
		<? if ($_GET['cmd']=='cg') { displayChangeTimeSheet(); } ?>
		<? if ($_GET['cmd']=='as') { displayChangeTimeSheet(); } ?>
		<? if (isset($_GET['sID'])) { displayChangeMovieTimeSheet(); } ?>
		<? if ($_GET['cmd']=='asm') { displayInsertNewMovieTimeSheet(); } ?>
				
		<div class="container">
		<h3><a href="staff.php?cmd=out">Log Out </a></h3>
        <div class="clear"></div>
        </div>
		
 </div>
 
<?php //include_once('footer.php'); ?>