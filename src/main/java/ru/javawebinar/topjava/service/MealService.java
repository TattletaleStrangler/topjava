package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public MealTo create(Meal meal, int userId) {
        meal.setUserId(userId);
        Meal savedMeal = repository.save(meal);
        boolean excess = MealsUtil.isExcess(repository.getByDate(savedMeal.getDate(), userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        return MealsUtil.createTo(savedMeal, excess);
    }

    public void delete(int mealId, int userId) {
        checkNotFoundWithId(repository.delete(mealId, userId), mealId);
    }

    public MealTo get(int mealId, int userId) {
        Meal findMeal = checkNotFoundWithId(repository.get(mealId, userId), mealId);
        boolean excess = MealsUtil.isExcess(repository.getByDate(findMeal.getDate(), userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        return MealsUtil.createTo(findMeal, excess);
    }

    public List<MealTo> getAll(int userId) {
        return MealsUtil.getTos(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        LocalDateTime startDateTime = startDate.atTime(startTime);
        LocalDateTime endDateTime = endDate.atTime(endTime);
        List<Meal> result = repository.getBetweenHalfOpen(startDateTime, endDateTime, userId);
        return MealsUtil.getTos(result, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public void update(Meal meal, int userId) {
        meal.setUserId(userId);
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }
}