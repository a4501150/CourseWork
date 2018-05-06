<?php

require_once('header.php');


function __autoload($class_name) { 
$path = str_replace('_', '/', $class_name); 
require_once $path . '.class.php'; 
} 

session_start();




?>
<div class="pageContent">
    <div id="main">
      <div class="container">
        <h1>Online Cinema </h1>
        <h2>Enjoying the real rewards! </h2>
        </div>
        <div class="container">
		<h3><a href="forum.php">Discussion Forum</a></h3>
		<h2>User's water space </h2> <br>
		<h3><a href="movieSchedule.php">Movie Sechdules </a></h3>
		<h2>Newest movie find here</h2> <br>
		<h3><a href="admin.php">Managment and Staff Area </a></h3>
		<h2>Take it easy </h2> <br>
		
		<h3><a href="QUERIES.php">QUERIES demonstrate </a></h3>
		<h2>Another place </h2>
        <div class="clear"></div>
        </div>
 </div>
 
<?php include_once('footer.php'); ?>