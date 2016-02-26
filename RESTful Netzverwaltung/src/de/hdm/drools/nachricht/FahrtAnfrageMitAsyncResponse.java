package de.hdm.drools.nachricht;

import javax.ws.rs.container.AsyncResponse;

/**
 * Erweiter eine {@link FahrtAnfrage} um eine {@link javax.ws.rs.container.AsyncResponse}
 * zum beantworten der Anfrage.
 * @author user
 *
 */
public class FahrtAnfrageMitAsyncResponse extends FahrtAnfrage {
	private AsyncResponse asyncResponse;
	private boolean istErledigt=false;

	public FahrtAnfrageMitAsyncResponse(FahrtAnfrage fahrtanfrage,
			AsyncResponse asyncResponse){
		this.zug=fahrtanfrage.getZug();
		this.strecke=fahrtanfrage.getStrecke();
		this.asyncResponse=asyncResponse;
	}
	public AsyncResponse getAsyncResponse() {
		return asyncResponse;
	}
	public boolean getIstErledigt(){
		return istErledigt;
	}
	public void setIstErledigt(boolean istErledigt){
		this.istErledigt=istErledigt;
	}
}