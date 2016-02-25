package de.hdm.drools.nachricht;

import javax.ws.rs.container.AsyncResponse;

import de.hdm.drools.resource.Zug;

/**
 * Nachricht, die eine Auffahrtmeldung beschreibt. Generalisiert {@link Nachricht}
 * @author Andreas Herrmann
 * @param asyncResponse {@link javax.ws.rs.container.AsyncResponse}, die zum Beantworten der Anfrage dient
 * @param istErledigt Dient dem {@link de.hdm.drools.KnowledgeBaseThread} zum unterscheiden von schon bearbeiteten und noch nicht bearbeiteten Anfragen
 */
public class AuffahrtMeldungMitAsyncResponse extends Nachricht {
	/**
	 * Dient zum beantworten der Anfrage
	 */
	private AsyncResponse asyncResponse;
	/**
	 * Dient dem {@link de.hdm.drools.KnowledgeBaseThread} zum unterscheiden von schon bearbeiteten und noch nicht bearbeiteten Anfragen
	 */
	private boolean istErledigt=false;
	
	/**
	 * Konstruktor zum setzen der Parameter der Anfrage
	 * @param asyncResponse {@link javax.ws.rs.container.AsyncResponse}, die zum Beantworten der Anfrage dient
	 * @param zug Der Zug auf den sich die Anfrage bezieht
	 */
	public AuffahrtMeldungMitAsyncResponse(Zug zug, AsyncResponse asyncResponse){
		this.zug=zug;
		this.asyncResponse=asyncResponse;
	}

	public AsyncResponse getAsyncResponse() {
		return asyncResponse;
	}
	public boolean getIstErledigt() {
		return istErledigt;
	}
	public void setIstErledigt(boolean istErledigt) {
		this.istErledigt = istErledigt;
	}
}
