package de.hdm.bahnhofsteuerung.bahnhof;

public class Gleis {
	private int nummer;
	private Bahnhof bahnhof;
	private Anzeige anzeige;
	
	public Gleis(Bahnhof bahnhof, int nummer) {
		this.nummer=nummer;
		this.bahnhof=bahnhof;
		this.anzeige=new Anzeige(this);
		
	}

	public int getNummer(){
		return nummer;
	}
	
	public Anzeige getAnzeige(){
		return anzeige;
	}

	public Bahnhof getBahnhof() {
		return bahnhof;
	}
}
