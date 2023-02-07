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
    <form action="/api/v1/questions" method="GET">
        <fieldset>
            <legend><span class="number">1.</span>List of technical interview questions</legend>
            <div style="display: inline-block; margin-left:15px">
                <input type="submit" id="list_of_questions" value="List of questions."/>
            </div>
        </fieldset>
    </form>
</div>
<div class="form-style-5">
    <fieldset>
        <legend><span class="number">2.</span>Sign-In</legend>
        <div style="display: inline-block; margin-left:15px">
            <input type="submit" id="login_page" value="Sign-In"/>
        </div>
    </fieldset>
</div>

<div class="form-style-5">
    <fieldset>
        <legend><span class="number">3.</span>Sign-Up</legend>
        <div style="display: inline-block; margin-left:15px">
            <input type="submit" id="list_of_questions" value="Sign-up"/>
        </div>
    </fieldset>
</div>
<div class="form-style-5">
    <fieldset>
        <legend><span class="number">4.</span>Help</legend>
        <div style="display: inline-block; margin-left:15px">
            <input type="submit" id="list_of_questions" value="Help"/>
        </div>
    </fieldset>
</div>
</body>
</html>
