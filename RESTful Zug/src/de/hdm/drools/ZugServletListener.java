package de.hdm.drools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ZugServletListener implements ServletContextListener {
	private static ZugThread zugThread;
	
	/**
	 * Wird beim Beenden des Servlets automatisch aufgerufen. Hält den
	 * ZugThread an und löscht die Referenz auf diesen,
	 * sodass er von der Garbage Collection eingesammelt werden.
	 * @param sce Das ServletContextEvent, dass zum Aufrufen der Methode geführt hat
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		zugThread.interrupt();
		zugThread=null;

	}

	/**
	 * Wird beim Start des Servlets automatisch aufgerufen.
	 * Erstellt einen neuen ZugThread und startet diesen.
	 * @param sce Das ServletContextEvent, dass zum Aufrufen der Methode geführt hat
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		zugThread = new ZugThread();
		zugThread.start();
		System.out.println("ZugThread gestartet");
	}
}