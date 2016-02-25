package de.hdm.drools.resource;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("istBahnhof")
public class Knotenpunkt extends Resource {
	protected URI adresse;
	
	public Knotenpunkt(){
		
	}
	public Knotenpunkt(long iD){
		this.id=iD;
	}
	public URI getAdresse() {
		return adresse;
	}
	public void setAdresse(URI adresse) {
		this.adresse = adresse;
	}
	
	public boolean equals(Knotenpunkt knotenpunkt){
		if(this.id==knotenpunkt.getid()){
			return true;
		}
		else{
			return false;
		}
	}
}
