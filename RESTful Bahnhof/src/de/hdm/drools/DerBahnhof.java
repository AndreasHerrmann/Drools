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
import de.hdm.drools.nachricht.Einfahrt;
import de.hdm.drools.nachricht.EinfahrtAnfrage;
import de.hdm.drools.nachricht.EinfahrtAnfrageMitAsyncResponse;
import de.hdm.drools.nachricht.EinfahrtMitAsyncResponse;
import de.hdm.drools.resource.Bahnhof;
import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;

public class DerBahnhof {
	/**
	 * Die Referenz auf den {@link KnowledgeBaseThread} des Servlets.
	 */
	private KnowledgeBaseThread kieSessionThread;
	/**
	 * Ein {@link javax.ws.rs.client.WebTarget}, das auf die Netzverwaltung zeigt.
	 * Wird aus der Adresse in {@link Config} erstellt.
	 */
	private WebTarget netzverwaltung;
	/**
	 * Daten des Bahnhofs, die ihm bei der Anmeldung von der Netzverwaltung übergeben wurden.
	 */
	private Bahnhof bahnhof;
	/**
	 * Alle Gleise des Bahnhofs
	 */
	private Vector<Gleis> gleise;
	/**
	 * Alle Strecken, die vom Bahnhof wegführen. Wird dem Bahnhof von der Netzverwaltung übergeben.
	 */
	private Strecke[] wegfuehrendeStrecken;
	
	/**
	 * Methode zum Anmelden bei der Netzverwaltung. Erstellt außerdem alle Gleise
	 * und fragt die wegführenden Strecken bei der Netzverwaltung ab.
	 * @return {@code true}, wenn die Anmeldung erfolgreich war, ansonsten {@code false}
	 */
	public DerBahnhof(){
		kieSessionThread = new KnowledgeBaseThread();
		kieSessionThread.start();
		anmelden();
	}
	public void anhalten(){
		abmelden();
		kieSessionThread.interrupt();
		kieSessionThread=null;
	}
	public boolean anmelden(){
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
				kieSessionThread.gleisEinfuegen(gleis);
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
	public void abmelden(){
		WebTarget abmeldenTarget = netzverwaltung.path("/bahnhof/abmelden");
		abmeldenTarget.request().post(Entity.json(bahnhof));
	}
	public boolean erneutAnmelden(){
		abmelden();
		if(anmelden()){
			return true;
		}
		else{
			return false;
		}
	}
	public void einfahrtAnfragen(EinfahrtAnfrage einfahrtAnfrage, AsyncResponse asyncResponse){
		EinfahrtAnfrageMitAsyncResponse einfahrtAnfrageMitAsync = new EinfahrtAnfrageMitAsyncResponse(einfahrtAnfrage,asyncResponse);
		kieSessionThread.einfahrtAnfragen(einfahrtAnfrageMitAsync);
	}
	public void einfahrtMelden(Einfahrt einfahrt, AsyncResponse asyncResponse){
		EinfahrtMitAsyncResponse einfahrtMitAsync = new EinfahrtMitAsyncResponse(einfahrt,asyncResponse);
		kieSessionThread.einfahrtMelden(einfahrtMitAsync);
	}
	public boolean abfahrtMelden(Abfahrt abfahrt){
		kieSessionThread.abfahrtMelden(abfahrt);
		return true;
	}
	public Strecke[] findeStreckeZuKnotenpunkt(Knotenpunkt ziel){
		Vector<Strecke> gefundeneStrecken = new Vector<Strecke>();
		for(int i=0;i<wegfuehrendeStrecken.length;i++){
			if(wegfuehrendeStrecken[i].getZiel().equals(ziel)){
				gefundeneStrecken.add(wegfuehrendeStrecken[i]);
			}
		}
		Strecke[] strecken = new Strecke[gefundeneStrecken.size()];
		return gefundeneStrecken.toArray(strecken);
	}
}
