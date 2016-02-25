package de.hdm.drools.resource;

/**
 * Beschreibt ein Gleis eines Bahnhofs
 * @author Andreas Herrmann
 * @param nummer Die Nummer des Gleises
 */
public class Gleis {
	/**
	 * Die Nummer des Gleises
	 */
	private int nummer;
	
	/**
	 * Leerer Konstruktor für den Jackson JSON-Provider
	 */
	public Gleis(){
		
	}
	/**
	 * Konstruktor, der auch die Gleisnummer setzt
	 * @param nummer Die Nummer des Gleises
	 */
	public Gleis(int nummer){
		this.nummer=nummer;
	}
	public int getNummer() {
		return nummer;
	}
}
