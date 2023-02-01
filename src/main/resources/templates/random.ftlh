<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Technical interview questions</title>
    <link rel="stylesheet" type="text/css" href="/css/style.x47307.css"/>
</head>
<body>
<h2>Technical interview questions: (questions count : ${randomQuestionSize})</h2>
<table border="1px" id="list_tasks_table">
    <tr>
        <td>Id</td>
        <td>Task Name</td>
        <td>Description</td>
        <td>Completed</td>
        <td>Complete task</td>
    </tr>
    <#list randomQuestion as questions>
        <tr>
            <td>${questions.taskId}</td>
            <td>${questions.taskName}</td>
            <td>${questions.description}</td>
            <td>${questions.completed?string("Yes","No")}</td>
            <td><a href="${'/api/v1/complete/'+questions.taskId}">Complete</a></td>
        </tr>
    </#list>
</table>
</body>
</html>