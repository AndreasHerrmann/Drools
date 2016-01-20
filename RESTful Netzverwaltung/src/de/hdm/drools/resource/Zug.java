package de.hdm.drools.resource;

import java.net.URI;

public class Zug extends Resource {
	private URI adresse;
	private Fahrplan fahrplan;
	
	public Zug(){
		
	}
	public Zug(long iD, Fahrplan fahrplan){
		this.iD=iD;
		this.fahrplan=fahrplan;
	}
	public URI getAdresse() {
		return adresse;
	}
	public void setAdresse(URI adresse) {
		this.adresse = adresse;
	}
	public Fahrplan getFahrplan(){
		return fahrplan;
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
