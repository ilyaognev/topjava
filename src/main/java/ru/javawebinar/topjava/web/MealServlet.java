package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String insertOrEdit = "mealsEditor.jsp";
    private static final String listMeals = "meals.jsp";
    private MealRepository repository;

    public void init() throws ServletException {
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "delete":
                log.debug("meal action = delete");
                repository.delete(getId(request));
                response.sendRedirect("meals");
                break;
            case "edit":
                log.debug("meal action = edit");
                Meal meal = repository.getById(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(insertOrEdit).forward(request, response);
                break;
            case "add":
                log.debug("meal action = add");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime now = LocalDateTime.now();
                String formatDateTimeString = now.format(formatter);
                LocalDateTime formatDateTime = LocalDateTime.parse(formatDateTimeString, formatter);

                Meal newMeal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0);
                request.setAttribute("meal", newMeal);
                request.getRequestDispatcher(insertOrEdit).forward(request, response);
                break;
            default:
                log.debug("meal action = default");
                request.setAttribute("meals", getMealTo());
                request.getRequestDispatcher(listMeals).forward(request, response);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        log.debug("Add and Update in doPost");

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        int id = getId(request);

        Meal meal = new Meal(id, dateTime, description, calories);

        repository.update(meal);
        response.sendRedirect("meals");
    }

    protected int getId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("mealId"));
    }

    protected List<MealTo> getMealTo() {
        return MealsUtil.filteredByStreams(repository.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.MAX_CALORIES_PER_DAY);
    }
}
