import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    public static double add(String stringEquation) throws InvalidEquationStringException, NegativeNumbersNotAllowedException{
        if(StringCalculator.hasConsecutiveDelimiters(stringEquation)) {
            throw new InvalidEquationStringException();
        }

        double sum = 0.0;

        String selectDelimiterLineRegex = "//.*\n";
        String equationWithoutDelimiterLine = stringEquation.replaceFirst(selectDelimiterLineRegex, "");

        String delimiters = StringCalculator.getDelimiter(stringEquation);
        Pattern compiledSplitOnDelimiterRegex = Pattern.compile(delimiters);
        String[] numbersInEquation = compiledSplitOnDelimiterRegex.split(equationWithoutDelimiterLine);

        StringJoiner negativeNumberErrorMessage = new StringJoiner(", ");

        for(String numberAsString : numbersInEquation) {
            double number = Double.parseDouble(numberAsString);
            if(number < 0) {
                negativeNumberErrorMessage.add(numberAsString);
                continue;
            }
            if(number >= 1000) {
                continue;
            }
            sum += number;
        }

        boolean negativeNumbersInEquation = negativeNumberErrorMessage.length() > 0;
        if(negativeNumbersInEquation) {
            throw new NegativeNumbersNotAllowedException(negativeNumberErrorMessage.toString());
        }
        return sum;
    }

    public static String getDelimiter(String stringEquation) {
        String defaultDelimiters = ",|\n";

        final String selectDelimiterLineRegex = "(?<=//).+(?=\n)";
        Matcher selectCustomDelimiterLine = StringCalculator.getMatcher(selectDelimiterLineRegex, stringEquation);

        if(selectCustomDelimiterLine.find()) {
            String customDelimiterLine = selectCustomDelimiterLine.group();

            String selectDelimiterRegex = "(?<=\\[).+?(?=\\])";
            Matcher selectCustomDelimiter = StringCalculator.getMatcher(selectDelimiterRegex, customDelimiterLine);

            StringJoiner customDelimiters = new StringJoiner("|");
            while(selectCustomDelimiter.find()) {
                String delimiter = selectCustomDelimiter.group();
                // Need \Q and \E to escape the delimiter characters
                customDelimiters.add("\\Q" + delimiter + "\\E");
            }
            return customDelimiters.toString();
        }
        else {
            return defaultDelimiters;
        }
    }

    public static boolean hasConsecutiveDelimiters(String stringEquation) {
        String delimiters = StringCalculator.getDelimiter(stringEquation);
        String consecutiveDelimiterRegex = "(" + delimiters + "){2,}";

        Matcher consecutiveDelimiter = StringCalculator.getMatcher(consecutiveDelimiterRegex, stringEquation);
        return consecutiveDelimiter.find();
    }

    private static Matcher getMatcher(String regex, String stringToSearchIn) {
        Pattern compiledRegex = Pattern.compile(regex);
        return compiledRegex.matcher(stringToSearchIn);
    }
}
