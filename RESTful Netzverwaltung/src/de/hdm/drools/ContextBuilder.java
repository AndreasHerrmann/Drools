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
	public ContextBuilder(Netzverwaltung netzverwaltung){
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
		Bahnhof stuttgart = new Bahnhof(1, "Stuttgart", kStuttgart,4);
		
		Knotenpunkt kFlughafen[] = {knotenpunkt10,knotenpunkt11};
		Bahnhof flughafen = new Bahnhof(2,"Flughafen",kFlughafen,2);
		
		Knotenpunkt kBoeblingen[] = {knotenpunkt12,knotenpunkt13};
		Bahnhof boeblingen = new Bahnhof(3,"Böblingen",kBoeblingen,2);
		
		Knotenpunkt kLeonberg[] = {knotenpunkt8,knotenpunkt9};
		Bahnhof leonberg = new Bahnhof(4,"Leonberg",kLeonberg,2);
		
		Knotenpunkt kPlochingen[] = {knotenpunkt6,knotenpunkt7};
		Bahnhof plochingen = new Bahnhof(5,"Plochingen",kPlochingen,2);
		
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
		netzverwaltung.addKnotenpunkt(knotenpunkt1);
		netzverwaltung.addKnotenpunkt(knotenpunkt2);
		netzverwaltung.addKnotenpunkt(knotenpunkt3);
		netzverwaltung.addKnotenpunkt(knotenpunkt4);
		netzverwaltung.addKnotenpunkt(knotenpunkt5);
		netzverwaltung.addKnotenpunkt(knotenpunkt6);
		netzverwaltung.addKnotenpunkt(knotenpunkt7);
		netzverwaltung.addKnotenpunkt(knotenpunkt8);
		netzverwaltung.addKnotenpunkt(knotenpunkt9);
		netzverwaltung.addKnotenpunkt(knotenpunkt10);
		netzverwaltung.addKnotenpunkt(knotenpunkt11);
		netzverwaltung.addKnotenpunkt(knotenpunkt12);
		netzverwaltung.addKnotenpunkt(knotenpunkt13);
		netzverwaltung.addKnotenpunkt(knotenpunkt14);
		netzverwaltung.addKnotenpunkt(knotenpunkt15);
		netzverwaltung.addKnotenpunkt(knotenpunkt16);
		//Bahnhöfe
		netzverwaltung.addBahnhof(stuttgart);
		netzverwaltung.addBahnhof(flughafen);
		netzverwaltung.addBahnhof(boeblingen);
		netzverwaltung.addBahnhof(leonberg);
		netzverwaltung.addBahnhof(plochingen);
		//Strecken
		netzverwaltung.addStrecke(strecke1);
		netzverwaltung.addStrecke(strecke2);
		netzverwaltung.addStrecke(strecke3);
		netzverwaltung.addStrecke(strecke4);
		netzverwaltung.addStrecke(strecke5);
		netzverwaltung.addStrecke(strecke6);
		netzverwaltung.addStrecke(strecke7);
		netzverwaltung.addStrecke(strecke8);
		netzverwaltung.addStrecke(strecke9);
		netzverwaltung.addStrecke(strecke10);
		netzverwaltung.addStrecke(strecke11);
		netzverwaltung.addStrecke(strecke12);
		netzverwaltung.addStrecke(strecke13);
		netzverwaltung.addStrecke(strecke14);
		netzverwaltung.addStrecke(strecke15);
		netzverwaltung.addStrecke(strecke16);
		netzverwaltung.addStrecke(strecke17);
		netzverwaltung.addStrecke(strecke18);
		netzverwaltung.addStrecke(strecke19);
		netzverwaltung.addStrecke(strecke20);
		netzverwaltung.addStrecke(strecke21);
		netzverwaltung.addStrecke(strecke22);
		netzverwaltung.addStrecke(strecke23);
		netzverwaltung.addStrecke(strecke24);
		netzverwaltung.addStrecke(strecke25);
		netzverwaltung.addStrecke(strecke26);
		netzverwaltung.addStrecke(strecke27);
		netzverwaltung.addStrecke(strecke28);
		netzverwaltung.addStrecke(strecke29);
		netzverwaltung.addStrecke(strecke30);
		netzverwaltung.addStrecke(strecke31);
		netzverwaltung.addStrecke(strecke32);
		//Züge
		netzverwaltung.addZug(zug1);
		netzverwaltung.addZug(zug2);
		netzverwaltung.addZug(zug3);
		netzverwaltung.getNetzveraltungsOutput().println("Context is build");
	}
}
