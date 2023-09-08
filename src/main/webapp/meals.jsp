<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="https://topjava.ru/functions" %>

<html>
<head>
    <title>Meal list</title>
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
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h4>Meals</h4>

<%--    <h4><a href="edit-meal.jsp">Add meal</a></h4>--%>
    <h4><a href="/topjava/meals?action=add">Add meal</a></h4>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th colspan=3>Action</th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>${fn:formatDateTime(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>${meal.id}</td>
                <td><a href="/topjava/meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
                <td><a href="/topjava/meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>