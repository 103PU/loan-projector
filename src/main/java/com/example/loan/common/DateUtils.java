package com.example.loan.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Date utilities: parse, format, date arithmetic.
 */
public final class DateUtils {

    // Default ISO-like date format
    private static final DateTimeFormatter DEFAULT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateUtils() {
    }

    /** Parse string to LocalDate */
    public static LocalDate parse(String date) {
        return LocalDate.parse(date, DEFAULT_FORMAT);
    }

    /** Convert LocalDate to formatted string */
    public static String format(LocalDate date) {
        return date.format(DEFAULT_FORMAT);
    }

    /** Add N months to a date */
    public static LocalDate addMonths(LocalDate date, int months) {
        return date.plusMonths(months);
    }
}
