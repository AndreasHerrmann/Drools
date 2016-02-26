package de.hdm.drools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletContextListener für die Netzverwaltung. contextInitialized wird beim Start
 * des Servlets automatisch aufgerufen. contextDestroyed wird beim Beenden des Servlets
 * automatisch aufgerufen. Implementiert ServletContextListener.
 * @author Andreas Herrmann
 *
 */
public class NetzverwaltungServletListener implements ServletContextListener {
	private static Netzverwaltung netzverwaltung;
	/**
	 * Wird beim Beenden des Servlets automatisch aufgerufen. Hält den
	 * KnowledgeBaseThread an und löscht die Referenz auf ihn,
	 * sodass er von der Garbage Collection
	 * eingesammelt wird.
	 * Hält ggf. den Logger an und löscht die Referenz auf ihn.
	 * @param sce Das ServletContextEvent, dass zum Aufrufen der Methode geführt hat
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce)
		{
			netzverwaltung.stoppen();
		}

	/**
	 * Wird beim Start des Servlets automatisch aufgerufen. Startet ggf. den Logger,
	 * ruft den ContextBuilder auf,
	 * erstellt einen neuen KnowledgeBaseThread und startet diesen.
	 * @param sce Das ServletContextEvent, dass zum Aufrufen der Methode geführt hat
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		netzverwaltung=new Netzverwaltung();
		new ContextBuilder(netzverwaltung);
	}
}