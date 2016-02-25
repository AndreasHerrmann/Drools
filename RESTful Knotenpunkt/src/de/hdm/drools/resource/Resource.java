package de.hdm.drools.resource;

/**
 * Abstrakte Oberklasse aller Resourcen, die der Knotenpunkt kennt
 * @author Andreas Herrmann
 * @param id Die ID der jeweiligen Resource
 */
public abstract class Resource {
	/**
	 * Die ID der Resource
	 */
	protected long id;
	
	public long getid() {
		return id;
	}

	public void setid(long iD) {
		this.id = iD;
	}
	
}
