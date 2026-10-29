package edu.kirkwood.model.Masean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GradePointAverageTest {

    private GradePointAverage g1;
    private GradePointAverage g2;

    @BeforeEach
    void setUp() {
        g1 = new GradePointAverage();
        g2 = new GradePointAverage(5, 0, new String[]{"A"});
    }

    @Test
    void getClasses() {
        assertEquals(4, g1.getClasses());
        assertEquals(5, g2.getClasses());
    }

    @Test
    void setClasses() {
        // Act
        g1.setClasses(6);
        // Assert
        assertEquals(6, g1.getClasses());
        assertEquals("Classes 6, numberGrade [4.0, 3.0, 2.0], letterGrade [A, B, C]", g1.toString());
    }

    @Test
    void getNumberGrades() {
        List<Double> expectedGrades = Arrays.asList(4.0, 3.0, 2.0);
        assertEquals(expectedGrades, g1.getNumberGrades());
    }

    @Test
    void getNumberGradesWithInvalidLetterGrades() {
        // Arrange
        g1.setLetterGrades(new String[]{"A", "B", "Z"});

        // Arrange
        String expectedError = "Invalid letter grade: Z";
        // Act
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> g1.getNumberGrades());
        String actualError = e.getMessage();
        // Assert
        assertTrue(actualError.contains(expectedError));
    }

    @Test
    @DisplayName("Tests if a lowercase letter is still converted to a number grade")
    void getNumberGradeLowerCaseLetter() {
        GradePointAverage gpa = new GradePointAverage(1, 0, new String[]{"c", "b", "d"});
        assertEquals(List.of(2.0, 3.0, 1.0), gpa.getNumberGrades());
    }

    @Test
    @DisplayName("Tests if the lack of letter grades makes the test fail")
    void emptyGetNumberGrades() {
        GradePointAverage gpa = new GradePointAverage(0, 0, new String[]{});
        assertEquals(List.of(), gpa.getNumberGrades());
    }

    @Test
    @DisplayName("If List has a null value it will throw an exception")
    void GetNullNumberGrades() {
        GradePointAverage gpa = new GradePointAverage(1, 0, new String[]{null});
        assertThrows(NullPointerException.class, () -> gpa.getNumberGrades());
    }

//    @Test
//    void setNumberGrades() {
//        // Act
//        g1.setNumberGrades(3.0);
//        // Assert
//        assertEquals(3.0, g1.getNumberGrades());
//        assertEquals("Classes 4, numberGrade 3.0, letterGrade [F, D, C, B]", g1.toString());
//    }

    @Test
    void getLetterGrades() {
        assertArrayEquals(new String[]{"A", "B", "C"}, g1.getLetterGrades());
        assertArrayEquals(new String[]{"A"}, g2.getLetterGrades());
    }

    @Test
    void setLetterGrades() {
        g1.setLetterGrades(new String[]{"C"});

        assertArrayEquals(new String[]{"C"}, g1.getLetterGrades());
        assertEquals("Classes 4, numberGrade [2.0], letterGrade [C]", g1.toString());
    }

    @Test
    @DisplayName("Convert letter grade A to the number grade")
    void toNumberGradeA() {
        GradePointAverage gpa = new GradePointAverage(1, 0, new String[]{"A"});
        assertEquals(4.0, gpa.toNumberGrade());
    }

    @Test
    @DisplayName("Convert letter grade B to the proper number grade")
    void toNumberGradeB() {
        GradePointAverage gpa = new GradePointAverage(1, 0, new String[]{"B"});
        assertEquals(3.0, gpa.toNumberGrade());
    }

    @Test
    @DisplayName("Convert letter grade C to the number grade")
    void toNumberGradeC() {
        GradePointAverage gpa = new GradePointAverage(1, 0, new String[]{"C"});
        assertEquals(2.0, gpa.toNumberGrade());
    }

    @Test
    @DisplayName("Convert letter grade D to the number grade")
    void toNumberGradeD() {
        GradePointAverage gpa = new GradePointAverage(1, 0, new String[]{"D"});
        assertEquals(1.0, gpa.toNumberGrade());
    }

    @Test
    @DisplayName("Convert letter grade F to the number grade")
    void toNumberGradeF() {
        GradePointAverage gpa = new GradePointAverage(1, 0, new String[]{"F"});
        assertEquals(0.0, gpa.toNumberGrade());
    }

    @Test
    @DisplayName("Tests if a lowercase letter is still converted to a number grade")
    void toNumberGradeLowerCaseLetter() {
        GradePointAverage gpa = new GradePointAverage(1, 0, new String[]{"c"});
        assertEquals(2.0, gpa.toNumberGrade());
    }

    @Test
    @DisplayName("Tests if an invalid letter grade throws an exception")
    void toNumberGradeInvalidLetter() {
        GradePointAverage gpa = new GradePointAverage(1, 0, new String[]{"Q"});
        assertThrows(IllegalArgumentException.class, () -> gpa.toNumberGrade());

    }

    @Test
    @DisplayName("Tests if equals method returns true for two objects with the same values")
    void equalsReturnsTrueIfValuesMatch() {
        GradePointAverage value1 = new GradePointAverage(3, 0, new String[]{"A", "B", "C"});
        GradePointAverage value2 = new GradePointAverage(3, 0, new String[]{"A", "B", "C"});
        assertEquals(value1, value2);
    }

    @Test
    @DisplayName("Tests if equals method returns false for two objects with different values")
    void equalsReturnsFalseIfValuesDoesNotMatch() {
        GradePointAverage value1 = new GradePointAverage(3, 0, new String[]{"A", "B", "C"});
        GradePointAverage value2 = new GradePointAverage(3, 0, new String[]{"A", "B", "D"});
        assertNotEquals(value1, value2);
    }

    @Test
    @DisplayName("Tests if compareTo method returns a negative value when the first object has a lower GPA than the second object")
    void compareToWhenGPAOneIsHigher() {
        GradePointAverage value1 = new GradePointAverage(3, 0, new String[]{"A", "A", "A"});
        GradePointAverage value2 = new GradePointAverage(3, 0, new String[]{"A", "B", "D"});
        assertTrue(value1.compareTo(value2) > 0);
    }

    @Test
    @DisplayName("Tests if compareTo method returns a positive value when the first object has a higher GPA than the second object")
    void compareToWhenGPAOneIsLower() {
        GradePointAverage value1 = new GradePointAverage(3, 0, new String[]{"F", "F", "F"});
        GradePointAverage value2 = new GradePointAverage(3, 0, new String[]{"A", "B", "D"});
        assertTrue(value1.compareTo(value2) < 0);
    }

    @Test
    @DisplayName("Tests if compareTo method returns zero when the first object has the same GPA as the second object")
    void compareToWhenGPAValuesAreEqual() {
        GradePointAverage value1 = new GradePointAverage(3, 0, new String[]{"F", "F", "F"});
        GradePointAverage value2 = new GradePointAverage(3, 0, new String[]{"F", "F", "F"});
        assertTrue(value1.compareTo(value2) == 0);
    }
}