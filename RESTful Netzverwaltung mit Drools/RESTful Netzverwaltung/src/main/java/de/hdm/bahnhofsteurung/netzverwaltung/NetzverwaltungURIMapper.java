package de.hdm.bahnhofsteurung.netzverwaltung;

import java.util.Vector;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.hdm.bahnhofsteurung.resource.*;

@RestController
@RequestMapping(value="/netzverwaltung")
public class NetzverwaltungURIMapper{
	
	@RequestMapping(value="/bahnhof", method=RequestMethod.PUT,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResourceBahnhof bahnhofAnlegen(@RequestBody ResourceBahnhof resourceBahnhof){
		return Netzverwaltung.netzverwaltung().addBahnhof(resourceBahnhof);
	}
	
	@RequestMapping(value="/bahnhof/{iD}/", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResourceBahnhof bahnhofAbfragen(@PathVariable("iD") long iD){
		return Netzverwaltung.netzverwaltung().getBahnhof(iD);
	}
	
	@RequestMapping(value="/bahnhof/{iD}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void bahnhofLoeschen(@PathVariable("iD") long iD){
		
	}
	
	@RequestMapping(value="/bahnhof", method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Vector<ResourceBahnhof> getAllBahnhoefe(){
		return Netzverwaltung.netzverwaltung().getBahnhoefe();
	}
}
