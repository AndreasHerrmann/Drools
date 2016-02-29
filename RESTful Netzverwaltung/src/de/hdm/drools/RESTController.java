package de.hdm.drools;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.server.ChunkedOutput;

import de.hdm.drools.nachricht.FahrtAbschluss;
import de.hdm.drools.nachricht.FahrtAnfrage;
import de.hdm.drools.nachricht.FahrtBeginn;
import de.hdm.drools.nachricht.Lebenszeichen;
import de.hdm.drools.resource.Bahnhof;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

/**
 * REST-Schnittstelle der Netzverwaltung, geschrieben in Jersey.
 * @author Andreas Herrmann
 * @param knotenpunkte Vector, der alle bekannten Knotenpunkte enthält
 * @param bahnhoefe Vector, der alle bekannten Bahnhöfe enthält
 * @param zuege Vector, der alle bekannten Züge enthält
 * @param strecken Vector, der alle bekannten Strecken enthält
 */
@Path("/")
public class RESTController {
	private static Netzverwaltung netzverwaltung=null;
	
	public static void setNetzverwaltung(Netzverwaltung netzverwaltung){
		RESTController.netzverwaltung=netzverwaltung;
	}
	
	@Path("/knotenpunkt/anmelden")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void knotenpunktAnmelden(@Context HttpServletRequest req,
			@Suspended final AsyncResponse asyncResponse){
		URI adresse = URI.create(req.getRemoteAddr());
		netzverwaltung.knotenpunktAnmelden(adresse, asyncResponse);
	}
	
	@Path("/knotenpunkt/abmelden")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response knotenpunktAbmelden(@Context HttpServletRequest req,
			@NotNull Knotenpunkt knotenpunkt){
		URI adresse = URI.create(req.getRemoteAddr());
		if(!netzverwaltung.knotenpunktAbmelden(adresse, knotenpunkt)){
			return Response.status(HttpStatus.FORBIDDEN_403).build();
		}
		else{
			return Response.ok().build();
		}
	}
	
	@Path("/bahnhof/anmelden")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void bahnhofAnmelden(@Context HttpServletRequest req,
			@Suspended final AsyncResponse asyncResponse){
		URI adresse = URI.create(req.getRemoteAddr());
		netzverwaltung.bahnhofAnmelden(adresse, asyncResponse);
	}
	
	@Path("/bahnhof/abmelden")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response bahnhofAbmelden(@Context HttpServletRequest req,
			@NotNull Bahnhof bahnhof){
		URI adresse = URI.create(req.getRemoteAddr());
		if(!netzverwaltung.bahnhofAbmelden(adresse, bahnhof)){
			return Response.status(HttpStatus.FORBIDDEN_403).build();
		}
		else{
			return Response.ok().build();
		}
	}
	
	@Path("/zug/anmelden")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void zugAnmelden(@Context HttpServletRequest req,
			@Suspended final AsyncResponse asyncResponse){
		URI adresse = URI.create(req.getRemoteAddr());
		netzverwaltung.zugAnmelden(adresse, asyncResponse);
	}
	
	@Path("/zug/abmelden")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response zugAbmelden(@Context HttpServletRequest req,
			@NotNull Zug zug){
		URI adresse = URI.create(req.getRemoteAddr());
		if(!netzverwaltung.zugAbmelden(adresse, zug)){
			return Response.status(HttpStatus.FORBIDDEN_403).build();
		}
		else{
			return Response.ok().build();
		}
	}
	
	@Path("/fahrt/anfrage")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void 	fahrtAnfrageStellen(@Context HttpServletRequest req,
			@Suspended final AsyncResponse asyncResponse,
			@NotNull FahrtAnfrage fahrtanfrage){
		netzverwaltung.fahrtAnfragen(fahrtanfrage, asyncResponse);
	}
	
	@Path("/fahrt/beginn")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void 	fahrtBeginnMelden(@Context HttpServletRequest req,
			@Suspended final AsyncResponse asyncResponse,
			@NotNull FahrtBeginn fahrtbeginn){
		netzverwaltung.fahrtBeginnen(fahrtbeginn, asyncResponse);
	}
	
	@Path("/fahrt/abschluss")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response fahrtAbschlussMelden(@Context HttpServletRequest req,
			@NotNull FahrtAbschluss fahrtabschluss){
		netzverwaltung.fahrtAbschliessen(fahrtabschluss);
			return Response.ok().build();
	}
	
	@Path("/fahrt/lebenszeichen")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response lebenszeichenAbsetzen(@Context HttpServletRequest req,
			@NotNull Lebenszeichen lebenszeichen){
		netzverwaltung.lebenszeichenAbsetzen(lebenszeichen);
		return Response.ok().build();
	}
	
	@Path("/knotenpunkt/{ID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findKnotenpunktByID(@PathParam("ID") long id){
		if(id<=0){
			return Response.status(HttpStatus.BAD_REQUEST_400).build();
		}
		else{
			Knotenpunkt knotenpunkt=netzverwaltung.findeKnotenpunktAnhandID(id);
			if(knotenpunkt!=null){
				return Response.ok(Entity.json(knotenpunkt)).build();
			}
			else{
				return Response.status(HttpStatus.NOT_FOUND_404).build();
			}
		}
	}
	
	@Path("/bahnhof/{ID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findBahnhofByID(@PathParam("ID") long id){
		if(id<=0){
			return Response.status(HttpStatus.BAD_REQUEST_400).build();
		}
		else{
			Bahnhof bahnhof=netzverwaltung.findeBahnhofAnhandID(id);
			if(bahnhof!=null){
				return Response.ok(Entity.json(bahnhof)).build();
			}
			else{
				return Response.status(HttpStatus.NOT_FOUND_404).build();
			}
		}
	}
	
	@Path("/zug/{ID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findZugByID(@PathParam("ID") long id){
		if(id<=0){
			return Response.status(HttpStatus.BAD_REQUEST_400).build();
		}
		else{
			Zug zug=netzverwaltung.findeZugAnhandID(id);
			if(zug!=null){
				return Response.ok(Entity.json(zug)).build();
			}
			else{
				return Response.status(HttpStatus.NOT_FOUND_404).build();
			}
		}
	}
	
	@Path("/strecke/zu/knotenpunkt")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findStreckeZuKnotenpunktHin(@NotNull Knotenpunkt knotenpunkt){
		Strecke[] gefundeneStrecken=netzverwaltung.findeStreckenZuKnotenpunkt(knotenpunkt);
		if(gefundeneStrecken.length<1){
			return Response.noContent().build();
		}
		else{
			return Response.ok(Entity.json(gefundeneStrecken)).build();
		}
	}
	
	@Path("/strecke/von/knotenpunkt")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findStreckeVonKnotenpunktWeg(@NotNull Knotenpunkt knotenpunkt){
		Strecke[] gefundeneStrecken=netzverwaltung.findeStreckenVonKnotenpunkt(knotenpunkt);
		if(gefundeneStrecken.length<1){
			return Response.noContent().build();
		}
		else{
			return Response.ok(Entity.json(gefundeneStrecken)).build();
		}
	}
	@Path("/netzverwaltung/beobachten")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ChunkedOutput<String> netzverwaltungBeobachten() {
		final ChunkedOutput<String> output = new ChunkedOutput<String>(String.class);
        netzverwaltung.beobachten(output);
        return output;
    }
}