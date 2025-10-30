package lotto.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RandomLottoNumberGeneratorTest {

    private final RandomLottoNumberGenerator randomLottoNumberGenerator = new RandomLottoNumberGenerator();

    @Test
    @DisplayName("1 ~ 45 사이의 중복되지 않는 6개의 번호가 생성된다.")
    public void generate() throws Exception {
        // when
        List<Integer> generatedLotto = randomLottoNumberGenerator.generate();

        // then
        assertThat(generatedLotto)
                .hasSize(6)
                .doesNotHaveDuplicates()
                .allMatch(num -> num >= 1 && num <= 45);
    }

}