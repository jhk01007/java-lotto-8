package lotto.view.flow;

import lotto.global.BaseResponse;
import lotto.view.InputValidator;
import lotto.view.LottoInputView;
import lotto.view.LottoOutputView;
import lotto.controller.PurchaseLottoController;
import lotto.controller.dto.request.PurchaseRequest;
import lotto.controller.dto.response.PurchaseResponse;

public class PurchaseFlow {

    private final LottoInputView lottoInputView;
    private final LottoOutputView lottoOutputView;
    private final PurchaseLottoController purchaseLottoController;

    public PurchaseFlow(LottoInputView lottoInputView, LottoOutputView lottoOutputView, PurchaseLottoController purchaseLottoController) {
        this.lottoInputView = lottoInputView;
        this.lottoOutputView = lottoOutputView;
        this.purchaseLottoController = purchaseLottoController;
    }

    public PurchaseResponse run() {
        while (true) {
            // 구매 금액 입력
            String purchaseAmount = lottoInputView.readPurchaseAmount();

            int parsedPurchaseAmount = 0;
            try {
                parsedPurchaseAmount = InputValidator.parseAndValidateInt(purchaseAmount);
            } catch (IllegalArgumentException e) {
                // 구매금액 관련 오류 발생 시 오류 메시지 출력
                lottoOutputView.printErrorMessage(e.getMessage());
            }

            // 컨트롤러에 구매 요청
            PurchaseRequest purchaseRequest = new PurchaseRequest(parsedPurchaseAmount);
            BaseResponse<?> response = purchaseLottoController.purchase(purchaseRequest);

            // 요청 성공시
            if (isRequestSuccess(response)) {
                PurchaseResponse purchaseResponse = (PurchaseResponse) response.getResult().get();
                // 출력
                lottoOutputView.printPurchasedLottos(purchaseResponse);
                return purchaseResponse;
            }
        }
    }

    private static boolean isRequestSuccess(BaseResponse<?> response) {
        return response != null && response.isSuccess() && response.getResult().isPresent();
    }
}
