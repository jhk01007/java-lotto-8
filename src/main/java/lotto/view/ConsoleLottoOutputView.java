package lotto.view;

import lotto.controller.dto.response.AnalysisResponse;
import lotto.controller.dto.response.PurchaseResponse;

import static lotto.view.GuideMessage.*;

public class ConsoleLottoOutputView implements LottoOutputView {

    @Override
    public void printPurchasedLottos(PurchaseResponse purchaseResponse) {
        // 구매한 로또 게임 수 출력
        System.out.println(PURCHASE_AMOUNT_OUTPUT_GUIDE.getMessage(purchaseResponse.numberOfGames()));

        // 로또 번호 출력
        for (PurchaseResponse.LottoDto purchasedLotto : purchaseResponse.purchasedLottos()) {
            System.out.println(purchasedLotto.lottoNumbers());
        }
    }

    @Override
    public void printWinningStatistics(AnalysisResponse response) {
        System.out.println(WINNING_STATISTICS_OUTPUT_GUIDE.getMessage());
        for (AnalysisResponse.WinningResultDto winningResult : response.winningResults()) {
            // 당첨 결과 출력
            // 2등인 경우, '보너스 볼 일치'까지 출력됨
            if(winningResult.rank() == 2) {
                System.out.println(SECOND_WINNING_RESULTS_OUTPUT_GUIDE.getMessage(
                        winningResult.matchedCount(), winningResult.winningAmount(), winningResult.winningCount())
                );
                continue;
            }
            // 나머지 등수인 경우 '보너스 볼 일치' 출력 X
            System.out.println(DEFAULT_WINNING_RESULTS_OUTPUT_GUIDE.getMessage(
                    winningResult.matchedCount(), winningResult.winningAmount(), winningResult.winningCount())
            );
        }

        // 수익률 출력
        System.out.println(PROFIT_RATE_OUTPUT_GUIDE.getMessage(response.profitRate()));
    }

}
