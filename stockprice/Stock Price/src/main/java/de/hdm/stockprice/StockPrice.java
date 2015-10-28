package de.hdm.stockprice;

import java.util.Date;
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

@SuppressWarnings("restriction")
public class StockPrice {
	private static KieSession kSession;
	
	public static void main(String[] args) {
		Stock ibm = new Stock("International Business Machines","ibm");
		CSVReader ibmCSVReader = new CSVReader(ibm,"/home/user/Downloads/ibm.csv");
		final Vector<StockEvent> ibmStockEvents = ibmCSVReader.createStockEvents();
		Stock msft = new Stock("Microsoft","msft");
		CSVReader msftCSVReader = new CSVReader(msft,"/home/user/Downloads/microsoft.csv");
		final Vector<StockEvent> msftStockEvents = msftCSVReader.createStockEvents();
		
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
			KieSessionConfiguration conf = KieServices.Factory.get().newKieSessionConfiguration();
			conf.setOption( ClockTypeOption.get( "pseudo" ) );
			kSession = kBase.newKieSession(conf,null);
			final SessionPseudoClock clock = kSession.getSessionClock();
			//KieSession in neuem Thread starten
			new Thread() {
				 
		        @Override
		        public void run() {
		            kSession.fireUntilHalt();
		        }
		    }.start();
		    
		    //Aktiendaten in neuem Thread einfügen
		    new Thread(){
		    	@Override
		    	public void run(){
		    		//Solange einfügen, bis keine mehr da sind
		    		for(int i=1; (i<ibmStockEvents.size())&&(i<msftStockEvents.size());i++){
		    			System.out.println(new Date(clock.getCurrentTime()));
		    			//IBM Stock Events einfügen
		    			kSession.insert(ibmStockEvents.get(i));
		    			//Microsoft Stock Events einfügen
		    			kSession.insert(msftStockEvents.get(i));
		    			//Uhr vordrehen
		    			clock.advanceTime(1, TimeUnit.DAYS);
		    			try {
							sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}
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
