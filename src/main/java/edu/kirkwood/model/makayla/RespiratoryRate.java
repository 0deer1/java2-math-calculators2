package edu.kirkwood.model.makayla;

/**
 * Represents a respiratory rate calculation based on the number of
 * breaths counted during a specified observation time.
 */
public class RespiratoryRate {
    public static final String INVALID_BREATHS_MESSAGE = "Breaths must be greater than zero";
    public static final String INVALID_OBSERVATION_TIME_MESSAGE = "Observation time must be greater than zero";

    private int breaths;
    private int observationTime;

    /**
     * Creates a RespiratoryRate object.
     *
     * @param breaths number of breaths counted
     * @param observationTime time used to count the breaths in seconds
     * @throws IllegalArgumentException if breaths or observation time is less than or equal to zero.
     */
    public RespiratoryRate(int breaths, int observationTime) {
        setBreaths(breaths);
        setObservationTime(observationTime);
    }

    /**
     * Gets the number of breaths.
     *
     *
     * @return number of breaths
     *
     */
    public int getBreaths(){
        return breaths;
    }
    /**
     * Sets the number of breaths
     *
     * @param breaths number of breaths counted
     * */
    public void setBreaths(int breaths) {
        if (breaths <= 0) {
            throw new IllegalArgumentException(INVALID_BREATHS_MESSAGE);
        }
        this.breaths = breaths;
    }

    /**
     * Gets the observation time.
     *
     *
     * @return observation time in seconds
     */
    public int getObservationTime() {
        return observationTime;
    }

    /**
     * Sets the observation time.
     *
     * @param observationTime time used to count the breaths in seconds
     */
    public void setObservationTime(int observationTime) {
        if (observationTime <= 0) {
            throw new IllegalArgumentException(INVALID_OBSERVATION_TIME_MESSAGE);
        }
        this.observationTime = observationTime;
    }

    /**
     * Gets the respiratory rate.
     *
     *
     * @return respiratory rate
     */
    public double getRespiratoryRate() {
        return calculateRate();
    }

    /**
     *
     * Calculates the respiratory rate.
     *
     * @return respiratory rate in breaths per minute
     */
    public double calculateRate() {
        return ((double) breaths / observationTime) * 60;
    }
}
