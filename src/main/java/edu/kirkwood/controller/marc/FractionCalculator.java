package edu.kirkwood.controller.marc;

import edu.kirkwood.model.marc.Fraction;

import static edu.kirkwood.view.Messages.fractionGoodbye;
import static edu.kirkwood.view.Messages.fractionGreet;
import static edu.kirkwood.view.UIUtility.displayError;
import static edu.kirkwood.view.UIUtility.pressEnterToContinue;
import static edu.kirkwood.view.UserInput.getString;

public class FractionCalculator {
    public static final String INVALID_FRACTION_MESSAGE = "Invalid format. Ensure operator (+, -, *, /) has a space on both sides.";
    public static final String INVALID_SECOND_FRACTION_MESSAGE = "Missing the second fraction";
    public static final String INVALID_FIRST_FRACTION_MESSAGE = "Missing the first fraction";
    public static final String INVALID_FRACTION = "Invalid fraction format";


    public static void start() {
        fractionGreet();
        while(true) {
            // Get user input
            String value = getString("Enter your equation (or 'q' to quit)");
            if (value.equalsIgnoreCase("q") || value.equalsIgnoreCase("quit")) {
                break;
            }
            // Validate the input
            String[] parts = null;
            try {
                parts = splitCalculation(value);
            } catch(IllegalArgumentException e) {
               displayError(e.getMessage());
               continue;
            }
            // Parse the fraction strings into Fraction objects
            String fractionStr1 = parts[0];
            String operator = parts[1];
            String fractionStr2 = parts[2];
            Fraction f1 = null;
            Fraction f2 = null;
            try {
                f1 = parseFraction(fractionStr1);
                f2 = parseFraction(fractionStr2);
            } catch(Exception e) {
                displayError(e.getMessage());
                continue;
            }
            // Perform mathematical operation
            Fraction result = null;
            // Use .equals when checking equality of Strings in Java
            if(operator.equals("+")) {
                result = f1.add(f2);
            } else if(operator.equals("-")) {
                result = f1.subtract(f2);
            } else if(operator.equals("*")) {
                result = f1.multiply(f2);
            } else if(operator.equals("/")) {
                try {
                    result = f1.divide(f2);
                } catch (ArithmeticException e) {
                    displayError(e.getMessage());
                    continue;
                }
            }
            // Display output
            System.out.printf("Result: %s %s %s = %s%n%n",
                    f1.toMixedNumberString(), operator,
                    f2.toMixedNumberString(), result.toMixedNumberString());
        }
        fractionGoodbye();
        pressEnterToContinue();
    }


    /**
     * Splits the user input string into three parts: first fraction, operator, and second fraction.
     *
     * @param input the raw input string from the user.
     * @return a String array of size 3.
     * @throws IllegalArgumentException if the input format or operator is invalid.
     */
    public static String[] splitCalculation(String input) throws IllegalArgumentException {
        // Validate the mathematical operator
        String operator = "";
        int operatorIndex = -1;
        if(input.contains(" + ")) {
            operator = "+";
            operatorIndex = input.indexOf(" + "); // Find the String index of the space before the operator
        } else if(input.contains(" - ")) {
            operator = "-";
            operatorIndex = input.indexOf(" - "); // Find the String index of the space before the operator
        } else if(input.contains(" * ")) {
            operator = "*";
            operatorIndex = input.indexOf(" * "); // Find the String index of the space before the operator
        } else if(input.contains(" / ")) {
            operator = "/";
            operatorIndex = input.indexOf(" / "); // Find the String index of the space before the operator
        }
        // What should I do if the operator is still "" and the operatorIndex is -1?????
        if(operator.equals("") && operatorIndex == -1) {
            throw new IllegalArgumentException(INVALID_FRACTION_MESSAGE);
        }
        // We have a valid mathematical operator
        // Get the first fraction
        String fractionStr1 = input.substring(0, operatorIndex).trim();
        // Does the first fraction string exist? What if it was an empty string?
        if(fractionStr1.isEmpty()) {
            throw new IllegalArgumentException(INVALID_FIRST_FRACTION_MESSAGE);
        }
        // Get the second fraction, start where the mathematical operator stopped
        String fractionStr2 = input.substring(operatorIndex + 3).trim();
        // Does the second fraction string exist? What if it was an empty string?
        if(fractionStr2.isEmpty()) {
            throw new IllegalArgumentException(INVALID_SECOND_FRACTION_MESSAGE);
        }
        // Celebrate, we have all three parts!
        return new String[]{fractionStr1, operator, fractionStr2};
    }

    /**
     * Parse a string into a Fraction objecct. Handles whole numbers, proper and improper fractions, and mixed numbers
     * @param str The string input to parse
     * @return a Fraction representing the parsed string
     * @throws NumberFormatException if the numerator or denominator are not valid integers
     * @throws IllegalArgumentException if the fraction format is not valid
     */
//    public static Fraction parseFraction(String str) throws NumberFormatException, IllegalArgumentException {
//        if(str.contains(" ")) { // Mixed number fraction
//            String[] parts = str.split(" ", 2); // 1 1/2 => {"1", "1/2"}
//            int whole = 0;
//            try {
//                whole = Integer.parseInt(parts[0]);
//            } catch(NumberFormatException e) {
//                throw new NumberFormatException("Invalid mixed number format: '" + str + "' (the correct format is '1 2/3' or '-2 1/4')");
//            }
//            String[] parts2 = parts[1].split("/", 2); // "1/2" => {"1", "2"}
//            // Do not continue if any part of the fraction is missing.
//            if(parts2.length != 2 || parts2[0].isEmpty() || parts[1].isEmpty()) {
//                throw new NumberFormatException("Invalid mixed number format");
//            }
//
//            int numerator = 0;
//            try {
//                numerator = Integer.parseInt(parts2[0]);
//            } catch(NumberFormatException e) {
//                throw new NumberFormatException("Invalid numerator");
//            }
//
//
//            int denominator = 0;
//            try {
//                denominator = Integer.parseInt(parts2[1]);
//            } catch(NumberFormatException e) {
//                throw new NumberFormatException("Invalid denominator");
//            }
//            // No validation errors
//            if(whole >= 0) { // Calculates positive fraction
//                numerator = whole * denominator + numerator;
//            } else { // Calculates negative fraction
//                numerator = whole * denominator - numerator;
//            }
//            Fraction result = new Fraction(numerator, denominator);
//            return result;
//        } else if(str.contains("/")) { // proper and improper fractions 3/4 or 7/3
//            String[] parts = str.split("/");
//            int numerator = 0;
//            try {
//                numerator = Integer.parseInt(parts[0]);
//            } catch(NumberFormatException e) {
//                throw new NumberFormatException("Invalid numerator");
//            }
//            int denominator = 0;
//            try {
//                denominator = Integer.parseInt(parts[1]);
//            } catch(NumberFormatException e) {
//                throw new NumberFormatException("Invalid denominator");
//            }
//            return new Fraction(numerator, denominator);
//        } else { // Implementation for whole numbers
//            int whole = 0;
//            try {
//                whole =  Integer.parseInt(str);
//            } catch(NumberFormatException e) {
//                throw new NumberFormatException("Invalid whole number");
//            }
//            return new Fraction(whole, 1);
//        }
//    }

    /**
     * Parse a string into a Fraction objecct. Handles whole numbers, proper and improper fractions, and mixed numbers
     * @param str The string input to parse
     * @return a Fraction representing the parsed string
     * @throws NumberFormatException if the numerator or denominator are not valid integers
     * @throws IllegalArgumentException if the fraction format is not valid
     */
    // Implemented by GitHub Copilot with the following prompt:
    // Implement the `parseFraction` method in the `FractionCalculator` class to get the related unit tests in the `FractionCalculatorTest` class to pass.
    // I used this as a follow-up prompt
    // I'm new to Java programming. Can you help me better understand the implementation of the `parseFraction` method.
    public static Fraction parseFraction(String str) throws NumberFormatException, IllegalArgumentException {
        if(str.contains(" ")) {
            String[] parts = str.split(" ", 2);
            int whole;
            try {
                whole = Integer.parseInt(parts[0]);
            } catch(NumberFormatException e) {
                throw new NumberFormatException("Invalid mixed number format");
            }

            if(parts.length != 2) {
                throw new IllegalArgumentException("Invalid mixed number format");
            }

            String[] fractionParts = parts[1].split("/", -1);
            if(fractionParts.length != 2) {
                throw new IllegalArgumentException("Invalid mixed number format");
            }

            int numerator;
            try {
                numerator = Integer.parseInt(fractionParts[0]);
            } catch(NumberFormatException e) {
                throw new NumberFormatException("Invalid numerator");
            }

            int denominator;
            try {
                denominator = Integer.parseInt(fractionParts[1]);
            } catch(NumberFormatException e) {
                throw new NumberFormatException("Invalid denominator");
            }

            boolean isNegative = whole < 0 || numerator < 0;
            numerator = Math.abs(whole) * denominator + Math.abs(numerator);

            if (isNegative) {
                numerator = -numerator;
            }

            return new Fraction(numerator, denominator);
        }

        if(str.contains("/")) {
            String[] parts = str.split("/", -1);
            if(parts.length != 2) {
                throw new IllegalArgumentException("Invalid fraction format");
            }

            int numerator;
            try {
                numerator = Integer.parseInt(parts[0]);
            } catch(NumberFormatException e) {
                throw new NumberFormatException("Invalid numerator");
            }

            int denominator;
            try {
                denominator = Integer.parseInt(parts[1]);
            } catch(NumberFormatException e) {
                throw new NumberFormatException("Invalid denominator");
            }
            return new Fraction(numerator, denominator);
        }

        int whole;
        try {
            whole = Integer.parseInt(str);
        } catch(NumberFormatException e) {
            throw new NumberFormatException("Invalid whole number");
        }
        return new Fraction(whole, 1);
    }
}
