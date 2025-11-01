package lotto.view;

import lotto.view.flow.AnalysisFlow;
import lotto.view.flow.PurchaseFlow;
import lotto.controller.dto.response.PurchaseResponse;


public class LottoCommandLineRunner {
    private final PurchaseFlow purchaseFlow;
    private final AnalysisFlow analysisFlow;

    public LottoCommandLineRunner(PurchaseFlow purchaseFlow, AnalysisFlow analysisFlow) {
        this.purchaseFlow = purchaseFlow;
        this.analysisFlow = analysisFlow;
    }

    public void run() {
        // 로또 구매
        PurchaseResponse purchaseResponse = purchaseFlow.run();
        // 로또 통계
        analysisFlow.run(purchaseResponse);
    }

}

