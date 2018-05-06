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


  if($_REQUEST['act']==NULL) {
  
	returnToMovieDetail();
  
  } else {
	  
	  
	if($_REQUEST['act']=='add') addLikes($_REQUEST['rID']);
	if($_REQUEST['act']=='del') delLikes($_REQUEST['rID']);
	  
	  
  }

  function addLikes($rID) {
	  

	$dao = new DAO();
	
	$dao->addLikes($rID);
	
	echo 'thanks for your like! &nbsp';
	
	returnToMovieDetail();
	  
	  
	  
  }
  
	function delLikes($rID) {
		
		$dao = new DAO();
		
		$dao->delLikes($rID);
		
		echo 'sorry for your dislike! &nbsp';
		
		returnToMovieDetail();
	}
	
	function returnToMovieDetail(){
		
		$mid = $_SESSION['mID'];
		echo "<a href=\"movieDetail.php?mID=$mid\">GO BACK</a>";
		
	}
  
  
  
?>

</div></div>

<?php include_once('footer.php'); ?>