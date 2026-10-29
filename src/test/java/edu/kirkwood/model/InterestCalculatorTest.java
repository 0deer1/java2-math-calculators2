package edu.kirkwood.model;

import edu.kirkwood.model.noah.InterestCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InterestCalculatorTest {

    private InterestCalculator investment1;
    private InterestCalculator investment2;
    private InterestCalculator investment2Copy;
    private InterestCalculator simpleInvestment;
    private InterestCalculator contributionsInvestment;

    @BeforeEach
    void setUp() {
        // Default, Investing 500 dollars, with an interest rate of 5%, compounding every 2 months, for a year.
        investment1 = new InterestCalculator();
        // Investing 1000 dollars, with an interest rate of 5%, compounding monthly, for 3 years.
        investment2 = new InterestCalculator(1000,0.05,12,3);
        investment2Copy = new InterestCalculator(1000,0.05,12,3);
        // Investing 1000 dollars, with an interest rate of 5%, non-compounding, for 3 years.
        simpleInvestment = new InterestCalculator(1000,0.05,3);
        // Investing 1000 dollars, with an interest rate of 5%, compounding monthly, for 3 years, contributing 100 dollars every month.
        contributionsInvestment = new InterestCalculator(1000,0.05,12,3,100);
    }

    // This must throw an exception, as you cannot calculate interest on an investment that does not exist.
    @Test
    void testPrincipalEqualsZero() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> investment1.setPrincipal(0));
    }

    // This must throw an exception, as you cannot calculate interest without interest.
    @Test
    void testInterestRateEqualsZero() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> investment1.setAnnualRate(0));
    }

    // This must throw an exception, as you cannot calculate interest over time without a timeframe.
    @Test
    void testTotalYearsEqualsZero() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> investment1.setYears(0));
    }

    // Testing if the calculateCompoundingInterest() function calculates correctly.
    @Test
    void testCalculateCompoundingInterest() {
        // Act
        double totalProfit = investment2.calculateCompoundingInterest();
        // Assert
        assertEquals(1161.47, totalProfit);
    }

    // Testing if the calculateSimpleInterest() function calculates correctly.
    @Test
    void testCalculateSimpleInterest() {
        // Act
        double totalProfit = simpleInvestment.calculateSimpleInterest();
        // Assert
        assertEquals(1150, totalProfit);
    }

    // Testing if the calculateCompoundingInterestWithContributions() function calculates correctly.
    @Test
    void testCalculateCompoundingInterestWithContributions() {
        // Act
        double totalProfit = contributionsInvestment.calculateCompoundingInterestWithContributions();
        // Assert
        assertEquals(5036.81, totalProfit);
    }

    /* Should display all investment information properly. Two decimal places allowed in interest rate and principal,
    one decimal place allowed in total years. */
    @Test
    void testToString() {
        // Act and Assert
        assertEquals("Principal Investment/Value: 1000.00\nInterest Rate: 0.05\nPeriods per Year: 12\nTotal Years: 3.0",
                investment2.toString());
        assertEquals("Principal Investment/Value: 1000.00\nInterest Rate: 0.05\nTotal Years: 3.0",
                simpleInvestment.toString());
        assertEquals("Principal Investment/Value: 1000.00\nInterest Rate: 0.05\nPeriods per Year: 12\nTotal Years: 3.0\nContribution Amount: 100.00",
                contributionsInvestment.toString());
    }

    // Testing Getters

    @Test
    void testGetContributions() {
        // Assert
        assertEquals(100, contributionsInvestment.getContributions());
    }

    @Test
    void testGetPrincipal() {
        // Assert
        assertEquals(1000, simpleInvestment.getPrincipal());
        assertEquals(1000, investment2.getPrincipal());
        assertEquals(500, investment1.getPrincipal());
    }

    @Test
    void testGetAnnualRate() {
        // Assert
        assertEquals(0.05, simpleInvestment.getAnnualRate());
        assertEquals(0.05, investment2.getAnnualRate());
        assertEquals(0.05, investment1.getAnnualRate());
    }

    @Test
    void testGetPeriodsPerYear() {
        // Assert
        assertEquals(12, investment2.getPeriodsPerYear());
        assertEquals(6, investment1.getPeriodsPerYear());
    }

    @Test
    void testGetYears() {
        // Assert
        assertEquals(3, simpleInvestment.getYears());
        assertEquals(3, investment2.getYears());
        assertEquals(1, investment1.getYears());
    }

    // Testing Setters

    @Test
    void testSetContributions() {
        // Act
        contributionsInvestment.setContributions(200);
        // Assert
        assertEquals(200, contributionsInvestment.getContributions());
    }


    @Test
    void testSetPrincipal() {
        // Act
        investment1.setPrincipal(10000);
        investment2.setPrincipal(10000);
        // Assert
        assertEquals(10000, investment1.getPrincipal());
        assertEquals(10000, investment2.getPrincipal());
    }

    @Test
    void testSetAnnualRate() {
        // Act
        investment1.setAnnualRate(0.03);
        investment2.setAnnualRate(0.03);
        // Assert
        assertEquals(0.03, investment1.getAnnualRate());
        assertEquals(0.03, investment2.getAnnualRate());
    }

    @Test
    void testSetYears() {
        // Act
        investment1.setYears(10);
        investment2.setYears(10);
        // Assert
        assertEquals(10, investment1.getYears());
        assertEquals(10, investment2.getYears());
    }

    @Test
    void testEquals() {
        // Act and Assert
        assertTrue(investment2.equals(investment2Copy));
    }

    @Test
    void testCompareTo() {
        // Act and Assert
        assertEquals(0, investment2.compareTo(investment2Copy));
    }

}
