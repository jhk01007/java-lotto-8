package lotto.service;

import lotto.domain.LottoTicket;
import lotto.domain.vo.Lotto;
import lotto.global.constants.LottoConstants;
import lotto.infra.random.LottoNumberGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class GenerateLottoTicketServiceTest {

    private final FixedLottoNumberGenerator fixedLottoNumberGenerator = new FixedLottoNumberGenerator();
    private final GenerateLottoTicketService service = new GenerateLottoTicketService(fixedLottoNumberGenerator);

    @Test
    @DisplayName("로또 티켓을 생성한다.")
    public void generate() throws Exception {
        // given
        int purchaseAmount = 8000;

        // when
        LottoTicket lottoTicket = service.generate(purchaseAmount);

        // then
        assertThat(lottoTicket.getPurchaseAmount().getAmount()).isEqualTo(purchaseAmount);
        assertThat(lottoTicket.getPurchasedLottos()).hasSize(purchaseAmount / LottoConstants.PRICE_PER_GAME)
                .allSatisfy(lotto ->
                        assertThat(lotto.getNumbers()).containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6));
    }


    static class FixedLottoNumberGenerator implements LottoNumberGenerator {

        @Override
        public List<Integer> generate() {
            return List.of(1, 2, 3, 4, 5, 6);
        }
    }

}