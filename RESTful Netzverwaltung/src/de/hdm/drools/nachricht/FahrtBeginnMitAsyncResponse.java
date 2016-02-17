package de.hdm.drools.nachricht;

import javax.ws.rs.container.AsyncResponse;

public class FahrtBeginnMitAsyncResponse extends FahrtBeginn{
	private AsyncResponse asyncResponse;
	private boolean istErledigt=false;
	private boolean positivBeantwortet=false;
	
	public FahrtBeginnMitAsyncResponse(FahrtBeginn fahrtbeginn,
			AsyncResponse asyncResponse){
		this.zug=fahrtbeginn.getZug();
		this.strecke=fahrtbeginn.getStrecke();
		this.asyncResponse=asyncResponse;
	}
	public AsyncResponse getAsyncResponse(){
		return asyncResponse;
	}
	public boolean getIstErledigt(){
		return istErledigt;
	}
	public void setIstErledigt(boolean istErledigt){
		this.istErledigt=istErledigt;
	}
	public boolean getPositivBeantwortet(){
		return positivBeantwortet;
	}
	public void setPositivBeantwortet(boolean positivBeantwortet){
		this.positivBeantwortet=positivBeantwortet;
	}
}