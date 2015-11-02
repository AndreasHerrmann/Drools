package de.homeautomation.GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.kie.api.runtime.KieSession;

import de.homeautomation.SystemBuilder;
import de.homeautomation.GUI.CustomOutputStream;

public class HomeAutomationGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static HomeAutomationGUI gui=null;
	private static SystemBuilder builder;
	private JPanel panel;
	private JTextArea streamText;
	
	private HomeAutomationGUI(KieSession session){
		super("Home Automation");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		builder = SystemBuilder.buildSystem(session);
		panel=new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		//Einstellungen für die Font
		Font schriftart = new Font("Arial",Font.PLAIN,20);
				
		//Ausgabefeld für den Standardprintstream
		streamText = new JTextArea(50, 20);
		streamText.setEditable(false);
		streamText.setFont(schriftart);
		PrintStream printStream = new PrintStream(new CustomOutputStream(streamText));
		
		//Fernseher
		Box tvBox = new Box(BoxLayout.X_AXIS);
		tvBox.setBorder(BorderFactory.createTitledBorder("Fernseher"));
		JButton tvAn = new JButton("An");
		tvAn.setFont(schriftart);
		tvAn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				builder.getWohnTV().anschalten();
			}});
		tvBox.add(tvAn);
		JButton tvAus = new JButton("Aus");
		tvAus.setFont(schriftart);
		tvAus.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				builder.getWohnTV().ausschalten();
			}});
		tvBox.add(tvAn);
		tvBox.add(tvAus);
		
		panel.add(tvBox);
		this.add(panel);
		
	}
	
	public static HomeAutomationGUI newGUI(KieSession session){
		if(gui==null){
			gui=new HomeAutomationGUI(session);
		}
		return gui;
	}

}
