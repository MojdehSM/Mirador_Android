package fr.um2.miradoresclave.unused;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsOrderReceiverOld extends BroadcastReceiver {
	// Get the object of SmsManager

//	final SmsManager sms = SmsManager.getDefault();
	public ArrayList<SmsMessage> inboxSms = new ArrayList<SmsMessage>();
	public Parser p = new ParserSms();
	public Traitement t = new TraitementSms();

	
	//Teste : simule provisoirememnt la bdd
	public ArrayList<String> numeroParent = new ArrayList<String>() {{ add("0675343889"); add("1234");}};
	public String contenu = "<service>";

	/**
	 * Receive  SMSs
	 */
	public void onReceive(Context context, Intent intent) {
		
		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras();

		try {

			if (bundle != null) {

				final Object[] pdusObj = (Object[]) bundle.get("pdus");
				for (int i = 0; i < pdusObj.length; i++) {
					SmsMessage currentMessage = SmsMessage
							.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage
							.getDisplayOriginatingAddress();
					String senderNum = phoneNumber;
					String message = currentMessage.getDisplayMessageBody();	
					//Test Numero + Contenu
					if (numeroParent.contains(senderNum)){
						Log.println(Log.INFO, "Interception SMS", "senderNum : "+senderNum+" contenu : "+message);
						//rajouter à la collection des message à traiter
						inboxSms.add(currentMessage);
					}	
					this.traitementInbox();
				}
			}

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" + e);

		}
	}
	public boolean isMsgInbox(){
		if (inboxSms.size()>0)
			return true;
		return false;
	}
	
	public SmsMessage getMessage(){
		SmsMessage msg = inboxSms.get(inboxSms.size()-1);
		inboxSms.remove(inboxSms.size()-1);
		return msg;
	}
	
	public void traitementInbox(){
		SmsMessage sms = getMessage();
		
		Map<String, String> contenu = new HashMap<String, String>();
		contenu.put("lat", "1");
		contenu.put("long", "2");
		System.out.println(p.encoderSms("localisation", contenu).toString());
		p.decoderSms(sms);
		t.setType(p.getOrderType());
		t.setContent(p.getContent());
		t.actionCall();
		
	}

}
