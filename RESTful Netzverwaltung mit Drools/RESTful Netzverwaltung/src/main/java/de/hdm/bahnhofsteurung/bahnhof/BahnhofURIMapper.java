package de.hdm.bahnhofsteurung.bahnhof;

import java.util.Vector;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.hdm.bahnhofsteurung.resource.ResourceBahnhof;

@RestController
@RequestMapping(value="/bahnhof")
public class BahnhofURIMapper {
	private Vector<OrgaBahnhof> bahnhoefe = new Vector<OrgaBahnhof>();
	
	@RequestMapping(value="/", method=RequestMethod.PUT,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public void addBahnhof(@RequestBody ResourceBahnhof resourceBahnhof,@RequestBody int anzahlGleise){
		bahnhoefe.add(new OrgaBahnhof(resourceBahnhof,anzahlGleise));
		
	}
	
}
