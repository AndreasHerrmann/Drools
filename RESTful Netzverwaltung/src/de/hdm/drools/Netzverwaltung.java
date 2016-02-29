package de.hdm.drools;

import java.net.URI;
import java.util.Vector;

import javax.ws.rs.container.AsyncResponse;

import org.glassfish.jersey.server.ChunkedOutput;

import de.hdm.drools.abmeldung.BahnhofAbmeldung;
import de.hdm.drools.abmeldung.KnotenpunktAbmeldung;
import de.hdm.drools.abmeldung.ZugAbmeldung;
import de.hdm.drools.anmeldung.BahnhofAnmeldung;
import de.hdm.drools.anmeldung.KnotenpunktAnmeldung;
import de.hdm.drools.anmeldung.ZugAnmeldung;
import de.hdm.drools.nachricht.FahrtAbschluss;
import de.hdm.drools.nachricht.FahrtAnfrage;
import de.hdm.drools.nachricht.FahrtAnfrageMitAsyncResponse;
import de.hdm.drools.nachricht.FahrtBeginn;
import de.hdm.drools.nachricht.FahrtBeginnMitAsyncResponse;
import de.hdm.drools.nachricht.Lebenszeichen;
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
		RESTController.setNetzverwaltung(this);
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
	public boolean knotenpunktAbmelden(URI adresse, Knotenpunkt knotenpunkt){
		if(adresse!=knotenpunkt.getAdresse()){
			return false;
		}
		else{
			KnotenpunktAbmeldung abmeldung = new KnotenpunktAbmeldung(knotenpunkt);
			kieSessionThread.knotenpunktAbmelden(abmeldung);
			return true;
		}
	}
	public void zugAnmelden(URI adresse, AsyncResponse asyncResponse){
		ZugAnmeldung anmeldung = new ZugAnmeldung(asyncResponse, adresse);
		kieSessionThread.zugAnmelden(anmeldung);
	}
	public boolean zugAbmelden(URI adresse, Zug zug){
		if(adresse!=zug.getAdresse()){
			return false;
		}
		else{
			ZugAbmeldung abmeldung = new ZugAbmeldung(zug);
			kieSessionThread.zugAbmelden(abmeldung);
			return true;
		}
	}
	public void fahrtAnfragen(FahrtAnfrage fahrtanfrage, AsyncResponse asyncResponse){
		FahrtAnfrageMitAsyncResponse fahrtAnfrageMitAsync = new FahrtAnfrageMitAsyncResponse(fahrtanfrage,asyncResponse);
		kieSessionThread.fahrtAnfragen(fahrtAnfrageMitAsync);
	}
	public void fahrtBeginnen(FahrtBeginn fahrtbeginn,AsyncResponse asyncResponse){
		FahrtBeginnMitAsyncResponse fahrtBeginnMitAsync = new FahrtBeginnMitAsyncResponse(fahrtbeginn,asyncResponse);
		kieSessionThread.fahrtBeginn(fahrtBeginnMitAsync);
	}
	public void fahrtAbschliessen(FahrtAbschluss fahrtabschluss){
		kieSessionThread.fahrtAbschluss(fahrtabschluss);
	}
	public void lebenszeichenAbsetzen(Lebenszeichen lebenszeichen){
		kieSessionThread.lebenszeichen(lebenszeichen);
	}
	public Knotenpunkt findeKnotenpunktAnhandID(long id){
		for(int i=0; i<knotenpunkte.size();i++){
			if(knotenpunkte.get(i).getId()==id){
				return knotenpunkte.get(i);
			}
		}
		return null;
	}
	public Bahnhof findeBahnhofAnhandID(long id){
		for(int i=0; i<bahnhoefe.size();i++){
			if(bahnhoefe.get(i).getId()==id){
				return bahnhoefe.get(i);
			}
		}
		return null;
	}
	public Zug findeZugAnhandID(long id){
		for(int i=0; i<zuege.size();i++){
			if(zuege.get(i).getId()==id){
				return zuege.get(i);
			}
		}
		return null;
	}
	public Strecke[] findeStreckenZuKnotenpunkt(Knotenpunkt ziel){
		Vector<Strecke> gefundeneStrecken = new Vector<Strecke>();
		for(int i=0;i<strecken.size();i++){
			if(strecken.get(i).getZiel().equals(ziel)){
				gefundeneStrecken.add(strecken.get(i));
			}
		}
		Strecke[] strecken=new Strecke[gefundeneStrecken.size()];
		return gefundeneStrecken.toArray(strecken);
	}
	public Strecke[] findeStreckenVonKnotenpunkt(Knotenpunkt start){
		Vector<Strecke> gefundeneStrecken = new Vector<Strecke>();
		for(int i=0;i<strecken.size();i++){
			if(strecken.get(i).getStart().equals(start)){
				gefundeneStrecken.add(strecken.get(i));
			}
		}
		Strecke[] strecken=new Strecke[gefundeneStrecken.size()];
		return gefundeneStrecken.toArray(strecken);
	}
	public void beobachten(ChunkedOutput<String> chunkedOutput){
		NetzverwaltungsOutputThread outputThread = new NetzverwaltungsOutputThread(chunkedOutput,netzverwaltungsOutput);
		outputThread.start();
	}
}
