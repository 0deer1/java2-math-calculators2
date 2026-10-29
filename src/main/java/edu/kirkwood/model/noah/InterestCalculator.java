package edu.kirkwood.model.noah;


import edu.kirkwood.view.Helpers;

import java.util.Objects;

/**
 * Represents an investment, requires at least a principal value, annual interest rate and total time.
 * Can overload with total yearly compounds for compounding interest.
 * Can also overload with contribution value for compounding interest with contributions.
 */
public class InterestCalculator implements Comparable<InterestCalculator> {
    private double principal;
    private double annualRate;
    private int periodsPerYear;
    private double years;
    private double contributions;


    /**
     * Default constructor.
     * Initializes with Principal = 500, Interest Rate = 5%, Compounding Bi-Monthly, For a year.
     */
    public InterestCalculator() {
        principal = 500;
        annualRate = 0.05;
        periodsPerYear = 6;
        years = 1;
    }

    /**
     * Parameterized constructor; Compounding interest investment with regular contributions.
     * Has a principal value, annual interest rate, number of compounding periods per year,
     * the amount of years, frequency of contributions, and the contribution value.
     *
     * @param principal             The starting investment.
     * @param annualRate            The annual interest rate.
     * @param periodsPerYear        The amount of times the investment compounds per year.
     * @param years                 The total amount of years the investment will be accruing interest.
     * @param contributions         The contribution value per term.
     */
    public InterestCalculator(double principal, double annualRate, int periodsPerYear, double years, double contributions) {
        setPrincipal(principal);
        setAnnualRate(annualRate);
        setPeriodsPerYear(periodsPerYear);
        setYears(years);
        setContributions(contributions);
    }

    /**
     * Parameterized constructor; Compounding interest investment.
     * Has a principal value, annual interest rate, number of compounding periods per year, and the amount of years.
     *
     * @param principal         The starting investment
     * @param annualRate        The annual interest rate.
     * @param periodsPerYear    The amount of times the investment compounds per year, as well as the frequency of contributions.
     * @param years             The total amount of years the investment will be accruing interest.
     */
    public InterestCalculator(double principal, double annualRate, int periodsPerYear, double years) {
        setPrincipal(principal);
        setAnnualRate(annualRate);
        setPeriodsPerYear(periodsPerYear);
        setYears(years);
    }

    /**
     * Parameterized constructor; Simple interest investment.
     * Has a principal value, annual interest rate, and the amount of years.
     *
     * @param principal         The starting investment.
     * @param annualRate        The annual interest rate.
     * @param years             The total amount of years the investment will be accruing interest.
     */
    public InterestCalculator(double principal, double annualRate, double years) {
        setPrincipal(principal);
        setAnnualRate(annualRate);
        setYears(years);
    }

    /**
     * Calculates profit made from a simple interest investment.
     *
     * @return The total amount of interest gained.
     */
    public double calculateSimpleInterest() {
        // Formula: I = P * R * T
        // Key: I = Total Interest, P = Principal Amount, R = Interest Rate, T = Time in Years

        double finalAmount = principal + (principal * annualRate * years);
        return Math.round(finalAmount * 100) / 100.0;
    }

    /**
     * Calculates profit made from a compounding interest investment.
     *
     * @return The total amount of interest gained.
     */
    public double calculateCompoundingInterest () {
        // Formula: A = P( 1 + R/N)^TN
        // Key: A = Final Amount, P = Principal Amount, R = Interest Rate, N = Periods per Year, T = Total Time

        double finalAmount = principal * Math.pow((1 + (annualRate/periodsPerYear)), (years*periodsPerYear));
        return Math.round(finalAmount * 100) / 100.0;
    }

    /**
     * Calculates profit made from an interest investment, including additions per user designated term.
     *
     * @return The total amount of interest gained.
     */
    public double calculateCompoundingInterestWithContributions () {
        // Formula: A = P( 1 + R/N)^TN + C * [((1+R/N)^TN - 1)/RN]
        // Key: A = Final Amount, P = Principal Amount, R = Interest Rate,
        //      N = Periods per Year, T = Total Time, C = Contribution Amount.

        double finalAmount = principal * Math.pow((1 + (annualRate/periodsPerYear)), (years*periodsPerYear))
                + contributions * (Math.pow((1 + (annualRate/periodsPerYear)), (years*periodsPerYear)) - 1)
                / (annualRate/periodsPerYear);
        return Math.round(finalAmount * 100) / 100.0;
    }

    /*
        Claude AI helped with the displayIncrementalInterestTable() function.
        Particularly, the (interestPerTerm) variable I did not have, and it helped refresh me on StringBuilder().

        After making this function, I now see you created a separator in UIUtility I would assume to be used particularly
        for cases like this, so that's my bad. Hopefully this is still okay.

        I did not write a test for these prior to implementation as I didn't know exactly what the tables
        were going to look like, but I wrote some code to test this function visually.

                InterestCalculator contributionsInvestment = new InterestCalculator(1000,0.05,12,3,100);
                System.out.print(contributionsInvestment.displayIncrementalInterestTable());

                InterestCalculator investment2 = new InterestCalculator(1000,0.05,12,3);
                System.out.print(investment2.displayIncrementalInterestTable());

                InterestCalculator simpleInvestment = new InterestCalculator(1000,0.05,3);
                System.out.println(simpleInvestment.displayIncrementalInterestTable());
    */

    /**
     * Displays a table showing incremental increases as interest distributes through each year/term.
     *
     * @return A string formatted as a table showing incremental interest gained per term.
     */
    public String displayIncrementalInterestTable () {
        StringBuilder sb = new StringBuilder();

        if (periodsPerYear == 0) {
            String separator = "*" + "-".repeat(40) + "*" + "\n"; // 6+12+12+10(Lengths of | strings)-2(asterisks)
            sb.append(String.format("%s| %-6s | %13s | %13s |\n%s",
                    separator,"Year","Interest","Value",separator));
            double value = principal;

            for (int year = 1; year <= years; year++) {
                double termInterest = principal * annualRate;
                value += termInterest;
                sb.append(String.format("| %-6d | %13s | %13s |\n",year,Helpers.toCurrency(termInterest),Helpers.toCurrency(value)));
            }
            return sb.toString();
        } else if (contributions == 0) {
            String separator = "*" + "-".repeat(40) + "*" + "\n";
            sb.append(String.format("%s| %-6s | %12s | %12s |\n%s",
                    separator,"Term","Interest","Value",separator));
            double value = principal;
            double interestPerTerm = annualRate / periodsPerYear;
            int totalTerms = (int) (years * periodsPerYear);

            for (int term = 1; term <= totalTerms; term++) {
                double termInterest = value * interestPerTerm;
                value += termInterest;
                sb.append(String.format("| %-6d | %12s | %12s |\n",term,Helpers.toCurrency(termInterest),Helpers.toCurrency(value)));
            }
            return sb.toString();
        } else {
            String separator = "*" + "-".repeat(64) + "*" + "\n";
            sb.append(String.format("%s| %-6s | %13s | %17s | %17s |\n%s",
                    separator, "Term", "Interest", "Contribution", "Value", separator));
            double value = principal;
            double interestPerTerm = annualRate / periodsPerYear;
            int totalTerms = (int) (years * periodsPerYear);

            for (int term = 1; term <= totalTerms; term++) {
                double termInterest = value * interestPerTerm;
                value += termInterest;
                value += contributions;
                sb.append(String.format("| %-6d | %12s | %16s | %16s |\n", term, Helpers.toCurrency(termInterest), Helpers.toCurrency(contributions), Helpers.toCurrency(value)));
            }
            return sb.toString();
        }
    }

    /**
     * Gets the regular contribution value per term.
     *
     * @return The regular contribution value per term.
     */
    public double getContributions() {
        return contributions;
    }

    /**
     * Sets the regular contribution value per term.
     *
     * @param contributions Contribution value per term.
     */
    public void setContributions(double contributions) {
        this.contributions = contributions;
    }
    
    /**
     * Gets the annual interest rate of the investment.
     *
     * @return The annual interest rate.
     */
    public double getAnnualRate() {
        return annualRate;
    }

    /**
     * Sets the annual interest rate of the investment.
     *
     * @param annualRate The annual interest rate.
     */
    public void setAnnualRate(double annualRate) {

        if (annualRate <= 0) {
            throw new IllegalArgumentException("Interest rate cannot be less than or equal to zero.");
        } else {
            this.annualRate = annualRate;
        }
    }

    /**
     * Gets the frequency of compounds and contributions for the investment.
     *
     * @return The amount of times the investment compounds per year, as well as the frequency of contributions.
     */
    public int getPeriodsPerYear() {
        return periodsPerYear;
    }

    /**
     * Sets the frequency of compounds and contributions for the investment.
     *
     * @param periodsPerYear The amount of times the investment compounds per year, or receives contributions.
     */
    public void setPeriodsPerYear(int periodsPerYear) {
        this.periodsPerYear = periodsPerYear;
    }

    /**
     * Gets the total years the investment will be accruing interest.
     *
     * @return The amount of years the investment will be accruing interest.
     */
    public double getYears() {
        return years;
    }

    /**
     * Sets the total years the investment will be accruing interest.
     *
     * @param years The amount of years the investment will be accruing interest.
     */
    public void setYears(double years) {
        if (years <= 0) {
            throw new IllegalArgumentException("Time in years cannot be less than or equal to zero.");
        } else {
            this.years = years;
        }
    }

    /**
     * Gets the principal investment (starting investment).
     *
     * @return The principal investment.
     */
    public double getPrincipal() {
        return principal;
    }

    /**
     * Sets the principal investment (starting investment).
     *
     * @param principal The principal investment.
     */
    public void setPrincipal(double principal) {
        if (principal <= 0) {
            throw new IllegalArgumentException("Principal value cannot be less than or equal to zero.");
        } else {
            this.principal = principal;
        }
    }

    /**
     * Returns a string representation of the investment information.
     *
     * @return A string representation of the investment information.
     */
    @Override
    public String toString() {
        if (contributions == 0.0 && periodsPerYear == 0) {
            return String.format(
                    "Principal Investment/Value: %.2f\nInterest Rate: %.2f\nTotal Years: %.1f"
                    ,principal,annualRate,years
            );
        } else if (contributions == 0.0) {
            return String.format(
                    "Principal Investment/Value: %.2f\nInterest Rate: %.2f\nPeriods per Year: %d\nTotal Years: %.1f"
                    ,principal,annualRate,periodsPerYear,years
            );
        } else {
            return String.format(
                    "Principal Investment/Value: %.2f\nInterest Rate: %.2f\nPeriods per Year: %d\nTotal Years: %.1f\nContribution Amount: %.2f"
                    ,principal,annualRate,periodsPerYear,years,contributions
            );
        }

    }

    /**
     * Compares one investment to another.
     *
     * @param o the other investment to be compared.
     * @return a negative int, zero, or positive int. Positives mean objects are out of sort order.
     */
    @Override
    public int compareTo(InterestCalculator o) {

        // This was generated by Claude AI for the most part. I changed the sort order, and variable names.

        int result = Double.compare(this.principal, o.principal);
        if (result != 0) return result;

        result = Double.compare(this.annualRate, o.annualRate);
        if (result != 0) return result;

        result = Integer.compare(this.periodsPerYear, o.periodsPerYear);
        if (result != 0) return result;

        result = Double.compare(this.years, o.years);
        if (result != 0) return result;

        return Double.compare(this.contributions, o.contributions);

    }

    /**
     * Determines if two investments are equivalent.
     *
     * @param o the other object to be compared.
     * @return a boolean true if both objects are the same, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        InterestCalculator other = (InterestCalculator) o;

        return Double.compare(principal, other.principal) == 0
                && Double.compare(annualRate, other.annualRate) == 0
                && periodsPerYear == other.periodsPerYear
                && Double.compare(years, other.years) == 0
                && Double.compare(contributions, other.contributions) == 0;
    }

    /**
     * This method is supported for the benefit of hash tables such as HashMap and HashSet.
     *
     * @return an integer representing a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(principal, annualRate, periodsPerYear, years, contributions);
    }
}
