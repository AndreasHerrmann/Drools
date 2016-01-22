package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Zug;

public class EinfahrtErlaubnis extends Nachricht {

	public EinfahrtErlaubnis(){
		
	}
	public EinfahrtErlaubnis(Zug zug, Gleis gleis){
		this.zug=zug;
		this.gleis=gleis;
	}
}