package de.hdm.bahnhofsteuerung.bahnhof;

import javax.swing.JTextArea;


public class Anzeige extends JTextArea{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Gleis gleis;
	
	public Anzeige(Gleis gleis){
		super();
		this.setEditable(false);
		this.gleis=gleis;
	}

	public Gleis getGleis(){
		return gleis;
	}
	public void anzeigen(String string){
		this.append("\n"+string);
		this.setCaretPosition(this.getDocument().getLength());
		this.repaint();
	}
}
