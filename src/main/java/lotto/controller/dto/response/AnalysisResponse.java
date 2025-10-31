package lotto.controller.dto.response;

import java.util.List;

public record AnalysisResponse(
        List<WinningResultDto> winningResults,
        double profitRate
) {
    public record WinningResultDto(
            int rank, // 등수
            int matchedCount, // 일치한 번호 갯수
            long winningAmount, // 당첨 상금
            int winningCount // 당첨 수
    ) {
    }
}
