package de.hdm.drools.resource;

public class Bahnhof extends Knotenpunkt {
	private String name;
	private Knotenpunkt[] knotenpunkte;
	private int gleisAnzahl;
	public Bahnhof(){
		
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
	public int getGleisAnzahl(){
		return gleisAnzahl;
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
