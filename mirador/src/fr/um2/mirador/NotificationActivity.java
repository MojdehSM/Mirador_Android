package fr.um2.mirador;

import java.util.HashMap;
import java.util.Map;

import fr.um2.model.Children;
import fr.um2.orders.OrderKey;
import fr.um2.orders.OrderResponse;
import fr.um2.orders.OrderType;
import android.os.Bundle;
import android.app.Activity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class NotificationActivity extends Activity  implements OnClickListener {
	
	private RadioGroup rg;
	private Button notifyButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		rg = (RadioGroup) findViewById(R.id.radioGroupChilds);
		// lire dans BD: prenom enfant / numero enfant
		String[] childs = Children.childrenToString(getApplicationContext());

		for (int i = 0; i < childs.length; i++) {
			RadioButton rb = new RadioButton(this);
			Integer id = Integer.parseInt(childs[i].subSequence(0,
					childs[i].indexOf(" ")).toString());
			String text = childs[i].subSequence(childs[i].indexOf(" "),
					childs[i].length()).toString();
			rb.setId(id);
			rb.setText(text);
			rg.addView(rb);
		}
		if (rg.getChildCount() > 0) {
			rg.check(rg.getChildAt(0).getId());
		}
		notifyButton = (Button) findViewById(R.id.button_send_notification);
		notifyButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Integer child = ((RadioButton) this.findViewById(rg.getCheckedRadioButtonId())).getId();
		String phone = Children.getChild(child, getApplicationContext()).numero;
		String text = ((EditText) findViewById(R.id.editTextNotification)).getText().toString();
		OrderResponse or = new OrderResponse(OrderType.NOTIFICATION);
		or.AddPropertie(OrderKey.MESSAGE, text);
		OrderResponse.SendBySms(phone,or); //"0644880144" j'ai remplace par numero d'enfant
		finish();
	}
	
}
