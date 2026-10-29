package edu.kirkwood.model.makayla;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class RespiratoryRateTest {

    RespiratoryRate respiratoryRate;

    @BeforeEach
    void setUp() {
        // Arrange
        respiratoryRate = new RespiratoryRate(12, 60);
    }

    @Test
    @DisplayName("Test getting the observation time")
    void getObservationTime() {
        // Arrange
        RespiratoryRate first = new RespiratoryRate(12, 60);
        RespiratoryRate second = new RespiratoryRate(24, 120);
        // Act
        int firstResult = first.getObservationTime();
        int secondResult = second.getObservationTime();
        // Assert
        assertEquals(60, firstResult);
        assertEquals(120, secondResult);
    }

    @Test
    @DisplayName("Test getting the number of breaths")
    void getBreaths() {
        // Arrange
        RespiratoryRate first = new RespiratoryRate(12, 60);
        RespiratoryRate second = new RespiratoryRate(24, 120);
        // Act
        int firstResult = first.getBreaths();
        int secondResult = second.getBreaths();
        // Assert
        assertEquals(12, firstResult);
        assertEquals(24, secondResult);
    }


    @Test
    @DisplayName("Test getting the respiratory rate")
    void getRespiratoryRate() {
        // Arrange
        RespiratoryRate first = new RespiratoryRate(12, 60);
        RespiratoryRate second = new RespiratoryRate(24, 120);
        // Act
        first.calculateRate();
        second.calculateRate();

        double firstResult = first.getRespiratoryRate();
        double secondResult = second.getRespiratoryRate();
        // Assert
        assertEquals(12, firstResult);
        assertEquals(12, secondResult);
    }

    @Test
    @DisplayName("Test calculating the respiratory rate")
    void calculateRate() {
        //Act
        double result = respiratoryRate.calculateRate();
        //Assert
        assertEquals(12, result);
    }

    @Test
    @DisplayName("Test with 20 breaths/60 seconds")
    void calculateRateMoreBreaths() {
        //Arrange
        respiratoryRate = new RespiratoryRate(20, 60);

        // Act
        double result = respiratoryRate.calculateRate();

        // Assert
        assertEquals(20, result);
    }

    @Test
    @DisplayName("Test with 12 breaths/30 seconds")
    void calculateRateDifferentTime() {
        // Arrange
        respiratoryRate = new RespiratoryRate(12, 30);

        // Act
        double result = respiratoryRate.calculateRate();

        // Assert
        assertEquals(24, result);
    }

    @Test
    @DisplayName("Test with 0 seconds")
    void zeroSeconds() {
        // Arrange
        int breaths = 12;
        int observationTime = 0;
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new RespiratoryRate(breaths, observationTime);
        });
        // Assert
        assertEquals(RespiratoryRate.INVALID_OBSERVATION_TIME_MESSAGE, exception.getMessage());

    }

    @Test
    @DisplayName("Test with 0 breaths")
    void zeroBreaths() {
        // Arrange
        int breaths = 0;
        int observationTime = 60;
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new RespiratoryRate(breaths, observationTime);
        });
        // Assert
        assertEquals(RespiratoryRate.INVALID_BREATHS_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Test breaths with negative value")
    void negativeBreaths() {
        // Arrange
        int breaths = -1;
        int observationTime = 60;
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new RespiratoryRate(breaths, observationTime);
        });
        // Assert
        assertEquals(RespiratoryRate.INVALID_BREATHS_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Test observation time with negative value")
    void negativeObservationTime() {
        // Arrange
        int breaths = 12;
        int observationTime = -1;
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new RespiratoryRate(breaths, observationTime);
        });
        // Assert
        assertEquals(RespiratoryRate.INVALID_OBSERVATION_TIME_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Test with a large number of breaths")
    void largeNumberOfBreaths() {
        // Arrange
        respiratoryRate = new RespiratoryRate(1000, 60);
        // Act
        double result = respiratoryRate.calculateRate();
        // Assert
        assertEquals(1000, result, 0.0001);
    }

    @Test
    @DisplayName("Test with a high respiratory rate")
    void highRate() {
        // Arrange
        respiratoryRate = new RespiratoryRate(100, 1);
        // Act
        double result = respiratoryRate.calculateRate();
        // Assert
        assertEquals(6000, result);
    }

    @Test
    @DisplayName("Test with a large observation time")
    void largeObservationTime() {
        // Arrange
        respiratoryRate = new RespiratoryRate(60, 1000);
        // Act
        double result = respiratoryRate.calculateRate();
        // Assert
        assertEquals(3.6, result, 0.0001);
    }

    @Test
    @DisplayName("Test with large breaths and observation time")
    void largeInputs() {
        // Arrange
        respiratoryRate = new RespiratoryRate(1000, 1000);
        // Act
        double result = respiratoryRate.calculateRate();
        // Assert
        assertEquals(60, result);
    }

    @Test
    @DisplayName("Test with 1 second")
    void oneSecond() {
        // Arrange
        respiratoryRate = new RespiratoryRate(12, 1);
        // Act
        double result = respiratoryRate.calculateRate();
        // Assert
        assertEquals(720, result);
    }

    @Test
    @DisplayName("Test with a decimal respiratory rate")
    void decimalRate() {
        // Arrange
        respiratoryRate = new RespiratoryRate(10, 45);
        // Act
        double result = respiratoryRate.calculateRate();
        // Assert
        assertEquals(13.333333333333334, result, 0.0001);
    }

    @Test
    @DisplayName("Test different inputs with the same respiratory rate")
    void sameRateDifferentInputs() {
        // Arrange
        RespiratoryRate first = new RespiratoryRate(12, 60);
        RespiratoryRate second = new RespiratoryRate(24, 120);
        // Act
        double firstResult = first.calculateRate();
        double secondResult = second.calculateRate();
        // Assert
        assertEquals(firstResult, secondResult);
    }

    @Test
    @DisplayName("Test with a respiratory rate below 1")
    void rateBelowOne() {
        // Arrange
        respiratoryRate = new RespiratoryRate(1, 120);
        // Act
        double result = respiratoryRate.calculateRate();
        // Assert
        assertEquals(0.5, result, 0.0001);
    }

    @Test
    @DisplayName("Tests if both inputs are 1")
    void oneAndOne() {
        //Arrange
        int breaths = 1;
        int observationTime = 1;
        respiratoryRate = new RespiratoryRate(breaths, observationTime);
        // Act
        double result = respiratoryRate.calculateRate();
        //Assert
        assertEquals(60, result);
    }

    @Test
    @DisplayName("Test calculating the rate more than once")
    void calculateRateTwice() {
        // Arrange
        respiratoryRate = new RespiratoryRate(12, 60);
        // Act
        double firstResult = respiratoryRate.calculateRate();
        double secondResult = respiratoryRate.calculateRate();
        // Assert
        assertEquals(firstResult, secondResult);
    }

    @Test
    @DisplayName("Test setting a positive number of breaths")
    void setBreathsPositive() {
        // Arrange
        RespiratoryRate rate = new RespiratoryRate(10, 30);
        // Act
        rate.setBreaths(20);
        // Assert
        assertEquals(20, rate.getBreaths());
    }

    @Test
    @DisplayName("Test setting zero breaths")
    void setBreathsZero() {
        // Arrange
        RespiratoryRate rate = new RespiratoryRate(10, 30);
        // Act
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> rate.setBreaths(0)
        );
        // Assert
        assertEquals(RespiratoryRate.INVALID_BREATHS_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Test setting a negative number of breaths")
    void setBreathsNegative() {
        // Arrange
        RespiratoryRate rate = new RespiratoryRate(10, 30);
        // Act
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> rate.setBreaths(-5)
        );
        // Assert
        assertEquals(RespiratoryRate.INVALID_BREATHS_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Test setting a positive observation time")
    void setObservationTimePositive() {
        // Arrange
        RespiratoryRate rate = new RespiratoryRate(10, 30);
        // Act
        rate.setObservationTime(60);
        // Assert
        assertEquals(60, rate.getObservationTime());
    }

    @Test
    void setObservationTimeZero() {
        // Arrange
        RespiratoryRate rate = new RespiratoryRate(10, 30);
        // Act
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> rate.setObservationTime(0)
        );
        // Assert
        assertEquals(RespiratoryRate.INVALID_OBSERVATION_TIME_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Test setting a negative observation time")
    void setObservationTimeNegative() {
        // Arrange
        RespiratoryRate rate = new RespiratoryRate(10, 30);
        // Act
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> rate.setObservationTime(-10)
        );
        // Assert
        assertEquals(RespiratoryRate.INVALID_OBSERVATION_TIME_MESSAGE, exception.getMessage());
    }

}
