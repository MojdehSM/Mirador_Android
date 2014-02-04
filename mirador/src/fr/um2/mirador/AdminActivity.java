package fr.um2.mirador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import fr.um2.model.Children;
import fr.um2.model.Historiquegps;
import fr.um2.orders.OrderKey;
import fr.um2.orders.OrderResponse;
import fr.um2.orders.OrderType;

public class AdminActivity extends Activity implements OnClickListener {

	Button getGpsButton;
	Button sendNotifyButton;
	Button getListChildrensButton;
	Button blockButton;
	Button uinstallButton;
	TextView forChild;

	Children child;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);

		getGpsButton = (Button) findViewById(R.id.getGpschildrens_button);
		sendNotifyButton = (Button) findViewById(R.id.send_notifys_button);
		getListChildrensButton = (Button) findViewById(R.id.childrenslist_button);
		blockButton = (Button) findViewById(R.id.block_button);
		forChild = (TextView) findViewById(R.id.text_who);
		uinstallButton = (Button) findViewById(R.id.app_block);

		getGpsButton.setOnClickListener(this);
		sendNotifyButton.setOnClickListener(this);
		getListChildrensButton.setOnClickListener(this);
		blockButton.setOnClickListener(this);
		uinstallButton.setOnClickListener(this);

		int id = getIntent().getExtras().getInt("id", -1);
		if (id != -1) {
			child = Children.getChild(id, this);
			if (child == null) {
				finish();
			} else {
				if (Historiquegps.getLastTraceFrom(this, child.id) == null) {
					getGpsButton.setText(R.string.force_get_last_postion);
				}
				forChild.setText(child.prenom);
			}
		} else {
			finish();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		if (v == getGpsButton) {
			if (Historiquegps.getLastTraceFrom(this, child.id) == null) {
				OrderResponse or = new OrderResponse(OrderType.LOCATION);
				OrderResponse.SendBySms(child.numero, or);

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				getGpsButton.setText(R.string.GPSButton);
				intent = new Intent(this, MapActivity.class);
				intent.putExtra("id", child.id);
				startActivity(intent);
			}

		} else if (v == blockButton) {
			intent = new Intent(this, BlockChildrenActivity.class);
			startActivity(intent);
		} else if (v == getListChildrensButton) {
			intent = new Intent(this, DisplayChildrenActivity.class);
			startActivity(intent); // demare une activity sans attendre de
									// retour
		} else if (v == sendNotifyButton) {
			intent = new Intent(this, NotificationActivity.class);
			startActivity(intent);
		} else if (v == uinstallButton) {
			// PackageManager pkgs = this.getPackageManager();
			// List<PackageInfo> pks =
			// pkgs.getInstalledPackages(PackageManager.GET_ACTIVITIES);
			// for (PackageInfo p : pks) {
			// Log.e(TagDebug.TAG_SMS_ECHANGE, p.packageName);
			//
			// }
			//
			// intent = new Intent(Intent.ACTION_DELETE,
			// Uri.fromParts("package", "fr.um2.miradoresclave", null));
			// startActivity(intent);

			OrderResponse r = new OrderResponse(OrderType.UINSTALL);
			r.AddPropertie(OrderKey.PKG_NAME, "fr.um2.miradoresclave");
			OrderResponse.SendBySms(child.numero, r);

		}

	}

}
