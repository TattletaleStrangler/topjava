package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController extends AbstractMealController{

    public List<MealTo> getAll() {
        return super.getAll(authUserId());
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return super.getFiltered(startDate, endDate, startTime, endTime, authUserId());
    }

    public MealTo get(int mealId) {
        return super.get(mealId, authUserId());
    }

    public MealTo create(Meal meal) {
        return super.create(meal, authUserId());
    }

    public void delete(int mealId) {
        super.delete(mealId, authUserId());
    }

    public void update(Meal meal) {
        super.update(meal, authUserId());
    }
}