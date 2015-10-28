package de.homeautomation.devices;

import java.util.Vector;

public class Router extends Geraet {
	private Vector<Geraet> angeschlossen;
	
	public Router(){
		this.name = "Router";
		this.macAdresse = "RouterMAC";
		this.connection = Connection.Ethernet;
		this.status = Status.An;
	}
	public void geraetAnmelden (Geraet geraet){
		if(this.angeschlossen.contains(geraet)){
			System.out.println(geraet.name+" ist schon am Router angemeldet");
		}
	}
	public void geraetAbmelden(Geraet geraet){
		this.angeschlossen.removeElement(geraet);
	}
	
	public void ausgefallen(){
		this.angeschlossen.clear();
		this.status = Status.Aus;
	}
}
