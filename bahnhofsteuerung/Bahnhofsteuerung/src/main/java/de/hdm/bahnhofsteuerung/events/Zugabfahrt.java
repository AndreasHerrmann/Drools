package de.hdm.bahnhofsteuerung.events;

import de.hdm.bahnhofsteuerung.bahnhof.Gleis;
import de.hdm.bahnhofsteuerung.bahnhof.Zug;

public class Zugabfahrt {
	private Zug zug;
	private Gleis gleis;
	
	public Zugabfahrt(Zug zug, Gleis gleis){
		this.zug=zug;
		this.gleis=gleis;
	}
	public Zug getZug() {
		return zug;
	}
	public Gleis getGleis() {
		return gleis;
	}
}
