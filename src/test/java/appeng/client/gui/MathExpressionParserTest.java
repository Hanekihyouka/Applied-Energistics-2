package appeng.client.gui;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathExpressionParserTest {

    @ParameterizedTest
    @CsvSource(value = {
            "1 + 2|3",
            "3 *4 |12",
            "1 + 2 * 3 |7",
            "1 - 6|-5",
            "1/3|0.333333",
            "23.4 + 0.6|24",
            "1 - -4|5",
            "1 + 4*3*2|25",
            "1/0|failed",
            "1/(1 - 1)|failed",
            "3 + 2 * 4 - 1 /2|10.5",
            "1 + (2 * (2 * (1 + 1)))|9",
            "arkazkdhz|failed",
            "1 + 2 3 7 - 1|237", // whitespace is trimmed
            "2 + + 2|failed",
            "10e6|10000000",
            "-1 -1|-2",
            "- (1 + 1)|-2",
            "2 * -1|-2",
            "2 -2|0",
            "-  1|-1",
            "-1|-1",
            "- - - - - 5|failed", // not able to handle multiple negations, may fix in the future
            "-(-(-(-2)))|failed", // not able to handle multiple negations, may fix in the future
            "1 - -1|2",
            "1 + -(2|failed"
    }, delimiter = '|')
    void testMath(String expression, String expected) {
        DecimalFormat format = new DecimalFormat("#.######", DecimalFormatSymbols.getInstance(Locale.US));
        format.setParseBigDecimal(true);
        format.setNegativePrefix("-");

        double parsed = MathExpressionParser.parse(expression);
        if (Double.isNaN(parsed)) {
            assertEquals(expected, "failed");
        } else {
            assertEquals(expected, format.format(parsed));
        }
    }
}
