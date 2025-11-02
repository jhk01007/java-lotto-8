package lotto.domain.vo;

import java.util.Optional;

public enum LottoRank {

    // TODO: 상금이 회차별로 변경되는 부분 고려 - java.util.Properties
    FIRST(1, 6, 2_000_000_000L),
    SECOND(2, 5, 30_000_000L),
    THIRD(3, 5, 1_500_000L),
    FOURTH(4, 4, 50_000L),
    FIFTH(5, 3, 5_000L);

    private final int rank;
    private final int matchedCount;
    private final long winningAmount;

    LottoRank(int rank, int matchedCount, long winningAmount) {
        this.rank = rank;
        this.matchedCount = matchedCount;
        this.winningAmount = winningAmount;
    }

    public int getRank() {
        return rank;
    }

    public int getMatchedCount() {
        return matchedCount;
    }

    public long getWinningAmount() {
        return winningAmount;
    }

    public static Optional<LottoRank> of(int matchedCount, boolean isBonusNumberMatch) {
        if (matchedCount == 5 && isBonusNumberMatch) { // 2등 판별
            return Optional.of(SECOND);
        }
        if (matchedCount == 5 && !isBonusNumberMatch) { // 3등 판별
            return Optional.of(THIRD);
        }
        for (LottoRank lottoRank : LottoRank.values()) { // 나머지 판별
            if(lottoRank.getMatchedCount() == matchedCount) {
                return Optional.of(lottoRank);
            }
        }
        return Optional.empty(); // 미당첨된 경우
    }
}
