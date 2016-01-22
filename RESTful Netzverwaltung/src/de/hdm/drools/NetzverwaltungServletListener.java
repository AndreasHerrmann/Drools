package de.hdm.drools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletContextListener für die Netzverwaltung. contextInitialized wird beim Start
 * des Servlets automatisch aufgerufen. contextDestroyed wird beim Beenden des Servlets
 * automatisch aufgerufen. Implementiert ServletContextListener.
 * @author Andreas Herrmann
 * @param logger Der Logger, der gestartet wird, wenn dies in der Config-Klasse angegeben ist
 * @param kieSessionThread Der KnowledgeBaseThread, der gestartet bzw. beendet werden soll
 *
 */
public class NetzverwaltungServletListener implements ServletContextListener {
	private static KnowledgeBaseThread kieSessionThread;
	private static Logger logger;
	
	/**
	 * Wird beim Beenden des Servlets automatisch aufgerufen. Hält den
	 * KnowledgeBaseThread an und löscht die Referenz auf ihn,
	 * sodass er von der Garbage Collection
	 * eingesammelt wird.
	 * Hält ggf. den Logger an und löscht die Referenz auf ihn.
	 * @param sce Das ServletContextEvent, dass zum Aufrufen der Methode geführt hat
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		kieSessionThread.interrupt();
		kieSessionThread=null;
		if(logger!=null){
			logger.interrupt();
			logger=null;
		}

	}

	/**
	 * Wird beim Start des Servlets automatisch aufgerufen. Startet ggf. den Logger,
	 * ruft den ContextBuilder auf,
	 * erstellt einen neuen KnowledgeBaseThread und startet diesen.
	 * @param sce Das ServletContextEvent, dass zum Aufrufen der Methode geführt hat
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if(Config.logEvents){
			logger = new Logger();
			logger.start();	
		}
		new ContextBuilder();
		kieSessionThread = new KnowledgeBaseThread();
		kieSessionThread.start();

	}
}