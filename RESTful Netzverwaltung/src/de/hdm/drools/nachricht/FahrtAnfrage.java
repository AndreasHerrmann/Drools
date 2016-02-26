package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

/**
 * Nachricht, die eine Anfrage für eine Fahrt beschreibt. Generalisiert {@link Nachricht}
 * @author Andreas Herrmann
 */
public class FahrtAnfrage extends Nachricht {
	/**
	 * Leerer Konstruktor für den Jackson JSON-Provider
	 */
	public FahrtAnfrage(){
		
	}
	/**
	 * Konstruktor, der auch den Zug und die Strecke setzt
	 * @param zug Der Zug, der die Fahrtanfrage stellt
	 * @param strecke Die Strecke, die der Zug befahren möchte
	 */
	public FahrtAnfrage(Zug zug, Strecke strecke){
		this.zug=zug;
		this.strecke=strecke;
	}
}