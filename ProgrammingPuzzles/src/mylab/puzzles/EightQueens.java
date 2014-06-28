package mylab.puzzles;

import java.util.Stack;

/**
 * EightQueens
 *
 * Solution to 8 queens problem using y = mx+c as checking formula
 *
 * Indra Gunawan - June 29, 2014
 */

public class EightQueens {

    private static int MAX_QUEENS = 8;

    private Stack xpos = new Stack();
    private Stack ypos = new Stack();

    private int solutionCounter = 0;

    private boolean isValid(int col, int row) {
        for (int i = 0;i<xpos.size();i++) {
            int _col = (Integer)xpos.get(i);
            int _row = (Integer)ypos.get(i);

            if (_col == col) return false;

            int _c = _row - _col;
            int c = row - col;
            if (_c == c) return false;

            int dx = _col - col;
            int dy = _row - row;
            if (dx == -dy) return false;
        }

        return true;
    }

    private void printSolution() {
        int colIdx = 0;
        System.out.println("Solution #" + (solutionCounter + 1));
        solutionCounter++;
        for (int i = 0;i<MAX_QUEENS;i++) {
            StringBuffer sb = new StringBuffer();
            for (int j = 0;j<MAX_QUEENS;j++) {
                int col = (Integer)xpos.get(colIdx);

                if (j != col) sb.append("   |");
                else sb.append(" * |");
            }
            System.out.println(sb.toString());
            colIdx++;
        }
    }

    private void _solve(int row) {
        for (int col = 0;col<MAX_QUEENS;col++) {
            if (isValid(col, row)){
                xpos.push(col);
                ypos.push(row);

                if (row<MAX_QUEENS - 1) {
                    _solve(row+1);
                } else {
                    printSolution();
                }

                xpos.pop();
                ypos.pop();
            }
        }
    }

    public void solve() {
        _solve(0);
    }

    public static void main(String args[]) throws Exception {
        EightQueens problem = new EightQueens();
        problem.solve();
    }

}
