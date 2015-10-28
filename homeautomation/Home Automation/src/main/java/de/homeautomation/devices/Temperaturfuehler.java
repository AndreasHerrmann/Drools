package de.homeautomation.devices;

import de.homeautomation.resources.Ort;

public class Temperaturfuehler extends Geraet {

	private float temperatur;
	private Ort ort;
	
	public Temperaturfuehler (String macAdresse, Ort ort){
		this.name = "Temperaturfuehler im "+ ort.toString();
		this.connection = Connection.Funk;
		this.macAdresse = macAdresse;
		this.ort = ort;
		this.status = Status.Aus;
	}

	public float getTemperatur() {
		return temperatur;
	}

	public void setTemperatur(float temperatur) {
		this.temperatur = temperatur;
	}

	public Ort getOrt() {
		return ort;
	}
}
