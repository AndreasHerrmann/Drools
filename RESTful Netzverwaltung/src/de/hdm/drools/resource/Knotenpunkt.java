package de.hdm.drools.resource;

import java.net.URI;

public class Knotenpunkt extends Resource {
	protected URI adresse;
	private boolean istBahnhof=false;
	
	public Knotenpunkt(){
		
	}
	public Knotenpunkt(long iD){
		this.iD=iD;
	}
	public Knotenpunkt(long iD, boolean istBahnhof){
		this.iD=iD;
		this.istBahnhof=istBahnhof;
	}
	public URI getAdresse() {
		return adresse;
	}
	public void setAdresse(URI adresse) {
		this.adresse = adresse;
	}
	public boolean getIstBahnhof(){
		return istBahnhof;
	}
	
	public boolean equals(Knotenpunkt knotenpunkt){
		if(this.iD==knotenpunkt.getID()){
			return true;
		}
		else{
			return false;
		}
	}
}
