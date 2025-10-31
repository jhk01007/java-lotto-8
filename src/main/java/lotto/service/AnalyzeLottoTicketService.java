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

import static lotto.domain.vo.LottoRank.SECOND;
import static lotto.domain.vo.LottoRank.THIRD;

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
        return roundUpProfitRate(profilRate); // 첫째자리까지 반올림
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
        // 2등 3등에 대해 먼저 처리
        if (handleSecondAndThird(matchedCount, isBonusNumberMatch, results)) {
            return;
        }
        // 나머지 등수(1,4,5등) 처리
        handleOtherRanks(matchedCount, results);
    }

    private static boolean handleSecondAndThird(int matchedCount, boolean isBonusNumberMatch, HashMap<LottoRank, Integer> results) {
        // 2등인 경우
        if(matchedCount == 5 && isBonusNumberMatch) {
            increment(results, SECOND);
            return true;
        }
        // 3등인 경우
        if(matchedCount == 5 && !isBonusNumberMatch) {
            increment(results, THIRD);
            return true;
        }
        return false;
    }

    private static void handleOtherRanks(int matchedCount, HashMap<LottoRank, Integer> results) {
        for (LottoRank lottoRank : results.keySet()) {
            // 2등이나 3등인 경우는 패스
            if(lottoRank.equals(SECOND) || lottoRank.equals(THIRD)) {
                continue;
            }
            if(lottoRank.getMatchedCount() == matchedCount) {
                increment(results, lottoRank);
            }
        }
    }

    private static void increment(HashMap<LottoRank, Integer> results, LottoRank rank) {
        results.merge(rank, 1, Integer::sum);
    }

    private static double roundUpProfitRate(double profitRate) {
        return Math.round(profitRate * Math.pow(10, ROUND_SCALE - 1)) / Math.pow(10, ROUND_SCALE - 1);
    }
}
