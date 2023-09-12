package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        Integer userId = meal.getUserId();
        Map<Integer, Meal> meals = repository.getOrDefault(userId, new ConcurrentHashMap<>());

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            repository.computeIfPresent(userId, (key, value) -> meals);
            return meal;
        }
        Meal oldMeal = meals.get(meal.getId());
        ValidationUtil.checkNotFoundWithId(oldMeal, meal.getId());
        meals.computeIfPresent(meal.getId(), (key, value) -> meal);
        // handle case: update, but not present in storage
        return meal;
    }

    @Override
    public boolean delete(int mealId, int userId) {
        Map<Integer, Meal> meals = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        return meals.remove(mealId) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        return meals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        List<Meal> meals = repository.get(userId).values().stream()
                .sorted((m1, m2) -> m2.getDate().compareTo(m1.getDate()))
                .collect(Collectors.toList());
        return meals;
    }
}

