package de.hdm.bahnhofsteuerung;

/*
 * Einstellungen für den Ablauf der Bahnhofsteuerung
 */
public class Einstellungen {
	private static Einstellungen einstellungen=null;
	//Länge einer Zeiteinheit in Millisekunden (entspr. 1 Minute)
	private long zeitEinheitLaenge = 250;
	private int vergangeneZeiteinheiten = 0; 
	
	private Einstellungen(){
		super();
	}

	public static Einstellungen einstellungen(){
		if(einstellungen==null){
			einstellungen=new Einstellungen();
		}
		return einstellungen;
	}
	
	public long getZeitEinheitLaenge() {
		return zeitEinheitLaenge;
	}

	public int getVergangeneZeiteinheiten() {
		return vergangeneZeiteinheiten;
	}

	public void vergangeneZeiteinheitenErhoehen() {
		this.vergangeneZeiteinheiten++;
	}
}
