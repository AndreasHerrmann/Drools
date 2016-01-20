package de.hdm.drools;

import org.kie.api.runtime.rule.FactHandle;
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

import de.hdm.drools.nachricht.FahrtAbschluss;
import de.hdm.drools.nachricht.FahrtAnfrageMitAsyncResponse;
import de.hdm.drools.nachricht.FahrtBeginnMitAsyncResponse;
import de.hdm.drools.nachricht.Lebenszeichen;
import de.hdm.drools.resource.Bahnhof;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Zug;

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
			kSession.insert(Netzverwaltung.getBahnhoefe());
			kSession.insert(Netzverwaltung.getKnotenpunkte());
			kSession.insert(Netzverwaltung.getStrecken());
			kSession.insert(Netzverwaltung.getZuege());
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
	public static void bahnhofAnmelden(Bahnhof bahnhof){
		FactHandle facthandle = kSession.getFactHandle(bahnhof);
		kSession.update(facthandle, bahnhof);
	}
	public static void bahnhofAbmelden(Bahnhof bahnhof){
		FactHandle facthandle = kSession.getFactHandle(bahnhof);
		bahnhof.setAdresse(null);
		kSession.update(facthandle, bahnhof);
	}
	public static void knotenpunktAnmelden(Knotenpunkt knotenpunkt){
		FactHandle facthandle = kSession.getFactHandle(knotenpunkt);
		kSession.update(facthandle, knotenpunkt);
	}
	public static void knotenpunktAbmelden(Knotenpunkt knotenpunkt){
		FactHandle facthandle = kSession.getFactHandle(knotenpunkt);
		knotenpunkt.setAdresse(null);
		kSession.update(facthandle, knotenpunkt);
	}
	public static void zugAnmelden(Zug zug){
		FactHandle facthandle= kSession.getFactHandle(zug);
		kSession.update(facthandle, zug);
	}
	public static void zugAbmelden(Zug zug){
		FactHandle facthandle = kSession.getFactHandle(zug);
		zug.setAdresse(null);
		kSession.update(facthandle, zug);
	}
	public static void fahrtAnfragen(FahrtAnfrageMitAsyncResponse fahrtanfrage){
		kSession.insert(fahrtanfrage);
	}
	public static void fahrtBeginn(FahrtBeginnMitAsyncResponse fahrtbeginn){
		kSession.insert(fahrtbeginn);
	}
	public static void fahrtAbschluss(FahrtAbschluss fahrtabschluss){
		kSession.insert(fahrtabschluss);
	}
	public static void lebenszeichen(Lebenszeichen lebenszeichen){
		kSession.insert(lebenszeichen);
	}
}
