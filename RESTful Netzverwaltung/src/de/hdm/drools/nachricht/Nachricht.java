package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

/**
 * Abstrakte Oberklasse für alle Nachrichten, die an die Netzverwaltung geschickt werden können
 * und weder eine {@link de.hdm.drools.abmeldung.Abmeldung}, noch eine {@link de.hdm.drools.anmeldung.Anmeldung} sind.
 * @author Andreas Herrmann
 * @param zug Der {@link de.hdm.drools.resource.Zug} auf den sich die Nachricht bezieht
 * @param strecke Die {@link de.hdm.drools.resource.Strecke} auf die sich die Nachricht bezieht
 */
public abstract class Nachricht {
	/**
	 * Der Zug auf den sich die Nachricht bezieht
	 */
	protected Zug zug;
	/**
	 * Die Strecke auf die sich die Nachricht bezieht
	 */
	protected Strecke strecke;
	
	/**
	 * Leerer Konstruktor für den Jackson JSON-Provider
	 */
	public Nachricht(){
		
	}
	/**
	 * Konstruktor, der auch den Zug und die Strecke setzt
	 * @param zug Der Zug auf den sich die Nachricht bezieht
	 * @param strecke Die Strecke auf die sich die Nachricht bezieht
	 */
	public Nachricht(Zug zug, Strecke strecke){
		this.zug=zug;
		this.strecke=strecke;
	}
	public Zug getZug() {
		return zug;
	}
	public Strecke getStrecke() {
		return strecke;
	}
}