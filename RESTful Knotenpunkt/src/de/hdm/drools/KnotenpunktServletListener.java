package de.hdm.drools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class KnotenpunktServletListener implements ServletContextListener {
	private static KnowledgeBaseThread kieSessionThread;
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		KnotenpunktController.abmelden();
		kieSessionThread.interrupt();
		kieSessionThread=null;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		kieSessionThread = new KnowledgeBaseThread();
		kieSessionThread.start();
		System.out.println("KieSession gestartet");
		KnotenpunktController.anmelden();
	}

}
