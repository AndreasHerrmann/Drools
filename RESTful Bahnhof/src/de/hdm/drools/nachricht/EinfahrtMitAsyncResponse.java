package de.hdm.drools.nachricht;

import javax.ws.rs.container.AsyncResponse;

public class EinfahrtMitAsyncResponse extends Einfahrt{
	private AsyncResponse asyncResponse;
	
	public EinfahrtMitAsyncResponse(Einfahrt fahrtbeginn,
			AsyncResponse asyncResponse){
		this.zug=fahrtbeginn.getZug();
		this.gleis=fahrtbeginn.getGleis();
		this.asyncResponse=asyncResponse;
	}
	public AsyncResponse getAsyncResponse(){
		return asyncResponse;
	}
}