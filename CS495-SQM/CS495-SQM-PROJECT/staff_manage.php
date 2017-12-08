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

    <h3> Staff Managment</h3>

    <hr>

    <h4> Your Role is <? echo $role;?> </h4>

    <hr>

    <?

        if ($role!= "Manager") {

            echo "You can view your Job detail here <br>";

            if($role == "Tester") echo "Your Job is to test the software.";
            else echo "Your job is to develop the software.";

        } else {

            echo 'You can register new staff or delete existing staffs';

            echo "    <div class=\"panel-body\">
        <div class=\"well\">
            <div class=\"form-group\">

                <form action=\"utils/uploadTemp.php\" id=\"temp\">
                    Name:<br>
                    <input type=\"text\" name=\"title\" id=\"title\"><span id=\"sug\"></span><br>
                    Email:<br>
                    <input type=\"text\" name=\"thumb\"><br>
                    Role:<br>
                    <input type=\"text\" name=\"director\"><br>
                    <br>
                    <button type=\"submit\" class=\"btn btn-primary\">Submit</button>
                </form>

            </div>
            <hr>
                <select>
        <option value=\"volvo\">Simon</option>
        <option value=\"saab\">Matthew</option>
        <option value=\"mercedes\">Jinyang</option>
    </select>
    <br><br>
    
             <button type=\"submit\" class=\"btn btn-primary\">Delete</button>
        </div>
    </div>";

        }

    ?>


</div>
