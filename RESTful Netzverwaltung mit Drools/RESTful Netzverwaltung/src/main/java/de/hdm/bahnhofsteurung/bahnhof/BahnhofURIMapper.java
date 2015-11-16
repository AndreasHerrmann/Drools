package de.hdm.bahnhofsteurung.bahnhof;

import java.util.Vector;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.hdm.bahnhofsteurung.resource.ResourceBahnhof;

/**
 * URI-Mapper für alle Bahnhöfe.
 * Die Klasse BahnhofURIMapper dient als URI-Mapper für alle
 * Bahnhofsthreads, sodass auf diese mit ihrer jeweiligen ID zugegriffen
 * werden kann. Dazu werden die Bahnhöfe auf /bahnhof/{ID} gemapped.
 * @author Andreas Herrmann
 * @param bahnhoefe Sammlung aller laufenden Bahnhofthreads
 *
 */
@RestController
@RequestMapping(value="/bahnhof")
public class BahnhofURIMapper {
	private Vector<OrgaBahnhof> bahnhoefe = new Vector<OrgaBahnhof>();
	
	/**
	 * Die Methode addBahnhof dient dazu einen neuen Bahnhofthread hinzuzufügen.
	 * Der Methode wird hierzu ein schon in der Netzverwaltung existierender
	 * Bahnhof übergeben, sodass er die Aufgaben des jeweiligen Bahnhof übernimmt.
	 * Dies dient dazu, dass der Bahnhof auch nach abmelden noch mit den jeweiligen Strecken
	 * verknüpft bleibt. Desweiteren wird angegeben wieviele Gleise er haben soll.
	 * @param resourceBahnhof Der schon existierende Bahnhof in der Netzverwaltung
	 * @param anzahlGleise Die Anzahl der Gleise, die für diesen Bahnhof erstellt werden sollen
	 */
	@RequestMapping(value="/", method=RequestMethod.PUT,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public void addBahnhof(@RequestBody ResourceBahnhof resourceBahnhof,@RequestBody int anzahlGleise){
		bahnhoefe.add(new OrgaBahnhof(resourceBahnhof,anzahlGleise));
		
	}
	
}
