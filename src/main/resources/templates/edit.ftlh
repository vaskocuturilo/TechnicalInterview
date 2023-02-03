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
<h1>Edit Technical interview questions.</h1>
<form action="${'/api/v1/edit/'+id}" method="PUT">
    <table border="1px" id="table_edit_task">
        <tr>
            <td>
                <label id="id_label">id: </label>
                <input type="text" id="id_input" name="id" value="${id}" required
                       minlength="1" maxlength="255">
            </td>
            <td>
                <label id="task_name_label">Task name: </label>
                <input type="text" id="task_name_input" name="taskName"
                       required minlength="1"
                       maxlength="255">
            </td>
            <td>
                <label id="description_label">Description:</label>
                <input type="text" id="description_input" name="description" required minlength="1" maxlength="255">
            </td>
            <td>
                <input type="submit" id="add_task_button" value="Add task">
            </td>
        </tr>
    </table>
</form>
</body>
</html>