package de.hdm.drools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Dient zum automatischen Start des {@link KnowledgeBaseThread} und zum Aufruf
 * der Methode {@code anmelden()} des {@link RESTController} beim Start des Servlets
 * und zum automatischen Beenden des {@link KnowledgeBaseThread} und zum Aufruf
 * der Methode {@code abmelden()} des {@link RESTController} beim Beenden des Servlets.
 * Implementiert {@link javax.servlet.ServletContextListener}
 * @author Andreas Herrmann
 * @param kieSessionThread Die Referenz auf den {@link KnowledgeBaseThread} des Servlets
 */
public class KnotenpunktServletListener implements ServletContextListener {
	/**
	 * Die Referenz auf den {@link KnowledgeBaseThread} des Servlets.
	 */
	private DerKnotenpunkt derKnotenpunkt;
	
	/**
	 * Wird beim Beenden des Servlets automatisch aufgerufen. Hält den
	 * {@linkKnowledgeBaseThread} an und löscht die Referenz auf ihn,
	 * sodass er von der Garbage Collection eingesammelt wird.
	 * @param sce Das ServletContextEvent, dass zum Aufrufen der Methode geführt hat
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		derKnotenpunkt.anhalten();
	}
	
	/**
	 * Wird beim Start des Servlets automatisch aufgerufen.
	 * Erstellt einen neuen KnowledgeBaseThread und startet diesen.
	 * Ruft die Methode {@code anmelden()} des {@link RESTController} auf.
	 * @param sce Das ServletContextEvent, dass zum Aufrufen der Methode geführt hat
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		derKnotenpunkt = new DerKnotenpunkt();
	}

}
