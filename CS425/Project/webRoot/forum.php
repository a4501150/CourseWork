<?php

require_once('header.php');


function __autoload($class_name) {
    $path = str_replace('_', '/', $class_name);
    require_once $path . '.class.php';
}

session_start();

$tinM = array_key_exists('tinM', $_POST) ? $_POST['tinM'] : null;
$tinT = array_key_exists('tinT', $_POST) ? $_POST['tinT'] : null;
$cinM = array_key_exists('cinM', $_POST) ? $_POST['cinM'] : null;
$cinT = array_key_exists('cinT', $_POST) ? $_POST['cinT'] : null;

if($tinM!=null) {
	
	$tinM=nl2br($tinM);
	$cinM=nl2br($cinM);
	
	insertThread($tinM,$cinM,true);
	
} else if ($tinT!=null) {
	
	$tinT=nl2br($tinT);
	$cinT=nl2br($cinT);
	
	insertThread($tinT,$cinT,false);
	
	
}

$tinM=null;
$cinM=null;
$cinT=null;
$tinT=null;

function insertThread($title, $content, $isMovieTopic) {
	
	$dao = new DAO();
	
	if($isMovieTopic==true) {
		
		$mID = $_REQUEST['mselect'];
		$tID = null;
		
	} else {
		
		$tID = $_REQUEST['tselect'];
		$mID = null;
	}
	
	$uid = $dao->fetchUserIDByUserName($_SESSION['LoginName']);
	
	$dao->insertThreadTopic($title, $content, $uid, $mID, $tID);
	
}

function displayMovieTopics() {


    $dao = new DAO();

    $dao -> fetchThreadNameForMovie();


}

function displayTheatreTopics() {

	$dao = new DAO();

    $dao -> fetchThreadNameForTheatre();


}

function fetchMovieOptions(){
	
	$dao = new DAO();
	
	return $dao -> fetchMovieIDs();
	
}

function getMovieName($id){
	
	$dao = new DAO();
	
	return $dao -> fetchMovieName($id);
	
	
}

function fetchTheatreOptions(){
	
	$dao = new DAO();
	
	return $dao -> fetchTheatreIDs();
	
}

function getTheatreName($id){
	
	$dao = new DAO();
	
	return $dao -> fetchTheatreName($id);
	
}


?>
    <div class="pageContent">
        <div id="main">
            <div class="container">
                <h1>Online Cinema Forum</h1>
                <h2>Enjoying the real rewards! </h2>
            </div>
            <div class="container">
                <h3>Topics Regarding Movie</h3> <br>
				<p> <? displayMovieTopics() ?> </p>
                <div class="clear"></div>
				
				
				<? if (isset($_SESSION['LoginName'])) { 
				
				echo '<button class=flip1> editor </button>';
				
				echo "
				<form class=editorf1 action=\"\" method=\"post\">
				<textarea name=\"tinM\" cols=\"100\" rows=\"1\" name=\"contents\" \'>Type your title here.</textarea>
				<textarea name=\"cinM\" cols=\"100\" rows=\"10\" name=\"contents\" \'>Type your content here.</textarea>
				<br><input type=\"submit\" value=\"submit thread\">
				
				choose a movie <select name=\"mselect\" required> ";
				echo '<option value =\'\' selected=\'selected\' >----must select one---</option>';
				foreach (fetchMovieOptions() as $rows) {					
					foreach($rows as $value) {						
						echo "<option value =$value >".getMovieName($value).'</option>';						
					}										
				}				
				echo "</select>
				
				</form>";
						
				 } ?>
				
				<!-- fantastic required html5 mark -->
				
            </div>

            <div class="container">
                <h3>Topics Regarding Theatre</h3> <br>
				<p> <? displayTheatreTopics() ?> </p>
                <div class="clear"></div>
				
				<? if (isset($_SESSION['LoginName'])) { 
				echo '<button class=flip2> editor </button>';
				echo "
				<form class=editorf2 action=\"\" method=\"post\">
				<textarea name=\"tinT\" cols=\"100\" rows=\"1\" name=\"contents\" \'>Type your title here.</textarea>
				<textarea name=\"cinT\" cols=\"100\" rows=\"10\" name=\"contents\" \'>Type your content here.</textarea>
				<br><input type=\"submit\" value=\"submit thread\">
				
				choose a Theatre <select name=\"tselect\" required> ";
				echo '<option value =\'\' selected=\'selected\' >----must select one---</option>';
				foreach (fetchTheatreOptions() as $rows) {					
					foreach($rows as $value) {						
						echo "<option value =$value >".getTheatreName($value).'</option>';						
					}										
				}				
				echo "</select>
				
				</form>";
						
				 } ?>
				
				
            </div>
        </div>

<?php include_once('footer.php'); ?>