package edu.kirkwood.model.issouf;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class RetirementProjectionTest {

    private RetirementProjection projection;
    private RetirementProjection emptyProjection;
    private RetirementAccount account;

    @BeforeEach
    void setUp() {
        projection = new RetirementProjection(30, 65, 3.0, 7.0);
        emptyProjection = new RetirementProjection();
        account = new RetirementAccount(20000.0, 50000.0, 6.0, 50.0, 6.0);
    }

    @Test
    @DisplayName("Test the parameterized constructor stores the given values")
    void parameterizedConstructor() {
        // Act and Assert
        assertEquals(30, projection.getCurrentAge());
        assertEquals(65, projection.getRetirementAge());
        assertEquals(3.0, projection.getSalaryIncreaseRate(), 0.01);
        assertEquals(7.0, projection.getAnnualReturnRate(), 0.01);
    }

    @Test
    void getCurrentAge() {
        assertEquals(30, projection.getCurrentAge());
    }

    @Test
    void setCurrentAgePositive() {
        // Act
        projection.setCurrentAge(40);
        // Assert
        assertEquals(40, projection.getCurrentAge());
    }

    @Test
    void setCurrentAgeZero() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> projection.setCurrentAge(0));
    }

    @Test
    void setCurrentAgeNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> projection.setCurrentAge(-1));
    }

    @Test
    void getRetirementAge() {
        assertEquals(65, projection.getRetirementAge());
    }

    @Test
    void setRetirementAgePositive() {
        // Act
        projection.setRetirementAge(67);
        // Assert
        assertEquals(67, projection.getRetirementAge());
    }

    @Test
    void setRetirementAgeEqual() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> projection.setRetirementAge(30));
    }

    @Test
    void setRetirementAgeLessThanCurrentAge() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> projection.setRetirementAge(25));
    }

    @Test
    void getSalaryIncreaseRate() {
        assertEquals(3.0, projection.getSalaryIncreaseRate(), 0.01);
    }

    @Test
    void setSalaryIncreaseRatePositive() {
        // Act
        projection.setSalaryIncreaseRate(5.0);
        // Assert
        assertEquals(5.0, projection.getSalaryIncreaseRate(), 0.01);
    }

    @Test
    void setSalaryIncreaseRateZero() {
        // Act
        projection.setSalaryIncreaseRate(0.0);
        // Assert
        assertEquals(0.0, projection.getSalaryIncreaseRate(), 0.01);
    }

    @Test
    void setSalaryIncreaseRateNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> projection.setSalaryIncreaseRate(-3.0));
    }

    @Test
    void getAnnualReturnRate() {
        assertEquals(7.0, projection.getAnnualReturnRate(), 0.01);
    }

    @Test
    void setAnnualReturnRatePositive() {
        // Act
        projection.setAnnualReturnRate(10.0);
        // Assert
        assertEquals(10.0, projection.getAnnualReturnRate(), 0.01);
    }

    @Test
    void setAnnualReturnRateZero() {
        // Act
        projection.setAnnualReturnRate(0.0);
        // Assert
        assertEquals(0.0, projection.getAnnualReturnRate(), 0.01);
    }

    @Test
    void setAnnualReturnRateNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> projection.setAnnualReturnRate(-7.0));
    }

    @Test
    @DisplayName("Test years until retirement (65 - 30 = 35)")
    void calculateYearsUntilRetirement() {
        // Act
        int result = projection.calculateYearsUntilRetirement();
        // Assert
        assertEquals(35, result);
    }

    @Test
    void calculateYearsUntilRetirementOneYear() {
        // Arrange
        RetirementProjection oneYear = new RetirementProjection(64, 65, 3.0, 7.0);
        // Act
        int result = oneYear.calculateYearsUntilRetirement();
        // Assert
        assertEquals(1, result);
    }

    @Test
    void projectFutureSalaryYearZero() {
        // Act
        double result = projection.projectFutureSalary(50000.0, 0);
        // Assert
        assertEquals(50000.0, result, 0.01);
    }

    @Test
    @DisplayName("Test one salary increase ($50,000 at 3% = $51,500)")
    void projectFutureSalaryOneYear() {
        // Act
        double result = projection.projectFutureSalary(50000.0, 1);
        // Assert
        assertEquals(51500.0, result, 0.01);
    }

    @Test
    void projectFutureSalaryCompounds() {
        // Act
        double result = projection.projectFutureSalary(50000.0, 5);
        // Assert
        assertEquals(57963.70, result, 0.01);
    }

    @Test
    void projectFutureSalaryZeroRate() {
        // Arrange
        RetirementProjection flat = new RetirementProjection(30, 65, 0.0, 7.0);
        // Act
        double result = flat.projectFutureSalary(50000.0, 10);
        // Assert
        assertEquals(50000.0, result, 0.01);
    }

    @Test
    void projectRetirementBalanceGrows() {
        // Act
        double result = projection.projectRetirementBalance(account);
        // Assert
        assertTrue(result > 20000.0);
    }

    @Test
    void projectRetirementBalanceNullAccount() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> projection.projectRetirementBalance(null));
    }

    @Test
    void projectRetirementBalanceNoGrowth() {
        // Arrange
        RetirementAccount inert = new RetirementAccount(20000.0, 50000.0, 0.0, 0.0, 0.0);
        // Act
        double result = projection.projectRetirementBalance(inert);
        // Assert
        assertEquals(20000.0, result, 0.01);
    }

    @Test
    void projectRetirementBalanceLongerHorizon() {
        // Arrange
        RetirementProjection shortRun = new RetirementProjection(64, 65, 3.0, 7.0);
        // Act
        double shortBalance = shortRun.projectRetirementBalance(
                new RetirementAccount(20000.0, 50000.0, 6.0, 50.0, 6.0));
        double longBalance = projection.projectRetirementBalance(account);
        // Assert
        assertTrue(longBalance > shortBalance);
    }

    @Test
    @DisplayName("Test the projection summary is not empty")
    void generateProjectionSummary() {
        // Act
        String summary = projection.generateProjectionSummary();
        // Assert
        assertNotNull(summary);
        assertFalse(summary.isEmpty());
    }

    @Test
    void generateProjectionSummaryHasCurrentAge() {
        // Act
        String summary = projection.generateProjectionSummary();
        // Assert
        assertTrue(summary.contains("30"));
    }

    @Test
    void generateProjectionSummaryHasRetirementAge() {
        // Act
        String summary = projection.generateProjectionSummary();
        // Assert
        assertTrue(summary.contains("65"));
    }

    @Test
    void displayProjection() {
        // Act and Assert
        projection.displayProjection();
    }

    @Test
    void compareToEqual() {
        // Arrange
        RetirementProjection other = new RetirementProjection(30, 65, 3.0, 7.0);
        // Assert
        assertEquals(0, projection.compareTo(other), "30 == 30");
    }

    @Test
    void compareToGreaterThan() {
        // Arrange
        RetirementProjection older = new RetirementProjection(45, 65, 3.0, 7.0);
        // Assert
        assertTrue(older.compareTo(projection) > 0, "45 > 30");
    }

    @Test
    void compareToLessThan() {
        // Arrange
        RetirementProjection younger = new RetirementProjection(25, 65, 3.0, 7.0);
        // Assert
        assertTrue(younger.compareTo(projection) < 0, "25 < 30");
    }

    @Test
    void compareToSortsArrayListFromSmallestToLargest() {
        // Arrange
        ArrayList<RetirementProjection> projections = new ArrayList<>();
        projections.add(new RetirementProjection(45, 65, 3.0, 7.0));
        projections.add(new RetirementProjection(25, 65, 3.0, 7.0));
        projections.add(new RetirementProjection(35, 65, 3.0, 7.0));
        projections.add(new RetirementProjection(30, 65, 3.0, 7.0));

        // Act
        Collections.sort(projections);

        // Assert
        assertEquals(25, projections.get(0).getCurrentAge());
        assertEquals(30, projections.get(1).getCurrentAge());
        assertEquals(35, projections.get(2).getCurrentAge());
        assertEquals(45, projections.get(3).getCurrentAge());
    }

    @Test
    void equals() {
        // Arrange
        RetirementProjection other = new RetirementProjection(30, 65, 3.0, 7.0);
        // Assert
        assertTrue(projection.equals(other), "30/65/3/7 == 30/65/3/7");
    }

    @Test
    void equalsDifferentAge() {
        // Arrange
        RetirementProjection other = new RetirementProjection(40, 65, 3.0, 7.0);
        // Assert
        assertTrue(!projection.equals(other), "30 != 40");
    }

    @Test
    void equalsDifferentRates() {
        // Arrange
        RetirementProjection other = new RetirementProjection(30, 65, 5.0, 7.0);
        // Assert
        assertTrue(!projection.equals(other), "3% != 5%");
    }

    @Test
    void notEqualsNull() {
        assertTrue(!projection.equals(null), "projection != null");
    }

    @Test
    void hashCodeEqual() {
        // Arrange
        RetirementProjection other = new RetirementProjection(30, 65, 3.0, 7.0);
        // Assert
        assertEquals(projection.hashCode(), other.hashCode());
    }
}
