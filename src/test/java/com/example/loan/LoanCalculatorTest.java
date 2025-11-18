package com.example.loan;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanCalculatorTest {

    @Test
    void calcMonthlyPayment_typicalLoan() {
        BigDecimal principal = BigDecimal.valueOf(100000);
        BigDecimal annual = BigDecimal.valueOf(5);
        int months = 360;

        // Standard 30-year mortgage calculation
        BigDecimal monthly = LoanCalculator.calcMonthlyPayment(principal, annual, months);

        assertEquals(BigDecimal.valueOf(536.82).setScale(2), monthly);
    }

    @Test
    void calcMonthlyPayment_zeroInterest() {
        BigDecimal p = BigDecimal.valueOf(1200);
        BigDecimal interest = BigDecimal.ZERO;
        int months = 12;

        // Zero-interest loan → equal monthly installments
        BigDecimal monthly = LoanCalculator.calcMonthlyPayment(p, interest, months);

        assertEquals(BigDecimal.valueOf(100.00).setScale(2), monthly);
    }

    @Test
    void generateSchedule_principalSumCorrect() {
        BigDecimal p = BigDecimal.valueOf(10000);
        BigDecimal annual = BigDecimal.valueOf(6);
        int months = 24;

        // Generate amortization breakdown
        List<AmortizationEntry> schedule = LoanCalculator.generateSchedule(p, annual, months);

        // Must generate exactly one entry per month
        assertEquals(months, schedule.size());

        // Loan should be fully paid at last entry
        assertEquals(BigDecimal.ZERO.setScale(2), schedule.get(months - 1).getRemainingBalance());

        // Validate that sum of principal paid equals original principal
        BigDecimal totalPrincipalPaid = schedule.stream()
                .map(AmortizationEntry::getPrincipal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2);

        assertEquals(p.setScale(2), totalPrincipalPaid);
    }

    @Test
    void computeTotalPaid_correct() {
        BigDecimal p = BigDecimal.valueOf(5000);
        BigDecimal annual = BigDecimal.valueOf(4);
        int months = 10;

        // Generate payment schedule and compute total payment
        List<AmortizationEntry> schedule = LoanCalculator.generateSchedule(p, annual, months);
        BigDecimal total = LoanCalculator.computeTotalPaid(schedule);

        // Expected total = sum of all "payment" fields
        BigDecimal expected = schedule.stream()
                .map(AmortizationEntry::getPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2);

        assertEquals(expected, total);
    }

    @Test
    void invalidInputs_throwException() {
        // Negative principal
        assertThrows(IllegalArgumentException.class,
                () -> LoanCalculator.calcMonthlyPayment(BigDecimal.valueOf(-1), BigDecimal.ONE, 12));

        // Negative interest rate
        assertThrows(IllegalArgumentException.class,
                () -> LoanCalculator.calcMonthlyPayment(BigDecimal.ONE, BigDecimal.valueOf(-1), 12));

        // Zero months → invalid duration
        assertThrows(IllegalArgumentException.class,
                () -> LoanCalculator.calcMonthlyPayment(BigDecimal.ONE, BigDecimal.ONE, 0));
    }

    @Test
    void oneMonthLoan() {
        BigDecimal p = BigDecimal.valueOf(1000);
        BigDecimal annual = BigDecimal.valueOf(12);

        // A 1-month loan should produce exactly one amortization entry
        List<AmortizationEntry> schedule = LoanCalculator.generateSchedule(p, annual, 1);
        assertEquals(1, schedule.size());
    }
}
