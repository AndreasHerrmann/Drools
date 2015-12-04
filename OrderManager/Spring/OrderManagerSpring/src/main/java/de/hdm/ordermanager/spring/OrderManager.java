package de.hdm.ordermanager.spring;

import java.util.NoSuchElementException;
import java.util.Vector;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.hdm.ordermanager.spring.OrderManager;
import de.hdm.ordermanager.spring.order.Item;
import de.hdm.ordermanager.spring.order.Order;

@RestController
@ResponseBody
public class OrderManager{
	private static Vector<Order> orders=new Vector<Order>();
	private static Vector<Item> items=new Vector<Item>();
	
	public OrderManager(){
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public @ResponseBody String message(){
		return "Gestartet";
	}
	
	@RequestMapping(value="/order", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody Order addOrder(Order order){
		long newId = 0;
		
		try{
			newId=orders.lastElement().getOrderNummer();
		}
		catch(NoSuchElementException nsee){
			newId=0;
		}
		order.setOrderNummer(newId);
		OrderManager.orders.add(order);
		System.out.println("Order");
		for(int i=0; i<order.getOrderPositionen().length;i++){
			System.out.println(order.getOrderPositionen()[i].getAnzahl());
		}
		
		return order;
		
	}
	
	@RequestMapping(value="/order", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody Object[] getOrders(){
		if(orders.size()>0){
			return OrderManager.orders.toArray();
		}
		else{
			return null;
		}
	}
	
	@RequestMapping(value="/order/{orderID}", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody Order getOrder(@PathVariable("orderID") long nummer){
		for(int i=0; i<orders.size(); i++){
			if(nummer==orders.get(i).getOrderNummer()){
				return orders.get(i);
			}
		}
		return null;
	}
	
	@RequestMapping(value="/item", method=RequestMethod.POST, produces="application/json")
	public Item addItem(@RequestBody Item item){
		long newId=0;
		try{
			newId=items.lastElement().getNummer()+1;
		}
		catch(NoSuchElementException nsee){
			newId=0;
		}
		item.setNummer(newId);
		OrderManager.items.add(item);
		System.out.println(item);
		
		return item;
	}
	
	@RequestMapping(value="/item", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody Object[] getItems(){
		if(items.size()>0){
			return OrderManager.items.toArray();
		}
		else{
			return null;
		}
	}
	
	@RequestMapping(value="/item/{itemID}", method=RequestMethod.GET, produces="application/json")
	public Item getItem(@PathVariable("itemdID")long nummer){
		for(int i=0;i<items.size(); i++){
			if(nummer==items.get(i).getNummer()){
				return items.get(i);
			}
		}
		return null;
	}
	
}
