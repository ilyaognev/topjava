package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final AtomicInteger idCounter = new AtomicInteger(10);

    private List<Meal> testMealList = Arrays.asList(
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

    private Map<Integer, Meal> realySafeMap = testMealList
            .stream()
            .collect(Collectors.toConcurrentMap(Meal::getId, meal -> new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories())));

    @Override
    public Meal add(Meal meal) {
        if (meal.getId() == 0) {
            int id = idCounter.getAndIncrement();
            meal.setId(id);
            realySafeMap.put(id, meal);
        } else {
            realySafeMap.put(meal.getId(), meal);
        }
        return meal;
    }

    @Override
    public void delete(int id) {
        //safeList.removeIf(m -> m.getId() == id);
        realySafeMap.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        if (meal.getId() == 0) {
            return add(meal);
        }
        realySafeMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(realySafeMap.values());
    }

    @Override
    public Meal getById(int id) {
        return realySafeMap.get(id);
    }
}
