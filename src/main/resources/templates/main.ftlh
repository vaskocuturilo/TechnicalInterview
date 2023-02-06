<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Technical interview questions</title>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container text-center">
    <h1>Technical interview questions.</h1>
</div>
<div class="m-3">
    <h5>Add a question for the technical interview to list</h5>
    <div class="form-group row">
        <form action="/api/v1/questions" method="POST">
            <table border="1px" id="table_add_task">
                <tr>
                    <td>
                        <strong><label class="col-4 col-form-label" id="task_name_label">Task
                                name: </label></strong>
                        <div class="col-8">
                            <input type="text" id="task_name_input" name="taskName" required minlength="1"
                                   maxlength="255">
                        </div>
                    </td>
                    <td>
                        <strong><label class="col-4 col-form-label"
                                       id="description_label">Description:</label></strong>
                        <div class="col-8">
                            <input type="text" id="description_input" name="description" required minlength="1"
                                   maxlength="255">
                        </div>
                    </td>
                </tr>
            </table>
            <br>
            <div>
                <button type="submit" class="btn btn-primary" id="button_add_question">Add question</button>
            </div>
        </form>
    </div>
</div>
<br>
<div class="m-3">
    <h5>Upload the file with technical interview questions:</h5>
    <div class="form-group row">
        <form action="/api/v1/questions/upload" method="POST" enctype="multipart/form-data">
            <table border="1px" id="upload_file_table">
                <tr>
                    <td>
                        <div class="col-8">
                            <input type="file" id="upload_file" name="file" accept="application/JSON">
                        </div>
                    </td>
                    <td>
                        <div class="col-8">
                            <button type="submit" class="btn btn-primary" id="upload_file_button">upload
                            </button>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div>
    <h3 id="upload_message"><#if message??>${message}</#if></h3>
</div>
<div class="m-3">
    <div class="form-group row">
        <h5>Reset all completed technical interview questions:</h5>
        <form action="/api/v1/reset" method="GET">
            <button type="submit" class="btn btn-primary" id="reset_button">Reset all questions</button>
        </form>
    </div>
</div>
<div class="m-3">
    <div class="container text-center">
        <h5>Technical interview questions: (questions count : ${entityListSize})</h5>
    </div>
</div>
<div class="m-3">
    <div class="container text-center">
        <h5>Technical interview questions in status pass is: (questions count : <#if pass??>${pass}</#if>)</h5>
    </div>
</div>
<div class="m-3">
    <div class="container text-center">
        <h5>Technical interview questions in status fail is: (questions count : <#if fail??>${fail}</#if>)</h5>
    </div>
</div>
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
                    <td><a href="${'/api/v1/edit/'+task.taskId}">Edit</a></td>
                    <td><a href="${'/api/v1/complete/'+task.taskId}">Complete</a></td>
                    <td><a href="${'/api/v1/delete/'+task.taskId}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
<br>
<div class="form-group row">
    <form action="/api/v1/random" method="GET">
        <table>
            <tr>
                <td>
                </td>
            </tr>
        </table>
        <div class="col-8">
            <div class="container text-center">
                <button type="submit" class="btn btn-primary" id="random_button" value="Random">Click for
                    random
                    question
                </button>
            </div>
        </div>
    </form>
</div>
</body>
</html>