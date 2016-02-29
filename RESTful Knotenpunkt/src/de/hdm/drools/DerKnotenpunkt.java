package de.hdm.drools;

import java.util.Vector;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import de.hdm.drools.nachricht.Abfahrt;
import de.hdm.drools.nachricht.AuffahrtAnfrageMitAsyncResponse;
import de.hdm.drools.nachricht.AuffahrtMeldungMitAsyncResponse;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

public class DerKnotenpunkt {
	private KnowledgeBaseThread kieSessionThread;
	/**
	 * Ein {@link javax.ws.rs.client.WebTarget}, das auf die Netzverwaltung zeigt.
	 * Wird aus der Adresse in {@link Config} erstellt.
	 */
	private WebTarget netzverwaltung;
	/**
	 * Daten des Knotenpunkts, die ihm bei der Anmeldung von der Netzverwaltung übergeben wurden.
	 */
	private Knotenpunkt knotenpunkt;
	/**
	 * Alle Strecken, die vom Knotenpunkt wegführen. Wird dem Knotenpunkt von der Netzverwaltung übergeben.
	 */
	private Strecke[] wegfuehrendeStrecken;
	
	public DerKnotenpunkt(){
		kieSessionThread=new KnowledgeBaseThread();
		kieSessionThread.start();
		anmelden();
	}
	public void anhalten(){
		abmelden();
		kieSessionThread.interrupt();
		kieSessionThread=null;
	}
	/**
	 * Methode zum Anmelden bei der Netzverwaltung. Fragt die wegführenden Strecken
	 * bei der Netzverwaltung ab.
	 */
	public void anmelden(){
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
	public void abmelden(){
		WebTarget abmeldenTarget = netzverwaltung.path("/knotenpunkt/abmelden");
		abmeldenTarget.request().post(Entity.json(knotenpunkt));
	}
	
	public Strecke[] findeStreckenAnhandKnotenpunkt(Knotenpunkt ziel){
		Vector<Strecke> gefundeneStrecken = new Vector<Strecke>();
		for (int i=0;i<wegfuehrendeStrecken.length;i++){
			if(wegfuehrendeStrecken[i].getZiel().equals(ziel)){
				gefundeneStrecken.add(wegfuehrendeStrecken[i]);
			}
		}
		Strecke[] strecken = new Strecke[gefundeneStrecken.size()];
		return gefundeneStrecken.toArray(strecken);
	}
	public void auffahrtAnfragen(Zug zug, AsyncResponse asyncResponse){
		AuffahrtAnfrageMitAsyncResponse auffahrtAnfrageMitAsync = new AuffahrtAnfrageMitAsyncResponse(asyncResponse,zug);
		kieSessionThread.auffahrtAnfragen(auffahrtAnfrageMitAsync);
	}
	public void auffahrtMelden(Zug zug, AsyncResponse asyncResponse){
		AuffahrtMeldungMitAsyncResponse auffahrtMeldungMitAsync = new AuffahrtMeldungMitAsyncResponse(zug,asyncResponse);
		kieSessionThread.auffahrtMelden(auffahrtMeldungMitAsync);
	}
	public boolean abfahrtMelden(Zug zug){
		Abfahrt abfahrt = new Abfahrt(zug);
		kieSessionThread.abfahrtMelden(abfahrt);
		return true;
	}
}
