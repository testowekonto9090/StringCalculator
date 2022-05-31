package string_calculator;

import com.example.string_calculator.exception.NegativeNumException;
import com.example.string_calculator.service.StringCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestStringCalculator {

    @Test
    public void testTask1() {

        StringCalculator calculator = new StringCalculator();

        assertEquals(0, calculator.add(""));
        assertEquals(1, calculator.add("1"));
        assertEquals(3, calculator.add("1,2"));
    }

    @Test
    public void testTask2() {

        StringCalculator calculator = new StringCalculator();

        assertEquals(1807, calculator.add("1,543,234,222,434,42,5,4,322"));
        assertEquals(45, calculator.add("1,2,3,4,5,6,7,8,9"));

    }

    @Test
    public void testTask3() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(6, calculator.add("1\n2,3"));
        assertEquals(1, calculator.add("1,\n"));
    }

    @Test
    public void testTask4() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(3, calculator.add("//[;]\n1;2"));

        assertEquals(6, calculator.add("1\n2,3"));
        assertEquals(1807, calculator.add("1,543,234,222,434,42,5,4,322"));
        assertEquals(0, calculator.add(""));
        assertEquals(1, calculator.add("1"));
        assertEquals(3, calculator.add("1,2"));
        assertEquals(1, calculator.add("1,\n"));
    }

    @Test
    public void testTask5() {
        StringCalculator calculator = new StringCalculator();

        NegativeNumException negativeNumException = assertThrows(NegativeNumException.class, () -> calculator.add("-1"));
        assertEquals("Negatives not allowed: -1", negativeNumException.getMessage());

        negativeNumException = assertThrows(NegativeNumException.class, () -> calculator.add("-1,2,5,-4"));
        assertEquals("Negatives not allowed: -1;-4", negativeNumException.getMessage());

        negativeNumException = assertThrows(NegativeNumException.class, () -> calculator.add("//[;]\n1;2;-4;-5"));
        assertEquals("Negatives not allowed: -4;-5", negativeNumException.getMessage());
    }

    @Test
    public void testTask6() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(2, calculator.add("2,1001"));
        assertEquals(0, calculator.add("1001"));
    }

    @Test
    public void testTask7() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(1, calculator.add("//[abc]\n1abc1001"));
        assertEquals(6, calculator.add("//[***]\n1***2***3"));
    }

    @Test
    public void testTask8() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(6, calculator.add("//[*][%]\n1*2%3"));
        assertEquals(16, calculator.add("//[*][%][&][{][}]\n1*2%3&4{5}1"));
    }
}
