<?php
/**
 * Created by PhpStorm.
 * User: jinyang
 * Date: 4/18/17
 * Time: 5:40 PM
 */

if(!session_id()) session_start();

if(isset($_SESSION['role'])) {
    $role = $_SESSION['role'] ;
}

?>

<div class="text-center">

    <h3> Project Description</h3>

    <hr>

    <br>
    <h4> Target Audience </h4>
    <pre class=" language-markup">The target audience is xxxxxxxxxxx</pre>

    <br>
    <h4> What does it do? </h4>
    <pre class=" language-markup">Lorem Ipsum is simply dummy text of the printing and typesetting industry.
	Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a
	galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,
	but also the leap into electronic typesetting, remaining essentially unchanged.
	It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
	and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</pre>

    <br>
    <h4> What is timeFrame?</h4>
    <? if ($role=="Manager") { ?>
        <select>
        <option value="volvo">AAA</option>
        <option value="saab">BBB</option>
        <option value="opel">CCC</option>
    </select>
    <? } else echo 'AAA';?>

    <br><br><br>
    <h4> Resource Repo </h4>
    <pre class=" language-markup"> github.com/sample </pre>

    <? if($role == "Manager") { ?>
    <button>add resources</button>
    <? } ?>
    <br>
    <h4> Issues Tracker </h4>



</div>

<script>
    $(function() {
        Metis.dashboard();
    });
</script>
