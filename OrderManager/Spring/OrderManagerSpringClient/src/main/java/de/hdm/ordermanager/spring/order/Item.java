package de.hdm.ordermanager.spring.order;

public class Item {
	private String name;
	private long nummer;
	private float preis;
	
	public Item(){
		
	}
	public Item(String name, float preis){
		this.name=name;
		this.nummer=0;
		this.preis=preis;
	}
	public String getName() {
		return name;
	}
	public long getNummer() {
		return nummer;
	}
	public float getPreis() {
		return preis;
	}
}
