package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepositoryImpl implements MealRepository {
    private final AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public void addMeal(Meal meal) {
        MealsUtil.MEALS.add(new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories(), idCounter.getAndIncrement()));
    }

    @Override
    public void deleteMeal(int id) {
        MealsUtil.MEALS.removeIf(m -> m.getId() == id);
    }

    @Override
    public void updateMeal(Meal meal) {
        deleteMeal(meal.getId());
        addMeal(meal);
    }

    @Override
    public List<MealTo> getAllMeals() {
        return MealsUtil.listToServlet();
    }

    @Override
    public Meal getById(int id) {
        return MealsUtil.MEALS.stream().filter(m -> m.getId() == id).findAny().get();
    }
}
