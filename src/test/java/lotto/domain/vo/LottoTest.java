package lotto.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static lotto.global.exception.ErrorMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {

    @Test
    @DisplayName("로또 번호는 중복되지 않는 6개의 숫자로 이루어진다.")
    void from_success() {

        // given
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        // when
        Lotto lotto = Lotto.from(numbers);

        // then
        assertThat(lotto.getNumbers())
                .containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6);
    }

    @Test
    @DisplayName("로또 번호의 개수가 6개가 넘어가면 예외가 발생한다")
    void from_fail1() {

        // given
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7);

        // when then
        assertThatThrownBy(() -> Lotto.from(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(LOTTO_NUMBERS_SIZE_ERROR.getMessage());
    }

    @Test
    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    void from_fail2() {
        // given
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 5);

        // when then
        assertThatThrownBy(() -> Lotto.from(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(LOTTO_NUMBERS_DUPLICATE_ERROR.getMessage());
    }

    // TODO: 추가 기능 구현에 따른 테스트 코드 작성
}
