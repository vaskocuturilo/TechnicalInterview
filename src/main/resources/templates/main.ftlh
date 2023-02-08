<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Technical interview questions</title>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <#include "style.css">
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="right">
    <div>
        <form action="/logout" method="POST">
            <div style="display: inline-block; margin-left:15px">
                <input type="submit" id="button_logout" value="Log out"/>
            </div>
        </form>
    </div>
</div>
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
                <input class="inputWidth1" type="text" id="description_input" name="description" required
                       minlength="1"
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
                <table class="table table-striped table-bordered" id="main_table">
                    <thead class="table-dark">
                    <tr>
                        <td>Title</td>
                        <td>Description</td>
                        <td>Completed</td>
                        <td>Edit question</td>
                        <td>Complete question</td>
                        <td>Delete</td>
                    </tr>
                    </thead>
                    <tbody>
                    <#list entityList as questions>
                        <tr>
                            <td><#if questions.taskName??>${questions.taskName}</#if></td>
                            <td><#if questions.description??>${questions.description}</#if></td>
                            <td><#if questions.completed??>${questions.completed?string("Pass","Fail")}</#if></td>
                            <td><a href="${'/api/v1/edit/'+questions.taskId}" id="cell_edit">Edit</a></td>
                            <td><a href="${'/api/v1/complete/'+questions.taskId}" id="cell_complete">Complete</a></td>
                            <td><a href="${'/api/v1/delete/'+questions.taskId}" id="cell_delete"
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
            <form action="/api/v1/questions/delete/all" method="POST">
                <div style="display: inline-block; margin-left:15px">"
                    <input type="submit" class="btn btn-primary" id="delete_button" value="Delete all questions"
                           onclick="return confirm('Are you sure?')"/>
                </div>
            </form>
        </div>
    </fieldset>
</div>
</body>
</html>