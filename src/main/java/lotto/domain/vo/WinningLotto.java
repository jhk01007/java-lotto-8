package lotto.domain.vo;


import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static lotto.global.constants.LottoConstants.LOTTO_MAX_NUMBER;
import static lotto.global.constants.LottoConstants.LOTTO_MIN_NUMBER;
import static lotto.global.exception.ErrorMessage.*;

/**
 * 당첨 로또
 * Lotto 클래스가 있는데 WinningLotto 를 만든 이유
 * 1. 예외 메시지를 분리해서 가져갈 수 있음
 * 2. 지금 당장은 두 개의 비즈니스 규칙이 같지만 추후 요구사항에 따라 달라질 수도 있음
 */
public final class WinningLotto {
    private final List<Integer> numbers;

    private WinningLotto(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public static WinningLotto from(List<Integer> numbers) {
        validate(numbers);
        return new WinningLotto(numbers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WinningLotto lotto = (WinningLotto) o;
        return Objects.equals(numbers, lotto.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numbers);
    }

    public List<Integer> getNumbers() {
        return List.copyOf(this.numbers);
    }

    private static void validate(List<Integer> numbers) {
        validateNumbersSize(numbers);
        validateNumbersDuplicate(numbers);
        validateNumbersRange(numbers);
    }

    private static void validateNumbersSize(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException(WINNING_NUMBERS_SIZE_ERROR.getMessage());
        }
    }

    private static void validateNumbersDuplicate(List<Integer> numbers) {
        HashSet<Integer> numbersSet = new HashSet<>(numbers);
        if(numbers.size() != numbersSet.size()) {
            throw new IllegalArgumentException(WINNING_NUMBERS_DUPLICATE_ERROR.getMessage());
        }
    }

    private static void validateNumbersRange(List<Integer> numbers) {
        boolean isOutOfRange = numbers.stream()
                .anyMatch(number -> LOTTO_MIN_NUMBER > number || LOTTO_MAX_NUMBER < number);
        if(isOutOfRange) {
            throw new IllegalArgumentException(WINNING_NUMBERS_RANGE_ERROR.getMessage());
        }
    }
}
