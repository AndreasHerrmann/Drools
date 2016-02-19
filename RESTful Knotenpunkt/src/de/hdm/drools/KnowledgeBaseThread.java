package de.hdm.drools;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import de.hdm.drools.nachricht.Abfahrt;
import de.hdm.drools.nachricht.AuffahrtAnfrageMitAsyncResponse;
import de.hdm.drools.nachricht.AuffahrtMeldungMitAsyncResponse;

/**
 * Der Thread, in dem die KnowledgeBase läuft. Generalisiert java.lang.Thread.
 * Wird beim Start des Servlets aufgerufen.
 * @author Andreas Herrmann
 * @param kSession Die KieSession, die in dem Thread abläuft
 *
 */
public class KnowledgeBaseThread extends Thread {
	private static KieSession kSession=null;
	//Startet die KieSession
	public void run(){
		try {
			KieServices kService = KieServices.Factory.get();
	    	KieFileSystem kfs = kService.newKieFileSystem();
	    	kfs.write(kService.getResources().newClassPathResource("de/hdm/drools/Rules.drl"));
	    	KieBuilder kieBuilder = kService.newKieBuilder(kfs).buildAll();
	        Results results = kieBuilder.getResults();
	        if( results.hasMessages( Message.Level.ERROR ) )
	        	{
	            System.out.println( results.getMessages() );
	            throw new IllegalStateException( "### errors ###" );
	        	}

	        KieBaseConfiguration config = kService.newKieBaseConfiguration();
			config.setOption( EventProcessingOption.STREAM);
			KieContainer kieContainer = kService.newKieContainer(kService.getRepository().getDefaultReleaseId() );
			KieBase kBase = kieContainer.newKieBase(config);
			kSession = kBase.newKieSession();
			System.out.println("KieSession aufgebaut");
			kSession.fireUntilHalt();
		}
		catch(Throwable t){
		    t.printStackTrace();
			}
		finally{
			kSession.halt();
			System.out.println("KieSession angehalten");
			kSession.dispose();
			System.out.println("KieSession Resourcen freigegeben");
		}
	}
	public static void auffahrtAnfragen(AuffahrtAnfrageMitAsyncResponse auffahrtAnfrageMitAsyncResponse){
		kSession.insert(auffahrtAnfrageMitAsyncResponse);
	}
	public static void auffahrtMelden(AuffahrtMeldungMitAsyncResponse auffahrtMeldungMitAsyncResponse){
		kSession.insert(auffahrtMeldungMitAsyncResponse);
	}
	public static void abfahrtMelden(Abfahrt abfahrt){
		kSession.insert(abfahrt);
	}
}		