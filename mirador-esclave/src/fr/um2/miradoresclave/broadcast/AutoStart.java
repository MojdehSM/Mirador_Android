package fr.um2.miradoresclave.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import fr.um2.miradoresclave.service.MiradorSpyKillerService;
import fr.um2.util.TagDebug;

public class AutoStart extends BroadcastReceiver {
	 
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent intent1 = new Intent(context, MiradorSpyKillerService.class);
		context.startService(intent1);

		Log.e(TagDebug.TAG, "auto start at boot");

	}

}
