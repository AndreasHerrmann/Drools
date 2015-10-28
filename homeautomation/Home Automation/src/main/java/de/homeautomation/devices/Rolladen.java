package de.homeautomation.devices;

import de.homeautomation.resources.Ort;

public class Rolladen extends Geraet {
	private Ort ort;
	private int geschlossen; //0= offen, 100=geschlossen
	
	public Rolladen(String name, String macAdresse, Ort ort){
		this.name = ort.toString()+" "+name;
		this.macAdresse = macAdresse;
		this.ort = ort;
		this.connection = Connection.Funk;
		this.status = Status.Aus;
		this.geschlossen = 0;
	}
	
	public void rolladenBewegen (int zielwert){
		if(zielwert<0){
			zielwert=0;
		}
		else if(zielwert>100){
			zielwert=100;
		}
		if(zielwert!=this.geschlossen){
			geschlossen=zielwert;
			System.out.println(this.name+" ist jetzt zu "
			+geschlossen+"% geschlossen");
		}
	}

	public Ort getOrt() {
		return ort;
	}

	public int getGeschlossen() {
		return geschlossen;
	}
}
