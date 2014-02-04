package fr.um2.miradoresclave.unused;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.telephony.SmsMessage;


public class ParserSms extends Parser {
	
	String type;
	Map<String, String> contenu = new HashMap<String, String>();
	

	@Override
	public String getOrderType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public Map<String, String> getContent() {
		// TODO Auto-generated method stub
		return contenu;
	}
	
	public void loadSms(JSONObject Jobj){
		
	}
	
	public JSONObject encoderSms(String type, Map<String, String> contenu){
		JSONObject jObj = new JSONObject();
			try {
				jObj.put("type", type);
				JSONObject contenuJson = new JSONObject();
				Set cles = contenu.keySet();
				Iterator it = cles.iterator();
				while (it.hasNext()){
				   Object cle = it.next(); 
				   Object valeur = contenu.get(cle);
				   contenuJson.put(cle.toString(), valeur.toString());
				}
				jObj.put("contenu", contenuJson);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return jObj;
	}
	
	public JSONObject decoderSms(SmsMessage sms){
		JSONObject jObj = new JSONObject();
		JSONObject jType = new JSONObject();
		JSONObject jContent;
		try {
			jObj = new JSONObject(sms.getDisplayMessageBody());
			this.type = jObj.getString("type");
			jContent = jObj.getJSONObject("contenu");
			Iterator<?> keys = jContent.keys();
		       while( keys.hasNext() ){
		            String key = (String)keys.next();
		            String value = jContent.getString(key);
		            contenu.put(key, value);
		       }
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jObj;
	}



}
