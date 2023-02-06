<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Technical interview questions</title>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <style type="text/css">
        .form-style-5 {
            max-width: 900px;
            padding: 10px 20px;
            background: #f4f7f8;
            margin: 10px auto;
            padding: 20px;
            background: #f4f7f8;
            border-radius: 8px;
            font-family: Georgia, "Times New Roman", Times, serif;
        }

        .form-style-5 fieldset {
            border: none;
        }

        .form-style-5 legend {
            font-size: 1.4em;
            margin-bottom: 10px;
        }

        .form-style-5 label {
            display: block;
            margin-bottom: 8px;
        }

        .form-style-5 input[type="text"],
        .form-style-5 input[type="date"],
        .form-style-5 input[type="datetime"],
        .form-style-5 input[type="email"],
        .form-style-5 input[type="number"],
        .form-style-5 input[type="search"],
        .form-style-5 input[type="time"],
        .form-style-5 input[type="url"],
        .form-style-5 textarea,
        .form-style-5 select {
            font-family: Georgia, "Times New Roman", Times, serif;
            background: rgba(255, 255, 255, .1);
            border: none;
            border-radius: 4px;
            font-size: 16px;
            margin: 0;
            outline: 0;
            padding: 7px;
            width: 100%;
            box-sizing: border-box;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            background-color: #e8eeef;
            color: #8a97a0;
            -webkit-box-shadow: 0 1px 0 rgba(0, 0, 0, 0.03) inset;
            box-shadow: 0 1px 0 rgba(0, 0, 0, 0.03) inset;
            margin-bottom: 30px;

        }

        .form-style-5 input[type="text"]:focus,
        .form-style-5 input[type="date"]:focus,
        .form-style-5 input[type="datetime"]:focus,
        .form-style-5 input[type="email"]:focus,
        .form-style-5 input[type="number"]:focus,
        .form-style-5 input[type="search"]:focus,
        .form-style-5 input[type="time"]:focus,
        .form-style-5 input[type="url"]:focus,
        .form-style-5 textarea:focus,
        .form-style-5 select:focus {
            background: #d2d9dd;
        }

        .form-style-5 select {
            -webkit-appearance: menulist-button;
            height: 35px;
        }

        .form-style-5 .number {
            background: #FF9900;
            color: #fff;
            height: 30px;
            width: 30px;
            display: inline-block;
            font-size: 0.8em;
            margin-right: 4px;
            line-height: 30px;
            text-align: center;
            text-shadow: 0 1px 0 rgba(255, 255, 255, 0.2);
            border-radius: 15px 15px 15px 0px;
        }

        .form-style-5 input[type="submit"],
        .form-style-5 input[type="button"] {
            position: relative;
            display: block;
            padding: 19px 39px 18px 39px;
            color: #FFF;
            margin: 0 auto;
            background: #FF9900;
            font-size: 18px;
            text-align: center;
            font-style: normal;
            width: 100%;
            border-width: 1px 1px 3px;
            margin-bottom: 10px;
            height: 55px;
            border-radius: 15px;
        }

        .form-style-5 input[type="submit"]:hover,
        .form-style-5 input[type="button"]:hover {
            background: grey;
        }

        .formSpacing {
            margin-top: 10px;
        }

        .inputWidth1 {
            width: 80% !important;
        }

        .inputWidth2 {
            width: 70% !important;
        }
    </style>
</head>
<body>
<div class="container text-center">
    <h1>Technical interview questions.</h1>
</div>
<div class="form-style-5">
    <form action="/api/v1/questions" method="POST">
        <fieldset>
            <legend><span class="number">1.</span>Add technical interview questions:</legend>
            <div style="display: inline-block;">
                <strong><label class="formSpacing" for="job" id="label_title">Title of question:</label></strong>
                <input class="inputWidth1" type="text" id="task_name_input" name="taskName" required
                       minlength="1"
                       maxlength="255" placeholder="Title of question *">
            </div>
            <div style="display: inline-block; margin-left:20px">
                <strong><label class="formSpacing" for="job" id="label_description">Description of
                        question:</label></strong>
                <input class="inputWidth1" type="text" id="description_input" name="description" required minlength="1"
                       maxlength="255" placeholder="Description of question *">
            </div>
            <div style="display: inline-block; margin-left:15px">
                <input type="submit" id="button_add_question" value="Add question"/>
            </div>
        </fieldset>
    </form>
</div>
<div class="form-style-5">
    <form action="/api/v1/questions/upload" method="POST" enctype="multipart/form-data">
        <legend><span class="number">2.</span>Upload technical interview questions:</legend>
        <div style="display: inline-block; margin-left:20px">
            <label class="formSpacing" for="job" id="label_add_json">Add JSON file:</label>
            <input type="file" id="upload_file" name="file" accept="application/JSON">
        </div>
        <div style="display: inline-block; margin-left:15px">
            <input type="submit" id="upload_questions" value="Upload questions"/>
        </div>
        </fieldset>
    </form>
</div>
<div class="form-style-5">
    <div class="container text-center">
        <legend><#if message??><span class="number" id="info_icon">INF</span></legend>
        <strong><p id="info_message">${message}</#if></p></strong>
    </div>
</div>
<div class="form-style-5">
    <form>
        <fieldset>
            <legend><span class="number">3.</span>Statistics for technical interview questions:</legend>
            <div style="display: inline-block; margin-left:20px">
                <section>
                    <label class="formSpacing" for="job" id="label_total">Total:</label>
                    <h1 class="jumbo" id="total_count">${entityListSize}</h1>
                </section>
            </div>
            <div style="display: inline-block; margin-left:20px">
                <section>
                    <label class="formSpacing" for="job" id="label_pass">Pass:</label>
                    <h1 class="jumbo" id="pass_count"><#if pass??>${pass}</#if></h1>
                </section>
            </div>
            <div style="display: inline-block; margin-left:20px">
                <section>
                    <label class="formSpacing" for="job" id="label_fail">Fail:</label>
                    <h1 class="jumbo" id="fail_count"> <#if fail??>${fail}</#if></h1>
                </section>
            </div>
        </fieldset>
    </form>
</div>
<div class="form-style-5">
    <fieldset>
        <legend><span class="number">4.</span>List of technical interview questions:</legend>
        <div class="container text-center">
            <div>
                <table class="table table-striped table-bordered">
                    <thead class="table-dark">
                    <tr>
                        <td>Task Name</td>
                        <td>Description</td>
                        <td>Completed</td>
                        <td>Edit task</td>
                        <td>Complete task</td>
                        <td>Delete from list</td>
                    </tr>
                    </thead>
                    <tbody>
                    <#list entityList as task>
                        <tr>
                            <td><#if task.taskName??>${task.taskName}</#if></td>
                            <td><#if task.description??>${task.description}</#if></td>
                            <td><#if task.completed??>${task.completed?string("Pass","Fail")}</#if></td>
                            <td><a href="${'/api/v1/edit/'+task.taskId}" id="cell_edit">Edit</a></td>
                            <td><a href="${'/api/v1/complete/'+task.taskId}" id="cell_complete">Complete</a></td>
                            <td><a href="${'/api/v1/delete/'+task.taskId}" id="cell_delete"
                                   onclick="return confirm('Are you sure?')">Delete</a>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </fieldset>
</div>
<div class="form-style-5">
    <fieldset>
        <div style="display: inline-block; margin-left:100px">
            <form action="/api/v1/random" method="GET">
                <div style="display: inline-block; margin-left:15px">
                    <input type="submit" class="btn btn-primary" id="random_button" value="Take random question"/>
                </div>
            </form>
        </div>

        <div style="display: inline-block; margin-right:0px">
            <form action="/api/v1/reset" method="GET">
                <div style="display: inline-block; margin-left:15px">
                    <input type="submit" class="btn btn-primary" id="reset_button" value="Reset all questions"/>
                </div>
            </form>
        </div>
        <div style="display: inline-block; margin-right:0px">
            <form action="#" method="#">
                <div style="display: inline-block; margin-left:15px">
                    <input type="submit" class="btn btn-primary" id="delete_button" value="Delete all questions"/>
                </div>
            </form>
        </div>
    </fieldset>
</div>
</body>
</html>