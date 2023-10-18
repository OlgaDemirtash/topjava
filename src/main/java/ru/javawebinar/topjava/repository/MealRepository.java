package ru.javawebinar.topjava.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import ru.javawebinar.topjava.model.Meal;

public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, int userId);

    // false if meal does not belong to userId
    boolean delete(int id, int userId);

    // null if meal does not belong to userId
    Meal get(int id, int userId);

    // ORDERED dateTime desc
    List<Meal> getAll(int userId);

    List<Meal> getAllFilteredByDateTime(int userId, LocalTime starTime, LocalTime endTime, LocalDate startDate, LocalDate endDate);
}