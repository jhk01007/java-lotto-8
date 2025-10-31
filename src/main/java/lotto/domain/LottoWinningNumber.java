package lotto.domain;

import java.util.HashSet;
import java.util.List;

import static lotto.global.exception.ErrorMessage.*;

public class LottoWinningNumber {

    private final List<Integer> winningNumbers;
    private final int bonusNumber;

    private LottoWinningNumber(List<Integer> winningNumbers, int bonusNumber) {
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }
    
    public static LottoWinningNumber of(List<Integer> winningNumbers, int bonusNumber) {
        validate(winningNumbers, bonusNumber);
        return new LottoWinningNumber(winningNumbers, bonusNumber);
    }

    public List<Integer> getWinningNumbers() {
        return List.copyOf(winningNumbers);
    }

    public int getBonusNumber() {
        return bonusNumber;
    }

    private static void validate(List<Integer> winningNumbers, int bonusNumber) {
        validateWinningNumbersSize(winningNumbers);
        validateLottoResultDuplicate(winningNumbers, bonusNumber);
    }

    private static void validateWinningNumbersSize(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException(WINNING_NUMBERS_SIZE_ERROR.getMessage());
        }
    }

    private static void validateLottoResultDuplicate(List<Integer> numbers, int bonusNumber) {
        HashSet<Integer> numbersSet = new HashSet<>(numbers);
        numbersSet.add(bonusNumber);

        if(numbersSet.size() != numbers.size() + 1) {
            throw new IllegalArgumentException(LOTTO_RESULT_DUPLICATE_ERROR.getMessage());
        }
    }
}
