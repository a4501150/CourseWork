<?php
/**
 * Created by PhpStorm.
 * User: jinyang
 * Date: 4/18/17
 * Time: 2:29 PM
 */

session_start();

if (isset($_SESSION['id'])) {

    $url='app.php';
    echo '<META HTTP-EQUIV=REFRESH CONTENT="1; '.$url.'">';

} else

{

    $url='login.php';
    echo '<META HTTP-EQUIV=REFRESH CONTENT="1; '.$url.'">';


}