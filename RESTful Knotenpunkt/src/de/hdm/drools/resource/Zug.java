package de.hdm.drools.resource;

import java.net.URI;

/**
* Beschreibt einen Zug aus der Sicht eines Knotenpunkts. Generalisiert {@link Resource}.
* @author Andreas Herrmann
* @param adresse Die Adresse des Zuges
*/
public class Zug extends Resource {
	/**
	 * Die Adresse des Zuges
	 */
	private URI adresse;
	
	/**
	 * Leerer Konstruktor für den Jackson JSON-Provider
	 */
	public Zug(){
		
	}
	public URI getAdresse() {
		return adresse;
	}
	public void setAdresse(URI adresse) {
		this.adresse = adresse;
	}
	
	/**
	 * Vergleicht die ID des Zuges mit der ID des übergebenen Zuges.
	 * @param zug Der Zug, mit dem verglichen werden soll
	 * @return {@code true}, wenn beide IDs übereinstimmen, ansonsten {@code false}
	 */
	public boolean equals(Zug zug){
		if(this.id==zug.getid()){
			return true;
		}
		else{
			return false;
		}
	}
}
