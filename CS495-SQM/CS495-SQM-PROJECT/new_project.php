<?php
/**
 * Created by PhpStorm.
 * User: jinyang
 * Date: 4/19/17
 * Time: 2:28 PM
 */

?>

<div class="text-center">

    <h3> Add New Project</h3>

    <hr>

    <br>
    <h4> Please Specify The requirments and infomation ragarding project </h4>

    <div class="panel-body">
        <div class="well">
            <div class="form-group">

                <form action="utils/uploadTemp.php" id="temp">
                    Project Name:<br>
                    <input type="text" name="title" id="title"><span id="sug"></span><br>
                    Description:<br>
                    <input type="text" name="thumb"><br>
                    Workflow:<br>
                    <input type="text" name="director"><br>
                    Project Scope:<br>
                    <input type="text" name="actors" id="act"><br>
                    Work repo:<br>
                    <input type="text" name="lang"><br>
                    Requirments:<br>
                    <input type="text" name="region"><br>
                    <br><br>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>

            </div>
        </div>
    </div>


</div>
