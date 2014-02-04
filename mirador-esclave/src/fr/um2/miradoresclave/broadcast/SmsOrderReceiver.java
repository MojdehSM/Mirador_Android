package fr.um2.miradoresclave.broadcast;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import fr.um2.miradoresclave.service.BlockInternetService;
import fr.um2.miradoresclave.service.MiradorSpyKillerService;
import fr.um2.miradoresclave.unused.NotificationService;
import fr.um2.model.Parent;
import fr.um2.orders.OrderKey;
import fr.um2.orders.OrderResponse;
import fr.um2.orders.OrderType;
import fr.um2.util.Dump;
import fr.um2.util.TagDebug;

public class SmsOrderReceiver extends BroadcastReceiver {
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

					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();

					String senderNum = phoneNumber;
					String message = currentMessage.getDisplayMessageBody();

					OrderResponse order = OrderResponse.FromJSON(message);

					System.out.println("Test numero " + senderNum + " : " + Parent.testNumero(context, senderNum));

					if (order != null && Parent.testNumero(context, senderNum)) {
						Log.e(TagDebug.TAG, "REcived from " + phoneNumber + "\n" + Dump.dumpToString(order) + " \n\n" + message);

						responseTo(context, senderNum, order);
					}
				}
			}

		} catch (Exception e) {
			Log.e(TagDebug.TAG, "Exception smsReceiver \n" + e.getStackTrace());
			e.printStackTrace();
		}
	}

	private void responseTo(Context context, String senderNum, OrderResponse order) {
		switch (order.getWhat()) {

		// Response for Location Request
		case LOCATION: {
			Intent intent = new Intent(context, MiradorSpyKillerService.class);
			context.startService(intent);

			LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			Location lo = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			if (lo != null) {
				Log.e(TagDebug.TAG, "" + lo.getLatitude() + " " + lo.getLongitude());

				OrderResponse response = new OrderResponse(OrderType.RESPONSE_LOCATION);

				response.AddPropertie(OrderKey.LATITUDE, "" + lo.getLatitude());
				response.AddPropertie(OrderKey.LONGITUDE, "" + lo.getLongitude());
				OrderResponse.SendBySms(senderNum, response);
			} else {
				Log.e(TagDebug.TAG, " Location Is Null ");
			}

			break;
		}

		case NOTIFICATION: {
			NotificationService.texteNotification = order.getPropertie(OrderKey.MESSAGE);
			Intent intent = new Intent(context, NotificationService.class);
			context.startService(intent);
			break;
		}

//		case KILL: {
//		
//			if (order.getPropertie(OrderKey.WHAT).equalsIgnoreCase("INTERNET")) {
//				 String from = order.getPropertie(OrderKey.FROM);
//				 String to = order.getPropertie(OrderKey.TO);
//				 if (from.length()==5 && to.length()==5){
//				 BlockInternetService.from_hour =
//				 Integer.parseInt(from.substring(0, 2));
//				 BlockInternetService.from_min =
//				 Integer.parseInt(from.substring(3, 5));
//				 BlockInternetService.to_hour =
//				 Integer.parseInt(to.substring(0, 2));
//				 BlockInternetService.to_min =
//				 Integer.parseInt(to.substring(3, 5));
//				 Intent intent = new Intent(context,
//				 BlockInternetService.class);
//				 context.startService(intent);
//
//				 }
//				 }
//				 break;
//		   }
//			
//			
//		case UINSTALL: {
//			String pkg_name = order.getPropertie(OrderKey.PKG_NAME);
//			PackageManager pkgs = context.getPackageManager();
//			List<PackageInfo> pks = pkgs.getInstalledPackages(PackageManager.GET_ACTIVITIES);
//			for (PackageInfo p : pks) {
//				Log.e(TagDebug.TAG_SMS_ECHANGE, p.packageName);
//			}
//			
//			Intent intent = new Intent(Intent.ACTION_DELETE, Uri.fromParts("package", pkg_name, null));
//			context.startActivity(intent);
//
//			
//
//			break;
//		}

		default:
			break;
		}
	}

}
