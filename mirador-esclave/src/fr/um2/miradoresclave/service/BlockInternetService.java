package fr.um2.miradoresclave.service;

import java.util.Date;

import fr.um2.util.TagDebug;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

public class BlockInternetService extends Service{
	
	private ThreadStopWifi td = null;
	public static Integer from_hour = null;
	public static Integer from_min = null;
	public static Integer to_hour = null;
	public static Integer to_min = null;
	
	@Override
	public void onCreate() {
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (from_hour!=null && from_min!=null && to_hour!=null && to_min!=null){
			td=new ThreadStopWifi();
			Date d1 = new Date();
			d1.setHours(from_hour);
			d1.setMinutes(from_min);
			td.setDateFrom(d1);
			d1 = new Date();
			d1.setHours(to_hour);
			d1.setMinutes(to_min);
			td.setDateTo(d1);
			td.setService(this);
			td.start();
			Log.e(TagDebug.TAG, "Start Thread");
			System.out.println("Lancement BlockInternetService");
		}
		return Service.START_STICKY;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onDestroy() {
		if (td != null){
			td.stop();
			td = null;
		}
		from_hour = null;
		from_min = null;
		to_hour = null;
		to_min = null;
		Log.e(TagDebug.TAG, "Stop Thread wifi");
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class ThreadStopWifi extends Thread{
        
		private Date from = null;
		private Date to = null;
		private Service s = null;
		
		@Override
        public void run() {
            super.run();
            try{
            	if (from!=null && to!=null){
            		while (new Date().compareTo(from) < 0){
            			sleep(1*1000);
            		}
            		while (new Date().compareTo(to) < 0){
            			this.stopInternet();
            			sleep(1*1000);
            		}
            		this.startInternet();
            	}
            	if (s!=null){
            		s.stopSelf();
            	}
            }catch(Exception e){
                e.getMessage();
           }
        }
		
		public void setDateFrom(Date from){
			this.from = from;
		}
		
		public void setDateTo(Date to){
			this.to = to;
		}
		
		public void setService(Service s){
			this.s = s;
		}
		
		private void stopInternet() {
			WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			if (estWifiActif(wifiManager)){
				wifiManager.setWifiEnabled(false);
				Log.e(TagDebug.TAG, "l'arret de wifi a été lance");
			}else{
				//verifier 3G
			}
		}
		
		private void startInternet(){
			getNetworkPreference();
		}
		
		private void startWifi(){
			WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			if (!estWifiActif(wifiManager)){
				wifiManager.setWifiEnabled(true);
			}
		}
		
		private boolean estWifiActif(WifiManager wifiManager) {
			if (!wifiManager.isWifiEnabled()) {
				if (wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLING) {
					return false;
				}
			}
			return true;
		}
		
		private void getNetworkPreference() {
			ConnectivityManager m = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			int pref = m.getNetworkPreference();
			if (pref == ConnectivityManager.TYPE_WIFI){
				startWifi();
			}
			if (pref == ConnectivityManager.TYPE_MOBILE){
				//start
			}
		}
    }
}
