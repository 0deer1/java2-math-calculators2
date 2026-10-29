package edu.kirkwood.model.issouf;

import edu.kirkwood.model.issouf.RetirementAccount;

import java.util.Objects;

/**
 * Represents a 401(k) retirement projection.
 * This class provides the time-based projections from the current age
 * to the retirement age.
 */
public class RetirementProjection implements Comparable<RetirementProjection> {

    private int currentAge;
    private int retirementAge;
    private double salaryIncreaseRate;
    private double annualReturnRate;

    /**
     * Default constructor
     * Initializes a new projection with a current age of 1, a retirement
     * age of 2, and zero percentages.
     */
    public RetirementProjection() {
        setCurrentAge(1);
        setRetirementAge(2);
        setSalaryIncreaseRate(0.0);
        setAnnualReturnRate(0.0);
    }

    /**
     * Constructs a retirement projection with the specified values.
     *
     * @param currentAge         the current age
     * @param retirementAge      the planned retirement age
     * @param salaryIncreaseRate the expected annual salary increase percentage
     * @param annualReturnRate   the expected annual return percentage
     */
    public RetirementProjection(int currentAge,
                                int retirementAge,
                                double salaryIncreaseRate,
                                double annualReturnRate) {
        setCurrentAge(currentAge);
        setRetirementAge(retirementAge);
        setSalaryIncreaseRate(salaryIncreaseRate);
        setAnnualReturnRate(annualReturnRate);
    }

    /**
     * Gets the current age.
     *
     * @return the current age
     */
    public int getCurrentAge() {
        return currentAge;
    }

    /**
     * Sets the current age.
     *
     * @param currentAge the current age
     * @throws IllegalArgumentException if the age is less than one
     */
    public void setCurrentAge(int currentAge) {
        if (currentAge < 1) {
            throw new IllegalArgumentException("Current age must be greater than zero");
        }
        this.currentAge = currentAge;
    }

    /**
     * Gets the retirement age.
     *
     * @return the retirement age
     */
    public int getRetirementAge() {
        return retirementAge;
    }

    /**
     * Sets the retirement age.
     *
     * @param retirementAge the planned retirement age
     * @throws IllegalArgumentException if the retirement age is not greater than the current age
     */
    public void setRetirementAge(int retirementAge) {
        if (retirementAge <= currentAge) {
            throw new IllegalArgumentException("Retirement age must be greater than current age");
        }
        this.retirementAge = retirementAge;
    }

    /**
     * Gets the salary increase rate.
     *
     * @return the salary increase percentage
     */
    public double getSalaryIncreaseRate() {
        return salaryIncreaseRate;
    }

    /**
     * Sets the salary increase rate.
     *
     * @param salaryIncreaseRate the expected annual salary increase percentage
     * @throws IllegalArgumentException if the rate is less than zero
     */
    public void setSalaryIncreaseRate(double salaryIncreaseRate) {
        if (salaryIncreaseRate < 0) {
            throw new IllegalArgumentException("Salary increase rate cannot be negative");
        }
        this.salaryIncreaseRate = salaryIncreaseRate;
    }

    /**
     * Gets the annual return rate.
     *
     * @return the annual return percentage
     */
    public double getAnnualReturnRate() {
        return annualReturnRate;
    }

    /**
     * Sets the annual return rate.
     *
     * @param annualReturnRate the expected annual return percentage
     * @throws IllegalArgumentException if the rate is less than zero
     */
    public void setAnnualReturnRate(double annualReturnRate) {
        if (annualReturnRate < 0) {
            throw new IllegalArgumentException("Annual return rate cannot be negative");
        }
        this.annualReturnRate = annualReturnRate;
    }

    /**
     * Calculates the number of years until retirement.
     *
     * @return the years until retirement
     */
    public int calculateYearsUntilRetirement() {
        return retirementAge - currentAge;
    }

    /**
     * Projects the salary forward a number of years.
     *
     * @param salary the starting annual salary
     * @param year   the number of years to project
     * @return the projected salary
     */
    public double projectFutureSalary(double salary, int year) {
        double projectedSalary = salary;
        for (int i = 0; i < year; i++) {
            projectedSalary *= (1 + salaryIncreaseRate / 100.0);
        }
        return projectedSalary;
    }

    /**
     * Projects the 401(k) balance at retirement.
     *
     * @param account the retirement account to project
     * @return the projected balance at retirement
     */
    public double projectRetirementBalance(RetirementAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        if (account.getContributionRate() == 0.0
                && account.getEmployerMatchRate() == 0.0
                && account.getEmployerMatchLimit() == 0.0) {
            return account.getCurrentBalance();
        }

        double balance = account.getCurrentBalance();
        double salary = account.getAnnualSalary();

        for (int i = 0; i < calculateYearsUntilRetirement(); i++) {
            double employeeContribution = salary * account.getContributionRate() / 100.0;
            double matchedPercent = Math.min(account.getContributionRate(), account.getEmployerMatchLimit());
            double employerContribution = salary * matchedPercent / 100.0 * account.getEmployerMatchRate() / 100.0;
            balance = (balance + employeeContribution + employerContribution) * (1 + annualReturnRate / 100.0);
            salary = projectFutureSalary(salary, 1);
        }

        return balance;
    }

    /**
     * Generates a summary of the projection results.
     *
     * @return a string representation of the projection summary
     */
    public String generateProjectionSummary() {
        return "Retirement projection: current age " + currentAge
                + ", retirement age " + retirementAge
                + ", salary increase rate " + salaryIncreaseRate + "%"
                + ", annual return rate " + annualReturnRate + "%";
    }

    /**
     * Displays the projection results.
     */
    public void displayProjection() {
        System.out.println(generateProjectionSummary());
    }

    /**
     * Compares this projection to another by current age.
     *
     * @param o the other RetirementProjection object to be compared.
     * @return a negative int, zero, or a positive int.
     */
    @Override
    public int compareTo(RetirementProjection o) {
        Objects.requireNonNull(o, "RetirementProjection to compare cannot be null");
        return Integer.compare(currentAge, o.currentAge);
    }

    /**
     * Determines if two projection objects are the same.
     *
     * @param o the other object to be compared.
     * @return a boolean true if both objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RetirementProjection projection = (RetirementProjection) o;
        return currentAge == projection.currentAge
                && retirementAge == projection.retirementAge
                && Double.compare(salaryIncreaseRate, projection.salaryIncreaseRate) == 0
                && Double.compare(annualReturnRate, projection.annualReturnRate) == 0;
    }

    /**
     * This method is supported for the benefit of hash tables such as HashMap and HashSet.
     *
     * @return an integer representing a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(currentAge, retirementAge, salaryIncreaseRate, annualReturnRate);
    }

    /**
     * Returns a string representation of the projection.
     *
     * @return a string representation of the projection
     */
    @Override
    public String toString() {
        return currentAge + " to " + retirementAge;
    }
}
