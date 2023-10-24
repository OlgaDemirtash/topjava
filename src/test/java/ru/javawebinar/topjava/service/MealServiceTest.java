package ru.javawebinar.topjava.service;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.DATE;
import static ru.javawebinar.topjava.MealTestData.MEAL_1;
import static ru.javawebinar.topjava.MealTestData.MEAL_2;
import static ru.javawebinar.topjava.MealTestData.MEAL_3;
import static ru.javawebinar.topjava.MealTestData.MEAL_4;
import static ru.javawebinar.topjava.MealTestData.MEAL_5;
import static ru.javawebinar.topjava.MealTestData.MEAL_6;
import static ru.javawebinar.topjava.MealTestData.MEAL_7;
import static ru.javawebinar.topjava.MealTestData.MEAL_ID_1;
import static ru.javawebinar.topjava.MealTestData.MEAL_ID_2;
import static ru.javawebinar.topjava.MealTestData.NOT_FOUND;
import static ru.javawebinar.topjava.MealTestData.USER_ID_1;
import static ru.javawebinar.topjava.MealTestData.USER_ID_2;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.getNew;
import static ru.javawebinar.topjava.MealTestData.getUpdated;

import java.time.LocalDateTime;
import java.time.Month;
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

@ContextConfiguration({
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-db.xml"
})
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
    Meal meal = service.get(MEAL_ID_1, USER_ID_1);
    assertMatch(meal, MEAL_1);
  }

  @Test
  public void getNotFound() {
    assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND,USER_ID_1));
  }

  @Test
  public void duplicateDateTimeCreate() {
    assertThrows(DataAccessException.class, () ->
        service.create(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500), USER_ID_1));
  }

  @Test
  public void delete() {
    service.delete(MEAL_ID_2,USER_ID_1);
    assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_2, USER_ID_1));
  }

  @Test
  public void deletedNotFound() {
    assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID_1));
  }

  @Test
  public void getBetweenInclusive() {
    List<Meal> all = service.getBetweenInclusive(DATE, DATE , USER_ID_1);
        assertMatch(all, MEAL_7, MEAL_6, MEAL_5, MEAL_4);
  }

  @Test
  public void getAll() {
    List<Meal> all = service.getAll(USER_ID_1);
    assertMatch(all, MEAL_7, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
  }

  @Test
  public void update() {
    Meal updated = getUpdated();
    service.update(updated, USER_ID_1);
    assertMatch(service.get(MEAL_ID_1, USER_ID_1), getUpdated());
  }

  @Test
  public void updateNotUserMeal() {
    Meal updated = getUpdated();
    assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID_2));
  }

  @Test
  public void create() {
    Meal created = service.create(getNew(), USER_ID_1);
    Integer newId = created.getId();
    Meal newMeal = getNew();
    newMeal.setId(newId);
    assertMatch(created, newMeal);
    assertMatch(service.get(newId, USER_ID_1), newMeal);
  }
}