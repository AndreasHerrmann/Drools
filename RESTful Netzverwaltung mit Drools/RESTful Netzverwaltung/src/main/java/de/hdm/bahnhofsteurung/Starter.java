package de.hdm.bahnhofsteurung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Applikationseinstieg, der die Netzverwaltung, die Bahnhöfe und die
 * Züge startet, sodass sie alle auf dem Server laufen.
 * 
 * Die Klasse Starter dient als Einstieg des Spring Bootprogramms und
 * initialisiert die URI-Mapper, den Apache Webserver und startet alle
 * Threads in denen die Netzverwaltung, die Bahnhöfe und die Züge ablaufen.
 * 
 * @author Andreas Herrmann
 *
 */
@SpringBootApplication
public class Starter {
	
	/**
	 * Die Hauptmethode, die das gesamte Programm startet.
	 * Die Main-Methode dient als Einstieg der Spring Applikation und startet
	 * außerdem alle Threads in denen die Netzverwaltung, die Bahnhöfe und die
	 * Züge ablaufen.
	 * @param args Argumente, die dem Programm beim Start mitgegeben wurden
	 */
	public static void main(String[] args) {
		SpringApplication.run(Starter.class, args);

	}

}
