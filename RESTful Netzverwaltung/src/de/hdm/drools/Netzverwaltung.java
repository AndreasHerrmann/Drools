package de.hdm.drools;

import java.net.URI;
import java.util.Vector;

import javax.ws.rs.container.AsyncResponse;

import de.hdm.drools.abmeldung.BahnhofAbmeldung;
import de.hdm.drools.anmeldung.BahnhofAnmeldung;
import de.hdm.drools.anmeldung.KnotenpunktAnmeldung;
import de.hdm.drools.resource.Bahnhof;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

public class Netzverwaltung {
	private NetzverwaltungsOutput netzverwaltungsOutput=null;
	private KnowledgeBaseThread kieSessionThread = null;
	private Vector<Knotenpunkt> knotenpunkte = new Vector<Knotenpunkt>();
	private Vector<Bahnhof> bahnhoefe = new Vector<Bahnhof>();
	private Vector<Zug> zuege = new Vector<Zug>();
	private Vector<Strecke> strecken = new Vector<Strecke>();
	
	public Netzverwaltung(){
		if(netzverwaltungsOutput==null){
			netzverwaltungsOutput = new NetzverwaltungsOutput();
			netzverwaltungsOutput.start();
		}
		if(kieSessionThread==null){
			kieSessionThread=new KnowledgeBaseThread(netzverwaltungsOutput);
		}
	}
	public void stoppen(){
		kieSessionThread.interrupt();
		kieSessionThread=null;
		netzverwaltungsOutput.stop();
	}
	public NetzverwaltungsOutput getNetzveraltungsOutput(){
		return netzverwaltungsOutput;
	}
	/**
	 * Fügt dem Vector knotenpunkte einen neuen Knotenpunkt hinzu.
	 * @param knotenpunkt Der Knotenpunkt, der hinzugefügt werden soll
	 */
	public void addKnotenpunkt(Knotenpunkt knotenpunkt){
		knotenpunkte.add(knotenpunkt);
		kieSessionThread.addKnotenpunkt(knotenpunkt);
	}
	/**
	 * Fügt dem Vector bahnhoefe einen neuen Bahnhof hinzu.
	 * @param bahnhof Der Bahnhof, der hinzugefügt werden soll
	 */
	public void addBahnhof(Bahnhof bahnhof){
		bahnhoefe.add(bahnhof);
		kieSessionThread.addBahnhof(bahnhof);
	}
	/**
	 * Fügt dem Vector zuege einen neuen Zug hinzu.
	 * @param zug Der Zug, der hinzugefügt werden soll
	 */
	public void addZug(Zug zug){
		zuege.add(zug);
		kieSessionThread.addZug(zug);
	}
	/**
	 * Fügt dem Vector strecken eine neue Strecke hinzu.
	 * @param strecke Die Strecke, die hinzugefügt werden soll
	 */
	public void addStrecke(Strecke strecke){
		strecken.add(strecke);
		kieSessionThread.addStrecke(strecke);
	}
	public void bahnhofAnmelden(URI adresse, AsyncResponse asyncResponse){
		BahnhofAnmeldung anmeldung = new BahnhofAnmeldung(asyncResponse, adresse);
		kieSessionThread.bahnhofAnmelden(anmeldung);
	}
	public boolean bahnhofAbmelden(URI adresse, Bahnhof bahnhof){
		if(adresse!=bahnhof.getAdresse()){
			return false;
		}
		else{
			BahnhofAbmeldung abmeldung = new BahnhofAbmeldung(bahnhof);
			kieSessionThread.bahnhofAbmelden(abmeldung);
			return true;
		}
	}
	public void knotenpunktAnmelden(URI adresse, AsyncResponse asyncResponse){
		KnotenpunktAnmeldung anmeldung = new KnotenpunktAnmeldung(asyncResponse, adresse);
		kieSessionThread.knotenpunktAnmelden(anmeldung);
	}
}
