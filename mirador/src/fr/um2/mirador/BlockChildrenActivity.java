package fr.um2.mirador;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import fr.um2.model.Children;
import fr.um2.orders.OrderKey;
import fr.um2.orders.OrderResponse;
import fr.um2.orders.OrderType;

@SuppressLint("ValidFragment")
public class BlockChildrenActivity extends Activity implements OnClickListener {
	
	private RadioGroup rg;
	private Button blockButton;
	static String startTime;
	static String endTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_block_children);
		rg = (RadioGroup)findViewById(R.id.radioGroup_childrens);
		
		//lire dans BD: prenom enfant / numero enfant
		String[] childs = Children.childrenToString(getApplicationContext());
		
		for (int i=0; i<childs.length; i++){
			RadioButton rb = new RadioButton(this);
			Integer id = Integer.parseInt(childs[i].subSequence(0, childs[i].indexOf(" ")).toString());
			String text = childs[i].subSequence(childs[i].indexOf(" "), childs[i].length()).toString();
			rb.setId(id);
			rb.setText(text);
	        rg.addView(rb);
		}
		if (rg.getChildCount()>0){
			rg.check(rg.getChildAt(0).getId());
		}
		
		blockButton = (Button) findViewById(R.id.block_fonc_child_button);
		blockButton.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.block_children, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Integer child = ((RadioButton) this.findViewById(rg.getCheckedRadioButtonId())).getId();
		String phone = Children.getChild(child, getApplicationContext()).numero;
		
		if (((CheckBox) findViewById(R.id.checkBox_sms)).isChecked()) {
			// send block sms for sms
		}
		
		if (((CheckBox) findViewById(R.id.checkBox_call)).isChecked()) {
			// send block sms for call
		}
		if (((CheckBox) findViewById(R.id.checkBox_internet)).isChecked()) {
			// send block sms for internet
			if (startTime!=null && endTime!=null){
				OrderResponse or = new OrderResponse(OrderType.KILL);
				or.AddPropertie(OrderKey.WHAT, "INTERNET");
				or.AddPropertie(OrderKey.FROM, startTime);
				or.AddPropertie(OrderKey.TO, endTime);
				OrderResponse.SendBySms(phone,or);
				String msg = "Block internet " + child + " " + phone + " start: " + startTime + " end:  " + endTime;
				AlertDialog.Builder builder = new
				AlertDialog.Builder(BlockChildrenActivity.this);
				builder.setMessage(msg).setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						BlockChildrenActivity.this.finish();
					}
				  })
				.setCancelable(true) .show();
			}
		}

	}
	
	@SuppressLint("NewApi")
	public void startTime(View view) {
		DialogFragment newFragment = new TimePickerFragment(view);
		newFragment.show(getFragmentManager(), "timePicker");
	}
	
	@SuppressLint("NewApi")
	public void endTime(View view) {
		DialogFragment newFragment = new TimePickerFragment(view);
		newFragment.show(getFragmentManager(), "timePicker");
	}
	
	@SuppressLint({ "NewApi", "ValidFragment" })
	public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
		Button button;
		public TimePickerFragment(View view) {
			super();
			this.button = (Button) view;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			String time = hourOfDay + ":" + minute;
			button.setText(time);
			if (button.getId() == R.id.button_start_time) {
				startTime = time;
			} else if (button.getId() == R.id.button_end_time) {
				endTime = time;
			}
		}
	}

}
