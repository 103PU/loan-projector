package com.example.loan.common;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DateUtils.
 */
class DateUtilsTest {

    @Test
    void testFormatParse() {
        LocalDate date = LocalDate.of(2023, 5, 10);

        String str = DateUtils.format(date); // Convert to string
        assertEquals("2023-05-10", str);

        LocalDate parsed = DateUtils.parse(str); // Convert back to date
        assertEquals(date, parsed);
    }

    @Test
    void testAddMonths() {
        LocalDate res = DateUtils.addMonths(LocalDate.of(2023, 5, 10), 3);
        assertEquals(LocalDate.of(2023, 8, 10), res);
    }
}
