<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #d9534f;
        }
        .error-info {
            margin-bottom: 20px;
        }
        .error-info span {
            font-weight: bold;
        }
        pre {
            background-color: #f9f9f9;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            overflow-x: auto;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Error Occurred</h1>
    <div class="error-info">
        <p><span>Status Code:</span> <%= request.getAttribute("status_code") %></p>
        <p><span>Exception Type:</span> <%= request.getAttribute("exception_type") %></p>
        <p><span>Message:</span> <%= request.getAttribute("message") %></p>
        <p><span>Request URI:</span> <%= request.getAttribute("request_uri") %></p>
    </div>
    <div class="exception">
        <p><span>Exception:</span></p>
        <pre><%= request.getAttribute("exception") %></pre>
    </div>
</div>
</body>
</html>
