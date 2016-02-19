package de.hdm.drools.resource;

public class Knotenpunkt extends Resource {
	
	public Knotenpunkt(){
		
	}
	public Knotenpunkt(long iD){
		this.iD=iD;
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
