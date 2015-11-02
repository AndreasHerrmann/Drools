package de.hdm.bahnhofsteuerung.events;

import de.hdm.bahnhofsteuerung.bahnhof.Gleis;
import de.hdm.bahnhofsteuerung.bahnhof.Zug;

public class Zugabfahrt {
	private Zug zug;
	private Gleis gleis;
	private String ziel;
	
	public Zugabfahrt(Zug zug, Gleis gleis, String ziel){
		this.zug=zug;
		this.gleis=gleis;
		this.ziel=ziel;
	}
	public Zug getZug() {
		return zug;
	}
	public Gleis getGleis() {
		return gleis;
	}
	public String getZiel() {
		return ziel;
	}
}
