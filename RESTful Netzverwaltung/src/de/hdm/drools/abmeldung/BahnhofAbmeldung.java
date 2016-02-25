package de.hdm.drools.abmeldung;

import de.hdm.drools.resource.Bahnhof;

public class BahnhofAbmeldung extends Abmeldung {
private Bahnhof bahnhof;

public BahnhofAbmeldung(Bahnhof bahnhof){
	this.bahnhof=bahnhof;
}
public Bahnhof getBahnhof(){
	return bahnhof;
}
}
