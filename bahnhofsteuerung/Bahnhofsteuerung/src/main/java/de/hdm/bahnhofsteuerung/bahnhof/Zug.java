package de.hdm.bahnhofsteuerung.bahnhof;

import java.util.Vector;

import org.kie.api.runtime.KieSession;

import de.hdm.bahnhofsteuerung.Einstellungen;
import de.hdm.bahnhofsteuerung.event.Zugabfahrt;
import de.hdm.bahnhofsteuerung.event.Zugdurchfahrt;
import de.hdm.bahnhofsteuerung.event.Zugeinfahrt;

public class Zug extends Thread {
	private int nummer; //Nummer des Zuges
	private Vector<Fahrplan> fahrplan;
	private String ziel;
	private KieSession kSession; //Session in die die Ereignisse eingefügt werden sollen
	
	public Zug (KieSession kSession, int nummer, Vector<Fahrplan> fahrplan, String ziel){
		this.kSession=kSession;
		this.nummer=nummer;
		this.fahrplan=fahrplan;
		this.ziel=ziel;
		System.out.println("Zug "+nummer+" gestartet");
		start();
	}
	public void run(){
		Einstellungen einst = Einstellungen.einstellungen();
		//Der Thread soll solange laufen, bis er mit interrupt() angehalten wird
		int i=0;
		while(!isInterrupted()&& i<fahrplan.size()){
			if(fahrplan.get(i).getAufenthalt()>0){
				kSession.insert(new Zugeinfahrt(this,fahrplan.get(i).getGleis(),ziel));
				try {
					sleep((fahrplan.get(i).getAufenthalt()*einst.getZeitEinheitLaenge()));
					kSession.insert(new Zugabfahrt(this,fahrplan.get(i).getGleis(),ziel));
					sleep((fahrplan.get(i).getFahrzeit()*einst.getZeitEinheitLaenge()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if(fahrplan.get(i).getAufenthalt()==0){
				kSession.insert(new Zugdurchfahrt(this,fahrplan.get(i).getGleis()));
			}
			
			if(i>=fahrplan.size()-1){
				i=0;
			}
			else{
				i++;
			}
		}
	}

	public int getNummer() {
		return nummer;
	}
	public Vector<Fahrplan> getFahrplan() {
		return fahrplan;
	}
	public String getZiel(){
		return ziel;
	}
}
