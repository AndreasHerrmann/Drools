package de.hdm.drools;

import de.hdm.drools.resource.Bahnhof;
import de.hdm.drools.resource.Fahrplan;
import de.hdm.drools.resource.Knotenpunkt;
import de.hdm.drools.resource.Strecke;
import de.hdm.drools.resource.Zug;

/**
 * Erstellt den Kontext der Netzverwaltung. Erstellt die Objekte aller bekannten
 * Züge, Bahnhöfe, Knotenpunkte und Strecken.
 * @author Andreas Herrmann
 *
 */
public class ContextBuilder {
	public ContextBuilder(){
		//16 Knotenpunkte erstellen
		Knotenpunkt knotenpunkt1 = new Knotenpunkt(1);
		Knotenpunkt knotenpunkt2 = new Knotenpunkt(2,true);
		Knotenpunkt knotenpunkt3 = new Knotenpunkt(3,true);
		Knotenpunkt knotenpunkt4 = new Knotenpunkt(4);
		Knotenpunkt knotenpunkt5 = new Knotenpunkt(5);
		Knotenpunkt knotenpunkt6 = new Knotenpunkt(6,true);
		Knotenpunkt knotenpunkt7 = new Knotenpunkt(7,true);
		Knotenpunkt knotenpunkt8= new Knotenpunkt(8,true);
		Knotenpunkt knotenpunkt9 = new Knotenpunkt(9,true);
		Knotenpunkt knotenpunkt10 = new Knotenpunkt(10,true);
		Knotenpunkt knotenpunkt11 = new Knotenpunkt(11,true);
		Knotenpunkt knotenpunkt12 = new Knotenpunkt(12,true);
		Knotenpunkt knotenpunkt13 = new Knotenpunkt(13,true);
		Knotenpunkt knotenpunkt14 = new Knotenpunkt(14);
		Knotenpunkt knotenpunkt15 = new Knotenpunkt(15);
		Knotenpunkt knotenpunkt16 = new Knotenpunkt(16);
		
		//5 Bahnhöfe erstellen
		Knotenpunkt kStuttgart[] = {knotenpunkt2,knotenpunkt3};
		Bahnhof stuttgart = new Bahnhof(1, "Stuttgart", kStuttgart);
		
		Knotenpunkt kFlughafen[] = {knotenpunkt10,knotenpunkt11};
		Bahnhof flughafen = new Bahnhof(2,"Flughafen",kFlughafen);
		
		Knotenpunkt kBoeblingen[] = {knotenpunkt12,knotenpunkt13};
		Bahnhof boeblingen = new Bahnhof(3,"Böblingen",kBoeblingen);
		
		Knotenpunkt kLeonberg[] = {knotenpunkt8,knotenpunkt9};
		Bahnhof leonberg = new Bahnhof(4,"Leonberg",kLeonberg);
		
		Knotenpunkt kPlochingen[] = {knotenpunkt6,knotenpunkt7};
		Bahnhof plochingen = new Bahnhof(5,"Plochingen",kPlochingen);
		
		//Knotenpunkte mit Strecken verbinden
		Strecke strecke1 = new Strecke(1,knotenpunkt1,knotenpunkt2);
		Strecke strecke2 = new Strecke(2,knotenpunkt1,knotenpunkt2);
		Strecke strecke3 = new Strecke(3,knotenpunkt2,knotenpunkt1);
		Strecke strecke4 = new Strecke(4,knotenpunkt2,knotenpunkt1);
		Strecke strecke5 = new Strecke(5,knotenpunkt3,knotenpunkt4);
		Strecke strecke6 = new Strecke(6,knotenpunkt4,knotenpunkt3);
		Strecke strecke7 = new Strecke(7,knotenpunkt3,knotenpunkt5);
		Strecke strecke8 = new Strecke(8,knotenpunkt5,knotenpunkt3);
		Strecke strecke9 = new Strecke(9,knotenpunkt4,knotenpunkt5);
		Strecke strecke10= new Strecke(10,knotenpunkt5,knotenpunkt4);
		Strecke strecke11= new Strecke(11,knotenpunkt4,knotenpunkt7);
		Strecke strecke12= new Strecke(12,knotenpunkt7,knotenpunkt4);
		Strecke strecke13= new Strecke(13,knotenpunkt6,knotenpunkt5);
		Strecke strecke14= new Strecke(14,knotenpunkt5,knotenpunkt6);
		Strecke strecke15= new Strecke(15,knotenpunkt5,knotenpunkt8);
		Strecke strecke16= new Strecke(16,knotenpunkt8,knotenpunkt5);
		Strecke strecke17= new Strecke(17,knotenpunkt9,knotenpunkt14);
		Strecke strecke18= new Strecke(18,knotenpunkt14,knotenpunkt9);
		Strecke strecke19= new Strecke(19,knotenpunkt14,knotenpunkt12);
		Strecke strecke20= new Strecke(20,knotenpunkt12,knotenpunkt14);
		Strecke strecke21= new Strecke(21,knotenpunkt13,knotenpunkt15);
		Strecke strecke22= new Strecke(22,knotenpunkt15,knotenpunkt13);
		Strecke strecke23= new Strecke(23,knotenpunkt15,knotenpunkt11);
		Strecke strecke24= new Strecke(24,knotenpunkt11,knotenpunkt15);
		Strecke strecke25= new Strecke(25,knotenpunkt12,knotenpunkt1);
		Strecke strecke26= new Strecke(26,knotenpunkt1,knotenpunkt12);
		Strecke strecke27= new Strecke(27,knotenpunkt1,knotenpunkt10);
		Strecke strecke28= new Strecke(28,knotenpunkt10,knotenpunkt1);
		Strecke strecke29= new Strecke(29,knotenpunkt14,knotenpunkt16);
		Strecke strecke30= new Strecke(30,knotenpunkt9,knotenpunkt16);
		Strecke strecke31= new Strecke(31,knotenpunkt16,knotenpunkt12);
		Strecke strecke32= new Strecke(32,knotenpunkt16,knotenpunkt1);
		
		//Fahrpläne für die Züge erstellen
		//Fahrplan 1
		Knotenpunkt kf1[] = {knotenpunkt2,knotenpunkt3,knotenpunkt5,knotenpunkt6
				,knotenpunkt7,knotenpunkt4,knotenpunkt3};
		Integer if1[] = {2,10,5,1,7,10,0};
		Fahrplan fahrplan1 = new Fahrplan(1,kf1,if1);
		//Fahrplan 2
		Knotenpunkt kf2[] = {knotenpunkt12,knotenpunkt13,knotenpunkt15,knotenpunkt11
				,knotenpunkt10,knotenpunkt1,knotenpunkt2,knotenpunkt3,knotenpunkt5
				,knotenpunkt8,knotenpunkt9,knotenpunkt16};
		Integer if2[] = {1,3,5,1,7,10,1,5,10,1,5,4};
		Fahrplan fahrplan2 = new Fahrplan(2,kf2,if2);
		//Fahrplan 3
		Knotenpunkt kf3[] = {knotenpunkt8,knotenpunkt9,knotenpunkt16,knotenpunkt1
				,knotenpunkt10,knotenpunkt11,knotenpunkt10,knotenpunkt1,knotenpunkt12
				,knotenpunkt14,knotenpunkt9};
		Integer if3[] = {1,5,10,8,1,1,8,6,10,8,1};
		Fahrplan fahrplan3 = new Fahrplan(3,kf3,if3);
				
		//Züge
		Zug zug1 = new Zug(1,fahrplan1);
		Zug zug2 = new Zug(2,fahrplan2);
		Zug zug3 = new Zug(3,fahrplan3);
		
		//Alle Objekte den entsprechenden Vectoren der Netzverwaltung hinzufügen
		//Knotenpunkte
		Netzverwaltung.addKnotenpunkt(knotenpunkt1);
		Netzverwaltung.addKnotenpunkt(knotenpunkt2);
		Netzverwaltung.addKnotenpunkt(knotenpunkt3);
		Netzverwaltung.addKnotenpunkt(knotenpunkt4);
		Netzverwaltung.addKnotenpunkt(knotenpunkt5);
		Netzverwaltung.addKnotenpunkt(knotenpunkt6);
		Netzverwaltung.addKnotenpunkt(knotenpunkt7);
		Netzverwaltung.addKnotenpunkt(knotenpunkt8);
		Netzverwaltung.addKnotenpunkt(knotenpunkt9);
		Netzverwaltung.addKnotenpunkt(knotenpunkt10);
		Netzverwaltung.addKnotenpunkt(knotenpunkt11);
		Netzverwaltung.addKnotenpunkt(knotenpunkt12);
		Netzverwaltung.addKnotenpunkt(knotenpunkt13);
		Netzverwaltung.addKnotenpunkt(knotenpunkt14);
		Netzverwaltung.addKnotenpunkt(knotenpunkt15);
		Netzverwaltung.addKnotenpunkt(knotenpunkt16);
		//Bahnhöfe
		Netzverwaltung.addBahnhof(stuttgart);
		Netzverwaltung.addBahnhof(flughafen);
		Netzverwaltung.addBahnhof(boeblingen);
		Netzverwaltung.addBahnhof(leonberg);
		Netzverwaltung.addBahnhof(plochingen);
		//Strecken
		Netzverwaltung.addStrecke(strecke1);
		Netzverwaltung.addStrecke(strecke2);
		Netzverwaltung.addStrecke(strecke3);
		Netzverwaltung.addStrecke(strecke4);
		Netzverwaltung.addStrecke(strecke5);
		Netzverwaltung.addStrecke(strecke6);
		Netzverwaltung.addStrecke(strecke7);
		Netzverwaltung.addStrecke(strecke8);
		Netzverwaltung.addStrecke(strecke9);
		Netzverwaltung.addStrecke(strecke10);
		Netzverwaltung.addStrecke(strecke11);
		Netzverwaltung.addStrecke(strecke12);
		Netzverwaltung.addStrecke(strecke13);
		Netzverwaltung.addStrecke(strecke14);
		Netzverwaltung.addStrecke(strecke15);
		Netzverwaltung.addStrecke(strecke16);
		Netzverwaltung.addStrecke(strecke17);
		Netzverwaltung.addStrecke(strecke18);
		Netzverwaltung.addStrecke(strecke19);
		Netzverwaltung.addStrecke(strecke20);
		Netzverwaltung.addStrecke(strecke21);
		Netzverwaltung.addStrecke(strecke22);
		Netzverwaltung.addStrecke(strecke23);
		Netzverwaltung.addStrecke(strecke24);
		Netzverwaltung.addStrecke(strecke25);
		Netzverwaltung.addStrecke(strecke26);
		Netzverwaltung.addStrecke(strecke27);
		Netzverwaltung.addStrecke(strecke28);
		Netzverwaltung.addStrecke(strecke29);
		Netzverwaltung.addStrecke(strecke30);
		Netzverwaltung.addStrecke(strecke31);
		Netzverwaltung.addStrecke(strecke32);
		//Züge
		Netzverwaltung.addZug(zug1);
		Netzverwaltung.addZug(zug2);
		Netzverwaltung.addZug(zug3);
		System.out.println("Context is build");
	}
}
