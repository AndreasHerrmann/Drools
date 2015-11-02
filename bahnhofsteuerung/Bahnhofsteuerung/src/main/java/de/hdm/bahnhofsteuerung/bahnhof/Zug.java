package de.hdm.bahnhofsteuerung.bahnhof;

import java.util.Vector;

import org.kie.api.runtime.KieSession;

import de.hdm.bahnhofsteuerung.Einstellungen;
import de.hdm.bahnhofsteuerung.events.Zugabfahrt;
import de.hdm.bahnhofsteuerung.events.Zugdurchfahrt;
import de.hdm.bahnhofsteuerung.events.Zugeinfahrt;

public class Zug extends Thread {
	private int nummer; //Nummer des Zuges
	private Vector<Weg> weg;
	private KieSession kSession; //Session in die die Ereignisse eingefügt werden sollen
	
	public Zug (KieSession kSession, int nummer, Vector<Weg> weg){
		this.kSession=kSession;
		this.nummer=nummer;
		this.weg=weg;
		System.out.println("Zug "+nummer+" gestartet");
		start();
	}
	public void run(){
		Einstellungen einst = Einstellungen.einstellungen();
		//Der Thread soll solange laufen, bis er mit interrupt() angehalten wird
		int i=0;
		while(!isInterrupted()&& i<weg.size()){
			if(weg.get(i).getAufenthalt()>0){
				kSession.insert(new Zugeinfahrt(this,weg.get(i).getGleis(),weg.lastElement().getBahnhof().getName()));
				try {
					sleep((weg.get(i).getAufenthalt()*einst.getZeitEinheitLaenge()));
					kSession.insert(new Zugabfahrt(this,weg.get(i).getGleis(),weg.lastElement().getBahnhof().getName()));
					sleep((weg.get(i).getFahrzeit()*einst.getZeitEinheitLaenge()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else{
				kSession.insert(new Zugdurchfahrt(this,weg.get(i).getGleis()));
			}
			if(i>=weg.size()-1){
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
	public Vector<Weg> getWeg() {
		return weg;
	}
	public String getZiel(){
		return this.weg.lastElement().getBahnhof().getName();
	}
}
