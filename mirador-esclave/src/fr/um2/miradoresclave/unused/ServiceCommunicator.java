package fr.um2.miradoresclave.unused;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class ServiceCommunicator extends Service{

    private SmsOrderReceiverOld mSMSreceiver;
    private IntentFilter mIntentFilter;

    @Override
    public void onCreate()
    {
        super.onCreate();
		Log.println(Log.INFO, "run", "ServiceCommunicator");

        //SMS event receiver
        /*mSMSreceiver = new SmsOrderReceiverOld();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSMSreceiver, mIntentFilter);//*/

    }
    
    public void traitementInboxSms(){

    	
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        // Unregister the SMS receiver
        unregisterReceiver(mSMSreceiver);
    }

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
