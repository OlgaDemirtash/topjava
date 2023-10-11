package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealDao implements MealDao {
    private final AtomicInteger countId = new AtomicInteger(1);
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    @Override
    public Meal add(Meal meal) {
        meal.setId(countId.getAndIncrement());
        storage.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        if (id == 0) {
            return;
        }
        storage.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        if (meal.getId() == null) {
            return null;
        }
        storage.replace(meal.getId(), meal);
        return meal;
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
