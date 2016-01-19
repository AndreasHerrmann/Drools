package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

public class FahrtAnfrage extends Nachricht {

	public FahrtAnfrage(){
		
	}
	public FahrtAnfrage(Zug zug, Strecke strecke){
		this.zug=zug;
		this.strecke=strecke;
	}
}