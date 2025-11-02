package lotto.domain.vo;

import lotto.global.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BonusNumberTest {

    @ParameterizedTest
    @DisplayName("보너스 번호가 범위(1~45)에서 벗어나면 예외가 발생한다.")
    @ValueSource(ints = {0, 46})
    public void from_fail(int value) throws Exception {
        // when then
        Assertions.assertThatThrownBy(() -> BonusNumber.from(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.BONUS_NUMBERS_RANGE_ERROR.getMessage());
    }

}