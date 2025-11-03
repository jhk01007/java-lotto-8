package lotto.service;

import lotto.domain.LottoTicket;
import lotto.domain.vo.PurchaseAmount;
import lotto.domain.LottoNumberGenerator;
import lotto.infra.random.RandomLottoNumberGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GenerateLottoTicketServiceTest {

    private final LottoNumberGenerator lottoNumberGenerator = new RandomLottoNumberGenerator();
    private final GenerateLottoTicketService service = new GenerateLottoTicketService(lottoNumberGenerator);

    @Test
    @DisplayName("로또 티켓을 생성한다.")
    public void generate() throws Exception {
        // given
        int amount = 3000;
        PurchaseAmount purchaseAmount = PurchaseAmount.from(amount);

        // when
        LottoTicket lottoTicket = service.generate(purchaseAmount);

        // then
        assertThat(lottoTicket.getPurchaseAmount().getAmount()).isEqualTo(3000);
        assertThat(lottoTicket.getPurchasedLottos()).hasSize(3);
    }

}