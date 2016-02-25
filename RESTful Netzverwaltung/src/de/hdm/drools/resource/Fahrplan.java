package de.hdm.drools.resource;

public class Fahrplan extends Resource {
	private Knotenpunkt[] fahrtziele;
	private Integer[] aufenthaltszeiten;
	
	public Fahrplan(){
		
	}
	public Fahrplan(long id, Knotenpunkt[] knotenpunkte,Integer[] zeiten){
		this.id=id;
		this.fahrtziele=knotenpunkte;
		this.aufenthaltszeiten=zeiten;
	}
	public Knotenpunkt[] getFahrtziele() {
		return fahrtziele;
	}
	public Integer[] getAufenthaltszeiten() {
		return aufenthaltszeiten;
	}
}