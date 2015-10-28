package de.homeautomation;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import de.homeautomation.devices.*;
import de.homeautomation.devices.GameConsole.Typ;
import de.homeautomation.devices.Geraet.Connection;
import de.homeautomation.resources.Ort;

public class HomeautomationSystem {
	public static void main(String[] args) {
		Ambilight ambi = new Ambilight("AmbilightMac");
		Fenster schlafFenster = new Fenster("Fenster",Ort.Schlafzimmer);
		Fenster schlafBalkonTuer = new Fenster("Balkontuer",Ort.Schlafzimmer);
		Fenster wohnFenster = new Fenster("Fenster",Ort.Wohnzimmer);
		Fenster wohnBalkonTuer = new Fenster("Balkontuer",Ort.Wohnzimmer);
		GameConsole wiiU = new GameConsole("WiiU","WiiUMAC",Connection.WiFi,Typ.WiiU);
		GameConsole xbox = new GameConsole("XBOX One","XboxMAC",Connection.Ethernet,Typ.XboxOne);
		Heizung badHeizung = new Heizung("BadHeizungMAC",Ort.Bad);
		Heizung schlafHeizung = new Heizung("SchlafzimmerHeizungMac",Ort.Schlafzimmer);
		Heizung wohnHeizung = new Heizung("WohnzimmerHeizungMAC",Ort.Wohnzimmer);
		HTPC mediaCenter = new HTPC("KodiMediaCenter","HTPCMAC",Connection.Ethernet);
		Licht badSpiegel = new Licht("Spiegellicht","SpiegellichtMAC",Ort.Bad);
		Licht badDecke = new Licht("Deckenlicht","BadDeckenlichtMAC",Ort.Bad);
		Licht schlafDecke = new Licht("Deckenlicht","SchlafzimmerDeckenlichtMAC",Ort.Schlafzimmer);
		Licht schlafNachttisch = new Licht("Nachttischlampe","SchlafzimmerNachttischlampeMAC",Ort.Schlafzimmer);
		Licht schlafStehlampe = new Licht("Stehlampe","SChlafzimmerStehlampeMAC",Ort.Schlafzimmer);
		Licht wohnDecke = new Licht("Deckenlicht","WohnzimmerDeckenlichtMAC",Ort.Wohnzimmer);
		Licht wohnSofaLicht = new Licht("Sofalicht","WohnzimmerSofalichtMAC",Ort.Wohnzimmer);
		Licht wohnSchreibtischlicht = new Licht("Schreibtischlicht","WohnzimmerSchreibtischlichtMAC",Ort.Wohnzimmer);
		PC arbeitsComputer = new PC("Arbeitsrechner","ArbeitsrechnerMAC",Connection.Ethernet);
		Rolladen schlafFensterRolladen = new Rolladen("Fensterrolladen","SchlafzimmerrolladenMAC",Ort.Schlafzimmer);
		Rolladen schlafBalkontuerRolladen = new Rolladen("Balkontuerrolladen","SchlafzimmerBalkontuerrolladenMAC",Ort.Schlafzimmer);
		Rolladen wohnFensterRolladen = new Rolladen("Fensterrolladen","WohnzimmerRolladenMAC",Ort.Wohnzimmer);
		Rolladen wohnBalkontuerRolladen = new Rolladen("Balkontuerrolladen","WohnzimmerBalkontuerrolladenMAC",Ort.Wohnzimmer);
		Router router = new Router();
		Temperaturfuehler tempBad = new Temperaturfuehler("TempBadMAC",Ort.Bad);
		Temperaturfuehler tempSchlaf = new Temperaturfuehler("TempSchlafMAC",Ort.Schlafzimmer);
		Temperaturfuehler tempWohn = new Temperaturfuehler("TempWohnMAC",Ort.Wohnzimmer);
		TV wohnTV = new TV("Wohnzimmerfernseher","WohnzimmertvMAC",Connection.WiFi);
		
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
	     
	    //Geraete einsetzen
    	kSession.insert(ambi);
    	kSession.insert(schlafFenster);
    	kSession.insert(schlafBalkonTuer);
    	kSession.insert(wohnFenster);
    	kSession.insert(wohnBalkonTuer);
    	kSession.insert(wiiU);
    	kSession.insert(xbox);
    	kSession.insert(badHeizung);
    	kSession.insert(wohnHeizung);
    	kSession.insert(schlafHeizung);
    	kSession.insert(mediaCenter);
    	kSession.insert(badSpiegel);
    	kSession.insert(badDecke);
    	kSession.insert(schlafDecke);
    	kSession.insert(schlafNachttisch);
    	kSession.insert(schlafStehlampe);
    	kSession.insert(wohnDecke);
    	kSession.insert(wohnSofaLicht);
    	kSession.insert(wohnSchreibtischlicht);
    	kSession.insert(arbeitsComputer);
    	kSession.insert(schlafFensterRolladen);
    	kSession.insert(schlafBalkontuerRolladen);
    	kSession.insert(wohnFensterRolladen);
    	kSession.insert(wohnBalkontuerRolladen);
    	kSession.insert(router);
    	kSession.insert(tempBad);
    	kSession.insert(tempSchlaf);
    	kSession.insert(tempWohn);
    	kSession.insert(wohnTV);
    	
    	//Wohnzimmertemperatur ändern
    	kSession.fireAllRules();
    	FactHandle wohnFactHandle = kSession.getFactHandle(tempWohn);
    	tempWohn.setTemperatur(23.0F);
    	kSession.update(wohnFactHandle, tempWohn);
    	kSession.fireAllRules();
    	FactHandle wohnTVFactHandle = kSession.getFactHandle(wohnTV);
    	wohnTV.anschalten();
    	kSession.update(wohnTVFactHandle, wohnTV);
    	kSession.fireAllRules();
    	
	} catch(Throwable t)
		{
	    t.printStackTrace();
		}
	finally
		{
		if (kSession != null) {
			kSession.dispose();
		    }
		}
	}
}
