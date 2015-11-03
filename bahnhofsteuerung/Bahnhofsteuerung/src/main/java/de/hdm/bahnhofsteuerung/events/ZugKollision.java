package de.hdm.bahnhofsteuerung.events;

import de.hdm.bahnhofsteuerung.bahnhof.Gleis;
import de.hdm.bahnhofsteuerung.bahnhof.Zug;

public class ZugKollision extends ZugEvent{
	private Zug zug2;
	
	public ZugKollision(Zug ersterZug, Zug zweiterZug, Gleis gleis){
		this.zug=ersterZug;
		this.zug2=zweiterZug;
		this.gleis=gleis;
	}

	public Zug getZug2() {
		return zug2;
	}
}
