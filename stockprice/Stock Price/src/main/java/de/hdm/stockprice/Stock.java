package de.hdm.stockprice;

public class Stock {
	private String name="";
	private String symbol="";
	
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

}
