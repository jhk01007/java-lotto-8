package lotto.controller;

import lotto.controller.dto.response.AnalysisResponse;
import lotto.controller.dto.request.AnalysisRequest;
import lotto.domain.LottoTicket;
import lotto.domain.LottoWinningNumber;
import lotto.domain.vo.Lotto;
import lotto.domain.vo.LottoRank;
import lotto.domain.vo.PurchaseAmount;
import lotto.domain.vo.WinningLotto;
import lotto.global.BaseResponse;
import lotto.service.AnalyzeLottoTicketService;

import java.util.*;

public class AnalysisLottoTicketController {
    private final AnalyzeLottoTicketService analyzeLottoTicketService;

    public AnalysisLottoTicketController(AnalyzeLottoTicketService analyzeLottoTicketService) {
        this.analyzeLottoTicketService = analyzeLottoTicketService;
    }

    public BaseResponse<?> analysis(AnalysisRequest request) {
        HashMap<LottoRank, Integer> results;
        double profitRate;
        try {
            LottoTicket lottoTicket = createLottoTicket(request);
            LottoWinningNumber lottoWinningNumber = createLottoWinningNumber(request);
            results = analyzeLottoTicketService.computeWinningResults(lottoTicket, lottoWinningNumber); // 당첨 결과 계산
            profitRate = analyzeLottoTicketService.computeProfitRate(results, lottoTicket.getPurchaseAmount()); // 수익률 계산
        } catch (IllegalArgumentException e) {
            return BaseResponse.onFailure(e.getMessage());
        }
        return BaseResponse.onSuccess(new AnalysisResponse(toSortedWinningResultDto(results), profitRate));
    }

    private static LottoTicket createLottoTicket(AnalysisRequest request) {
        List<Lotto> purchasedLottos = toLottoDomains(request);
        PurchaseAmount purchaseAmount = PurchaseAmount.from(request.purchaseAmount());
        return LottoTicket.of(purchaseAmount, purchasedLottos);
    }

    private static LottoWinningNumber createLottoWinningNumber(AnalysisRequest request) {
        return LottoWinningNumber.of(WinningLotto.from(request.winningNumbers()), request.bonusNumber());
    }

    private static List<Lotto> toLottoDomains(AnalysisRequest request) {
        return request.purchasedLottos().stream()
                .map(purchasedLottosDto -> Lotto.from(purchasedLottosDto.lottoNumbers()))
                .toList();
    }

    // 등수를 기준으로 내림차순으로 정렬 후 변환됨
    private static List<AnalysisResponse.WinningResultDto> toSortedWinningResultDto(HashMap<LottoRank, Integer> results) {
        return Arrays.stream(LottoRank.values())
                .sorted(Comparator.comparingLong(LottoRank::getRank).reversed())
                .map(rank -> new AnalysisResponse.WinningResultDto(
                        rank.getRank(),
                        rank.getMatchedCount(),
                        rank.getWinningAmount(),
                        results.getOrDefault(rank, 0)
                ))
                .toList();
    }
}

