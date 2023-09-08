<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Edit Meals</h2>

    <form action="${pageContext.request.contextPath}/meals" method="post" name="addMeal">
<%--        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>--%>
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
        <p><input type="hidden" name="id" value="${meal.id}"/></p>
        <p>DateTime: <input type="datetime-local" name="dateTime" value="${meal.dateTime}"/> </p>
        <p>Description: <input type="text" name="description" value="${meal.description}"/> </p>
        <p>Calories: <input type="number" name="calories" value="${meal.calories}"/> </p>
        <p>
        <input type="submit" value="Save"/>
        <button onclick="window.history.back()" type="button">Cancel</button>
        </p>
    </form>

</section>
</body>
</html>