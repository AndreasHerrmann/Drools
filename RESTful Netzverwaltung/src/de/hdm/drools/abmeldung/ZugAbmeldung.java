package de.hdm.drools.abmeldung;

import de.hdm.drools.resource.Zug;

public class ZugAbmeldung extends Abmeldung {
private Zug zug;

public ZugAbmeldung(Zug zug){
	this.zug=zug;
}
public Zug getZug(){
	return zug;
}
}
