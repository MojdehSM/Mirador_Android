package fr.um2.miradoresclave.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import fr.um2.model.Parent;
import fr.um2.orders.OrderKey;
import fr.um2.orders.OrderResponse;
import fr.um2.orders.OrderType;
import fr.um2.util.Dump;
import fr.um2.util.TagDebug;
//envoie periodique à parents ma position (envoie à map)
public class LocationSpy implements LocationListener {

	static private Date last = null;

	private Context context = null;

	public LocationSpy(Context context) {
		this.context = context;

	}

	/**
	 * Send Location
	 * 
	 * @param lo
	 */
	private void send(Location lo) {
		List<Parent> parents = Parent.GetAllParents(context);
		if (!parents.isEmpty()) {

			OrderResponse response = new OrderResponse(OrderType.RESPONSE_LOCATION);
			response.AddPropertie(OrderKey.LATITUDE, "" + lo.getLatitude());
			response.AddPropertie(OrderKey.LONGITUDE, "" + lo.getLongitude());

			// send to all parent
			for (Parent p : parents) {
				Log.e(TagDebug.TAG, " message sended to " + p.numero + " \n" + Dump.dumpToString(response));
				OrderResponse.SendBySms(p.numero, response);
			}
		}

	}

	@Override
	public void onLocationChanged(Location location) {
		if (last == null) {
			last = Calendar.getInstance().getTime();
			Log.e(TagDebug.TAG, location.getLatitude() + " " + location.getLongitude());
			send(location);
		} else {
			if (Calendar.getInstance().getTimeInMillis() - last.getTime() > 120000) {
				Log.e(TagDebug.TAG, " AFTER " + location.getLatitude() + " " + location.getLongitude());
				send(location);
			} else {
				Log.e(TagDebug.TAG, " No Sended Because of Time " + location.getLatitude() + " " + location.getLongitude());
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
