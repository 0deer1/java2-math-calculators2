package edu.kirkwood.model.makayla;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents a recorded respiratory rate measurement.
 */
public class RespiratoryMeasurement
        implements Comparable<RespiratoryMeasurement> {
    private final RespiratoryRate respiratoryRate;
    private final Instant measurementTime;

    /**
     * Creates a RespiratoryMeasurement object.
     *
     * @param respiratoryRate the recorded respiratory rate
     * @param measurementTime the time the measurement was recorded
     */
    public RespiratoryMeasurement(
            RespiratoryRate respiratoryRate,
            Instant measurementTime) {
        if (respiratoryRate == null) {
            throw new IllegalArgumentException(
                    "Respiratory rate cannot be null."
            );
        }
        if (measurementTime == null) {
            throw new IllegalArgumentException(
                    "Measurement time cannot be null."
            );
        }
        this.respiratoryRate = respiratoryRate;
        this.measurementTime = measurementTime;
    }

    /**
     * Gets the respiratory rate object.
     *
     * @return the respiratory rate
     */
    public RespiratoryRate getRespiratoryRate() {
        return respiratoryRate;
    }

    /**
     * Gets the measurement time.
     *
     * @return the measurement time
     */
    public Instant getMeasurementTime() {
        return measurementTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RespiratoryMeasurement)) {
            return false;
        }
        RespiratoryMeasurement that = (RespiratoryMeasurement) o;
        return respiratoryRate.getBreaths() == that.respiratoryRate.getBreaths()
                && respiratoryRate.getObservationTime()
                == that.respiratoryRate.getObservationTime()
                && Objects.equals(measurementTime, that.measurementTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                respiratoryRate.getBreaths(),
                respiratoryRate.getObservationTime(),
                measurementTime
        );
    }


    @Override
    public int compareTo(RespiratoryMeasurement other) {
        if (other == null) {
            throw new IllegalArgumentException(
                    "Cannot compare to a null measurement."
            );
        }
        return Double.compare(
                respiratoryRate.calculateRate(),
                other.respiratoryRate.calculateRate()
        );
    }
}
