package lotto.domain.vo;

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
}
