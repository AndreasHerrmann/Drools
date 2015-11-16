package de.hdm.bahnhofsteurung.bahnhof;

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
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;

import de.hdm.bahnhofsteurung.resource.ResourceBahnhof;

public class OrgaBahnhof extends Thread {
	private ResourceBahnhof resourceBahnhof;
	private Vector<OrgaGleis> gleise;
	private KieSession kSession;
	
	public OrgaBahnhof(ResourceBahnhof resourceBahnhof, int anzahlGleise){
		this.resourceBahnhof=resourceBahnhof;
		gleise=new Vector<OrgaGleis>();
		for(int i=0; i<anzahlGleise; i++){
			this.gleise.add(new OrgaGleis(resourceBahnhof,i));
		}
		
		//Kie KnowledgeBase laden 
		try {
			KieServices kService = KieServices.Factory.get();
			KieFileSystem kfs = kService.newKieFileSystem();
			kfs.write(kService.getResources().newClassPathResource("Bahnhof.drl"));
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
		}
		catch(Throwable t){
			t.printStackTrace();
			}
		this.start();
	}
	
	@Override
	public void run(){
		kSession.insert(gleise);
	}
}
