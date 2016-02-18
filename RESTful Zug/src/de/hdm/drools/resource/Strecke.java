package de.hdm.drools.resource;

public class Strecke extends Resource {
	private Knotenpunkt start;
	private Knotenpunkt ziel;
	
	public Strecke(){
		
	}
	public Knotenpunkt getStart() {
		return start;
	}
	public Knotenpunkt getZiel() {
		return ziel;
	}
	public boolean equals(Strecke strecke){
		if(this.iD==strecke.getiD()){
			return true;
		}
		else{
			return false;
		}
	}
}
