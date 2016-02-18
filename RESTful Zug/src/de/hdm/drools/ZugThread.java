package de.hdm.drools;

import java.util.Vector;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import de.hdm.drools.nachricht.Abfahrt;
import de.hdm.drools.nachricht.Einfahrt;
import de.hdm.drools.nachricht.EinfahrtErlaubnis;
import de.hdm.drools.nachricht.FahrtAbschluss;
import de.hdm.drools.nachricht.FahrtAnfrage;
import de.hdm.drools.nachricht.FahrtBeginn;
import de.hdm.drools.nachricht.FahrtErlaubnis;
import de.hdm.drools.nachricht.Lebenszeichen;
import de.hdm.drools.nachricht.EinfahrtAnfrage;
import de.hdm.drools.resource.Fahrplan;
import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

public class ZugThread extends Thread {
	private WebTarget netzverwaltung = null;
	private Zug dieserZug=null;
	private Fahrplan fahrplan;
	private Knotenpunkt letzterKnotenpunkt=null;
	private Strecke letzteStrecke=null;
	private Gleis letztesGleis=null;
	
	public void run(){
		int counter=0;
		//Wenn dieserZug null ist, dann dreimal versuchen sich anzumelden
		for(counter=0;(counter<3)&&(dieserZug==null);counter++){
			anmelden();
		}
		//Wenn nach drei Versuchen dieserZug immer noch null ist, dann konnte der Zug nicht angemeldet werden
		if((dieserZug==null)&&counter>=3){
			System.out.println("Anmeldung nicht möglich! Adresse der Netzverwaltung falsch?");
		}
		Client client = ClientBuilder.newClient();
		netzverwaltung = client.target(Config.netzverwaltungAdresse);
		while(!isInterrupted()){
			for(int i=0; i<fahrplan.getAufenthaltszeiten().length;i++){
				//Wenn der letzte Ort ein Knotenpunkt war
				if(letzterKnotenpunkt!=null){
					//Wenn der letzte Knotenpunkt ein Bahnhof war
					if(letzterKnotenpunkt.getIstBahnhof()){
						//Wenn der aktuelle Knotenpunkt ein Bahnhof ist
						if(fahrplan.getFahrtziel(i).getIstBahnhof()){
							//Wenn der letzte und der aktuelle Bahnhof der gleiche ist
							if(fahrplan.getFahrtziel(i).getAdresse().equals(letzterKnotenpunkt.getAdresse())){
								//Den letzten Knotenpunkt auf den aktuellen setzen (Dann bin ich schon durch den Bahnhof gefahren)
								letzterKnotenpunkt=fahrplan.getFahrtziel(i);
								letzteStrecke=null;
							}
							else{
								//Wenn er nicht der selbe Bahnhof ist, dann muss ich erst zu ihm hin fahren
								vonBahnhofZuKnotenpunktFahren(fahrplan.getFahrtziel(i),fahrplan.getAufenthaltszeit(i),client);
							}
						}
						else{
							/*Wenn der letzte Knotenpunkt ein Bahnhof ist und der aktuelle
							 * nicht, dann muss ich zu einem Knotenpunkt fahren
							 */
							vonBahnhofZuKnotenpunktFahren(fahrplan.getFahrtziel(i),fahrplan.getAufenthaltszeit(i),client);
						}
					}
					else{
						/*Wenn der letzte Ort ein Knotenpunkt war und der aktuelle
						 * kein Bahnhof ist, dann muss ich von diesem Knotenpunkt
						 * zum nächsten fahren
						 */
						vonKnotenpunktZuKnotenpunktFahren(fahrplan.getFahrtziel(i),fahrplan.getAufenthaltszeit(i),client);
					}
				}
				else{
					//Wenn der letzte Ort kein Knotenpunkt ist, dann ist er eine Strecke
					if(fahrplan.getFahrtziel(i).getIstBahnhof()){
						//Wenn der aktuelle Knotenpunkt ein Bahnhof ist, dann muss ich in ihn einfahren
						vonStreckeInBahnhofFahren(fahrplan.getFahrtziel(i),fahrplan.getAufenthaltszeit(i),client);
					}
					else{
						/*Wenn der aktuelle Knotenpunkt kein Bahnhof ist,
						 *dann muss ich auf einen Knotenpunkt auffahren
						 */
						vonStreckeAufKnotenpunktFahren(fahrplan.getFahrtziel(i),client);
					}
				}
			}
		}
		//Nachdem der Thread angehalten wurde den Zug abmelden
		abmelden();
	}
	/*
	 * Methode um von einem Kotenpunkt zum nächsten zu fahren
	 */
	private void vonKnotenpunktZuKnotenpunktFahren(Knotenpunkt knotenpunkt,int fahrtzeit,Client client){
		WebTarget streckenTarget = client.target(letzterKnotenpunkt.getAdresse()).path("/strecke");
		Response resp = streckenTarget.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(knotenpunkt));
		if(!resp.hasEntity()){
			resp = knotenpunktOffline(knotenpunkt,client);
		}
		Strecke strecken[] = resp.readEntity(Strecke[].class);
		if(strecken.length<=0){
			//Fehler
		}
		WebTarget anfrageTarget = netzverwaltung.path("/fahrt/anfrage");
		
		Vector<Response> antworten = new Vector<Response>();
		do{
			for(int i=0;i<strecken.length;i++){
				resp=anfrageTarget.request(MediaType.APPLICATION_JSON)
						.post(Entity.json(new FahrtAnfrage(dieserZug,strecken[i])));
				antworten.addElement(resp);
			}
		}while((!eineAntwortWahr(antworten))&&warten(10));
		Response trueResponse = wahreAntwortFinden(antworten);
		if(trueResponse==null){
			//Fehler
		}
		FahrtErlaubnis fahrtErlaubnis = trueResponse.readEntity(FahrtErlaubnis.class);
		WebTarget fahrtBeginnTarget = netzverwaltung.path("/fahrt/beginn");
		int counter=0;
		do{
			resp = fahrtBeginnTarget.request(MediaType.APPLICATION_JSON)
			.post(Entity.json(new FahrtBeginn(dieserZug,fahrtErlaubnis.getStrecke())));
			counter++;
		}while((resp.getStatus()!=HttpStatus.OK_200)&&(counter<=3)&&(warten(10)));
		if(!resp.hasEntity()){
			//Fehler
		}
		//Abfahrt vom letzten Knotenpunkt melden
		client.target(letzterKnotenpunkt.getAdresse()).path("/abfahrtMelden/")
		.request(MediaType.APPLICATION_JSON).post(Entity.json(dieserZug));
		WebTarget lebenszeichenTarget = netzverwaltung.path("/fahrt/lebenszeichen");
		for(int i=0; i<fahrtzeit;i++){
			//Eine Minute warten
			warten(60);
			//Lebenszeichen absetzen
			resp = lebenszeichenTarget.request()
					.post(Entity.json(new Lebenszeichen(dieserZug,fahrtErlaubnis.getStrecke())));
			if(resp.getStatus()!=HttpStatus.OK_200){
				//Fehler
			}
		}
		//Fahrt abschließen
		letzteStrecke = fahrtErlaubnis.getStrecke();
		letzterKnotenpunkt=null;
	}
	/*
	 * Methode um von einem Bahnhof zu einem Knotenpunkt zu fahren
	 */
	private void vonBahnhofZuKnotenpunktFahren(Knotenpunkt knotenpunkt, int fahrtzeit, Client client){
		WebTarget streckenTarget = client.target(letzterKnotenpunkt.getAdresse()).path("/strecke");
		Response resp = streckenTarget.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(knotenpunkt));
		if(!resp.hasEntity()){
			//Dann ist der Knotenpunkt nicht zu erreichen (Adresse nicht mehr aktuell?)
			resp = knotenpunktOffline(knotenpunkt,client);
		}
		Strecke strecken[] = resp.readEntity(Strecke[].class);
		if(strecken.length<=0){
			//Fehler
		}
		WebTarget anfrageTarget = netzverwaltung.path("/fahrt/anfrage");
		
		Vector<Response> antworten = new Vector<Response>();
		do{
			for(int i=0;i<strecken.length;i++){
				resp=anfrageTarget.request(MediaType.APPLICATION_JSON)
						.post(Entity.json(new FahrtAnfrage(dieserZug,strecken[i])));
				antworten.addElement(resp);
			}
		}while((!eineAntwortWahr(antworten))&&warten(10));
		Response trueResponse = wahreAntwortFinden(antworten);
		if(trueResponse==null){
			//Fehler
		}
		FahrtErlaubnis fahrtErlaubnis = trueResponse.readEntity(FahrtErlaubnis.class);
		WebTarget fahrtBeginnTarget = netzverwaltung.path("/fahrt/beginn");
		int counter=0;
		do{
			resp = fahrtBeginnTarget.request(MediaType.APPLICATION_JSON)
			.post(Entity.json(new FahrtBeginn(dieserZug,fahrtErlaubnis.getStrecke())));
			counter++;
		}while((resp.getStatus()!=HttpStatus.OK_200)&&(counter<=3)&&(warten(10)));
		if(!resp.hasEntity()){
			//Fehler
		}
		//Abfahrt vom Bahnhof melden
		client.target(letzterKnotenpunkt.getAdresse()).path("/abfahrtMelden/")
		.request(MediaType.APPLICATION_JSON).post(Entity
				.json(new Abfahrt(dieserZug,letztesGleis)));
		WebTarget lebenszeichenTarget = netzverwaltung.path("/fahrt/lebenszeichen");
		for(int i=0; i<fahrtzeit;i++){
			//Eine Minute warten
			warten(60);
			//Lebenszeichen absetzen
			resp = lebenszeichenTarget.request()
					.post(Entity.json(new Lebenszeichen(dieserZug,fahrtErlaubnis.getStrecke())));
			if(resp.getStatus()!=HttpStatus.OK_200){
				//Fehler
			}
		}
		//Fahrt abschließen
		letzteStrecke = fahrtErlaubnis.getStrecke();
		letzterKnotenpunkt=null;
		letztesGleis=null;
	}
	/*
	 * Methode um von einer Strecke auf einen Knotenpunkt aufzufahren
	 */
	private void vonStreckeAufKnotenpunktFahren(Knotenpunkt knotenpunkt, Client client){
		WebTarget auffahrtTarget = client.target(knotenpunkt.getAdresse()).path("/auffahrtAnfragen/");
		Response resp=null;
		do{
			resp = auffahrtTarget.request(MediaType.APPLICATION_JSON)
					.post(Entity.json(dieserZug));
		}while((resp.getStatus()!=HttpStatus.OK_200)&&warten(10));
		if(resp.getStatus()!=HttpStatus.OK_200){
			//Fehler
		}
		auffahrtTarget = client.target(knotenpunkt.getAdresse()).path("/auffahrtMelden/");
		do{
			resp = auffahrtTarget.request(MediaType.APPLICATION_JSON)
					.post(Entity.json(dieserZug));
		}while((resp.getStatus()!=HttpStatus.OK_200)&&warten(10));
		//Abfahrt von letzter Strecke melden
		netzverwaltung.path("/fahrt/abschluss").request(MediaType.APPLICATION_JSON)
		.post(Entity.json(new FahrtAbschluss(dieserZug,letzteStrecke)));
		letzteStrecke = null;
		letzterKnotenpunkt=knotenpunkt;
		letztesGleis=null;
		
	}
	/*
	 * Methode um von einer Strecke in einen Bahnhof einzufahren
	 */
	private void vonStreckeInBahnhofFahren(Knotenpunkt knotenpunkt,int aufenthalt,Client client){
		WebTarget einfahrtTarget = client.target(knotenpunkt.getAdresse()).path("/einfahrtAnfragen");
		Response resp=null;
		do{
			resp = einfahrtTarget.request(MediaType.APPLICATION_JSON)
					.post(Entity.json(new EinfahrtAnfrage(dieserZug,null)));
		}while((resp.getStatus()!=HttpStatus.OK_200)&&warten(10));
		if(!resp.hasEntity()){
			//Fehler
		}
		EinfahrtErlaubnis erlaubnis =  resp.readEntity(EinfahrtErlaubnis.class);
		einfahrtTarget = client.target(knotenpunkt.getAdresse()).path("/einfahrtMelden");
		do{
			resp = einfahrtTarget.request(MediaType.APPLICATION_JSON)
					.post(Entity.json(new Einfahrt(dieserZug,erlaubnis.getGleis())));
		}while((resp.getStatus()!=HttpStatus.OK_200)&&warten(10));
		if(resp.getStatus()!=HttpStatus.OK_200){
			//Fehler
		}
		//Abfahrt von letzter Strecke melden
		netzverwaltung.path("/fahrt/abschluss").request(MediaType.APPLICATION_JSON)
		.post(Entity.json(new FahrtAbschluss(dieserZug,letzteStrecke)));
		//Fahrt Abschließen
		letzteStrecke = null;
		letzterKnotenpunkt=knotenpunkt;
		letztesGleis=erlaubnis.getGleis();
		//An Gleis warten
		if(!warten(aufenthalt)){
			//Fehler
		}
		
		
	}
	/*
	 * Methode um 'length' Minuten zu warten
	 */
	private boolean warten(int length){
		try {
			wait(length*60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/*
	 * Methode um herauszufinden, ob eine der Antworten wahr ist
	 * (den Antwortstatus 200:OK hat)
	 */
	private boolean eineAntwortWahr(Vector<Response> dieAntworten){
		boolean antwort = false;
		for(int i=0;i<dieAntworten.size();i++){
			if(dieAntworten.get(i).getStatus()==HttpStatus.OK_200){
				antwort=true;
			}
		}
		return antwort;
	}
	/*
	 * Methode um die erste wahre Antwort zu finden
	 * (den Antwortstatus 200:OK hat)
	 */
	private Response wahreAntwortFinden(Vector<Response> antworten){
		for(int i=0;i<antworten.size();i++){
			if(antworten.get(i).getStatus()==HttpStatus.OK_200){
				return antworten.get(i);
			}
		}
		return null;
	}
	private Response knotenpunktOffline(Knotenpunkt knotenpunkt,Client client){
		Response resp = netzverwaltung.path("/knotenpunkt/"+letzterKnotenpunkt.getiD()).request().get();
		if(resp.getStatus()==HttpStatus.OK_200){
			letzterKnotenpunkt = resp.readEntity(Knotenpunkt.class);
			//Erneut versuchen
			WebTarget streckenTarget = client.target(letzterKnotenpunkt.getAdresse()).path("/strecke");
			resp = streckenTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(knotenpunkt));
			//Wenn er zu erreichen ist
			if(resp.hasEntity()){
				return resp;
			}
			//Wenn nicht
			else{
				resp = netzverwaltung.path("/strecke/zu/knotenpunkt").request(MediaType.APPLICATION_JSON)
				.post(Entity.json(knotenpunkt));
				if(resp.hasEntity()){
					Strecke strecken[] = resp.readEntity(Strecke[].class);
					Vector<Strecke> passendeStrecken = new Vector<Strecke>();
					for(int i=0;i<strecken.length;i++){
						if(strecken[i].getStart().equals(knotenpunkt)){
							passendeStrecken.addElement(strecken[i]);
						}
					}
					return Response.ok(passendeStrecken.toArray(strecken)).build();
				}
				else{
					//Fehler
					return null;
				}
			}
		}
		//Wenn die Netzverwaltung ihn nicht kennt
		else{
			//Fehler
			return null;
		}
		
		
	}
	private boolean anmelden(){
		Client client = ClientBuilder.newClient();
		netzverwaltung = client.target(Config.netzverwaltungAdresse);
		WebTarget anmeldenTarget = netzverwaltung.path("/zug/anmelden");
		Response resp=anmeldenTarget.request(MediaType.APPLICATION_JSON).post(null);
		if(resp.getStatus()==HttpStatus.OK_200){
			dieserZug = resp.readEntity(Zug.class);
			return true;
		}
		else{
			System.out.println("Anmeldung nicht möglich: "+resp.toString());
			return false;
		}
	}
	private void abmelden(){
		netzverwaltung.path("/zug/abmelden").request().post(Entity.json(dieserZug));
		dieserZug=null;
	}
}