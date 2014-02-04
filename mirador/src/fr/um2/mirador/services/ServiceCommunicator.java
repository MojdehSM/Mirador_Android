package fr.um2.mirador.services;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import fr.um2.model.Children;
import fr.um2.orders.OrderResponse;
import fr.um2.orders.OrderType;
import fr.um2.util.TagDebug;

public class ServiceCommunicator extends Service {

	Timer timer = new Timer();
	private static final int TWO_MINUTE = 2000;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TagDebug.TAG_SERVICE_COMMINICATOR, "create");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TagDebug.TAG_SERVICE_COMMINICATOR, "create");
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// do somthinc periodeclly
				List<Children> childrens = Children.getAllChildren(getApplicationContext());
				for(Children child : childrens){
					OrderResponse.SendBySms(child.numero,new OrderResponse(OrderType.LOCATION));
				}
			}
		}, TWO_MINUTE, TWO_MINUTE);

		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
