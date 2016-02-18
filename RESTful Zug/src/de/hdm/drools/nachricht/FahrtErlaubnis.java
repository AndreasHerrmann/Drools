package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

public class FahrtErlaubnis extends NachrichtNetzverwaltung {

	public FahrtErlaubnis(){
		
	}
	public FahrtErlaubnis(Zug zug, Strecke strecke){
		this.zug=zug;
		this.strecke=strecke;
	}
}