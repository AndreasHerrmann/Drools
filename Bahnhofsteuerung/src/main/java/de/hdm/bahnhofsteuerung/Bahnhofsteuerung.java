package de.hdm.bahnhofsteuerung;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
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
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;

import de.hdm.bahnhofsteuerung.GUI.BahnhofGUI;
import de.hdm.bahnhofsteuerung.bahnhof.Bahnhof;
import de.hdm.bahnhofsteuerung.bahnhof.Fahrplan;
import de.hdm.bahnhofsteuerung.bahnhof.Zug;

/**
 * Steuert den Ablauf des Programms und enthält die main-Funktion.
 * @author Andreas Herrmann
 *
 */
@SuppressWarnings("restriction")
public class Bahnhofsteuerung {
	private static KieSession kSession;
 
	public static void main(String[] args) {
		final BahnhofGUI gui;
		
		//Kie KnowledgeBase laden 
				
		try {
			KieServices kService = KieServices.Factory.get();
			KieFileSystem kfs = kService.newKieFileSystem();
			kfs.write(kService.getResources().newClassPathResource("Bahnhofsteuerung.drl"));
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
			KieSessionConfiguration conf = KieServices.Factory.get().newKieSessionConfiguration();
			conf.setOption( ClockTypeOption.get( "pseudo" ) );
			kSession = kBase.newKieSession(conf,null);
			
			//Bahnhöfe und Gleise erstellen
			Vector<Bahnhof> bahnhoefe = new Vector<Bahnhof>();
			Bahnhof boeblingen = new Bahnhof("Boeblingen",2);
			bahnhoefe.add(boeblingen);
			Bahnhof herrenberg = new Bahnhof("Herrenberg",2);
			bahnhoefe.add(herrenberg);
			Bahnhof stuttgart = new Bahnhof("Stuttgart",4);
			bahnhoefe.add(stuttgart);
			Bahnhof leonberg = new Bahnhof("Leonberg",2);
			bahnhoefe.add(leonberg);
			Bahnhof plochingen = new Bahnhof("Plochingen",2);
			bahnhoefe.add(plochingen);
			Bahnhof flughafen = new Bahnhof("Flughafen",2);
			bahnhoefe.add(flughafen);
			Bahnhof tuebingen = new Bahnhof("Tuebingen",3);
			bahnhoefe.add(tuebingen);
			
			//Fahrplan für Zug1 erstellen
			Vector<Fahrplan> fahrplanZug1 = new Vector<Fahrplan>();
			fahrplanZug1.add(new Fahrplan(boeblingen,boeblingen.getGleise().get(0),1,20));
			fahrplanZug1.add(new Fahrplan(stuttgart,stuttgart.getGleise().get(0),1,5));
			fahrplanZug1.add(new Fahrplan(plochingen,plochingen.getGleise().get(0),1,15));
			fahrplanZug1.add(new Fahrplan(flughafen,flughafen.getGleise().get(0),1,20));
			fahrplanZug1.add(new Fahrplan(tuebingen,tuebingen.getGleise().get(1),1,10));
			fahrplanZug1.add(new Fahrplan(herrenberg,herrenberg.getGleise().get(0),5,5));
			
			//Fahrplan für Zug2 erstellen
			Vector<Fahrplan> fahrplanZug2 = new Vector<Fahrplan>();
			fahrplanZug2.add(new Fahrplan(herrenberg,herrenberg.getGleise().get(1),1,10));
			fahrplanZug2.add(new Fahrplan(tuebingen,tuebingen.getGleise().get(0),1,20));
			fahrplanZug2.add(new Fahrplan(flughafen,flughafen.getGleise().get(1),1,15));
			fahrplanZug2.add(new Fahrplan(plochingen,plochingen.getGleise().get(1),1,5));
			fahrplanZug2.add(new Fahrplan(stuttgart,stuttgart.getGleise().get(1),1,20));
			fahrplanZug2.add(new Fahrplan(boeblingen,boeblingen.getGleise().get(1),5,5));
			
			//Fahrplan für Zug3 erstellen (Schnellzug von Herrenberg zum Flughafen und zurueck)
			Vector<Fahrplan> fahrplanZug3 = new Vector<Fahrplan>();
			fahrplanZug3.add(new Fahrplan(herrenberg,herrenberg.getGleise().get(0),1,9));
			fahrplanZug3.add(new Fahrplan(tuebingen,tuebingen.getGleise().get(2),0,18));
			fahrplanZug3.add(new Fahrplan(flughafen,flughafen.getGleise().get(0),2,18));
			fahrplanZug3.add(new Fahrplan(tuebingen,tuebingen.getGleise().get(2),0,9));
			
			//Fahrplan für Zug4 erstellen
			Vector<Fahrplan> fahrplanZug4 = new Vector<Fahrplan>();
			fahrplanZug4.add(new Fahrplan(tuebingen,tuebingen.getGleise().get(1),1,20));
			fahrplanZug4.add(new Fahrplan(flughafen,flughafen.getGleise().get(0),1,15));
			fahrplanZug4.add(new Fahrplan(plochingen,plochingen.getGleise().get(1),1,5));
			fahrplanZug4.add(new Fahrplan(stuttgart,stuttgart.getGleise().get(3),1,25));
			
			//GUI erstellen und anzeigen lassen
			gui = new BahnhofGUI(bahnhoefe);
			
			//Zeiteinheitslänge ausgeben
			System.out.println("Die Laenge einer Zeiteinheit ist "
			+Einstellungen.einstellungen().getZeitEinheitLaenge()
			+"ms");
			
			//Züge erstellen
			new Zug(kSession,1,fahrplanZug1,"Herrenberg");
			new Zug(kSession,2,fahrplanZug2,"Boeblingen");
			new Zug(kSession,3,fahrplanZug3,"Flughafen");
			new Zug(kSession,4,fahrplanZug4,"Stuttgart, über Plochingen");
			
			//Referenz auf PseudoClock erhalten
        	final SessionPseudoClock clock = kSession.getSessionClock();
        	//Uhr in eigenem Thread vordrehen bis Programm beendet wird
        	new Thread() {
        		@Override
        		public void run(){
        			System.out.println("Uhr gestartet");
        			while(!isInterrupted()){
                		//Jeweils um eine Minute pro Zeiteinheit vordrehen
        				try {
							sleep(Einstellungen.einstellungen().getZeitEinheitLaenge());
							Einstellungen.einstellungen().vergangeneZeiteinheitenErhoehen();
							gui.vergangeneZeiteinheitenAktualisieren(Einstellungen.einstellungen().getVergangeneZeiteinheiten());
						}
        				catch (InterruptedException e) {
							e.printStackTrace();
						}
        				clock.advanceTime(1, TimeUnit.MINUTES);
                	}
        		}
        	}.start();
        	
			//KieSession in neuem Thread starten
			new Thread() {
				 
		        @Override
		        public void run() {
		        	kSession.fireUntilHalt();
		        }
		    }.start();
			System.out.println("Drools gestartet");
			
			} catch(Throwable t){
				t.printStackTrace();
				}
	}
}