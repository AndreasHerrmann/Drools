package de.hdm.drools;

import java.util.Vector;

import de.hdm.drools.resource.Bahnhof;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

public class Netzverwaltung {
	private static Vector<Knotenpunkt> knotenpunkte = new Vector<Knotenpunkt>();
	private static Vector<Bahnhof> bahnhoefe = new Vector<Bahnhof>();
	private static Vector<Zug> zuege = new Vector<Zug>();
	private static Vector<Strecke> strecken = new Vector<Strecke>();
	
	public static void addKnotenpunkt(Knotenpunkt knotenpunkt){
		knotenpunkte.add(knotenpunkt);
	}
	public static void addBahnhof(Bahnhof bahnhof){
		bahnhoefe.add(bahnhof);
	}
	public static void addZug(Zug zug){
		zuege.add(zug);
	}
	public static void addStrecke(Strecke strecke){
		strecken.add(strecke);
	}
}
