package de.hdm.bahnhofsteurung.resource;

public class Strecke extends Resource {
	private ResourceBahnhof start;
	private ResourceBahnhof ziel;
	
	public Strecke(long iD, ResourceBahnhof start, ResourceBahnhof ziel) {
		super(iD);
		this.start=start;
		this.ziel=ziel;
	}

	public ResourceBahnhof getStart() {
		return start;
	}

	public ResourceBahnhof getZiel() {
		return ziel;
	}

}
