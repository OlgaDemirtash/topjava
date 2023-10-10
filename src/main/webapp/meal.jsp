<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://javawebinar.topjava.ru/functions" prefix="f" %>
<html lang="ru">
<head>
    <title>Edit meal</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form method="POST" action='meals' name="frmAddMeal">
   <input type="hidden" readonly="readonly" name="id"
                     value="${meal.id}"/>
    DateTime : <input
           type="datetime-local" name="dateTime"
           value="${meal.dateTime}"/><br/>
    Description : <input
        type="text" name="description"
        value="${meal.description}"/><br/>
    Calories : <input
        type="number" name="calories"
        value="${meal.calories}"/><br/>
       <button type = "submit">Save</button>
       <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>