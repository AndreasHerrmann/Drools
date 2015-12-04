package de.hdm.ordermanager.spring.order;

import java.util.Vector;

public class Order{

	/**
	 * 
	 */
	private long orderNummer;
	private float gesamtPreis;
	private Vector<OrderPosition> orderPositionen = new Vector<OrderPosition>();
	
	public Order(){
		
	}
	public Order(long nummer){
		this.orderNummer=nummer;
		this.gesamtPreis=0.0F;
	}

	public long getOrderNummer() {
		return orderNummer;
	}

	public float getGesamtPreis() {
		return gesamtPreis;
	}
	
	public void addPosition(OrderPosition orderPosition){
		orderPositionen.add(orderPosition);
		float preis = 0.0F;
		preis= (orderPosition.getAnzahl()*orderPosition.getItem().getPreis());
		this.gesamtPreis+=preis;
	}
	
	public OrderPosition[] getOrderPositionen() {
		return orderPositionen.toArray(new OrderPosition[0]);
	}

	public boolean equals(Order order){
		if(this.orderNummer==order.getOrderNummer()){
			return true;
		}
		else{
			return false;
		}
	}

	public void setOrderNummer(long orderNummer) {
		this.orderNummer = orderNummer;
	}
}
