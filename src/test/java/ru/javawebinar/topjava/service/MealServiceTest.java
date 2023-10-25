package ru.javawebinar.topjava.service;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID_8;
import static ru.javawebinar.topjava.MealTestData.NOT_FOUND;
import static ru.javawebinar.topjava.MealTestData.ADMIN_ID;
import static ru.javawebinar.topjava.MealTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL_ID_1;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL_ID_2;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.date;
import static ru.javawebinar.topjava.MealTestData.getNew;
import static ru.javawebinar.topjava.MealTestData.getUpdated;
import static ru.javawebinar.topjava.MealTestData.getUpdatedDuplicate;
import static ru.javawebinar.topjava.MealTestData.getUpdatedMaxDate;
import static ru.javawebinar.topjava.MealTestData.userMeal1;
import static ru.javawebinar.topjava.MealTestData.userMeal2;
import static ru.javawebinar.topjava.MealTestData.userMeal3;
import static ru.javawebinar.topjava.MealTestData.userMeal4;
import static ru.javawebinar.topjava.MealTestData.userMeal5;
import static ru.javawebinar.topjava.MealTestData.userMeal6;
import static ru.javawebinar.topjava.MealTestData.userMeal7;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-app-jdbc.xml",
    "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_ID_1, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotUserMeal() {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_ID_8, USER_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class,
            () -> service.create(new Meal(null, userMeal1.getDateTime(), "Завтрак", 500), USER_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID_2, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID_2, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotUserMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(ADMIN_MEAL_ID_8, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = service.getBetweenInclusive(date, date, USER_ID);
        assertMatch(all, userMeal7, userMeal6, userMeal5, userMeal4);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL_ID_1, USER_ID), getUpdated());
    }

    @Test
    public void duplicateDateTimeUpdate() {
        Meal updated = getUpdatedDuplicate();
        assertThrows(DataAccessException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void updateMaxDate() {
        Meal updatedMaxDate = getUpdatedMaxDate();
        service.update(updatedMaxDate, USER_ID);
        assertMatch(service.get(USER_MEAL_ID_1, USER_ID), getUpdatedMaxDate());
    }

    @Test
    public void updateNotUserMeal() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }
}