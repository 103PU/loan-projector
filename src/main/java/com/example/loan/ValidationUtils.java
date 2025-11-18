package com.example.loan;

import java.math.BigDecimal;

public final class ValidationUtils {

    private ValidationUtils() {
    } // Prevent instantiation

    // Ensures the provided object is not null
    public static void requireNonNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " must not be null");
        }
    }

    // Validates that a BigDecimal is strictly positive (> 0)
    public static void requirePositive(BigDecimal value, String name) {
        requireNonNull(value, name);
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(name + " must be > 0");
        }
    }

    // Validates that a BigDecimal is zero or positive (>= 0)
    public static void requireNonNegative(BigDecimal value, String name) {
        requireNonNull(value, name);
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(name + " must be >= 0");
        }
    }

    // Ensures loan duration (months) is a positive integer
    public static void requirePositiveMonths(int months, String name) {
        if (months <= 0) {
            throw new IllegalArgumentException(name + " must be > 0");
        }
    }
}
