package de.hdm.drools.resource;

import java.net.URI;

public class Bahnhof extends Knotenpunkt {
	private String name;
	private Knotenpunkt[] knotenpunkte;
	private int gleisAnzahl;
	public Bahnhof(){
		
	}
	public Bahnhof(long iD, String name,Knotenpunkt[] knotenpunkte, int gleisAnzahl){
		this.iD=iD;
		this.name=name;
		this.knotenpunkte=knotenpunkte;
		this.gleisAnzahl=gleisAnzahl;
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
	public int getGleisAnzahl(){
		return gleisAnzahl;
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
	public void setAdresse(URI adresse){
		this.adresse=adresse;
		for(int i=0;i<knotenpunkte.length;i++){
			knotenpunkte[i].setAdresse(adresse);
		}
	}
}
