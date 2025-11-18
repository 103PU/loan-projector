package com.example.loan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public final class LoanCalculator {

    // Precision for internal calculations to avoid rounding errors
    private static final int SCALE = 10;

    // Standard rounding mode for financial calculations
    private static final RoundingMode ROUND = RoundingMode.HALF_EVEN;

    private LoanCalculator() {
    }

    /**
     * Converts annual interest percentage (e.g., 12%) to monthly decimal rate.
     * Example: 12% → 0.01
     */
    public static BigDecimal convertAnnualRateToMonthly(BigDecimal annualPercent) {
        ValidationUtils.requireNonNull(annualPercent, "annualPercent");
        return annualPercent.divide(BigDecimal.valueOf(100), SCALE, ROUND)
                .divide(BigDecimal.valueOf(12), SCALE, ROUND);
    }

    /**
     * Calculates monthly installment using the standard amortization formula.
     * Handles zero-interest loans as a special case.
     */
    public static BigDecimal calcMonthlyPayment(BigDecimal principal, BigDecimal annualPercent, int months) {
        ValidationUtils.requirePositive(principal, "principal");
        ValidationUtils.requireNonNegative(annualPercent, "annualPercent");
        ValidationUtils.requirePositiveMonths(months, "months");

        BigDecimal monthlyRate = convertAnnualRateToMonthly(annualPercent);

        // If interest rate = 0, simply divide the principal evenly
        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(BigDecimal.valueOf(months), 2, ROUND);
        }

        BigDecimal r = monthlyRate;
        BigDecimal onePlusR = BigDecimal.ONE.add(r);
        BigDecimal pow = onePlusR.pow(months);

        // Standard EMI formula: P*r / (1 - (1+r)^-n)
        BigDecimal numerator = principal.multiply(r);
        BigDecimal denominator = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(pow, SCALE, ROUND));

        BigDecimal monthly = numerator.divide(denominator, SCALE, ROUND);
        return monthly.setScale(2, ROUND);
    }

    /**
     * Generates a full amortization schedule with monthly breakdown.
     */
    public static List<AmortizationEntry> generateSchedule(BigDecimal principal, BigDecimal annualPercent, int months) {
        ValidationUtils.requirePositive(principal, "principal");
        ValidationUtils.requireNonNegative(annualPercent, "annualPercent");
        ValidationUtils.requirePositiveMonths(months, "months");

        BigDecimal monthlyPayment = calcMonthlyPayment(principal, annualPercent, months);
        List<AmortizationEntry> schedule = new ArrayList<>(months);

        BigDecimal balance = principal.setScale(SCALE, ROUND);
        BigDecimal rate = convertAnnualRateToMonthly(annualPercent);

        for (int m = 1; m <= months; m++) {

            // Monthly interest = current balance * monthly rate
            BigDecimal interest = balance.multiply(rate).setScale(10, ROUND);

            // Principal paid this month = payment - interest
            BigDecimal principalPaid = monthlyPayment.subtract(interest).setScale(10, ROUND);

            // Adjust final payment to clear any rounding leftover
            if (m == months) {
                principalPaid = balance;
                monthlyPayment = principalPaid.add(interest).setScale(2, ROUND);
            }

            balance = balance.subtract(principalPaid).setScale(10, ROUND);

            // Prevent negative balance from rounding drift
            if (balance.compareTo(BigDecimal.ZERO) < 0) {
                balance = BigDecimal.ZERO;
            }

            schedule.add(new AmortizationEntry(
                    m,
                    monthlyPayment.setScale(2, ROUND),
                    interest.setScale(2, ROUND),
                    principalPaid.setScale(2, ROUND),
                    balance.setScale(2, ROUND)));
        }

        return schedule;
    }

    /**
     * Sums all payments in the schedule to calculate total paid.
     */
    public static BigDecimal computeTotalPaid(List<AmortizationEntry> schedule) {
        ValidationUtils.requireNonNull(schedule, "schedule");
        return schedule.stream()
                .map(AmortizationEntry::getPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, ROUND);
    }
}
