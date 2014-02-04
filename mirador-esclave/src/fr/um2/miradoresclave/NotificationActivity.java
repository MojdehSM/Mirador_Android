package fr.um2.miradoresclave;

import fr.um2.miradoresclave.unused.NotificationService;
import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class NotificationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		// On créer un TextView en Java
		Intent intent = this.getIntent();
		String message = intent.getExtras().getString("message");
		
		TextView txt = (TextView)findViewById(R.id.notificationMessage3);
		txt.setText(message);
		
		
		// On supprime la notification de la liste de notification comme dans la
		// méthode cancelNotify de l'Activity principale
		NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
		notificationManager.cancel(1);
		
		NotificationService.vibrator.cancel();
		intent = new Intent(this, NotificationService.class);
		stopService(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}

}
