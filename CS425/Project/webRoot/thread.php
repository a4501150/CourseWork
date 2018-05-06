<?php

require_once('header.php');


function __autoload($class_name) {
    $path = str_replace('_', '/', $class_name);
    require_once $path . '.class.php';
}

session_start();

isset($_GET['tid']) or die('u cannot access it directly');


$fin = array_key_exists('fin', $_POST) ? $_POST['fin'] : null;
$mID = array_key_exists('mID', $_POST) ? $_POST['mID'] : null;
$ttID = array_key_exists('ttID', $_POST) ? $_POST['ttID'] : null;


if($fin!=null) {
	
	$parentID = $_GET['tid'];
	$content = $fin;
	
	
	
	insertFollowUp($parentID, $content, $mID, $ttID);
	
	
	
}

$fin = null;
$mID = null;
$ttID = null;

function insertFollowUp($parentID, $content, $mID, $ttID){
	
	$dao = new DAO();
	
	$uID = $dao->fetchUserIDByUserName($_SESSION['LoginName']);
	
	$dao->insertFollowUp($parentID, $content, $uID, $mID, $ttID);
	
	
}

function displayTopicName() {
	
	$dao = new DAO();
	
	echo $dao -> fetchThreadName($_GET['tid']).' &nbsp by: '.$dao->fetchUserName($dao->fetchUserIDByThreadID($_GET['tid']));
	
}

function displayTopicContent() {
	
	$dao = new DAO();
	
	echo $dao -> fetchThreadContent($_GET['tid']);
	
}

function displayFollowUp() {
	
	$dao = new DAO();
	
	$rs = $dao -> fetchFollowUpIDs($_GET['tid']);
	
	foreach ($rs as $rows) {
		
		foreach ($rows as $value) {
			
			
			echo '<div class="container">';
            echo  '<p> To:'.$dao->fetchThreadName($value) .'&nbsp Reply By '.$dao->fetchUserName($dao->fetchUserIDByThreadID($value)).'<br>'.$dao->fetchThreadContent($value). '</p>';
            echo '</div>';
			
			
		}
		
		
	}
	
}

function displayFollowUpEditor(){
				
				
				echo '<div class="container">';
				echo '<button class=flip1> editor </button>';
				echo "
				<form class=editorf1 action=\"\" method=\"post\">
				<textarea name=\"fin\" cols=\"100\" rows=\"10\" name=\"contents\" \'>Type your follow up here.</textarea>
				<br><input type=\"submit\" value=\"submit follow up\">			
				</form>";
				echo '</div>';
	
}
?>
    <div class="pageContent">
        <div id="main">
            <div class="container">
                <h1>Online Cinema Forum</h1>
                <h2>Enjoying the real rewards! </h2>
            </div>
            <div class="container">
                <h1><? displayTopicName() ?></h1> <br>
				<p> <? displayTopicContent() ?> </p>
                <div class="clear"></div>
            </div>
			<?displayFollowUp() ?>
			<? if (isset($_SESSION['LoginName'])) { 
				
				displayFollowUpEditor();
						
				 } ?>

        </div>

<?php include_once('footer.php'); ?>