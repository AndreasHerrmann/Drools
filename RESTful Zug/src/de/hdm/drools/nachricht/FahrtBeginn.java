package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

public class FahrtBeginn extends NachrichtNetzverwaltung {

	public FahrtBeginn(){
		
	}
	public FahrtBeginn(Zug zug,Strecke strecke){
		this.zug=zug;
		this.strecke=strecke;
	}
}