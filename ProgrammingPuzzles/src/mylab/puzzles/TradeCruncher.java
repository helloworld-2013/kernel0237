package mylab.puzzles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: iguana
 * Date: 21/9/14
 * Time: 11:28 PM
 *
 * Rules :
 * 1. Each unique account + security identifier combination creates one aggregated position.
 * 2. The position quantity will be incremented when a trade has following attributes :
 *      a) direction = BUY, operation = NEW or AMEND
 *      b) direction = SELL, operation = CANCEL
 *
 * 3. The position quantity will be decremented when a trade has following attributes :
 *      a) direction = SELL, operation = NEW or AMEND
 *      b) direction = BUY, operation = CANCEL
 *
 * 4. Multiple versions of a trade with the same trade ID can be processed, however
 *    the trade highest version transaction should remain part of the aggregated position
 *    record.
 */
public class TradeCruncher {

    public static void main(String args[]) throws Exception {
        TradeProcessor processor = new TradeProcessor();
        processor.processTransactions("trade_input.txt","trade_output.txt");
    }

}
