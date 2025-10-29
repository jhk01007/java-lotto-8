package lotto.domain.vo;


import java.util.Objects;

import static lotto.global.exception.ErrorMessage.PURCHASE_UNIT_ERROR;

public class PurchaseAmount {

    private static final int PURCHASE_UNIT = 1000;
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
        if(amount == 0 || amount % PURCHASE_UNIT != 0) {
            throw new IllegalArgumentException(PURCHASE_UNIT_ERROR.getMessage());
        }
    }

    public int getPurchaseCount() {
        return amount / PURCHASE_UNIT;
    }
}
