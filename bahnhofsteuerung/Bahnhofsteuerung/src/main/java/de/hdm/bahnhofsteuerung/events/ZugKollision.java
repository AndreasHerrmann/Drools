package de.hdm.bahnhofsteuerung.events;

import de.hdm.bahnhofsteuerung.bahnhof.Gleis;
import de.hdm.bahnhofsteuerung.bahnhof.Zug;

public class ZugKollision {
	private Zug zug1;
	private Zug zug2;
	private Gleis gleis;
	
	public ZugKollision(Zug ersterZug, Zug zweiterZug, Gleis gleis){
		this.zug1=ersterZug;
		this.zug2=zweiterZug;
		this.gleis=gleis;
	}

	public Zug getZug1() {
		return zug1;
	}

	public Zug getZug2() {
		return zug2;
	}

	public Gleis getGleis() {
		return gleis;
	}
}
