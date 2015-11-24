package de.hdm.ordermanager.resteasy.order;

public class OrderPosition {
	private int anzahl;
	private Item item;
	
	public OrderPosition(){
		
	}
	public OrderPosition(int anzahl,Item item){
		this.anzahl=anzahl;
		this.item=item;
	}

	public int getAnzahl() {
		return anzahl;
	}

	public Item getItem() {
		return item;
	}
}
