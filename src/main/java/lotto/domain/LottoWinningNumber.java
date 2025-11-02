package lotto.domain;

import lotto.domain.vo.Lotto;
import lotto.domain.vo.WinningLotto;

import java.util.HashSet;

import static lotto.global.exception.ErrorMessage.*;

public class LottoWinningNumber {

    private final WinningLotto winningNumber;
    private final int bonusNumber;

    private LottoWinningNumber(WinningLotto winningNumber, int bonusNumber) {
        this.winningNumber = winningNumber;
        this.bonusNumber = bonusNumber;
    }
    
    public static LottoWinningNumber of(WinningLotto winningNumber, int bonusNumber) {
        validate(winningNumber, bonusNumber);
        return new LottoWinningNumber(winningNumber, bonusNumber);
    }

    public WinningLotto getWinningNumber() {
        return winningNumber; // WinningLotto 는 불변 객체이기 때문에 그대로 반환
    }

    public int getBonusNumber() {
        return bonusNumber;
    }

    private static void validate(WinningLotto winningNumber, int bonusNumber) {
        validateLottoResultDuplicate(winningNumber, bonusNumber);
    }


    private static void validateLottoResultDuplicate(WinningLotto winningNumber, int bonusNumber) {
        HashSet<Integer> numbersSet = new HashSet<>(winningNumber.getNumbers());
        numbersSet.add(bonusNumber);

        if(numbersSet.size() != winningNumber.getNumbers().size() + 1) {
            throw new IllegalArgumentException(LOTTO_RESULT_DUPLICATE_ERROR.getMessage());
        }
    }
}
