<?php
/**
 * Created by PhpStorm.
 * User: jinyang
 * Date: 4/18/17
 * Time: 5:48 PM
 */


if(!session_id()) session_start();

if(isset($_SESSION['role'])) {
    $role = $_SESSION['role'] ;
}


?>

<div id="content">
    <div class="outer">
        <div class="inner bg-light lter">
            <!--Begin Datatables-->
            <? if($role == 'Manager') { ?>

            <div class="row">
                <div class="col-lg-12 ui-sortable">
                    <div class="box ui-sortable-handle">
                        <header>
                            <div class="icons"><i class="fa fa-table"></i></div>
                            <h5>Worker Assignment</h5>
                        </header>
                        <div id="collapse4" class="body">
                            <div id="dataTable_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer"><div class="row"><div class="col-sm-6"><div class="dataTables_length" id="dataTable_length"><label>Show <select name="dataTable_length" aria-controls="dataTable" class="form-control input-sm"><option value="10">10</option><option value="25">25</option><option value="50">50</option><option value="100">100</option></select> entries</label></div></div><div class="col-sm-6"><div id="dataTable_filter" class="dataTables_filter"><label>Search:<input type="search" class="form-control input-sm" placeholder="" aria-controls="dataTable"></label></div></div></div><div class="row"><div class="col-sm-12"><table id="dataTable" class="table table-bordered table-condensed table-hover table-striped dataTable no-footer" role="grid" aria-describedby="dataTable_info">
                                            <thead>
                                            <tr role="row"><th class="sorting_asc" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Project: activate to sort column descending" style="width: 127px;">Project</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Developer: activate to sort column ascending" style="width: 238px;">Developer</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Deffects ID(s): activate to sort column ascending" style="width: 224px;">Deffects ID(s)</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="assigner ID(S): activate to sort column ascending" style="width: 143px;">assigner ID(S)</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending" style="width: 73px;">Status</th></tr>
                                            </thead>
                                            <tbody>

                                            <tr role="row" class="odd">
                                                <td class="sorting_1"><select>
                                                        <option value="volvo">Demo 1</option>
                                                        <option value="saab">Demo 2</option>
                                                        <option value="mercedes">Demo 3</option>
                                                    </select></td>
                                                <td><select>
                                                        <option value="volvo">Genji</option>
                                                        <option value="saab">Mcree</option>
                                                        <option value="mercedes">Soldier</option>
                                                        <option value="audi">hanzo</option>
                                                    </select></td>
                                                <td>Win 98+ / OSX.2+</td>
                                                <td>1.7</td>
                                                <td>A</td>
                                            </tr><tr role="row" class="even">
                                                <td class="sorting_1"><select>
                                                        <option value="volvo">Demo 1</option>
                                                        <option value="saab">Demo 2</option>
                                                        <option value="mercedes">Demo 3</option>
                                                    </select></td>
                                                <td><select>
                                                        <option value="volvo">Genji</option>
                                                        <option value="saab">Mcree</option>
                                                        <option value="mercedes">Soldier</option>
                                                        <option value="audi">hanzo</option>
                                                    </select></td>
                                                <td>Win 98+ / OSX.2+</td>
                                                <td>1.8</td>
                                                <td>A</td>
                                            </tr><tr role="row" class="odd">
                                                <td class="sorting_1"><select>
                                                        <option value="volvo">Demo 1</option>
                                                        <option value="saab">Demo 2</option>
                                                        <option value="mercedes">Demo 3</option>
                                                    </select></td>
                                                <td><select>
                                                        <option value="volvo">Genji</option>
                                                        <option value="saab">Mcree</option>
                                                        <option value="mercedes">Soldier</option>
                                                        <option value="audi">hanzo</option>
                                                    </select></td>
                                                <td>Win 98+ / OSX.2+</td>
                                                <td>1.8</td>
                                                <td>A</td>
                                            </tr><tr role="row" class="even">
                                                <td class="sorting_1"><select>
                                                        <option value="volvo">Demo 1</option>
                                                        <option value="saab">Demo 2</option>
                                                        <option value="mercedes">Demo 3</option>
                                                    </select></td>
                                                <td><select>
                                                        <option value="volvo">Genji</option>
                                                        <option value="saab">Mcree</option>
                                                        <option value="mercedes">Soldier</option>
                                                        <option value="audi">hanzo</option>
                                                    </select></td>
                                                <td>Win 2k+ / OSX.3+</td>
                                                <td>1.9</td>
                                                <td>A</td>
                                            </tr><tr role="row" class="odd">
                                                <td class="sorting_1"><select>
                                                        <option value="volvo">Demo 1</option>
                                                        <option value="saab">Demo 2</option>
                                                        <option value="mercedes">Demo 3</option>
                                                    </select></td>
                                                <td><select>
                                                        <option value="volvo">Genji</option>
                                                        <option value="saab">Mcree</option>
                                                        <option value="mercedes">Soldier</option>
                                                        <option value="audi">hanzo</option>
                                                    </select></td>
                                                <td>OSX.2+</td>
                                                <td>1.8</td>
                                                <td>A</td>
                                            </tr><tr role="row" class="even">
                                                <td class="sorting_1"><select>
                                                        <option value="volvo">Demo 1</option>
                                                        <option value="saab">Demo 2</option>
                                                        <option value="mercedes">Demo 3</option>
                                                    </select></td>
                                                <td><select>
                                                        <option value="volvo">Genji</option>
                                                        <option value="saab">Mcree</option>
                                                        <option value="mercedes">Soldier</option>
                                                        <option value="audi">hanzo</option>
                                                    </select></td>
                                                <td>OSX.3+</td>
                                                <td>1.8</td>
                                                <td>A</td>
                                            </tr><tr role="row" class="odd">
                                                <td class="sorting_1"><select>
                                                        <option value="volvo">Demo 1</option>
                                                        <option value="saab">Demo 2</option>
                                                        <option value="mercedes">Demo 3</option>
                                                    </select></td>
                                                <td><select>
                                                        <option value="volvo">Genji</option>
                                                        <option value="saab">Mcree</option>
                                                        <option value="mercedes">Soldier</option>
                                                        <option value="audi">hanzo</option>
                                                    </select></td>
                                                <td>Win 95+ / Mac OS 8.6-9.2</td>
                                                <td>1.7</td>
                                                <td>A</td>
                                            </tr><tr role="row" class="even">
                                                <td class="sorting_1"><select>
                                                        <option value="volvo">Demo 1</option>
                                                        <option value="saab">Demo 2</option>
                                                        <option value="mercedes">Demo 3</option>
                                                    </select></td>
                                                <td><select>
                                                        <option value="volvo">Genji</option>
                                                        <option value="saab">Mcree</option>
                                                        <option value="mercedes">Soldier</option>
                                                        <option value="audi">hanzo</option>
                                                    </select></td>
                                                <td>Win 98SE+</td>
                                                <td>1.7</td>
                                                <td>A</td>
                                            </tr><tr role="row" class="odd">
                                                <td class="sorting_1"><select>
                                                        <option value="volvo">Demo 1</option>
                                                        <option value="saab">Demo 2</option>
                                                        <option value="mercedes">Demo 3</option>
                                                    </select></td>
                                                <td><select>
                                                        <option value="volvo">Genji</option>
                                                        <option value="saab">Mcree</option>
                                                        <option value="mercedes">Soldier</option>
                                                        <option value="audi">hanzo</option>
                                                    </select></td>
                                                <td>Win 98+ / OSX.2+</td>
                                                <td>1.8</td>
                                                <td>A</td>
                                            </tr><tr role="row" class="even">
                                                <td class="sorting_1"><select>
                                                        <option value="volvo">Demo 1</option>
                                                        <option value="saab">Demo 2</option>
                                                        <option value="mercedes">Demo 3</option>
                                                    </select></td>
                                                <td><select>
                                                        <option value="volvo">Genji</option>
                                                        <option value="saab">Mcree</option>
                                                        <option value="mercedes">Soldier</option>
                                                        <option value="audi">hanzo</option>
                                                    </select></td>
                                                <td>Win 95+ / OSX.1+</td>
                                                <td>1</td>
                                                <td>A</td>
                                            </tr></tbody>                </table></div></div><div class="row"><div class="col-sm-5"><div class="dataTables_info" id="dataTable_info" role="status" aria-live="polite">Showing 1 to 10 of 57 entries</div></div><div class="col-sm-7"><div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate"><ul class="pagination"><li class="paginate_button previous disabled" id="dataTable_previous"><a href="#" aria-controls="dataTable" data-dt-idx="0" tabindex="0">Previous</a></li><li class="paginate_button active"><a href="#" aria-controls="dataTable" data-dt-idx="1" tabindex="0">1</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="2" tabindex="0">2</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="3" tabindex="0">3</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="4" tabindex="0">4</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="5" tabindex="0">5</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="6" tabindex="0">6</a></li><li class="paginate_button next" id="dataTable_next"><a href="#" aria-controls="dataTable" data-dt-idx="7" tabindex="0">Next</a></li></ul></div></div></div></div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.row -->
                <br>
                <div class="row">
                    <div class="col-lg-12 ui-sortable">
                        <div class="box ui-sortable-handle">
                            <header>
                                <div class="icons"><i class="fa fa-table"></i></div>
                                <h5>Tester Assignment</h5>
                            </header>
                            <div id="collapse4" class="body">
                                <div id="dataTable_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer"><div class="row"><div class="col-sm-6"><div class="dataTables_length" id="dataTable_length"><label>Show <select name="dataTable_length" aria-controls="dataTable" class="form-control input-sm"><option value="10">10</option><option value="25">25</option><option value="50">50</option><option value="100">100</option></select> entries</label></div></div><div class="col-sm-6"><div id="dataTable_filter" class="dataTables_filter"><label>Search:<input type="search" class="form-control input-sm" placeholder="" aria-controls="dataTable"></label></div></div></div><div class="row"><div class="col-sm-12"><table id="dataTable" class="table table-bordered table-condensed table-hover table-striped dataTable no-footer" role="grid" aria-describedby="dataTable_info">
                                                <thead>
                                                <tr role="row"><th class="sorting_asc" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Project: activate to sort column descending" style="width: 127px;">Project</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Developer: activate to sort column ascending" style="width: 238px;">Tester</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Deffects ID(s): activate to sort column ascending" style="width: 224px;">Test Case ID(s)</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="assigner ID(S): activate to sort column ascending" style="width: 143px;">assigner ID(S)</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending" style="width: 73px;">Status</th></tr>
                                                </thead>
                                                <tbody>

                                                <tr role="row" class="odd">
                                                    <td class="sorting_1"><select>
                                                            <option value="volvo">Demo 1</option>
                                                            <option value="saab">Demo 2</option>
                                                            <option value="mercedes">Demo 3</option>
                                                        </select></td>
                                                    <td><select>
                                                            <option value="volvo">Rein</option>
                                                            <option value="saab">Orisa</option>
                                                            <option value="mercedes">Zara</option>
                                                            <option value="audi">Simon</option>
                                                        </select></td>
                                                    <td>Win 98+ / OSX.2+</td>
                                                    <td>1.7</td>
                                                    <td>A</td>
                                                </tr><tr role="row" class="even">
                                                    <td class="sorting_1"><select>
                                                            <option value="volvo">Demo 1</option>
                                                            <option value="saab">Demo 2</option>
                                                            <option value="mercedes">Demo 3</option>
                                                        </select></td>
                                                    <td><select>
                                                            <option value="volvo">Rein</option>
                                                            <option value="saab">Orisa</option>
                                                            <option value="mercedes">Zara</option>
                                                            <option value="audi">Simon</option>
                                                        </select></td>
                                                    <td>Win 98+ / OSX.2+</td>
                                                    <td>1.8</td>
                                                    <td>A</td>
                                                </tr><tr role="row" class="odd">
                                                    <td class="sorting_1"><select>
                                                            <option value="volvo">Demo 1</option>
                                                            <option value="saab">Demo 2</option>
                                                            <option value="mercedes">Demo 3</option>
                                                        </select></td>
                                                    <td><select>
                                                            <option value="volvo">Rein</option>
                                                            <option value="saab">Orisa</option>
                                                            <option value="mercedes">Zara</option>
                                                            <option value="audi">Simon</option>
                                                        </select></td>
                                                    <td>Win 98+ / OSX.2+</td>
                                                    <td>1.8</td>
                                                    <td>A</td>
                                                </tr><tr role="row" class="even">
                                                    <td class="sorting_1"><select>
                                                            <option value="volvo">Demo 1</option>
                                                            <option value="saab">Demo 2</option>
                                                            <option value="mercedes">Demo 3</option>
                                                        </select></td>
                                                    <td><select>
                                                            <option value="volvo">Rein</option>
                                                            <option value="saab">Orisa</option>
                                                            <option value="mercedes">Zara</option>
                                                            <option value="audi">Simon</option>
                                                        </select></td>
                                                    <td>Win 2k+ / OSX.3+</td>
                                                    <td>1.9</td>
                                                    <td>A</td>
                                                </tr><tr role="row" class="odd">
                                                    <td class="sorting_1"><select>
                                                            <option value="volvo">Demo 1</option>
                                                            <option value="saab">Demo 2</option>
                                                            <option value="mercedes">Demo 3</option>
                                                        </select></td>
                                                    <td><select>
                                                            <option value="volvo">Rein</option>
                                                            <option value="saab">Orisa</option>
                                                            <option value="mercedes">Zara</option>
                                                            <option value="audi">Simon</option>
                                                        </select></td>
                                                    <td>OSX.2+</td>
                                                    <td>1.8</td>
                                                    <td>A</td>
                                                </tr><tr role="row" class="even">
                                                    <td class="sorting_1"><select>
                                                            <option value="volvo">Demo 1</option>
                                                            <option value="saab">Demo 2</option>
                                                            <option value="mercedes">Demo 3</option>
                                                        </select></td>
                                                    <td><select>
                                                            <option value="volvo">Rein</option>
                                                            <option value="saab">Orisa</option>
                                                            <option value="mercedes">Zara</option>
                                                            <option value="audi">Simon</option>
                                                        </select></td>
                                                    <td>OSX.3+</td>
                                                    <td>1.8</td>
                                                    <td>A</td>
                                                </tr><tr role="row" class="odd">
                                                    <td class="sorting_1"><select>
                                                            <option value="volvo">Demo 1</option>
                                                            <option value="saab">Demo 2</option>
                                                            <option value="mercedes">Demo 3</option>
                                                        </select></td>
                                                    <td><select>
                                                            <option value="volvo">Rein</option>
                                                            <option value="saab">Orisa</option>
                                                            <option value="mercedes">Zara</option>
                                                            <option value="audi">Simon</option>
                                                        </select></td>
                                                    <td>Win 95+ / Mac OS 8.6-9.2</td>
                                                    <td>1.7</td>
                                                    <td>A</td>
                                                </tr><tr role="row" class="even">
                                                    <td class="sorting_1"><select>
                                                            <option value="volvo">Demo 1</option>
                                                            <option value="saab">Demo 2</option>
                                                            <option value="mercedes">Demo 3</option>
                                                        </select></td>
                                                    <td><select>
                                                            <option value="volvo">Rein</option>
                                                            <option value="saab">Orisa</option>
                                                            <option value="mercedes">Zara</option>
                                                            <option value="audi">Simon</option>
                                                        </select></td>
                                                    <td>Win 98SE+</td>
                                                    <td>1.7</td>
                                                    <td>A</td>
                                                </tr><tr role="row" class="odd">
                                                    <td class="sorting_1"><select>
                                                            <option value="volvo">Demo 1</option>
                                                            <option value="saab">Demo 2</option>
                                                            <option value="mercedes">Demo 3</option>
                                                        </select></td>
                                                    <td><select>
                                                            <option value="volvo">Rein</option>
                                                            <option value="saab">Orisa</option>
                                                            <option value="mercedes">Zara</option>
                                                            <option value="audi">Simon</option>
                                                        </select></td>
                                                    <td>Win 98+ / OSX.2+</td>
                                                    <td>1.8</td>
                                                    <td>A</td>
                                                </tr><tr role="row" class="even">
                                                    <td class="sorting_1"><select>
                                                            <option value="volvo">Demo 1</option>
                                                            <option value="saab">Demo 2</option>
                                                            <option value="mercedes">Demo 3</option>
                                                        </select></td>
                                                    <td><select>
                                                            <option value="volvo">Rein</option>
                                                            <option value="saab">Orisa</option>
                                                            <option value="mercedes">Zara</option>
                                                            <option value="audi">Simon</option>
                                                        </select></td>
                                                    <td>Win 95+ / OSX.1+</td>
                                                    <td>1</td>
                                                    <td>A</td>
                                                </tr></tbody>                </table></div></div><div class="row"><div class="col-sm-5"><div class="dataTables_info" id="dataTable_info" role="status" aria-live="polite">Showing 1 to 10 of 57 entries</div></div><div class="col-sm-7"><div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate"><ul class="pagination"><li class="paginate_button previous disabled" id="dataTable_previous"><a href="#" aria-controls="dataTable" data-dt-idx="0" tabindex="0">Previous</a></li><li class="paginate_button active"><a href="#" aria-controls="dataTable" data-dt-idx="1" tabindex="0">1</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="2" tabindex="0">2</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="3" tabindex="0">3</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="4" tabindex="0">4</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="5" tabindex="0">5</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="6" tabindex="0">6</a></li><li class="paginate_button next" id="dataTable_next"><a href="#" aria-controls="dataTable" data-dt-idx="7" tabindex="0">Next</a></li></ul></div></div></div></div>
                            </div>
                        </div>
                    </div>
                </div>
            <?  } else if ($role == 'Tester') { ?>
            <!--End Datatables-->
                <div class="row">
                    <div class="col-lg-12 ui-sortable">
                        <div class="box ui-sortable-handle">
                            <header>
                                <div class="icons"><i class="fa fa-table"></i></div>
                                <h5>Testing Assigned To You</h5>
                            </header>
                            <div id="collapse4" class="body">
                                <div id="dataTable_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer"><div class="row"><div class="col-sm-6"><div class="dataTables_length" id="dataTable_length"><label>Show <select name="dataTable_length" aria-controls="dataTable" class="form-control input-sm"><option value="10">10</option><option value="25">25</option><option value="50">50</option><option value="100">100</option></select> entries</label></div></div><div class="col-sm-6"><div id="dataTable_filter" class="dataTables_filter"><label>Search:<input type="search" class="form-control input-sm" placeholder="" aria-controls="dataTable"></label></div></div></div><div class="row"><div class="col-sm-12"><table id="dataTable" class="table table-bordered table-condensed table-hover table-striped dataTable no-footer" role="grid" aria-describedby="dataTable_info">
                                                <thead>
                                                <tr role="row"><th class="sorting_asc" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Project: activate to sort column descending" style="width: 127px;">Project</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Developer: activate to sort column ascending" style="width: 238px;">Tester</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Deffects ID(s): activate to sort column ascending" style="width: 224px;">Deffects ID(s)</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="assigner ID(S): activate to sort column ascending" style="width: 143px;">assigner ID(S)</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending" style="width: 73px;">Status</th></tr>
                                                </thead>
                                                <tbody>
                                                <tr role="row" class="odd">
                                                    <td class="sorting_1">Demo 1</td>
                                                    <td>Simon</td>
                                                    <td>Win 95+ / Mac OS 8.6-9.2</td>
                                                    <td>1.7</td>
                                                    <td>A</td>
                                                </tr><tr role="row" class="even">
                                                    <td class="sorting_1">Demo 2</td>
                                                    <td>Simon</td>
                                                    <td>Win 95+ / OSX.1+</td>
                                                    <td>1</td>
                                                    <td>A</td>
                                                </tr></tbody>                </table></div></div><div class="row"><div class="col-sm-5"><div class="dataTables_info" id="dataTable_info" role="status" aria-live="polite">Showing 1 to 10 of 57 entries</div></div><div class="col-sm-7"><div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate"><ul class="pagination"><li class="paginate_button previous disabled" id="dataTable_previous"><a href="#" aria-controls="dataTable" data-dt-idx="0" tabindex="0">Previous</a></li><li class="paginate_button active"><a href="#" aria-controls="dataTable" data-dt-idx="1" tabindex="0">1</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="2" tabindex="0">2</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="3" tabindex="0">3</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="4" tabindex="0">4</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="5" tabindex="0">5</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="6" tabindex="0">6</a></li><li class="paginate_button next" id="dataTable_next"><a href="#" aria-controls="dataTable" data-dt-idx="7" tabindex="0">Next</a></li></ul></div></div></div></div>
                            </div>
                        </div>
                    </div>
                </div>

            <?  } else { ?>

                <div class="row">
                    <div class="col-lg-12 ui-sortable">
                        <div class="box ui-sortable-handle">
                            <header>
                                <div class="icons"><i class="fa fa-table"></i></div>
                                <h5>Development Assigned To You</h5>
                            </header>
                            <div id="collapse4" class="body">
                                <div id="dataTable_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer"><div class="row"><div class="col-sm-6"><div class="dataTables_length" id="dataTable_length"><label>Show <select name="dataTable_length" aria-controls="dataTable" class="form-control input-sm"><option value="10">10</option><option value="25">25</option><option value="50">50</option><option value="100">100</option></select> entries</label></div></div><div class="col-sm-6"><div id="dataTable_filter" class="dataTables_filter"><label>Search:<input type="search" class="form-control input-sm" placeholder="" aria-controls="dataTable"></label></div></div></div><div class="row"><div class="col-sm-12"><table id="dataTable" class="table table-bordered table-condensed table-hover table-striped dataTable no-footer" role="grid" aria-describedby="dataTable_info">
                                                <thead>
                                                <tr role="row"><th class="sorting_asc" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Project: activate to sort column descending" style="width: 127px;">Project</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Developer: activate to sort column ascending" style="width: 238px;">Developer</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Deffects ID(s): activate to sort column ascending" style="width: 224px;">Deffects ID(s)</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="assigner ID(S): activate to sort column ascending" style="width: 143px;">assigner ID(S)</th><th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending" style="width: 73px;">Status</th></tr>
                                                </thead>
                                                <tbody>

                                                <tr role="row" class="odd">
                                                    <td class="sorting_1">Demo 1</td>
                                                    <td>Chetan</td>
                                                    <td>Win 98+ / OSX.2+</td>
                                                    <td>1.7</td>
                                                    <td>A</td>
                                                </tr><tr role="row" class="odd">
                                                    <td class="sorting_1">Demo 1</td>
                                                    <td>Chetan</td>
                                                    <td>Win 98+ / OSX.2+</td>
                                                    <td>1.8</td>
                                                    <td>A</td>
                                                </tr><tr role="row" class="even">
                                                    <td class="sorting_1">Demo 3</td>
                                                    <td>Chetan</td>
                                                    <td>Win 95+ / OSX.1+</td>
                                                    <td>1</td>
                                                    <td>A</td>
                                                </tr></tbody>                </table></div></div><div class="row"><div class="col-sm-5"><div class="dataTables_info" id="dataTable_info" role="status" aria-live="polite">Showing 1 to 10 of 57 entries</div></div><div class="col-sm-7"><div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate"><ul class="pagination"><li class="paginate_button previous disabled" id="dataTable_previous"><a href="#" aria-controls="dataTable" data-dt-idx="0" tabindex="0">Previous</a></li><li class="paginate_button active"><a href="#" aria-controls="dataTable" data-dt-idx="1" tabindex="0">1</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="2" tabindex="0">2</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="3" tabindex="0">3</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="4" tabindex="0">4</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="5" tabindex="0">5</a></li><li class="paginate_button "><a href="#" aria-controls="dataTable" data-dt-idx="6" tabindex="0">6</a></li><li class="paginate_button next" id="dataTable_next"><a href="#" aria-controls="dataTable" data-dt-idx="7" tabindex="0">Next</a></li></ul></div></div></div></div>
                            </div>
                        </div>
                    </div>
                </div>
            <?  } ?>

        </div>
        <!-- /.inner -->
    </div>
    <!-- /.outer -->
</div>
