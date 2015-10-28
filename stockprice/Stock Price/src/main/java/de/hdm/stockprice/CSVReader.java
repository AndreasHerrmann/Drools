package de.hdm.stockprice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class CSVReader {
	private Stock stock;
	private String file="";
	
	public CSVReader(Stock stock, String file){
		this.stock=stock;
		this.file=file;
	}
	
	@SuppressWarnings("deprecation")
	public Vector<StockEvent> createStockEvents(){
		BufferedReader br = null;
		Vector<StockEvent> stockEvents = new Vector<StockEvent>();
		try {

			br = new BufferedReader(new FileReader(file));
			String line="";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			//Zeile lesen, um in der Schleife die Diskriptorzeile zu überspringen
			br.readLine();
			while ((line = br.readLine()) != null) {

			    // Komma als Seperator benutzen
				String[] stockData = line.split(",");
				Date stockDate = sdf.parse(stockData[0]);
				stockDate.setHours(16);
				stockDate.setMinutes(0);
				stockDate.setSeconds(0);
				stockEvents.add(new StockEvent(stock,stockDate,Float.valueOf(stockData[3]),Float.valueOf(stockData[1]),Float.valueOf(stockData[4]),Float.valueOf(stockData[5])));
				

			}

		} catch (NumberFormatException nfe){
			nfe.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException pe){
			pe.printStackTrace();
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
