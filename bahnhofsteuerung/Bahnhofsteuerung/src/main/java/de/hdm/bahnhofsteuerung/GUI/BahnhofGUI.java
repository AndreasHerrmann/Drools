package de.hdm.bahnhofsteuerung.GUI;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.PrintStream;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import de.hdm.bahnhofsteuerung.bahnhof.Bahnhof;
import de.hdm.bahnhofsteuerung.bahnhof.Gleis;

public class BahnhofGUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTextField vergangeneZeiteinheiten = new JTextField(7);

	public BahnhofGUI(Vector<Bahnhof> bahnhoefe){
		super("Bahnhofsteuerung");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation( 0, 0 );
		this.setSize( Toolkit.getDefaultToolkit().getScreenSize() );
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,4));
		
		//Einstellungen für die Schriftart
		Font schriftart = new Font("Arial",Font.PLAIN,14);
		//Einstellungen für Grenzen
		Border border = BorderFactory.createLineBorder(Color.BLACK,2);
        
        //Vergangene Zeiteinheiten verpacken
        JLabel zeitEinheitenLabel = new JLabel("vergangene Zeiteinheiten:");
        zeitEinheitenLabel.setFont(schriftart);
        vergangeneZeiteinheiten.setFont(schriftart);
        vergangeneZeiteinheiten.setEditable(false);
        zeitEinheitenLabel.setLabelFor(vergangeneZeiteinheiten);
        Box zeitEinheitenBox = new Box(BoxLayout.X_AXIS);
        zeitEinheitenBox.add(zeitEinheitenLabel);
        zeitEinheitenBox.add(vergangeneZeiteinheiten);
     
        //Ausgabefeld für den Standardprintstream
      	JTextArea outputStream = new JTextArea(50, 10);
        outputStream.setEditable(false);
        outputStream.setFont(schriftart);
        PrintStream printStream = new PrintStream(new CustomOutputStream(outputStream));
              
        // Standard Output Stream und Error Output Stream dem textFeld zuweisen
        System.setOut(printStream);
        System.setErr(printStream);
        //In eine Box packen
        Box outputBox = new Box(BoxLayout.Y_AXIS);
        outputBox.add(zeitEinheitenBox);
        outputBox.setBorder(BorderFactory.createTitledBorder(border,"Standard Output Stream"));
        outputBox.add(new JScrollPane(outputStream));
        panel.add(outputBox);
        
        //Für jeden Bahnhof die Anzeigen in eine Box mit Titel packen und dem Panel hinzufügen
		for(int i=0;i<bahnhoefe.size();i++){
			Box bahnhofBox = new Box(BoxLayout.Y_AXIS);
			bahnhofBox.setBorder(BorderFactory.createTitledBorder(border,bahnhoefe.get(i).getName()));
			//Für jedes Gleis die Anzeige abfragen und in eine eigene Box packen
			for(int j=0;j<bahnhoefe.get(i).getGleise().size();j++){
				Box anzeigeBox = new Box(BoxLayout.X_AXIS);
				Gleis gleis = bahnhoefe.get(i).getGleise().get(j);
				anzeigeBox.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
				JLabel label = new JLabel("Gleis "+gleis.getNummer());
				label.setFont(schriftart);
				label.setLabelFor(gleis.getAnzeige());
				anzeigeBox.add(label);
				gleis.getAnzeige().setFont(schriftart);
				gleis.getAnzeige().setSize(20,5);
				anzeigeBox.add(new JScrollPane(gleis.getAnzeige()));
				bahnhofBox.add(anzeigeBox);
			
			}
			panel.add(bahnhofBox);
		}
		add(panel);
		setVisible(true);
	}
	
	public void vergangeneZeiteinheitenAktualisieren(int zeitEinheiten){
		vergangeneZeiteinheiten.setText(Integer.toString(zeitEinheiten));
		vergangeneZeiteinheiten.repaint();
	}
}
