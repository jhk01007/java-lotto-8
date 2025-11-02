package lotto.view;

import lotto.controller.dto.response.AnalysisResponse;
import lotto.controller.dto.response.PurchaseResponse;

public class ConsoleLottoOutputView implements LottoOutputView {

    @Override
    public void printPurchasedLottos(PurchaseResponse purchaseResponse) {
        // 구매한 로또 게임 수 출력
        System.out.println(GuideMessage.PURCHASE_AMOUNT_OUTPUT_GUIDE.getMessage(purchaseResponse.numberOfGames()));

        // 로또 번호 출력
        for (PurchaseResponse.LottoDto purchasedLotto : purchaseResponse.purchasedLottos()) {
            System.out.println(purchasedLotto.lottoNumbers());
        }
    }

    @Override
    public void printWinningStatistics(AnalysisResponse response) {
        System.out.println(GuideMessage.WINNING_STATISTICS_OUTPUT_GUIDE.getMessage());
        for (AnalysisResponse.WinningResultDto winningResult : response.winningResults()) {
            // 당첨 결과 출력
            if(winningResult.rank() == 2) {
                printSecondRank(winningResult); // 2등인 경우, '보너스 볼 일치'까지 출력
                continue;
            }
            printOtherRank(winningResult); // 나머지 등수인 경우 '보너스 볼 일치' 출력 X
        }
        // 수익률 출력
        System.out.println(GuideMessage.PROFIT_RATE_OUTPUT_GUIDE.getMessage(response.profitRate()));
    }

    private static void printSecondRank(AnalysisResponse.WinningResultDto winningResult) {
        System.out.println(GuideMessage.SECOND_WINNING_RESULTS_OUTPUT_GUIDE.getMessage(
                winningResult.matchedCount(), winningResult.winningAmount(), winningResult.winningCount())
        );
    }

    private static void printOtherRank(AnalysisResponse.WinningResultDto winningResult) {
        System.out.println(GuideMessage.DEFAULT_WINNING_RESULTS_OUTPUT_GUIDE.getMessage(
                winningResult.matchedCount(), winningResult.winningAmount(), winningResult.winningCount())
        );
    }

    @Override
    public void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

}
