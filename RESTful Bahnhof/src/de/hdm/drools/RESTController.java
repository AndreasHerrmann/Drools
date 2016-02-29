package de.hdm.drools;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import de.hdm.drools.nachricht.Abfahrt;
import de.hdm.drools.nachricht.Einfahrt;
import de.hdm.drools.nachricht.EinfahrtAnfrage;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

/**
 * REST-Controller des Bahnhofs. Verarbeitet alle Anfragen, die an den Bahnhof gesendet werden.
 * @author Andreas Herrmann
 * @param netzverwaltung Ein {@link javax.ws.rs.client.WebTarget}, das auf die Netzverwaltung zeigt
 * @param bahnhof Beinhaltet die Daten des Bahnhofs, die ihm von der Netzverwaltung geschickt werden
 * @param gleise Die Gleise des Bahnhofs
 * @param wegfuehrendeStrecken Alle Strecken, die vom Bahnhof wegfuehren
 */
@Path("/")
public class RESTController {
	private static DerBahnhof derBahnhof;
	
	public static void setDerBahnhof(DerBahnhof derBahnhof){
		RESTController.derBahnhof=derBahnhof;
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
			if(derBahnhof.erneutAnmelden()){
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
	public void einfahrtAnfragen(@Suspended final AsyncResponse asyncResponse,
			EinfahrtAnfrage fahrtanfrage){
		derBahnhof.einfahrtAnfragen(fahrtanfrage, asyncResponse);
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
	public void einfahrtAnfragenOhneGleis(@Suspended final AsyncResponse asyncResponse,
		@NotNull Zug zug){
		EinfahrtAnfrage fahrtanfrage = new EinfahrtAnfrage(zug,null);
		derBahnhof.einfahrtAnfragen(fahrtanfrage
						,asyncResponse);
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
		derBahnhof.einfahrtMelden(einfahrt, asyncResponse);
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
	public Response abfahrtMelden(@NotNull Abfahrt abfahrt){
		if(derBahnhof.abfahrtMelden(abfahrt)){
			return Response.ok().build();
		}
		else{
			return Response.serverError().build();
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
		Strecke[] gefundeneStrecken = derBahnhof.findeStreckeZuKnotenpunkt(knotenpunkt);
		if(gefundeneStrecken.length<1){
			return Response.noContent().build();
		}
		else{
			return Response.ok(Entity.json(gefundeneStrecken)).build();
		}
	}
}
