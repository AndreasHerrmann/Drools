package de.hdm.bahnhofsteuerung.event;

import de.hdm.bahnhofsteuerung.bahnhof.Gleis;
import de.hdm.bahnhofsteuerung.bahnhof.Zug;

public class Zugeinfahrt extends ZugEvent{

	private String ziel;
	
	public Zugeinfahrt(Zug zug, Gleis gleis, String ziel){
		this.zug=zug;
		this.gleis=gleis;
		this.ziel=ziel;
	}
	public String getZiel(){
		return ziel;
	}
}
