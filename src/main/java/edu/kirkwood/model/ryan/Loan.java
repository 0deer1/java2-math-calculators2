package edu.kirkwood.model.ryan;

import edu.kirkwood.view.Helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * The LoanCalculator class provides methods to calculate loan payments,
 * total payments, total interest, and generate an amortization schedule
 * based on the principal amount, annual interest rate, and loan term in years.
 * It also allows for setting a budgeted monthly payment and down payment to
 * calculate the maximum affordable loan amount.
 */
public class Loan {

    private double principal;
    private double annualRate;
    private int termYears;
    private double budgetedPayment = 1.0;
    private double downPayment = 0.0;

    /**
     * Default constructor that initializes the LoanCalculator with default values.
     */
    public Loan() {
        principal = 1.0;
        annualRate = 1.0;
        termYears = 1;
    }

    /**
     * Constructs a LoanCalculator with the specified principal, annual interest rate, and term in years.
     *
     * @param principal  the principal amount of the loan
     * @param annualRate the annual interest rate as a percentage (e.g., 5 for 5%)
     * @param termYears  the term of the loan in years
     */
    public Loan(double principal, double annualRate, int termYears) {
        setPrincipal(principal);
        setAnnualRate(annualRate);
        setTermYears(termYears);
    }

    /**
     * Gets the principal amount of the loan.
     *
     * @return the principal amount
     */
    public double getPrincipal() {
        return principal;
    }

    /**
     * Gets the annual interest rate of the loan.
     *
     * @return the annual interest rate
     */
    public double getAnnualRate() {
        return annualRate;
    }

    /**
     * Gets the term of the loan in years.
     *
     * @return the term of the loan in years
     */
    public int getTermYears() {
        return termYears;
    }

    /**
     * Gets the budgeted monthly payment for the loan.
     *
     * @return the budgeted monthly payment
     */
    public double getBudgetedPayment() {
        return budgetedPayment;
    }

    /**
     * Gets the down payment for the loan.
     *
     * @return the down payment
     */
    public double getDownPayment() {
        return downPayment;
    }

    /**
     * Sets the annual interest rate for the loan.
     *
     * @param annualRate the annual interest rate as a percentage (e.g., 5 for 5%)
     */
    public void setAnnualRate(double annualRate) {
        if(annualRate < 0) {
            throw new IllegalArgumentException("Annual Rate cannot be negative");
        }else{
            this.annualRate = annualRate;
        }
    }

    /**
     * Sets the principal amount for the loan.
     *
     * @param principal the principal amount of the loan
     */
    public void setPrincipal(double principal) {
        if(principal <= 0){
            throw new IllegalArgumentException("Principal must be a positive number.");
        } else {
            this.principal = principal;
        }
    }

    /**
     * Sets the term of the loan in years.
     *
     * @param termYears the term of the loan in years
     */
    public void setTermYears(int termYears) {
        if(termYears <= 0){
            throw new IllegalArgumentException("Loan term must be greater than zero.");
        }else {
            this.termYears = termYears;
        }
    }

    /**
     * Sets the budgeted monthly payment for the loan.
     *
     * @param budgetedPayment the budgeted monthly payment
     */
    public void setBudgetedPayment(double budgetedPayment) {
        if(budgetedPayment <= 0){
            throw new IllegalArgumentException("Budget must be a positive number.");
        } else {
            this.budgetedPayment = budgetedPayment;
        }
    }

    /**
     * Sets the down payment for the loan.
     *
     * @param downPayment the down payment
     */
    public void setDownPayment(double downPayment) {
        if(downPayment < 0){
            throw new IllegalArgumentException("Down payment cannot be negative.");
        } else {
            this.downPayment = downPayment;
        }
    }

    /**
     * Calculates the monthly payment for the loan using the formula:
     * M = P[r(1+r)^n]/[(1+r)^n - 1]
     * where:
     * M = monthly payment
     * P = principal amount
     * r = monthly interest rate (annualRate / 12)
     * n = total number of payments (termYears * 12)
     *
     * @return the monthly payment amount
     */
    public double calculateMonthlyPayment() {
        // Implement the formula for calculating monthly payment
        double r = (annualRate / 100) / 12;
        double n = termYears * 12;
        if (r == 0) {
            return principal / n;
        } else {
            double value = Math.pow((1 + r), n);
            return Double.parseDouble(Helpers.round(principal * (r * value) / (value - 1), 2));
        }
    }



    /**
     * Calculates the total payment over the life of the loan.
     *
     * @return the total payment amount
     */
    public double calculateTotalPayment() {
        // Implement the formula for calculating total payment
        double n = termYears * 12;
        return calculateMonthlyPayment() * n;
    }

    /**
     * Calculates the total interest paid over the life of the loan.
     *
     * @return the total interest amount
     */
    public double calculateTotalInterest() {
        // Implement the formula for calculating total interest
        return calculateTotalPayment() - principal;
    }

    /**
     * Calculates the maximum affordable loan amount based on the budgeted monthly payment.
     *
     * @return the maximum affordable loan amount
     */
    public double calculateAffordableLoan(){
        double r = (annualRate / 100) / 12;
        double n = termYears * 12;
        if (r == 0) {
            return budgetedPayment * n;
        } else {
            double value = Math.pow((1 + r), -n);
            return Double.parseDouble(Helpers.round(budgetedPayment * ((1 - value) / r), 2));
        }
    }

    /**
     * Returns the amortization schedule for the loan.
     * The schedule includes the payment number, payment amount, principal paid,
     * interest paid, and remaining balance for each payment period.
     */
    public List<PaymentRecord> amortizationSchedule() {
        //  Implement the logic to return the amortization schedule.
        double monthlyPayment = calculateMonthlyPayment();
        double remainingBalance = principal;
        double r = (annualRate / 100) / 12;
        int n = termYears * 12;
        List<PaymentRecord> schedule = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            double interestPaid = remainingBalance * r;
            double principalPaid;
            double payment;

            if (i == n) {
                // last payment: clear the balance exactly
                payment = remainingBalance + interestPaid;
            } else {
                payment = monthlyPayment;
            }

            principalPaid = payment - interestPaid;
            remainingBalance -= principalPaid;
            PaymentRecord record = new PaymentRecord(i, payment, principalPaid, interestPaid, remainingBalance);
            schedule.add(record);
        }
        return schedule;
    }


    /**
     * Compares this LoanCalculator object with another LoanCalculator object for order.
     * The comparison is based on the principal amount, annual interest rate, and term in years.
     *
     * @param o the other LoanCalculator object to be compared
     * @return a negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object
     */
    public int compareTo(Loan o) {
        int result = Double.compare(this.principal, o.principal);
        if (result != 0) {
            return result;
        }
        result = Double.compare(this.annualRate, o.annualRate);
        if (result != 0) {
            return result;
        }
        return Integer.compare(this.termYears, o.termYears);
    }

    /**
     * Checks if this LoanCalculator object is equal to another object.
     * Two LoanCalculator objects are considered equal if they have the same
     * principal amount, annual interest rate, and term in years.
     *
     * @param o the object to be compared for equality
     * @return true if the objects are equal; false otherwise
     */
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Double.compare(loan.principal, principal) == 0 &&
                Double.compare(loan.annualRate, annualRate) == 0 &&
                termYears == loan.termYears;
    }

    /**
     * PaymentRecord class provides methods that hold the paymentNumber, paymentAmount, interestPaid,
     * principalPaid and remainingBalance for the amortizationSchedule method.
     */
    // Claude AI helped me understand nested classes, this class is static because it is fully self-contained,
    // it receives already validated inputs (nothing new), once it has all 5 variables it stores them
    // as a record object and the amortizationSchedule method then adds it to the schedule.
    public static class PaymentRecord{
        private final int paymentNumber;
        private final double paymentAmount;
        private final double interestPaid;
        private final double principalPaid;
        private final double remainingBalance;

        /**
         * Constructs a PaymentRecord with the specified paymentNumber, paymentAmount, principalPaid, interestPaid and remainingBalance
         *
         * @param paymentNumber the payment number
         * @param paymentAmount the current payment amount
         * @param principalPaid the amount being paid into the principal of the loan
         * @param interestPaid  the amount being paid into the interest of the loan
         * @param remainingBalance the amount of the loan left
         */
        public PaymentRecord(int paymentNumber, double paymentAmount, double principalPaid, double interestPaid, double remainingBalance) {
            this.paymentNumber = paymentNumber;
            this.paymentAmount = paymentAmount;
            this.principalPaid = principalPaid;
            this.interestPaid = interestPaid;
            this.remainingBalance = remainingBalance;
        }

        /**
         * Get the payment number
         *
         * @return the payment number
         */
        public int getPaymentNumber() {
            return paymentNumber;
        }

        /**
         * Get the payment amount
         *
         * @return the payment amount
         */
        public double getPaymentAmount() {
            return paymentAmount;
        }

        /**
         * Get the interest paid
         *
         * @return the interest paid
         */
        public double getInterestPaid() {
            return interestPaid;
        }

        /**
         * Get the principal paid
         *
         * @return the principal paid
         */
        public double getPrincipalPaid() {
            return principalPaid;
        }

        /**
         * Get the remaining balance
         *
         * @return the remaining balance
         */
        public double getRemainingBalance() {
            return remainingBalance;
        }
    }
}
