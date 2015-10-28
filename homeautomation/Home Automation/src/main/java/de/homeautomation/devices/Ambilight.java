package de.homeautomation.devices;

public class Ambilight extends Geraet {
	// Eingang { XBOX, WiiU, HTPC, PC, TV};
	
	private int eingang=5;
	private int helligkeit;
	
	public Ambilight (String macAdresse){
		this.name = "Ambilight";
		this.connection = Connection.Ethernet;
		this.macAdresse = macAdresse;
		this.status = Status.Aus;
		this.helligkeit = 0;
	}
	public int getEingang() {
		return eingang;
	}

	public void setEingang(int eingang) {
		this.eingang = eingang;
	}

	public int getHelligkeit() {
		return helligkeit;
	}

	public void setHelligkeit(int helligkeit) {
		if(helligkeit<0){
			this.helligkeit = 0;
		}
		else if (helligkeit>255){
			this.helligkeit=255;
		}
		else{
			this.helligkeit = helligkeit;	
		}
	}
}
