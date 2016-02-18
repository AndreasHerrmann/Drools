package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

public class Lebenszeichen extends NachrichtNetzverwaltung {

	public Lebenszeichen(){
		
	}
	public Lebenszeichen(Zug zug, Strecke strecke){
		this.zug=zug;
		this.strecke=strecke;
	}
}