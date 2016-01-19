package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

public abstract class Nachricht {
	protected Zug zug;
	protected Strecke strecke;
	
	public Nachricht(){
		
	}
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