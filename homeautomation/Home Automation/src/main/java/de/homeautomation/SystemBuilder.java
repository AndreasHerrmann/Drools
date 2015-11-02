package de.homeautomation;

import org.kie.api.runtime.KieSession;

import de.homeautomation.devices.*;
import de.homeautomation.devices.GameConsole.Typ;
import de.homeautomation.devices.Geraet.Connection;
import de.homeautomation.resources.Ort;

public class SystemBuilder {
	private static Ambilight ambi;
	private static Fenster schlafFenster;
	private static Fenster schlafBalkonTuer;
	private static Fenster wohnFenster;
	private static Fenster wohnBalkonTuer;
	private static GameConsole wiiU;
	private static GameConsole xbox;
	private static Heizung badHeizung;
	private static Heizung schlafHeizung;
	private static Heizung wohnHeizung;
	private static HTPC mediaCenter;
	private static Licht badSpiegel;
	private static Licht badDecke;
	private static Licht schlafDecke;
	private static Licht schlafNachttisch;
	private static Licht schlafStehlampe;
	private static Licht wohnDecke;
	private static Licht wohnSofaLicht;
	private static Licht wohnSchreibtischlicht;
	private static PC arbeitsComputer;
	private static Rolladen schlafFensterRolladen;
	private static Rolladen schlafBalkontuerRolladen;
	private static Rolladen wohnFensterRolladen;
	private static Rolladen wohnBalkontuerRolladen;
	private static Router router;
	private static Temperaturfuehler tempBad;
	private static Temperaturfuehler tempSchlaf;
	private static Temperaturfuehler tempWohn;
	private static TV wohnTV;
	private static SystemBuilder builder=null;
	
	private SystemBuilder(KieSession kSession){
		ambi = new Ambilight("AmbilightMac");
		schlafFenster = new Fenster("Fenster",Ort.Schlafzimmer);
		schlafBalkonTuer = new Fenster("Balkontuer",Ort.Schlafzimmer);
		wohnFenster = new Fenster("Fenster",Ort.Wohnzimmer);
		wohnBalkonTuer = new Fenster("Balkontuer",Ort.Wohnzimmer);
		wiiU = new GameConsole("WiiU","WiiUMAC",Connection.WiFi,Typ.WiiU);
		xbox = new GameConsole("XBOX One","XboxMAC",Connection.Ethernet,Typ.XboxOne);
		badHeizung = new Heizung("BadHeizungMAC",Ort.Bad);
		schlafHeizung = new Heizung("SchlafzimmerHeizungMac",Ort.Schlafzimmer);
		wohnHeizung = new Heizung("WohnzimmerHeizungMAC",Ort.Wohnzimmer);
		mediaCenter = new HTPC("KodiMediaCenter","HTPCMAC",Connection.Ethernet);
		badSpiegel = new Licht("Spiegellicht","SpiegellichtMAC",Ort.Bad);
		badDecke = new Licht("Deckenlicht","BadDeckenlichtMAC",Ort.Bad);
		schlafDecke = new Licht("Deckenlicht","SchlafzimmerDeckenlichtMAC",Ort.Schlafzimmer);
		schlafNachttisch = new Licht("Nachttischlampe","SchlafzimmerNachttischlampeMAC",Ort.Schlafzimmer);
		schlafStehlampe = new Licht("Stehlampe","SChlafzimmerStehlampeMAC",Ort.Schlafzimmer);
		wohnDecke = new Licht("Deckenlicht","WohnzimmerDeckenlichtMAC",Ort.Wohnzimmer);
		wohnSofaLicht = new Licht("Sofalicht","WohnzimmerSofalichtMAC",Ort.Wohnzimmer);
		wohnSchreibtischlicht = new Licht("Schreibtischlicht","WohnzimmerSchreibtischlichtMAC",Ort.Wohnzimmer);
		arbeitsComputer = new PC("Arbeitsrechner","ArbeitsrechnerMAC",Connection.Ethernet);
		schlafFensterRolladen = new Rolladen("Fensterrolladen","SchlafzimmerrolladenMAC",Ort.Schlafzimmer);
		schlafBalkontuerRolladen = new Rolladen("Balkontuerrolladen","SchlafzimmerBalkontuerrolladenMAC",Ort.Schlafzimmer);
		wohnFensterRolladen = new Rolladen("Fensterrolladen","WohnzimmerRolladenMAC",Ort.Wohnzimmer);
		wohnBalkontuerRolladen = new Rolladen("Balkontuerrolladen","WohnzimmerBalkontuerrolladenMAC",Ort.Wohnzimmer);
		router = new Router();
		tempBad = new Temperaturfuehler("TempBadMAC",Ort.Bad);
		tempSchlaf = new Temperaturfuehler("TempSchlafMAC",Ort.Schlafzimmer);
		tempWohn = new Temperaturfuehler("TempWohnMAC",Ort.Wohnzimmer);
		wohnTV = new TV("Wohnzimmerfernseher","WohnzimmertvMAC",Connection.WiFi);
		
		//Geraete einsetzen
    	kSession.insert(ambi);
    	kSession.insert(schlafFenster);
    	kSession.insert(schlafBalkonTuer);
    	kSession.insert(wohnFenster);
    	kSession.insert(wohnBalkonTuer);
    	kSession.insert(wiiU);
    	kSession.insert(xbox);
    	kSession.insert(badHeizung);
    	kSession.insert(wohnHeizung);
    	kSession.insert(schlafHeizung);
    	kSession.insert(mediaCenter);
    	kSession.insert(badSpiegel);
    	kSession.insert(badDecke);
    	kSession.insert(schlafDecke);
    	kSession.insert(schlafNachttisch);
    	kSession.insert(schlafStehlampe);
    	kSession.insert(wohnDecke);
    	kSession.insert(wohnSofaLicht);
    	kSession.insert(wohnSchreibtischlicht);
    	kSession.insert(arbeitsComputer);
    	kSession.insert(schlafFensterRolladen);
    	kSession.insert(schlafBalkontuerRolladen);
    	kSession.insert(wohnFensterRolladen);
    	kSession.insert(wohnBalkontuerRolladen);
    	kSession.insert(router);
    	kSession.insert(tempBad);
    	kSession.insert(tempSchlaf);
    	kSession.insert(tempWohn);
    	kSession.insert(wohnTV);
	}
	public static SystemBuilder buildSystem(KieSession session){
		if(builder==null){
			builder= new SystemBuilder(session);
		}
		return builder;
	}
	public static Ambilight getAmbi() {
		return ambi;
	}
	public static Fenster getSchlafFenster() {
		return schlafFenster;
	}
	public static Fenster getSchlafBalkonTuer() {
		return schlafBalkonTuer;
	}
	public static Fenster getWohnFenster() {
		return wohnFenster;
	}
	public static Fenster getWohnBalkonTuer() {
		return wohnBalkonTuer;
	}
	public static GameConsole getWiiU() {
		return wiiU;
	}
	public static GameConsole getXbox() {
		return xbox;
	}
	public static Heizung getBadHeizung() {
		return badHeizung;
	}
	public static Heizung getSchlafHeizung() {
		return schlafHeizung;
	}
	public static Heizung getWohnHeizung() {
		return wohnHeizung;
	}
	public static HTPC getMediaCenter() {
		return mediaCenter;
	}
	public static Licht getBadSpiegel() {
		return badSpiegel;
	}
	public static Licht getBadDecke() {
		return badDecke;
	}
	public static Licht getSchlafDecke() {
		return schlafDecke;
	}
	public static Licht getSchlafNachttisch() {
		return schlafNachttisch;
	}
	public static Licht getSchlafStehlampe() {
		return schlafStehlampe;
	}
	public static Licht getWohnDecke() {
		return wohnDecke;
	}
	public Licht getWohnSofaLicht() {
		return wohnSofaLicht;
	}
	public Licht getWohnSchreibtischlicht() {
		return wohnSchreibtischlicht;
	}
	public PC getArbeitsComputer() {
		return arbeitsComputer;
	}
	public Rolladen getSchlafFensterRolladen() {
		return schlafFensterRolladen;
	}
	public Rolladen getSchlafBalkontuerRolladen() {
		return schlafBalkontuerRolladen;
	}
	public Rolladen getWohnFensterRolladen() {
		return wohnFensterRolladen;
	}
	public Rolladen getWohnBalkontuerRolladen() {
		return wohnBalkontuerRolladen;
	}
	public Router getRouter() {
		return router;
	}
	public Temperaturfuehler getTempBad() {
		return tempBad;
	}
	public Temperaturfuehler getTempSchlaf() {
		return tempSchlaf;
	}
	public Temperaturfuehler getTempWohn() {
		return tempWohn;
	}
	public TV getWohnTV() {
		return wohnTV;
	}
	public SystemBuilder getBuilder() {
		return builder;
	}
}
