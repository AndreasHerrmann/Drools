package de.hdm.ordermanager.order;

import java.util.Vector;

public class Order extends Vector<OrderPosition>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long orderNummer;
	private float gesamtPreis;
	
	public Order(long nummer){
		this.orderNummer=nummer;
	}

	public long getOrderNummer() {
		return orderNummer;
	}

	public float getGesamtPreis() {
		return gesamtPreis;
	}
	
	public void addPosition(OrderPosition orderPosition){
		this.add(orderPosition);
		this.gesamtPreis+=(orderPosition.getAnzahl()*orderPosition.getItem().getPreis());
	}
}
