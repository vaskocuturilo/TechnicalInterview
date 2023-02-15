<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Technical interview questions</title>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <#include "style.css">
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="form-style-5">
    <form action="#" method="#">
        <fieldset>
            <div style="display: inline-block;">
                <strong><label class="formSpacing">One Time Password:</label></strong>
                <input class="one_time_password" type="text" name="otp" id="one_time_password" minlength="1"
                       maxlength="6" placeholder="One Time Password *"
                       onkeypress="return (event.charCode == 8 || event.charCode == 0 || event.charCode == 13) ? null : event.charCode >= 48 && event.charCode <= 57"
                       required/>
            </div>
            <div style="display: inline-block; margin-left:15px">
                <input type="submit" id="verify_one_time_password" value="Verify one time password."/>
            </div>
        </fieldset>
    </form>
</div>
<div class="form-style-5">
    <div class="container text-center">
        <legend><#if otp_message??><span class="number" id="info_icon">INF</span></legend>
        <strong><p id="info_message">${otp_message}</#if></p></strong>
    </div>
</div>
</body>
</html>