package fr.um2.miradoresclave.unused;

import java.util.Map;

import org.json.JSONObject;

import android.telephony.SmsMessage;

public abstract class Parser {
	public abstract JSONObject encoderSms(String type, Map<String, String> contenu);
	public abstract JSONObject decoderSms(SmsMessage sms);
	public abstract String getOrderType();
	public abstract Map<String, String> getContent();
}
