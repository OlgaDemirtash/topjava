package ru.javawebinar.topjava;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import ru.javawebinar.topjava.model.Meal;

public class MealTestData {

    public static final int MEAL_ID_1 = START_SEQ + 3;
    public static final int MEAL_ID_2 = START_SEQ + 4;
    public static final int MEAL_ID_3 = START_SEQ + 5;
    public static final int MEAL_ID_4 = START_SEQ + 6;
    public static final int MEAL_ID_5 = START_SEQ + 7;
    public static final int MEAL_ID_6 = START_SEQ + 8;
    public static final int MEAL_ID_7 = START_SEQ + 9;
    public static final int MEAL_ID_8 = START_SEQ + 10;
    public static final int USER_ID_1 = START_SEQ;
    public static final int USER_ID_2 = START_SEQ + 1;
    public static final int NOT_FOUND = 100;

    public static final Meal meal1 = new Meal(MEAL_ID_1,
        LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак:User", 500);
    public static final Meal meal2 = new Meal(MEAL_ID_2,
        LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед:User", 1000);
    public static final Meal meal3 = new Meal(MEAL_ID_3,
        LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин:User", 500);
    public static final Meal meal4 = new Meal(MEAL_ID_4,
        LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение:User", 100);
    public static final Meal meal5 = new Meal(MEAL_ID_5,
        LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак:User", 1000);
    public static final Meal meal6 = new Meal(MEAL_ID_6,
        LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед:User", 500);
    public static final Meal meal7 = new Meal(MEAL_ID_7,
        LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин:User", 410);
    public static final Meal meal8 = new Meal(MEAL_ID_8,
        LocalDateTime.of(2020, Month.JANUARY, 28, 8, 0), "Завтрак:Admin", 600);

    public static final LocalDate date = LocalDate.of(2020, Month.JANUARY, 31);
    public static final LocalDateTime dateTime = LocalDateTime.of(2020, Month.JANUARY, 31, 14, 0);
    public static final LocalDateTime dateTimeDuplicate = LocalDateTime.of(2020, Month.JANUARY, 31,
        13, 0);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "new", 100);
    }

    public static Meal getUpdatedMaxDate() {
        Meal updated = new Meal(meal1);
        updated.setDescription("UpdatedMaxDate");
        updated.setDateTime(LocalDateTime.MAX);
        return updated;
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDescription("UpdatedDescription");
        updated.setDateTime(dateTime);
        updated.setCalories(998);
        return updated;
    }

    public static Meal getUpdatedDuplicate() {
        Meal updated = new Meal(meal1);
        updated.setDescription("UpdatedDescription");
        updated.setDateTime(dateTimeDuplicate);
        updated.setCalories(998);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}