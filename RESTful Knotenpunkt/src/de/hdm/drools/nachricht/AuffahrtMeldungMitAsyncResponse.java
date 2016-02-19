package de.hdm.drools.nachricht;

import javax.ws.rs.container.AsyncResponse;

import de.hdm.drools.resource.Zug;

public class AuffahrtMeldungMitAsyncResponse extends Nachricht {
	private AsyncResponse asyncResponse;
	private boolean istErledigt=false;
	
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
