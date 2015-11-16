package de.hdm.bahnhofsteurung.netzverwaltung;

import java.util.NoSuchElementException;
import java.util.Vector;

import de.hdm.bahnhofsteurung.resource.ResourceBahnhof;
import de.hdm.bahnhofsteurung.resource.Strecke;
import de.hdm.bahnhofsteurung.resource.Zug;

public class Netzverwaltung extends Thread {
	private static Netzverwaltung netzverwaltung=null;;
	private Vector<ResourceBahnhof> bahnhoefe;
	private Vector<Strecke> strecken;
	private Vector<Zug> zuege;
	
	private Netzverwaltung(){
		this.bahnhoefe=new Vector<ResourceBahnhof>();
		this.strecken=new Vector<Strecke>();
		this.zuege=new Vector<Zug>();
		this.start();
	}
	
	public static Netzverwaltung netzverwaltung(){
		if(netzverwaltung!=null){
			return netzverwaltung;
		}
		else{
			netzverwaltung= new Netzverwaltung();
			return netzverwaltung;
		}
	}
	
	public ResourceBahnhof addBahnhof(ResourceBahnhof resourceBahnhof){
		try{
			resourceBahnhof.setID(bahnhoefe.lastElement().getID()+1);
		}
		/* Wenn es kein letztes Element gibt, dann ist der Vector leer
		 * und der Bahnhof bekommt die iD 0
		 */
		catch(NoSuchElementException nsee){
			resourceBahnhof.setID(0);
		}
		bahnhoefe.add(resourceBahnhof);
		return resourceBahnhof;
	}
	
	public ResourceBahnhof getBahnhof(long iD){
		for(int i=0; i<this.bahnhoefe.size();i++){
			if(bahnhoefe.get(i).equals(iD)){
				return bahnhoefe.get(i);
			}
		}
		return null;
	}
	
	public Vector<ResourceBahnhof> getBahnhoefe() {
		return bahnhoefe;
	}
}
