package de.hdm.ordermanager.server;

import java.util.Vector;

import de.hdm.ordermanager.order.Item;
import de.hdm.ordermanager.order.Order;

public class OrderManager {
	private Vector<Order> orders;
	private Vector<Item> items;
	
	public OrderManager(){
		this.orders=new Vector<Order>();
		this.items=new Vector<Item>();
	}
	
	public void addOrder(Order order){
		this.orders.add(order);
	}
	
	public Vector<Order> getOrders(){
		return this.orders;
	}
	
	public Order getOrder(long nummer){
		for(int i=0; i<orders.size(); i++){
			if(nummer==orders.get(i).getOrderNummer()){
				return orders.get(i);
			}
		}
		return null;
	}
	
	public void addItem(Item item){
		this.items.add(item);
	}
	
	public Vector<Item> getItems(){
		return this.items;
	}
	
	public Item getItem(long nummer){
		for(int i=0;i<items.size(); i++){
			if(nummer==items.get(i).getNummer()){
				return items.get(i);
			}
		}
		return null;
	}
}
