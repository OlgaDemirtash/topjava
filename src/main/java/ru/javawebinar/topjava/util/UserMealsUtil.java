package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;
import ru.javawebinar.topjava.model.UserMealWithExcessOpt2_1;
import ru.javawebinar.topjava.model.UserMealWithExcessOpt2_2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class UserMealsUtil {

    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        System.out.println("Filtered by cycles");
        List<UserMealWithExcess> mealsByCycles = filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(17, 0), 2005);
        mealsByCycles.forEach(System.out::println);

        System.out.println("Filtered by cycles Opt2 (Array reference)");
        List<UserMealWithExcessOpt2_1> mealsByCyclesOpt21 = filteredByCyclesOpt2_1(meals, LocalTime.of(0, 0), LocalTime.of(17, 0), 2005);
        mealsByCyclesOpt21.forEach(System.out::println);

        System.out.println("Filtered by cycles Opt2_2 (AtomicBoolean)");
        List<UserMealWithExcessOpt2_2> mealsByCyclesOpt22 = filteredByCyclesOpt2_2(meals, LocalTime.of(0, 0), LocalTime.of(17, 0), 2005);
        mealsByCyclesOpt22.forEach(System.out::println);

        System.out.println("Filtered by streams");
        List<UserMealWithExcess> mealsByStreams = filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(17, 0), 2005);
        mealsByStreams.forEach(System.out::println);

        System.out.println("Filtered by streams Opt2");
        List<UserMealWithExcess> mealsByStreamsOpt2 = filteredByStreamsOpt2(meals, LocalTime.of(0, 0), LocalTime.of(17, 0), 2005);
        mealsByStreamsOpt2.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesDaySum = new HashMap<>();
        for (UserMeal meal : meals) {
            caloriesDaySum.merge(meal.getDateTime().toLocalDate(),
                    meal.getCalories(),
                    Integer::sum);
        }
        List<UserMealWithExcess> userMealsWithExcess = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealsWithExcess.add(
                        new UserMealWithExcess(meal.getDateTime(),
                                meal.getDescription(),
                                meal.getCalories(),
                                caloriesDaySum.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return userMealsWithExcess;
    }

    private static List<UserMealWithExcessOpt2_1> filteredByCyclesOpt2_1(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesDaySum = new HashMap<>();
        Map<LocalDate, Boolean[]> exceededMap = new HashMap<>();
        List<UserMealWithExcessOpt2_1> userMealsWithExcess = new ArrayList<>();
        meals.forEach(meal -> {
            Boolean[] isExceed = exceededMap.computeIfAbsent(meal.getDateTime().toLocalDate(), date -> new Boolean[]{false});
            int dailyCalories = caloriesDaySum.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
            if (dailyCalories > caloriesPerDay) {
                isExceed[0] = true;
            }
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealsWithExcess.add(new UserMealWithExcessOpt2_1(meal.getDateTime(),
                        meal.getDescription(), meal.getCalories(), isExceed));
            }
        });
        return userMealsWithExcess;
        }

    private static List<UserMealWithExcessOpt2_2> filteredByCyclesOpt2_2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesDaySum = new HashMap<>();
        Map<LocalDate, AtomicBoolean> exceededMap = new HashMap<>();
        List<UserMealWithExcessOpt2_2> userMealsWithExcess = new ArrayList<>();
        meals.forEach(meal -> {
            AtomicBoolean isExceed = exceededMap.computeIfAbsent(meal.getDateTime().toLocalDate(), date -> new AtomicBoolean());
            int dailyCalories = caloriesDaySum.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
            if (dailyCalories > caloriesPerDay) {
                isExceed.set(true);
            }
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealsWithExcess.add(new UserMealWithExcessOpt2_2(meal.getDateTime(),
                        meal.getDescription(), meal.getCalories(), isExceed));
            }
        });
        return userMealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesDaySum = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        caloriesDaySum.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByStreamsOpt2(List<UserMeal> meals, LocalTime startTime,
                                                                  LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate())).values().stream()
                .flatMap(mealGroupByDate -> {
                    boolean excess = mealGroupByDate.stream()
                                                    .mapToInt(UserMeal::getCalories).sum() > caloriesPerDay;
                    return mealGroupByDate.stream()
                            .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                            .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess));
                })
                .collect(Collectors.toList());
    }
}
