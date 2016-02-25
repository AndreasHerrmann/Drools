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

/**
 * REST-Controller des Bahnhofs. Verarbeitet alle Anfragen, die an den Bahnhof gesendet werden.
 * @author Andreas Herrmann
 * @param netzverwaltung Ein {@link javax.ws.rs.client.WebTarget}, das auf die Netzverwaltung zeigt
 * @param bahnhof Beinhaltet die Daten des Bahnhofs, die im von der Netzverwaltung geschickt werden
 * @param gleise Die Gleise des Bahnhofs
 * @param wegfuehrendeStrecken Alle Strecken, die vom Bahnhof wegfuehren
 */
@Path("/")
public class BahnhofController {
	/**
	 * Ein {@link javax.ws.rs.client.WebTarget}, das auf die Netzverwaltung zeigt.
	 * Wird aus der Adresse in {@link Config} erstellt.
	 */
	private static WebTarget netzverwaltung;
	/**
	 * Daten des Bahnhofs, die ihm bei der Anmeldung von der Netzverwaltung übergeben wurden.
	 */
	private static Bahnhof bahnhof;
	/**
	 * Alle Gleise des Bahnhofs
	 */
	private static Vector<Gleis> gleise;
	/**
	 * Alle Strecken, die vom Bahnhof wegführen. Wird dem Bahnhof von der Netzverwaltung übergeben.
	 */
	private static Strecke[] wegfuehrendeStrecken;
	
	/**
	 * Methode zum Anmelden bei der Netzverwaltung. Erstellt außerdem alle Gleise
	 * und fragt die wegführenden Strecken bei der Netzverwaltung ab.
	 * @return {@code true}, wenn die Anmeldung erfolgreich war, ansonsten {@code false}
	 */
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
	/**
	 * Methode zum Abmelden bei der Netzverwaltung
	 */
	public static void abmelden(){
		WebTarget abmeldenTarget = netzverwaltung.path("/bahnhof/abmelden");
		abmeldenTarget.request().post(Entity.json(bahnhof));
	}
	/**
	 * Dient zum erneuten Anmelden bei der Netzverwaltung, falls dies erforderlich
	 * sein sollte. Kann nur von der Netzverwaltung ausgelöst werden.
	 * @param req {@link javax.servlet.http.HttpServletRequest}, die automatisch aus der Anfrage übernommen wird
	 * @return {@link javax.ws.rs.core.Response}, die den Status der Anfrage enthält
	 */
	@Path("/erneutAnmelden")
	@POST
	public Response erneutAnmelden(@Context HttpServletRequest req){
		URI adresse=URI.create("http://"+req.getRemoteAddr()+":8080");
		if(!adresse.equals(URI.create(Config.netzverwaltungAdresse))){
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
	
	/**
	 * Dient zum Anfragen einer Einfahrterlaubnis.
	 * @param req {@link javax.servlet.http.HttpServletRequest}, die automatisch aus der Anfrage übernommen wird
	 * @param asyncResponse {@link javax.ws.rs.container.AsyncResponse}, zum beantworten aus dem {@link KnowledgeBaseThread}
	 * @param fahrtanfrage Die {@link de.hdm.drools.nachricht.EinfahrtAnfrage}, die beantwortet werden soll
	 */
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
	
	/**
	 * Dient zum Anfragen einer Einfahrterlaubnis ohne die Angabe eines {@link de.hdm.drools.resource.Gleis}
	 * @param req {@link javax.servlet.http.HttpServletRequest}, die automatisch aus der Anfrage übernommen wird
	 * @param asyncResponse {@link javax.ws.rs.container.AsyncResponse}, zum beantworten aus dem {@link KnowledgeBaseThread}
	 * @param zug Der {@link de.hdm.drools.resource.Zug} auf den sich die Anfrage bezieht
	 */
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
	
	/**
	 * Dient zum Melden einer Einfahrt
	 * @param req {@link javax.servlet.http.HttpServletRequest}, die automatisch aus der Anfrage übernommen wird
	 * @param asyncResponse {@link javax.ws.rs.container.AsyncResponse}, zum beantworten aus dem {@link KnowledgeBaseThread}
	 * @param einfahrt Die {@link de.hdm.drools.nachricht.Einfahrt} auf die sich die Meldung bezieht
	 */
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
	
	/**
	 * Dient zum Melden einer Abfahrt
	 * @param req {@link javax.servlet.http.HttpServletRequest}, die automatisch aus der Anfrage übernommen wird
	 * @param asyncResponse {@link javax.ws.rs.container.AsyncResponse}, zum beantworten aus dem {@link KnowledgeBaseThread}
	 * @param abfahrt Die {@link de.hdm.drools.nachricht.Abfahrt} auf die sich die Meldung bezieht
	 * @return {@link javax.ws.rs.core.Response}, die den Status der Anfrage enthält
	 */
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
	
	/**
	 * Dient zum Abfragen der Strecken, die von diesem {@link de.hdm.drools.resource.Bahnhof}
	 * zum übergebenen {@link de.hdm.drools.resource.Knotenpunkt} führen
	 * @param knotenpunkt Der {@link de.hdm.drools.resource.Knotenpunkt} zu dem die Strecken führen sollen
	 * @return {@link javax.ws.rs.core.Response}, die die gefundenen Strecken enthält
	 */
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
