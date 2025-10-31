package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static lotto.global.exception.ErrorMessage.LOTTO_RESULT_DUPLICATE_ERROR;
import static lotto.global.exception.ErrorMessage.WINNING_NUMBERS_SIZE_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoWinningNumberTest {

    @Test
    @DisplayName("로또 결과는 중복되지 않는 당첨번호 6개와 1개의 보너스 번호로 이루어진다.")
    public void of_success() throws Exception {
        // given
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int bonusNumber = 7;

        // when
        LottoWinningNumber lottoWinningNumber = LottoWinningNumber.of(winningNumbers, bonusNumber);

        // then
        assertThat(lottoWinningNumber.getWinningNumbers())
                .containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6);
        assertThat(lottoWinningNumber.getBonusNumber())
                .isEqualTo(7);
    }

    @Test
    @DisplayName("당첨번호가 6개 보다 많으면 예외가 발생한다..")
    public void of_fail1() throws Exception {
        // given
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        int bonusNumber = 10;

        // when // then
        assertThatThrownBy(() -> LottoWinningNumber.of(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(WINNING_NUMBERS_SIZE_ERROR.getMessage());
    }

    @ParameterizedTest
    @DisplayName("당첨번호가 6개 및 보너스 번호 1개가 중복되면 예외가 발생한다..")
    @MethodSource("of_fail2_parameters")
    public void of_fail2(List<Integer> winningNumbers, int bonusNumber) throws Exception {

        // when // then
        assertThatThrownBy(() -> LottoWinningNumber.of(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(LOTTO_RESULT_DUPLICATE_ERROR.getMessage());
    }

    static Stream<Arguments> of_fail2_parameters() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 3, 4, 5, 5), 7), // 당첨번호 내에서 중복되는 경우
                Arguments.of(List.of(1, 2, 3, 4, 5, 6), 6) // 당첨번호와 보너스 번호가 중복되는 경우
        );
    }



}