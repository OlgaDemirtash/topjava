package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<MealTo> getAll(int userId) {
        return MealsUtil.getTos(repository.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public List<MealTo> getAllFilteredByDateTime(int userId, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate) {
        startTime = (startTime == null) ? LocalTime.MIN : startTime;
        endTime = (endTime == null) ? LocalTime.MAX : endTime;
        startDate = (startDate == null) ? LocalDate.MIN : startDate;
        endDate = (endDate == null) ? LocalDate.MAX : endDate;
        return  MealsUtil.getTos(repository.getAllFilteredByDateTime(userId, startTime, endTime, startDate, endDate),SecurityUtil.authUserCaloriesPerDay());
    }
}