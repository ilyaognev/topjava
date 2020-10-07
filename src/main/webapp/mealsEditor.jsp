<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://example.com/functions" %>
<html>
<head>
    <title>Meals editor</title>
</head>
<body>
<form method="POST" action='meals' name="frmAddMeal">
    DateTime : <input
        type="datetime-local" name="dateTime"
        value="${meal.dateTime}"/> <br/>
    Description : <input
        type="text" name="description"
        value="${meal.description}"/> <br/>
    Calories : <input
        type="number" name="calories"
        value="${meal.calories}"/> <br/>
    <p><input type="hidden" name="mealId"
              value=${meal.id}
                      readonly></p>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
