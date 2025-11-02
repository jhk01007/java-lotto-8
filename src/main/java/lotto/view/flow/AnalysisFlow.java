package lotto.view.flow;

import lotto.global.BaseResponse;
import lotto.view.InputValidator;
import lotto.view.LottoInputView;
import lotto.view.LottoOutputView;
import lotto.controller.AnalysisLottoTicketController;
import lotto.controller.dto.request.AnalysisRequest;
import lotto.controller.dto.response.AnalysisResponse;
import lotto.controller.dto.response.PurchaseResponse;

import java.util.List;

import static lotto.global.constants.LottoConstants.PRICE_PER_GAME;

public class AnalysisFlow {
    private final LottoInputView lottoInputView;
    private final LottoOutputView lottoOutputView;
    private final AnalysisLottoTicketController analysisLottoTicketController;

    public AnalysisFlow(LottoInputView lottoInputView, LottoOutputView lottoOutputView, AnalysisLottoTicketController analysisLottoTicketController) {
        this.lottoInputView = lottoInputView;
        this.lottoOutputView = lottoOutputView;
        this.analysisLottoTicketController = analysisLottoTicketController;
    }

    public void run(PurchaseResponse purchaseResponse) {
        while (true) {
            List<Integer> winning = getWinningNumbers(); // 당첨번호 입력
            int bonus = getBonusNumber(); // 보너스 번호 입력
            AnalysisRequest req = createAnalysisRequest(purchaseResponse, winning, bonus);
            BaseResponse<?> res = analysisLottoTicketController.analysis(req); // 컨트롤러에 분석 요청
            if (isRequestSuccess(res)) { // 요청 성공시
                AnalysisResponse result = (AnalysisResponse) res.getResult().get();
                lottoOutputView.printWinningStatistics(result);
                return;
            }
            lottoOutputView.printErrorMessage(res.getMessage()); // 요청 실패시
        }
    }

    /**
     * 당첨번호(6개) 입력 처리
     */
    private List<Integer> getWinningNumbers() {
        while (true) {
            String input = lottoInputView.readWinningNumbers();
            try {
                return InputValidator.parseAndValidateWinningNumbers(input);
            } catch (IllegalArgumentException e) {
                lottoOutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * 보너스 번호 입력 처리
     */
    private int getBonusNumber() {
        while (true) {
            String input = lottoInputView.readBonusNumber();
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

    private static AnalysisRequest createAnalysisRequest(PurchaseResponse purchaseResponse, List<Integer> parsedWinningNumbers, int parsedBonusNumber) {
        return new AnalysisRequest(
                purchaseResponse.numberOfGames() * PRICE_PER_GAME,
                purchaseResponse.purchasedLottos().stream()
                        .map(lottoDto -> new AnalysisRequest.PurchasedLottosDto(lottoDto.lottoNumbers()))
                        .toList(),
                parsedWinningNumbers, parsedBonusNumber
        );
    }
}
