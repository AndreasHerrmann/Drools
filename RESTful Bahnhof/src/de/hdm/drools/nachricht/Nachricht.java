package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Zug;

/**
 * Abstrakte Oberklasse für alle Nachrichten, die ein Bahnhof erhalten kann.
 * @author Andreas Herrmann
 * @param zug Der Zug auf den sich diese Nachricht bezieht
 * @param gleis Das Gleis auf den sich diese Nachricht bezieht
 */
public abstract class Nachricht {
	protected Zug zug;
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