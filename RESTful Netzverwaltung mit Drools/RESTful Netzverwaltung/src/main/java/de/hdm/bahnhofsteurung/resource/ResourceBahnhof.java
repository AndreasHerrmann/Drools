package de.hdm.bahnhofsteurung.resource;

public class ResourceBahnhof extends Resource {
	private String name;
	
	public ResourceBahnhof(long iD, String name) {
		super(iD);
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}

}
