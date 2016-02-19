package de.hdm.drools.nachricht;

import javax.ws.rs.container.AsyncResponse;

import de.hdm.drools.resource.Zug;

public class AuffahrtAnfrageMitAsyncResponse extends Nachricht {
	private AsyncResponse asyncResponse;
	private boolean istErledigt=false;
	
	public AuffahrtAnfrageMitAsyncResponse(AsyncResponse asyncResponse, Zug zug){
		this.asyncResponse=asyncResponse;
		this.zug=zug;
	}
	public AsyncResponse getAsyncResponse(){
		return asyncResponse;
	}
	public boolean getIstErledigt() {
		return istErledigt;
	}
	public void setIstErledigt(boolean istErledigt) {
		this.istErledigt = istErledigt;
	}
}
