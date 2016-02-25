package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Zug;

/**
 * Nachricht, die eine Einfahrtanfrage beschreibt. Generalisiert {@link Nachricht}
 * @author Andreas Herrmann
 *
 */
public class EinfahrtAnfrage extends Nachricht {
	/**
	 * Leerer Konstruktor für den Jackson JSON-Parser 
	 */
	public EinfahrtAnfrage(){
		
	}
	/**
	 * Konstruktor zum setzen des Zuges und des Gleises, auf die sich diese Nachricht bezieht.
	 * @param zug Zug auf den sich diese Nachricht bezieht
	 * @param gleis Gleis auf das sich diese Nachricht bezieht
	 */
	public EinfahrtAnfrage(Zug zug, Gleis gleis){
		this.zug=zug;
		this.gleis=gleis;
	}
}