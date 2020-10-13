package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>(); //userID, (mealID, meal)
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(SecurityUtil.authUserId(), meal));
        save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед Юзера 2", 1000));
        save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин Юзера 2", 1000));
    }

    @Override
    public Meal save(int userId, Meal meal) {

        Map<Integer, Meal> savedMealOfUser;
        if (repository.get(userId) == null){
            savedMealOfUser = new ConcurrentHashMap<>();
        } else {
            savedMealOfUser = repository.get(userId);
        }

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (repository.get(userId).containsKey(meal.getId())){
            savedMealOfUser = repository.get(userId);
        } else return null;

        savedMealOfUser.put(meal.getId(), meal);
        repository.put(userId, savedMealOfUser);
        return meal;

    }

    @Override
    public boolean delete(int userId, int id) {
        return repository.get(userId).get(id) != null && repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        if (repository.get(userId).values().stream().filter(m -> m.getId() == id).findFirst().orElse(null) != null
                && repository.get(userId).values().stream().filter(m -> m.getId() == id).findFirst().get().getId() == id) {
            return repository.get(userId).values().stream().filter(m -> m.getId() == id).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.get(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDateTime start, LocalDateTime end) {
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenDateAndTimeHalfOpen(meal.getDateTime(), start, end))
                .collect(Collectors.toList());
    }
}

