package lotto.global.exception;

import static lotto.global.constants.LottoConstants.LOTTO_MAX_NUMBER;
import static lotto.global.constants.LottoConstants.LOTTO_MIN_NUMBER;

public enum ErrorMessage {

    // 구매금액 관련 에러
    PURCHASE_AMOUNT_UNIT_ERROR("구매금액의 단위는 천원 단위여야 합니다."),

    // 로또 번호 관련 에러
    LOTTO_NUMBERS_SIZE_ERROR("로또 번호는 6개여야 합니다."),
    LOTTO_NUMBERS_DUPLICATE_ERROR("로또 번호는 중복될 수 없습니다."),
    LOTTO_NUMBERS_RANGE_ERROR(String.format("로또 번호는 %d ~ %d 사이여야 합니다", LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER)),

    // 당첨 번호 관련 에러
    WINNING_NUMBERS_DUPLICATE_ERROR("당첨 번호는 중복될 수 없습니다."),
    WINNING_NUMBERS_RANGE_ERROR(String.format("당첨 번호는 %d ~ %d 사이여야 합니다", LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER)),
    WINNING_NUMBERS_SIZE_ERROR("당첨 번호는 6개여야 합니다."),

    // 보너스 번호 관련 에러
    LOTTO_RESULT_DUPLICATE_ERROR("보너스 번호는 당첨번호와 중복될 수 없습니다."),
    BONUS_NUMBERS_RANGE_ERROR(String.format("보너스 번호는 %d ~ %d 사이여야 합니다", LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER)),


    // 기타 에러
    INVALID_LOTTO_TICKET_ERROR("구매 금액과 발행한 로또 수가 일치하지 않습니다."),
    INVALID_WINNING_RESULTS_ERROR("유효하지 않은 당첨 결과입니다."),

    // 입력 형식 관련 에러
    EMPTY_INPUT_ERROR("값을 입력해주세요."),
    INVALID_NUMBER_FORMAT_ERROR("숫자만 입력해야 합니다."),
    NUMBER_OVERFLOW_ERROR("입력된 숫자가 범위를 초과했습니다.");

    private final String message;
    private static final String ERROR_PREFIX = "[ERROR] ";

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}
