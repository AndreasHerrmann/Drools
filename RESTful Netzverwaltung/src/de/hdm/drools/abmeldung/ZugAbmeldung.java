package de.hdm.drools.abmeldung;

import de.hdm.drools.resource.Zug;

/**
 * Nachricht, die die Abmeldung eines Zugs bei der Netzverwaltung beschreibt.
 * Generalisiert {@link Abmeldung}
 * @author Andreas Herrmann
 * @param zug Der {@link de.hdm.drools.resource.Zug}, der sich abmelden möchte
 */
public class ZugAbmeldung extends Abmeldung {
	/**
	 * Der {@link de.hdm.drools.resource.Zug}, der sich abmelden möchte
	 */
	private Zug zug;
	
	/**
	 * Konstruktor, der auch den {@link de.hdm.drools.resource.Zug} setzt, der sich abmelden möchte
	 * @param zug Der {@link de.hdm.drools.resource.Zug}, der sich abmelden möchte
	 */
	public ZugAbmeldung(Zug zug){
		this.zug=zug;
	}
	public Zug getZug(){
		return zug;
	}
}
