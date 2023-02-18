<!DOCTYPE html>
<head>
    <meta charset="ISO-8859-1">
    <title>List Users</title>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container text-center">
    <div>
        <form action="/logout" method="post">
            <input type="submit" value="Sign Out"/>
        </form>
    </div>
    <div>
        <h1>List of Users</h1>
    </div>
    <div class="form-style-5">
        <fieldset>
            <div class="container text-center">
                <div>
                    <table class="table table-striped table-bordered" id="main_table">
                        <thead class="table-dark">
                        <tr>
                            <td>Email</td>
                            <td>Gender</td>
                            <td>Active</td>
                            <td>Do you want to active?</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list userList as users>
                            <tr>
                                <td><#if users.email??>${users.email}</#if></td>
                                <td><#if users.gender??>${users.gender}</#if></td>
                                <td><#if users.isActive()??>${users.isActive()?string("Active","Not Active")}</#if></td>
                                <td><a href="${'/api/v1/admin/active/'+ users.id}"
                                       id="cell_active" onclick="return confirm('Are you sure?')">Active</a>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </fieldset>
    </div>
</div>
</body>
</html>