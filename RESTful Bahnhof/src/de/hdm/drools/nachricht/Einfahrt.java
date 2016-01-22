package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Zug;

public class Einfahrt extends Nachricht {

	public Einfahrt(){
		
	}
	public Einfahrt(Zug zug,Gleis gleis){
		this.zug=zug;
		this.gleis=gleis;
	}
}