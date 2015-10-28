package de.homeautomation.devices;

import de.homeautomation.resources.Ort;

public class Licht extends Geraet {
	private Ort ort;
	private int helligkeit;
	
	public Licht (String name, String macAdresse, Ort ort){
		this.name = ort.toString()+" "+name;
		this.macAdresse = macAdresse;
		this.connection = Connection.Funk;
		this.helligkeit = 0;
		this.status=Status.Aus;
		this.ort = ort;
	}
	
	public void dimmen (int zielwert){
		if(zielwert<0){
			zielwert = 0;
		}
		else if (zielwert>255){
			zielwert=255;
		}
		int dimmstufen;
		if(zielwert<this.helligkeit){
			dimmstufen = this.helligkeit-zielwert;
		}
		else if(zielwert>this.helligkeit){
			dimmstufen=zielwert-this.helligkeit;
		}
		else{
			dimmstufen=0;
		}
		while(dimmstufen>0){
			this.helligkeit--;
			System.out.println("Das Licht "+this.name+" wurde auf"
					+this.helligkeit+" geregelt");
		}
	}

	public int getHelligkeit() {
		return helligkeit;
	}

	public void setHelligkeit(int helligkeit) {
		if(helligkeit<0){
			this.helligkeit = 0;
		}
		else if (helligkeit>255){
			this.helligkeit=255;
		}
		else{
			this.helligkeit = helligkeit;	
		}
	}

	public Ort getOrt() {
		return ort;
	}
}
