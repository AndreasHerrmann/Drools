package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Zug;

/**
 * Abstrakte Oberklasse für alle Nachrichten, die einKnotenpunkt erhalten kann.
 * @author Andreas Herrmann
 * @param zug Der {@link de.hdm.drools.resource.Zug} auf den sich diese Nachricht bezieht
 */
public abstract class Nachricht {
	/**
	 * Der {@link de.hdm.drools.resource.Zug} auf den sich diese Nachricht bezieht
	 */
	protected Zug zug;
	
	public Nachricht(){
		
	}
	public Nachricht(Zug zug){
		this.zug=zug;
	}
	public Zug getZug(){
		return zug;
	}
}
