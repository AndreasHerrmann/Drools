package de.hdm.bahnhofsteuerung;

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
import de.hdm.bahnhofsteuerung.bahnhof.Bahnhof;
import de.hdm.bahnhofsteuerung.bahnhof.Gleis;
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
			
			//Bahnhof und Gleise erstellen
			Bahnhof einBahnhof = new Bahnhof(2);
			Gleis gleis1 = einBahnhof.getGleise().get(0);
			Gleis gleis2 = einBahnhof.getGleise().get(1);
			kSession.insert(gleis1);
			kSession.insert(gleis2);
			
			//Züge erstellen
			new Zug(kSession,1,gleis1,60,30,"Herrenberg");
			new Zug(kSession,2,gleis2,60,30,"Stuttgart");
			System.out.println("Züge gestartet");
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