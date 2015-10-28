package de.hdm.bahnhofsteuerung;

/*
 * Einstellungen für den Ablauf der Bahnhofsteuerung
 */
public class Einstellungen {
	private Einstellungen einstellungen=null;
	//Länge einer Zeiteinheit in Millisekunden
	private long zeitEinheitLaenge = 250;


	public Einstellungen einstellungen(){
		if(einstellungen==null){ 
			einstellungen=new Einstellungen();
		}
		return einstellungen;
	}
	
	public long getZeitEinheitLaenge() {
		return zeitEinheitLaenge;
	}
}
