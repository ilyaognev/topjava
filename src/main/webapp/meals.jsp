<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 005 05.10.20
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://example.com/functions" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<table border=1>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr style="color:${meal.excess ? 'limegreen' : 'red'}">
            <td><c:out value="${f:formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy HH:mm')}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
    <p><a href="mealServlet?action=insert">Add meal</a></p>
</table>
</body>
</html>
