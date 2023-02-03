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
<h1>Technical interview questions.</h1>
<h3>Add a question for the technical interview to list</h3>
<form action="/api/v1/add" method="POST">
    <table border="1px" id="table_add_task">
        <tr>
            <td>
                <label id="task_name_label">Task name: </label>
                <input type="text" id="task_name_input" name="taskName" required minlength="1" maxlength="255">
            </td>
            <td>
                <label id="description_label">Description:</label>
                <input type="text" id="description_input" name="description" required minlength="1" maxlength="255">
            </td>
            <td>
                <label id="complete_label">Complete:</label>
                <input type="checkbox" id="complete_checkbox" name="complete_checkbox" disabled>
            </td>
            <td>
                <input type="submit" id="add_task_button" value="Add task">
            </td>
        </tr>
    </table>
</form>
<br>
<br>
<h2>Upload the file with technical interview questions:</h2>
<form action="/api/v1/upload" method="POST" enctype="multipart/form-data">
    <table border="1px" id="upload_file_table">
        <tr>
            <td>
                <input type="file" id="upload_file" name="upload" accept="application/JSON">
            </td>
            <td>
                <input type="submit" id="upload_file_button" value="upload file">
            </td>
        </tr>
    </table>
</form>
<br>
<div>
    <h3 id="upload_message"><#if message??>${message}</#if></h3>
</div>
<br>
<h2>Reset all completed technical interview questions:</h2>
<form action="/api/v1/reset" method="GET">
    <input type="submit" id="reset_button" value="Reset">
</form>
<br>
<h2>Technical interview questions: (questions count : ${entityListSize})</h2>
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
                <td><#if task.completed??>${task.completed?string("Yes","No")}</#if></td>
                <td><a href="${'/api/v1/edit/'+task.taskId}">Edit</a></td>
                <td><a href="${'/api/v1/complete/'+task.taskId}">Complete</a></td>
                <td><a href="${'/api/v1/delete/'+task.taskId}">Delete</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
<br>
<br>
<br>
<form action="/api/v1/random" method="GET">
    <table>
        <tr>
            <td>
                <input type="submit" id="random_button" value="Random">
            </td>
        </tr>
    </table>
</form>
</body>
</html>