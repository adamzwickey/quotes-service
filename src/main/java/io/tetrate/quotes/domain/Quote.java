package io.tetrate.quotes.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a point in time price and information for a company's stock.
 * 
 * @author Adam Zwickey
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
/*
 * {
  "avgTotalVolume": 3066857,
  "calculationPrice": "close",
  "change": -0.48,
  "changePercent": -0.00414,
  "close": null,
  "closeSource": "official",
  "closeTime": null,
  "companyName": "Vmware Inc. - Class A",
  "currency": "USD",
  "delayedPrice": null,
  "delayedPriceTime": null,
  "extendedChange": null,
  "extendedChangePercent": null,
  "extendedPrice": null,
  "extendedPriceTime": null,
  "high": null,
  "highSource": "15 minute delayed price",
  "highTime": 1640206800004,
  "iexAskPrice": 0,
  "iexAskSize": 0,
  "iexBidPrice": 0,
  "iexBidSize": 0,
  "iexClose": 115.48,
  "iexCloseTime": 1640206794731,
  "iexLastUpdated": 1640206794731,
  "iexMarketPercent": 0.11579014968137134,
  "iexOpen": 116.5,
  "iexOpenTime": 1640183401055,
  "iexRealtimePrice": 115.48,
  "iexRealtimeSize": 4,
  "iexVolume": 351591,
  "lastTradeTime": 1640206799976,
  "latestPrice": 115.4,
  "latestSource": "Close",
  "latestTime": "December 22, 2021",
  "latestUpdate": 1640206802252,
  "latestVolume": null,
  "low": null,
  "lowSource": "15 minute delayed price",
  "lowTime": 1640196071189,
  "marketCap": 12870571347,
  "oddLotDelayedPrice": null,
  "oddLotDelayedPriceTime": null,
  "open": null,
  "openTime": null,
  "openSource": "official",
  "peRatio": 24.09,
  "previousClose": 115.88,
  "previousVolume": 1985438,
  "primaryExchange": "NEW YORK STOCK EXCHANGE INC.",
  "symbol": "VMW",
  "volume": null,
  "week52High": 172,
  "week52Low": 108.8,
  "ytdChange": -0.17796004848139166,
  "isUSMarketOpen": false
}
 */
	private String symbol;
	private String companyName;
	private BigDecimal latestPrice;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal change;

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public BigDecimal getLatestPrice() {
		return latestPrice;
	}
	public void setLatestPrice(BigDecimal latestPrice) {
		this.latestPrice = latestPrice;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
  
    public BigDecimal getHigh() {
    return high;
  }
  public void setHigh(BigDecimal high) {
    this.high = high;
  }
  public BigDecimal getLow() {
    return low;
  }
  public void setLow(BigDecimal low) {
    this.low = low;
  }
  public BigDecimal getChange() {
    return change;
  }
  public void setChange(BigDecimal change) {
    this.change = change;
  }
    @Override
    public String toString() {
        return "Quote [change=" + change + ", companyName=" + companyName + ", high=" + high + ", latestPrice="
                + latestPrice + ", low=" + low + ", symbol=" + symbol + "]";
    }

}
