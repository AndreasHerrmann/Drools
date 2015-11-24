package de.hdm.bahnhofsteurung.bahnhof;

import de.hdm.bahnhofsteurung.resource.ResourceBahnhof;
import de.hdm.bahnhofsteurung.resource.ResourceGleis;

public class OrgaGleis {
	private ResourceGleis gleis;
	private String anzeige;
	
	public OrgaGleis(ResourceBahnhof bahnhof, long iD){
		this.gleis=new ResourceGleis(iD,bahnhof);
		this.anzeige=new String();
	}

	public ResourceGleis getGleis() {
		return gleis;
	}

	public String getAnzeige() {
		return anzeige;
	}
	
	public void addLine(String line){
		this.anzeige=anzeige+"\n"+line;
	}
}
