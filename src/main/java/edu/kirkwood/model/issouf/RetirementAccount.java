package edu.kirkwood.model.issouf;

import java.util.Objects;

/**
 * Represents a 401(k) retirement account.
 * This class provides the yearly contribution and growth calculations for
 * a single retirement account.
 */
public class RetirementAccount {

    private double currentBalance;
    private double annualSalary;
    private double contributionRate;
    private double employerMatchRate;
    private double employerMatchLimit;

    /**
     * Default constructor
     * Initializes a new account with a zero balance, a zero salary,
     * and zero percentages.
     */
    public RetirementAccount() {
        setCurrentBalance(0.0);
        setAnnualSalary(1.0);
        setContributionRate(0.0);
        setEmployerMatchRate(0.0);
        setEmployerMatchLimit(0.0);
    }

    /**
     * Constructs a retirement account with the specified values.
     *
     * @param currentBalance     the starting 401(k) balance
     * @param annualSalary       the current annual salary
     * @param contributionRate    the employee contribution percentage
     * @param employerMatchRate   the employer match percentage
     * @param employerMatchLimit  the maximum percentage of salary matched
     */
    public RetirementAccount(double currentBalance,
                             double annualSalary,
                             double contributionRate,
                             double employerMatchRate,
                             double employerMatchLimit) {
        setCurrentBalance(currentBalance);
        setAnnualSalary(annualSalary);
        setContributionRate(contributionRate);
        setEmployerMatchRate(employerMatchRate);
        setEmployerMatchLimit(employerMatchLimit);
    }

    /**
     * Gets the current balance of the account.
     *
     * @return the current balance
     */
    public double getCurrentBalance() {
        return currentBalance;
    }

    /**
     * Sets the current balance of the account.
     *
     * @param currentBalance the starting 401(k) balance
     * @throws IllegalArgumentException if the balance is less than zero
     */
    public void setCurrentBalance(double currentBalance) {
        if (currentBalance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.currentBalance = currentBalance;
    }

    /**
     * Gets the annual salary.
     *
     * @return the annual salary
     */
    public double getAnnualSalary() {
        return annualSalary;
    }

    /**
     * Sets the annual salary.
     *
     * @param annualSalary the current annual salary
     * @throws IllegalArgumentException if the salary is not greater than zero
     */
    public void setAnnualSalary(double annualSalary) {
        if (annualSalary <= 0) {
            throw new IllegalArgumentException("Salary must be greater than zero");
        }
        this.annualSalary = annualSalary;
    }

    /**
     * Gets the employee contribution rate.
     *
     * @return the contribution percentage
     */
    public double getContributionRate() {
        return contributionRate;
    }

    /**
     * Sets the employee contribution rate.
     *
     * @param contributionRate the employee contribution percentage
     * @throws IllegalArgumentException if the rate is less than zero
     */
    public void setContributionRate(double contributionRate) {
        if (contributionRate < 0) {
            throw new IllegalArgumentException("Contribution rate cannot be negative");
        }
        this.contributionRate = contributionRate;
    }

    /**
     * Gets the employer match rate.
     *
     * @return the employer match percentage
     */
    public double getEmployerMatchRate() {
        return employerMatchRate;
    }

    /**
     * Sets the employer match rate.
     *
     * @param employerMatchRate the employer match percentage
     * @throws IllegalArgumentException if the rate is less than zero
     */
    public void setEmployerMatchRate(double employerMatchRate) {
        if (employerMatchRate < 0) {
            throw new IllegalArgumentException("Employer match rate cannot be negative");
        }
        this.employerMatchRate = employerMatchRate;
    }

    /**
     * Gets the employer match limit.
     *
     * @return the maximum percentage of salary matched
     */
    public double getEmployerMatchLimit() {
        return employerMatchLimit;
    }

    /**
     * Sets the employer match limit.
     *
     * @param employerMatchLimit the maximum percentage of salary matched
     * @throws IllegalArgumentException if the limit is less than zero
     */
    public void setEmployerMatchLimit(double employerMatchLimit) {
        if (employerMatchLimit < 0) {
            throw new IllegalArgumentException("Employer match limit cannot be negative");
        }
        this.employerMatchLimit = employerMatchLimit;
    }

    /**
     * Calculates the employee's annual contribution.
     *
     * @return the employee contribution for one year
     */
    public double calculateEmployeeContribution() {
        return annualSalary * contributionRate / 100.0;
    }

    /**
     * Calculates the employer's annual matching contribution.
     *
     * @return the employer contribution for one year
     */
    public double calculateEmployerContribution() {
        double matchedPercent = Math.min(contributionRate, employerMatchLimit);
        return annualSalary * matchedPercent / 100.0 * employerMatchRate / 100.0;
    }

    /**
     * Calculates the investment growth on the balance for one year.
     *
     * @param returnRate the expected annual return percentage
     * @return the growth amount for the year
     */
    public double calculateAnnualGrowth(double returnRate) {
        return currentBalance * returnRate / 100.0;
    }

    /**
     * Advances the account balance by one year of contributions and growth.
     *
     * @param returnRate the expected annual return percentage
     * @return the account balance after one year
     */
    public double updateAnnualBalance(double returnRate) {
        double contribution = calculateEmployeeContribution();
        double employerContribution = calculateEmployerContribution();
        double newBalance = (currentBalance + contribution + employerContribution) * (1 + returnRate / 100.0);
        currentBalance = newBalance;
        return currentBalance;
    }

    /**
     * Determines if two account objects are the same.
     *
     * @param o the other object to be compared.
     * @return a boolean true if both objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RetirementAccount account = (RetirementAccount) o;
        return Double.compare(currentBalance, account.currentBalance) == 0
                && Double.compare(annualSalary, account.annualSalary) == 0
                && Double.compare(contributionRate, account.contributionRate) == 0
                && Double.compare(employerMatchRate, account.employerMatchRate) == 0
                && Double.compare(employerMatchLimit, account.employerMatchLimit) == 0;
    }

    /**
     * This method is supported for the benefit of hash tables such as HashMap and HashSet.
     *
     * @return an integer representing a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(currentBalance, annualSalary, contributionRate,
                employerMatchRate, employerMatchLimit);
    }

    /**
     * Returns a string representation of the account.
     *
     * @return a string representation of the account
     */
    @Override
    public String toString() {
        return "RetirementAccount";
    }
}
