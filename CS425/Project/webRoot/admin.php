<?php
require_once('header.php');

function __autoload($class_name) { 
$path = str_replace('_', '/', $class_name); 
require_once $path . '.class.php'; 
} 

session_start();

if(isset($_POST['ssn'])) {
	
	validateStaff();
	
}


 function validateStaff() {
	 
	 
	 $dao = new DAO();
	 
	 $id = $dao -> fetchStaffIDbySSN($_POST['ssn']);
	 
	 if($id!=null) {
		 
		 $_SESSION['STAFFID'] = $id;
		 echo 'WelCome, redirecting to management page...';
		 header("refresh:2; url=staff.php");
		 
	 } else {
		 
		 
		 echo 'you are not a staff of our company.';
		 
	 }
	 
 }


?>


<div id="main"> 
    
   	<div class="container">
	<form action="admin.php" method="Post" name="loginform">
    	<h1>Staff Login</h1><br>
	<fieldset>
		<div class="field">
			<label>SSN #: </label>
			<input type="text" name="ssn" class="text" value /required>
		</div>


		<label><input type=submit name="submit" id="r-submit" class="newbutton" value="Submit"></label>
		<input type="hidden" name="type" value="create">
	</fieldset>
	</form>
			
</div>

<?php include_once('footer.php'); ?>