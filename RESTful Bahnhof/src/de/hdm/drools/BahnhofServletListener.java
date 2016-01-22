package de.hdm.drools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class BahnhofServletListener implements ServletContextListener{
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
	 * Wird beim Start des Servlets automatisch aufgerufen.
	 * Erstellt einen neuen KnowledgeBaseThread und startet diesen.
	 * @param sce Das ServletContextEvent, dass zum Aufrufen der Methode geführt hat
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		kieSessionThread = new KnowledgeBaseThread();
		kieSessionThread.start();
		System.out.println("KieSession gestartet");
	}
}