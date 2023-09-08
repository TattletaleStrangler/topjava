package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static String INSERT_OR_EDIT = "/edit-meal.jsp";
    private static String LIST_MEAL = "/meals.jsp";

    private final MealDao mealDao;

    public MealServlet() {
        this.mealDao = new MealDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String forward="";
        String action = request.getParameter("action");

        if (action == null) {
            log.debug("getAll Meal");
            forward = LIST_MEAL;
            List<MealTo> meals = MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
            request.setAttribute("meals", meals);
        } else if (action.equalsIgnoreCase("delete")){
            log.debug("delete Meal");
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealDao.delete(mealId);
            forward = LIST_MEAL;
            List<MealTo> meals = MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
            request.setAttribute("meals", meals);
        } else if (action.equalsIgnoreCase("edit")){
            log.debug("edit Meal");
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealDao.getById(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("add")) {
            log.debug("add Meal");
            forward = INSERT_OR_EDIT;
            Meal meal = new Meal(LocalDateTime.now(), "", 0);
            meal.setId(0);
            request.setAttribute("meal", meal);
        } else {
            forward = INSERT_OR_EDIT;
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        int id = Integer.parseInt(request.getParameter("id"));

        Meal meal = new Meal(dateTime, description, calories);
        if (id == 0) {
            mealDao.create(meal);
        } else {
            meal.setId(id);
            mealDao.update(meal);
        }

        log.debug("postMeal");
        List<MealTo> meals = MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
        request.setAttribute("meals", meals);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
