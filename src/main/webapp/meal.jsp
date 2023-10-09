<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://javawebinar.topjava.ru/functions" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add new meal</title>
</head>
<body>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<form method="POST" action='mealController' name="frmAddMeal">
    User ID : <input type="text" readonly="readonly" name="mealId"
                     value="<c:out value="${meal.mealId}" />"/> <br/>
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input
        type="number" name="calories"
        value="<c:out value="${meal.calories}" />"/> <br/>
    DateTime : <input
        type="datetime-local" name="dateTime"

        value="<c:out value="${meal.dateTime}" />"/> <br/>
    <input
            type="submit" value="Submit"/>
</form>
</body>
</html>