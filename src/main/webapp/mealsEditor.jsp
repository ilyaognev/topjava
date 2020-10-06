<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 006 06.10.20
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://example.com/functions" %>
<html>
<head>
    <title>Meals editor</title>
</head>
<body>
<form method="POST" action='mealServlet' name="frmAddMeal">
    DateTime : <input
        type="datetime" name="dateTime"
        value="<c:out value="${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm')}" />"/> <br/>
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />"/> <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
