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
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_BREAKFAST, USER_ID);
        assertMatch(meal, MealTestData.userBreakfast);
    }

    @Test
    public void delete() {
        service.delete(USER_BREAKFAST, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_BREAKFAST, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 31), LocalDate.of(2020, Month.JANUARY, 31), ADMIN_ID);
        assertMatch(meals, adminDinner, adminLunch, adminBreakfast);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, userDinner, userLunch, userBreakfast);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_BREAKFAST, USER_ID), getUpdated());
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void whenGetAlienMeal_thenNotFoundException() {
        assertThrows(NotFoundException.class, () -> service.get(USER_BREAKFAST, ADMIN_ID));
    }

    @Test
    public void whenCreateTwoMealInSameTime_whenDublicateTimeException() {
        service.create(getNew(), USER_ID);
        assertThrows(DataAccessException.class, () -> service.create(getNew(), USER_ID));
    }

    @Test
    public void whenUpdateNotOwn_thenNotFoundException() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void whenDeleteNotOwn_thenNotFoundException() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_BREAKFAST, ADMIN_ID));
    }
}