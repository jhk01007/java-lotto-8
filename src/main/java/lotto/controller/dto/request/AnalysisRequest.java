package lotto.controller.dto.request;

import java.util.List;

public record AnalysisRequest(
        int purchaseAmount,
        List<PurchasedLottosDto> purchasedLottos,
        List<Integer> winningNumbers,
        int bonusNumber
) {

    public record PurchasedLottosDto(
            List<Integer> lottoNumbers
    ) {
    }

}
