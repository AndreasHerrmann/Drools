package de.hdm.stockprice;

import java.util.Date;

public class StockEvent {
	private Stock stock;
	private Date timestamp;
	private float openValue;
	private float closeValue;
	private float dailyHigh;
	private float dailyLow;
	
	public StockEvent(Stock stock, Date timestamp, float openValue, float closeValue,
			float dailyHigh, float dailyLow) {
		this.stock=stock;
		this.timestamp = timestamp;
		this.openValue = openValue;
		this.closeValue = closeValue;
		this.dailyHigh = dailyHigh;
		this.dailyLow = dailyLow;
	}

	public Stock getStock() {
		return stock;
	}

	public Date getTimestamp() {
		return timestamp;
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
}
