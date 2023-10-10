package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal add(Meal meal);

    void delete(Integer id);

    Meal update(Meal meal);

    List<Meal> getAll();

    Meal getById(Integer id);
}
