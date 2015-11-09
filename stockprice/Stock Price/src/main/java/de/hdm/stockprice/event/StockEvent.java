package de.hdm.stockprice.event;

import de.hdm.stockprice.Stock;

public class StockEvent {
	private Stock stock;
	private float openValue;
	private float closeValue;
	private float dailyHigh;
	private float dailyLow;
	
	public StockEvent(Stock stock, float openValue, float closeValue,
			float dailyHigh, float dailyLow) {
		this.stock=stock;
		this.openValue = openValue;
		this.closeValue = closeValue;
		this.dailyHigh = dailyHigh;
		this.dailyLow = dailyLow;
	}

	public Stock getStock() {
		return stock;
	}

	public float getOpenValue() {
		return openValue;
	}

	public float getCloseValue() {
		return closeValue;
	}

	public float getDailyHigh() {
		return dailyHigh;
	}

	public float getDailyLow() {
		return dailyLow;
	}
	
	@Override
	public String toString(){
		return new String(openValue+" "+closeValue+" "+dailyHigh+" "+dailyLow);
		
	}
}
