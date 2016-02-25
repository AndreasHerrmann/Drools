package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Zug;

/**
 * Abstrakte Oberklasse für alle Nachrichten, die ein Bahnhof erhalten kann.
 * @author Andreas Herrmann
 * @param zug Der {@link de.hdm.drools.resource.Zug} auf den sich diese Nachricht bezieht
 * @param gleis Das {@link de.hdm.drools.resource.Gleis} auf den sich diese Nachricht bezieht
 */
public abstract class Nachricht {
	/**
	 * Der {@link de.hdm.drools.resource.Zug} auf den sich diese Nachricht bezieht
	 */
	protected Zug zug;
	/**
	 * Das {@link de.hdm.drools.resource.Gleis} auf den sich diese Nachricht bezieht
	 */
	protected Gleis gleis;
	
	public Nachricht(){
		
	}
	public Nachricht(Zug zug, Gleis gleis){
		this.zug=zug;
		this.gleis=gleis;
	}
	public Zug getZug() {
		return zug;
	}
	public Gleis getGleis(){
		return gleis;
	}
}