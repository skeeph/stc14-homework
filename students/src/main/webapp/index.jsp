<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Студенты</title>
    <meta charset="UTF-8"/>
</head>
<body>
session-name: <%= session.getAttribute("name") %> <br/>
hello, <%= request.getParameter("name")%> <br/>
<ul>
    <li><a href="/person/list">Список студентов</a></li>
    <li><a href="form.jsp">Новый студент</a></li>
</ul>
</body>
</html>