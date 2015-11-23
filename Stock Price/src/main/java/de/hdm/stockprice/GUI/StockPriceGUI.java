package de.hdm.stockprice.GUI;

import java.awt.Font;
import java.io.PrintStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * Klasse um den Standardausgabestream in ein Fenster umzuleiten,
 * um die Ausgabe übersichtlicher zu gestalten
 */
public class StockPriceGUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static StockPriceGUI gui=null;
	//Die Textfelder und ihre Labels
	private JTextArea textFeld;
	private JTextArea ibmTrend;
	private JLabel ibmTrendLabel= new JLabel("IBM Aktientrend ");
	private JTextArea msftTrend;
	private JLabel msftTrendLabel= new JLabel("Microsoft Aktientrend ");;
	private JTextArea datum;
	private JLabel datumLabel= new JLabel("Datum ");;
	
	
	private StockPriceGUI(){
		super("Stock Price Ausgabe");
		//Einstellungen für die Font
		Font schriftart = new Font("Arial",Font.PLAIN,20);
		
		//Ausgabefeld für den Standardprintstream
		textFeld = new JTextArea(50, 20);
        textFeld.setEditable(false);
        textFeld.setFont(schriftart);
        PrintStream printStream = new PrintStream(new CustomOutputStream(textFeld));
        
        //IBM Trend
        ibmTrend = new JTextArea(1,10);
        ibmTrend.setEditable(false);
        ibmTrend.setFont(schriftart);
        ibmTrendLabel.setFont(schriftart);
        ibmTrendLabel.setLabelFor(ibmTrend);
        
        //Microsoft Trend
        msftTrend = new JTextArea(1,10);
        msftTrend.setEditable(false);
        msftTrend.setFont(schriftart);
        msftTrendLabel.setFont(schriftart);
        msftTrendLabel.setLabelFor(msftTrend);
        
        //Datum
        datum = new JTextArea(1,10);
        datum.setEditable(false);
        datum.setFont(schriftart);
        datumLabel.setFont(schriftart);
        datumLabel.setLabelFor(datum);
        // re-assigns standard output stream and error output stream
        System.setOut(printStream);
        //System.setErr(printStream);
 
        // creates the GUI
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Box datumBox = new Box(BoxLayout.X_AXIS);
        datumBox.add(datumLabel);
        datumBox.add(datum);
        panel.add(datumBox);
        
        Box trendBox = new Box(BoxLayout.X_AXIS);
        trendBox.add(ibmTrendLabel);
        trendBox.add(ibmTrend);
        trendBox.add(msftTrendLabel);
        trendBox.add(msftTrend);
        panel.add(trendBox);
        panel.add(new JScrollPane(textFeld));
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 640);
        setLocationRelativeTo(null);    // centers on screen
	}
	
	public void changeDatum(String datum){
		this.datum.setText(datum);
		this.datum.repaint();
	}
	
	public void changeIbmTrend(String trend){
		this.ibmTrend.setText(trend);
		this.ibmTrend.repaint();
	}
	
	public void changeMicrosoftTrend(String trend){
		this.msftTrend.setText(trend);
		this.msftTrend.repaint();
	}
	
	public static StockPriceGUI createNewGUI(){
		if(gui==null){
			gui=new StockPriceGUI();
		}
		return gui;
	}
}
