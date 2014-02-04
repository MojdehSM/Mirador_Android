package fr.um2.miradoresclave.unused;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import fr.um2.miradoresclave.NotificationActivity;
import fr.um2.util.TagDebug;


public class NotificationService extends Service {
	
	public static Vibrator vibrator;
	public static String texteNotification;
	
	@Override
	public void onCreate() {
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO do something useful

		Log.e(TagDebug.TAG, "start Notification Service");

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		@SuppressWarnings("deprecation")
		Notification notification = new Notification(
				R.drawable.ic_notification_overlay, "Message!",
				System.currentTimeMillis());
		// notification.defaults |= Notification.DEFAULT_VIBRATE;
		// notification.vibrate = new long[] {0,200,100,200,100,200};
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;

		// On definit le titre de la notif
		String titreNotification = "Attention...";
		// On definit le texte qui caracterise la notif
		//String texteNotification = "Notification des parents!";

		// Le PendingIntent c'est ce qui va nous permettre d'atteindre notre
		// deuxieme Activity
		// ActivityNotification sera donc le nom de notre seconde Activity
		Intent intentNotification = new Intent(this, NotificationActivity.class);
		intentNotification.putExtra("message", texteNotification);

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intentNotification, 0);

		// On configure notre notification avec tous les parametres que l'on
		// vient de creer
		notification.setLatestEventInfo(this, titreNotification,
				texteNotification, pendingIntent);
		// On ajoute un style de vibration а notre notification
		// L'utilisateur est donc egalement averti par les vibrations de son
		// telephone
		// Ici les chiffres correspondent а 0sec de pause, 0.2sec de vibration,
		// 0.1sec de pause, 0.2sec de vibration, 0.1sec de pause, 0.2sec de
		// vibration
		// Vous pouvez bien entendu modifier ces valeurs а votre convenance

		// Enfin on ajoute notre notification et son ID а notre gestionnaire de
		// notification
		notificationManager.notify(1, notification);
		
		
		vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
		long pattern[] = { 0, 300, 200, 300, 500 };
		// start vibration with repeated count, use -1 if you don't want to
		// repeat the vibration
		vibrator.vibrate(pattern, 0);

		return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		if (vibrator != null)
			vibrator.cancel();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
