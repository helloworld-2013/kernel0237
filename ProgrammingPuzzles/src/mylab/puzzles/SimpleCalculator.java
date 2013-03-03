package mylab.puzzles;

/**
 * SimpleCalculator
 *
 * A very simple calculator which only accept '+' and '-' and numbers
 *
 * Indra Gunawan - March 4, 2013
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SimpleCalculator {

    public static void main(String args[]) throws Exception {
        System.out.println("I am Gavin, how may I address you?");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String thyName = reader.readLine();

        System.out.println("Hello " + thyName + ", type your numbering problem below...");
        String thyProblem = reader.readLine();

        List<String> brain = new LinkedList<String>();

        Scanner _brain = new Scanner(thyProblem);

        // we need to change the sequence from 1 + 2 + 3... into 1 2 + 3 +...
        boolean hasOperator = false;
        while (_brain.hasNext()) {
            String token = _brain.next();
            if ("+-".indexOf(token) != -1) {
                brain.add(token);
                hasOperator = true;
            } else {
                if (hasOperator)
                    brain.add(brain.size() - 1,token);
                else
                    brain.add(token);

                hasOperator = false;
            }
        }

        // the sequence is ready, now process them...
        int result = 0;
        int _operand = 0;
        for (String brainCell : brain) {
            if ("+".indexOf(brainCell) != -1) {
                result += _operand;
            } else if ("-".indexOf(brainCell) != -1) {
                result -= _operand;
            } else {
                if (result == 0) result = Integer.parseInt(brainCell);
                else _operand = Integer.parseInt(brainCell);
            }
        }

        System.out.println("The answer is : " + result);
    }

}
