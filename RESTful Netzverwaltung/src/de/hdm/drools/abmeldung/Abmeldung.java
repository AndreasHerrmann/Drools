package de.hdm.drools.abmeldung;

/**
 * Abstrakte Oberklasse für alle Abmeldungen, die an die Netzverwaltung geschickt werden können.
 * @author Andreas Herrmann
 * @param istErledigt Dient dem {@link de.hdm.drools.KnowledgeBaseThread} zum unterscheiden von schon bearbeiteten und noch nicht bearbeiteten Anfragen
 */
public abstract class Abmeldung {
	/**
	 * Dient dem {@link de.hdm.drools.KnowledgeBaseThread} zum unterscheiden von schon bearbeiteten und noch nicht bearbeiteten Anfragen
	 */
	protected boolean istErledigt=false;

	public boolean getIstErledigt() {
		return istErledigt;
	}

	public void setIstErledigt(boolean istErledigt) {
		this.istErledigt = istErledigt;
	}
	
}
