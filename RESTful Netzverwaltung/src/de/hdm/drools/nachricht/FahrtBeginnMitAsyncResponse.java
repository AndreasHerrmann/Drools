package de.hdm.drools.nachricht;

import javax.ws.rs.container.AsyncResponse;

public class FahrtBeginnMitAsyncResponse extends FahrtBeginn{
	private AsyncResponse asyncResponse;
	
	public FahrtBeginnMitAsyncResponse(FahrtBeginn fahrtbeginn,
			AsyncResponse asyncResponse){
		this.zug=fahrtbeginn.getZug();
		this.strecke=fahrtbeginn.getStrecke();
		this.asyncResponse=asyncResponse;
	}
	public AsyncResponse getAsyncResponse(){
		return asyncResponse;
	}
}