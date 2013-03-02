package mylab.puzzles;

/**
 * Coin Denomination Puzzle
 *
 * Best description can be found here : http://en.wikipedia.org/wiki/Change-making_problem
 *
 * Indra Gunawan - March 3, 2013
 */

import java.util.ArrayList;
import java.util.List;

public class CoinDenomination {

    private int total;
    private int[] coins;

    public CoinDenomination(int total, int[] coins) {
        this.total = total;
        this.coins = coins;
    }

    public void solve() {
        _solve(total, new ArrayList<String>(), 0);
    }

    private void _solve(int _total, List<String> _coins, int startIdx) {
        for (int i = startIdx;i<coins.length;i++) {
            _coins.add(String.valueOf(coins[i]));
            int aCoin = coins[i];
            int aTotal = _total - aCoin;
            if (aTotal==0) {
                System.out.println(_coins);
            } else if (aTotal>0) {
                _solve(aTotal, _coins, i);
            }
            _coins.remove(_coins.size() - 1);
        }
    }

    public static void main(String args[]) throws Exception {
        CoinDenomination problem = new CoinDenomination(4, new int[] {1,2,3});
        problem.solve();
    }

}
