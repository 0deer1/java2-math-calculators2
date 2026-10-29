package edu.kirkwood.model.makayla;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RespiratoryHistoryTest {

    private RespiratoryHistory history;
    @BeforeEach
    void setUp() {
        history = new RespiratoryHistory();
    }

    private RespiratoryRate createRate(int breaths) {
        return new RespiratoryRate(breaths, 60);
    }

    @Test
    @DisplayName("Test recording a measurement")
    void recordMeasurement() {
        // Arrange
        RespiratoryMeasurement measurement =
                new RespiratoryMeasurement(createRate(12), Instant.now());
        // Act
        history.recordMeasurement(measurement);
        // Assert
        assertEquals(1, history.getMeasurementCount());
    }

    @Test
    @DisplayName("Test calculating the average respiratory rate")
    void calculateAverage() {
        // Arrange
        RespiratoryMeasurement first =
                new RespiratoryMeasurement(createRate(12), Instant.now());
        RespiratoryMeasurement second =
                new RespiratoryMeasurement(createRate(20), Instant.now());
        // Act
        history.recordMeasurement(first);
        history.recordMeasurement(second);
        double result = history.calculateAverage();
        // Assert
        assertEquals(16.0, result, .0001);
    }



    @Test
    @DisplayName("Test getting the highest respiratory rate")
    void getHighestRate() {
        // Arrange
        RespiratoryMeasurement first =
                new RespiratoryMeasurement(createRate(12), Instant.now());
        RespiratoryMeasurement second =
                new RespiratoryMeasurement(createRate(20), Instant.now());
        RespiratoryMeasurement third =
                new RespiratoryMeasurement(createRate(16), Instant.now());
        // Act
        history.recordMeasurement(first);
        history.recordMeasurement(second);
        history.recordMeasurement(third);
        double result = history.getHighestRate();
        // Assert
        assertEquals(20.0, result, .0001);
    }


    @Test
    @DisplayName("Test getting the lowest respiratory rate")
    void getLowestRate() {
        // Arrange
        RespiratoryMeasurement first =
                new RespiratoryMeasurement(createRate(12), Instant.now());
        RespiratoryMeasurement second =
                new RespiratoryMeasurement(createRate(20), Instant.now());
        RespiratoryMeasurement third =
                new RespiratoryMeasurement(createRate(16), Instant.now());
        // Act
        history.recordMeasurement(first);
        history.recordMeasurement(second);
        history.recordMeasurement(third);
        double result = history.getLowestRate();
        // Assert
        assertEquals(12.0, result, .0001);
    }
    @Test
    @DisplayName("Test getting the measurement count")
    void getMeasurementCount() {
        // Arrange
        RespiratoryMeasurement first =
                new RespiratoryMeasurement(createRate(12), Instant.now());
        RespiratoryMeasurement second =
                new RespiratoryMeasurement(createRate(20), Instant.now());
        // Act
        history.recordMeasurement(first);
        history.recordMeasurement(second);
        int result = history.getMeasurementCount();
        // Assert
        assertEquals(2, result);
    }

    @Test
    void getMeasurementsReturnsCopy() {
        // arrange
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(12, 60), Instant.now()
        ));
        // act
        var measurements = history.getMeasurements();
        measurements.clear();
        // assert
        assertEquals(1, history.getMeasurementCount());
    }

    @Test
    @DisplayName("Average throws exception for empty history")
    void calculateAverageThrowsExceptionForEmptyHistory() {
        // Arrange
        String expectedMessage = RespiratoryHistory.EMPTY_HISTORY_MESSAGE;
        // Act
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> history.calculateAverage()
        );
        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Highest rate throw exception for empty history")
    void getHighestRateThrowsExceptionForEmptyHistory() {
        // Arrange
        String expectedMessage = RespiratoryHistory.EMPTY_HISTORY_MESSAGE;
        // Act
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> history.getHighestRate()
        );
        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }
    @Test
    @DisplayName("Lowest rate throws exception for empty history")
    void getLowestRateThrowsExceptionForEmptyHistory() {
        // Arrange
        String expectedMessage = RespiratoryHistory.EMPTY_HISTORY_MESSAGE;
        // Act
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> history.getLowestRate()
        );
        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Test recording a null measurement")
    void recordNullMeasurement() {
        // Arrange
        int initialCount = history.getMeasurementCount();
        // Act
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> history.recordMeasurement(null)
        );
        // Assert
        assertEquals(
                RespiratoryHistory.INVALID_MEASUREMENT_MESSAGE,
                exception.getMessage()
        );
        assertEquals(initialCount, history.getMeasurementCount());
    }

    @Test
    @DisplayName("Tests calculating standard deviation with different rates")
    void calculateStandardDeviation() {
        // Arrange
        RespiratoryHistory history = new RespiratoryHistory();
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(12, 60), Instant.now()));
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(12, 60), Instant.now()));
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(24, 60), Instant.now()));
        // Act
        double result = history.calculateStandardDeviation();
        // Assert
        assertEquals(5.656854, result, 0.000001);
    }


    @Test
    @DisplayName("Calculates a positive trend for increasing respiratory rates")
    void calculateTrendIncreasingRates() {
        // Arrange
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(12, 60),
                Instant.parse("2026-01-01T00:00:00Z")));
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(15, 60),
                Instant.parse("2026-01-01T00:01:00Z")));
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(18, 60),
                Instant.parse("2026-01-01T00:02:00Z")));
        // Act
        double trend = history.calculateTrend();
        // Assert
        assertEquals(3.0, trend, 0.000001);
    }

    @Test
    @DisplayName("Calculates a negative trend for decreasing respiratory rates")
    void calculateTrendDecreasingRates() {
        // Arrange
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(24, 60),
                Instant.parse("2026-01-01T00:00:00Z")));
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(18, 60),
                Instant.parse("2026-01-01T00:01:00Z")));
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(12, 60),
                Instant.parse("2026-01-01T00:02:00Z")));
        // Act
        double trend = history.calculateTrend();
        // Assert
        assertEquals(-6.0, trend, 0.000001);
    }

    @Test
    @DisplayName("Returns zero trend for steady respiratory rates")
    void calculateTrendSteadyRates() {
        // Arrange
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(15, 60),
                Instant.parse("2026-01-01T00:00:00Z")));
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(15, 60),
                Instant.parse("2026-01-01T00:01:00Z")));
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(15, 60),
                Instant.parse("2026-01-01T00:02:00Z")));
        // Act
        double trend = history.calculateTrend();
        // Assert
        assertEquals(0.0, trend, 0.000001);
    }

    @Test
    @DisplayName("Throws exception when less than two measurements exist")
    void calculateTrendInsufficientMeasurements() {
        // Arrange
        history.recordMeasurement(new RespiratoryMeasurement(
                new RespiratoryRate(15, 60),
                Instant.parse("2026-01-01T00:00:00Z")));
        // Act and Assert
        assertThrows(IllegalStateException.class,
                () -> history.calculateTrend());
    }


}