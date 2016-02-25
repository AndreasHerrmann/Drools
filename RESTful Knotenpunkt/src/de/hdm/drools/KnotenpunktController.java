package de.hdm.drools;

import java.util.Vector;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import de.hdm.drools.nachricht.Abfahrt;
import de.hdm.drools.nachricht.AuffahrtAnfrageMitAsyncResponse;
import de.hdm.drools.nachricht.AuffahrtMeldungMitAsyncResponse;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

/**
 * REST-Controller des Knotenpunkts. Verarbeitet alle Anfragen, die an den Knotenpunkt gesendet werden.
 * @author Andreas Herrmann
 * @param netzverwaltung Ein {@link javax.ws.rs.client.WebTarget}, das auf die Netzverwaltung zeigt
 * @param knotenpunkt Beinhaltet die Daten des Knotenpunkts, die ihm von der Netzverwaltung geschickt werden
 * @param wegfuehrendeStrecken Alle Strecken, die vom Knotenpunkt wegfuehren
 */
@Path("/")
public class KnotenpunktController {
	/**
	 * Ein {@link javax.ws.rs.client.WebTarget}, das auf die Netzverwaltung zeigt.
	 * Wird aus der Adresse in {@link Config} erstellt.
	 */
	private static WebTarget netzverwaltung;
	/**
	 * Daten des Knotenpunkts, die ihm bei der Anmeldung von der Netzverwaltung übergeben wurden.
	 */
	private static Knotenpunkt knotenpunkt;
	/**
	 * Alle Strecken, die vom Knotenpunkt wegführen. Wird dem Knotenpunkt von der Netzverwaltung übergeben.
	 */
	private static Strecke[] wegfuehrendeStrecken;
	
	/**
	 * Methode zum Anmelden bei der Netzverwaltung. Fragt die wegführenden Strecken
	 * bei der Netzverwaltung ab.
	 */
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
	
	/**
	 * Methode zum Abmelden bei der Netzverwaltung
	 */
	public static void abmelden(){
		WebTarget abmeldenTarget = netzverwaltung.path("/knotenpunkt/abmelden");
		abmeldenTarget.request().post(Entity.json(knotenpunkt));
	}
	
	/**
	 * Dient zum Abfragen der Strecken, die von diesem {@link de.hdm.drools.resource.Knotenpunkt}
	 * zum übergebenen {@link de.hdm.drools.resource.Knotenpunkt} führen
	 * @param knotenpunkt Der {@link de.hdm.drools.resource.Knotenpunkt} zu dem die Strecken führen sollen
	 * @return {@link javax.ws.rs.core.Response}, die die gefundenen Strecken enthält
	 */
	@Path("/strecke")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findStreckeByZiel(@NotNull Knotenpunkt knotenpunkt){
		Vector<Strecke> gefundeneStrecken = new Vector<Strecke>();
		for (int i=0;i<wegfuehrendeStrecken.length;i++){
			if(wegfuehrendeStrecken[i].getZiel().equals(knotenpunkt)){
				gefundeneStrecken.add(wegfuehrendeStrecken[i]);
			}
		}
		Strecke[] strecken = new Strecke[gefundeneStrecken.size()];
		return Response.ok(gefundeneStrecken.toArray(strecken)).build();
	}
	
	/**
	 * Dient zum Anfragen einer Auffahrterlaubnis.
	 * @param asyncResponse {@link javax.ws.rs.container.AsyncResponse}, zum beantworten aus dem {@link KnowledgeBaseThread}
	 * @param zug Der {@link de.hdm.drools.resource.Zug}, der um eine Auffahrterlaubsnis fragt
	 */
	@Path("/auffahrtAnfragen")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void auffahrtAnfragen(@Suspended final AsyncResponse asyncResponse,
			@NotNull Zug zug){
		KnowledgeBaseThread.auffahrtAnfragen(new AuffahrtAnfrageMitAsyncResponse(asyncResponse,zug));
	}
	
	/**
	 * Dient zum Melden einer Auffahrt.
	 * @param asyncResponse {@link javax.ws.rs.container.AsyncResponse}, zum beantworten aus dem {@link KnowledgeBaseThread}
	 * @param zug Der {@link de.hdm.drools.resource.Zug}, der auffährt
	 */
	@Path("/auffahrtMelden")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void auffahrtMelden(@Suspended final AsyncResponse asyncResponse,
			@NotNull Zug zug){
		KnowledgeBaseThread.auffahrtMelden(new AuffahrtMeldungMitAsyncResponse(zug,asyncResponse));
	}
	
	/**
	 * Dient zum Melden einer Abfahrt.
	 * @param zug Der {@link de.hdm.drools.resource.Zug}, der abfährt
	 */
	@Path("/abfahrtMelden")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response abfahrtMelden(@NotNull Zug zug){
		KnowledgeBaseThread.abfahrtMelden(new Abfahrt(zug));
		return Response.ok().build();
	}
}
