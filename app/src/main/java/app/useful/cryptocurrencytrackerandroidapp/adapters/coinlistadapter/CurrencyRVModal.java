package app.useful.cryptocurrencytrackerandroidapp.adapters.coinlistadapter;

public class CurrencyRVModal {
    private String name;
    private String symbol;
    private double price;
    private double percent_change_1h;
    private double percent_change_24h;
    private double percent_change_7d;

    private double percent_change_30d;
    private double percent_change_60d;
    private double percent_change_90d;

    private double market_cap;
    private double volume24h;
    private double dominance;

    public CurrencyRVModal(String name, String symbol, double price, double percent_change_1h, double percent_change_24h, double percent_change_7d, double percent_change_30d, double percent_change_60d, double percent_change_90d, double market_cap, double volume24h, double dominance) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.percent_change_1h = percent_change_1h;
        this.percent_change_24h = percent_change_24h;
        this.percent_change_7d = percent_change_7d;
        this.percent_change_30d = percent_change_30d;
        this.percent_change_60d = percent_change_60d;
        this.percent_change_90d = percent_change_90d;
        this.market_cap = market_cap;
        this.volume24h = volume24h;
        this.dominance = dominance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPercent_change_1h() {
        return percent_change_1h;
    }

    public void setPercent_change_1h(double percent_change_1h) {
        this.percent_change_1h = percent_change_1h;
    }

    public double getPercent_change_24h() {
        return percent_change_24h;
    }

    public void setPercent_change_24h(double percent_change_24h) {
        this.percent_change_24h = percent_change_24h;
    }

    public double getPercent_change_7d() {
        return percent_change_7d;
    }

    public void setPercent_change_7d(double percent_change_7d) {
        this.percent_change_7d = percent_change_7d;
    }

    public double getPercent_change_30d() {
        return percent_change_30d;
    }

    public void setPercent_change_30d(double percent_change_30d) {
        this.percent_change_30d = percent_change_30d;
    }

    public double getPercent_change_60d() {
        return percent_change_60d;
    }

    public void setPercent_change_60d(double percent_change_60d) {
        this.percent_change_60d = percent_change_60d;
    }

    public double getPercent_change_90d() {
        return percent_change_90d;
    }

    public void setPercent_change_90d(double percent_change_90d) {
        this.percent_change_90d = percent_change_90d;
    }

    public double getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(double market_cap) {
        this.market_cap = market_cap;
    }

    public double getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(double volume24h) {
        this.volume24h = volume24h;
    }

    public double getDominance() {
        return dominance;
    }

    public void setDominance(double dominance) {
        this.dominance = dominance;
    }
}
