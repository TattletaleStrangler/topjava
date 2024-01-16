package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_BREAKFAST = START_SEQ + 3;
    public static final int USER_LUNCH = START_SEQ + 4;
    public static final int USER_DINNER = START_SEQ + 5;
    public static final int ADMIN_BORDER = START_SEQ + 6;
    public static final int ADMIN_BREAKFAST = START_SEQ + 7;
    public static final int ADMIN_LUNCH = START_SEQ + 8;
    public static final int ADMIN_DINNER = START_SEQ + 9;


    public static final int NOT_FOUND = 10;

    public static final Meal userBreakfast = new Meal(USER_BREAKFAST, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0, 0), "Завтрак", 500);
    public static final Meal userLunch = new Meal(USER_LUNCH, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0, 0), "Обед", 1000);
    public static final Meal userDinner = new Meal(USER_DINNER, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0, 0), "Ужин", 500);
    public static final Meal adminBreakfast = new Meal(ADMIN_BREAKFAST, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0, 0), "Завтрак", 1000);
    public static final Meal adminLunch = new Meal(ADMIN_LUNCH, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0, 0), "Обед", 500);
    public static final Meal adminDinner = new Meal(ADMIN_DINNER, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0, 0), "Ужин", 410);
    public static final Meal adminBorderValue = new Meal(ADMIN_BORDER, LocalDateTime.of(2020, Month.JANUARY, 31, 00, 0, 0), "Еда на граничное значение", 100);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2024, Month.JANUARY, 14, 13, 0, 0), "newMeal", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal();
        updated.setId(USER_BREAKFAST);
        updated.setDateTime(LocalDateTime.of(2024, Month.JANUARY, 13, 11, 22, 0));
        updated.setDescription("updatedMeal");
        updated.setCalories(1111);
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
