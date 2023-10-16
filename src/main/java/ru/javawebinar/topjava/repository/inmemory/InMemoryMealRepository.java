package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal,1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.getUserId() != userId) {
            return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.get(id).getUserId() != userId) {
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal != null) {
            if (meal.getUserId() != userId) {
                return null;
            }
        }
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDate).reversed().thenComparing(Meal::getDescription))
                .collect(Collectors.toList());
    }

    @Override
    public List<MealTo> getAllTo(int userId) {
       return filterByPredicate(getAll(userId), SecurityUtil.authUserCaloriesPerDay(), meal -> true);
    }

    @Override
    public Collection<Meal> getAllFilteredByDateTime(int userId, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate) {
        return repository.values()
                .stream()
                .filter(combineFilters(meal -> meal.getUserId() == userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime),
                        meal -> DateTimeUtil.isBetweenLocalDates(meal.getDate(), startDate, endDate)))
                .sorted(Comparator.comparing(Meal::getDate).reversed().thenComparing(Meal::getDescription))
                .collect(Collectors.toList());
    }

    public static Predicate<Meal> combineFilters(Predicate<Meal>... predicates) {
        return Stream.of(predicates).reduce(x -> true, Predicate::and);
    }

    private static List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getUserId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}

