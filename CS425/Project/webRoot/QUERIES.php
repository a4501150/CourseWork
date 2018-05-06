<?php
require_once('header.php');

function __autoload($class_name) { 
$path = str_replace('_', '/', $class_name); 
require_once $path . '.class.php'; 
} 

session_start();

function q1(){
	
				$dao = new DAO();

				echo "
				<form action=\"\" method=\"post\">
				choose a Thread <select name=\"threadselect\" onchange=\"Q1(this.value)\" required> ";
				echo '<option value =\'\' selected=\'selected\' >----must select one---</option>';
				foreach (fetchThreads() as $rows) {					
					foreach($rows as $value) {						
						echo "<option value =$value >".$dao->fetchThreadName($value).'</option>';						
					}										
				}				
				echo "</select>
				
				</form>";
						
				
	
	
}

function Q3() {
	
	$dao = new DAO();
	
	$q = "SELECT PID FROM Q3 WHERE NUM = (SELECT MIN(NUM) FROM Q3)";
			
	$stmt = oci_parse($dao->con, $q);
			
	oci_execute($stmt, OCI_DEFAULT);
		
	oci_fetch_all($stmt,$rs);
	
		foreach($rs as $rows){
				echo "";
				foreach($rows as $col_values){			
				echo "least poplular thread name:  ".$dao->fetchThreadName($col_values).'&nbsp &nbsp Content: '.$dao->fetchThreadContent($col_values).'<br>';   
				}
		   
        }

		oci_free_statement($stmt); 
		
	$q = "select id from thread where parentthreadid is null AND ID NOT IN (SELECT PARENTTHREADID FROM THREAD WHERE PARENTTHREADID IS NOT NULL)";
	
		$stmt = oci_parse($dao->con, $q);
			
	oci_execute($stmt, OCI_DEFAULT);
		
	oci_fetch_all($stmt,$rs);
	
		foreach($rs as $rows){
				echo "";
				foreach($rows as $col_values){			
				echo "No comment or discussion thread name:  ".$dao->fetchThreadName($col_values).'&nbsp &nbsp Content: '.$dao->fetchThreadContent($col_values).'<br>';   
				}
		   
        }
	
}

function Q2(){
	
	$dao = new DAO();
	
	$q = "SELECT * FROM (SELECT ID FROM THREAD WHERE PARENTTHREADID IS NULL order by id DESC) WHERE ROWNUM <=3 ";
			
	$stmt = oci_parse($dao->con, $q);
			
	oci_execute($stmt, OCI_DEFAULT);
		
	oci_fetch_all($stmt,$rs);
	
		foreach($rs as $rows){
				echo "";
				foreach($rows as $col_values){
				
				echo "Comment/discussion Content: ".$dao->fetchThreadContent($col_values).'&nbsp &nbsp ThreadName: '.$dao->fetchThreadName($col_values).'<br>';   
				}
		   
        }	
	
}

function q5() {
	
	$dao = new DAO();
	
	$q = "SELECT USERID FROM Q5 WHERE NUM = (SELECT MAX(NUM) FROM Q5)";
			
	$stmt = oci_parse($dao->con, $q);
			
	oci_execute($stmt, OCI_DEFAULT);
		
	oci_fetch_all($stmt,$rs);
	
		foreach($rs as $rows){
				echo "";
				foreach($rows as $col_values){
				
				echo "User contributed most reviews: ".$dao->fetchUserName($col_values).'<br>';   
				}
		   
        }

	oci_free_statement($stmt); 
	
	$q = "SELECT USERID FROM Q5 WHERE NUM = (SELECT MAX(NUM) FROM Q5)";
			
	$stmt = oci_parse($dao->con, $q);
			
	oci_execute($stmt, OCI_DEFAULT);
		
	oci_fetch_all($stmt,$rs);
	
		foreach($rs as $rows){
				echo "";
				foreach($rows as $col_values){
				
				echo "User contributed most comments on threads: ".$dao->fetchUserName($col_values).'<br>';   
				}
		   
        }
	
	

	
}

function q6(){
	
	$dao = new DAO();
	
	$q = "SELECT tid FROM Q6 WHERE NUM = (SELECT MAX(NUM) FROM Q6)";
			
	$stmt = oci_parse($dao->con, $q);
			
	oci_execute($stmt, OCI_DEFAULT);
		
	oci_fetch_all($stmt,$rs);
	
		foreach($rs as $rows){
				echo "";
				foreach($rows as $col_values){
				
				echo "Theatre which was playing the most number of movies: ".$dao->fetchTheatreName($col_values).'<br>';   
				}
		   
        }
	
	
	
}

function q7(){
	
	$dao = new DAO();
	
	$q = "Select ID from theatre where id = (select theatreid from screenroom where id = (select sid from q7 where num = (select max(num) from q7)))";
			
	$stmt = oci_parse($dao->con, $q);
			
	oci_execute($stmt, OCI_DEFAULT);
		
	oci_fetch_all($stmt,$rs);
	
		foreach($rs as $rows){
				echo "";
				foreach($rows as $col_values){
				
				echo "Theatre which sales most online ticktes : ".$dao->fetchTheatreName($col_values).'<br>';   
				}
		   
        }
	
	
}

function fetchThreads(){
	
	$dao = new DAO();
	
	$query = 'SELECT ID FROM THREAD WHERE PARENTTHREADID IS NULL';
			
	$stmt = oci_parse($dao->con, $query);
			
	oci_execute($stmt, OCI_DEFAULT);
		
	oci_fetch_all($stmt,$result);
	
	return $result;
			
	
}

function q8(){
	
	
				$dao = new DAO();

				echo "
				<form action=\"\" method=\"post\">
				choose a Theare <select name=\"threadselect\" onchange=\"Q8(this.value)\" required> ";
				echo '<option value =\'\' selected=\'selected\' >----must select one---</option>';
				foreach ($dao->fetchTheatreIDs() as $rows) {					
					foreach($rows as $value) {						
						echo "<option value =$value >".$dao->fetchTheatreName($value).'</option>';						
					}										
				}				
				echo "</select>
				
				</form>";

	
	
}


?>

<div class="pageContent">
<div id="main"> 
    
	      <div class="container">
        <h1>Online Cinema </h1>
        <h2>Enjoying the real rewards! </h2>
        </div>
   	<div class="container">
	
	<H3>Here is the Queries demonstrate for 1,2,3,5,6,7,8 <BR>Others Already shows up and works in the main application logics.</a></H3>
	</div>
	
	<div class="container">
	<h3><a href="#">Query 1 <br>Display the 3 most recent discussions/comments from a specific discussion thread.</a></h3>
	<? q1(); ?>
	<div id="threadcontent"> </div>
	</div>
	
	<div class="container">
	<h3><a href="#">Query 2 <br>Display the 3 most recent discussion/comments from all discussion threads.</a></h3>
	<? q2() ?>
	</div>
	
	<div class="container">
	<h3><a href="#">Query 3 <br>Display the least popular discussion thread in terms of visits and comments.
</a></h3>
	<? q3()?>
	</div>
	
	<div class="container">
	<h3><a href="#">Query 5 <br>Display the registered guest who has contributed most comments.</a></h3>
	<? q5() ?>
	</div>
	
	<div class="container">
	<h3><a href="#">Query 6 <br>Display the theatre(s) that are showing most number of movies.
</a></h3>
	<? q6() ?>
	</div>
	
	<div class="container">
	<h3><a href="#">Query 7 <br>Display the theatre (s) that has the most online ticket sales.</a></h3>
	<?q7()?>
	</div>
	
	<div class="container">
	<h3><a href="#">Query 8 <br>Display the list of all employees who are on duty on Monday on a specific theatre. Display also their jobs and time table.</a></h3>
	<?q8()?>
	<div id="theatre"> </div>
	</div>

</div> </div>

<script>
function Q1(str) {
    if (str == "" || str =="0") {
        document.getElementById("threadcontent").innerHTML = "";
        //return;
    } else { 
        if (window.XMLHttpRequest) {
            // code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                document.getElementById("threadcontent").innerHTML = xmlhttp.responseText;
            }
        };
        xmlhttp.open("POST","getContent.php?tid="+str,true);
        xmlhttp.send();
    }
}

function Q8(str) {
    if (str == "" || str =="0") {
        document.getElementById("theatre").innerHTML = "";
        //return;
    } else { 
        if (window.XMLHttpRequest) {
            // code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                document.getElementById("theatre").innerHTML = xmlhttp.responseText;
            }
        };
        xmlhttp.open("POST","getContent.php?ttid="+str,true);
        xmlhttp.send();
    }
}
</script>

<?php include_once('footer.php'); ?>