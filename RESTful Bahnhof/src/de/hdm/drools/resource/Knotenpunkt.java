package de.hdm.drools.resource;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Beschreibt einen Knotenpunkt aus der SIcht eines Bahnhofs. Generalisiert {@link Resource}.
 * @author Andreas Herrmann
 * @param adresse Die Adresse des Knotenpunkts
 */
@JsonIgnoreProperties("istBahnhof")
public class Knotenpunkt extends Resource {
	/**
	 * Die Adresse des Knotenpunkts
	 */
	protected URI adresse;
	
	/**
	 * Leerer Konstruktor für den Jackson JSON-Provider
	 */
	public Knotenpunkt(){
		
	}
	public URI getAdresse(){
		return adresse;
	}
	/**
	 * Vergleicht die ID des Knotenpunkts mit der ID des übergebenen Knotenpunkts.
	 * Gibt {@code true} zurück, wenn sie übereinstimmen, ansonsten {@code false}
	 * @param knotenpunkt Der Knotenpunkt mit dem verglichen werden soll
	 * @return {@code true}, wenn beide IDs übereinstimmen, ansonsten {@code false}
	 */
	public boolean equals(Knotenpunkt knotenpunkt){
		if(this.id==knotenpunkt.getid()){
			return true;
		}
		else{
			return false;
		}
	}
}
