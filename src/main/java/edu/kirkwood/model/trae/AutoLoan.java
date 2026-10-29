package edu.kirkwood.model.trae;


import edu.kirkwood.view.Helpers;

/**
 * Represents an auto loan and provides calculations for the loan.
 * The class calculates the loan amount, monthly payment, interest,
 * total cost, remaining balance, and amortization schedule.
 */
public class AutoLoan implements Comparable<AutoLoan>{
    // Instance variables that store the information about the auto loan
    private double carPrice;
    private double downPayment;
    private double interestRate;
    private int loanTerm;

    /**
     * Creates an AutoLoan object using default values:
     * car price of $13,450, down payment of $6,000,
     * annual interest rate of 8.4%, and loan term of 6 years.
     */
    public AutoLoan() {
        this.carPrice = 13450;
        this.downPayment = 6000;
        this.interestRate = 8.4;
        this.loanTerm = 6;
    }
    public AutoLoan(double carPrice, double downPayment, double interestRate, int loanTerm) {
        setCarPrice(carPrice);
        setDownPayment(downPayment);
        setInterestRate(interestRate);
        setLoanTerm(loanTerm);
    }


    /**
     * Gets the price of the car.
     *
     * @return the car price
     */
    public double getCarPrice() {
        return carPrice;
    }

    /**
     * Sets the price of the car.
     *
     * @param carPrice the price of the car
     */
    public void setCarPrice(double carPrice) {
        if (carPrice <= 0) {
            throw new IllegalArgumentException("Car Price must be greater than zero");
        }
        this.carPrice = carPrice;
    }


    /**
     * Gets the down payment.
     *
     * @return the down payment
     */
    public double getDownPayment() {
        return downPayment;
    }

    /**
     * Sets the down payment.
     *
     * @param downPayment the amount paid up front
     */
    public void setDownPayment(double downPayment) {
        if (downPayment < 0) {
            throw new IllegalArgumentException("Down payment cannot be less than zero");
        } else if(this.carPrice < downPayment) {
            throw new IllegalArgumentException("Down Payment can't be more than the car price");
        }
        this.downPayment = downPayment;
    }

    /**
     * Gets the interest rate.
     *
     * @return the annual interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the interest rate.
     * The interest rate must be greater than 0% and no more than 18%.
     *
     * @param interestRate the annual interest rate
     * @throws IllegalArgumentException if the interest rate is invalid
     */
    public void setInterestRate(double interestRate) {
        if (interestRate <= 0 || interestRate > 18) {
            throw new IllegalArgumentException("Interest rate must be greater than 0% and no more than 18%");
        }
        this.interestRate = interestRate;
    }

    /**
     * Gets the loan term.
     *
     * @return the loan term in years
     */
    public int getLoanTerm() {
        return loanTerm;
    }

    /**
     * Sets the loan term.
     *
     * @param loanTerm the length of the loan in years
     */
    public void setLoanTerm(int loanTerm) {
        if (loanTerm <= 0) {
            throw new IllegalArgumentException("Loan must be greater than 0");
        }
        this.loanTerm = loanTerm;
    }

    /**
     * Calculates the amount of money that needs to be borrowed.
     *
     * Loan amount = car price - down payment
     *
     * @return the amount financed
     */
    public double calculateLoanAmount() {
        double newCarPrice = this.carPrice;
        double newDownPayment = this.downPayment;

        double loanAmount = newCarPrice - newDownPayment;
        return loanAmount;
    }

    /**
     * Calculates the monthly loan payment.
     *
     * The annual interest rate is converted to a monthly
     * decimal interest rate before calculating the payment.
     *
     * @return the monthly payment
     */
    public double calculateMonthlyPayment() {
        double loanAmount = calculateLoanAmount();

        // Convert the annual percentage rate into a monthly decimal rate
        double newInterestRate = this.interestRate /100 / 12;
        int newLoanTerm = this.loanTerm * 12;

        // Calculate the monthly payment using the loan payment formula
        double monthlyPayment = loanAmount * (newInterestRate * Math.pow(1 + newInterestRate, newLoanTerm)) / (Math.pow(1+ newInterestRate, newLoanTerm) -1 );

        return monthlyPayment;
    }

    /**
     * Calculates the total amount of interest paid over the life of the loan.
     *
     * Total interest = total loan payments - loan amount
     *
     * @return the total interest paid
     */
    public double calculateTotalInterest() {
        double monthlyPayment = calculateMonthlyPayment();
        // Calculate the total number of monthly payments
        double numberOfPayments = this.loanTerm * 12;
        double loanAmount = calculateLoanAmount();

        // Find the total amount paid and subtract the original loan amount
        double totalInterest = (monthlyPayment * numberOfPayments) - loanAmount;

        return totalInterest;
    }

    /**
     * Calculates the total cost of the vehicle including
     * the down payment and all loan payments.
     *
     * @return the total cost of the vehicle
     */
    public double calculateTotalCost() {
        double monthlyPayment = calculateMonthlyPayment();
        // Convert the loan term from years to the total number of payments
        double numberOfPayments = this.loanTerm * 12;
        double newDownPayment = this.downPayment;

        // Add the down payment to all of the monthly loan payments
        double totalCost = newDownPayment + (monthlyPayment * numberOfPayments);

        return totalCost;

    }

    /**
     * Calculates the interest portion of the first monthly payment.
     *
     * @return the interest portion of the payment
     */

    public double calculateInterestPayment() {
        double loanAmount = calculateLoanAmount();
        // Convert the annual interest rate to a monthly decimal rate
        double monthlyInterestRate = this.interestRate / 100/ 12;

        // Interest is calculated using the current loan balance
        double interestPayment = loanAmount * monthlyInterestRate;

        return interestPayment;
    }

    /**
     * Calculates the principal portion of the first monthly payment.
     *
     * Principal payment = monthly payment - interest payment
     *
     * @return the principal portion of the payment
     */
    public double calculatePrincipalPayment() {
        double monthlyPayment = calculateMonthlyPayment();
        // Calculate the interest portion of the payment
        double interestPayment = calculateInterestPayment();

        // The rest of the payment goes toward the loan principal
        double principalPayment = monthlyPayment - interestPayment;

        return principalPayment;
    }

    /**
     * Calculates the remaining loan balance after the first payment.
     *
     * @return the remaining loan balance
     */
    public double calculateRemainingBalance() {
        double loanAmount = calculateLoanAmount();
        // Find how much of the first payment went toward the principal
        double principalPayment = calculatePrincipalPayment();

        // Subtract the principal payment from the original loan amount
        double remainingBalance = loanAmount - principalPayment;

        return remainingBalance;
    }

    /**
     * Calculates the number of payments required to pay off the loan.
     *
     * @return the number of payments needed to pay off the loan
     */
    public int calculateEarlyPayoff(double extraPayment) {
        double loanAmount = calculateLoanAmount();
        // The regular monthly payment is used to pay down the balance
        double monthlyPayment = calculateMonthlyPayment();
        // Convert the annual interest rate to a monthly decimal rate
        double monthlyInterestRate = this.interestRate / 100/ 12;

        int numberOfPayments = 0;
        // Start with the original loan balance
        double remainingBalance = loanAmount;

        if(extraPayment < 0) {
            throw new IllegalArgumentException("Extra Payment needs to be greater than zero");
        }

        // Continue making payments until the loan is paid off
        while (remainingBalance > 0) {
            // Calculate the interest based on the current balance
            double interestPayment = remainingBalance * monthlyInterestRate;
            // The rest of the payment goes toward the principal
            double principalPayment = monthlyPayment - interestPayment + extraPayment;

            // Subtract the principal payment from the remaining balance
            remainingBalance -= principalPayment;
            // Count the payment
            numberOfPayments++;
        }

        return numberOfPayments;
    }


    /**
     * Generates an amortization table showing each payment,
     * the principal paid, the interest paid, and the remaining balance.
     */
    public void generateAmortizationTable() {
        double loanAmount = calculateLoanAmount();
        // Get the regular monthly payment
        double monthlyPayment = calculateMonthlyPayment();
        // Convert the annual interest rate to a monthly decimal rate
        double monthlyInterestRate = this.interestRate / 100 / 12;
        // Convert the loan term from years to months
        int numberOfPayments = this.loanTerm * 12;

        // Start the amortization table with the original loan amount
        double remainingBalance = loanAmount;

        // Print the column headings
        // didn't know how to format this sourced @CHATGPT for help
        System.out.println("\n");
        System.out.printf(
                "%5s %12s %12s %12s %14s%n",
                "Month",
                "Payment",
                "Principal",
                "Interest",
                "Balance"
        );

        System.out.print("---------------------------------------------------------");
        System.out.println("\n");


        // Calculate each payment until the loan term is complete
        // paymentNumber = 1; keeping track of what payment we are currently calculating.
        for (int paymentNumber = 1;
             paymentNumber <= numberOfPayments;
             paymentNumber++) {

            // Calculate interest based on the current balance
            double interestPayment = remainingBalance * monthlyInterestRate;
            // Calculate how much of the payment reduces the loan
            double principalPayment = monthlyPayment - interestPayment;

            // Subtract the principal from the remaining balance
            remainingBalance -= principalPayment;

            // Prevent the balance from becoming negative
            if (remainingBalance < 0) {
                remainingBalance = 0;
            }


            // Display the current payment information
            // sourced@CHATGPT for formating
            System.out.printf(
                    "%5d %12s %12s %12s %14s%n",
                    paymentNumber,
                    Helpers.toCurrency(monthlyPayment),
                    Helpers.toCurrency(principalPayment),
                    Helpers.toCurrency(interestPayment),
                    Helpers.toCurrency(remainingBalance)
            );
        }
    }


    /**
     * Compares this AutoLoan to another AutoLoan based on car price.
     *
     * @param o the AutoLoan to compare to
     * @return a negative value if this car price is less, zero if equal,
     *         or a positive value if this car price is greater
     */
    @Override
    public int compareTo(AutoLoan o) {
        double carPrice = this.carPrice;
        double otherCarPrice = o.carPrice;

        return Double.compare(carPrice, otherCarPrice);
    }

    /**
     * Checks whether this AutoLoan is equal to another AutoLoan.
     *
     * @param o the object to compare to
     * @return true if all AutoLoan values are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AutoLoan autoLoan = (AutoLoan) o;

        return carPrice == autoLoan.carPrice && downPayment == autoLoan.downPayment && interestRate == autoLoan.interestRate &&loanTerm == autoLoan.loanTerm;
    }

    @Override
    /**
     * Returns a String containing the main information about the loan.
     *
     * @return formatted loan information
     */
    public String toString() {
        return "Auto Loan information\n" +
                String.format("%-20s %12s\n", "Car Price:", Helpers.toCurrency(carPrice)) +
                String.format("%-20s %12s\n", "Down Payment:", Helpers.toCurrency(downPayment)) +
                String.format("%-20s %12s\n", "Interest Rate:", interestRate + "%") +
                String.format("%-20s %12s\n", "Loan Term:", loanTerm + " years") +
                String.format("%-20s %12s\n", "Loan Amount:", Helpers.toCurrency(calculateLoanAmount())) +
                String.format("%-20s %12s\n", "Monthly Payment:", Helpers.toCurrency(calculateMonthlyPayment())) +
                String.format("%-20s %12s\n", "Total Interest:", Helpers.toCurrency(calculateTotalInterest())) +
                String.format("%-20s %12s", "Total Cost:", Helpers.toCurrency(calculateTotalCost()));

    }
}
