package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Zug;

public abstract class Nachricht {
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
