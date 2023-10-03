package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMealWithExcessOpt2_1 {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final Boolean[] excess;

    public UserMealWithExcessOpt2_1(LocalDateTime dateTime, String description, int calories, Boolean[] excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess[0] +
                '}';
    }
}
