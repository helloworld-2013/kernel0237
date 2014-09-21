package mylab.puzzles;

/**
 * Created by kernel0237 on 9/20/14.
 */
public class TradeTransaction {

    private Integer tradeId;
    private Integer tradeVersion;
    private String security;
    private Integer qty;
    private String direction;
    private String operation;
    private String acctNumber;

    public TradeTransaction(Integer tradeId, Integer tradeVersion, String security, Integer qty, String direction, String operation, String acctNumber) {
        this.tradeId = tradeId;
        this.tradeVersion = tradeVersion;
        this.security = security;
        this.qty = qty;
        this.direction = direction;
        this.operation = operation;
        this.acctNumber = acctNumber;
    }

    public TradeTransaction() {}

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getTradeVersion() {
        return tradeVersion;
    }

    public void setTradeVersion(Integer tradeVersion) {
        this.tradeVersion = tradeVersion;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getAcctNumber() {
        return acctNumber;
    }

    public void setAcctNumber(String acctNumber) {
        this.acctNumber = acctNumber;
    }
}
