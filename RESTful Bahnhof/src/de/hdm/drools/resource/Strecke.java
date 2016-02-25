package de.hdm.drools.resource;

/**
 * Beschreibt eine Strecke aus der Sicht eines Bahnhofs. Generalisiert {@link Resource}.
 * @author Andreas Herrmann
 * @param start Der {@link Knotenpunkt}, an dem die Strecke beginnt
 * @param ziel Der {@link Knotenpunkt}, an dem die Strecke endet
 */
public class Strecke extends Resource {
	private Knotenpunkt start;
	private Knotenpunkt ziel;
	
	/**
	 * Leerer Konstruktor für den Jackson JSON-Provider
	 */
	public Strecke(){
		
	}
	public Knotenpunkt getStart() {
		return start;
	}
	public Knotenpunkt getZiel() {
		return ziel;
	}
	/**
	 * Vergleicht die ID der Strecke mit der ID der übergebenen Strecke.
	 * @param strecke Strecke, mit der verglichen werden soll
	 * @return {@code true}, wenn beide IDs übereinstimmen, ansonsten {@code false}
	 */
	public boolean equals(Strecke strecke){
		if(this.id==strecke.getid()){
			return true;
		}
		else{
			return false;
		}
	}
}
