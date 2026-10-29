package edu.kirkwood.model.wren;

/**
 * represents a 2-dimensional coordinate location
 * capable of calculating the distance between it and another coordinate
 */
public class Coordinate implements Comparable<Coordinate> {

    private double xValue;
    private double yValue;

    /**
     * no param constructor, defaults values to 1
     */
    public Coordinate(){
        xValue = 1;
        yValue = 1;
    }

    /**
     * constructor, accepts one double each for x and y values
     * @param inputX double, x value of coordinate
     * @param inputY double, y value of coordinate
     */
    public Coordinate(double inputX, double inputY){
        setXValue(inputX);
        setYValue(inputY);
    }

    /**
     * math rounding citation: https://www.geeksforgeeks.org/java/java-program-to-round-a-number-to-n-decimal-places/
     * calculates distances between this coordinate and a provided coordinate
     * @param otherCoordinate Coordinate, the other point
     * @return double, the distance between these two points
     */
    public double calculateDistanceBetween(Coordinate otherCoordinate){
        double result = Math.hypot(this.getXValue() - otherCoordinate.getXValue(), this.getYValue() - otherCoordinate.getYValue());
        result = Math.round(result * Math.pow(10, 2)) / Math.pow(10, 2);
        return result;
    }


    /**
     * calculates the slope of the line between this coordinate and a provided coordinate
     * @param otherCoordinate Coordinate, the other point
     * @return double, the slope of the line between these two points
     */
    public double calculateSlope(Coordinate otherCoordinate){
        if (this.getXValue() == otherCoordinate.getXValue() && this.getYValue() == otherCoordinate.getYValue()){
            throw new ArithmeticException("These points are the same.");
        }
        if (this.getXValue() == otherCoordinate.getXValue()){
            throw new ArithmeticException("The slope is infinite.");
        }
        if (this.getYValue() == otherCoordinate.getYValue()){
            return 0;
        }

        double result = (this.getYValue() - otherCoordinate.getYValue()) / (this.getXValue() - otherCoordinate.getXValue());
        result = Math.round(result * Math.pow(10, 2)) / Math.pow(10, 2);
        return result;
    }

    /**
     * determines the quadrant location of the coordinate
     * @return int, the location of the quadrant 1,2,3 or 4. also returns 0 for origin
     * -1 for x-axis and -2 for y-axis
     */
    public int getQuadrant(){
        if(getXValue() == 0 && getYValue() == 0){
            return 0;
        }
        if (getYValue() == 0){
            return -1;
        }
        if (getXValue() == 0){
            return -2;
        }
        if (getXValue() > 0 && getYValue() > 0){
            return 1;
        }
        if (getXValue() < 0 && getYValue() > 0){
            return 2;
        }
        if (getXValue() < 0 && getYValue() < 0){
            return 3;
        }
        if (getXValue() > 0 && getYValue() < 0){
            return 4;
        }
        return -3;
    }

    /**
     * accessor for xValue
     * @return double, xValue
     */
    public double getXValue() {
        return xValue;
    }

    /**
     * mutator for xValue
     * @param xValue double, xValue
     */
    public void setXValue(double xValue) {
        this.xValue = xValue;
    }

    /**
     * accessor for yValue
     * @return double, yValue
     */
    public double getYValue() {
        return yValue;
    }

    /**
     * mutator for yValue
     * @param yValue double, yValue
     */
    public void setYValue(double yValue) {
        this.yValue = yValue;
    }

    /**
     * override for toString(), returns self descriptive string
     * @return String, information about this Coordinate
     */
    @Override
    public String toString() {
        return "(" + String.format("%.2f", xValue) + "," + String.format("%.2f", yValue) + ")";
    }


    @Override
    public int compareTo(Coordinate o) {
        if(this.getQuadrant() > o.getQuadrant()){
            return 1;
        } else if (this.getQuadrant() < o.getQuadrant()) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * override for equals method
     *  referenced "https://www.geeksforgeeks.org/java/overriding-equals-method-in-java/"
     * @param o   the reference object with which to compare.
     * @return boolean, whether or not the compared object is equal
     */
    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }

        if(!(o instanceof Coordinate)) {
            return false;
        }

        Coordinate otherCoordinate = (Coordinate) o;

        if (this.getXValue() == otherCoordinate.getXValue() && this.getYValue() == otherCoordinate.getYValue()){
            return true;
        } else {
            return false;
        }
    }
}
