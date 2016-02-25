package de.hdm.drools.resource;

import java.net.URI;

public class Zug extends Resource {
	private URI adresse;
	private Fahrplan fahrplan;
	
	public Zug(){
		
	}
	public URI getAdresse() {
		return adresse;
	}
	public Fahrplan getFahrplan(){
		return fahrplan;
	}
	public boolean equals(Zug zug){
		if(this.id==zug.getId()){
			return true;
		}
		else{
			return false;
		}
	}
}
