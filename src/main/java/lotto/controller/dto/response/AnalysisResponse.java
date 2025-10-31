package lotto.controller.dto.response;

import java.util.List;

public record AnalysisResponse(
        List<WinningResultDto> winningResults,
        double profitRate
) {
    public record WinningResultDto(
            int rank,
            int matchedCount,
            long winningAmount,
            int winningCount
    ) {
    }
}
