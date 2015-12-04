package de.hdm.ordermanager.spring.client;

import java.net.URI;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import de.hdm.ordermanager.spring.order.Order;
import de.hdm.ordermanager.spring.order.Item;

@SpringBootApplication
public class OrderManagerSpringClientApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(OrderManagerSpringClientApplication.class, args);
    }
    
    @Override
    public void run(String... strings) throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	String orderManagerURI = new String("http://localhost:8080/OrderManagerSpring");
    	URI itemURI = URI.create(orderManagerURI+"/item");
    	URI orderURI = URI.create(orderManagerURI+"/order");
    	
    	//Items erstellen
		Item karotte = new Item("Karotte",0.99F);
		Item kartoffel = new Item("Kartoffel",0.66F);
		Item zwiebel = new Item("Zwiebel",1.99F);
		Item sellerie = new Item("Sellerie",2.99F);
		karotte = restTemplate.postForObject(itemURI, karotte, Item.class);
		kartoffel = restTemplate.postForObject(itemURI, kartoffel, Item.class);
		zwiebel = restTemplate.postForObject(itemURI, zwiebel, Item.class);
		sellerie = restTemplate.postForObject(itemURI, sellerie, Item.class);
		
		//Order erstellen
		Order order1 = new Order();
		System.out.println(karotte.getName());
		order1.addPosition(5,karotte);
		order1.addPosition(2,kartoffel);
		System.out.println(order1.getOrderPositionen()[0].getAnzahl());
		order1 = restTemplate.postForObject(orderURI, order1, Order.class);
		System.out.println(order1.getOrderPositionen()[0].getAnzahl());
		
		System.out.println(order1);
    }
}
