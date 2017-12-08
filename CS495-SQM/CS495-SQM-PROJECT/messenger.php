<?php
/**
 * Created by PhpStorm.
 * User: jinyang
 * Date: 4/19/17
 * Time: 2:43 PM
 */

if(!session_id()) session_start();

if(isset($_SESSION['role'])) {
    $role = $_SESSION['role'] ;
}

?>


<div class="text-center">

    <h3> Staff Messenger</h3>

    <hr>

    <?

    if ($role!= "Manager") {

        echo "You can view Messages From Managers Here <hr>";

        echo "
        
        <p>Manager: Project XXX asdsadsads  sdasdsadaas  sdsadasdsadas </p><hr>
        <p>Manager: Project XXX asdsadsads  sdasdsadaas  sdsadasdsadas </p><hr>
        <p>Manager: Project XXX asdsadsads  sdasdsadaas  sdsadasdsadas </p><hr>
        <p>Manager: Project XXX asdsadsads  sdasdsadaas  sdsadasdsadas </p><hr>
        <p>Manager: Project XXX asdsadsads  sdasdsadaas  sdsadasdsadas </p><hr>
        
        ";

    } else {

        echo 'You can send messages to existing staffs';

        echo "    <div class=\"panel-body\">
        <div class=\"well\">
            <div class=\"form-group\">

                <form id=\"temp\">
                    <textarea rows=\"4\" cols=\"50\">
send message to whole team for the pending tasks. 
</textarea><br>
                    <button id='send' type=\"submit\" class=\"btn btn-primary\">Submit</button>
                </form>

            </div>
            <hr>
            <p>Also you can delete message here</p>
                <select>
        <option value=\"volvo\">Message 1</option>
        <option value=\"saab\">Message 2</option>
        <option value=\"mercedes\">Message 3</option>
    </select>
    <br><br>
    
             <button id='delete_m' type=\"submit\" class=\"btn btn-primary\">Delete</button>
        </div>
    </div>";

    }

    ?>


    <script>

        $('#send').click(function () {


            alert('send message successful');


        });

        $('#delete_m').click(function () {


            alert('delete message successful');


        });

    </script>

</div>