package mylab.puzzles;

/**
 * Letter Stamper Puzzle
 *
 * Best description can be found here : https://code.google.com/codejam/contest/801485/dashboard
 *
 * Indra Gunawan - September 2, 2012
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class LetterStamper {

    public int solve(String letters) {
        int result = 0;

        Stack<String> machine = new Stack<String>();
        for (int i = 0;i<letters.length();i++) {
            String letter = String.valueOf(letters.charAt(i));

            String _letter = null;
            if (!machine.empty()) _letter = machine.peek();

            if (_letter != null && letter.equals(_letter)) {
                System.out.println("Print " + letter);
                result++;
            } else {
                int position = machine.search(letter);
                if (position == -1) {
                    machine.push(letter);
                    System.out.println("Push " + letter);
                    result++;
                    System.out.println("Print " + letter);
                    result++;
                } else {
                    for (int j = 0;j < position;j++) {
                        _letter = machine.peek();
                        if (letter.equals(_letter)) {
                            System.out.println("Print " + _letter);
                            result++;
                        } else {
                            _letter = machine.pop();
                            System.out.println("Pop " + _letter);
                            result++;
                        }
                    }
                }
            }
        }

        while (!machine.empty()) {
            String _letter = machine.pop();
            System.out.println("Pop " + _letter);
            result++;
        }

        return result;
    }

    public static void main(String args[]) throws Exception {
        LetterStamper problem = new LetterStamper();

        BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

        String numberOfTestCases = userInputReader.readLine();
        for (int i = 0;i < Integer.parseInt(numberOfTestCases);i++) {
            String letters = userInputReader.readLine();
            int result = problem.solve(letters);
            System.out.println("Case #" + (i + 1) + ": " + result);
        }
    }

}
