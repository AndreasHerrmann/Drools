package de.homeautomation.devices;

public class PC extends Geraet {
	public PC (String name, String macAdresse, Connection con){
		this.name = name;
		this.macAdresse = macAdresse;
		this.connection = con;
	}
}
