package de.hdm.drools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ZugServletListener implements ServletContextListener {
	private static DerZug derZug;
	
	/**
	 * Wird beim Beenden des Servlets automatisch aufgerufen. Hält den
	 * ZugThread an und löscht die Referenz auf diesen,
	 * sodass er von der Garbage Collection eingesammelt werden.
	 * @param sce Das ServletContextEvent, dass zum Aufrufen der Methode geführt hat
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		derZug.interrupt();
		derZug=null;

	}

	/**
	 * Wird beim Start des Servlets automatisch aufgerufen.
	 * Erstellt einen neuen ZugThread und startet diesen.
	 * @param sce Das ServletContextEvent, dass zum Aufrufen der Methode geführt hat
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		derZug = new DerZug();
		derZug.start();
		System.out.println("ZugThread gestartet");
	}
}