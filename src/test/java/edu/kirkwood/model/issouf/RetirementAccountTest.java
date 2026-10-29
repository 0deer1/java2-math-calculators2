package edu.kirkwood.model.issouf;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class RetirementAccountTest {

    private RetirementAccount account;
    private RetirementAccount emptyAccount;

    @BeforeEach
    void setUp() {
        account = new RetirementAccount(20000.0, 50000.0, 6.0, 50.0, 6.0);
        emptyAccount = new RetirementAccount();
    }

    @Test
    void testToString() {
        assertEquals("RetirementAccount", account.toString());
    }

    @Test
    @DisplayName("Test the parameterized constructor stores the given values")
    void parameterizedConstructor() {
        // Act and Assert
        assertEquals(20000.0, account.getCurrentBalance(), 0.01);
        assertEquals(50000.0, account.getAnnualSalary(), 0.01);
        assertEquals(6.0, account.getContributionRate(), 0.01);
        assertEquals(50.0, account.getEmployerMatchRate(), 0.01);
        assertEquals(6.0, account.getEmployerMatchLimit(), 0.01);
    }

    @Test
    void getCurrentBalance() {
        assertEquals(20000.0, account.getCurrentBalance(), 0.01);
        assertEquals(0.0, emptyAccount.getCurrentBalance(), 0.01);
    }

    @Test
    void setCurrentBalancePositive() {
        // Act
        account.setCurrentBalance(30000.0);
        // Assert
        assertEquals(30000.0, account.getCurrentBalance(), 0.01);
    }

    @Test
    void setCurrentBalanceZero() {
        // Act
        account.setCurrentBalance(0.0);
        // Assert
        assertEquals(0.0, account.getCurrentBalance(), 0.01);
    }

    @Test
    void setCurrentBalanceNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> account.setCurrentBalance(-1.0));
    }

    @Test
    void getAnnualSalary() {
        assertEquals(50000.0, account.getAnnualSalary(), 0.01);
    }

    @Test
    void setAnnualSalaryPositive() {
        // Act
        account.setAnnualSalary(60000.0);
        // Assert
        assertEquals(60000.0, account.getAnnualSalary(), 0.01);
    }

    @Test
    void setAnnualSalaryZero() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> account.setAnnualSalary(0.0));
    }

    @Test
    void setAnnualSalaryNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> account.setAnnualSalary(-50000.0));
    }

    @Test
    void getContributionRate() {
        assertEquals(6.0, account.getContributionRate(), 0.01);
    }

    @Test
    void setContributionRatePositive() {
        // Act
        account.setContributionRate(10.0);
        // Assert
        assertEquals(10.0, account.getContributionRate(), 0.01);
    }

    @Test
    void setContributionRateZero() {
        // Act
        account.setContributionRate(0.0);
        // Assert
        assertEquals(0.0, account.getContributionRate(), 0.01);
    }

    @Test
    void setContributionRateNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> account.setContributionRate(-6.0));
    }

    @Test
    void getEmployerMatchRate() {
        assertEquals(50.0, account.getEmployerMatchRate(), 0.01);
    }

    @Test
    void setEmployerMatchRatePositive() {
        // Act
        account.setEmployerMatchRate(100.0);
        // Assert
        assertEquals(100.0, account.getEmployerMatchRate(), 0.01);
    }

    @Test
    void setEmployerMatchRateZero() {
        // Act
        account.setEmployerMatchRate(0.0);
        // Assert
        assertEquals(0.0, account.getEmployerMatchRate(), 0.01);
    }

    @Test
    void setEmployerMatchRateNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> account.setEmployerMatchRate(-50.0));
    }

    @Test
    void getEmployerMatchLimit() {
        assertEquals(6.0, account.getEmployerMatchLimit(), 0.01);
    }

    @Test
    void setEmployerMatchLimitPositive() {
        // Act
        account.setEmployerMatchLimit(10.0);
        // Assert
        assertEquals(10.0, account.getEmployerMatchLimit(), 0.01);
    }

    @Test
    void setEmployerMatchLimitZero() {
        // Act
        account.setEmployerMatchLimit(0.0);
        // Assert
        assertEquals(0.0, account.getEmployerMatchLimit(), 0.01);
    }

    @Test
    void setEmployerMatchLimitNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> account.setEmployerMatchLimit(-6.0));
    }

    @Test
    @DisplayName("Test the employee contribution ($50,000 at 6% = $3,000)")
    void calculateEmployeeContribution() {
        // Act
        double result = account.calculateEmployeeContribution();
        // Assert
        assertEquals(3000.0, result, 0.01);
    }

    @Test
    void calculateEmployeeContributionZeroRate() {
        // Arrange
        account.setContributionRate(0.0);
        // Act
        double result = account.calculateEmployeeContribution();
        // Assert
        assertEquals(0.0, result, 0.01);
    }

    @Test
    @DisplayName("Test the employer contribution ($3,000 [50000 * 6%] at 50% = $1,500)")
    void calculateEmployerContribution() {
        // Act
        double result = account.calculateEmployerContribution();
        // Assert
        assertEquals(1500.0, result, 0.01);
    }

    @Test
    @DisplayName("Test the employer contribution is capped at the match limit")
    void calculateEmployerContributionAboveLimit() {
        // Arrange
        account.setContributionRate(10.0);
        // Act
        double result = account.calculateEmployerContribution();
        // Assert
        assertEquals(1500.0, result, 0.01);
    }

    @Test
    void calculateEmployerContributionBelowLimit() {
        // Arrange
        account.setContributionRate(3.0);
        // Act
        double result = account.calculateEmployerContribution();
        // Assert
        assertEquals(750.0, result, 0.01);
    }

    @Test
    void calculateEmployerContributionZeroMatch() {
        // Arrange
        account.setEmployerMatchRate(0.0);
        // Act
        double result = account.calculateEmployerContribution();
        // Assert
        assertEquals(0.0, result, 0.01);
    }

    @Test
    @DisplayName("Test the annual growth ($20,000 at 7% = $1,400)")
    void calculateAnnualGrowth() {
        // Act
        double result = account.calculateAnnualGrowth(7.0);
        // Assert
        assertEquals(1400.0, result, 0.01);
    }

    @Test
    void calculateAnnualGrowthZeroReturn() {
        // Act
        double result = account.calculateAnnualGrowth(0.0);
        // Assert
        assertEquals(0.0, result, 0.01);
    }

    @Test
    void calculateAnnualGrowthZeroBalance() {
        // Act and Assert
        assertEquals(0.0, emptyAccount.calculateAnnualGrowth(7.0), 0.01);
    }

    @Test
    void updateAnnualBalance() {
        // Act
        double result = account.updateAnnualBalance(7.0);
        // Assert
        assertEquals(26215.0, result, 0.01);
    }

    @Test
    void updateAnnualBalanceZeroReturn() {
        // Act
        double result = account.updateAnnualBalance(0.0);
        // Assert
        assertEquals(24500.0, result, 0.01);
    }

    @Test
    void updateAnnualBalanceTwice() {
        // Act
        account.updateAnnualBalance(7.0);
        double result = account.updateAnnualBalance(7.0);
        // Assert
        assertEquals(32865.05, result, 0.01);
    }
}
