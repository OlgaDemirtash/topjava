package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");


    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(pattern);
    }
}

