package de.hdm.drools.resource;

public class Strecke extends Resource {
	private Knotenpunkt start;
	private Knotenpunkt ziel;
	
	public Strecke(){
		
	}
	public Strecke(long iD, Knotenpunkt von, Knotenpunkt nach){
		this.iD=iD;
		this.start=von;
		this.ziel=nach;
	}
	public Knotenpunkt getStart() {
		return start;
	}
	public Knotenpunkt getZiel() {
		return ziel;
	}
	public boolean equals(Strecke strecke){
		if(this.iD==strecke.getID()){
			return true;
		}
		else{
			return false;
		}
	}
}
