package de.hdm.ordermanager.resteasy;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.hdm.ordermanager.resteasy.OrderManager;
import de.hdm.ordermanager.resteasy.order.Item;
import de.hdm.ordermanager.resteasy.order.Order;

@Path("/")
public class OrderManager{
	private String baseURI = "http://localhost:8080/OrderManagerRESTeasy/resteasy";
	private static Vector<Order> orders=new Vector<Order>();
	private static Vector<Item> items=new Vector<Item>();
	
	public OrderManager(){
	}
	@Path("/")
	@GET
	public String message(){
		return "Gestartet";
	}
	
	@Path("/order")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addOrder(Order order){
		long newId = 0;
		try{
			newId=orders.lastElement().getOrderNummer();
		}
		catch(NoSuchElementException nsee){
			newId=0;
		}
		order.setOrderNummer(newId);
		OrderManager.orders.add(order);
		URI createdURI = URI.create(baseURI+"/order/"
				+order.getOrderNummer());
		return Response.created(createdURI).entity(order).build();
	}
	
	@Path("/order")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object[] getOrders(){
		if(orders.size()>0){
			return OrderManager.orders.toArray();
		}
		else{
			return null;
		}
	}
	
	@Path("/order/{orderID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Order getOrder(@PathParam("orderID") long nummer){
		for(int i=0; i<orders.size(); i++){
			if(nummer==orders.get(i).getOrderNummer()){
				return orders.get(i);
			}
		}
		return null;
	}
	
	@Path("/item")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItem(Item item){
		long newId=0;
		try{
			newId=items.lastElement().getNummer()+1;
		}
		catch(NoSuchElementException nsee){
			newId=0;
		}
		item.setNummer(newId);
		OrderManager.items.add(item);
		String itemID = Long.toString(item.getNummer());
		URI createdURI = URI.create(baseURI+"/item/"+itemID);
		System.out.println(item);
		return Response.created(createdURI).entity(item).build();
	}
	
	@Path("/item")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object[] getItems(){
		if(items.size()>0){
			return OrderManager.items.toArray();
		}
		else{
			return null;
		}
	}
	
	@Path("/item/{itemID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Item getItem(@PathParam("itemdID")long nummer){
		for(int i=0;i<items.size(); i++){
			if(nummer==items.get(i).getNummer()){
				return items.get(i);
			}
		}
		return null;
	}
}
