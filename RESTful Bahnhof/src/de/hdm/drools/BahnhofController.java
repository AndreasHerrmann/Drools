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
import de.hdm.drools.nachricht.Einfahrt;
import de.hdm.drools.nachricht.EinfahrtMitAsyncResponse;
import de.hdm.drools.nachricht.FahrtAnfrage;
import de.hdm.drools.nachricht.FahrtAnfrageMitAsyncResponse;
import de.hdm.drools.resource.Bahnhof;
import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;

@Path("/")
public class BahnhofController {
	private static WebTarget netzverwaltung;
	private static Bahnhof bahnhof=null;
	private static Gleis[] gleise;
	private static Strecke[] wegfuehrendeStrecken;
	
	public static boolean anmelden(){
		Client client = ClientBuilder.newClient();
		netzverwaltung = client.target(Config.netzverwaltungAdresse);
		WebTarget anmeldenTarget = netzverwaltung.path("/bahnhof/anmelden");
		Response resp=anmeldenTarget.request(MediaType.APPLICATION_JSON).post(null);
		if(resp.getStatus()==HttpStatus.OK_200){
			bahnhof=resp.readEntity(Bahnhof.class);
			WebTarget streckenTarget = netzverwaltung.path("/strecke/von/knotenpunkt");
			wegfuehrendeStrecken = new Strecke[0];
			for(int i=0;i<bahnhof.getKnotenpunkte().length;i++){
				Response resp2 = streckenTarget.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(bahnhof.getKnotenpunkt(i)));
				if(resp2.hasEntity()){
					Strecke[] neueStrecken = resp2.readEntity(Strecke[].class);
					Strecke[] neuerArray = new Strecke[wegfuehrendeStrecken
					                                   .length+neueStrecken.length];
					System.arraycopy(wegfuehrendeStrecken, 0, neuerArray, 0
							, wegfuehrendeStrecken.length);
					System.arraycopy(neueStrecken, 0, neuerArray
							, wegfuehrendeStrecken.length, neueStrecken.length);
					wegfuehrendeStrecken=neuerArray;
				}
			}
			return true;
		}
		else{
			System.out.println("Anmeldung nicht möglich: "+resp.toString());
			return false;
		}
				
	}
	public static void abmelden(){
		WebTarget abmeldenTarget = netzverwaltung.path("/bahnhof/abmelden");
		abmeldenTarget.request().post(Entity.json(bahnhof));
	}
	@Path("/erneutAnmelden")
	@POST
	public Response erneutAnmelden(@Context HttpServletRequest req){
		URI adresse=URI.create(req.getRemoteAddr()+Integer.toString(req.getRemotePort()));
		if(!adresse.equals(Config.netzverwaltungAdresse)){
			return Response.status(HttpStatus.FORBIDDEN_403).build();
		}
		else{
			boolean antwort = anmelden();
			if(antwort){
				return Response.ok().build();
			}
			else{
				return Response.serverError().build();
			}
		}
	}
	
	@Path("/einfahrtAnfragen")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void einfahrtAnfragen(@Context HttpServletRequest req,
			@Suspended final AsyncResponse asyncResponse,
			@NotNull FahrtAnfrage fahrtanfrage){
		URI adresse=URI.create(req.getRemoteAddr()+Integer.toString(req.getRemotePort()));
		if(!adresse.equals(fahrtanfrage.getZug().getAdresse())){
			asyncResponse.resume(Response.status(HttpStatus.FORBIDDEN_403).build());
		}
		else{
			for(int i=0;i<gleise.length;i++){
				if(fahrtanfrage.getGleis().equals(gleise[i])){
					KnowledgeBaseThread
					.fahrtAnfragen(new FahrtAnfrageMitAsyncResponse(fahrtanfrage
							,asyncResponse));
				}
			}
			asyncResponse.resume(Response.status(HttpStatus.NOT_FOUND_404).build());
		}
	}
	
	@Path("/einfahrtMelden")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void einfahrtMelden(@Context HttpServletRequest req,
			@Suspended final AsyncResponse asyncResponse,
			@NotNull Einfahrt einfahrt){
		URI adresse=URI.create(req.getRemoteAddr()+Integer.toString(req.getRemotePort()));
		if(!adresse.equals(einfahrt.getZug().getAdresse())){
			asyncResponse.resume(Response.status(HttpStatus.FORBIDDEN_403).build());
		}
		else{
			for(int i=0;i<gleise.length;i++){
				if(einfahrt.getGleis().equals(gleise[i])){
					KnowledgeBaseThread
					.einfahrtMelden(new EinfahrtMitAsyncResponse(einfahrt
							,asyncResponse));
				}
			}
			asyncResponse.resume(Response.status(HttpStatus.NOT_FOUND_404).build());
		}
	}
	
	@Path("/abfahrtMelden")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response abfahrtMelden(@Context HttpServletRequest req,
			@NotNull Abfahrt abfahrt){
		URI adresse=URI.create(req.getRemoteAddr()+Integer.toString(req.getRemotePort()));
		if(!adresse.equals(abfahrt.getZug().getAdresse())){
			return Response.status(HttpStatus.FORBIDDEN_403).build();
		}
		else{
			KnowledgeBaseThread.abfahrtMelden(abfahrt);
			return Response.ok().build();
		}
	}
	
	@Path("/strecken")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findStreckenZuKnotenpunkt(@NotNull Knotenpunkt knotenpunkt){
		Vector<Strecke> gefundeneStrecken = new Vector<Strecke>();
		for(int i=0;i<wegfuehrendeStrecken.length;i++){
			if(wegfuehrendeStrecken[i].getZiel().equals(knotenpunkt)){
				gefundeneStrecken.add(wegfuehrendeStrecken[i]);
			}
		}
		if(gefundeneStrecken.size()>0){
			return Response.ok(gefundeneStrecken.toArray()).build();
		}
		else{
			return Response.status(HttpStatus.NOT_FOUND_404).build();
		}
	}
}
