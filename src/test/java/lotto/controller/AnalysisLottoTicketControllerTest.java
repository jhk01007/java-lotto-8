package lotto.controller;

import lotto.controller.dto.request.AnalysisRequest;
import lotto.controller.dto.response.AnalysisResponse;
import lotto.domain.vo.LottoRank;
import lotto.global.BaseResponse;
import lotto.service.AnalyzeLottoTicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static lotto.domain.vo.LottoRank.*;
import static org.assertj.core.api.Assertions.*;

class AnalysisLottoTicketControllerTest {


    private final AnalyzeLottoTicketService service = new AnalyzeLottoTicketService();
    private final AnalysisLottoTicketController controller = new AnalysisLottoTicketController(service);

    @Test
    @DisplayName("구매한 로또에 대해 당첨 통계를 계산한다.")
    public void analysis() throws Exception {
        // given
        AnalysisRequest analysisRequest = createAnalysisRequest();

        // when
        BaseResponse<AnalysisResponse> response = controller.analysis(analysisRequest);

        // then
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getResult()).isPresent();
        assertResult(response);
    }

    private static void assertResult(BaseResponse<AnalysisResponse> response) {
        AnalysisResponse analysisResponse = response.getResult().get();
        assertThat(analysisResponse.winningResults()).hasSize(LottoRank.values().length)
                .extracting(
                        AnalysisResponse.WinningResultDto::matchedCount,
                        AnalysisResponse.WinningResultDto::winningAmount,
                        AnalysisResponse.WinningResultDto::winningCount
                ).containsExactly(
                        tuple(FIFTH.getMatchedCount(), FIFTH.getWinningAmount(), 0),
                        tuple(FOURTH.getMatchedCount(), FOURTH.getWinningAmount(), 0),
                        tuple(THIRD.getMatchedCount(), THIRD.getWinningAmount(), 1),
                        tuple(SECOND.getMatchedCount(), SECOND.getWinningAmount(), 1),
                        tuple(FIRST.getMatchedCount(), FIRST.getWinningAmount(), 1)
                );
    }

    private static AnalysisRequest createAnalysisRequest() {
        int purchaseAmount = 3000;
        List<AnalysisRequest.PurchasedLottosDto> purchasedLottos = List.of(
                createPurchasedLottosDto(List.of(1, 2, 3, 4, 5, 6)), // 1등
                createPurchasedLottosDto(List.of(1, 2, 3, 4, 5, 7)), // 2등
                createPurchasedLottosDto(List.of(1, 2, 3, 4, 5, 8)) // 3등
        );
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int bonusNumber = 7;

        AnalysisRequest analysisRequest = new AnalysisRequest(
                purchaseAmount,
                purchasedLottos,
                winningNumbers,
                bonusNumber
        );
        return analysisRequest;
    }

    private static AnalysisRequest.PurchasedLottosDto createPurchasedLottosDto(List<Integer> lottoNumbers) {
        return new AnalysisRequest.PurchasedLottosDto(lottoNumbers);
    }

}