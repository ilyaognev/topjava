package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int MEAL_ID = START_SEQ + 2;

    public static final Meal MEAL_1 = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.APRIL, 14, 4, 5), "Users night dozor", 1000);
    public static final Meal MEAL_2 = new Meal(MEAL_ID + 1, LocalDateTime.of(2020, Month.APRIL, 12, 12, 5), "Users lunch", 990);
    public static final Meal MEAL_3 = new Meal(MEAL_ID + 2, LocalDateTime.of(2020, Month.APRIL, 12, 16, 5), "Users second lunch dozor", 100);
    public static final Meal MEAL_4 = new Meal(MEAL_ID + 3, LocalDateTime.of(2020, Month.APRIL, 14, 10, 5), "Admin breakfast", 1000);
    public static final Meal MEAL_5 = new Meal(MEAL_ID + 4, LocalDateTime.of(2020, Month.APRIL, 12, 22, 5), "Admin dinner", 900);

    public static Meal getNew(){
        return new Meal(LocalDateTime.of(2020, Month.OCTOBER, 20, 23, 5), "New meal", 990);
    }

    public static Meal getUpdated(){
        Meal updated = new Meal(MEAL_1);
        updated.setDescription("UpdatedMeal");
        updated.setCalories(330);
        updated.setDateTime(LocalDateTime.of(1999, Month.MAY, 7, 0, 0));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

}
