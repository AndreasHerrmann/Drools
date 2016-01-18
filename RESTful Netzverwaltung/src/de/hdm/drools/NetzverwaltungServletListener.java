package de.hdm.drools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class NetzverwaltungServletListener implements ServletContextListener {
	private static KnowledgeBaseThread kieSessionThread;
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		kieSessionThread.interrupt();
		kieSessionThread=null;

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		new ContextBuilder();
		kieSessionThread = new KnowledgeBaseThread();
		kieSessionThread.start();
		System.out.println("KieSession gestartet");

	}

}
