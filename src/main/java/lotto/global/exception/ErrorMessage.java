package lotto.global.exception;

public enum ErrorMessage {
    PURCHASE_UNIT_ERROR("구매 단위는 천원 단위여야 합니다."),
    LOTTO_NUMBERS_SIZE_ERROR("로또 번호는 6개여야 합니다."),
    LOTTO_NUMBERS_DUPLICATE_ERROR("로또 번호는 중복될 수 없습니다."),
    WINNING_NUMBERS_SIZE_ERROR("당첨 번호는 6개여야 합니다."),
    LOTTO_RESULT_DUPLICATE_ERROR("당첨번호 및 보너스 번호는 중복될 수 없다."),
    INVALID_LOTTO_TICKET_ERROR("구매 금액과 발행한 로또 수가 일치하지 않습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
