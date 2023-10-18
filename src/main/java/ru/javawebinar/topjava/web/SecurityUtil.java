package ru.javawebinar.topjava.web;

public class SecurityUtil {

    private static int authUser = 1;
    private static int  authUserCaloriesPerDay = 2000;

    public static int authUserId() {
        return authUser;
    }

    public static void setAuthUser(int authUser) {
        SecurityUtil.authUser = authUser;
    }

    public static int authUserCaloriesPerDay() {
        return authUserCaloriesPerDay;
    }
}