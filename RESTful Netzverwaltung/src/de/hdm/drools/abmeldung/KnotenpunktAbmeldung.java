package de.hdm.drools.abmeldung;

import de.hdm.drools.resource.Knotenpunkt;

/**
 * Nachricht, die die Abmeldung eines Knotenpunktss bei der Netzverwaltung beschreibt.
 * Generalisiert {@link Abmeldung}
 * @author Andreas Herrmann
 * @param knotenpunkt Der {@link de.hdm.drools.resource.Knotenpunkt}, der sich abmelden möchte
 */
public class KnotenpunktAbmeldung extends Abmeldung {
	/**
	 * Der {@link de.hdm.drools.resource.Knotenpunkt}, der sich abmelden möchte
	 */
	private Knotenpunkt knotenpunkt;
	
	/**
	 * Konstruktor, der auch den {@link de.hdm.drools.resource.Knotenpunkt} setzt, der sich abmelden möchte
	 * @param knotenpunkt Der {@link de.hdm.drools.resource.Knotenpunkt}, der sich abmelden möchte
	 */
	public KnotenpunktAbmeldung(Knotenpunkt knotenpunkt){
		this.knotenpunkt=knotenpunkt;
	}
	public Knotenpunkt getKnotenpunkt(){
		return knotenpunkt;
	}
}