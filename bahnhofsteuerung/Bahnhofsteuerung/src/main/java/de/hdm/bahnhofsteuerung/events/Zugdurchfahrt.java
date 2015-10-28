package de.hdm.bahnhofsteuerung.events;

import de.hdm.bahnhofsteuerung.bahnhof.Gleis;
import de.hdm.bahnhofsteuerung.bahnhof.Zug;

public class Zugdurchfahrt {
	private Zug zug;
	private Gleis gleis;
	
	public Zugdurchfahrt(Zug zug, Gleis gleis){
		this.zug=zug;
		this.gleis=gleis;
	}
	public Zug getZug(){
		return zug;
	}
	public Gleis getGleis(){
		return gleis;
	}
}
