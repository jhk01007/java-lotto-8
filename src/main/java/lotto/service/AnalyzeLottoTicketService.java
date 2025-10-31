package lotto.service;

import lotto.domain.LottoTicket;
import lotto.domain.LottoWinningNumber;
import lotto.domain.vo.Lotto;
import lotto.domain.vo.LottoRank;
import lotto.domain.vo.PurchaseAmount;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class AnalyzeLottoTicketService {

    private static final int ROUND_SCALE = 2; // 반올림할 자릿수 n (n번째 자리에서 반올림)


    public HashMap<LottoRank, Integer> computeWinningResults(
            LottoTicket lottoTicket, LottoWinningNumber lottoWinningNumber) {
        HashMap<LottoRank, Integer> results = initWinningResults();
        // 구매한 각 로또에 대해 당첨번호 매칭
        for (Lotto lotto : lottoTicket.getPurchasedLottos()) {
            HashSet<Integer> lottoSet = new HashSet<>(lotto.getNumbers());
            // 당첨번호 중 일치하는 것이 몇개인지 계산
            int matchedCount = countMatchedNumbers(lottoSet, lottoWinningNumber.getWinningNumbers());
            // 보너스 번호와 일치하는지 확인
            boolean isBonusNumberMatch = isBonusNumberMatch(lottoSet, lottoWinningNumber.getBonusNumber());
            // 현재 로또의 결과를 기록
            updateWinningResults(matchedCount, isBonusNumberMatch, results);
        }
        return results;
    }

    public double computeProfitRate(Map<LottoRank, Integer> winningResults, PurchaseAmount purchaseAmount) {

        double totalWinningAmount = 0;
        for (LottoRank lottoRank : winningResults.keySet()) {
            totalWinningAmount += lottoRank.getWinningAmount() * winningResults.get(lottoRank);
        }


        double profilRate = totalWinningAmount / purchaseAmount.getAmount() * 100;
        return roundUpProfilRate(profilRate); // 첫째자리까지 반올림
    }

    /*
    private 메서드 시작
     */

    private static HashMap<LottoRank, Integer> initWinningResults() {
        HashMap<LottoRank, Integer> initWinningResults = new HashMap<>(LottoRank.values().length); // 등수 갯수 만큼 Map의 크기를 제한
        for (LottoRank lottoRank : LottoRank.values()) {
            initWinningResults.put(lottoRank, 0);
        }
        return initWinningResults;
    }
    private static int countMatchedNumbers(HashSet<Integer> lottoSet, List<Integer> winningNumbers) {
        int matchedCount = 0;
        for (int winningNumber : winningNumbers) {
            if (!lottoSet.add(winningNumber)) {
                matchedCount++;
            }
        }
        return matchedCount;
    }

    private static boolean isBonusNumberMatch(HashSet<Integer> lottoSet, int bonusNumber) {
        return !lottoSet.add(bonusNumber);
    }

    private static void updateWinningResults(
            int matchedCount,
            boolean isBonusNumberMatch,
            HashMap<LottoRank, Integer> results
    ) {
        for (LottoRank rank : LottoRank.values()) {
            // 일치하지 않은 경우 패스
            if (rank.getMatchedCount() != matchedCount) {
                continue;
            }
            // 5개 맞춘 경우 2등, 3등 구분 필요
            if (matchedCount == 5) {
                handleFiveMatch(rank, isBonusNumberMatch, results);
                return;
            }
            // 그외의 경우
            increment(results, rank);
            return;
        }
    }

    private static void handleFiveMatch(
            LottoRank rank,
            boolean isBonusNumberMatch,
            HashMap<LottoRank, Integer> results
    ) {
        // 2등: 보너스 일치 O
        if (isBonusNumberMatch && rank == LottoRank.SECOND) {
            increment(results, rank);
            return;
        }
        // 3등: 보너스 일치 X
        if (!isBonusNumberMatch && rank == LottoRank.THIRD) {
            increment(results, rank);
        }
    }

    private static void increment(HashMap<LottoRank, Integer> results, LottoRank rank) {
        results.merge(rank, 1, Integer::sum);
    }

    private static double roundUpProfilRate(double profilRate) {
        return Math.round(profilRate * Math.pow(10, ROUND_SCALE - 1)) / Math.pow(10, ROUND_SCALE - 1);
    }
}
