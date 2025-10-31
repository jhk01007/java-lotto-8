package lotto.service;

import lotto.domain.LottoTicket;
import lotto.domain.vo.Lotto;
import lotto.domain.vo.PurchaseAmount;
import lotto.infra.random.LottoNumberGenerator;

import java.util.ArrayList;
import java.util.List;

import static lotto.global.constants.LottoConstants.PRICE_PER_GAME;

public class GenerateLottoTicketService {

    private final LottoNumberGenerator lottoNumberGenerator;

    public GenerateLottoTicketService(LottoNumberGenerator lottoNumberGenerator) {
        this.lottoNumberGenerator = lottoNumberGenerator;
    }

    public LottoTicket generate(int purchaseAmount) {
        List<Lotto> generatedLottos = new ArrayList<>();
        for (int i = 0; i < purchaseAmount / PRICE_PER_GAME; i++) {
            generatedLottos.add(Lotto.from(lottoNumberGenerator.generate()));
        }

        return LottoTicket.of(PurchaseAmount.from(purchaseAmount), generatedLottos);
    }
}
