package de.hdm.drools.resource;

import java.net.URI;

public class Knotenpunkt extends Resource {
	protected URI adresse;
	
	public Knotenpunkt(){
		
	}
	public Knotenpunkt(long iD){
		this.iD=iD;
	}
	public URI getAdresse() {
		return adresse;
	}
	public void setAdresse(URI adresse) {
		this.adresse = adresse;
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
