package de.hdm.drools.nachricht;

import javax.ws.rs.container.AsyncResponse;

public class EinfahrtAnfrageMitAsyncResponse extends EinfahrtAnfrage {
	private AsyncResponse asyncResponse;
	private boolean istErledigt = false;

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