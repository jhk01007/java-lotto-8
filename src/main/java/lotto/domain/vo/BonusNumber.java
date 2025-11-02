package lotto.domain.vo;

import lotto.global.constants.LottoConstants;

import java.util.Objects;

import static lotto.global.exception.ErrorMessage.BONUS_NUMBERS_RANGE_ERROR;

public final class BonusNumber {

    private final int number;

    private BonusNumber(int number) {
        validateBonusNumberRange(number);
        this.number = number;
    }

    public static BonusNumber from(int bonusNumber) {
        return new BonusNumber(bonusNumber);
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BonusNumber that = (BonusNumber) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    private static void validateBonusNumberRange(int bonusNumber) {
        if(bonusNumber < LottoConstants.LOTTO_MIN_NUMBER || bonusNumber > LottoConstants.LOTTO_MAX_NUMBER) {
            throw new IllegalArgumentException(BONUS_NUMBERS_RANGE_ERROR.getMessage());
        }
    }
}
