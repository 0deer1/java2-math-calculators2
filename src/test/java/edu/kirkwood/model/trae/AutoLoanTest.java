package edu.kirkwood.model;

import edu.kirkwood.model.trae.AutoLoan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutoLoanTest {

    private AutoLoan l1;
    private AutoLoan l2;

    @BeforeEach
    void setUp() {

        l1 = new AutoLoan();
        l2 = new AutoLoan(30000, 5000, 6.5, 5);
    }

    @Test
    void getCarPrice() {

        assertEquals(13450, l1.getCarPrice());
        assertEquals(30000, l2.getCarPrice());
    }

    @Test
    void setCarPriceValid() {
        // Act
        l1.setCarPrice(35000);

        // Assert
        assertEquals(35000, l1.getCarPrice());
    }

    @Test
    void setCarPriceZero() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> l1.setCarPrice(0));
    }

    @Test
    void setCarPriceNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> l1.setCarPrice(-1));
    }


    @Test
    void getDownPayment() {
        assertEquals(6000, l1.getDownPayment());
        assertEquals(5000, l2.getDownPayment());
    }

    @Test
    void setDownPaymentValid() {
        // Act
        l1.setDownPayment(7000);

        //Assert
        assertEquals(7000, l1.getDownPayment());
    }

    @Test
    void setDownPaymentNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> l1.setDownPayment(-1));
    }

    @Test
    void getInterestRate() {

        assertEquals(8.4, l1.getInterestRate());
        assertEquals(6.5, l2.getInterestRate());
    }

    @Test
    void setInterestRateValid() {
        // Act
        l1.setInterestRate(7.5);

        //Assert
        assertEquals(7.5, l1.getInterestRate());
    }

    @Test
    void setInterestRateZero() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> l1.setInterestRate(0));
    }

    @Test
    void setInterestRateNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> l1.setInterestRate(-1));
    }

    @Test
    void setInterestRateTooHigh() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> l1.setInterestRate(18.1));
    }

    @Test
    void getLoanTerm() {

        assertEquals(6, l1.getLoanTerm());
        assertEquals(5, l2.getLoanTerm());
    }

    @Test
    void setLoanTerm() {
        // Act
        l1.setLoanTerm(6);

        //Assert
        assertEquals(6, l1.getLoanTerm());
    }

    @Test
    void setLoanTermZero() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> l1.setLoanTerm(0));
    }

    @Test
    void setLoanTermNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> l1.setLoanTerm(-5));
    }

    @Test
    void calculateLoanAmount() {

        assertEquals(25000, l2.calculateLoanAmount());
    }

    @Test
    void calculateLoanAmountNoDownPayment() {
        // Arrange
        AutoLoan loan = new AutoLoan(30000, 0, 6.5, 5);

        //Act
        double result = loan.calculateLoanAmount();
        assertEquals(30000, result);
    }

    @Test
    void calculateMonthlyPayment() {
        //Arrange
        double expected = 489.15;

        //Act
        double result = l2.calculateMonthlyPayment();

        //Assert
        assertEquals(expected, result, 0.01);
    }

    @Test
    void calculateTotalInterest() {
        // Arrange
        double expected = 4349.22;

        // Act
        double result = l2.calculateTotalInterest();

        // Assert
        assertEquals(expected, result, 0.01);
    }

    @Test
    void calculateTotalCost() {
        // Arrange
        double expected = 34349.22;

        // Act
        double result = l2.calculateTotalCost();

        // Assert
        assertEquals(expected, result, 0.01);
    }

    @Test
    void calculateInterestPayment() {
        double expected = 135.42;

        // Act
        double result = l2.calculateInterestPayment();

        // Assert
        assertEquals(expected, result, 0.01);
    }

    @Test
    void calculateRemainingBalance() {
        // Arrange
        double expected = l1.calculateLoanAmount() - l1.calculatePrincipalPayment();

        //Act
        double result = l1.calculateRemainingBalance();

        assertEquals(expected, result);
    }

    @Test
    void calculateEarlyPayoff() {
        // ACT
        int result = l2.calculateEarlyPayoff(0);

        // Assert
        assertEquals(60, result);
    }

    @Test
    void calculateEarlyPayoffWithExtraPayment() {
        // ACT
        int result = l2.calculateEarlyPayoff(100);

        // Assert
        assertTrue(result < 60);
    }

    @Test
    void compareToGreaterThan() {
        l2 = new AutoLoan(30000, 5000, 6.5, 5);
        assertTrue(l2.compareTo(l1) > 0, "30000 > 13450");

        l2 = new AutoLoan(20000, 5000, 6.5, 5);
        assertTrue(l2.compareTo(l1) > 0, "20000 > 13450");
    }

    @Test
    void compareToLessThan() {
        l2 = new AutoLoan(10000, 5000, 6.5, 5);
        assertTrue(l2.compareTo(l1) < 0, "10000 < 13450");

        l2 = new AutoLoan(10000, 5000, 6.5, 5);
        assertTrue(l2.compareTo(l1) < 0, "10000 < 13450");
    }

    @Test
    void compareToEquals() {
        l2 = new AutoLoan();
        assertEquals(0, l1.compareTo(l2), "13450 == 13450");

        l2 = new AutoLoan(13450, 5000, 6.5, 5);
        assertEquals(0, l1.compareTo(l2), "13450 == 13450");
    }

    @Test
    void equals() {
        AutoLoan l2 = new AutoLoan();
        assertTrue(l1.equals(l2), "13450 == 13450");
    }

    @Test
    void notEquals() {
        AutoLoan l2 = new AutoLoan(25000, 15000, 5, 6);
        assertTrue(!l1.equals(l2));
    }

    // Sourced ChatGPT For help
    @Test
    void toStringTest() {
        // Act
        String result = l1.toString();

        // Assert
        assertTrue(result.contains("Auto Loan information"));
        assertTrue(result.contains("Car Price"));
        assertTrue(result.contains("Down Payment"));
        assertTrue(result.contains("Interest Rate"));
        assertTrue(result.contains("Loan Term"));
        assertTrue(result.contains("Loan Amount"));
        assertTrue(result.contains("Monthly Payment"));
        assertTrue(result.contains("Total Interest"));
        assertTrue(result.contains("Total Cost"));
    }

    @Test
    void totalCostIncludesDownPayment() {
        //Arrange
        double totalCost = l1.calculateTotalCost();
        double totalInterest = l1.calculateTotalInterest();
        double loanAmount = l1.calculateLoanAmount();

        // Act
        double expected = l1.getDownPayment() +  loanAmount + totalInterest;

        // Assert
        assertEquals(expected, totalCost);
    }

    @Test
    void loanAmountMatchesPriceAndDownPayment() {
        //Arrange
        double loanAmount = l1.calculateLoanAmount();

        // Act
        double expected = l1.getCarPrice() - l1.getDownPayment();

        //Assert
        assertEquals(expected, loanAmount);
    }

    @Test
    void largerDownPaymentReducesLoanAmountAndTotalCost() {
        //Arrange
        AutoLoan smallDownPayment = new AutoLoan(30000, 2000, 6.5, 5);
        AutoLoan largeDownPayment = new AutoLoan(30000, 8000, 6.5, 5);

        // Act
        double smallDownLoan = smallDownPayment.calculateLoanAmount();
        double largeDownLoan = largeDownPayment.calculateLoanAmount();

        double smallDownCost = smallDownPayment.calculateTotalCost();
        double largeDownCost = largeDownPayment.calculateTotalCost();

        // Assert
        assertTrue(largeDownLoan < smallDownLoan);
        assertTrue(largeDownCost < smallDownCost);
    }

    @Test
    void higherInterestRateIncreaseMonthlyPayment() {
        // Arrange
        AutoLoan lowRate = new AutoLoan(30000, 5000, 5, 5);
        AutoLoan highRate = new AutoLoan(30000, 5000, 10, 5);

        // Act
        double lowPayment = lowRate.calculateMonthlyPayment();
        double highPayment = highRate.calculateMonthlyPayment();

        // Assert
        assertTrue(highPayment > lowPayment);
    }

    @Test
    void longerLoanTermLowersMonthlyPayment() {
        // Arrange
        AutoLoan shortTerm = new AutoLoan(30000, 5000, 6.5, 3);
        AutoLoan longTerm = new AutoLoan(30000, 5000, 6.5, 6);

        // Act
        double shortPayment = shortTerm.calculateMonthlyPayment();
        double longPayment = longTerm.calculateMonthlyPayment();

        // Assert
        assertTrue(longPayment < shortPayment);
    }

    @Test
    void longerLoanTermIncreasesTotalInterest() {
        // Arrange
        AutoLoan shortTerm = new AutoLoan(30000, 5000, 6.5, 3);
        AutoLoan longTerm = new AutoLoan(30000, 5000, 6.5, 6);

        // Act
        double shortInterest = shortTerm.calculateTotalInterest();
        double longInterest = longTerm.calculateTotalInterest();

        // Assert
        assertTrue(longInterest > shortInterest);
    }
}