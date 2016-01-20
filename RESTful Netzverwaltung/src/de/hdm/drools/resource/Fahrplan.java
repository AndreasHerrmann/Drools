package de.hdm.drools.resource;

import java.util.Vector;

public class Fahrplan extends Resource {
	private Knotenpunkt[] fahrtziele;
	private Integer[] aufenthaltszeiten;
	
	Fahrplan(){
		
	}
	Fahrplan(long id, Vector<Knotenpunkt> knotenpunkte,Vector<Integer> zeiten){
		this.iD=id;
		this.fahrtziele = new Knotenpunkt[knotenpunkte.size()];
		this.fahrtziele=knotenpunkte.toArray(fahrtziele);
		this.aufenthaltszeiten= new Integer[zeiten.size()];
		this.aufenthaltszeiten=zeiten.toArray(aufenthaltszeiten);
	}
	public Knotenpunkt[] getFahrtziele() {
		return fahrtziele;
	}
	
	public Knotenpunkt getFahrtziel(int index){
		return fahrtziele[index];
	}
	
	public int getAufenthaltszeit(int index){
		return aufenthaltszeiten[index];
	}
	
	public Integer[] getAufenthaltszeiten(){
		return aufenthaltszeiten;
	}
}
