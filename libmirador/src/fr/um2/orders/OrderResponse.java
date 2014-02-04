package fr.um2.orders;

import java.util.HashMap;

import android.telephony.SmsManager;
import android.util.Log;

import com.google.gson.GsonBuilder;

import fr.um2.util.Dump;
import fr.um2.util.TagDebug;

/**
 * 
 * @author Mojdeh Class order/Response between parent to children
 */

public class OrderResponse {

	OrderType what = OrderType.UNKNOWN;
	HashMap<OrderKey, String> params = new HashMap<OrderKey, String>();

	/**
	 * Default Constructor For Gson
	 */
	public OrderResponse() {
	}

	public OrderResponse(OrderType what) {
		this.what = what;
	}

	/**
	 * Add Properties to params
	 * 
	 * @param key
	 * @param value
	 */
	
	public OrderType getWhat() {
		return what;
	}
	
	public HashMap<OrderKey, String> getParams() {
		return params;
	}

	public void AddPropertie(OrderKey key, String value) {
		params.put(key, value);
	}

	/**
	 * GEt Propertie if exist, defaultValue else
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getPropertie(OrderKey key, String defaultValue) {
		if (params.containsKey(key)) {
			return params.get(key);
		}
		return defaultValue;
	}

	/**
	 * Get Propertie if exist null else
	 * 
	 * @param key
	 * @return
	 */
	public String getPropertie(OrderKey key) {
		if (params.containsKey(key)) {
			return params.get(key);
		}
		return null;
	}

	/**
	 * Serialize in json
	 * 
	 * @return
	 */
	public String ToJSON() {
		return new GsonBuilder().create().toJson(this);
	}

	/**
	 * Unserialize from Json
	 * 
	 * @param order
	 * @return
	 */
	static public OrderResponse FromJSON(String order) {
		return new GsonBuilder().create().fromJson(order, OrderResponse.class);
	}
	
	/**
	 * send sms method helper
	 * @param phonenumber
	 * @param order
	 */
	public static void SendBySms(String phonenumber, OrderResponse order) {
		SmsManager def = SmsManager.getDefault();
		def.sendTextMessage(phonenumber, null, order.ToJSON(), null, null);
		Log.e(TagDebug.TAG,
				" message sended to " + phonenumber + " \n"
						+ Dump.dumpToString(order));
	}

}
