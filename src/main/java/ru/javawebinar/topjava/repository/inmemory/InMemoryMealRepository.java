package ru.javawebinar.topjava.repository.inmemory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
        MealsUtil.meals.forEach(meal -> save(meal.clone(), 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> meals = repository.computeIfAbsent(userId,
                oldMeals -> new ConcurrentHashMap<>());
            return meals.put(meal.getId(), meal);
        } else {
            Map<Integer, Meal> mealsForUser = repository.get(userId);
            // handle case: update, but not present in storage
            return mealsForUser == null ? null
                : mealsForUser.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals != null && userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return (userMeals == null ? null : userMeals.get(id));
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = filterByPredicate(userId, meal -> true);
        return (meals == null) ? new ArrayList<>() : meals;
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            return userMeals.values()
                .stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Meal> getAllFilteredByDateTime(int userId, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate) {
        List<Meal> meals = filterByPredicate(userId,
            meal -> (DateTimeUtil.isBetweenLocalDateTime(meal.getDateTime(),
                LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime))));
        return (meals == null) ? new ArrayList<>() : meals;
    }
}