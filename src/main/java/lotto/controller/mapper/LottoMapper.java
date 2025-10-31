package lotto.controller.mapper;

import lotto.controller.dto.response.PurchaseResponse;
import lotto.domain.LottoTicket;

public class LottoMapper {

    public static PurchaseResponse toPurchaseResponse(LottoTicket lottoTicket) {
        return new PurchaseResponse(
                lottoTicket.getPurchaseAmount().getNumberOfGames(),
                lottoTicket.getPurchasedLottos().stream()
                        .map(lotto -> new PurchaseResponse.LottoDto(lotto.getNumbers()))
                        .toList()
        );
    }
}
