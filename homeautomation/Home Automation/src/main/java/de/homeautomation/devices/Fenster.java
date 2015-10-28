package de.homeautomation.devices;

import de.homeautomation.resources.Ort;

public class Fenster {
	private boolean offen;
	private String name;
	private Ort ort;
	
	public Fenster (String name, Ort ort){
		this.name = ort.toString()+" "+name;
		this.ort = ort;
		this.offen = false;
	}

	public boolean isOffen() {
		return offen;
	}

	public void setOffen(boolean offen) {
		this.offen = offen;
	}

	public String getName() {
		return name;
	}

	public Ort getOrt() {
		return ort;
	}
}
