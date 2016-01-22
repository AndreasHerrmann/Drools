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
		if(index<knotenpunkte.length){
			return knotenpunkte[index];
		}
		else{
			return null;
		}
	}
	public boolean equals(Bahnhof bahnhof){
		if(this.iD==bahnhof.getiD()){
			return true;
		}
		else{
			return false;
		}
	}
}
