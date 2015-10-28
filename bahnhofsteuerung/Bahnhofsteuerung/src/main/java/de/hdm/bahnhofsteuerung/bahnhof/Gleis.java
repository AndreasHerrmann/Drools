package de.hdm.bahnhofsteuerung.bahnhof;

public class Gleis {
	private int nummer;
	private Anzeige anzeige;
	
	public Gleis(int nummer) {
		this.nummer=nummer;
		this.anzeige=new Anzeige(this);
		
	}

	public int getNummer(){
		return nummer;
	}
	
	public Anzeige getAnzeige(){
		return anzeige;
	}
}
