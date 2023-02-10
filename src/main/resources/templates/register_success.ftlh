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