package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        Meal mealCheckedId = repository.get(meal.getId());
        if (mealCheckedId != null && mealCheckedId.getUserId() != userId) {
            return null;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        return meal != null && meal.getUserId() == userId && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return (meal != null && meal.getUserId() != userId ? null : meal);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filterByPredicate(userId, meal -> true);
    }

    public List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDate).reversed().thenComparing(Meal::getDescription))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllFilteredByDateTime(int userId, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate) {
        return filterByPredicate(userId, meal -> (DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) &&
                (DateTimeUtil.isBetweenLocalDates(meal.getDate(), startDate, endDate)));
    }
}