package lotto.service;

import lotto.domain.LottoTicket;
import lotto.domain.vo.Lotto;
import lotto.domain.vo.PurchaseAmount;
import lotto.infra.random.LottoNumberGenerator;

import java.util.ArrayList;
import java.util.List;

public class GenerateLottoTicketService {

    private final LottoNumberGenerator lottoNumberGenerator;

    public GenerateLottoTicketService(LottoNumberGenerator lottoNumberGenerator) {
        this.lottoNumberGenerator = lottoNumberGenerator;
    }

    public LottoTicket generate(PurchaseAmount purchaseAmount) {
        List<Lotto> generatedLottos = new ArrayList<>();
        for (int i = 0; i < purchaseAmount.getNumberOfGames(); i++) {
            generatedLottos.add(Lotto.from(lottoNumberGenerator.generate()));
        }

        return LottoTicket.of(purchaseAmount, generatedLottos);
    }
}
