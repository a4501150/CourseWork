<?php


function __autoload($class_name) { 
$path = str_replace('_', '/', $class_name); 
require_once $path . '.class.php'; 
} 

session_start();

$tid = array_key_exists('tid', $_REQUEST) ? $_REQUEST['tid'] : null;
$ttid = array_key_exists('ttid', $_REQUEST) ? $_REQUEST['ttid'] : null;

if($tid!=null) {
	
	$rs = getThreadContent($_REQUEST['tid']);
	$dao = new DAO();
		foreach($rs as $rows){
				echo "";
				foreach($rows as $col_values){
				
				echo "Comment/discussion Content: ".$dao->fetchThreadContent($col_values).'&nbsp &nbsp ThreadName: '.$dao->fetchThreadName($col_values).'<br>';   
				}
		   
        }
	
	
}

if($ttid !=null) {
	
	$dao = new DAO();
	$rs = getStaffID($_REQUEST['ttid']);
	
		foreach($rs as $rows){
				echo "";
				foreach($rows as $col_values){
				
				echo "Staffs on duty on mondays: ".$dao->fetchStaffName($col_values).'&nbsp &nbsp type: '.$dao->fetchStaffTypeName($dao->fetchstaffType($col_values)).' schedule: '.$dao->fetchStaffStartTime($col_values).'&nbsp'.$dao->fetchStaffEndTime($col_values).'<br>';   
				}
		   
        }
	
	
}

function getStaffID($tid){
	
	$dao = new DAO();
	
	$q = "select workerid from schedule where theatreid = $tid and q8(workerid)>0";
	
	$stid = oci_parse($dao->con, $q);
	
	oci_execute($stid, OCI_DEFAULT);
	
	oci_fetch_all($stid, $rs);
	
	return $rs;
	
	
	
}

function getThreadContent($TID){
	
	$dao = new DAO();
	
	$q = "SELECT * FROM (SELECT ID FROM THREAD WHERE PARENTTHREADID = $TID order by id DESC) WHERE ROWNUM <=3 ";
	
	$stid = oci_parse($dao->con, $q);
	
	oci_execute($stid, OCI_DEFAULT);
	
	oci_fetch_all($stid, $rs);
	
	return $rs;
	
}






?>



