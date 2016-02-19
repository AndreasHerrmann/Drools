package de.hdm.drools;

import java.net.URI;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import de.hdm.drools.nachricht.Abfahrt;
import de.hdm.drools.nachricht.AuffahrtAnfrageMitAsyncResponse;
import de.hdm.drools.nachricht.AuffahrtMeldungMitAsyncResponse;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

@Path("/")
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
	
	public static void abmelden(){
		WebTarget abmeldenTarget = netzverwaltung.path("/knotenpunkt/abmelden");
		abmeldenTarget.request().post(Entity.json(knotenpunkt));
	}
	
	@Path("/strecke")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findStreckeByZiel(Knotenpunkt knotenpunkt){
		Vector<Strecke> gefundeneStrecken = new Vector<Strecke>();
		for (int i=0;i<wegfuehrendeStrecken.length;i++){
			if(wegfuehrendeStrecken[i].getZiel().equals(knotenpunkt)){
				gefundeneStrecken.add(wegfuehrendeStrecken[i]);
			}
		}
		if(gefundeneStrecken.size()>0){
			Strecke[] strecken = new Strecke[gefundeneStrecken.size()];
			return Response.ok(gefundeneStrecken.toArray(strecken)).build();
		}
		else{
			return Response.noContent().build();
		}
	}
	
	@Path("/auffahrtAnfragen/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void auffahrtAnfragen(@Context HttpServletRequest req,
			@Suspended final AsyncResponse asyncResponse,
			@NotNull Zug zug){
		URI adresse=URI.create(req.getRemoteAddr()+Integer.toString(req.getRemotePort()));
		if(!adresse.equals(zug.getAdresse())){
			asyncResponse.resume(Response.status(HttpStatus.BAD_REQUEST_400).build());
		}
		else{
			KnowledgeBaseThread.auffahrtAnfragen(new AuffahrtAnfrageMitAsyncResponse(asyncResponse,zug));
		}
	}
	
	@Path("/auffahrtMelden/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void auffahrtMelden(@Context HttpServletRequest req,
			@Suspended final AsyncResponse asyncResponse,
			@NotNull Zug zug){
		URI adresse=URI.create(req.getRemoteAddr()+Integer.toString(req.getRemotePort()));
		if(!adresse.equals(zug.getAdresse())){
			asyncResponse.resume(Response.status(HttpStatus.BAD_REQUEST_400).build());
		}
		else{
			KnowledgeBaseThread.auffahrtMelden(new AuffahrtMeldungMitAsyncResponse(zug,asyncResponse));
		}
	}
	
	@Path("/abfahrtMelden/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response abfahrtMelden(@Context HttpServletRequest req,@NotNull Zug zug){
		URI adresse=URI.create(req.getRemoteAddr()+Integer.toString(req.getRemotePort()));
		if(!adresse.equals(zug.getAdresse())){
			return Response.status(HttpStatus.BAD_REQUEST_400).build();
		}
		else{
			KnowledgeBaseThread.abfahrtMelden(new Abfahrt(zug));
			return Response.ok().build();
		}
	}
}
