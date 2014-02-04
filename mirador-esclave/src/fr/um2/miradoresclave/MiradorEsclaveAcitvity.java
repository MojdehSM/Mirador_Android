package fr.um2.miradoresclave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import fr.um2.miradoresclave.service.MiradorSpyKillerService;
import fr.um2.miradoresclave.unused.ServiceCommunicator;
import fr.um2.util.TagDebug;

public class MiradorEsclaveAcitvity extends Activity implements OnClickListener {
	
	Button startService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mirador_esclave_acitvity);
		Log.e(TagDebug.TAG," Mirador Esclave Run");
		startService =(Button) this.findViewById(R.id.startservicebutton);
		startService.setOnClickListener(this);
		
		//Activation service ecoute des sms reçut
		//Intent intent = new Intent(this, ServiceCommunicator.class);
		//startService(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mirador_esclave_acitvity, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		if(arg0 ==this.findViewById(R.id.startservicebutton)){
			Log.e(TagDebug.TAG, "start service");
			Intent intent1 = new Intent(this,MiradorSpyKillerService.class);
			this.startService(intent1);
			
		}
		
	}

}
