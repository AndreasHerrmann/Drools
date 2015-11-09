package de.hdm.stockprice.event;

public class Wertverlust {
	private StockEvent event;
	private float menge;
	
	public Wertverlust(StockEvent event, float menge){
		this.event=event;
		this.menge=menge;
	}
	
	public StockEvent getStockEvent(){
		return event;
	}
	
	public float getMenge(){
		return menge;
	}
}
