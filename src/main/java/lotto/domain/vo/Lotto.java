package lotto.domain.vo;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static lotto.global.exception.ErrorMessage.*;

// TODO: 불변처리
public class Lotto {
    private final List<Integer> numbers;

    private Lotto(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public static Lotto from(List<Integer> numbers) {
        validate(numbers);
        return new Lotto(sortNumbersAscending(numbers)); // 오름차순으로 정렬해서 저장
    }

    public List<Integer> getNumbers() {
        return List.copyOf(this.numbers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lotto lotto = (Lotto) o;
        return Objects.equals(numbers, lotto.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numbers);
    }

    private static List<Integer> sortNumbersAscending(List<Integer> numbers) {
        return numbers.stream()
                .sorted()
                .toList();
    }

    private static void validate(List<Integer> numbers) {
        validateNumbersSize(numbers);
        validateNumbersDuplicate(numbers);
        // TODO: 범위 검증 (1~45)
    }

    private static void validateNumbersSize(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException(LOTTO_NUMBERS_SIZE_ERROR.getMessage());
        }
    }

    private static void validateNumbersDuplicate(List<Integer> numbers) {
        HashSet<Integer> numbersSet = new HashSet<>(numbers);
        if(numbers.size() != numbersSet.size()) {
            throw new IllegalArgumentException(LOTTO_NUMBERS_DUPLICATE_ERROR.getMessage());
        }
    }
}
