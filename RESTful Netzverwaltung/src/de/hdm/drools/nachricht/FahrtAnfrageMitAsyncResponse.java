package de.hdm.drools.nachricht;

import javax.ws.rs.container.AsyncResponse;

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