package lotto.view;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static lotto.global.exception.ErrorMessage.*;

public class InputParser {

    private static final String WINNING_NUMBER_DELIMITER = ",";
    public static List<Integer> parseWinningNumbers(String winningNumbers) {
        if (winningNumbers == null || winningNumbers.isBlank()) {
            throw new IllegalArgumentException(EMPTY_INPUT_ERROR.getMessage());
        }

        return Arrays.stream(winningNumbers.split(WINNING_NUMBER_DELIMITER))
                .map(String::trim)
                .map(InputParser::parseInt)
                .collect(Collectors.toList());
    }

    public static int parseInt(String value) {
        try {
            BigInteger big = new BigInteger(value);
            // 언더플로우, 오버플로우 검증
            if (big.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0 ||
                    big.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
                throw new IllegalArgumentException(NUMBER_OVERFLOW_ERROR.getMessage());
            }
            return big.intValue();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_NUMBER_FORMAT_ERROR.getMessage());
        }
    }
}