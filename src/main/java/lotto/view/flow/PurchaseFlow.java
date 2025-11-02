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
            int amount = getPurchaseAmount(); // 구매금액 입력
            PurchaseRequest request = new PurchaseRequest(amount);
            BaseResponse<?> response = purchaseLottoController.purchase(request); // 컨트롤러에 구매 요청
            if (isRequestSuccess(response)) { // 요청 성공시
                PurchaseResponse result = (PurchaseResponse) response.getResult().get();
                lottoOutputView.printPurchasedLottos(result);
                return result;
            }
            lottoOutputView.printErrorMessage(response.getMessage()); // 응답 성공시
        }
    }

    /**
     * 구매금액 입력 처리
     */
    private int getPurchaseAmount() {
        while (true) {
            String input = lottoInputView.readPurchaseAmount();
            try {
                return InputValidator.parseAndValidateInt(input);
            } catch (IllegalArgumentException e) {
                lottoOutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private static boolean isRequestSuccess(BaseResponse<?> response) {
        return response != null && response.isSuccess() && response.getResult().isPresent();
    }
}
