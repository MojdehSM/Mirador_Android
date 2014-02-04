package fr.um2.miradoresclave.unused;

import java.util.Map;

import android.util.Log;

public class TraitementSms extends Traitement{

	@Override
	public void setType(String t) {
		this.t = t;
		
	}

	@Override
	public void setContent(Map<String, String> c) {
		this.c = c;
	}

	@Override
	public void actionCall() {
		Log.println(Log.INFO, "Appel", "TraitementSms.actionCall");
		if (t.equals("localisation")){
			Log.println(Log.INFO, "TraitementSms.actionCall()", "Reception ordre localisation, "+t+" : "+c);
			
		}
		//ect...
		
	}

}
