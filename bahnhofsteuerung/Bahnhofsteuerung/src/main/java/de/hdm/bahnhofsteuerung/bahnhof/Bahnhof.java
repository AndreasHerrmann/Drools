package de.hdm.bahnhofsteuerung.bahnhof;

import java.util.Vector;

public class Bahnhof {
	private String name;
	private Vector<Gleis> gleise = new Vector<Gleis>();

	public Bahnhof(String name,int gleisanzahl){
		
		this.name=name;
		for(int i=1;i<=gleisanzahl;i++){
			this.gleise.add(new Gleis(this,i));
		}
	}
	
	public void addGleis(){
		this.gleise.add(new Gleis(this,gleise.size()+1));
	}
	
	public Vector<Gleis> getGleise(){
		return gleise;
	}

	public String getName() {
		return name;
	}
}
