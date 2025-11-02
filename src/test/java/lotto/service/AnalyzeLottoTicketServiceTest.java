package lotto.service;

import lotto.domain.LottoTicket;
import lotto.domain.LottoWinningNumber;
import lotto.domain.vo.Lotto;
import lotto.domain.vo.LottoRank;
import lotto.domain.vo.PurchaseAmount;
import lotto.domain.vo.WinningLotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AnalyzeLottoTicketServiceTest {

    private final AnalyzeLottoTicketService service = new AnalyzeLottoTicketService();

    @Test
    @DisplayName("로또 티켓과 당첨 번호를 통해 당첨결과를 계산한다.")
    public void computeWinningResults() throws Exception {
        // given
        LottoTicket lottoTicket = createLottoTicket(5000, List.of(
                Lotto.from(List.of(1, 2, 3, 4, 5, 6)), // 1등
                Lotto.from(List.of(1, 2, 3, 4, 5, 7)), // 2등
                Lotto.from(List.of(1, 2, 3, 4, 5, 8)), // 3등
                Lotto.from(List.of(1, 2, 3, 4, 7, 8)), // 4등
                Lotto.from(List.of(1, 2, 3, 7, 8, 9)) // 5등
        ));
        LottoWinningNumber lottoWinningNumber =
                createLottoWinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);

        // when
        HashMap<LottoRank, Integer> results = service.computeWinningResults(lottoTicket, lottoWinningNumber);

        // then
        assertThat(results).hasSize(LottoRank.values().length)
                .containsExactlyInAnyOrderEntriesOf(
                        Map.of(
                                LottoRank.FIRST, 1,
                                LottoRank.SECOND, 1,
                                LottoRank.THIRD, 1,
                                LottoRank.FOURTH, 1,
                                LottoRank.FIFTH, 1
                        )
                );
    }

    @Test
    @DisplayName("구매 금액과 당첨결과를 통해 수익률을 계산한다. 수익률은 소숫점 둘째 자리에서 반올림 한다.")
    public void computeProfitRate() throws Exception {
        // given
        Map<LottoRank, Integer> winningResults = Map.of(
                LottoRank.FIRST, 0,
                LottoRank.SECOND, 0,
                LottoRank.THIRD, 0,
                LottoRank.FOURTH, 0,
                LottoRank.FIFTH, 1
        );
        PurchaseAmount purchaseAmount = PurchaseAmount.from(3000);

        // when
        double profitRate = service.computeProfitRate(winningResults, purchaseAmount);

        // then
        assertThat(profitRate).isEqualTo(166.7); // 5000/3000*100 = 166.666... -> 166.7
    }

    private static LottoTicket createLottoTicket(int amount, List<Lotto> lottos) {
        return LottoTicket.of(
                PurchaseAmount.from(amount),
                lottos
        );
    }

    private static LottoWinningNumber createLottoWinningNumbers(List<Integer> winningNumbers, int bonusNumber) {
        return LottoWinningNumber.of(
                WinningLotto.from(winningNumbers),
                bonusNumber
        );
    }

}