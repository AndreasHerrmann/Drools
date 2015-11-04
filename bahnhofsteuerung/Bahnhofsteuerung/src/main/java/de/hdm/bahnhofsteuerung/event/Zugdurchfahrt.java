package de.hdm.bahnhofsteuerung.event;

import de.hdm.bahnhofsteuerung.bahnhof.Gleis;
import de.hdm.bahnhofsteuerung.bahnhof.Zug;

public class Zugdurchfahrt extends ZugEvent{
	
	public Zugdurchfahrt(Zug zug, Gleis gleis){
		this.zug=zug;
		this.gleis=gleis;
	}
}
