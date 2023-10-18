<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <div class="container">
        <h3><a href="index.html">Home</a></h3>
        <hr/>
        <h2>Meals</h2>
        <div class="card border-dark">
            <div class="card-body pb-0">
                <form id="filter">
                    <div class="row">
                        <div class="col-2">
                            <label for="startDate">От даты (включая)</label>
                            <input type="date" name="startDate" value = "${param.startDate}" id="startDate" autocomplete="off">
                        </div>
                        <div class="col-2">
                            <label for="endDate">До даты (включая)</label>
                            <input type="date" name="endDate" value = "${param.endDate}" id="endDate" autocomplete="off">
                        </div>
                        <div class="offset-2 col-3">
                            <label for="startTime">От времени (включая)</label>
                            <input type="time" name="startTime" value = "${param.startTime}" id="startTime" autocomplete="off">
                        </div>
                        <div class="col-3">
                            <label for="endTime">До времени (исключая)</label>
                            <input type="time" name="endTime" value = "${param.endTime}" id="endTime" autocomplete="off">
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-footer text-right">
                <button class="btn btn-primary"
                        onclick="window.location.assign('meals?&startTime='
                        +document.getElementById('startTime').value
                        +'&endTime='+document.getElementById('endTime').value
                        +'&startDate='+document.getElementById('startDate').value
                        +'&endDate='+document.getElementById('endDate').value)">
                    Отфильтровать
                </button>
            </div>
        </div>
        <br>
        <a href="meals?action=create">Add Meal</a>
        <br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
        <br>
    </div>
</section>
</body>
</html>