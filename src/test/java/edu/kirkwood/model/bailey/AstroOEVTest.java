package edu.kirkwood.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the calculations and behavior of the AstroOEV class.
 * Tests include integration time, Overall Exposure Value (OEV),
 * sensor multipliers, and image quality ratings.
 */
class AstroOEVTest {
    // Create object to test.
    private AstroOEV astroOEV;
    private AstroOEV astroOEV2;

    /**
     * Creates a fresh AstroOEV object with the default test conditions
     * before each test is run.
     */
    @BeforeEach  // Before each test, run this setup.
    void setup(){
        astroOEV = new AstroOEV(360, 7.1,
                200, "full", 5);

        astroOEV2 = new AstroOEV(20, 5.6,
                300, "APS-C", 3);
    }

    // Getter tests----------------------------------------------------------------------

    @Test
    @DisplayName("Returns correct sub-exposure time")
    void getSubExposureTime() {
        assertEquals(360, astroOEV.getSubExposureTime(), 0.000001);
        assertEquals(20, astroOEV2.getSubExposureTime(), 0.000001);
    }

    @Test
    @DisplayName("Returns correct f-stop")
    void getFStop() {
        assertEquals(7.1, astroOEV.getFStop(), 0.000001);
        assertEquals(5.6, astroOEV2.getFStop(), 0.000001);
    }

    @Test
    @DisplayName("Returns correct frame count")
    void getFrameCount() {
        assertEquals(200, astroOEV.getFrameCount());
        assertEquals(300, astroOEV2.getFrameCount());
    }

    @Test
    @DisplayName("Returns correct sensor size")
    void getSensorSize() {
        assertEquals("full", astroOEV.getSensorSize());
        assertEquals("APS-C", astroOEV2.getSensorSize());
    }

    @Test
    @DisplayName("Returns correct Bortle value")
    void getBortle() {
        assertEquals(5, astroOEV.getBortle());
        assertEquals(3, astroOEV2.getBortle());
    }

    // Attribute tests------------------------------------------------------------
    @Test
    @DisplayName("Rejects zero sub-exposure time")
    void invalidSubExposureTimeZero(){
        assertThrows(IllegalArgumentException.class, () ->   // THIS is the moment that lambda expressions finally clicked for me!
                astroOEV.setSubExposureTime(0));
    }
    @Test
    @DisplayName("Rejects negative sub-exposure times")
    void invalidSubExposureTimeNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                astroOEV.setSubExposureTime(-5));
    }

    @Test
    @DisplayName("Rejects a zero F-stop.")
    void invalidFStopZero() {
        assertThrows(IllegalArgumentException.class, () ->
                astroOEV.setFStop(0));
    }
    @Test
    @DisplayName("Rejects a negative F-stop.")
    void invalidFStopNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                astroOEV.setFStop(-5));
    }

    @Test
    @DisplayName("Rejects a zero frame count")
    void invalidFrameCountZero() {
        assertThrows(IllegalArgumentException.class, () ->
                astroOEV.setFrameCount(0));
    }
    @Test
    @DisplayName("Rejects a negative frame count")
    void invalidFrameCountNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                astroOEV.setFrameCount(-5));
    }

    @Test
    @DisplayName("Rejects a null sensor size")
    void invalidSensorSizeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                astroOEV.setSensorSize(null));
    }
    @Test
    @DisplayName("Rejects a blank sensor size")
    void invalidSensorSizeBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                astroOEV.setSensorSize(""));
    }
    @Test
    @DisplayName("Rejects unsupported sensor sizes")
    void invalidSensorSizeUnsupported() {
        assertThrows(IllegalArgumentException.class, () ->
                astroOEV.setSensorSize("large"));
    }

    @Test
    @DisplayName("Rejects zero Bortle value")
    void invalidBortleZero() {
        assertThrows(IllegalArgumentException.class, () ->
                astroOEV.setBortle(0));
    }
    @Test
    @DisplayName("Rejects negative Bortle value")
    void invalidBortleNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                astroOEV.setBortle(-5));
    }
    @Test
    @DisplayName("Rejects Bortle values above 9")
    void invalidBortleAboveNine() {
        assertThrows(IllegalArgumentException.class, () ->
                astroOEV.setBortle(10));
    }

    // IntegrationTime tests--------------------------------------------------

    @Test
    @DisplayName("Calculates integration time using default conditions")
    void calcIntegrationTime(){
        // Act
        double actual = astroOEV.calcIntegrationTime();
        // Assert
        assertEquals(72000, actual, 0.000001);  // Expected vs actual.
    }

    @Test
    @DisplayName("Calculates integration time with one frame")
    void calcIntegrationTimeSingleFrame(){
        // Arrange
        astroOEV.setFrameCount(1);  // Set frameCount to 1.
        // Act
        double actual = astroOEV.calcIntegrationTime();
        // Assert
        assertEquals(360, actual, 0.000001);  // Expected vs actual.
    }

    @Test
    @DisplayName("Calculates integration time with a 1 second exposure")
    void calcIntegrationTimeShortExposure(){
        // Arrange
        astroOEV.setSubExposureTime(1);
        // Act
        double actual = astroOEV.calcIntegrationTime();
        // Assert
        assertEquals(200, actual, 0.000001);  // Expected vs actual.
    }

    @Test
    @DisplayName("Calculates integration time with a decimal exposure")
    void calcIntegrationTimeDecimalExposure(){
        // Arrange
        astroOEV.setSubExposureTime(2.5);  // 2.5 seconds.
        // Act
        double actual = astroOEV.calcIntegrationTime();
        // Assert
        assertEquals(500, actual, 0.000001);  // Expected vs actual.
    }

    @Test
    @DisplayName("Calculates integration time with many frames")
    void calcIntegrationTimeManyFrames(){
        // Arrange
        astroOEV.setFrameCount(25000);
        // Act
        double actual = astroOEV.calcIntegrationTime();
        // Assert
        assertEquals(9000000, actual, 0.000001);  // 25000 * 360 = 9 million seconds.
    }
    // OEV tests----------------------------------------------------------------------

    @Test
    @DisplayName("Calculates OEV using default conditions")
    void calcOEV(){
        double actual = astroOEV.calcOEV();
        assertEquals(84983, actual, 0.01);
    }

    @Test
    @DisplayName("Calculates OEV with a low f-stop")
    void calcOEVLowFStop(){
        astroOEV.setFStop(1.4);
        double actual = astroOEV.calcOEV();
        assertEquals(2185714, actual, 0.01);
    }

    @Test
    @DisplayName("Calculates OEV with 1 frame")
    void calcOEVSingleFrame(){
        astroOEV.setFrameCount(1);
        double actual = astroOEV.calcOEV();
        assertEquals(425, actual, 0.01);
    }

    @Test
    @DisplayName("Calculates OEV with a decimal exposure")
    void calcOEVDecimalExposure(){
        astroOEV.setSubExposureTime(3.25);
        double actual = astroOEV.calcOEV();
        assertEquals(767, actual, 0.01);
    }

    @Test
    @DisplayName("Calculates OEV with a different sensor")
    void calcOEVDifferentSensor(){
        astroOEV.setSensorSize("micro");
        double actual = astroOEV.calcOEV();
        assertEquals(22124, actual, 0.01);
    }

    @Test
    @DisplayName("Calculates OEV with alternate conditions")
    void calcOEVAlternateConditions(){
        astroOEV.setSubExposureTime(15);
        astroOEV.setFrameCount(300);
        astroOEV.setFStop(2.8);
        astroOEV.setSensorSize("apsc");
        double actual = astroOEV.calcOEV();
        assertEquals(14493, actual, 0.01);
    }
    // SensorMultiplier tests----------------------------------------

    @Test
    @DisplayName("Calculates multiplier for a micro sensor")
    void calcSensorMultiplierMicro(){
        astroOEV.setSensorSize("micro");
        double actual = astroOEV.calcSensorMultiplier();
        assertEquals(0.1549, actual, 0.000001);
    }
    @Test
    @DisplayName("Calculates multiplier for an APSC sensor")
    void calcSensorMultiplierApsc(){
        astroOEV.setSensorSize("apsc");
        double actual = astroOEV.calcSensorMultiplier();
        assertEquals(0.2525, actual, 0.000001);
    }
    @Test
    @DisplayName("Calculates multiplier for a full-frame sensor")
    void calcSensorMultiplierFull(){
        double actual = astroOEV.calcSensorMultiplier();
        assertEquals(0.595, actual, 0.000001);
    }
    @Test
    @DisplayName("Calculates multiplier for a medium sensor")
    void calcSensorMultiplierMedium(){
        astroOEV.setSensorSize("medium");
        double actual = astroOEV.calcSensorMultiplier();
        assertEquals(1, actual, 0.000001);
    }
    @Test
    @DisplayName("Recognizes uppercase 'Full' sensor")
    void calcSensorMultiplierUppercase1(){
        astroOEV.setSensorSize("Full");
        double actual = astroOEV.calcSensorMultiplier();
        assertEquals(0.595, actual, 0.000001);
    }
    @Test
    @DisplayName("Recognizes uppercase 'APSC' sensor")
    void calcSensorMultiplierUppercase2(){
        astroOEV.setSensorSize("APSC");
        double actual = astroOEV.calcSensorMultiplier();
        assertEquals(0.2525, actual, 0.000001);
    }
    @Test
    @DisplayName("Recognizes hyphen in 'APS-C' sensor")
    void calcSensorMultiplierHyphen(){
        astroOEV.setSensorSize("APS-C");  // Hyphenated 'APS-C'
        double actual = astroOEV.calcSensorMultiplier();
        assertEquals(0.2525, actual, 0.000001);
    }
    // ImageQuality tests--------------------------------------------------

    @Test
    @DisplayName("Returns Poor for minimum conditions")
    void calcImageQualityMinimum(){
        astroOEV.setBortle(9);
        astroOEV.setSubExposureTime(0.5);
        astroOEV.setFrameCount(1);
        String actual = astroOEV.calcImageQuality();
        assertEquals("Poor", actual);
    }
    @Test
    @DisplayName("Returns Poor for very low conditions")
    void calcImageQualityVeryLow(){
        astroOEV.setBortle(9);
        astroOEV.setSubExposureTime(5);
        astroOEV.setFrameCount(5);
        String actual = astroOEV.calcImageQuality();
        assertEquals("Poor", actual);
    }
    @Test
    @DisplayName("Returns Fair, right above the boundary between poor and fair (900 < 932.91)")
    void calcImageQualityFairBoundary(){
        astroOEV.setBortle(5);
        astroOEV.setSubExposureTime(15);
        astroOEV.setFrameCount(110);
        String actual = astroOEV.calcImageQuality();
        assertEquals("Fair", actual);
    }
    @Test
    @DisplayName("Returns Good for good conditions (2,400 < 2419.56")
    void calcImageQualityGood(){
        astroOEV.setBortle(4);
        astroOEV.setSubExposureTime(20);
        astroOEV.setFrameCount(180);
        String actual = astroOEV.calcImageQuality();
        assertEquals("Good", actual);
    }
    @Test
    @DisplayName("Returns Excellent for excellent conditions (12,000 < 12,170.22)")
    void calcImageQualityExcellent(){
        astroOEV.setBortle(3);
        astroOEV.setSubExposureTime(30);
        astroOEV.setFrameCount(520);
        String actual = astroOEV.calcImageQuality();
        assertEquals("Excellent", actual);
    }
    @Test
    @DisplayName("Returns Excellent, even if the bortle is high.")
    void calcImageQualityHighBortle(){
        astroOEV.setBortle(9);
        astroOEV.setSubExposureTime(300);
        astroOEV.setFrameCount(1000);
        String actual = astroOEV.calcImageQuality();
        assertEquals("Excellent", actual);
    }
}