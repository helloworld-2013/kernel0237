package mylab.puzzles;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by kernel0237 on 9/20/14.
 */
public class TradeSummary {

    private String acctNumber;
    private String security;
    private Integer qty = 0;
    private Set<Integer> tradeIDs = new LinkedHashSet<Integer>();

    public String getAcctNumber() {
        return acctNumber;
    }

    public void setAcctNumber(String acctNumber) {
        this.acctNumber = acctNumber;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Set<Integer> getTradeIDs() {
        return tradeIDs;
    }

    public void setTradeIDs(Set<Integer> tradeIDs) {
        this.tradeIDs = tradeIDs;
    }

    private String tradeIDsIntoString() {
        String results = "";

        for (Integer tradeID : tradeIDs)
            results += tradeID + ",";
        results = results.substring(0, results.lastIndexOf(","));

        return results;
    }

    public String toString() {
        return acctNumber + "|" + security + "|" + qty + "|" + tradeIDsIntoString();
    }

}
