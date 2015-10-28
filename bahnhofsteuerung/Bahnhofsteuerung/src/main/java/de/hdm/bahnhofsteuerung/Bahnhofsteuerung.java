package de.hdm.bahnhofsteuerung;

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

import de.hdm.bahnhofsteuerung.bahnhof.Bahnhof;
import de.hdm.bahnhofsteuerung.bahnhof.Gleis;
import de.hdm.bahnhofsteuerung.bahnhof.Zug;
import de.hdm.bahnhofsteuerung.events.Zugabfahrt;
import de.hdm.bahnhofsteuerung.events.Zugdurchfahrt;
import de.hdm.bahnhofsteuerung.events.Zugeinfahrt;

@SuppressWarnings("restriction")
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
					
			KieSessionConfiguration conf = kService.newKieSessionConfiguration();
			conf.setOption(ClockTypeOption.get("pseudo"));
			KieContainer kieContainer = kService.newKieContainer(kService.getRepository().getDefaultReleaseId() );
			KieBase kBase = kieContainer.newKieBase(config);
			kSession = kBase.newKieSession(conf,null);
			SessionPseudoClock clock = kSession.getSessionClock();
			
			//Bahnhof und Gleise erstellen
			Bahnhof einBahnhof = new Bahnhof(2);
			Gleis gleis1 = einBahnhof.getGleise().get(0);
			Gleis gleis2 = einBahnhof.getGleise().get(1);
			kSession.insert(gleis1);
			kSession.insert(gleis2);
			
			//Züge erstellen
			Zug zug1 = new Zug(1);
			Zug zug2 = new Zug(2);
			Zug zug3 = new Zug(3);
			Zug zug4 = new Zug(4);
			
			//KieSession in neuem Thread starten
			new Thread() {
				 
		        @Override
		        public void run() {
		            kSession.fireUntilHalt();
		        }
		    }.start();
		    
			//Zugeinfahrten erstellen
			Zugeinfahrt zugeinfahrt1 = new Zugeinfahrt(zug2,gleis2,"Herrenberg","1m");
			kSession.insert(zugeinfahrt1);
			clock.advanceTime(1,TimeUnit.MINUTES);
			Zugabfahrt zugabfahrt1 = new Zugabfahrt(zug2,gleis2);
			kSession.insert(zugabfahrt1);
			clock.advanceTime(2, TimeUnit.MINUTES);
			Zugeinfahrt zugeinfahrt2 = new Zugeinfahrt(zug1,gleis1,"Stuttgart","1m");
			kSession.insert(zugeinfahrt2);
			Zugabfahrt zugabfahrt2 = new Zugabfahrt(zug1,gleis1);
			kSession.insert(zugabfahrt2);
			clock.advanceTime(27, TimeUnit.MINUTES);
			Zugdurchfahrt zugdurchfahrt = new Zugdurchfahrt(zug4);
			kSession.insert(zugdurchfahrt);
			clock.advanceTime(2, TimeUnit.MINUTES);
			Zugeinfahrt zugeinfahrt3 = new Zugeinfahrt(zug3,gleis2,"Woauchimmer","1m");
			kSession.insert(zugeinfahrt3);
			clock.advanceTime(5, TimeUnit.MINUTES);
			
			
			} catch(Throwable t){
				t.printStackTrace();
				}
		finally
			{
			//if (kSession != null) {
				//kSession.dispose();
			    //}
			}
	}
}