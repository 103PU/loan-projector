package com.example.loan;

import java.math.BigDecimal;
import java.util.Objects;

public final class AmortizationEntry {

    // The month index in the amortization schedule (1 → n)
    private final int month;

    // Total monthly payment (principal + interest)
    private final BigDecimal payment;

    // Interest part of the payment
    private final BigDecimal interest;

    // Principal part of the payment
    private final BigDecimal principal;

    // Remaining balance after this month's payment
    private final BigDecimal remainingBalance;

    // Creates an immutable amortization record for a given month
    public AmortizationEntry(int month,
            BigDecimal payment,
            BigDecimal interest,
            BigDecimal principal,
            BigDecimal remainingBalance) {
        this.month = month;
        this.payment = payment;
        this.interest = interest;
        this.principal = principal;
        this.remainingBalance = remainingBalance;
    }

    public int getMonth() {
        return month;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AmortizationEntry))
            return false;

        // Compare all key fields to determine if two entries are identical
        AmortizationEntry e = (AmortizationEntry) o;
        return month == e.month &&
                payment.equals(e.payment) &&
                interest.equals(e.interest) &&
                principal.equals(e.principal) &&
                remainingBalance.equals(e.remainingBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, payment, interest, principal, remainingBalance);
    }

    @Override
    public String toString() {
        // Provides a readable summary for debugging/logging
        return "AmortizationEntry{" +
                "month=" + month +
                ", payment=" + payment +
                ", interest=" + interest +
                ", principal=" + principal +
                ", remainingBalance=" + remainingBalance +
                '}';
    }
}
