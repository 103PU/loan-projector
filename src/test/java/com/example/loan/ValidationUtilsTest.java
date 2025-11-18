package com.example.loan;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void requireNonNull_throws() {
        // Null value should trigger validation error
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.requireNonNull(null, "x"));
    }

    @Test
    void requirePositive_throwsWhenZeroOrNegative() {
        // Zero is not considered positive
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.requirePositive(BigDecimal.ZERO, "v"));

        // Negative values must also fail
        assertThrows(IllegalArgumentException.class,
                () -> ValidationUtils.requirePositive(BigDecimal.valueOf(-10), "v"));
    }

    @Test
    void requireNonNegative_allowsZero() {
        // Zero is allowed for non-negative validation
        assertDoesNotThrow(() -> ValidationUtils.requireNonNegative(BigDecimal.ZERO, "v"));
    }

    @Test
    void requirePositiveMonths_throws() {
        // Month count must be > 0; zero should fail
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.requirePositiveMonths(0, "m"));
    }
}
