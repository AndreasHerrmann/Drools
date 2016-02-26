package de.hdm.drools.anmeldung;

import java.net.URI;

import javax.ws.rs.container.AsyncResponse;

/**
 * Nachricht, die die Anmeldung eines Zugs beschreibt. Generalisiert {@link Anmeldung}
 * @author Andreas Herrmann
 */
public class ZugAnmeldung extends Anmeldung {
	
	/**
	 * Konstruktor, der auch die {@link javax.ws.rs.container.AsyncResponse} und die Adresse setzt
	 * @param asyncResponse Die {@link javax.ws.rs.container.AsyncResponse} zum Beantworten der Anfrage
	 * @param adresse Die Adresse des Zugs
	 */
	public ZugAnmeldung(AsyncResponse asyncResponse, URI adresse) {
		this.adresse=adresse;
		this.asyncResponse=asyncResponse;
	}

}
