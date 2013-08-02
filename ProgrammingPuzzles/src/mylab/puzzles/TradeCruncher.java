package mylab.puzzles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: iguana
 * Date: 24/7/13
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

    private List<TradeTransaction> transactions = new LinkedList<TradeTransaction>();
    private List<TradeSummary> summary = new LinkedList<TradeSummary>();

    public void processInput() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("trade_input.txt"));
        String input;
        boolean header = true;
        do {
            input = reader.readLine();
            if (input!=null) {
                if (!header) {
                    String tokens[] = input.split(",");

                    TradeTransaction aTransaction = new TradeTransaction();
                    aTransaction.tradeID = Integer.parseInt(tokens[0]);
                    aTransaction.tradeVersion = Integer.parseInt(tokens[1]);
                    aTransaction.security = tokens[2];
                    aTransaction.qty = Integer.parseInt(tokens[3]);
                    aTransaction.direction = tokens[4];
                    aTransaction.acctNumber = tokens[5];
                    aTransaction.operation = tokens[6];

                    transactions.add(aTransaction);
                } else header = false;
            }
        } while (input!=null);

        Set<Integer> txIdxToExclude = new HashSet<Integer>();
        for (int i = 0;i<transactions.size() - 1;i++) {
            TradeTransaction tx1 = transactions.get(i);
            for (int j = i+1;j<transactions.size();j++) {
                TradeTransaction tx2 = transactions.get(j);

                if (tx1.tradeID.equals(tx2.tradeID) && tx1.tradeVersion < tx2.tradeVersion) {
                    txIdxToExclude.add(i);
                }
            }
        }

        List<TradeTransaction> _transactions = new LinkedList<TradeTransaction>();
        for (int i = 0;i<transactions.size();i++) {
            if (!txIdxToExclude.contains(i))
                _transactions.add(transactions.get(i));
        }

        transactions = _transactions;
    }

    private boolean isPlus(TradeTransaction aTransaction) {
        if ("BUY".equals(aTransaction.direction)) {
            if ("NEW".equals(aTransaction.operation) || "AMEND".equals(aTransaction.operation))
                return true;

            return false;
        }

        if ("SELL".equals(aTransaction.direction)) {
            if ("NEW".equals(aTransaction.operation) || "AMEND".equals(aTransaction.operation))
                return false;

            return true;
        }

        return true;
    }

    public void processOutput() {
        for (TradeTransaction aTransaction : transactions) {
            boolean foundTx = false;
            for (TradeSummary aSummary : summary) {
                if (aSummary.acctNumber.equals(aTransaction.acctNumber) &&
                    aSummary.security.equals(aTransaction.security)) {

                    if (isPlus(aTransaction))
                        aSummary.qty += aTransaction.qty;
                    else
                        aSummary.qty -= aTransaction.qty;
                    aSummary.tradeIDs.add(aTransaction.tradeID);

                    foundTx = true;
                }
            }
            if (!foundTx) {
                TradeSummary aSummary = new TradeSummary();
                aSummary.acctNumber = aTransaction.acctNumber;
                aSummary.security = aTransaction.security;
                if (isPlus(aTransaction))
                    aSummary.qty += aTransaction.qty;
                else
                    aSummary.qty -= aTransaction.qty;
                aSummary.tradeIDs.add(aTransaction.tradeID);

                summary.add(aSummary);
            }
        }

        for (TradeSummary aSummary : summary) {
            String result = aSummary.acctNumber + ";" + aSummary.security + ";" + aSummary.qty + ";";
            for (Integer tradeID : aSummary.tradeIDs)
                result += tradeID + ",";
            result = result.substring(0, result.length() - 1);

            System.out.println(result);
        }
    }

    public static void main(String args[]) throws Exception {
        TradeCruncher cruncher = new TradeCruncher();
        cruncher.processInput();
        cruncher.processOutput();
    }

    class TradeTransaction {

        public Integer tradeID;
        public Integer tradeVersion;
        public String security;
        public Integer qty;
        public String direction;
        public String acctNumber;
        public String operation;

    }

    class TradeSummary {

        public String acctNumber;
        public String security;
        public Integer qty = 0;
        public List<Integer> tradeIDs = new ArrayList<Integer>();

    }

}
