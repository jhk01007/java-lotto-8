package lotto.view;

import camp.nextstep.edu.missionutils.test.NsTest;
import lotto.controller.dto.response.AnalysisResponse;
import lotto.controller.dto.response.PurchaseResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConsoleLottoOutputViewTest extends NsTest {

    private final LottoOutputView lottoOutputView = new ConsoleLottoOutputView();

    @Test
    @DisplayName("구매한 로또를 출력한다.")
    public void printPurchasedLottos() throws Exception {
        // given
        PurchaseResponse purchaseResponse = new PurchaseResponse(3,
                List.of(
                        createLottoDto(List.of(8, 21, 23, 41, 42, 43)),
                        createLottoDto(List.of(3, 5, 11, 16, 32, 38)),
                        createLottoDto(List.of(3, 5, 11, 16, 32, 38))
                )
        );
        // when // then
        lottoOutputView.printPurchasedLottos(purchaseResponse);

        assertThat(output())
                .isEqualTo("3개를 구매했습니다.\n" +
                        "[8, 21, 23, 41, 42, 43]\n" +
                        "[3, 5, 11, 16, 32, 38]\n" +
                        "[3, 5, 11, 16, 32, 38]");
    }

    @Test
    @DisplayName("당첨 통계를 출력한다.")
    public void printWinningStatistics() throws Exception {
        // given
        AnalysisResponse analysisResponse = new AnalysisResponse(
                List.of(
                        new AnalysisResponse.WinningResultDto(5, 3, 5000, 1),
                        new AnalysisResponse.WinningResultDto(4, 4, 50000, 0),
                        new AnalysisResponse.WinningResultDto(3, 5, 1500000, 0),
                        new AnalysisResponse.WinningResultDto(2, 5, 30000000, 0),
                        new AnalysisResponse.WinningResultDto(1, 6, 2000000000, 0)
                ), 62.5
        );

        // when
        lottoOutputView.printWinningStatistics(analysisResponse);

        // then
        assertThat(output())
                .isEqualTo("당첨 통계\n" +
                        "---\n" +
                        "3개 일치 (5,000원) - 1개\n" +
                        "4개 일치 (50,000원) - 0개\n" +
                        "5개 일치 (1,500,000원) - 0개\n" +
                        "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개\n" +
                        "6개 일치 (2,000,000,000원) - 0개\n" +
                        "총 수익률은 62.5%입니다.");

    }

    private static PurchaseResponse.LottoDto createLottoDto(List<Integer> lottoNumbers) {
        return new PurchaseResponse.LottoDto(lottoNumbers);
    }

    @Override
    protected void runMain() {

    }
}