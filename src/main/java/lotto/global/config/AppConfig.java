package lotto.global.config;

import lotto.controller.AnalysisLottoTicketController;
import lotto.controller.PurchaseLottoController;
import lotto.infra.random.LottoNumberGenerator;
import lotto.infra.random.RandomLottoNumberGenerator;
import lotto.service.AnalyzeLottoTicketService;
import lotto.service.GenerateLottoTicketService;
import lotto.view.*;
import lotto.view.flow.AnalysisFlow;
import lotto.view.flow.PurchaseFlow;


public class AppConfig {

    // --- 싱글턴 인스턴스 관리 ---
    private static final AppConfig INSTANCE = new AppConfig();

    // --- 싱글턴으로 관리할 의존성 객체들 ---
    private final LottoInputView lottoInputView = new ConsoleLottoInputView();
    private final LottoOutputView lottoOutputView = new ConsoleLottoOutputView();
    private final LottoNumberGenerator lottoNumberGenerator = new RandomLottoNumberGenerator();

    private final GenerateLottoTicketService generateLottoTicketService =
            new GenerateLottoTicketService(lottoNumberGenerator);

    private final AnalyzeLottoTicketService analyzeLottoTicketService =
            new AnalyzeLottoTicketService();

    private final PurchaseLottoController purchaseLottoController =
            new PurchaseLottoController(generateLottoTicketService);

    private final AnalysisLottoTicketController analysisLottoTicketController =
            new AnalysisLottoTicketController(analyzeLottoTicketService);

    private final PurchaseFlow purchaseFlow =
            new PurchaseFlow(lottoInputView, lottoOutputView, purchaseLottoController);

    private final AnalysisFlow analysisFlow =
            new AnalysisFlow(lottoInputView, lottoOutputView, analysisLottoTicketController);

    private final LottoCommandLineRunner lottoCommandLineRunner =
            new LottoCommandLineRunner(purchaseFlow, analysisFlow);

    private AppConfig() {}

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    // LottoCommandLineRunner 만 직접 사용 가능
    public LottoCommandLineRunner lottoCommandLineRunner() {
        return lottoCommandLineRunner;
    }
}
