package de.hdm.bahnhofsteuerung.bahnhof;

import java.util.Vector;

import javax.swing.JTextArea;


public class Bahnhof {
	private String name;
	private Vector<Gleis> gleise = new Vector<Gleis>();
	private JTextArea printStream;
	
	public JTextArea getPrintStream() {
		return printStream;
	}

	public Bahnhof(String name,int gleisanzahl){
		this.printStream=new JTextArea();
		this.printStream.setEditable(false);
		this.name=name;
		for(int i=1;i<=gleisanzahl;i++){
			this.gleise.add(new Gleis(this,i));
		}
	}
	
	public void addGleis(){
		this.gleise.add(new Gleis(this,gleise.size()+1));
	}
	
	public Vector<Gleis> getGleise(){
		return gleise;
	}

	public String getName() {
		return name;
	}
	
	public void println(String string){
		printStream.append("\n"+string);
		printStream.setCaretPosition(printStream.getDocument().getLength());
		printStream.repaint();
	}

}
