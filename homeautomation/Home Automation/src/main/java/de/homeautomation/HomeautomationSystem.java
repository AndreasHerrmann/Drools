package de.homeautomation;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import de.homeautomation.GUI.HomeAutomationGUI;

public class HomeautomationSystem {
	
	public static void main(String[] args) {
		
		
		//Kie KnowledgeBase laden 
		KieSession kSession = null;
		
		try {
	    	KieServices kService = KieServices.Factory.get();
	    	KieFileSystem kfs = kService.newKieFileSystem();
	    	
	    	kfs.write(kService.getResources().newClassPathResource("rules.drl"));
	    	KieBuilder kieBuilder = kService.newKieBuilder(kfs).buildAll();
	        Results results = kieBuilder.getResults();
	        if( results.hasMessages( Message.Level.ERROR ) )
	        	{
	            System.out.println( results.getMessages() );
	            throw new IllegalStateException( "### errors ###" );
	        	}

	     KieContainer kieContainer = kService.newKieContainer(kService.getRepository().getDefaultReleaseId() );
	     KieBase kBase = kieContainer.getKieBase();
	     kSession = kBase.newKieSession();
	     //System aufbauen und die Geräte einfügen lassen
	     SystemBuilder sBuilder =SystemBuilder.buildSystem(kSession);
	     //GUI laden
	     HomeAutomationGUI gui= HomeAutomationGUI.newGUI(kSession);
	     gui.setVisible(true);
    	
	} catch(Throwable t)
		{
	    t.printStackTrace();
		}
	finally
		{
		}
	}
}
