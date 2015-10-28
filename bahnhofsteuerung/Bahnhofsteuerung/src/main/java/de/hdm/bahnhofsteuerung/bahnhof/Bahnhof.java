package de.hdm.bahnhofsteuerung.bahnhof;

import java.util.Vector;

public class Bahnhof {
	private Vector<Gleis> gleise = new Vector<Gleis>();
	
	public Bahnhof(int gleisanzahl){
		for(int i=1;i<=gleisanzahl;i++){
			this.gleise.add(new Gleis(i));
		}
	}
	
	public void addGleis(){
		this.gleise.add(new Gleis(gleise.size()+1));
	}
	
	public Vector<Gleis> getGleise(){
		return gleise;
	}
}
