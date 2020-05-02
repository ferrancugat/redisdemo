package org.demo.nosql.redisdemo.utils;

public class TimeUtils {

    private TimeUtils() {
    }

    public static Long convertToMilliseconds(String timeSeconds) {
        return Long.parseLong(timeSeconds) * 1000;
    }
}
