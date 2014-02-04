package fr.um2.miradoresclave.unused;

import java.util.Map;

import org.json.JSONObject;

public abstract class Traitement {
	String t;
	Map<String, String> c;

	public abstract void setType(String t);
	public abstract void setContent(Map<String, String>  c);
	public abstract void actionCall();
}





