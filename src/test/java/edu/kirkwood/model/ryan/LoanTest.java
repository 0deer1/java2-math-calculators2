package edu.kirkwood.model.ryan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    private Loan loan;
    private Loan secondLoan;

    @BeforeEach
    void setUp() {
        loan = new Loan(10000, 5, 3);
        secondLoan = new Loan(20000, 10, 5);
    }

    @Test
    void getPrincipal() {
        assertEquals(10000, loan.getPrincipal());
        assertEquals(20000, secondLoan.getPrincipal());
    }

    @Test
    void getAnnualRate() {
        assertEquals(5, loan.getAnnualRate());
        assertEquals(10, secondLoan.getAnnualRate());
    }

    @Test
    void getTermYears() {
        assertEquals(3, loan.getTermYears());
        assertEquals(5, secondLoan.getTermYears());
    }

    @Test
    void setAnnualRate() {
        loan.setAnnualRate(6);
        assertEquals(6, loan.getAnnualRate());
    }

    @Test
    void setAnnualRateNegative() {
        assertThrows(IllegalArgumentException.class, () -> loan.setAnnualRate(-5));
    }

    @Test
    void setAnnualRateZero() {
        assertDoesNotThrow(() -> loan.setAnnualRate(0));
    }

    @Test
    void setPrincipal() {
        loan.setPrincipal(15000);
        assertEquals(15000, loan.getPrincipal());
    }

    @Test
    void setPrincipalNegative() {
        assertThrows(IllegalArgumentException.class, () -> loan.setPrincipal(-15000));
    }

    @Test
    void setPrincipalZero() {
        assertThrows(IllegalArgumentException.class, () -> loan.setPrincipal(0));
    }

    @Test
    void setBudgetPaymentZero() {
        //assert
        assertThrows(IllegalArgumentException.class, () -> loan.setBudgetedPayment(0));
    }

    @Test
    void setBudgetPaymentNegative() {
        //assert
        assertThrows(IllegalArgumentException.class, () -> loan.setBudgetedPayment(-100));
    }

    @Test
    void setBudgetPaymentGoodValue() {
        // arrange
        loan.setBudgetedPayment(500);
        // act + assert
        assertEquals(500, loan.getBudgetedPayment());
    }

    @Test
    void setDownPaymentNegative() {
        //assert
        assertThrows(IllegalArgumentException.class, () -> loan.setDownPayment(-1000));
    }

    @Test
    void setDownPaymentZero() {
        //arrange
        loan.setDownPayment(0);
        //act + assert
        assertEquals(0, loan.getDownPayment());
    }

    @Test
    void setDownPaymentGoodValue() {
        //arrange
        loan.setDownPayment(2000);
        //act + assert
        assertEquals(2000, loan.getDownPayment());
    }


    @Test
    void setTermYears() {
        loan.setTermYears(5);
        assertEquals(5, loan.getTermYears());
    }

    @Test
    void setTermYearsNegative() {
        assertThrows(IllegalArgumentException.class, () -> loan.setTermYears(-5));
    }

    @Test
    void setTermYearsZero() {
        assertThrows(IllegalArgumentException.class, () -> loan.setTermYears(0));
    }

    @Test
    void principalIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Loan(-10000, 5, 3));
    }

    @Test
    void annualRateIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Loan(10000, -5, 3));
    }

    @Test
    void termYearsIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Loan(10000, 5, -3));
    }

    @Test
    void principalIsZero() {
        assertThrows(IllegalArgumentException.class, () -> new Loan(0, 5, 3));
    }

    @Test
    void termYearsIsZero() {
        assertThrows(IllegalArgumentException.class, () -> new Loan(10000, 5, 0));
    }

    @Test
    void calculateMonthlyPayment() {
        assertEquals(299.71, loan.calculateMonthlyPayment(), 0.01);
    }

    @Test
    void calculateMonthlyPaymentForLargerLoan() {
        Loan bigLoan = new Loan(250000, 5.5, 30);
        assertEquals(1419.47, bigLoan.calculateMonthlyPayment(), 0.01);
    }

    @Test
    void calculateMonthlyPaymentWhenRateIsZero() {
        loan.setAnnualRate(0);
        assertEquals(10000.0 / 36, loan.calculateMonthlyPayment(), 0.01);
    }

    @Test
    void calculateTotalPayment() {
        assertEquals(10789.56, loan.calculateTotalPayment(), 0.01);
    }

    @Test
    void calculateTotalPaymentWhenRateIsZero() {
        loan.setAnnualRate(0);
        assertEquals(10000.0, loan.calculateTotalPayment(), 0.01);
    }

    @Test
    void calculateTotalInterest() {
        assertEquals(789.56, loan.calculateTotalInterest(), 0.01);
    }

    @Test
    void calculateTotalInterestWhenRateIsZero() {
        loan.setAnnualRate(0);
        assertEquals(0.0, loan.calculateTotalInterest(), 0.01);
    }

    @Test
    void equals() {
        Loan anotherLoan = new Loan(10000, 5, 3);
        assertEquals(loan, anotherLoan);
    }

    @Test
    void notEquals() {
        Loan anotherLoan = new Loan(15000, 5, 3);
        assertNotEquals(loan, anotherLoan);
    }

    @Test
    void compareToEqual() {
        Loan anotherLoan = new Loan(10000, 5, 3);
        assertEquals(0, loan.compareTo(anotherLoan));
    }

    @Test
    void compareToLargeLoan() {
        Loan largeLoan = new Loan(2000000, 10, 10);
        assertTrue(loan.compareTo(largeLoan) < 0);
    }

    @Test
    void compareToSmallLoan() {
        Loan smallLoan = new Loan(100, 1, 1);
        assertTrue(loan.compareTo(smallLoan) > 0);
    }

    @Test
    void amortizationScheduleLoanSize() {
        assertEquals(36, loan.amortizationSchedule().size());
    }

    @Test
    void amortizationScheduleRemainingBalanceZero() {
        List<Loan.PaymentRecord> schedule = loan.amortizationSchedule();
        Loan.PaymentRecord lastRecord = schedule.getLast();
        assertEquals(0.0, lastRecord.getRemainingBalance(), 0.01);
    }

    @Test
    void amortizationScheduleFirstPaymentNumber() {
        List<Loan.PaymentRecord> schedule = loan.amortizationSchedule();
        Loan.PaymentRecord firstRecord = schedule.getFirst();
        assertEquals(1, firstRecord.getPaymentNumber());
    }

    @Test
    void calculateAffordableLoan() {
        loan.setBudgetedPayment(299.71);
        loan.setAnnualRate(5);
        loan.setTermYears(3);
        assertEquals(10000, loan.calculateAffordableLoan(), 0.1);
    }

    @Test
    void calculateAffordableLoanZeroRate() {
        loan.setBudgetedPayment(299.71);
        loan.setAnnualRate(0);
        loan.setTermYears(3);
        assertEquals(10789.56, loan.calculateAffordableLoan(), 0.1);
    }
}