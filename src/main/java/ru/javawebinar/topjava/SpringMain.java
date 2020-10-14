package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 29, 13, 0), "Обед Юзера Spring", 1000));
            mealRestController.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 15, 0), "Обед2 Юзера Spring", 500));
            mealRestController.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 19, 0), "Обед3 Юзера Spring", 700));
            mealRestController.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 28, 20, 0), "Обед4 Юзера Spring", 2001));
            mealRestController.filter(LocalDate.of(2020, Month.JANUARY, 29), LocalDate.of(2020, Month.JANUARY, 30),
                    LocalTime.of(14, 00), LocalTime.of(16, 00));
            System.out.println(mealRestController.get(22));
        }
    }
}
