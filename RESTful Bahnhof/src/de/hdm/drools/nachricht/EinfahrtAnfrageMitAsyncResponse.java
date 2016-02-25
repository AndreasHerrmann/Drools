package de.hdm.drools.nachricht;

import javax.ws.rs.container.AsyncResponse;

/**
 * Erweitert die Klasse {@link EinfahrtAnfrage} um eine {@link javax.ws.rs.container.AsyncResponse},
 * sodass die Anfrage aus dem {@link de.hdm.drools.KnowledgeBaseThread} heraus beantwortet werden kann.
 * @author Andreas Herrmann
 * @param asyncResponse Die {@link javax.ws.rs.container.AsyncResponse} zum beantworten der Anfrage
 * @param istErledigt Dient dem {@link de.hdm.drools.KnowledgeBaseThread} zum unterscheiden von schon bearbeiteten und noch nicht bearbeiteten Anfragen
 */
public class EinfahrtAnfrageMitAsyncResponse extends EinfahrtAnfrage {
	/**
	 * Die {@link javax.ws.rs.container.AsyncResponse} zum beantworten der Anfrage
	 */
	private AsyncResponse asyncResponse;
	/**
	 * Dient dem {@link de.hdm.drools.KnowledgeBaseThread} zum unterscheiden von schon bearbeiteten und noch nicht bearbeiteten Anfragen
	 */
	private boolean istErledigt = false;
	
	/**
	 * Konstruktor zum setzen aller benötigten Werte.
	 * @param fahrtanfrage Die {@link EinfahrtAnfrage}, die mit einer {@link javax.ws.rs.container.AsyncResponse} versehen werden soll
	 * @param asyncResponse Die {@link javax.ws.rs.container.AsyncResponse} zum benatworten der Anfrage
	 */
	public EinfahrtAnfrageMitAsyncResponse(EinfahrtAnfrage fahrtanfrage,
			AsyncResponse asyncResponse){
		this.zug=fahrtanfrage.getZug();
		this.gleis=fahrtanfrage.getGleis();
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