<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://javawebinar.topjava.ru/functions" prefix="f" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table class=table>
    <tr>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
    </tr>
    <tbody>
    <c:forEach items="${mealsTo}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr class=${meal.excess ? 'exceeded' : 'normal'}>
            <td>${f:formatLocalDateTime(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
