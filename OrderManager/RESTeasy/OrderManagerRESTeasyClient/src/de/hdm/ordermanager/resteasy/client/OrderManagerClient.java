package de.hdm.ordermanager.resteasy.client;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import de.hdm.ordermanager.resteasy.order.Order;
import de.hdm.ordermanager.resteasy.order.Item;


public class OrderManagerClient {
	public static void main (String[] args){
		Item karotte = new Item("Karotte",0.99F);
		Item kartoffel = new Item("Kartoffel",0.66F);
		Item zwiebel = new Item("Zwiebel",1.99F);
		Item sellerie = new Item("Sellerie",2.99F);
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget zielAdresse = client.target("http://localhost:8080/OrderManagerRESTeasy/resteasy");
		//Anfragen des Clients über das Proxy Interface umleiten
		RestEasyProxyInterface repi = zielAdresse.proxy(RestEasyProxyInterface.class);
		
		//Die Items an den Server schicken und das jeweilige Antwortitem speichern
		karotte = repi.addItem(karotte).readEntity(Item.class);
		kartoffel = repi.addItem(kartoffel).readEntity(Item.class);
		zwiebel = repi.addItem(zwiebel).readEntity(Item.class);
		sellerie = repi.addItem(sellerie).readEntity(Item.class);
		
		//Die Order erstellen
		Order order1 = new Order();
		order1.addPosition(5,karotte);
		order1.addPosition(2,kartoffel);
		
		//Und an den Server schicken und die Antwortorder speichern
		order1 = repi.addOrder(order1).readEntity(Order.class);
		
		System.out.println(order1);
	}
}
