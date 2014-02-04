package fr.um2.util;

import com.google.gson.GsonBuilder;


/**
 * 
 * @author Mojdeh
 * 
 */
public class Dump {
	public static String dumpToString(Object c) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(c);
	}
}
