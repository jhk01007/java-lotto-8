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

            // 당첨번호 입력
            String winningNumbers = lottoInputView.readWinningNumbers();
            List<Integer> parsedWinningNumbers = null;
            try {
                parsedWinningNumbers = InputValidator.parseAndValidateWinningNumbers(winningNumbers);
            } catch (IllegalArgumentException e) {
                // 당첨번호 관련 오류 발생 시 오류 메시지 출력
                lottoOutputView.printErrorMessage(e.getMessage());
            }

            // 보너스 번호 입력
            String bonusNumber = lottoInputView.readBonusNumber();
            int parsedBonusNumber = 0;
            try {
                parsedBonusNumber = InputValidator.parseAndValidateInt(bonusNumber);
            } catch (IllegalArgumentException e) {
                // 보너스 번호 관련 오류 발생 시 오류 메시지 출력
                lottoOutputView.printErrorMessage(e.getMessage());
            }

            // 컨트롤러에 분석 요청
            AnalysisRequest analysisRequest =
                    createAnalysisRequest(purchaseResponse, parsedWinningNumbers, parsedBonusNumber);
            BaseResponse<?> analysisResponse = analysisLottoTicketController.analysis(analysisRequest);

            // 요청 성공시 출력
            if (isRequestSuccess(analysisResponse)) {
                AnalysisResponse response = (AnalysisResponse) analysisResponse.getResult().get();
                lottoOutputView.printWinningStatistics(response);
                return;
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
