package de.hdm.drools.resource;

import java.net.URI;

public class Knotenpunkt extends Resource {
	protected URI adresse;
	private boolean istBahnhof=false;
	
	public Knotenpunkt(){
		
	}
	public URI getAdresse() {
		return adresse;
	}
	public boolean getIstBahnhof(){
		return istBahnhof;
	}
	
	public boolean equals(Knotenpunkt knotenpunkt){
		if(this.iD==knotenpunkt.getiD()){
			return true;
		}
		else{
			return false;
		}
	}
}