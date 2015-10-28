package de.homeautomation.devices;

public class HTPC extends Geraet {
	public enum Wiedergabe {Abspielen, Pausiert, Beendet, Keine};
	
	private String medium = "";
	private Wiedergabe wiedergabe;
	
	public HTPC (String name, String macAdresse,
			Connection con){
		this.name = name;
		this.macAdresse = macAdresse;
		this.connection = con;
		this.status = Status.Aus;
		this.wiedergabe = Wiedergabe.Keine;
	}
	
	public void abspielen (String medium){
		this.medium = medium;
		this.wiedergabe = Wiedergabe.Abspielen;
	}
	
	public void pausieren (){
		this.wiedergabe = Wiedergabe.Pausiert;
	}
	
	public void beenden (){
		//Wiedergabe.Beendet und dann Wiedergabe.Keine um bestimmte 
		//Events zu erzeugen
		this.wiedergabe = Wiedergabe.Beendet;
		this.medium = "";
		this.wiedergabe = Wiedergabe.Keine;
	}
	
	public String getMedium() {
		return medium;
	}

	public Wiedergabe getWiedergabe() {
		return wiedergabe;
	}
}
