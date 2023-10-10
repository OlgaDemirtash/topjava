package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealDao implements MealDao {
    private static final AtomicInteger atomic = new AtomicInteger(0);
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    public MapMealDao() {
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(atomic.getAndIncrement());
        storage.putIfAbsent(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            return;
        }
        storage.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        return meal.getId() == null ? null : storage.replace(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Meal getById(Integer id) {
        return id != null ? storage.get(id) : null;
    }
}
