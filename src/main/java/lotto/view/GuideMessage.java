package lotto.view;

public enum GuideMessage {

    PURCHASE_AMOUNT_INPUT_GUIDE("구입 금액을 입력해주세요"),
    WINNING_NUMBERS_INPUT_GUIDE("당첨 번호를 입력해주세요."),
    BONUS_NUMBER_INPUT_GUIDE("보너스 번호를 입력해주세요."),

    PURCHASE_AMOUNT_OUTPUT_GUIDE("%d개를 구매했습니다."),
    WINNING_STATISTICS_OUTPUT_GUIDE("당첨 통계\n---"),
    DEFAULT_WINNING_RESULTS_OUTPUT_GUIDE("%d개 일치 (%,d원) - %d개"), // %,d: 천 단위 , 구분기호를 붙임
    SECOND_WINNING_RESULTS_OUTPUT_GUIDE("%d개 일치, 보너스 볼 일치 (%,d원) - %d개"), // 2등은 추가적으로 ", 보너스 볼 일치"를 출력해야 함
    PROFIT_RATE_OUTPUT_GUIDE("총 수익률은 %.1f%%입니다.");

    private final String message;

    GuideMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
