package edu.kirkwood.model.Masean;

import java.util.*;

import static edu.kirkwood.view.UserInput.getDouble;
import static edu.kirkwood.view.UserInput.getInt;

public class GradePointAverage implements Comparable<GradePointAverage> {
    private int classes;
//    private int numberGrades;
    private String[] letterGrades;
    public static HashMap<String, Double> numberGrades;

    static {
        numberGrades = new HashMap<>();
        numberGrades.put("A+", 4.3);
        numberGrades.put("A", 4.0);
        numberGrades.put("A-", 3.7);
        numberGrades.put("B+", 3.3);
        numberGrades.put("B", 3.0);
        numberGrades.put("B-", 2.7);
        numberGrades.put("C+", 2.3);
        numberGrades.put("C", 2.0);
        numberGrades.put("C-", 1.7);
        numberGrades.put("D+", 1.3);
        numberGrades.put("D", 1.0);
        numberGrades.put("F", 0.0);
    }

    /**
     * Default constructor.
     * Initializes a list with the number of classes, number grades, and letter grades to the lowest value
     */
    public GradePointAverage() {
        this.classes = 4;
//        this.numberGrades = new HashMap<>();
        this.letterGrades = new String[]{"A", "B", "C"};
    }

    /**
     * Constructs a list containing the amount of classes a user has taken, the number grade for each class, and a letter grade for each class.
     *
     * @param classes      the number of classes the user has taken
     * @param numberGrades The number grades for the classes
     * @param letterGrades the letter grades for the classes
     */
    public GradePointAverage(int classes, int numberGrades, String[] letterGrades) {
        setClasses(classes);
//        setNumberGrades(numberGrades);
        setLetterGrades(letterGrades);
    }

    /**
     * Gets the number of classes
     *
     * @return the classes
     */
    public int getClasses() {
        return classes;
    }

    /**
     * Sets number of classes.
     *
     * @param classes the new number of classes.
     */
    public void setClasses(int classes) {
        this.classes = classes;
    }

    /**
     * Gets the number grades
     *
     * @return the number grades
     */
    public List<Double> getNumberGrades() {
        List<Double> numberGrades = new ArrayList<>();
        for (String grade : letterGrades) {
            switch (grade.toUpperCase()) {
                case "A":
                    numberGrades.add(4.0);
                    break;
                case "B":
                    numberGrades.add(3.0);
                    break;
                case "C":
                    numberGrades.add(2.0);
                    break;
                case "D":
                    numberGrades.add(1.0);
                    break;
                case "F":
                    numberGrades.add(0.0);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid letter grade: " + grade);
            }
        }
        return numberGrades;
    }

    /**
     * Sets the number grades.
     *
     * @param numberGrades the new number grades.
     */
    public void setNumberGrades(HashMap<String, Double> numberGrades) {
        this.numberGrades = numberGrades;
    }



    /**
     * Gets the letter grades
     *
     * @return the letter grades
     */
    public String[] getLetterGrades() {
        return letterGrades;
    }

    public void setLetterGrades(String[] letterGrades) {
        this.letterGrades = letterGrades;
    }

    /**
     * Returns a message that contains the number of classes, the number grades, and the letter grades.
     *
     * @return a string of the number of classes, the number grades, and the letter grades.
     */
    @Override
    public String toString() {
        return
                "Classes " + classes + ", numberGrade " + getNumberGrades() +
                        ", letterGrade " + Arrays.toString(letterGrades);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GradePointAverage that = (GradePointAverage) o;
        return classes == that.classes == Objects.deepEquals(letterGrades, that.letterGrades);
    }



    /**
     * Converts to inputted letter grades into a number grade
     */
    public double toNumberGrade() {

        for (int i = 0; i < letterGrades.length; i++) {
            switch (letterGrades[i].toUpperCase()) {
                case "A":
                    return 4.0;
                case "B":
                    return 3.0;
                case "C":
                    return 2.0;
                case "D":
                    return 1.0;
                case "F":
                    return 0.0;
                default:
                    throw new IllegalArgumentException("Invalid letter grade: " + letterGrades[i]);
            }
        }
        return numberGrades.get(letterGrades[0].toUpperCase());
    }

    @Override
    public int compareTo(GradePointAverage other) {
        double thisGPA = getNumberGrades().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double otherGPA = other.getNumberGrades().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        int gpaComparison = Double.compare(thisGPA, otherGPA);
        if (gpaComparison != 0) {
            return gpaComparison;
        }
        return Integer.compare(classes, other.classes);
    }

    /**
     * Calculates the grade point average using inputted values.
     */
    public static double calculateGPA(double[] grades, int[] credits) {
        if (grades.length != credits.length) {
            throw new IllegalArgumentException("Grades and credits arrays must have the same length.");
        }
        double gradePoints = 0;
        int totalCredits = 0;
        for (int i = 0; i < grades.length; i++) {
            gradePoints += grades[i] * credits[i];
            totalCredits += credits[i];
        }

        return gradePoints / totalCredits;
    }
        /**
         * Prompts the user for the remaining amount of credits that they will need to complete their degree.
         */
        public static int promptForRemainingCredits() {
            int remainingCredits = 0;
            while (true) {
                remainingCredits = getInt("Enter the remaining credits needed: ");
                if (remainingCredits < 0) {
                    System.out.println("Please enter a positive number.");
                } else {
                    break;
                }
            }

            return remainingCredits;
        }

        /**
         * Prompts the user for the grade point average that they are hoping for.
         */
        public static double promptForGoalGPA() {

            double goalGPA = 0;

            while (true) {
                goalGPA = getDouble("Enter your goal GPA: (0.0 - 4.3)");
                if (goalGPA < 0.0 || goalGPA > 4.3) {
                    System.out.println("Please enter a valid GPA between 0.0 and 4.3.");
                } else {
                    break;
                }
            }
            return goalGPA;


        }
}