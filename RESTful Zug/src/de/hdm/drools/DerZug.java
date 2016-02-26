package de.hdm.drools;

import java.net.URI;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

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
import de.hdm.drools.resource.Fahrplan;
import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

public class DerZug extends Thread {
	private Client client=null;
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
		//Wenn nach drei Versuchen dieser Zug immer noch null ist, dann konnte der Zug nicht angemeldet werden
		if((dieserZug==null)&&counter>=3){
			System.out.println("Anmeldung nicht möglich! Adresse der Netzverwaltung falsch?");
		}
		fahrplan=dieserZug.getFahrplan();
		client = ClientBuilder.newClient();
		netzverwaltung = client.target(Config.netzverwaltungAdresse);
		/**
		 * Solange der Thread nicht angehalten wird soll der Fahrplan
		 * abgearbeitet werden.
		 */
		while(!isInterrupted()){
			/**
			 * Fahrplan Knotenpunkt für Knotenpunkt abarbeiten.
			 */
			for(int i=0; i<fahrplan.getAufenthaltszeiten().length;i++){
				/**
				 * Wenn der letzte Ort ein Knotenpunkt und ein Bahnhof war
				 * und der aktuelle notenpunkt auch ein Bahnhof ist und
				 * beide die selbe Adresse haben, dann bin ich durch den Bahnhof
				 * hindurch gefahren
				 */
				if(letzterKnotenpunkt!=null && letzterKnotenpunkt.getIstBahnhof() &&fahrplan.getFahrtziel(i).getIstBahnhof()
						&& fahrplan.getFahrtziel(i).getAdresse().equals(letzterKnotenpunkt.getAdresse())){
					//Den letzten Knotenpunkt auf den aktuellen setzen
					letzterKnotenpunkt=fahrplan.getFahrtziel(i);
					letzteStrecke=null;
				}
				/**
				 * Wenn der letzte Ort ein Knotenpunkt und ein Bahnhof war
				 * und der aktuelle Knotenpunkt auch ein Bahnhof ist und
				 * beide nicht die selbe Adresse haben, dann muss ich zum
				 * nächsten Bahnhof fahren
				 */
				else if(letzterKnotenpunkt!=null && letzterKnotenpunkt.getIstBahnhof() &&fahrplan.getFahrtziel(i).getIstBahnhof()
						&& !fahrplan.getFahrtziel(i).getAdresse().equals(letzterKnotenpunkt.getAdresse())){
					System.out.println("Bahnhof zu Bahnhof: "+fahrplan.getFahrtziel(i));
					fahreVonBahnhofZuKnotenpunkt(fahrplan.getFahrtziel(i),fahrplan.getAufenthaltszeit(i));
				}
				/**
				 * Wenn der letze Ort ein Knotenpunkt und ein Bahnhof war und der
				 * aktuelle ein Knotenpunkt ist, aber kein Bahnhof, dann muss ich
				 * zu dem Knotenpunkt hinfahren
				 */
				else if(letzterKnotenpunkt!=null && letzterKnotenpunkt.getIstBahnhof()
						&& !fahrplan.getFahrtziel(i).getIstBahnhof()){
					System.out.println("Von Bahnhof zu Knotenpunkt: "+fahrplan.getFahrtziel(i));
					fahreVonBahnhofZuKnotenpunkt(fahrplan.getFahrtziel(i),fahrplan.getAufenthaltszeit(i));
				}
				/**
				 * Wenn der letzte Ort ein Knotenpunkt, aber kein Bahnhof war und
				 * der aktuelle Knotenpunkt kein Bahnhof ist, dann muss ich von diesem
				 * Knotenpunkt zum nächsten fahren
				 */
				else if(letzterKnotenpunkt!=null && !letzterKnotenpunkt.getIstBahnhof()
						&& !fahrplan.getFahrtziel(i).getIstBahnhof()){
					System.out.println("Knotenpunkt zu Knotenpunkt :"+fahrplan.getFahrtziel(i));
					fahreVomLetztenKnotenpunktZuKnotenpunkt(fahrplan.getFahrtziel(i),fahrplan.getAufenthaltszeit(i));
				}
				/**
				 * Wenn der letzte Ort kein Knotenpunkt war und der aktuelle ein
				 * Bahnhof ist, dann muss ich von einer Strecke in einen Bahnhof
				 * fahren
				 */
				else if(letzterKnotenpunkt==null && fahrplan.getFahrtziel(i).getIstBahnhof()){
					System.out.println("Strecke in Bahnhof :"+fahrplan.getFahrtziel(i));
					vonStreckeInBahnhofFahren(fahrplan.getFahrtziel(i),fahrplan.getAufenthaltszeit(i),client);
				}
				/**
				 * Wenn der letzte Ort kein Knotenpunkt war und der aktuelle kein
				 * Bahnhof ist, dann muss ich von einer Strecke auf einen Knotenpunkt
				 * auffahren
				 */
				else if(letzterKnotenpunkt==null && !fahrplan.getFahrtziel(i).getIstBahnhof()){
					System.out.println("Von Strecke auf Knotenpunkt :"+fahrplan.getFahrtziel(i));
					vonStreckeAufKnotenpunktFahren(fahrplan.getFahrtziel(i),client);
				}
				/**
				 * Wenn alles nicht zutrifft, dann ist etwas schief gelaufen
				 */
				else{
					System.out.println("ZugThread [Zeile 71-118]: Initiale Verzweigung konnte nicht erfüllt werden!\n");
				}
			}
		}
		/**
		 * Nachdem der Thread angehalten wurde den Zug abmelden
		 */
		abmelden();
	}
	/**
	 * Methode um vom {@code letzterKnotenpunkt} zum nächsten zu fahren.
	 * @param ziel Knotenpunkt zu dem hingefahren werden soll
	 * @param fahrtzeit Die Fahrtzeit, die die Fahrt dauern soll
	 */
	private void fahreVomLetztenKnotenpunktZuKnotenpunkt(Knotenpunkt ziel,int fahrtzeit){
		/**
		 * Wenn der letzteKnotenpunkt noch keine Adresse hat, dann seine Adresse
		 * heraus finden
		 */
		if(letzterKnotenpunkt.getAdresse()==null){
			letzterKnotenpunkt.setAdresse(knotenpunktAdresseFinden(letzterKnotenpunkt));
		}
		/**
		 * Ein WebTarget zum letzen Knotenpunkt aufbauen und die Strecken zum
		 * nächsten Knotenpunkt abfragen
		 */
		WebTarget streckenTarget = client.target("http://"+letzterKnotenpunkt.getAdresse()+":8080").path("/strecke");
		Response resp = streckenTarget.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(ziel));
		/**
		 * Wenn die Antwort nicht 200:OK ist, dann ist der Knotenpunkt wohl offline
		 */
		if(resp.getStatus()!=HttpStatus.OK_200){
			resp = knotenpunktOffline(ziel,client);
		}
		/**
		 * Die erhaltenen Strecken auf der Antwort lesen und wenn ich keine
		 * zurück bekommen habe, dann ist etwas falsch gelaufen
		 */
		Strecke[] strecken = resp.readEntity(Strecke[].class);
		if(strecken.length<=0){
			System.out.println("ZugThread [Zeile 165]: Keine Strecken zurück bekommen!");
			System.out.println(resp);
		}
		/**
		 * Ein WebTarget zur Netzverwaltung aufbauen und FahrtAnfragen für die Strecken
		 * stellen. Solange machen bis eine Antwort positiv ist.
		 */
		WebTarget anfrageTarget = netzverwaltung.path("/fahrt/anfrage");
		Vector<Response> antworten = new Vector<Response>();
		do{
			for(int i=0;i<strecken.length;i++){
				resp=anfrageTarget.request(MediaType.APPLICATION_JSON)
						.post(Entity.json(new FahrtAnfrage(dieserZug,strecken[i])));
				antworten.addElement(resp);
			}
		}while((!eineAntwortWahr(antworten))&&warten(5));
		/**
		 * Die positive Antwort finden und wenn es keine gibt, dann einen Fehler ausgeben
		 */
		Response trueResponse = wahreAntwortFinden(antworten);
		if(trueResponse==null){
			System.out.println("ZugThread [Zeile 176-186]: Keine positive Antwort zurück erhalten!");
		}
		/**
		 * Fahrterlaubnis speichern, WebTarget auf die Netzverwaltung aufbauen und
		 * dann Fahrtbeginn melden
		 */
		FahrtErlaubnis fahrtErlaubnis = trueResponse.readEntity(FahrtErlaubnis.class);
		WebTarget fahrtBeginnTarget = netzverwaltung.path("/fahrt/beginn");
		int counter=0;
		do{
			resp = fahrtBeginnTarget.request(MediaType.APPLICATION_JSON)
			.post(Entity.json(new FahrtBeginn(dieserZug,fahrtErlaubnis.getStrecke())));
			counter++;
		}while((resp.getStatus()!=HttpStatus.OK_200)&&(counter<=3)&&(warten(5)));
		/**
		 * Wenn es keine positive Rückmeldung gab, dann einen Fehler ausgeben
		 */
		if(resp.getStatus()!=HttpStatus.OK_200){
			System.out.println("ZugThread [Zeile 194-205]: Keine positive Rückmeldung für FahrtBeginn!");
			System.out.println(resp);
		}
		/**
		 * Die Abfahrt vom letzten Knotenpunkt melden
		 */
		client.target("http://"+letzterKnotenpunkt.getAdresse()+":8080").path("/abfahrtMelden")
		.request(MediaType.APPLICATION_JSON).post(Entity.json(dieserZug));
		/**
		 * WebTarget für die Lebenszeichen aufbauen und dann jede Minute ein Lebenszeichen senden
		 * bis die Fahrt abgeschlossen ist
		 */
		WebTarget lebenszeichenTarget = netzverwaltung.path("/fahrt/lebenszeichen");
		for(int i=0; i<fahrtzeit;i++){
			//Eine Minute warten
			warten(60);
			//Lebenszeichen absetzen
			resp = lebenszeichenTarget.request()
					.post(Entity.json(new Lebenszeichen(dieserZug,fahrtErlaubnis.getStrecke())));
			/**
			 * Wenn das Absenden der Lebenszeichen nicht positiv quittiert wurde, dann einen
			 * Fehler ausgeben
			 */
			if(resp.getStatus()!=HttpStatus.OK_200){
				System.out.println("ZugThread [Zeile 219-229]: Lebenszeichen nicht richtig abgesetzt!");
				System.out.println(resp);
			}
		}
		/**
		 * Die Fahrt abschließen und als letzten Ort die befahrene Strecke speichern
		 */
		letzteStrecke = fahrtErlaubnis.getStrecke();
		letzterKnotenpunkt=null;
	}
	
	/**
	 * Methode um von einem Bahnhof zu einem Knotenpunkt zu fahren
	 * @param ziel Knotenpunkt zu dem gefahren werden soll
	 * @param fahrtzeit Die Zeit in Minuten, die die Fahrt dauern soll
	 */
	private void fahreVonBahnhofZuKnotenpunkt(Knotenpunkt ziel, int fahrtzeit){
		/**
		 * Prüfen, ob für den letzten Knotenpunkt eine Adresse hinterlegt ist.
		 * Wenn nicht, dann die Adresse des Knotenpunkts herausfinden
		 */
		if(letzterKnotenpunkt.getAdresse()==null){
			letzterKnotenpunkt.setAdresse(knotenpunktAdresseFinden(letzterKnotenpunkt));
		}
		WebTarget streckenTarget = client.target("http://"+letzterKnotenpunkt.getAdresse()+":8080").path("/strecke");
		Response resp = streckenTarget.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(ziel));
		if(resp.getStatus()!=HttpStatus.OK_200){
			//Dann ist der Knotenpunkt nicht zu erreichen (Adresse nicht mehr aktuell?)
			resp = knotenpunktOffline(ziel,client);
		}
		Strecke strecken[] = resp.readEntity(Strecke[].class);
		if(strecken.length<=0){
			System.out.println("Fehler: Zeile 185");
			System.out.println(resp);
		}
		WebTarget anfrageTarget = netzverwaltung.path("/fahrt/anfrage");
		
		Vector<Response> antworten = new Vector<Response>();
		do{
			for(int i=0;i<strecken.length;i++){
				resp=anfrageTarget.request(MediaType.APPLICATION_JSON)
						.post(Entity.json(new FahrtAnfrage(dieserZug,strecken[i])));
				antworten.addElement(resp);
			}
		}while((!eineAntwortWahr(antworten))&&warten(5));
		Response trueResponse = wahreAntwortFinden(antworten);
		if(trueResponse==null){
			System.out.println("Fehler: Zeile 199");
			System.out.println(resp);
		}
		FahrtErlaubnis fahrtErlaubnis = trueResponse.readEntity(FahrtErlaubnis.class);
		WebTarget fahrtBeginnTarget = netzverwaltung.path("/fahrt/beginn");
		int counter=0;
		do{
			resp = fahrtBeginnTarget.request(MediaType.APPLICATION_JSON)
			.post(Entity.json(new FahrtBeginn(dieserZug,fahrtErlaubnis.getStrecke())));
			counter++;
		}while((resp.getStatus()!=HttpStatus.OK_200)&&(counter<=3)&&(warten(5)));
		if(resp.getStatus()!=HttpStatus.OK_200){
			System.out.println("Fehler: Zeile 210");
			System.out.println(resp);
		}
		//Abfahrt vom Bahnhof melden
		client.target("http://"+letzterKnotenpunkt.getAdresse()+":8080").path("/abfahrtMelden")
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
				System.out.println("Fehler: Zeile 224");
				System.out.println(resp);
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
		if(knotenpunkt.getAdresse()==null){
			knotenpunkt.setAdresse(knotenpunktAdresseFinden(knotenpunkt));
		}
		WebTarget auffahrtTarget = client.target("http://"+knotenpunkt.getAdresse()+":8080").path("/auffahrtAnfragen");
		Response resp=null;
		int counter = 0;
		do{
			resp = auffahrtTarget.request(MediaType.APPLICATION_JSON)
					.post(Entity.json(dieserZug));
			counter++;
		}while((resp.getStatus()!=HttpStatus.OK_200) && warten(5) && counter<=3);
		if(resp.getStatus()!=HttpStatus.OK_200){
			System.out.println("Fehler: Zeile 248");
			System.out.println(resp);
		}
		auffahrtTarget = client.target("http://"+knotenpunkt.getAdresse()+":8080").path("/auffahrtMelden");
		counter=0;
		do{
			resp = auffahrtTarget.request(MediaType.APPLICATION_JSON)
					.post(Entity.json(dieserZug));
			counter++;
		}while((resp.getStatus()!=HttpStatus.OK_200) && warten(5) && counter<=3);
		if(resp.getStatus()!=HttpStatus.OK_200){
			System.out.println("Fehler: Zeile 258");
			System.out.println(resp);
		}
		//Abfahrt von letzter Strecke melden, wenn sie nicht null ist
		//Wenn die letzte Strecke null, dann beginnt der Zug gerade erst
		if(letzteStrecke!=null){
			netzverwaltung.path("/fahrt/abschluss").request(MediaType.APPLICATION_JSON)
			.post(Entity.json(new FahrtAbschluss(dieserZug,letzteStrecke)));
		}
		//Fahrt abschließen
		letzteStrecke = null;
		letzterKnotenpunkt=knotenpunkt;
		letztesGleis=null;
		
	}
	/*
	 * Methode um von einer Strecke in einen Bahnhof einzufahren
	 */
	private void vonStreckeInBahnhofFahren(Knotenpunkt knotenpunkt,int aufenthalt,Client client){
		if(knotenpunkt.getAdresse()==null){
			knotenpunkt.setAdresse(knotenpunktAdresseFinden(knotenpunkt));
		}
		WebTarget einfahrtTarget = client.target("http://"+knotenpunkt.getAdresse()+":8080").path("/einfahrtAnfragenOhneGleis");
		Response resp=null;
		int counter=0;
		do{
			resp = einfahrtTarget.request(MediaType.APPLICATION_JSON)
					.post(Entity.json(dieserZug));
			counter++;
		}while((resp.getStatus()!=HttpStatus.OK_200)&&warten(5)&&counter<=3);
		if(resp.getStatus()!=HttpStatus.OK_200){
			System.out.println("Fehler: Zeile 297");
			System.out.println(resp);
		}
		EinfahrtErlaubnis erlaubnis =  resp.readEntity(EinfahrtErlaubnis.class);
		einfahrtTarget = client.target("http://"+knotenpunkt.getAdresse()+":8080").path("/einfahrtMelden");
		counter=0;
		do{
			resp = einfahrtTarget.request(MediaType.APPLICATION_JSON)
					.post(Entity.json(new Einfahrt(dieserZug,erlaubnis.getGleis())));
			counter++;
		}while((resp.getStatus()!=HttpStatus.OK_200)&&warten(5)&&counter<=3);
		if(resp.getStatus()!=HttpStatus.OK_200){
			System.out.println("Fehler: Zeile 299");
			System.out.println(resp);
		}
		//Abfahrt von letzter Strecke melden, wenn sie nicht null ist
		//Wenn die letzte Strecke null, dann beginnt der Zug gerade erst
		if(letzteStrecke!=null){
			netzverwaltung.path("/fahrt/abschluss").request(MediaType.APPLICATION_JSON)
			.post(Entity.json(new FahrtAbschluss(dieserZug,letzteStrecke)));
		}
		//Fahrt Abschließen
		letzteStrecke = null;
		letzterKnotenpunkt=knotenpunkt;
		letztesGleis=erlaubnis.getGleis();
		//An Gleis warten
		if(!warten(aufenthalt*60)){
			System.out.println("Fehler: Zeile 313");
		}
		
		
	}
	/*
	 * Methode um 'length' Sekunden zu warten
	 */
	private boolean warten(int length){
		try {
			TimeUnit.SECONDS.sleep(length);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
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
		Response resp = netzverwaltung.path("/knotenpunkt/"+letzterKnotenpunkt.getId()).request().get();
		if(resp.getStatus()==HttpStatus.OK_200){
			letzterKnotenpunkt = resp.readEntity(Knotenpunkt.class);
			//Erneut versuchen
			WebTarget streckenTarget = client.target("http://"+letzterKnotenpunkt.getAdresse()+":8080").path("/strecke");
			resp = streckenTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(knotenpunkt));
			//Wenn er zu erreichen ist
			if(resp.getStatus()==HttpStatus.OK_200){
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
					System.out.println("Fehler: Zeile 381");
					return null;
				}
			}
		}
		//Wenn die Netzverwaltung ihn nicht kennt
		else{
			System.out.println("Fehler: Zeile 388");
			return null;
		}
		
		
	}
	private URI knotenpunktAdresseFinden(Knotenpunkt knotenpunkt){
		Response resp = netzverwaltung.path("/knotenpunkt/"+knotenpunkt.getId())
				.request(MediaType.APPLICATION_JSON).get();
		if(resp.getStatus()==HttpStatus.OK_200){
			knotenpunkt = resp.readEntity(Knotenpunkt.class);
			return knotenpunkt.getAdresse();
		}
		else{
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