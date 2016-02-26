package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

/**
 * Nachricht, die einen Fahrtabschluss beschreibt. Generalisiert {@link Nachricht}
 * @author Andreas Herrmann
 */
public class FahrtAbschluss extends Nachricht {
	/**
	 * Leerer Konstruktor für den Jackson JSON-Provider
	 */
	public FahrtAbschluss(){
		
	}
	/**
	 * Konstruktor, der auch den Zug und die Strecke setzt
	 * @param zug Der Zug, der seine Fahrt abgeschlossen hat
	 * @param strecke Die Strecke auf der die Fahrt stattfand
	 */
	public FahrtAbschluss(Zug zug, Strecke strecke){
		this.zug=zug;
		this.strecke=strecke;
	}
}