package de.hdm.drools.anmeldung;

import java.net.URI;

import javax.ws.rs.container.AsyncResponse;

public abstract class Anmeldung {
	protected AsyncResponse asyncResponse;
	protected URI adresse;
	protected boolean istErledigt = false;
	
	public AsyncResponse getAsyncResponse(){
		return asyncResponse;
	}
	public URI getAdresse(){
		return adresse;
	}
	public boolean getIstErledigt() {
		return istErledigt;
	}
	public void setIstErledigt(boolean istErledigt) {
		this.istErledigt = istErledigt;
	}
}