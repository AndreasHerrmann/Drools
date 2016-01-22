package de.hdm.drools;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;

public class KnotenpunktController {
	private static WebTarget netzverwaltung;
	private static Knotenpunkt knotenpunkt;
	private static Strecke[] wegfuehrendeStrecken;
	
	public static void anmelden(){
		Client client = ClientBuilder.newClient();
		netzverwaltung = client.target(Config.netzverwaltungAdresse);
		WebTarget anmeldenTarget = netzverwaltung.path("/knotenpunkt/anmelden");
		Response resp=anmeldenTarget.request(MediaType.APPLICATION_JSON).post(null);
		if(resp.getStatus()==HttpStatus.OK_200){
			knotenpunkt = resp.readEntity(Knotenpunkt.class);
			WebTarget streckenTarget = netzverwaltung.path("/strecke/von/knotenpunkt");
			wegfuehrendeStrecken = streckenTarget.request(MediaType.APPLICATION_JSON)
			.post(Entity.json(knotenpunkt)).readEntity(Strecke[].class);
		}
	}
}
