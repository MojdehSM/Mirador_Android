package fr.um2.miradoresclave;

import fr.um2.model.Password;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BlockPasswordActivity  extends Activity implements OnClickListener {
	
	private Boolean block;
	private String password;
	private Button ok;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_block_password);
		Intent intent = this.getIntent();
		block = intent.getExtras().getBoolean("block_state");
		password = intent.getExtras().getString("password");
		ok = (Button) findViewById(R.id.save_password_button);
		ok.setOnClickListener(this);
		if (block){
			((TextView)findViewById(R.id.textPassEdit)).setText(R.string.text_unblock_password);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.block_password, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String pass = ((EditText)findViewById(R.id.editPassword)).getText().toString();
		if (block){
			if (password.equals(pass)){
				//set Etat bloque (false) et password "" in DB
				Password.getPassword(getApplicationContext()).setPassword("");
				Intent returnIntent = new Intent();
				setResult(RESULT_OK, returnIntent); 				
				finish();
			}else{
				AlertDialog.Builder builder = new AlertDialog.Builder(BlockPasswordActivity.this);
				builder.setTitle(R.string.password)
				.setMessage(R.string.password_not_correct)
				.setPositiveButton("OK", null)
				.setCancelable(true)
				.show();
				((EditText)findViewById(R.id.editPassword)).setText("");
				Intent returnIntent = new Intent();
				setResult(RESULT_CANCELED, returnIntent);
				finish();
			} 
		}else{
			//set Etat bloque (true) et password "***" in DB
			try{
				new Password(pass).create(getApplicationContext());
				Intent returnIntent = new Intent();
				setResult(RESULT_CANCELED, returnIntent);  
				//Password.getPassword(getApplicationContext()).setPassword(pass);
			}catch(Exception ex){
			}
		}
		finish();
	}


}
