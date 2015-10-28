package de.homeautomation.devices;

public abstract class Geraet {
	public enum Connection {WiFi, Ethernet, Funk};
	public enum Status {An, Aus, Updaten};
	
	protected String name;
	protected String macAdresse;
	protected Connection connection;
	protected Status status = Status.Aus;
	
	public void anschalten (){
		this.status = Status.An;
		System.out.println(this.name+" wurde angeschalten");
	}
	
	public void ausschalten (){
		this.status = Status.Aus;
		System.out.println(this.name+" wurde ausgeschalten");
	}
	
	public void updaten(){
		this.status = Status.Updaten;
		System.out.println(this.name+" führt ein Update durch");
	}
	public Status getStatus() {
		return status;
	}
	public Connection getConnection() {
		return connection;
	}
	public String getName() {
		return name;
	}
	public String getMacAdresse() {
		return macAdresse;
	}
}
