package de.hdm.ordermanager.jersey.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import de.hdm.ordermanager.jersey.order.*;

public class OrderManagerClient {
	
	public static void main(String[] args){
		Item karotte = new Item("Karotte",0.99F);
		Item kartoffel = new Item("Kartoffel",0.66F);
		Item zwiebel = new Item("Zwiebel",1.99F);
		Item sellerie = new Item("Sellerie",2.99F);
		
		Client client = ClientBuilder.newClient();
		WebTarget zielAdresse = client.target("http://localhost:8080/OrderManagerJersey/jersey");
		WebTarget itemTarget = zielAdresse.path("item");
		WebTarget orderTarget = zielAdresse.path("order");
		
		karotte = itemTarget.request(MediaType.APPLICATION_JSON)
			.put(Entity.entity(karotte, MediaType.APPLICATION_JSON))
			.readEntity(Item.class);
		kartoffel = itemTarget.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(kartoffel, MediaType.APPLICATION_JSON))
				.readEntity(Item.class);
		zwiebel = itemTarget.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(zwiebel, MediaType.APPLICATION_JSON))
				.readEntity(Item.class);
		sellerie = itemTarget.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(sellerie, MediaType.APPLICATION_JSON))
				.readEntity(Item.class);
		Order order1 = new Order();
		order1.addPosition(5,karotte);
		order1.addPosition(2,kartoffel);
		
		order1 = orderTarget.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(order1, MediaType.APPLICATION_JSON))
				.readEntity(Order.class);
		System.out.println(order1);
			
	}
}
