package lotto.controller.mapper;

import lotto.controller.dto.response.PurchaseResponse;
import lotto.domain.LottoTicket;
import lotto.domain.vo.Lotto;
import lotto.domain.vo.PurchaseAmount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoMapperTest {

    @Test
    @DisplayName("LottoTicket -> PurchaseResponse")
    public void toPurchaseResponse() throws Exception {
        // given
        PurchaseAmount purchaseAmount = PurchaseAmount.from(3000);// 로또 3개 구매
        List<Lotto> lottos = List.of(
                Lotto.from(List.of(1, 2, 3, 4, 5, 6)),
                Lotto.from(List.of(1, 2, 12, 15, 18, 24)),
                Lotto.from(List.of(5, 7, 11, 23, 35, 40))
        );
        LottoTicket lottoTicket = LottoTicket.of(purchaseAmount, lottos);

        // when
        PurchaseResponse purchaseResponse = LottoMapper.toPurchaseResponse(lottoTicket);

        // then
        assertThat(purchaseResponse.numberOfGames()).isEqualTo(purchaseAmount.getNumberOfGames());
        assertThat(purchaseResponse.purchasedLottos()).hasSize(lottos.size())
                .extracting(PurchaseResponse.LottoDto::lottoNumbers)
                .containsExactlyInAnyOrder(
                        List.of(1, 2, 3, 4, 5, 6),
                        List.of(1, 2, 12, 15, 18, 24),
                        List.of(5, 7, 11, 23, 35, 40)
                );
    }

}