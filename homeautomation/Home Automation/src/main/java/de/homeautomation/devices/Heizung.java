package de.homeautomation.devices;

import de.homeautomation.resources.Ort;

public class Heizung extends Geraet {
	
	private Ort ort;
	
	public Ort getOrt() {
		return ort;
	}

	public Heizung (String macAdresse, Ort ort){
		this.name = "Heizung im "+ort.toString();
		this.macAdresse = macAdresse;
		this.connection = Connection.Funk;
		this.ort = ort;
		this.status = Status.Aus;
	}

}
