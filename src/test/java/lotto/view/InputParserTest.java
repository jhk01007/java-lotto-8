package lotto.view;

import lotto.global.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputParserTest {

    @Test
    @DisplayName(",로 구분된 당첨 번호를 리스트로 파싱한다.")
    public void parseWinningNumbers_success() throws Exception {
        // given
        String winningNumber = "1,2,3,4,5,6";

        // when
        List<Integer> winningNumbers = InputParser.parseWinningNumbers(winningNumber);

        // then
        assertThat(winningNumbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    @DisplayName("빈 입력이 들어오면 에러가 발생한다.")
    public void parseWinningNumbers_fail1() throws Exception {
        // given
        String winningNumber = "";

        // when then
        assertThatThrownBy(() -> InputParser.parseWinningNumbers(winningNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.EMPTY_INPUT_ERROR.getMessage());
    }

    @Test
    @DisplayName("문자열 형태의 정수를 정수형으로 변환한다.")
    public void parseInt_success() throws Exception {
        // given
        String value = "7";

        // when
        int parsedInt = InputParser.parseInt(value);

        // then
        assertThat(parsedInt).isEqualTo(7);
    }

    @ParameterizedTest
    @DisplayName("숫자가 너무 작거나 크면 예외가 발생한다.")
    @ValueSource(strings = {"-2147483649", "2147483648"})
    public void parseInt_fail1(String value) throws Exception {
        // when then
        assertThatThrownBy(() -> InputParser.parseInt(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NUMBER_OVERFLOW_ERROR.getMessage());
    }

    @Test
    @DisplayName("숫자 형태가 아니면 예외가 발생한다.")
    public void parseInt_fail2() throws Exception {
        // given
        String value = "a";

        // when then
        assertThatThrownBy(() -> InputParser.parseInt(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NUMBER_FORMAT_ERROR.getMessage());
    }
}

