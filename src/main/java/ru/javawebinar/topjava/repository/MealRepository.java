package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealRepository {
    public void addMeal(Meal meal);

    public void deleteMeal(int id);

    public void updateMeal(Meal meal);

    public List<MealTo> getAllMeals();

    public Meal getById(int id);
}
