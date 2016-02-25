package de.hdm.drools.resource;

import java.net.URI;

public class Knotenpunkt extends Resource {
	protected URI adresse;
	private boolean istBahnhof;
	
	public Knotenpunkt(){
		
	}
	public URI getAdresse() {
		return adresse;
	}
	public void setAdresse(URI adresse){
		this.adresse=adresse;
	}
	public boolean getIstBahnhof(){
		return istBahnhof;
	}
	
	public boolean equals(Knotenpunkt knotenpunkt){
		if(this.id==knotenpunkt.getId()){
			return true;
		}
		else{
			return false;
		}
	}
}