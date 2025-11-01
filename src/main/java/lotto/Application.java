package lotto;

import lotto.global.config.AppConfig;
import lotto.view.LottoCommandLineRunner;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = AppConfig.getInstance();
        LottoCommandLineRunner lottoCommandLineRunner = appConfig.lottoCommandLineRunner();
        lottoCommandLineRunner.run();
    }
}
