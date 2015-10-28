package de.homeautomation.devices;

public class GameConsole extends Geraet{
	public enum Typ {WiiU, XboxOne};
	private Typ typ;
	
	public GameConsole(String name, String macAdresse,
			Connection con, Typ typ) {
		this.name= name;
		this.macAdresse= macAdresse;
		this.typ = typ;
		this.connection = con;
		this.status = Status.Aus;
	}

	public Typ getTyp() {
		return typ;
	}

}
