package de.hdm.drools.resource;

public class Fahrplan extends Resource {
	@SuppressWarnings("unused")
	private Knotenpunkt[] fahrtziele;
	@SuppressWarnings("unused")
	private Integer[] aufenthaltszeiten;
	
	public Fahrplan(){
		
	}
	public Fahrplan(long id, Knotenpunkt[] knotenpunkte,Integer[] zeiten){
		this.iD=id;
		this.fahrtziele=knotenpunkte;
		this.aufenthaltszeiten=zeiten;
	}
}