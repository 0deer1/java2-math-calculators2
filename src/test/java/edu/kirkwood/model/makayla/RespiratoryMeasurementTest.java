package edu.kirkwood.model.makayla;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class RespiratoryMeasurementTest {
    private RespiratoryRate createRate(int breaths) {
        return new RespiratoryRate(breaths, 60);
    }

    @Test
    @DisplayName("Test getting the respiratory rate")
    void getRespiratoryRate() {
        // Arrange
        RespiratoryRate firstRate = createRate(12);
        RespiratoryRate secondRate = createRate(20);
        RespiratoryMeasurement first = new RespiratoryMeasurement(firstRate, Instant.now());
        RespiratoryMeasurement second = new RespiratoryMeasurement(secondRate, Instant.now());
        // Act
        RespiratoryRate firstResult = first.getRespiratoryRate();
        RespiratoryRate secondResult = second.getRespiratoryRate();
        // Assert
        assertSame(firstRate, firstResult);
        assertSame(secondRate, secondResult);
        assertEquals(12.0, firstResult.calculateRate());
        assertEquals(20.0, secondResult.calculateRate());
    }



    @Test
    @DisplayName("Test getting the measurement time")
    void getMeasurementTime() {
        // Arrange
        Instant firstTime = Instant.now();
        Instant secondTime = Instant.now();
        RespiratoryMeasurement first =
                new RespiratoryMeasurement(createRate(12), firstTime);
        RespiratoryMeasurement second =
                new RespiratoryMeasurement(createRate(20), secondTime);
        // Act
        Instant firstResult = first.getMeasurementTime();
        Instant secondResult = second.getMeasurementTime();
        // Assert
        assertEquals(firstTime, firstResult);
        assertEquals(secondTime, secondResult);
    }


    @Test
    @DisplayName("Test comparing respiratory measurements for equality")
    void equals() {
        // Arrange
        Instant time = Instant.now();
        RespiratoryMeasurement first =
                new RespiratoryMeasurement(createRate(12), time);
        RespiratoryMeasurement second =
                new RespiratoryMeasurement(createRate(12), time);
        // Act
        boolean result = first.equals(second);
        int firstHashCode = first.hashCode();
        int secondHashCode = second.hashCode();
        // Assert
        assertTrue(result);
        assertEquals(firstHashCode, secondHashCode);


    }

    @Test
    @DisplayName("Test different respiratory measurements are not equal")
    void notEquals() {
        // Arrange
        Instant time = Instant.now();
        RespiratoryMeasurement first = new RespiratoryMeasurement(createRate(12), time);
        RespiratoryMeasurement second = new RespiratoryMeasurement(createRate(20), time);
        /// Act
        boolean result = first.equals(second);
        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Test comparing respiratory measurement rates")
    void compareTo() {
        // Arrange
        RespiratoryMeasurement first =
                new RespiratoryMeasurement(createRate(12), Instant.now());
        RespiratoryMeasurement second =
                new RespiratoryMeasurement(createRate(20), Instant.now());
        // Act
        int result = first.compareTo(second);
        // Assert
        assertTrue(result < 0);


    }

    @Test
    @DisplayName("Test comparing higher respiratory measurement")
    void compareToHigher() {
        // Arrange
        RespiratoryMeasurement first =
                new RespiratoryMeasurement(createRate(20), Instant.now());
        RespiratoryMeasurement second =
                new RespiratoryMeasurement(createRate(12), Instant.now());
        // Act
        int result = first.compareTo(second);
        // Assert
        assertTrue(result > 0);
    }


    @Test
    @DisplayName("Test comparing equal respiratory measurements")
    void compareToEqual() {
        // Arrange
        RespiratoryMeasurement first =
                new RespiratoryMeasurement(createRate(12), Instant.now());
        RespiratoryMeasurement second =
                new RespiratoryMeasurement(createRate(12), Instant.now());
        // Act
        int result = first.compareTo(second);
        // Assert
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Test creating a measurement with a null respiratory rate")
    void nullRespiratoryRate() {
        // Arrange
        Instant measurementTime = Instant.now();
        // Act
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RespiratoryMeasurement(null, measurementTime)
        );
        // Assert
        assertEquals("Respiratory rate cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("Test creating a measurement with a null timestamp")
    void nullMeasurementTime() {
        // Arrange
        RespiratoryRate respiratoryRate = createRate(12);
        // Act
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RespiratoryMeasurement(respiratoryRate, null)
        );
        // Assert
        assertEquals("Measurement time cannot be null.", exception.getMessage());
    }

}