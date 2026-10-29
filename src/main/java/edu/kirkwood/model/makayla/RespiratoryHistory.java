package edu.kirkwood.model.makayla;

import java.util.ArrayList;
import java.time.Instant;

/**
 * Represents a history of respiratory rate measurements.
 */
public class RespiratoryHistory {

    public static final String INVALID_MEASUREMENT_MESSAGE =
            "Measurement cannot be null.";
    public static final String EMPTY_HISTORY_MESSAGE =
            "No respiratory measurements have been recorded.";
    public static final String INSUFFICIENT_MEASUREMENTS_MESSAGE =
            "At least two respiratory measurements are required to calculate a trend.";
    public static final String INVALID_TREND_TIMES_MESSAGE =
            "Trend cannot be calculated when all measurements have the same timestamp.";

    private final ArrayList<RespiratoryMeasurement> measurements;

    /**
     * Creates an empty respiratory history.
     */
    public RespiratoryHistory() {
        measurements = new ArrayList<>();
    }

    /**
     * Records a respiratory measurement.
     *
     * @param measurement the measurement to record
     */
    public void recordMeasurement(RespiratoryMeasurement measurement) {
        if (measurement == null) {
            throw new IllegalArgumentException(INVALID_MEASUREMENT_MESSAGE);
        }
        measurements.add(measurement);
    }


    /**
     * Calculates the average respiratory rate.
     *
     * @return the average respiratory rate
     */
    public double calculateAverage() {
        if (measurements.isEmpty()) {
            throw new IllegalStateException(EMPTY_HISTORY_MESSAGE);
        }
        double total = 0;

        for (RespiratoryMeasurement measurement : measurements) {
            total += measurement.getRespiratoryRate().calculateRate();
        }
        return total / measurements.size();
    }


    /**
     *
     * Calculates standard deviation of respiratory rates.
     *
     * @return the population standard deviation
     */
    public double calculateStandardDeviation() {
        if (measurements.isEmpty()) {
            throw new IllegalStateException(EMPTY_HISTORY_MESSAGE);
        }

        double average = calculateAverage();
        double squaredDifferences = 0;

        for (RespiratoryMeasurement measurement : measurements) {
            double rate = measurement.getRespiratoryRate().calculateRate();
            double difference = rate - average;
            squaredDifferences += difference * difference;
        }

        return Math.sqrt(squaredDifferences / measurements.size());
    }

    /**
     * Calculates the respiratory rate trend using linear regression.
     *
     * @return the trend in breaths per minute per minute
     */
    public double calculateTrend() {
        if (measurements.size() < 2) {
            throw new IllegalStateException(
                    INSUFFICIENT_MEASUREMENTS_MESSAGE);
        }
        Instant firstTime = measurements.get(0).getMeasurementTime();
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumX2 = 0;
        for (RespiratoryMeasurement measurement : measurements) {
            double x = java.time.Duration.between(
                    firstTime,
                    measurement.getMeasurementTime()
            ).toMillis() / 60000.0;
            double y = measurement.getRespiratoryRate().calculateRate();
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }
        int count = measurements.size();
        double numerator = count * sumXY - sumX * sumY;
        double denominator = count * sumX2 - sumX * sumX;
        if (denominator == 0) {
            throw new IllegalStateException(INVALID_TREND_TIMES_MESSAGE);
        }
        return numerator / denominator;
    }

    /**
     * Gets the highest respiratory rate in the history.
     *
     * @return the highest respiratory rate
     */
    public double getHighestRate() {
        if (measurements.isEmpty()) {
            throw new IllegalStateException(EMPTY_HISTORY_MESSAGE);
        }
        double highest = measurements.get(0)
                .getRespiratoryRate().calculateRate();

        for (RespiratoryMeasurement measurement : measurements) {
            double rate = measurement.getRespiratoryRate().calculateRate();

            if (rate > highest) {
                highest = rate;
            }
        }
        return highest;

    }

    /**
     * Gets the lowest respiratory rate in the history.
     *
     * @return the lowest respiratory rate
     */
    public double getLowestRate() {
        if (measurements.isEmpty()) {
            throw new IllegalStateException(EMPTY_HISTORY_MESSAGE);
        }
        double lowest = measurements.get(0)
                .getRespiratoryRate().calculateRate();
        for (RespiratoryMeasurement measurement : measurements) {
            double rate = measurement.getRespiratoryRate().calculateRate();
            if (rate < lowest) {
                lowest = rate;
            }
        }
        return lowest;

    }

    /**
     * Gets the number of recorded measurements.
     *
     * @return the number of measurements
     */
    public int getMeasurementCount() {
        return measurements.size();
    }


    /**
     * Gets all recorded respiratory measurements.
     *
     * @return a list of recorded measurements
     */
    public ArrayList<RespiratoryMeasurement> getMeasurements() {
        return new ArrayList<>(measurements);
    }


}