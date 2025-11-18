package com.example.loan;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoanCalculatorParameterizedTest {

    // Runs the same test logic with multiple input sets from the CSV table
    @ParameterizedTest(name = "principal={0}, annual={1}%, months={2} => expected={3}")
    @CsvSource({
            "100000, 5, 360, 536.82",
            "1200, 0, 12, 100.00",
            "10000, 6, 24, 443.21",
            "5000, 4, 10, 509.21"
    })
    void calcMonthlyPayment_various(double principal, double annual, int months, double expected) {

        // Convert primitive values to BigDecimal for precise financial calculation
        BigDecimal p = BigDecimal.valueOf(principal);
        BigDecimal a = BigDecimal.valueOf(annual);

        BigDecimal result = LoanCalculator.calcMonthlyPayment(p, a, months);

        // Compare expected monthly payment with rounding to 2 decimals
        assertEquals(BigDecimal.valueOf(expected).setScale(2), result);
    }
}
