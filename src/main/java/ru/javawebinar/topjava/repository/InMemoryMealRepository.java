package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {
    private final AtomicInteger idCounter = new AtomicInteger(10);

    @Override
    public Meal addNew(Meal meal) {
        if (meal.getId() == -1) {
            meal.setId(idCounter.getAndIncrement());
        }
        safeList.add(meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        safeList.removeIf(m -> m.getId() == id);
    }

    @Override
    public Meal update(Meal meal, int id) {
        delete(id);
        return addNew(meal);
    }

    @Override
    public List<Meal> getAll() {
        return safeList;
    }

    @Override
    public Meal getById(int id) {
        return safeList.stream().filter(m -> m.getId() == id).findAny().orElse(null);
    }

    private List<Meal> testMealList = Arrays.asList(
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

    private CopyOnWriteArrayList<Meal> safeList = new CopyOnWriteArrayList(testMealList);

}
