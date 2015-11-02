package de.hdm.bahnhofsteuerung.bahnhof;


public class Anzeige{
	/**
	 * 
	 */
	private Gleis gleis;
	
	public Anzeige(Gleis gleis){
		this.gleis=gleis;
	}
	public void anzeigen(String string){
		gleis.getBahnhof().println("Gleis "+gleis.getNummer()+": "+string);
	}
	public Gleis getGleis(){
		return gleis;
	}
}
