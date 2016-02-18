package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Zug;

public abstract class NachrichtBahnhof {
	protected Zug zug;
	protected Gleis gleis;
	
	public NachrichtBahnhof(){
		
	}
	public NachrichtBahnhof(Zug zug, Gleis gleis){
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