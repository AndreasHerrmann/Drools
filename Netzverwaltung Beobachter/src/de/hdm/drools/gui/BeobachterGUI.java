package de.hdm.drools.gui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BeobachterGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea outputStream;
	
	public BeobachterGUI(){
		super("Events der Netzverwaltung");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation( 0, 0 );
		this.setSize(800,600);
		
		//Einstellungen für die Schriftart
		Font schriftart = new Font("Arial",Font.PLAIN,16);
		
		//Ausgabefeld für die Events
      	outputStream = new JTextArea(20,20);
        outputStream.setEditable(false);
        outputStream.setFont(schriftart);
        outputStream.getInsets().set(5, 5, 5, 5);
        this.add(new JScrollPane(outputStream));
        
        //Anzeigen
        setVisible(true);
	}
	public void write(String text){
        // redirects data to the text area
        outputStream.append(text);
        // scrolls the text area to the end of data
        outputStream.setCaretPosition(outputStream.getDocument().getLength());
    }

}
