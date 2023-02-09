<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Technical interview questions</title>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <#include "style.css">
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>
        function clearForm() {
            document.getElementById("myForm").reset();
        }
    </script>
</head>
<body>
<div class="form-style-5">
    <form id="myForm" action="#" method="POST">
        <fieldset>
            <legend><span class="number">INF</span> Please Sign Up</legend>
            <div style="display: inline-block;">
                <strong><label class="formSpacing" for="job">User Name:</label></strong>
                <input class="inputWidth1" type="text" name="first_name" id="first_name" minlength="1"
                       maxlength="25" placeholder="User Name *">
            </div>
            <div style="display: inline-block; margin-left:20px">
                <strong><label for="job">Password:</label></strong>
                <input class="inputWidth1" type="password" name="password" id="password" minlength="1"
                       maxlength="25" placeholder="Password">
            </div>
            <div style="display: inline-block;">
                <label class="formSpacing" for="gender">Gender</label>
                <select id="gender" name="Gender" required>
                    <option value="">Select a Gender *</option>
                    <option value="PCP">Male</option>
                    <option value="Pediatrics">Female</option>
                </select>
            </div>
        </fieldset>
    </form>
    <div style="display: inline-block; margin-left:15px">
        <input type="submit" value="Sign Up" onclick="return confirm('This functionality is not ready yet')">
    </div>
    <div style="display: inline-block; margin-left:85px">
        <input type="button" value=" Clear " onClick="clearForm()">
    </div>
</div>
</body>
</html>