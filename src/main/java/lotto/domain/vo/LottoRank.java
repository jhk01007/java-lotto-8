package lotto.domain.vo;

public enum LottoRank {

    // TODO: 상금이 회차별로 변경되는 부분 고려 - java.util.Properties
    FIRST(6, 2000000000),
    SECOND(5, 30000000),
    THIRD(5, 1500000),
    FOURTH(4, 50000),
    FIFTH(3, 5000);

    private final int matchedCount;
    private final long winningAmount;

    LottoRank(int matchedCount, long winningAmount) {
        this.matchedCount = matchedCount;
        this.winningAmount = winningAmount;
    }

    public int getMatchedCount() {
        return matchedCount;
    }

    public long getWinningAmount() {
        return winningAmount;
    }
}
