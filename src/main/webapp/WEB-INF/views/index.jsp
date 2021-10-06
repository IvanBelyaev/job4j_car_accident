<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <title>Accident</title>
</head>
<body>
Hello : Accident

<br><br>

<table class="table">
    <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>text</th>
            <th>address</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="accident" items="${accidents}">
            <tr>
                <td>${accident.id}</td>
                <td>${accident.name}</td>
                <td>${accident.text}</td>
                <td>${accident.address}</td>
            </tr>
        </c:forEach>
    </tbody>
</body>
</html>