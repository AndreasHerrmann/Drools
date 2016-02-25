package de.hdm.drools.abmeldung;

import de.hdm.drools.resource.Knotenpunkt;

public class KnotenpunktAbmeldung extends Abmeldung {
private Knotenpunkt knotenpunkt;

public KnotenpunktAbmeldung(Knotenpunkt knotenpunkt){
	this.knotenpunkt=knotenpunkt;
}
public Knotenpunkt getKnotenpunkt(){
	return knotenpunkt;
}
}