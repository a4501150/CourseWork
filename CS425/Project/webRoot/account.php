<?php

require_once('header.php');

function __autoload($class_name) { 
$path = str_replace('_', '/', $class_name); 
require_once $path . '.class.php'; 
} 

session_start();

$sessionLoginName = array_key_exists('LoginName', $_SESSION) ? $_SESSION['LoginName'] : null;


if ($sessionLoginName == null) {
if($_GET['loginName']!=null) {
	
	$_SESSION['LoginName'] = $_GET['loginName'];
	
}
}

if(isset($_REQUEST['np'])) {
	
	changePhoneNumber();
	
}

isset($_SESSION['LoginName']) or die('u cannot access if you are not a member');

function changePhoneNumber(){
	
	$新的电话号码 = $_REQUEST['np'];
	
	$dao = new DAO();
	
	$dao -> 更新电话号码($dao->fetchUserIDByUserName($_SESSION['LoginName']),$新的电话号码);
	
}

function displayAlerts() {
	
	$dao = new DAO();
	
	$rs = $dao -> fetchUserAlerts($dao->fetchUserIDByUserName($_SESSION['LoginName']));
	

		
			foreach($rs as $rows){
				echo "";
				if ($rows == null) {
					
					echo 'No Alerts';
					
				}
				foreach($rows as $col_values){
				
				echo '<font color=red>'.$col_values.'! </font><br>';

				}
		   
			}
		

	
}

function displayUserStatus(){
	
	$dao = new DAO();
	
	$p = $dao->fetchUserPoint($dao->fetchUserIDByUserName($_SESSION['LoginName']));
	$s = $dao->fetchUserStatus($dao->fetchUserIDByUserName($_SESSION['LoginName']));
	$电话号码 = $dao -> fetchUserPhoneNumber($dao->fetchUserIDByUserName($_SESSION['LoginName']));
	echo 'Hi, your points are : &nbsp'.$p.'&nbsp Your member status is :'.$s.'<br> your phone number is '.$电话号码;
	
	echo '<button class=flip1> clicke me to change phone number </button>';
	echo "
		<form class=editorf1 action=\"\" method=\"post\">
		<input type=\"text\" name=\"np\" class=\"text\" value /required>
		<br><input type=\"submit\" value=\"submit change\">
		</form>";
	
}

function displayUserOrders(){
	
	$dao = new DAO();
	
	$rs = $dao -> fetchUserOrders($dao->fetchUserIDByUserName($_SESSION['LoginName']));
	
		foreach($rs as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				echo 'Theatre: '.$dao->fetchTheatreNameByScroomID($col_values)
					 .'&nbsp Show Time: '.$dao->fetchStartTime($col_values).'<br>'

					;   
				}
		   
        }

}

function displayRecentTopics(){
	
	$dao = new DAO();
	
	$rsRw = $dao -> fetchRewiewIDbyUserName($_SESSION['LoginName']); //result set of reviewIDs by this user.
	
	$rsTh = $dao -> fetchTopicIDbyUserName($_SESSION['LoginName']); // result set of TopicsIDs by
	
	$rsFu = $dao -> fetchFollowUpIDbyUserName($_SESSION['LoginName']); // result set of FollowUpsID by
	
	echo '<div class="container">';
	echo '<h1>reviews</h1>';
		foreach($rsRw as $rows){
				echo "<br>";
				foreach($rows as $col_values){
				//echo $col_values;
				$mID = $dao -> fetchMovieIDbyReviewID($col_values);
				echo "<a href=movieDetail.php?mID=$mID>".' review for '.$dao->fetchMovieName($mID).'</a></br>';
				}		   
        } 
	echo '</div>';
	
	echo '<div class="container">';
	echo '<h1> Threads </h1>';
		foreach($rsTh as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values;
				echo "<a href=thread.php?tid=$col_values>".' Thread:  '.$dao->fetchThreadName($col_values).'</a></br>';
				}		   
        } 
	echo '</div>';
	
	echo '<div class="container">';
	echo '<h1> Thread FollowUps </h1>';
		foreach($rsFu as $rows){
				echo "";
				foreach($rows as $col_values){
				//echo $col_values.'<br>';
				$tid = $dao->fetchParentThreadID($col_values);
				//echo $tid;
				echo "<a href=thread.php?tid=$tid>".' Thread:  '.$dao->fetchThreadName($tid).'</a></br>';
				}		   
        } 
	echo '</div>';
	
}


?>

<div class="pageContent">
    <div id="main">
      <div class="container">
        <h1>Online Cinema </h1>
        <h2>Enjoying the real rewards! </h2>
        </div>
        <div class="container">
		<h3><a href="#">Account View </a></h3>
		<h2><?php displayUserStatus(); ?></h2>
        <div class="clear"></div>
        </div>
		
		<div class="container">
		<h3><a href="#">Purchase History </a></h3>
		<h2><?php displayUserOrders()?></h2>
        <div class="clear"></div>
        </div>
		
		<div class="container">
		<h3><a href="#">Recent Topics </a></h3>
		<h2><?php displayRecentTopics() ?></h2>
        <div class="clear"></div>
        </div>
		
		<div class="container">
		<h3><a href="#">Alerts </a></h3>
		<h2><?php displayAlerts() ?></h2>
        <div class="clear"></div>
        </div>
		
 </div>
 
<?php include_once('footer.php'); ?>