package de.hdm.bahnhofsteuerung.event;

import de.hdm.bahnhofsteuerung.bahnhof.Gleis;
import de.hdm.bahnhofsteuerung.bahnhof.Zug;

public abstract class ZugEvent {
	protected Zug zug;
	protected Gleis gleis;
	
	public Zug getZug() {
		return zug;
	}
	public Gleis getGleis() {
		return gleis;
	}
}
