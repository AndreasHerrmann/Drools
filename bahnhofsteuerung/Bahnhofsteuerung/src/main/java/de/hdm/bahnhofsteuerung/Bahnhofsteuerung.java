package de.hdm.bahnhofsteuerung;

import java.util.Vector;

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

import de.hdm.bahnhofsteuerung.GUI.BahnhofGUI;
import de.hdm.bahnhofsteuerung.bahnhof.Bahnhof;
import de.hdm.bahnhofsteuerung.bahnhof.Weg;
import de.hdm.bahnhofsteuerung.bahnhof.Zug;

public class Bahnhofsteuerung {
 private static KieSession kSession;
 
	public static void main(String[] args) {
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
			kSession = kBase.newKieSession();
			
			//Bahnhöfe und Gleise erstellen
			Vector<Bahnhof> bahnhoefe = new Vector<Bahnhof>();
			Bahnhof boeblingen = new Bahnhof("Boeblingen",2);
			bahnhoefe.add(boeblingen);
			Bahnhof herrenberg = new Bahnhof("Herrenberg",2);
			bahnhoefe.add(herrenberg);
			Bahnhof maichingen = new Bahnhof("Maichingen",2);
			bahnhoefe.add(maichingen);
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
			
			//Weg für Zug1 erstellen
			Vector<Weg> wegZug1 = new Vector<Weg>();
			wegZug1.add(new Weg(boeblingen,boeblingen.getGleise().get(0),1,20));
			wegZug1.add(new Weg(stuttgart,stuttgart.getGleise().get(0),1,5));
			wegZug1.add(new Weg(plochingen,plochingen.getGleise().get(0),1,15));
			wegZug1.add(new Weg(flughafen,flughafen.getGleise().get(0),1,20));
			wegZug1.add(new Weg(tuebingen,tuebingen.getGleise().get(1),1,10));
			wegZug1.add(new Weg(herrenberg,herrenberg.getGleise().get(0),5,5));
			
			new BahnhofGUI(bahnhoefe);
			//Zeiteinheitslänge ausgeben
			System.out.println("Die Laenge einer Zeiteinheit ist "
			+Einstellungen.einstellungen().getZeitEinheitLaenge()
			+"ms");
			
			//Züge erstellen und einfügen
			new Zug(kSession,1,wegZug1);
			
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