<?php
require_once('header.php');

function __autoload($class_name) { 
$path = str_replace('_', '/', $class_name); 
require_once $path . '.class.php'; 
} 


session_start();

//scroomID alias sID
$_SESSION['sID'] = null;
$_SESSION['sID'] = $_GET['sID'];

function order() {
	
	//echo $_GET['sID'];
	
	if(isset($_SESSION['LoginName'])==false) {
	
		bookAsGuest();
	
	} else {
		
		bookAsMember();
		
	}
	
}

function bookAsGuest() {
	
	$dao = new DAO();
	
	if(isset($_SESSION['uID'])==false) {
	
	echo 'To book this ticket, enter the information below.<br>';
	
	echo 
	'	<form action="registerProcess.php" method="Post" name="registerform">
    	<h1>Payment InFo</h1><br>
	<fieldset>

		<div class="field">
			<label>Name: </label>
			<input type="text" name="name"  class="text" value /required>
		</div>

		<div class="field">
			<label>Email: </label>
			<input type="email" name="email" class="text" value /required>
		</div>
		
		<div class="field">
			<label>Phone #: </label>
			<input type=text name="phoneNumber"  class="text"  maxlength=12 value /required> (XXX-XXX-XXXX)
		</div>

		<div class="field">
			<label>CreditCard #: </label>
			<input type=text name="creditNumber"  class="text"  maxlength=12 value /required> (XXXXXXXXXX)
		</div>

		<label><input type=submit name="submit" id="r-submit" class="newbutton" value="buy"></label>
		<input type="hidden" name="type" value="create">
	</fieldset>
	</form> ';
	} else {
		
		echo 'Payment pass, confirm now! .<br>';
		
		$orderNumber = $dao->makeOrder($_GET['sID'], $_SESSION['uID']);
		
		echo 'Thanks for your booking ! . Your order number is '.$orderNumber;
		
	}
	
}

function bookAsMember() {
	
	$userName = $_SESSION['LoginName'];
	
	$dao = new DAO();
	
	$orderNumber = $dao->makeOrder($_GET['sID'], $dao->fetchUserIDByUserName($userName));
	
	echo 'Thanks for your booking ! . Your order number is '.$orderNumber;
	
	
}




?>

<div class="pageContent">
    <div id="main">
      <div class="container">
        <h1>Online Cinema </h1>
        <h2>Enjoying the real rewards! </h2>
        </div>
        <div class="container">
		<h3><a href="#">Ticket purchase </a></h3>
		<h2><?php order()?></h2>
        <div class="clear"></div>
        </div>
 </div>
 
<?php include_once('footer.php'); ?>