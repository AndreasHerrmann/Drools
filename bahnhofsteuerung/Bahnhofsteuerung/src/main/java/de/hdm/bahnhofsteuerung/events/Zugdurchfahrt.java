package de.hdm.bahnhofsteuerung.events;

import de.hdm.bahnhofsteuerung.bahnhof.Zug;

public class Zugdurchfahrt {
	private Zug zug;
	
	public Zugdurchfahrt(Zug zug){
		this.zug=zug;
	}
	public Zug getZug(){
		return zug;
	}
}
