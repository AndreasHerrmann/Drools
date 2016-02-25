package de.hdm.drools.resource;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("fahrplan")
public class Zug extends Resource {
	private URI adresse;
	
	public Zug(){
		
	}
	public Zug(long iD){
		this.id=iD;
	}
	public URI getAdresse() {
		return adresse;
	}
	public void setAdresse(URI adresse) {
		this.adresse = adresse;
	}
	public boolean equals(Zug zug){
		if(this.id==zug.getid()){
			return true;
		}
		else{
			return false;
		}
	}
}
