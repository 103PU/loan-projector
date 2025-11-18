package com.example.loan;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoanCalculatorScheduleParameterizedTest {

    // Runs amortization schedule validation for multiple input sets
    @ParameterizedTest(name = "principal={0}, annual={1}%, months={2}")
    @CsvSource({
            "10000, 6, 24",
            "5000, 4, 10",
            "1200, 0, 12"
    })
    void generateSchedule_invariants(double principal, double annual, int months) {
        BigDecimal p = BigDecimal.valueOf(principal);
        BigDecimal a = BigDecimal.valueOf(annual);

        // Generate amortization schedule for the given loan
        List<AmortizationEntry> schedule = LoanCalculator.generateSchedule(p, a, months);

        // Schedule must contain exactly <months> entries
        assertEquals(months, schedule.size());

        // Sum principal portions to ensure total principal paid = original loan amount
        BigDecimal totalPrincipalPaid = schedule.stream()
                .map(AmortizationEntry::getPrincipal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2);

        assertEquals(p.setScale(2), totalPrincipalPaid);

        // Final remaining balance must be zero
        assertEquals(BigDecimal.ZERO.setScale(2), schedule.get(schedule.size() - 1).getRemainingBalance());
    }
}
