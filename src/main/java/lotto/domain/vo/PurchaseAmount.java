package lotto.domain.vo;


import java.util.Objects;

import static lotto.global.constants.LottoConstants.PRICE_PER_GAME;
import static lotto.global.exception.ErrorMessage.PURCHASE_UNIT_ERROR;

public class PurchaseAmount {


    private final int amount;

    private PurchaseAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseAmount that = (PurchaseAmount) o;
        return amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }

    public static PurchaseAmount from(int amount) {
        validatePurchaseAmount(amount);
        return new PurchaseAmount(amount);
    }

    private static void validatePurchaseAmount(int amount) {
        if(amount == 0 || amount % PRICE_PER_GAME != 0) {
            throw new IllegalArgumentException(PURCHASE_UNIT_ERROR.getMessage());
        }
    }

    public int getPurchaseCount() {
        return amount / PRICE_PER_GAME;
    }
}
