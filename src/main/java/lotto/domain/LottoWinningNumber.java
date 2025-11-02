package lotto.domain;

import lotto.domain.vo.BonusNumber;
import lotto.domain.vo.WinningLotto;

import java.util.HashSet;

import static lotto.global.exception.ErrorMessage.*;

public class LottoWinningNumber {

    private final WinningLotto winningNumber;
    private final BonusNumber bonusNumber;

    private LottoWinningNumber(WinningLotto winningNumber, BonusNumber bonusNumber) {
        this.winningNumber = winningNumber;
        this.bonusNumber = bonusNumber;
    }
    
    public static LottoWinningNumber of(WinningLotto winningNumber, BonusNumber bonusNumber) {
        validate(winningNumber, bonusNumber);
        return new LottoWinningNumber(winningNumber, bonusNumber);
    }

    public WinningLotto getWinningNumber() {
        return winningNumber; // 불변 객체이기 때문에 그대로 반환
    }

    public BonusNumber getBonusNumber() {
        return bonusNumber; // 불변 객체이기 때문에 그대로 반환
    }

    private static void validate(WinningLotto winningNumber, BonusNumber bonusNumber) {
        validateLottoResultDuplicate(winningNumber, bonusNumber);
    }


    private static void validateLottoResultDuplicate(WinningLotto winningNumber, BonusNumber bonusNumber) {
        HashSet<Integer> numbersSet = new HashSet<>(winningNumber.getNumbers());
        numbersSet.add(bonusNumber.getNumber());

        if(numbersSet.size() != winningNumber.getNumbers().size() + 1) {
            throw new IllegalArgumentException(LOTTO_RESULT_DUPLICATE_ERROR.getMessage());
        }
    }
}
