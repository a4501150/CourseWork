<?php
require_once('header.php'); ?>

<div id="main">     
   	<div class="container">

<?php

function __autoload($class_name) { 
$path = str_replace('_', '/', $class_name); 
require_once $path . '.class.php'; 
} 

session_start();

  if(isset($_REQUEST['cmd'])) {

  $cmd = $_REQUEST['cmd'];
  
  logOut();
  
  
  }
  
  if(isset($_REQUEST['userName'])) {
  
  $userName = $_REQUEST['userName'];
  $userPass = $_REQUEST['userPass'];
  
  if(tryLogin($userName, $userPass)) {
  
  $_SESSION['LoginName'] = $userName;
	echo 'login successful, redirecting..';
	header("refresh:2; url=index.php");
  } else {
		echo '<h1> wrong PassWord or UserName, redirecting..</h1>';
		header("refresh:2; url=index.php");
  }
  
  
  } else {
  
  echo '<h1> You have no permission directly to this page. Forwarding <h1>';
  header("refresh:2; url=index.php");
  
  }

  function logOut() {
	  
	$_SESSION = array();
	session_destroy();
	
	header("Location: index.php");
	exit;
	  
	  
	  
  }
  
  function tryLogin($un, $up) {

	$dao = new DAO();
	$pass = $dao -> fetchPassWord($un);
	if ($pass!=$up) {
		return false;
	} else {
		return true;
	}
	

  }  
  
  
  
?>

</div></div>

<?php include_once('footer.php'); ?>