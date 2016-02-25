package de.hdm.drools.abmeldung;

public abstract class Abmeldung {
	protected boolean istErledigt=false;

	public boolean getIstErledigt() {
		return istErledigt;
	}

	public void setIstErledigt(boolean istErledigt) {
		this.istErledigt = istErledigt;
	}
	
}
