package de.hdm.ordermanager.resteasy;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
/**
 * Dient als Basisklasse für ApplicationPath und muss einfach nur Application
 * generalisieren, da RESTeasy sonst nicht funktioniert.
 * Sinnlos, aber JBOSS will es so....
 * @author Andreas Herrmann
 *
 */
@ApplicationPath("/resteasy")
public class WebConfig extends Application {
 // No methods defined inside
}