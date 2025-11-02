package lotto.view;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static lotto.global.exception.ErrorMessage.*;

public class InputValidator {

    public static List<Integer> parseAndValidateWinningNumbers(String winningNumbers) {
        if (winningNumbers == null || winningNumbers.isBlank()) {
            throw new IllegalArgumentException(MISSING_WINNING_NUMBERS_ERROR.getMessage());
        }

        return Arrays.stream(winningNumbers.split(","))
                .map(String::trim)
                .map(InputValidator::parseAndValidateInt)
                .collect(Collectors.toList());
    }

    public static int parseAndValidateInt(String value) {
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