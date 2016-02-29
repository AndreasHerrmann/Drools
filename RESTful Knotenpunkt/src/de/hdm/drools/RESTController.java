package de.hdm.drools;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

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
public class RESTController {
	private static DerKnotenpunkt derKnotenpunkt;
	
	public static void setDerKnotenpunkt(DerKnotenpunkt derKnotenpunkt){
		RESTController.derKnotenpunkt=derKnotenpunkt;
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
	public Response findStreckenByZiel(@NotNull Knotenpunkt knotenpunkt){
		Strecke[] gefundeneStrecken = derKnotenpunkt.findeStreckenAnhandKnotenpunkt(knotenpunkt);
		if(gefundeneStrecken.length<1){
			return Response.status(HttpStatus.NOT_FOUND_404).build();
		}
		else{
			return Response.ok(Entity.json(gefundeneStrecken)).build();
		}
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
		derKnotenpunkt.auffahrtAnfragen(zug, asyncResponse);
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
		derKnotenpunkt.auffahrtMelden(zug, asyncResponse);
	}
	
	/**
	 * Dient zum Melden einer Abfahrt.
	 * @param zug Der {@link de.hdm.drools.resource.Zug}, der abfährt
	 */
	@Path("/abfahrtMelden")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response abfahrtMelden(@NotNull Zug zug){
		if(derKnotenpunkt.abfahrtMelden(zug)){
			return Response.ok().build();
		}
		else{
			return Response.serverError().build();
		}
	}
}
