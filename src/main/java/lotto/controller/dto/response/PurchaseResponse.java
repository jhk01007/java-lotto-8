package lotto.controller.dto.response;

import java.util.List;

public record PurchaseResponse(
        int numberOfGames,
        List<LottoDto> purchasedLottos
) {

    public record LottoDto(
            List<Integer> lottoNumbers
    ) {}
}
