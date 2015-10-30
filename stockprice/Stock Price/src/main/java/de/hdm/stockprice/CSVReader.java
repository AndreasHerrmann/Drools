package de.hdm.stockprice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import de.hdm.stockprice.events.StockEvent;

public class CSVReader {
	private Stock stock;
	private String file="";
	
	public CSVReader(Stock stock, String file){
		this.stock=stock;
		this.file=file;
	}
	
	public Vector<StockEvent> createStockEvents(){
		BufferedReader br = null;
		Vector<StockEvent> stockEvents = new Vector<StockEvent>();
		try {

			br = new BufferedReader(new FileReader(file));
			String line="";
			//Zeile lesen, um in der Schleife die Diskriptorzeile zu überspringen
			br.readLine();
			while ((line = br.readLine()) != null) {

			    // Komma als Seperator benutzen
				String[] stockData = line.split(",");
				stockEvents.add(new StockEvent(stock,Float.valueOf(stockData[3]),Float.valueOf(stockData[1]),Float.valueOf(stockData[4]),Float.valueOf(stockData[5])));
				

			}

		} catch (NumberFormatException nfe){
			nfe.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stockEvents;
	}
}
