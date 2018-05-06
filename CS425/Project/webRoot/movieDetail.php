<?php
require_once('header.php');

function __autoload($class_name) { 
$path = str_replace('_', '/', $class_name); 
require_once $path . '.class.php'; 
} 

session_start();

$movieID = $_REQUEST['mID'];

$_SESSION['mID'] = $movieID;

$rinput = array_key_exists('rinput', $_POST) ? $_POST['rinput'] : null;

if($rinput!= null) {
	
	$str=nl2br($_REQUEST['rinput']);
	insertRewview($str, $movieID, $_SESSION['LoginName']);
	
}

function insertRewview($content, $mid, $userName) {
	
	$dao = new DAO();
	
	$dao-> insertRewview($content, $mid, $userName);
	
	
	
	
	
}

function displayMovieName(){
	
	global $movieID;
	
	$dao = new DAO();
	
	echo $dao->fetchMovieName($movieID);
	
	
}

function displayDetail(){
	
	global $movieID;
	
	$dao = new DAO();
	
	$dao->fetchMovieDetail($movieID); //echo already in DAO class. while this violated the PHP code regulations. 
	
	
}

function reviewContent() {
		
	global $movieID;
	
	$dao = new DAO();
	
	$dao->fetchReview($movieID);
	
	
	
}


?>


<div class="pageContent">
    <div id="main">
      <div class="container">
        <h1>Online Cinema </h1>
        <h2>Enjoying the real rewards! </h2>
        </div>
        <div class="container">
		<h3><a href="#"><? displayMovieName() ?><br></a></h3>
		<h2><?php displayDetail()?></h2>
        <div class="clear"></div>
        </div>
		<div class="container">
        <h3>Reviews </h3>
        <?reviewContent()?>
		
		<? if (isset($_SESSION['LoginName'])) { 
		
		echo '<button class=flip1> editor </button>';
		echo "
		<form class=editorf1 action=\"\" method=\"post\">
		<textarea name=\"rinput\" cols=\"100\" rows=\"10\" name=\"contents\" \'>Type your review here.</textarea>
		<br><input type=\"submit\" value=\"submit review\">
		</form>";
				
		 } ?>
		
        </div>
 </div>

<?php include_once('footer.php'); ?>