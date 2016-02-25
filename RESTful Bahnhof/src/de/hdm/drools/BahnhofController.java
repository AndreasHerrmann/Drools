package de.hdm.drools;

import java.net.URI;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
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
import de.hdm.drools.nachricht.EinfahrtAnfrage;
import de.hdm.drools.nachricht.EinfahrtAnfrageMitAsyncResponse;
import de.hdm.drools.resource.Bahnhof;
import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

@Path("/")
public class BahnhofController {
	private static WebTarget netzverwaltung;
	private static Bahnhof bahnhof;
	private static Vector<Gleis> gleise;
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
			//Gleise erstellen. Erstes Gleis hat die Nummer 1
			gleise = new Vector<Gleis>();
			for (int i=0;i<bahnhof.getGleisAnzahl();i++){
				Gleis gleis = new Gleis(i+1);
				gleise.add(gleis);
				KnowledgeBaseThread.gleisEinfuegen(gleis);
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
		URI adresse=URI.create("http://"+req.getRemoteAddr());
		if(!adresse.equals(Config.netzverwaltungAdresse)){
			return Response.status(HttpStatus.FORBIDDEN_403).build();
		}
		else{
			abmelden();
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
			EinfahrtAnfrage fahrtanfrage){
		URI adresse=URI.create(req.getRemoteAddr());
		if(!adresse.equals(fahrtanfrage.getZug().getAdresse())){
			asyncResponse.resume(Response.status(HttpStatus.FORBIDDEN_403).build());
		}
		else{
			KnowledgeBaseThread.fahrtAnfragen(new EinfahrtAnfrageMitAsyncResponse(fahrtanfrage
							,asyncResponse));
		}
	}
	
	@Path("/einfahrtAnfragenOhneGleis")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void einfahrtAnfragenOhneGleis(@Context HttpServletRequest req,
			@Suspended final AsyncResponse asyncResponse,
			Zug zug){
		EinfahrtAnfrage fahrtanfrage = new EinfahrtAnfrage(zug,null);
		System.out.println(fahrtanfrage.getZug().getid());
		KnowledgeBaseThread.fahrtAnfragen(new EinfahrtAnfrageMitAsyncResponse(fahrtanfrage
						,asyncResponse));
	}
	
	@Path("/einfahrtMelden")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void einfahrtMelden(@Context HttpServletRequest req,
			@Suspended final AsyncResponse asyncResponse,
			@NotNull Einfahrt einfahrt){
		URI adresse=URI.create(req.getRemoteAddr());
		if(!adresse.equals(einfahrt.getZug().getAdresse())){
			asyncResponse.resume(Response.status(HttpStatus.FORBIDDEN_403).build());
		}
		else{
			KnowledgeBaseThread.einfahrtMelden(new EinfahrtMitAsyncResponse(einfahrt
							,asyncResponse));
		}
	}
	
	@Path("/abfahrtMelden")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response abfahrtMelden(@Context HttpServletRequest req,
			@NotNull Abfahrt abfahrt){
		URI adresse=URI.create(req.getRemoteAddr());
		if(!adresse.equals(abfahrt.getZug().getAdresse())){
			return Response.status(HttpStatus.FORBIDDEN_403).build();
		}
		else{
			KnowledgeBaseThread.abfahrtMelden(abfahrt);
			return Response.ok().build();
		}
	}
	
	@Path("/strecke")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findStreckenZuKnotenpunkt(@NotNull Knotenpunkt knotenpunkt){
		Vector<Strecke> gefundeneStrecken = new Vector<Strecke>();
		for(int i=0;i<wegfuehrendeStrecken.length;i++){
			if(wegfuehrendeStrecken[i].getZiel().equals(knotenpunkt)){
				gefundeneStrecken.add(wegfuehrendeStrecken[i]);
			}
		}
		return Response.ok(gefundeneStrecken.toArray()).build();
	}
}
