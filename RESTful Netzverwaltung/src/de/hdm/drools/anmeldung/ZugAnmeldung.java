package de.hdm.drools.anmeldung;

import java.net.URI;

import javax.ws.rs.container.AsyncResponse;

public class ZugAnmeldung extends Anmeldung {

	public ZugAnmeldung(AsyncResponse asyncResponse, URI adresse) {
		this.adresse=adresse;
		this.asyncResponse=asyncResponse;
	}

}
