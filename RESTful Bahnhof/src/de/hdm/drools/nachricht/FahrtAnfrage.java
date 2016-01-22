package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Gleis;
import de.hdm.drools.resource.Zug;

public class FahrtAnfrage extends Nachricht {

	public FahrtAnfrage(){
		
	}
	public FahrtAnfrage(Zug zug, Gleis gleis){
		this.zug=zug;
		this.gleis=gleis;
	}
}