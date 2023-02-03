<!DOCTYPE html>
<head xmlns="http://www.w3.org/1999/html">
    <meta charset="ISO-8859-1">
    <title>Your question is:</title>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container text-center">
    <div>
        <h1>Your question is:</h1>
    </div>
    <div>
        <table class="table table-striped table-bordered">
            <thead class="table-dark">
            <tr>
                <td>Task Name</td>
                <td>Description</td>
                <td>Completed</td>
                <td>Do you complete task ?</td>
            </tr>
            </thead>
            <tbody>
            <#list randomQuestion as questions>
                <tr>
                    <td>${questions.taskName}</td>
                    <td>${questions.description}</td>
                    <td>${questions.completed?string("Yes","No")}</td>
                    <td>
                        <a href="${'/api/v1/complete/'+questions.taskId}">Complete</a>
                        <br>
                        <a href="${'/api/v1/questions'}">Not complete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>