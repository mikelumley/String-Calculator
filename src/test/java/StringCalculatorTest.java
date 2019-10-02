import org.junit.Test;
import static org.junit.Assert.*;

public class StringCalculatorTest {

    // Step 2
    @Test
    public void givenString1WithCommas_checkAdd_returns1() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("1");
        assertEquals(1.0, result, 0.0);
    }

    @Test
    public void givenString3WithCommas_checkAdd_returns3() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("3");
        assertEquals(3.0, result, 0.0);
    }

    // Step 3
    @Test
    public void givenString12_checkAdd_returns3() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("1,2");
        assertEquals(3.0, result, 0.0);
    }

    @Test
    public void givenString35_checkAdd_returns8() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("3,5");
        assertEquals(8.0, result, 0.0);
    }

    // Step 4
    @Test
    public void givenString123WithCommas_checkAdd_returns6() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("1,2,3");
        assertEquals(6.0, result, 0.0);
    }

    @Test
    public void givenString3539WithCommas_checkAdd_returns20() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("3,5,3,9");
        assertEquals(20.0, result, 0.0);
    }

    // Step 5
    @Test
    public void givenString123WithCommasAndNewline_checkAdd_returns6() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("1,2\n3");
        assertEquals(6.0, result, 0.0);
    }

    @Test
    public void givenString3539WithCommasAndNewline_checkAdd_returns20() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("3\n5\n3,9");
        assertEquals(20.0, result, 0.0);
    }

    @Test(expected = InvalidEquationStringException.class)
    public void givenString1WithDoubleDelimiter_checkAdd_throwsInvalidEquationStringException() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("1,\n");
    }

    @Test
    public void givenStringWithConsecutiveDelimiter_checkValidate_returnsFalse() {
        boolean result = StringCalculator.hasConsecutiveDelimiters("1,\n");
        assertTrue(result);
    }

    // Step 6
    @Test
    public void givenString123WithCustomDelimiter_checkAdd_returns6() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("//[;]\n1;2");
        assertEquals(3.0, result, 0.0);
    }

    @Test
    public void givenStringWithCustomDelimiterLine_checkGetDelimiter_returnsCustomDelimiter() {
        String result = StringCalculator.getDelimiter("//[;]\n");
        assertEquals("\\Q;\\E", result);
    }

    @Test
    public void givenStringWithIncorrectCustomDelimiterLine_checkGetDelimiter_returnsDefaultDelimiters() {
        String result = StringCalculator.getDelimiter("/;\n");
        assertEquals(",|\n", result);
    }

    // Step 7
    @Test
    public void givenString1WithNegativeNumbers_checkAdd_throwsNegativeArraySizeException() throws InvalidEquationStringException {
        try {
            double result = StringCalculator.add("-1,2,-3");
        } catch (NegativeNumbersNotAllowedException e) {
            assertEquals(new NegativeNumbersNotAllowedException("-1, -3").toString(), e.toString());
        }
    }

    // Step 8
    @Test
    public void givenString100010012WithCommas_checkAdd_returns2() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("1000,1001,2");
        assertEquals(2.0, result, 0.0);
    }

    // Step 9
    @Test
    public void givenStringWithCustomDelimiterOfAnyLength_checkGetDelimiter_returnsCustomDelimiter() {
        String result = StringCalculator.getDelimiter("//[***]\n");
        assertEquals("\\Q***\\E", result);
    }

    @Test
    public void givenString123WithCustomDelimiterLength3_checkAdd_returns6() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("//[***]\n1***2***3");
        assertEquals(6.0, result, 0.0);
    }

    // Step 10
    @Test
    public void givenStringWithMultipleCustomDelimiters_checkGetDelimiter_returnsCustomDelimiters() {
        String result = StringCalculator.getDelimiter("//[*][%]\n");
        assertEquals("\\Q*\\E|\\Q%\\E", result);
    }

    @Test
    public void givenString123WithCustomDelimiters_checkAdd_returns6() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("//[*][%]\n1*2%3");
        assertEquals(6.0, result, 0.0);
    }

    // Step 11
    @Test
    public void givenString1234WithCustomMultiCharDelimiters_checkAdd_returns10() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("//[***][#][%]\n1***2#3%4");
        assertEquals(10.0, result, 0.0);
    }

    // Step 12
    @Test
    public void givenString1123WithCustomMultiCharDelimitersWithNumbers_checkAdd_returns6() throws InvalidEquationStringException, NegativeNumbersNotAllowedException {
        double result = StringCalculator.add("//[*1*][%]\n1*1*2%3");
        assertEquals(6.0, result, 0.0);
    }
}
