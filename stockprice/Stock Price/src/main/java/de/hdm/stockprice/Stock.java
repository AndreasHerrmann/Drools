package de.hdm.stockprice;


public class Stock {
	private String name="";
	private String symbol="";
	private Trend trend=Trend.stagnierend;
	
	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	public Stock(String name, String symbol) {
		super();
		this.name = name;
		this.symbol = symbol;
	}
	
	public Trend getTrend(){
		return trend;
	}
	public void setTrend(Trend trend){
		this.trend=trend;
	}
}
