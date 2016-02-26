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

import de.hdm.drools.abmeldung.BahnhofAbmeldung;
import de.hdm.drools.abmeldung.KnotenpunktAbmeldung;
import de.hdm.drools.abmeldung.ZugAbmeldung;
import de.hdm.drools.anmeldung.BahnhofAnmeldung;
import de.hdm.drools.anmeldung.KnotenpunktAnmeldung;
import de.hdm.drools.anmeldung.ZugAnmeldung;
import de.hdm.drools.nachricht.FahrtAbschluss;
import de.hdm.drools.nachricht.FahrtAnfrageMitAsyncResponse;
import de.hdm.drools.nachricht.FahrtBeginnMitAsyncResponse;
import de.hdm.drools.nachricht.Lebenszeichen;
import de.hdm.drools.resource.Bahnhof;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

/**
 * Der Thread, in dem die KnowledgeBase läuft. Generalisiert java.lang.Thread.
 * Wird beim Start des Servlets aufgerufen.
 * @author Andreas Herrmann
 * @param kSession Die KieSession, die in dem Thread abläuft
 *
 */
public class KnowledgeBaseThread extends Thread {
	NetzverwaltungsOutput netzverwaltungsoutput;
	private static KieSession kSession=null;
	public KnowledgeBaseThread(NetzverwaltungsOutput netzverwaltungsOutput){
		this.netzverwaltungsoutput=netzverwaltungsOutput;
		KieServices kService = KieServices.Factory.get();
    	KieFileSystem kfs = kService.newKieFileSystem();
    	kfs.write(kService.getResources().newClassPathResource("de/hdm/drools/Rules.drl"));
    	KieBuilder kieBuilder = kService.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if( results.hasMessages(Message.Level.ERROR))
        	{
        	System.out.println( results.getMessages() );
        	netzverwaltungsoutput.println("KieSession konnte nicht gestartet werden!");
            throw new IllegalStateException( "### errors ###" );
        	}

        KieBaseConfiguration config = kService.newKieBaseConfiguration();
		config.setOption(EventProcessingOption.STREAM);
		KieContainer kieContainer = kService.newKieContainer(kService.getRepository().getDefaultReleaseId());
		KieBase kBase = kieContainer.newKieBase(config);
		kSession = kBase.newKieSession();
		netzverwaltungsoutput.println("KieSession aufgebaut");
	}
	//Startet die KieSession
	public void run(){
		try {
			kSession.fireUntilHalt();
		}
		catch(Throwable t){
		    t.printStackTrace();
			}
		finally{
			kSession.halt();
			netzverwaltungsoutput.println("KieSession angehalten");
			kSession.dispose();
			netzverwaltungsoutput.println("KieSession Resourcen freigegeben");
		}
    }
	public void addBahnhof(Bahnhof bahnhof){
		kSession.insert(bahnhof);
	}
	public void addKnotenpunkt(Knotenpunkt knotenpunkt){
		kSession.insert(knotenpunkt);
	}
	public void addStrecke(Strecke strecke){
		kSession.insert(strecke);
	}
	public void addZug(Zug zug){
		kSession.insert(zug);
	}
	public void bahnhofAnmelden(BahnhofAnmeldung bahnhofAnmeldung){
		kSession.insert(bahnhofAnmeldung);
	}
	public void bahnhofAbmelden(BahnhofAbmeldung bahnhofAbmeldung){
		kSession.insert(bahnhofAbmeldung);
	}
	public void knotenpunktAnmelden(KnotenpunktAnmeldung knotenpunktAnmeldung){
		kSession.insert(knotenpunktAnmeldung);
	}
	public void knotenpunktAbmelden(KnotenpunktAbmeldung knotenpunktAbmeldung){
		kSession.insert(knotenpunktAbmeldung);
	}
	public void zugAnmelden(ZugAnmeldung zugAnmeldung){
		kSession.insert(zugAnmeldung);
	}
	public void zugAbmelden(ZugAbmeldung zugAbmeldung){
		kSession.insert(zugAbmeldung);
	}
	public void fahrtAnfragen(FahrtAnfrageMitAsyncResponse fahrtanfrage){
		kSession.insert(fahrtanfrage);
	}
	public void fahrtBeginn(FahrtBeginnMitAsyncResponse fahrtbeginn){
		kSession.insert(fahrtbeginn);
	}
	public void fahrtAbschluss(FahrtAbschluss fahrtabschluss){
		kSession.insert(fahrtabschluss);
		netzverwaltungsoutput.println("FahrtAbschluss: Strecken-ID:"
		+fahrtabschluss.getStrecke().getId()+" Zug-ID:"
				+fahrtabschluss.getZug().getId());
	}
	public void lebenszeichen(Lebenszeichen lebenszeichen){
		kSession.insert(lebenszeichen);
		netzverwaltungsoutput.println("Lebenszeichen: Strecken-iD:"
		+lebenszeichen.getStrecke().getId()+" Zug-ID:"
				+lebenszeichen.getZug().getId());
	}
}
