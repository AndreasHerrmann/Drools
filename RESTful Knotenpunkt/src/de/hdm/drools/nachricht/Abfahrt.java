package de.hdm.drools.nachricht;

import de.hdm.drools.resource.Zug;

public class Abfahrt extends Nachricht {
	public Abfahrt(){
		
	}
	public Abfahrt(Zug zug){
		this.zug=zug;
	}
}
