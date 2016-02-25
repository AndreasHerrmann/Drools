package de.hdm.drools.resource;

/**
 * Beschreibt einen Bahnhof aus der Sicht des Bahnhofs. Generalisiert {@link Knotenpunkt}.
 * @author Andreas Herrmann
 * @param name Der Name des jeweiligen Bahnhofs
 * @param knotenpunkte Knotenpunkte, die zum Bahnhof gehören
 * @param gleisAnzahl Die Anzahl der Gleise, die der Bahnhof besitzt
 */
public class Bahnhof extends Knotenpunkt {
	/**
	 * Der Name des Bahnhofs
	 */
	private String name;
	/**
	 * Die Knotenpunkte, die zum Bahnhof gehören
	 */
	private Knotenpunkt[] knotenpunkte;
	/**
	 * Die Anzahl der Gleise, die der Bahnhof besitzt
	 */
	private int gleisAnzahl;
	
	/**
	 * Leerer Konstruktor für den Jackson JSON-Provider
	 */
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
	/**
	 * Vergleicht die ID des Bahnhofs mit der ID des übergebenen Bahnhofs.
	 * Gibt {@code true} zurück, wenn sie übereinstimmen, ansonsten {@code false}
	 * @param bahnhof Der Bahnhof mit dem verglichen werden soll
	 * @return {@code true}, wenn beide IDs übereinstimmen, ansonsten {@code false}
	 */
	public boolean equals(Bahnhof bahnhof){
		if(this.id==bahnhof.getid()){
			return true;
		}
		else{
			return false;
		}
	}
}
