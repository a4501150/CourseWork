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

 if(isset($_REQUEST['cexp'])) {
	 
	 $id = registerMember($_REQUEST['name'],$_REQUEST['phoneNumber'],$_REQUEST['email'],$_REQUEST['creditNumber']);
	 //first become a Member then a regular.
	 registerRegularMember(
	 $id,
	 $_REQUEST['userID'],
	 $_REQUEST['password'],
	 $_REQUEST['address'],
	 $_REQUEST['cexp']
	 	 
	 );
	 
	 	 echo '<h1> sign up sucessful. Redirecting..<h1>';
	 header("refresh:2; url=index.php");
 } else {
	 $uid = registerMember($_REQUEST['name'],$_REQUEST['phoneNumber'],$_REQUEST['email'],$_REQUEST['creditNumber']);	 
	 $_SESSION['uID'] = $uid;
	 $sID = $_SESSION['sID'];
	 echo "<a href=\"order.php?sID=$sID\">GO BACK TO CONTINUE PURCHASE</a>";
 }


 function registerMember ($name, $phone, $email, $Card) {
	 
	$dao = new DAO();
	 
	$newID = ($dao->getLastMemberID()+1);
	 
	$isRegular = 'N';
	
	$dao->registerMember($newID, $name, $phone, $email, $Card, $isRegular);
	 
	 return $newID;
	 
 }
   
 function registerRegularMember($id, $userID, $userPass, $address, $cexp) {
	
	$dao = new DAO();
	
	$dao->registerRegularMember($id, $userID, $userPass, $address, $cexp);
	 
	
 
 }
  
  
?>

</div></div>

<?php include_once('footer.php'); ?>