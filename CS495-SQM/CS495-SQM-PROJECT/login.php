<?


if(!session_id()) session_start();



if(isset($_POST['user'])) {

    echo $_POST['role'];

    $role = $_POST['role'];
    if(!isset($_SESSION['role'])) {
        $_SESSION['role'] = $role;
    }

} else if (isset($_GET['logout'])) {

    session_destroy();
    $url='login.php';
    echo '<META HTTP-EQUIV=REFRESH CONTENT="1; '.$url.'">';

} else {







?>

<html>

<head>
 <link rel="stylesheet" type="text/css" href="login.css">

    <script
            src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous">
    </script>
</head>

<section class="login">
	<div class="titulo">Staff Login</div>
	<form id='loginForm' action="login.php" method="post" enctype="application/x-www-form-urlencoded">
    	<input type="text" name="user" required title="Username required" placeholder="Username" data-icon="U">
        <input type="password" name="pass" required title="Password required" placeholder="Password" data-icon="x">
        <div class="olvido">
			<div class="col"><a href="#" title="s">Choose Your Role</a></div>
            <div class="col">
            <select name="role">
			  <option value="Manager">Manager</option>
			  <option value="Tester">Tester</option>
			  <option value="Developer">Developer</option>
			</select>
          </div>
        </div>
        <a id="bt_sbmt" href="#" class="enviar" type="submit">Submit</a>
    </form>
</section>


<script>


    $( "#bt_sbmt" ).on( "click", function( event ) {

        $.post( "login.php", $( "#loginForm" ).serialize(), function( data ) {
            //$( ".result" ).html( data );
            alert('login as ' + data);

            window.location.replace("app.php?role="+data);

        });


    });



</script>



</html>


<? }  ?>