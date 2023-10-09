package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.StorageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MealDao {
    private final Map<Integer, Meal> storage = StorageUtil.getStorage();

    public MealDao() {
    }

    public void addMeal(Meal meal) {
        storage.put(meal.getMealId(), meal);
    }

    public void deleteMeal(Integer mealId) {
        storage.remove(mealId);
    }

    public void updateMeal(Meal meal) {
        storage.replace(meal.getMealId(), meal);
    }

    public List<Meal> getAllMeals() {
        return new ArrayList<>(storage.values());
    }

    public Meal getMealById(int mealId) {
        return storage.get(mealId);
    }
}
