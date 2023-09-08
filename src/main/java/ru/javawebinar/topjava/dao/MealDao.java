package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void create(Meal meal);

    void update(Meal meal);

    void delete(Integer mealId);

    Meal getById(Integer mealId);

    List<Meal> getAll();
}
