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
	<form action="loginProcess.php" method="Post" name="loginform">
    	<h1>Member Login</h1><br>
	<fieldset>
		<div class="field">
			<label>User ID: </label>
			<input type="text" name="userName" class="text" value /required>
		</div>

		<div class="field">
			<label>Password: </label>
			<input type="text" name="userPass"  class="text" value /required>
		</div>

		<label><input type=submit name="submit" id="r-submit" class="newbutton" value="Submit"></label>
		<input type="hidden" name="type" value="create">
	</fieldset>
	</form>
			
</div>

<?php include_once('footer.php'); ?>