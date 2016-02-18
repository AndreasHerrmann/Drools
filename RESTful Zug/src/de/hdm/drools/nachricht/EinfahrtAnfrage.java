package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Zug;

public class EinfahrtAnfrage extends NachrichtBahnhof {

	public EinfahrtAnfrage(){
		
	}
	public EinfahrtAnfrage(Zug zug, Gleis gleis){
		this.zug=zug;
		this.gleis=gleis;
	}
}