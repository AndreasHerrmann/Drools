package de.hdm.drools.resource;

public class Fahrplan extends Resource {
	private Knotenpunkt[] fahrtziele;
	private Integer[] aufenthaltszeiten;
	
	public Fahrplan(){
		
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