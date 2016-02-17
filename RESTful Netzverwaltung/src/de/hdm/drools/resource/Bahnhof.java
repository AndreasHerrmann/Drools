package de.hdm.drools.resource;

public class Bahnhof extends Knotenpunkt {
	private String name;
	private Knotenpunkt[] knotenpunkte;
	public Bahnhof(){
		
	}
	public Bahnhof(long iD, String name,Knotenpunkt[] knotenpunkte){
		this.iD=iD;
		this.name=name;
		this.knotenpunkte=knotenpunkte;
	}
	public String getName() {
		return name;
	}
	public Knotenpunkt[] getKnotenpunkte() {
		return knotenpunkte;
	}
	public Knotenpunkt getKnotenpunkt(int index){
		if(knotenpunkte.length<=index){
			return null;
		}
		else{
			return knotenpunkte[index];
		}
	}
	public boolean equals(Bahnhof bahnhof){
		if(this.iD==bahnhof.getID()){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean equals(Knotenpunkt knotenpunkt){
		for(int i=0; i<this.knotenpunkte.length; i++){
			if(this.knotenpunkte[i].equals(knotenpunkt)){
				return true;
			}
		}
		return false;
	}
}
