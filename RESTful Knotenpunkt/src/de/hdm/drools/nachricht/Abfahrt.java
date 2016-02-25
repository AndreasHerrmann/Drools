package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Zug;

/**
 * Nachricht, die eine Abfahrt beschreibt. Generalisiert {@link Nachricht}
 * @author Andreas Herrmann
 *
 */
public class Abfahrt extends Nachricht {
	/**
	 * Leerer Konstruktor für den Jackson JSON-Parser 
	 */
	public Abfahrt(){
		
	}
	/**
	 * Konstruktor zum setzen des Zuges , auf den sich diese Nachricht bezieht.
	 * @param zug Zug auf den sich diese Nachricht bezieht
	 */
	public Abfahrt(Zug zug){
		this.zug=zug;
	}
}
