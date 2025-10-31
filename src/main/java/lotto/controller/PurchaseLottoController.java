package lotto.controller;

import lotto.controller.dto.request.PurchaseRequest;
import lotto.controller.dto.response.PurchaseResponse;
import lotto.domain.LottoTicket;
import lotto.domain.vo.PurchaseAmount;
import lotto.global.BaseResponse;
import lotto.service.GenerateLottoTicketService;

public class PurchaseLottoController {

    private final GenerateLottoTicketService generateLottoTicketService;

    public PurchaseLottoController(GenerateLottoTicketService generateLottoTicketService) {
        this.generateLottoTicketService = generateLottoTicketService;
    }

    public BaseResponse<?> purchase(PurchaseRequest request) {

        // TODO: 예외 처리 분리
        LottoTicket lottoTicket = null;
        try {
            lottoTicket = generateLottoTicketService.generate(PurchaseAmount.from(request.amount()));
        } catch (IllegalArgumentException e) {
            return BaseResponse.onFailure(e.getMessage());
        }

        return BaseResponse.onSuccess(toPurchaseResponse(lottoTicket));
    }

    private static PurchaseResponse toPurchaseResponse(LottoTicket lottoTicket) {
        return new PurchaseResponse(
                lottoTicket.getPurchaseAmount().getNumberOfGames(),
                lottoTicket.getPurchasedLottos().stream()
                        .map(lotto -> new PurchaseResponse.LottoDto(lotto.getNumbers()))
                        .toList()
        );
    }
}
