package de.hdm.ordermanager.resteasy.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.hdm.ordermanager.resteasy.order.Item;
import de.hdm.ordermanager.resteasy.order.Order;

/**
 * Stellt ein Interface zum RESTService des OrderManagers für den Client zur Verfügung.
 * Alle Anfragen an den Web-Server können dadurch über die Methoden des Interface geschehen.
 * @author Andreas Herrmann
 *
 */
public interface RestEasyProxyInterface {
	
	@PUT
	@Path("/order")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response addOrder (Order order);
	
	@GET
	@Path("/order")
	@Produces(MediaType.APPLICATION_JSON)
	Order[] getAllOrders();
	
	@GET
	@Path("/order/{nummer}")
	@Produces(MediaType.APPLICATION_JSON)
	Order getOrder(@PathParam("nummer") long nummer);
	
	@PUT
	@Path("/item")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response addItem(Item item);
	
	@GET
	@Path("/item")
	@Produces(MediaType.APPLICATION_JSON)
	Item[] getAllItems();
	
	@GET
	@Path("/item/{nummer}")
	@Produces(MediaType.APPLICATION_JSON)
	Order getItem(@PathParam("nummer") long nummer);
}
