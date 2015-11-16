package de.hdm.bahnhofsteurung.resource;

public class ResourceGleis extends Resource {
	private ResourceBahnhof bahnhof;
	
	public ResourceGleis(long iD, ResourceBahnhof bahnhof) {
		super(iD);
		this.bahnhof=bahnhof;
	}

}
