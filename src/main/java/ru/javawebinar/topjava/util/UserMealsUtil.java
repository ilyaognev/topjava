package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //создать мапу со всеми датами приема пищи и суммы количества съеденных калорий за этот день
        Map<LocalDate, Integer> mapOfCaloriesPerDay = new HashMap<>();
        for (UserMeal userMeal : meals){
            LocalDate daysOfEating = userMeal.getDateTime().toLocalDate();
            int caloriesPerEating = userMeal.getCalories();
            mapOfCaloriesPerDay.merge(daysOfEating, caloriesPerEating, (caloriesBefore, caloriesNew) -> caloriesBefore + caloriesNew);
        }

        //удалить из мапы лишние элементы со значением калорий меньше чем в caloriesPerDay
        for(Iterator<Map.Entry<LocalDate, Integer>> it = mapOfCaloriesPerDay.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<LocalDate, Integer> entry = it.next();
            if(entry.getValue() <= caloriesPerDay) {
                it.remove();
            }
        }

        //добавить в резултирующий список те приёмы пищи которые соответствуют дате из мапы и поподают в указанный временной промежуток
        List<UserMealWithExcess> mealsExcess = new ArrayList<>();
        for (UserMeal userMeal : meals){
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime) && mapOfCaloriesPerDay.containsKey(userMeal.getDateTime().toLocalDate())) {
                mealsExcess.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
            }
        }
        return mealsExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
