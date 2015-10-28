package de.homeautomation.devices;


public class TV extends Geraet {
	// Signalquelle {XBOX, WiiU, HTPC, PC,
	//	HDMI5, AV, SCART, COMPONENT, SAT, CABLE, USB, APP}
	
	private int kanal = 0;
	private int lautstaerke = 0;
	private int quelle = 9;
	
	public TV(String name, String macAdresse,
			Connection con){
		this.name = name;
		this.macAdresse = macAdresse;
		this.connection = con;		
	}
	
	public int getKanal() {
		return kanal;
	}
	public void setKanal(int kanal) {
		this.kanal = kanal;
	}
	public int getLautstaerke() {
		return lautstaerke;
	}
	public void setLautstaerke(int lautstaerke) {
		this.lautstaerke = lautstaerke;
	}
	public int getQuelle() {
		return quelle;
	}
	public void setQuelle(int quelle) {
		this.quelle = quelle;
	}
	
}
