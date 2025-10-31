package lotto.view;

import lotto.controller.dto.response.AnalysisResponse;
import lotto.controller.dto.response.PurchaseResponse;

import java.util.List;

public interface LottoOutputView {

    void printPurchasedLottos(PurchaseResponse purchaseResponse);

    void printWinningStatistics(AnalysisResponse response);
}
