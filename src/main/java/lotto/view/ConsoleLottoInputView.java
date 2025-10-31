package lotto.view;

import camp.nextstep.edu.missionutils.Console;

public class ConsoleLottoInputView implements LottoInputView {
    @Override
    public String readPurchaseAmount() {
        System.out.println(GuideMessage.PURCHASE_AMOUNT_INPUT_GUIDE.getMessage());
        return Console.readLine();
    }

    @Override
    public String readWinningNumbers() {
        System.out.println(GuideMessage.WINNING_NUMBERS_INPUT_GUIDE.getMessage());
        return Console.readLine();
    }

    @Override
    public String readBonusNumber() {
        System.out.println(GuideMessage.BONUS_NUMBER_INPUT_GUIDE.getMessage());
        return Console.readLine();
    }
}
