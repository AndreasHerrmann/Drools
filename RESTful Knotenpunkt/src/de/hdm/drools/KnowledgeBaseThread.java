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
 * Der Thread, in dem die KnowledgeBase läuft. Generalisiert {@link java.lang.Thread}.
 * @author Andreas Herrmann
 * @param kSession Die {@link org.kie.api.runtime.KieSession}, die in dem Thread läuft
 *
 */
public class KnowledgeBaseThread extends Thread {
	/**
	 * Die {@link org.kie.api.runtime.KieSession}, die in dem Thread läuft und
	 * alle Entscheidungen des Bahnhofs trifft. 
	 */
	private static KieSession kSession=null;
	
	/**
	 * Wird beim Start des Threads aufgerufen. Erstellt die {@link org.kie.api.runtime.KieSession}
	 * und lässt diese feuern bis der Thread angehalten wird.
	 * Wenn der Thread angehalten wird, dann beendet er die {@link org.kie.api.runtime.KieSession}
	 * und gibt deren Resourcen frei.
	 */
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
	/**
	 * Statische Methode zum einfügen einer {@link de.hdm.drools.nachricht.AuffahrtAnfrageMitAsyncResponse}
	 * in die {@link org.kie.api.runtime.KieSession} des Threads.
	 * @param auffahrtAnfrageMitAsyncResponse Die {@link de.hdm.drools.nachricht.EinfahrtMitAsyncResponse}, die eingefügt werden soll
	 */
	public static void auffahrtAnfragen(AuffahrtAnfrageMitAsyncResponse auffahrtAnfrageMitAsyncResponse){
		kSession.insert(auffahrtAnfrageMitAsyncResponse);
	}
	
	/**
	 * Statische Methode zum einfügen einer {@link de.hdm.drools.nachricht.AuffahrtMeldungMitAsyncResponse}
	 * in die {@link org.kie.api.runtime.KieSession} des Threads.
	 * @param auffahrtMeldungMitAsyncResponse Die {@link de.hdm.drools.nachricht.AuffahrtMeldungMitAsyncResponse}, die eingefügt werden soll
	 */
	public static void auffahrtMelden(AuffahrtMeldungMitAsyncResponse auffahrtMeldungMitAsyncResponse){
		kSession.insert(auffahrtMeldungMitAsyncResponse);
	}
	
	/**
	 * Statische Methode zum einfügen einer {@link de.hdm.drools.nachricht.Abfahrt}
	 * in die {@link org.kie.api.runtime.KieSession} des Threads.
	 * @param abfahrt Die {@link de.hdm.drools.nachricht.Abfahrt}, die eingefügt werden soll
	 */
	public static void abfahrtMelden(Abfahrt abfahrt){
		kSession.insert(abfahrt);
	}
}		