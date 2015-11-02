package de.hdm.bahnhofsteuerung.GUI;


import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.PrintStream;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.hdm.bahnhofsteuerung.bahnhof.Bahnhof;

public class BahnhofGUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BahnhofGUI(Vector<Bahnhof> bahnhoefe){
		super("Bahnhofsteuerung");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation( 0, 0 );
		this.setSize( Toolkit.getDefaultToolkit().getScreenSize() );
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,4));
		//Einstellungen für die Font
		Font schriftart = new Font("Arial",Font.PLAIN,14);
		//Ausgabefeld für den Standardprintstream
		JTextArea outputStream = new JTextArea(50, 20);
        outputStream.setEditable(false);
        outputStream.setFont(schriftart);
        PrintStream printStream = new PrintStream(new CustomOutputStream(outputStream));
        // Standard Output Stream und Error Output Stream dem textFeld zuweisen
        System.setOut(printStream);
        System.setErr(printStream);
        //In eine Box packen
        Box outputBox = new Box(BoxLayout.Y_AXIS);
        outputBox.setBorder(BorderFactory.createTitledBorder("Standard Output Stream"));
        outputBox.add(new JScrollPane(outputStream));
        panel.add(outputBox);
        
        //Für jeden Bahnhof den Outputstream in eine Box mit Titel packen und dem Panel hinzufügen
		for(int i=0;i<bahnhoefe.size()-1;i++){
			Box bahnhofBox = new Box(BoxLayout.Y_AXIS);
			bahnhofBox.setBorder(BorderFactory.createTitledBorder(bahnhoefe.get(i).getName()));
			bahnhoefe.get(i).getPrintStream().setFont(schriftart);
			bahnhoefe.get(i).getPrintStream().setSize(50, 10);
			bahnhofBox.add(new JScrollPane(bahnhoefe.get(i).getPrintStream()));
			panel.add(bahnhofBox);
		}
		add(panel);
		setVisible(true);
	}
}
