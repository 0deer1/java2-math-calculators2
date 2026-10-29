package edu.kirkwood.model.marc;

import java.util.Objects;

/**
 * Represents a fraction with an integer numerator and denominator.
 * This class provides methods for fraction arithmetic, simplification,
 * and comparison.
 */
public class Fraction implements Comparable<Fraction> {
    private int numerator;
    private int denominator;

    /**
     * Default constructor
     * Initializes a new fraction to 1/1
     */
    public Fraction() {
        numerator = 1;
        denominator = 1;
    }

    /**
     * Constructs a fraction with a specified numerator and denominator.
     *
     * @param numerator   the numerator of the fraction
     * @param denominator the denominator of the fraction
     */
    public Fraction(int numerator, int denominator) {
        setNumerator(numerator);
        setDenominator(denominator);
    }

    /**
     * Returns a string representation of the fraction in the format "numerator/denominator".
     *
     * @return a string representation of the fraction
     */
    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    /**
     * Gets the numerator of the fraction.
     *
     * @return the numerator
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Set the numerator of the fraction
     * @param numerator the top portion of a fraction
     */
    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }


    /**
     * Gets the denominator of the fraction.
     *
     * @return the denominator
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * Sets the denominator of the fraction.
     *
     * @param denominator the new denominator
     * @throws ArithmeticException if the denominator is zero
     */
    public void setDenominator(int denominator) {
        if(denominator == 0) {
            throw new ArithmeticException("Denominator cannot be zero");
        }
        if(denominator < 0 && numerator > 0 || denominator < 0 && numerator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
        this.denominator = denominator;
    }

    /**
     * Compares this fraction to another fraction
     * @param o the other Fraction object to be compared.
     * @return a negative int, zero, or a positive int. Positives mean objects are out of sort order.
     */
    @Override
    public int compareTo(Fraction o) {
        long thisNumerator = (long)this.numerator;
        long thisDenominator = (long)this.denominator;
        long otherNumerator = (long)o.numerator;
        long otherDenominator = (long)o.denominator;

        long a = thisNumerator * otherDenominator;
        long b = otherNumerator * thisDenominator;

        return Long.compare(a, b);
    }

    /**
     * Determines if two fraction objects are the same
     *
     * @param o the other object to be compared.
     * @return a boolean true is both objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        simplify(); // simplify fraction A
        fraction.simplify(); // simplify fraction B
        return numerator == fraction.numerator && denominator == fraction.denominator;
    }

    /**
     * This method is supported for the benefit of hash tables such as HashMap and HashSet.
     *
     * @return an integer representing a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    /**
     * Calculates the greatest common divisor (GCD) of two integers.
     *
     * @param a the first integer
     * @param b the second integer
     * @return the greatest common divisor of a and b
     */
    public static int gcd(int a, int b) {
        if (b == 0) {
            return Math.abs(a);
        }
        return gcd(b, a % b);
    }

    /**
     * Calculates the least common multiple (LCM) of two integers.
     *
     * @param a the first integer
     * @param b the second integer
     * @return the least common multiple of a and b
     */
    public static int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return a * (b / gcd(a, b));
    }

    /**
     * Simplifies this fraction to its lowest terms by dividing the numerator
     * and denominator by their greatest common divisor.
     */
    public void simplify() {
        if (numerator == 0) {
            denominator = 1;
            return;
        }
        int divisor = gcd(numerator, denominator);
        numerator /= divisor;
        denominator /= divisor;
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
    }

    /**
     * Converts this fraction to a mixed number string representation (e.g., "1 2/3").
     * If the fraction is a proper fraction, it returns the fraction itself.
     *
     * @return a string representation of the fraction as a mixed number
     */
    public String toMixedNumberString() {
        String result = "";
        this.simplify();

        if (denominator == 0) {
            throw new ArithmeticException("Denominator cannot be zero.");
        }
        if (numerator == 0) {
            result += "0";
        } else if (Math.abs(numerator) < Math.abs(denominator)) {
            result += numerator + "/" + denominator;
        } else if (Math.abs(numerator) >= Math.abs(denominator)) {
            int wholeNumber = numerator / denominator;
            int remainder = Math.abs(numerator % denominator);
            if (remainder == 0) {
                result += wholeNumber;
            } else {
                result += wholeNumber + " " + remainder + "/" + denominator;
            }
        } else {
            result += "0";
        }
        return result;
    }

    /**
     * Adds another fraction to this fraction.
     *
     * @param other the fraction to add
     * @return a new Fraction object representing the sum
     */
    public Fraction add(Fraction other) {
        int newNumerator = this.numerator * other.denominator + this.denominator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        Fraction result = new Fraction(newNumerator, newDenominator);
        result.simplify();
        return result;
    }

    /**
     * Subtracts another fraction from this fraction.
     *
     * @param other the fraction to subtract
     * @return a new Fraction object representing the difference
     */
    public Fraction subtract(Fraction other) {
        int newNumerator = this.numerator * other.denominator - this.denominator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        Fraction result = new Fraction(newNumerator, newDenominator);
        result.simplify();
        return result;
    }

    /**
     * Multiplies this fraction by another fraction.
     *
     * @param other the fraction to multiply by
     * @return a new Fraction object representing the product
     */
    public Fraction multiply(Fraction other) {
        int resultNumerator = this.numerator * other.numerator;
        int resultDenominator = this.denominator * other.denominator;
        Fraction result = new Fraction(resultNumerator, resultDenominator);
        result.simplify();
        return result;
    }

    /**
     * Divides this fraction by another fraction.
     *
     * @param otherFraction the fraction to divide by (the divisor)
     * @return a new Fraction object representing the quotient
     * @throws IllegalArgumentException if the divisor is zero
     */
    public Fraction divide(Fraction otherFraction){
        Fraction newFraction = null;
        try {
            int newNumerator = numerator * otherFraction.getDenominator();
            int newDenominator = denominator * otherFraction.getNumerator();
            newFraction = new Fraction(newNumerator,newDenominator);
            newFraction.simplify();
        } catch (ArithmeticException e) {
            throw e;
        }
        return newFraction;
    }

}
