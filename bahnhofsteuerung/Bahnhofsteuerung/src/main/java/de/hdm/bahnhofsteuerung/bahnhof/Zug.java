package de.hdm.bahnhofsteuerung.bahnhof;

import org.kie.api.runtime.KieSession;

import de.hdm.bahnhofsteuerung.Einstellungen;
import de.hdm.bahnhofsteuerung.events.Zugabfahrt;
import de.hdm.bahnhofsteuerung.events.Zugeinfahrt;

public class Zug extends Thread {
	private int nummer; //Nummer des Zuges
	private Gleis gleis; //Gleis auf dem er einfahren soll
	private final int intervall; //Intervall in dem er fahren soll in Zeiteinheiten
	private final int aufenthalt; //Länge des Aufenthalts im Bahnhof in Zeiteinheiten
	private final String ziel; //Ziel das auf der Anzeige angezeigt werden soll
	private KieSession kSession; //Session in die die Ereignisse eingefügt werden sollen
	
	public Zug (KieSession kSession, int nummer, Gleis gleis, int intervall, int aufenthalt, String ziel){
		this.kSession=kSession;
		this.nummer=nummer;
		this.gleis=gleis;
		this.intervall=intervall;
		this.aufenthalt=aufenthalt;
		this.ziel=ziel;
		start();
	}
	public void run(){
		Einstellungen einst = new Einstellungen();
		//Der Thread soll solange laufen, bis er mit interrupt() angehalten wird
		while(!isInterrupted()){
			try{
				sleep(intervall*einst.getZeitEinheitLaenge());
				kSession.insert(new Zugeinfahrt(this,gleis,ziel));
				sleep(aufenthalt*einst.getZeitEinheitLaenge());
				kSession.insert(new Zugabfahrt(this,gleis));
			}
			catch(InterruptedException ie){
				ie.printStackTrace();
			}
			catch(IllegalArgumentException iae){
				iae.printStackTrace();
			}
			finally{
				this.nummer=0;
			}
		}
	}

	public int getNummer() {
		return nummer;
	}
}
