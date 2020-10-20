package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertMatch(meal, MEAL_1);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() throws Exception {
        List<Meal> between = service.getBetweenInclusive(LocalDate.of(2020, Month.APRIL, 11),
                LocalDate.of(2020, Month.APRIL, 13), USER_ID);
        assertMatch(between, MEAL_2, MEAL_3, MEAL_5);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), updated);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void deleteStrangeMeal() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, USER_ID + 100));
    }

    @Test
    public void getStrangeMeal() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID + 100));
    }

    @Test
    public void updateStrangeMeal() throws Exception {
        assertThrows(NotFoundException.class, () -> service.update(MEAL_1, USER_ID + 100));
    }

    @Test
    public void duplicateDateTimeCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(LocalDateTime.of(2020, Month.APRIL, 4, 4, 5), "Users night dozor", 1000), USER_ID));
    }
}