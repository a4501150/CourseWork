<?php

require_once('header.php');



function __autoload($class_name) { 
$path = str_replace('_', '/', $class_name); 
require_once $path . '.class.php'; 
} 

session_start();


function displaySchedule() {
	
	$dao = new DAO();
	
	$stmt= oci_parse($dao->con,"SELECT TO_CHAR(TIMESTART, 'MM-DD-YYYY HH24:MI') AS TS ,TO_CHAR(TIMEEND, 'MM-DD-YYYY HH24:MI') AS TE, ID, THEATREID, MOVIEID FROM SCREENROOM");
	
    OCIDefineByName($stmt,"TS",$timeS);

    OCIDefineByName($stmt,"TE",$timeE);
	
	OCIDefineByName($stmt,"ID",$scroomID);
	
	OCIDefineByName($stmt,"THEATREID",$tID);
	
	OCIDefineByName($stmt,"MOVIEID",$mID);

	
	oci_execute($stmt,OCI_DEFAULT);
	
	echo '<table border=1>';
	
	while (oci_fetch($stmt)) {
	
	echo '<tr>';
	
    // Use the uppercase column names for the associative array indices
	$bookLink = '<a href=order.php?sID='.$scroomID.'>&nbsp book</a>';
	$detailLink = '<a href=movieDetail.php?mID='.$mID.'>&nbsp detaile</a>';
    echo '<td> MOVIE: '.$dao->fetchMovieName($mID).'</td>'.' <td>Start: '.$timeS. ' </td><td>End: '.$timeE.' </td><td> theatre: '.$dao->fetchTheatreName($tID).'</td><td>'.$bookLink.
	' </td><td>'.$detailLink.
	'</td>';
    
	echo '</tr>';
	
	}
	
	echo '</table>';
	
}





?>

<div class="pageContent">
    <div id="main">
      <div class="container">
        <h1>Online Cinema </h1>
        <h2>Enjoying the real rewards! </h2>
        </div>
        <div class="container">
		<h3><a href="#">Movie Sechdules </a></h3>
		<h2><?php displaySchedule()?></h2>
        <div class="clear"></div>
        </div>
 </div>
 
<?php include_once('footer.php'); ?>