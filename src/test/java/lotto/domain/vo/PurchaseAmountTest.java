package lotto.domain.vo;

import lotto.global.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;


class PurchaseAmountTest {

    @Test
    @DisplayName("유효한 금액(천원 단위)은 정상적으로 PurchaseAmount 가 생성된다.")
    void from() {
        // given
        int amount = 3000;

        // when
        PurchaseAmount purchaseAmount = PurchaseAmount.from(amount);

        // then
        assertThat(purchaseAmount.getAmount()).isEqualTo(amount);
    }

    @ParameterizedTest
    @DisplayName("유효한 금액(천원 단위)이 아닌 경우 PurchaseAmount 가 생성되지 않고 예외가 발생한다.")
    @CsvSource({"1100", "0"})
    public void from_fail(int amount) throws Exception {
        // when // then
        assertThatThrownBy(() -> PurchaseAmount.from(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.PURCHASE_UNIT_ERROR.getMessage());
    }


}