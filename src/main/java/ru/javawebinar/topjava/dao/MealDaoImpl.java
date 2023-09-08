package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoImpl implements MealDao{
    private static final AtomicInteger ID = new AtomicInteger(0);

    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    {
        List<Meal> mealList = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        mealList.forEach(m -> {m.setId(generateId());
                            meals.put(m.getId(), m);});
    }

    @Override
    public void create(Meal meal) {
        if (meal.getId() == null) {
            final Integer id = generateId();
            meal.setId(id);
            meals.put(id, meal);
        }
    }

    @Override
    public void update(Meal meal) {
        Meal oldMeal = meals.get(meal.getId());
        if (oldMeal != null) {
            meals.put(meal.getId(), meal);
        }
    }

    @Override
    public void delete(Integer mealId) {
        meals.remove(mealId);
    }

    @Override
    public Meal getById(Integer mealId) {
        return meals.get(mealId);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    private Integer generateId() {
        return ID.incrementAndGet();
    }
}
