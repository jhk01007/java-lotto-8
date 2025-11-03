package lotto.domain;

import lotto.domain.vo.Lotto;
import lotto.domain.vo.PurchaseAmount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static lotto.global.exception.ErrorMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTicketTest {

    @Test
    @DisplayName("로또 갯수가 구매 금액 갯수에 맞아야 LottoTicket을 생성할 수 있다.")
    public void of_success() throws Exception {
        // given
        PurchaseAmount purchaseAmount = PurchaseAmount.from(3000);// 로또 3개 구매
        List<Lotto> lottos = List.of(
                Lotto.from(List.of(1, 2, 3, 4, 5, 6)),
                Lotto.from(List.of(1, 2, 12, 15, 18, 24)),
                Lotto.from(List.of(5, 7, 11, 23, 35, 40))
        );
        // when
        LottoTicket lottoTicket = LottoTicket.of(purchaseAmount, lottos);
        // then
        assertThat(lottoTicket.getPurchaseAmount()).isEqualTo(purchaseAmount);
        assertThat(lottoTicket.getPurchasedLottos()).isEqualTo(lottos);
    }

    @Test
    @DisplayName("로또 갯수가 구매 금액 갯수가 맞지 않으면 예외가 발생한다.")
    public void of_fail() throws Exception {
        // given
        PurchaseAmount purchaseAmount = PurchaseAmount.from(3000);// 로또 3개 구매
        List<Lotto> lottos = List.of(
                Lotto.from(List.of(1, 2, 3, 4, 5, 6)),
                Lotto.from(List.of(1, 2, 12, 15, 18, 24))
        ); // 로또는 2개뿐

        // when then
        assertThatThrownBy(() -> LottoTicket.of(purchaseAmount, lottos))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_LOTTO_TICKET_ERROR.getMessage());
    }

}