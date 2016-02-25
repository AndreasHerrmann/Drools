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
import de.hdm.drools.nachricht.EinfahrtMitAsyncResponse;
import de.hdm.drools.resource.Gleis;
import de.hdm.drools.nachricht.EinfahrtAnfrageMitAsyncResponse;

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
			BahnhofController.anmelden();
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
	 * Statische Methode zum Einfügen einer {@link de.hdm.drools.nachricht.EinfahrtAnfrageMitAsyncResponse}
	 * in die {@link org.kie.api.runtime.KieSession} des Threads.
	 * @param fahrtanfrageMitAsync Die {@link de.hdm.drools.nachricht.EinfahrtAnfrageMitAsyncResponse}, die eingefügt werden soll
	 */
	public static void fahrtAnfragen(EinfahrtAnfrageMitAsyncResponse fahrtanfrageMitAsync){
		kSession.insert(fahrtanfrageMitAsync);
	}
	/**
	 * Statische Methode zum Einfügen einer {@link de.hdm.drools.nachricht.EinfahrtMitAsyncResponse}
	 * in die {@link org.kie.api.runtime.KieSession} des Threads.
	 * @param einfahrtMitAsync Die {@link de.hdm.drools.nachricht.EinfahrtMitAsyncResponse}, die eingefügt werden soll
	 */
	public static void einfahrtMelden(EinfahrtMitAsyncResponse einfahrtMitAsync){
		kSession.insert(einfahrtMitAsync);
	}
	/**
	 * Statische Methode zum Einfügen einer {@link de.hdm.drools.nachricht.Abfahrt}
	 * in die {@link org.kie.api.runtime.KieSession} des Threads.
	 * @param abfahrt Die {@link de.hdm.drools.nachricht.Abfahrt}, die eingefügt werden soll
	 */
	public static void abfahrtMelden(Abfahrt abfahrt){
		kSession.insert(abfahrt);
	}
	/**
	 * Statische Methode zum Einfügen eines {@link de.hdm.drools.resource.Gleis}
	 * in die {@link org.kie.api.runtime.KieSession} des Threads.
	 * @param gleis Das {@link e.hdm.drools.resource.Gleis}, das eingefügt werden soll
	 */
	public static void gleisEinfuegen(Gleis gleis){
			kSession.insert(gleis);
	}
}		