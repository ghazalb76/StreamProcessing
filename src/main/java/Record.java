public class Record {
    private String time;
    private String exchangeCode;
    private String buyOrSell;
    private String tradingSymbol;
    private String cost;
    private String enteredShareNumber;

    public Record(String time, String exchangeCode, String buyOrSell, String tradingSymbol, String cost, String enteredShareNumber) {
        this.time = time;
        this.exchangeCode = exchangeCode;
        this.buyOrSell = buyOrSell;
        this.tradingSymbol = tradingSymbol;
        this.cost = cost;
        this.enteredShareNumber = enteredShareNumber;
    }

    public String getTime() {
        return time;
    }

    public String getexchangeCode() {
        return exchangeCode;
    }

    public String getbuyOrSell() {
        return buyOrSell;
    }

    public String getTradingSymbol() {
        return tradingSymbol;
    }

    public String getCost() {
        return cost;
    }

    public String getEnteredShareNumber() {
        return enteredShareNumber;
    }
}
