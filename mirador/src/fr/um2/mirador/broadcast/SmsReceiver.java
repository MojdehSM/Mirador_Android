package fr.um2.mirador.broadcast;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import fr.um2.model.Children;
import fr.um2.model.Historiquegps;
import fr.um2.orders.OrderKey;
import fr.um2.orders.OrderResponse;
import fr.um2.util.Dump;
import fr.um2.util.TagDebug;

public class SmsReceiver extends BroadcastReceiver {

	// Get the object of SmsManager
	final SmsManager smsReciver = SmsManager.getDefault();

	/**
	 * Receive SMSs
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

					OrderResponse order = OrderResponse.FromJSON(message);
					if (order != null && Children.testNumero(context, senderNum)) {
						Log.e(TagDebug.TAG, "REcived from " + phoneNumber
								+ "\n" + Dump.dumpToString(order) + " \n\n"
								+ message);

						responseFrom(context, senderNum, order);
					}
				}
			}

		} catch (Exception e) {
			Log.e(TagDebug.TAG, "Exception smsReceiver \n" + e.getStackTrace());
			e.printStackTrace();
		}
	}

	private void responseFrom(Context context, String senderNum,
			OrderResponse order) {
		switch (order.getWhat()) {
		case RESPONSE_LOCATION: {
			String lat = order.getPropertie(OrderKey.LATITUDE);
			String log = order.getPropertie(OrderKey.LONGITUDE);

			// insert into data base Lat --- Log
			Log.e(TagDebug.TAG,
					"From" + senderNum + " \n" + Dump.dumpToString(order));
			Children c = Children.getChildByPhone(senderNum, context);
			if (c != null) {
			 new Historiquegps(c.id, lat, log, Calendar.getInstance().getTime()).save(context);
			}
			break;
		}

		default:
			break;
		}

	}

}
