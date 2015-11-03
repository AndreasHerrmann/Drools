package de.hdm.bahnhofsteuerung.bahnhof;

public class Fahrplan {
	private Bahnhof bahnhof;
	private Gleis gleis;
	private int aufenthalt;
	private int fahrzeit; //Fahrzeit zum nächsten Punkt
	
	public Fahrplan(Bahnhof bhf, Gleis gleis, int aufenthalt, int fahrzeit){
		this.bahnhof=bhf;
		this.gleis=gleis;
		this.aufenthalt=aufenthalt;
		this.fahrzeit=fahrzeit;
	}
	public Bahnhof getBahnhof() {
		return bahnhof;
	}

	public Gleis getGleis() {
		return gleis;
	}

	public int getAufenthalt() {
		return aufenthalt;
	}

	public int getFahrzeit() {
		return fahrzeit;
	}
}
