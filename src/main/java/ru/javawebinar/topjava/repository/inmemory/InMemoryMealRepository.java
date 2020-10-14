package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
        save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед Юзера 2", 1000));
        save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин Юзера 2", 1000));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> savedMealOfUser = repository.computeIfAbsent(userId, HashMap::new);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            savedMealOfUser.put(meal.getId(), meal);
            return meal;
        }

        return savedMealOfUser.computeIfPresent(meal.getId(), (mealId, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> meals = repository.get(userId);
        if (meals == null) {
            return null;
        }
        return meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filterByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate start, LocalDate end) {
        return filterByPredicate(userId, meal -> DateTimeUtil.isBetweenIncluding(meal.getDate(), start, end));
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> meals = repository.get(userId);

        if (meals == null) return null;

        return meals.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}

