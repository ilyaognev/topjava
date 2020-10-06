package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet(name = "MealServlet")
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static String INSERT_OR_EDIT = "mealsEditor.jsp";
    private static String LIST_MEALS = "/meals.jsp";
    private static MealRepository repository = new MealRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to users");

        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            repository.deleteMeal(mealId);
            forward = LIST_MEALS;
            request.setAttribute("meals", repository.getAllMeals());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = repository.getById(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeals")) {
            forward = LIST_MEALS;
            request.setAttribute("meals", repository.getAllMeals());
        } else {
            forward = LIST_MEALS;
            request.setAttribute("meals", repository.getAllMeals());

        }

//        forward = LIST_MEALS;
//        request.setAttribute("meals", repository.getAllMeals());

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(dateTime, description, calories);

        RequestDispatcher view = request.getRequestDispatcher(LIST_MEALS);
        request.setAttribute("meals", repository.getAllMeals());
        view.forward(request, response);
    }
}
