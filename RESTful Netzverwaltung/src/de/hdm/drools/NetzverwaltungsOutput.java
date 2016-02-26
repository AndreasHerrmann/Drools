package de.hdm.drools;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.NoSuchElementException;

public class NetzverwaltungsOutput {
	private static ArrayDeque<String> outputString;
	private static SimpleDateFormat sdf;
	
	public void println(String line){
		outputString.addLast("\n["+sdf.format(new Date())+"] "+line+"\r\n");
		System.out.println(line);
	}
	public String getNextString(){
		String string=new String();
		try{
			string = outputString.removeFirst();			
		}
		catch(NoSuchElementException nsee){
			return "";
		}
		return string;

	}
	public void start(){
		outputString = new ArrayDeque<String>();
		sdf = new SimpleDateFormat("HH:mm:ss");
		println("NetzverwaltungsOutput gestartet");
	}
	public void stop(){
		println("Netzverwaltung wird beendet");
		outputString.addLast(null);
	}
}
