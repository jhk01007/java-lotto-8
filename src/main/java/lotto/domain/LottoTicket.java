package lotto.domain;

import lotto.domain.vo.Lotto;
import lotto.domain.vo.PurchaseAmount;

import java.util.List;

import static lotto.global.exception.ErrorMessage.INVALID_LOTTO_TICKET_ERROR;

public class LottoTicket {

    private final PurchaseAmount purchaseAmount;
    private final List<Lotto> purchasedLottos;

    private LottoTicket(PurchaseAmount purchaseAmount, List<Lotto> purchasedLottos) {
        this.purchaseAmount = purchaseAmount;
        this.purchasedLottos = purchasedLottos;
    }

    public static LottoTicket of(PurchaseAmount purchaseAmount, List<Lotto> lottoGames) {
        validate(purchaseAmount, lottoGames);
        return new LottoTicket(purchaseAmount, lottoGames);
    }

    private static void validate(PurchaseAmount purchaseAmount, List<Lotto> lottoGames) {
        if(purchaseAmount.getNumberOfGames() != lottoGames.size()) {
            throw new IllegalArgumentException(INVALID_LOTTO_TICKET_ERROR.getMessage());
        }
    }

    public PurchaseAmount getPurchaseAmount() {
        return purchaseAmount;
    }

    public List<Lotto> getPurchasedLottos() {
        return List.copyOf(purchasedLottos);
    }
}
