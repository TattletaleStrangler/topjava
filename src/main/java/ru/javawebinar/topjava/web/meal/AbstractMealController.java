package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public abstract class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getAll(int userId) {
        log.info("getAll");
        return service.getAll(userId);
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        log.info("getFiltered wit userId={}, startDate={}, endDate={}, startTime={}, endTime={}",
                userId, startDate, endDate, startTime, endTime);
        return service.getFiltered(startDate, endDate, startTime, endTime, userId);
    }

    public MealTo get(int mealId, int userId) {
        log.info("get {}", mealId);
        return service.get(mealId, userId);
    }

    public MealTo create(Meal meal, int userId) {
        log.info("create {}, user id {}", meal, userId);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int mealId, int userId) {
        log.info("delete {}", mealId);
        service.delete(mealId, userId);
    }

    public void update(Meal meal, int userId) {
        log.info("update {} with userId={}", meal, userId);
        assureIdConsistent(meal, meal.getId());
        service.update(meal, userId);
    }
}
