package lotto.view;

import lotto.controller.dto.response.AnalysisResponse;
import lotto.controller.dto.response.PurchaseResponse;

public interface LottoOutputView {

    void printPurchasedLottos(PurchaseResponse purchaseResponse);

    void printWinningStatistics(AnalysisResponse response);

    void printErrorMessage(String errorMessage);
}
