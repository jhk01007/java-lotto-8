package lotto.infra.random;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.domain.LottoNumberGenerator;

import java.util.List;

import static lotto.global.constants.LottoConstants.*;

public class RandomLottoNumberGenerator implements LottoNumberGenerator {

    @Override
    public List<Integer> generate() {
        return Randoms.pickUniqueNumbersInRange(
                LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER, LOTTO_NUMBER_COUNT_PER_GAME);
    }

}
