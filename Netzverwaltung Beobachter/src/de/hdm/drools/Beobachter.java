package de.hdm.drools;

import java.awt.event.WindowEvent;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ChunkedInput;

import de.hdm.drools.gui.BeobachterGUI;

public class Beobachter {
	private static BeobachterGUI gui;
	
	public static void main(String[] args) {
		//GUI aufbauen und anzeigen
		gui=new BeobachterGUI();
		
		//Verbindung zur Netzverwaltung herstellen
		Client client = ClientBuilder.newClient();
		WebTarget netzverwaltung = client.target("http://0.0.0.0:8888");
		//Anfrage senden
		final Response response = netzverwaltung.path("netzverwaltung/beobachten")
		        .request().get();
		final ChunkedInput<String> chunkedInput =
		        response.readEntity(new GenericType<ChunkedInput<String>>() {});
		gui.addWindowListener(new java.awt.event.WindowAdapter() {
	        public void windowClosing(WindowEvent we) {
	        	chunkedInput.close();
	        }
	    });
		String chunk;
		while ((chunk = chunkedInput.read()) != null) {
			gui.write(chunk);
		}
	}
}