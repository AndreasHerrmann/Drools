package de.hdm.stockprice;

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

public class StockPrice {
	private static KieSession kSession;
	
	public static void main(String[] args) {
		Stock ibm = new Stock("International Business Machines","ibm");
		CSVReader ibmCSVReader = new CSVReader(ibm,"/home/user/Downloads/ibm.csv");
		Vector<StockEvent> ibmStockEvents = ibmCSVReader.createStockEvents();
		Stock msft = new Stock("Microsoft","msft");
		CSVReader msftCSVReader = new CSVReader(msft,"/home/user/Downloads/microsoft.csv");
		Vector<StockEvent> msftStockEvents = msftCSVReader.createStockEvents();
		
		//Kie KnowledgeBase laden 		
		try {
			KieServices kService = KieServices.Factory.get();
			KieFileSystem kfs = kService.newKieFileSystem();
			    	
			kfs.write(kService.getResources().newClassPathResource("StockPrice.drl"));
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
			//IBM Stock Events einfügen
			for(int i=1; i<ibmStockEvents.size();i++){
				kSession.insert(ibmStockEvents.get(i));
			}
			//Microsoft Stock Events einfügen
			for(int i=1;i<msftStockEvents.size();i++){
				kSession.insert(msftStockEvents.get(i));
			}
			//KieSession in neuem Thread starten
			new Thread() {
				 
		        @Override
		        public void run() {
		            kSession.fireUntilHalt();
		        }
		    }.start();
		}
		catch(IllegalStateException ise){
			ise.printStackTrace();
		}
		finally{
			
		}
	}

}
