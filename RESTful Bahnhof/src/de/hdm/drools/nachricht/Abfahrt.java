package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Zug;

public class Abfahrt extends Nachricht {

	public Abfahrt(){
		
	}
	public Abfahrt(Zug zug, Gleis gleis){
		this.zug=zug;
		this.gleis=gleis;
	}
}