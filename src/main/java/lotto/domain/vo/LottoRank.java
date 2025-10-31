package lotto.domain.vo;

public enum LottoRank {

    // TODO: 상금이 회차별로 변경되는 부분 고려 - java.util.Properties
    FIRST(1, 6, 2000000000),
    SECOND(2, 5, 30000000),
    THIRD(3, 5, 1500000),
    FOURTH(4, 4, 50000),
    FIFTH(5, 3, 5000);

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
