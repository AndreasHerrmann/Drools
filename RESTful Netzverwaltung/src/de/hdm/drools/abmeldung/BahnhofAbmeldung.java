package de.hdm.drools.abmeldung;

import de.hdm.drools.resource.Bahnhof;

/**
 * Nachricht, die die Abmeldung eines Bahnhofs bei der Netzverwaltung beschreibt.
 * Generalisiert {@link Abmeldung}
 * @author Andreas Herrmann
 * @param bahnhof Der {@link de.hdm.drools.resource.Bahnhof}, der sich abmelden möchte
 */
public class BahnhofAbmeldung extends Abmeldung {
	/**
	 * Der {@link de.hdm.drools.resource.Bahnhof}, der sich abmelden möchte
	 */
	private Bahnhof bahnhof;
	
	/**
	 * Konstruktor, der auch den {@link de.hdm.drools.resource.Bahnhof} setzt, der sich abmelden möchte
	 * @param bahnhof Der {@link de.hdm.drools.resource.Bahnhof}, der sich abmelden möchte
	 */
	public BahnhofAbmeldung(Bahnhof bahnhof){
		this.bahnhof=bahnhof;
	}
	public Bahnhof getBahnhof(){
		return bahnhof;
	}
}
