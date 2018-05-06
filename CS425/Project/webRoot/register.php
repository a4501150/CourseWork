<?php
require_once('header.php');

function __autoload($class_name) { 
$path = str_replace('_', '/', $class_name); 
require_once $path . '.class.php'; 
} 

session_start();





?>


<div id="main"> 
    
   	<div class="container">
	<form action="registerProcess.php" method="Post" name="registerform">
    	<h1>Member Sign Up</h1><br>
	<fieldset>
		<div class="field">
			<label>User ID: </label>
			<input type="text" name="userID" class="text" value /required>
		</div>
		<div class="field">
			<label>Name: </label>
			<input type="text" name="name"  class="text" value /required>
		</div>
		<div class="field">
			<label>Password: </label>
			<input type="password" name="password"  class="text" value /required>
		</div>
		<div class="field">
			<label>Email: </label>
			<input type="email" name="email" class="text" value /required>
		</div>
		<div class="field">
			<label>address: </label>
			<input type="text" name="address"  class="text" value /required>
		</div>
		<div class="field">
			<label>Phone #: </label>
			<input type=text name="phoneNumber"  class="text"  maxlength=12 value /required> (XXX-XXX-XXXX)
		</div>
		<div class="field">
			<label>CreditCard #: </label>
			<input type=text name="creditNumber"  class="text"  maxlength=12 value /required> (XXXXXXXXXX)
		</div>
		<div class="field">
			<label>CreditCard Exp: </label>
			<input type="text" name="cexp"  class="text" value /required>
		</div>
		<label><input type=submit name="submit" id="r-submit" class="newbutton" value="Submit"></label>
		<input type="hidden" name="type" value="create">
	</fieldset>
	</form>
			
</div>

<?php include_once('footer.php'); ?>