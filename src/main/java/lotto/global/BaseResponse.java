package lotto.global;

import java.util.Optional;

public class BaseResponse<T> {

    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private static final String ERROR_PREFIX = "[ERROR]";
    private final boolean isSuccess;
    private final String message;
    private final T result;

    public BaseResponse(boolean isSuccess, String message, T result) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.result = result;
    }

    public BaseResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.result = null;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public Optional<T> getResult() {
        return Optional.ofNullable(result);
    }

    //성공한 경우 응답 생성
    public static <T> BaseResponse<T> onSuccess(T result) {
        return new BaseResponse<>(true, SUCCESS_MESSAGE, result);
    }

    // 실패한 경우 응답 생성
    public static BaseResponse<Void> onFailure(String message) {
        return new BaseResponse<>(false, ERROR_PREFIX + " " + message);
    }

}
