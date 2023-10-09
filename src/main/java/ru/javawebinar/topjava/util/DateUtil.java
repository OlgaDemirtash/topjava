package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final DateTimeFormatter patternOutput = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
    public static final DateTimeFormatter patternInput = DateTimeFormatter.ISO_DATE_TIME;


    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            return localDateTime.format(patternOutput);
        } else {
            return "";
        }
    }
}

