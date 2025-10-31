package lotto.controller;


import lotto.controller.dto.request.PurchaseRequest;
import lotto.controller.dto.response.PurchaseResponse;
import lotto.global.BaseResponse;
import lotto.global.constants.LottoConstants;
import lotto.global.exception.ErrorMessage;
import lotto.infra.random.LottoNumberGenerator;
import lotto.infra.random.RandomLottoNumberGenerator;
import lotto.service.GenerateLottoTicketService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class PurchaseLottoControllerTest {


    private final LottoNumberGenerator lottoNumberGenerator = new RandomLottoNumberGenerator();
    private final GenerateLottoTicketService generateLottoTicketService =
            new GenerateLottoTicketService(lottoNumberGenerator);

    private final PurchaseLottoController purchaseLottoController =
            new PurchaseLottoController(generateLottoTicketService);


    @Test
    @DisplayName("로또를 구매할 금액과 함께 로또구매를 요청하면 해당 금액에 맞는 로또가 발행된다.")
    public void purchase_success() throws Exception {
        // given
        int amount = 3000;
        PurchaseRequest purchaseRequest = new PurchaseRequest(amount);

        // when
        BaseResponse<?> response = purchaseLottoController.purchase(purchaseRequest);

        // then
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getResult()).isPresent();

        PurchaseResponse purchaseResponse = (PurchaseResponse) response.getResult().get();
        int numberOfGames = amount / LottoConstants.PRICE_PER_GAME;
        assertThat(purchaseResponse.numberOfGames()).isEqualTo(numberOfGames);
        assertThat(purchaseResponse.purchasedLottos()).hasSize(numberOfGames);
    }

    @Test
    @DisplayName("로또를 구매할 금액이 1000원 단위가 아니면 에러가 발생한다.")
    public void purchase_fail() throws Exception {
        // given
        int amount = 3100;
        PurchaseRequest purchaseRequest = new PurchaseRequest(amount);

        // when
        BaseResponse<?> response = purchaseLottoController.purchase(purchaseRequest);

        // then
        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getMessage()).isEqualTo(ErrorMessage.PURCHASE_UNIT_ERROR.getMessage());
    }

}