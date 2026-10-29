package edu.kirkwood.model.wren;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoordinateTest {

    private Coordinate coordinate1;
    private Coordinate coordinate2;

    @BeforeEach
    void setUp() {
        coordinate1 = new Coordinate(2, 2);
        coordinate2 = new Coordinate(2, 3);
    }

    @Test
    void getXValue() {
        assertEquals(2, coordinate1.getXValue());
        assertEquals(2, coordinate2.getXValue());
    }

    @Test
    void setXValue() {
        double expectedValue = 4;
        coordinate1.setXValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getXValue());
    }

    @Test
    void setXValuePositive() {
        double expectedValue = 10;
        coordinate1.setXValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getXValue());
    }

    @Test
    void setXValueNegative() {
        double expectedValue = -10;
        coordinate1.setXValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getXValue());
    }

    @Test
    void setXValueZero() {
        double expectedValue = 0;
        coordinate1.setXValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getXValue());
    }

    @Test
    void setXValueNegativeZero() {
        double expectedValue = -0.0;
        coordinate1.setXValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getXValue());
    }

    @Test
    void setXValueZeroDecimal() {
        double expectedValue = 0.54;
        coordinate1.setXValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getXValue());
    }

    @Test
    void setXValueNegativeZeroDecimal() {
        double expectedValue = -0.98;
        coordinate1.setXValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getXValue());
    }

    @Test
    void setXValueLongDecimalZero() {
        double expectedValue = 0.865412;
        coordinate1.setXValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getXValue());
    }

    @Test
    void setXValueLongNegativeDecimalZero() {
        double expectedValue = -0.852416;
        coordinate1.setXValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getXValue());
    }

    @Test
    void setXValueDecimal() {
        double expectedValue = 2.33;
        coordinate1.setXValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getXValue());
    }

    @Test
    void setXValueLongDecimal() {
        double expectedValue = 5.987645;
        coordinate1.setXValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getXValue());
    }

    @Test
    void setXValueNegativeDecimal() {
        double expectedValue = -6.54;
        coordinate1.setXValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getXValue());
    }

    @Test
    void setXValueLongNegativeDecimal() {
        double expectedValue = -3.348561;
        coordinate1.setXValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getXValue());
    }

    @Test
    void getYValue() {
        assertEquals(2, coordinate1.getYValue());
        assertEquals(3, coordinate2.getYValue());
    }

    @Test
    void setYValue() {
        double expectedValue = 4;
        coordinate1.setYValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getYValue());
    }

    @Test
    void setYValuePositive() {
        double expectedValue = 10;
        coordinate1.setYValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getYValue());
    }

    @Test
    void setYValueNegative() {
        double expectedValue = -10;
        coordinate1.setYValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getYValue());
    }

    @Test
    void setYValueZero() {
        double expectedValue = 0;
        coordinate1.setYValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getYValue());
    }

    @Test
    void setYValueNegativeZero() {
        double expectedValue = -0.0;
        coordinate1.setYValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getYValue());
    }

    @Test
    void setYValueZeroDecimal() {
        double expectedValue = 0.54;
        coordinate1.setYValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getYValue());
    }

    @Test
    void setYValueNegativeZeroDecimal() {
        double expectedValue = -0.98;
        coordinate1.setYValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getYValue());
    }

    @Test
    void setYValueLongDecimalZero() {
        double expectedValue = 0.865412;
        coordinate1.setYValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getYValue());
    }

    @Test
    void setYValueLongNegativeDecimalZero() {
        double expectedValue = -0.852416;
        coordinate1.setYValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getYValue());
    }

    @Test
    void setYValueDecimal() {
        double expectedValue = 2.33;
        coordinate1.setYValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getYValue());
    }

    @Test
    void setYValueLongDecimal() {
        double expectedValue = 5.987645;
        coordinate1.setYValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getYValue());
    }

    @Test
    void setYValueNegativeDecimal() {
        double expectedValue = -6.54;
        coordinate1.setYValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getYValue());
    }

    @Test
    void setYValueLongNegativeDecimal() {
        double expectedValue = -3.348561;
        coordinate1.setYValue(expectedValue);
        assertEquals(expectedValue, coordinate1.getYValue());
    }

    @Test
    void toStringTest() {
        assertEquals("(2.00,2.00)", coordinate1.toString());
        assertEquals("(2.00,3.00)", coordinate2.toString());
    }

    @Test
    void calculateDistance() {
        double expectedValue = 1;
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceRoundTwoPlaces() {
        double expectedValue = 1.41;
        coordinate1 = new Coordinate(1, 1);
        coordinate2 = new Coordinate(2, 2);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceRoundUpFive() {
        double expectedValue = 3.61;
        coordinate1 = new Coordinate(1, 1);
        coordinate2 = new Coordinate(4, 3);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceRoundUpFiveNegative() {
        double expectedValue = 3.61;
        coordinate1 = new Coordinate(-1, -1);
        coordinate2 = new Coordinate(-4, -3);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceRoundUpRollover() {
        double expectedValue = 9.90;
        coordinate1 = new Coordinate(1, 1);
        coordinate2 = new Coordinate(8, 8);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceRoundUpRolloverNegative() {
        double expectedValue = 9.90;
        coordinate1 = new Coordinate(-1, -1);
        coordinate2 = new Coordinate(-8, -8);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceRoundDown() {
        double expectedValue = 1.41;
        coordinate1 = new Coordinate(1, 1);
        coordinate2 = new Coordinate(2, 2);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceRoundDownNegative() {
        double expectedValue = 1.41;
        coordinate1 = new Coordinate(-1, -1);
        coordinate2 = new Coordinate(-2, -2);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceAllPositive() {
        double expectedValue = 3;
        coordinate1 = new Coordinate(5, 6);
        coordinate2 = new Coordinate(5, 9);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceAllNegative() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(-6, -5);
        coordinate2 = new Coordinate(-5, -5);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceAllZero() {
        double expectedValue = 0;
        coordinate1 = new Coordinate(0,0);
        coordinate2 = new Coordinate(0,0);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceOnePositive() {
        double expectedValue = 17.89;
        coordinate1 = new Coordinate(4,8);
        coordinate2 = new Coordinate(-4,-8);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceOneNegative() {
        double expectedValue = 14.42;
        coordinate1 = new Coordinate(-3,-8);
        coordinate2 = new Coordinate(5,4);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceOneZero() {
        double expectedValue = 5.83;
        coordinate1 = new Coordinate(0,0);
        coordinate2 = new Coordinate(3,5);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceAllDecimal() {
        double expectedValue = 0.56;
        coordinate1 = new Coordinate(0.25, 0.75);
        coordinate2 = new Coordinate(0.5,1.25);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceAllDecimalPositive() {
        double expectedValue = 2.25;
        coordinate1 = new Coordinate(1.33,2.66);
        coordinate2 = new Coordinate(0.5,4.75);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceAllDecimalNegative() {
        double expectedValue = 1.13;
        coordinate1 = new Coordinate(-0.66,-4.5);
        coordinate2 = new Coordinate(-1.23,-3.52);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceSameValue() {
        double expectedValue = 0;
        coordinate1 = new Coordinate(5,4);
        coordinate2 = new Coordinate(5,4);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateDistanceOneDecimal() {
        double expectedValue = 3.59;
        coordinate1 = new Coordinate(5,5);
        coordinate2 = new Coordinate(1.66,6.32);
        assertEquals(expectedValue, coordinate1.calculateDistanceBetween(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateDistanceBetween(coordinate1));
    }

    @Test
    void calculateSlope() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(1,1);
        coordinate2 = new Coordinate(2,2);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }


    @Test
    void calculateSlopeRoundTwoPlaces() {
        double expectedValue = 1.28;
        coordinate1 = new Coordinate(1, 1);
        coordinate2 = new Coordinate(8.51, 10.65);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeRoundUpFive() {
        double expectedValue = 3.77;
        coordinate1 = new Coordinate(1, 1);
        coordinate2 = new Coordinate(3.39, 10);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeRoundUpFiveNegative() {
        double expectedValue = 3.77;
        coordinate1 = new Coordinate(-1, -1);
        coordinate2 = new Coordinate(-3.39, -10);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeRoundUpRollover() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(1, 1);
        coordinate2 = new Coordinate(10.68, 10.65);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeRoundUpRolloverNegative() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(-1, -1);
        coordinate2 = new Coordinate(-10.68, -10.65);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeRoundDown() {
        double expectedValue = 1.28;
        coordinate1 = new Coordinate(1, 1);
        coordinate2 = new Coordinate(8.52, 10.65);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeRoundDownNegative() {
        double expectedValue = 1.28;
        coordinate1 = new Coordinate(-1, -1);
        coordinate2 = new Coordinate(-8.52, -10.65);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopePositive() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(2,3);
        coordinate2 = new Coordinate(4,5);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeOneNegative() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(-1,-1);
        coordinate2 = new Coordinate(2,2);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeBothNegative() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(-1,-1);
        coordinate2 = new Coordinate(-2,-2);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeOneInputZero() {
        double expectedValue = -1;
        coordinate1 = new Coordinate(2,0);
        coordinate2 = new Coordinate(0,2);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeOnecoordinateZero() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(0,0);
        coordinate2 = new Coordinate(2,2);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeBothZero() {
        coordinate1 = new Coordinate(0,0);
        coordinate2 = new Coordinate(0,0);
        //Exception message should be "These two points are the same"
        assertThrows(ArithmeticException.class, ()-> coordinate1.calculateSlope(coordinate2));
    }

    @Test
    void calculateSlopeBothSame() {
        coordinate1 = new Coordinate(3,5);
        coordinate2 = new Coordinate(3,5);
        //Exception message should be "These two points are the same"
        assertThrows(ArithmeticException.class, ()-> coordinate1.calculateSlope(coordinate2));
    }

    @Test
    void calculateSlopeResultInfinite() {
        coordinate1 = new Coordinate(5,10);
        coordinate2 = new Coordinate(5,0);
        //Exception message should be "The Slope is Infinite"
        assertThrows(ArithmeticException.class, ()-> coordinate1.calculateSlope(coordinate2));
    }

    @Test
    void calculateSlopeResultZero() {
        double expectedValue = 0;
        coordinate1 = new Coordinate(0,1);
        coordinate2 = new Coordinate(5,1);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    //decimals

    @Test
    void calculateSlopeDecimal() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(1.5,1.5);
        coordinate2 = new Coordinate(2.5,2.5);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopePositiveDecimal() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(2.5,3.5);
        coordinate2 = new Coordinate(4.5,5.5);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeOneNegativeDecimal() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(-1.5,-1.5);
        coordinate2 = new Coordinate(2.5,2.5);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeBothNegativeDecimal() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(-1.5,-1.5);
        coordinate2 = new Coordinate(-2.5,-2.5);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeOneInputZeroDecimal() {
        double expectedValue = -1;
        coordinate1 = new Coordinate(2.5,0);
        coordinate2 = new Coordinate(0,2.5);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeOnecoordinateZeroDecimal() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(0,0);
        coordinate2 = new Coordinate(2.5,2.5);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeBothDecimal() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(0.5,0.5);
        coordinate2 = new Coordinate(0.75,0.75);

        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeBothSameDecimal() {
        coordinate1 = new Coordinate(3.5,5.5);
        coordinate2 = new Coordinate(3.5,5.5);
        //Exception message should be "These two points are the same"
        assertThrows(ArithmeticException.class, ()-> coordinate1.calculateSlope(coordinate2));
    }

    @Test
    void calculateSlopeResultInfiniteDecimal() {
        coordinate1 = new Coordinate(5.5,10.5);
        coordinate2 = new Coordinate(5.5,0.5);
        //Exception message should be "The Slope is Infinite"
        assertThrows(ArithmeticException.class, ()-> coordinate1.calculateSlope(coordinate2));
    }

    @Test
    void calculateSlopeResultZeroDecimal() {
        double expectedValue = 0;
        coordinate1 = new Coordinate(0.5,1.5);
        coordinate2 = new Coordinate(5.5,1.5);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    //Long Decimal

    @Test
    void calculateSlopeDecimalLong() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(1.56789,1.56789);
        coordinate2 = new Coordinate(2.56789,2.56789);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopePositiveDecimalLong() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(2.56789,3.56789);
        coordinate2 = new Coordinate(4.56789,5.56789);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeOneNegativeDecimalLong() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(-1.56789,-1.56789);
        coordinate2 = new Coordinate(2.56789,2.56789);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeBothNegativeDecimalLong() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(-1.56789,-1.56789);
        coordinate2 = new Coordinate(-2.56789,-2.56789);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeOneInputZeroDecimalLong() {
        double expectedValue = -1;
        coordinate1 = new Coordinate(2.56789,0);
        coordinate2 = new Coordinate(0,2.56789);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeOnecoordinateZeroDecimalLong() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(0,0);
        coordinate2 = new Coordinate(2.56789,2.56789);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeBothDecimalLong() {
        double expectedValue = 1;
        coordinate1 = new Coordinate(0.56789,0.56789);
        coordinate2 = new Coordinate(0.756789,0.756789);

        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeBothSameDecimalLong() {
        coordinate1 = new Coordinate(3.56789,5.56789);
        coordinate2 = new Coordinate(3.56789,5.56789);
        //Exception message should be "These two points are the same"
        assertThrows(ArithmeticException.class, ()-> coordinate1.calculateSlope(coordinate2));
        assertThrows(ArithmeticException.class, ()-> coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeResultInfiniteDecimalLong() {
        coordinate1 = new Coordinate(5.56789,10.56789);
        coordinate2 = new Coordinate(5.56789,0.56789);
        //Exception message should be "The Slope is Infinite"
        assertThrows(ArithmeticException.class, ()-> coordinate1.calculateSlope(coordinate2));
        assertThrows(ArithmeticException.class, ()-> coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void calculateSlopeResultZeroDecimalLong() {
        double expectedValue = 0;
        coordinate1 = new Coordinate(0.56789,1.56789);
        coordinate2 = new Coordinate(5.56789,1.56789);
        assertEquals(expectedValue, coordinate1.calculateSlope(coordinate2));
        assertEquals(expectedValue, coordinate2.calculateSlope(coordinate1));
    }

    @Test
    void equalsMethodTest() {
        coordinate1 = new Coordinate(3,2);
        coordinate2 = new Coordinate(3, 2);
        assertEquals(coordinate1, coordinate2);
    }

    @Test
    void getQuadrantOneTest() {
        int expectedValue = 1;
        coordinate1 = new Coordinate(1,1);

        assertEquals(expectedValue, coordinate1.getQuadrant());
    }

    @Test
    void getQuadrantTwoTest() {
        int expectedValue = 2;
        coordinate1 = new Coordinate(-1,1);

        assertEquals(expectedValue, coordinate1.getQuadrant());
    }

    @Test
    void getQuadrantThreeTest() {
        int expectedValue = 3;
        coordinate1 = new Coordinate(-1,-1);

        assertEquals(expectedValue, coordinate1.getQuadrant());
    }

    @Test
    void getQuadrantMethodTest() {
        int expectedValue = 4;
        coordinate1 = new Coordinate(1,-1);

        assertEquals(expectedValue, coordinate1.getQuadrant());
    }

    @Test
    void getQuadrantOriginTest() {
        int expectedValue = 0;
        coordinate1 = new Coordinate(0,0);

        assertEquals(expectedValue, coordinate1.getQuadrant());
    }

    @Test
    void getQuadrantXAxisTest() {
        int expectedValue = -1;
        coordinate1 = new Coordinate(1,0);

        assertEquals(expectedValue, coordinate1.getQuadrant());
    }

    @Test
    void getQuadrantYAxisTest() {
        int expectedValue = -2;
        coordinate1 = new Coordinate(0,1);

        assertEquals(expectedValue, coordinate1.getQuadrant());
    }

    @Test
    void compareToGreaterThanTest() {
        int expectedValue = 1;
        coordinate1 = new Coordinate(-1,1);
        coordinate2 = new Coordinate(1, 1);

        assertEquals(expectedValue, coordinate1.compareTo(coordinate2));
    }

    @Test
    void compareToLessThanTest() {
        int expectedValue = -1;
        coordinate1 = new Coordinate(1,1);
        coordinate2 = new Coordinate(-1, 1);

        assertEquals(expectedValue, coordinate1.compareTo(coordinate2));
    }

    @Test
    void compareToSameQuadrantTest() {
        int expectedValue = 0;
        coordinate1 = new Coordinate(1,1);
        coordinate2 = new Coordinate(3, 5);

        assertEquals(expectedValue, coordinate1.compareTo(coordinate2));
    }

}