package lotto.domain;

import lotto.domain.vo.BonusNumber;
import lotto.domain.vo.Lotto;
import lotto.domain.vo.WinningLotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static lotto.global.exception.ErrorMessage.LOTTO_RESULT_DUPLICATE_ERROR;
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
        LottoWinningNumber lottoWinningNumber = LottoWinningNumber.of(
                WinningLotto.from(winningNumbers), BonusNumber.from(bonusNumber));

        // then
        assertThat(lottoWinningNumber.getWinningNumber().getNumbers())
                .containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6);
        assertThat(lottoWinningNumber.getBonusNumber().getNumber())
                .isEqualTo(7);
    }

    @ParameterizedTest
    @DisplayName("당첨번호가 6개 및 보너스 번호 1개가 중복되면 예외가 발생한다.")
    @MethodSource("of_fail2_parameters")
    public void of_fail2(List<Integer> winningNumbers, int bonusNumber) throws Exception {

        // when // then
        assertThatThrownBy(() ->
                LottoWinningNumber.of(WinningLotto.from(winningNumbers), BonusNumber.from(bonusNumber)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(LOTTO_RESULT_DUPLICATE_ERROR.getMessage());
    }

    static Stream<Arguments> of_fail2_parameters() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 3, 4, 5, 6), 6) // 당첨번호와 보너스 번호가 중복되는 경우
        );
    }


}