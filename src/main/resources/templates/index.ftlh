<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Technical interview questions.</title>
    <link rel="stylesheet" type="text/css" href="/css/style.x47307.css"/>
</head>
<body>
<h1>Technical interview questions.</h1>
<h3>Add a question for the technical interview to list</h3>
<form action="/add" method="POST">
    <table border="1px">
        <tr>
            <td>
                <label>Task name: </label>
                <input type="text" name="taskName">
            </td>
            <td>
                <label>Description:</label>
                <input type="text" name="description">
            </td>
            <td>
                <input type="submit" value="Add task">
            </td>
        </tr>
    </table>
</form>
<br>
<form action="/upload" method="POST">
    <table border="1px">
        <tr>
            <td>
                <input type="file" id="upload_file" name="upload" accept="json/yaml">
            </td>
            <td>
                <input type="submit" value="upload file">
            </td>
        </tr>
    </table>
</form>

<br>
<h2>Technical interview questions: (questions count : ${entityListSize})</h2>
<table border="1px">
    <tr>
        <td>Id</td>
        <td>Task Name</td>
        <td>Description</td>
        <td>Completed</td>
        <td>Edit task</td>
        <td>Complete task</td>
        <td>Delete from list</td>
    </tr>
    <#list entityList as task>
        <tr>
            <td>${task.taskId}</td>
            <td>${task.taskName}</td>
            <td>${task.description}</td>
            <td>${task.completed?string("Yes","No")}</td>
            <td><a href="${'/#/'+task.taskId}">Edit</a></td>
            <td><a href="${'/complete/'+task.taskId}">Complete</a></td>
            <td><a href="${'/delete/'+task.taskId}">Delete</a></td>
        </tr>
    </#list>
</table>
<br>
<br>
<br>
<form action="/randomTask" method="GET">
    <table border="1px">
        <tr>
            <td>
                <input type="submit" value="Random">
            </td>
        </tr>
    </table>
</form>
<h2>The technical interview question is -> ${randomTechnicalInterviewQuestion!""}</h2>
</table>
</body>
</html>