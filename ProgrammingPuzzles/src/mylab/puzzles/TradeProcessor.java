package mylab.puzzles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by kernel0237 on 9/20/14.
 */
public class TradeProcessor {

    private List<TradeTransaction> rawTransactions = new LinkedList<TradeTransaction>();
    private Map<Integer,TradeTransaction> halfBakedTransactions = new HashMap<Integer,TradeTransaction>();
    private List<TradeSummary> summaries = new LinkedList<TradeSummary>();

    private void readFromFile(String inputFileName) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        String input = null;
        boolean header = true;
        do {
            input = reader.readLine();
            if (input!=null) {
                if (!header) {
                    StringTokenizer inputTokenizer = new StringTokenizer(input,"|");

                    TradeTransaction transaction = new TradeTransaction();
                    transaction.setTradeId(new Integer(inputTokenizer.nextToken()));
                    transaction.setTradeVersion(new Integer(inputTokenizer.nextToken()));
                    transaction.setSecurity(inputTokenizer.nextToken());
                    transaction.setQty(new Integer(inputTokenizer.nextToken()));
                    transaction.setDirection(inputTokenizer.nextToken());
                    transaction.setAcctNumber(inputTokenizer.nextToken());
                    transaction.setOperation(inputTokenizer.nextToken());

                    rawTransactions.add(transaction);
                } else header = false;
            }
        } while (input!=null);
    }

    private void writeIntoFile(String outputFileName) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
        for (TradeSummary summary : summaries) {
            writer.write(summary.toString());
            writer.newLine();
        }
        writer.close();
    }

    private Integer retrieveQty(TradeTransaction transaction) {
        if ("BUY".equals(transaction.getDirection())) {
            if ("NEW".equals(transaction.getOperation()) || "AMEND".equals(transaction.getOperation()))
                return transaction.getQty();

            return transaction.getQty() * -1;
        }

        if ("SELL".equals(transaction.getDirection())) {
            if ("NEW".equals(transaction.getOperation()) || "AMEND".equals(transaction.getOperation()))
                return transaction.getQty() * -1;

            return transaction.getQty();
        }

        return 0;
    }

    private void processRawTransactions() {
        for (TradeTransaction transaction : rawTransactions) {
            String acctNumber = transaction.getAcctNumber();
            String security = transaction.getSecurity();
            Integer tradeId = transaction.getTradeId();

            // capture ONLY latest tx with highest versions
            TradeTransaction _transaction = halfBakedTransactions.get(tradeId);
            if (_transaction!=null &&
                _transaction.getTradeId().equals(transaction.getTradeId()) &&
                _transaction.getTradeVersion()<transaction.getTradeVersion() || _transaction==null) {
                halfBakedTransactions.put(tradeId, transaction);
            }

            // capture all acct numbers and securities
            boolean foundSummary = false;
            for (TradeSummary summary : summaries) {
                if (summary.getAcctNumber().equals(acctNumber) &&
                    summary.getSecurity().equals(security)) {
                    summary.getTradeIDs().add(tradeId);

                    foundSummary = true;
                }
            }
            if (!foundSummary) {
                TradeSummary tradeSummary = new TradeSummary();
                tradeSummary.setAcctNumber(acctNumber);
                tradeSummary.setSecurity(security);
                tradeSummary.getTradeIDs().add(tradeId);

                summaries.add(tradeSummary);
            }
        }
    }

    private void processSummaries() {
        for (TradeSummary summary : summaries) {
            Set<Integer> tradeIDs = summary.getTradeIDs();
            Integer qty = 0;
            for (Integer tradeID : tradeIDs) {
                TradeTransaction transaction = halfBakedTransactions.get(tradeID);
                if (transaction.getAcctNumber().equals(summary.getAcctNumber()) &&
                    transaction.getSecurity().equals(summary.getSecurity()))
                    qty += retrieveQty(transaction);
            }
            summary.setQty( summary.getQty() + qty );
        }
    }

    private void processTransactions(String outputFileName) throws Exception {
        // if no input to process then throw error
        if (rawTransactions==null || rawTransactions.size()==0)
            throw new Exception("No input to be processed.");
        // if no output file name to contain processed transactions then throw error
        if (outputFileName==null || "".equals(outputFileName))
            throw new Exception("No output can be created.");

        processRawTransactions();
        processSummaries();
        writeIntoFile(outputFileName);
    }

    public List<TradeTransaction> getRawTransactions() {
        return rawTransactions;
    }

    public List<TradeSummary> getSummaries() {
        return summaries;
    }

    public void processTransactions(String inputFileName, String outputFileName) throws Exception {
        readFromFile(inputFileName);
        processTransactions(outputFileName);
    }

    public void processTransactions(List<TradeTransaction> rawTransactions, String outputFileName) throws Exception {
        this.rawTransactions = rawTransactions;
        processTransactions(outputFileName);
    }

}
