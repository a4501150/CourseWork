<div id="main">
 <div class="container">

<a href="index.php"><?php echo 'BACK TO HOME'; ?></a> &nbsp &nbsp
<?php if(isset($_SESSION['LoginName'])==false) { ?>
<a href="login.php"><?php echo 'LOG IN'; ?></a> &nbsp &nbsp
<a href="register.php"><?php echo 'SIGH UP'; ?></a> &nbsp &nbsp
<?php } else { ?>
<?php echo 'Welcome, '.$_SESSION['LoginName']; ?> &nbsp &nbsp
<a href="account.php"><?php echo 'Account View'; ?></a> &nbsp &nbsp
<a href="loginProcess.php?cmd=out"><?php echo 'Log Out'; ?></a> &nbsp &nbsp
<?php }  ?>

</div>
</div>

</body>


<script type="text/javascript">
$(document).ready(function(){
  $("#hide").click(function(){
  $("editor").hide();
  });
  $("#show").click(function(){
  $("editor").show();
  });
});
</script>

<script> 

$(".editorf1").hide();
$(".editorf2").hide();

$(document).ready(function(){
$(".flip1").click(function(){
$(".editorf1").toggle();
});
});

$(document).ready(function(){
$(".flip2").click(function(){
$(".editorf2").toggle();
});
});

</script>



</html>

