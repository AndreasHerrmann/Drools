package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

public class FahrtAbschluss extends Nachricht {

	public FahrtAbschluss(){
		
	}
	public FahrtAbschluss(Zug zug, Strecke strecke){
		this.zug=zug;
		this.strecke=strecke;
	}
}