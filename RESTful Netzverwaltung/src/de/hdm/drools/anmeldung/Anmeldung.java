package de.hdm.drools.anmeldung;

import java.net.URI;

import javax.ws.rs.container.AsyncResponse;

/**
 * Abstrakte Oberklasse für alle Anmeldungen, die an die Netzverwaltung geschickt werden können.
 * @author Andreas Herrmann
 * @param asyncResponse Die {@link javax.ws.rs.container.AsyncResponse}, die zum eantworten der Anfrage dient
 * @param adresse Die Adresse von der die Anfrage stammt
 * @param istErledigt Dient dem {@link de.hdm.drools.KnowledgeBaseThread} zum unterscheiden von schon bearbeiteten und noch nicht bearbeiteten Anfragen
 */
public abstract class Anmeldung {
	/**
	 * Die {@link javax.ws.rs.container.AsyncResponse}, die zum eantworten der Anfrage dient
	 */
	protected AsyncResponse asyncResponse;
	/**
	 * Die Adresse von der die Anfrage stammt
	 */
	protected URI adresse;
	/**
	 * Dient dem {@link de.hdm.drools.KnowledgeBaseThread} zum unterscheiden von schon bearbeiteten und noch nicht bearbeiteten Anfragen
	 */
	protected boolean istErledigt = false;
	
	public AsyncResponse getAsyncResponse(){
		return asyncResponse;
	}
	public URI getAdresse(){
		return adresse;
	}
	public boolean getIstErledigt() {
		return istErledigt;
	}
	public void setIstErledigt(boolean istErledigt) {
		this.istErledigt = istErledigt;
	}
}