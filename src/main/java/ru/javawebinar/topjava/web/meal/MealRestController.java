package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public class MealRestController extends AbstractMealController {

    public List<Meal> getAll() {
        return super.getAll(authUserId());
    }


    public List<MealTo> getAllTo(int userId) {
        return super.getAllTo(userId);
    }

    public List<Meal> getAllFilteredByDateTime(LocalDate startDate, LocalDate endDate,
                                               LocalTime startTime, LocalTime endTime) {

        if (startDate == null && endDate == null && startTime == null && endTime == null) {
            return super.getAll(authUserId());
        }
        return super.getAllFilteredByDateTime(authUserId(), startDate, endDate,
                startTime, endTime);
    }

    public Meal get(int id) {
        return super.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        return super.create(meal, authUserId());
    }

    public void delete(int id) {
        super.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        super.update(meal, id, authUserId());
    }
}