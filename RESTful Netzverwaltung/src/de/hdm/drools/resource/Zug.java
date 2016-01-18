package de.hdm.drools.resource;

import java.net.URI;

public class Zug extends Resource {
	private URI adresse;
	
	public Zug(){
		
	}
	public Zug(long iD){
		this.iD=iD;
	}
	public URI getAdresse() {
		return adresse;
	}
	public void setAdresse(URI adresse) {
		this.adresse = adresse;
	}
	public boolean equals(Zug zug){
		if(this.iD==zug.getiD()){
			return true;
		}
		else{
			return false;
		}
	}
}
