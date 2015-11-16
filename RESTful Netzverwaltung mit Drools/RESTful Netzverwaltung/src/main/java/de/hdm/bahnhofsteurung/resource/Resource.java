package de.hdm.bahnhofsteurung.resource;

public abstract class Resource {
	private long iD;
	
	public Resource(long iD){
		this.iD=iD;
	}
	
	public long getID(){
		return this.iD;
	}
	public void setID(long iD){
		this.iD=iD;
	}
	
	public boolean equals(Resource resource){
		if(this.iD!=resource.getID()){
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean equals(long iD){
		if(this.iD!=iD){
			return false;
		}
		else{
			return true;
		}
	}
}
