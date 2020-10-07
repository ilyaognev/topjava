package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    Meal add(Meal meal);

    void delete(int id);

    Meal update(Meal meal);

    List<Meal> getAll();

    Meal getById(int id);
}
