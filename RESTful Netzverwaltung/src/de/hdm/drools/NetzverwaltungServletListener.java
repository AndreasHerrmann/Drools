package de.hdm.drools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletContextListener für die Netzverwaltung. contextInitialized wird beim Start
 * des Servlets automatisch aufgerufen. contextDestroyed wird beim Beenden des Servlets
 * automatisch aufgerufen. Implementiert ServletContextListener.
 * @author Andreas Herrmann
 * @param kieSessionThread Der KnowledgeBaseThread, der gestartet bzw. beendet werden soll
 *
 */
public class NetzverwaltungServletListener implements ServletContextListener {
	private static KnowledgeBaseThread kieSessionThread;
	
	/**
	 * Wird beim Beenden des Servlets automatisch aufgerufen. Hält den
	 * KnowledgeBaseThread an und löscht die Referenz auf ihn,
	 * sodass er von der Garbage Collection
	 * eingesammelt wird.
	 * @param sce Das ServletContextEvent, dass zum Aufrufen der Methode geführt hat
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		kieSessionThread.interrupt();
		kieSessionThread=null;

	}

	/**
	 * Wird beim Start des Servlets automatisch aufgerufen. Ruft den ContextBuilder auf,
	 * erstellt einen neuen KnowledgeBaseThread und startet diesen.
	 * @param sce Das ServletContextEvent, dass zum Aufrufen der Methode geführt hat
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		new ContextBuilder();
		kieSessionThread = new KnowledgeBaseThread();
		kieSessionThread.start();
		System.out.println("KieSession gestartet");

	}

}
