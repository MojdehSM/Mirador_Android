package fr.um2.miradoresclave;

import fr.um2.model.Parent;
import fr.um2.model.Password;
import fr.um2.util.TagDebug;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChildrenActivity extends Activity implements OnClickListener {
	
	private Button parent_button;
	private Button block_unblock_button;
	private Boolean block;
	private String pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_children);
		parent_button = (Button) findViewById(R.id.edit_parent_button);
		block_unblock_button = (Button) findViewById(R.id.block_unblock_button);
		parent_button.setOnClickListener(this);
		block_unblock_button.setOnClickListener(this);
		/*
		 * lire l'attribut de BD "etat" (de blocage) et l'affectuer � block, si c'est true lire le mot de passe et = password;
		 */


		this.setInterface();
		/*Intent intent1 = new Intent(getApplicationContext(), MiradorSpyKillerService.class);
		startActivity(intent1);*/
		
	}
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.children, menu);
		return true;
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		if(v == parent_button){
			intent = new Intent(this, DisplayParentActivity.class);
			startActivity(intent);
//			NotificationService.texteNotification="Notification des parents!";
//			intent = new Intent(this, NotificationService.class);
//			startService(intent);
			
			
			
		}else{
			if (v == block_unblock_button){
				if (block_unblock_button.getText().equals("Unblock")){
				intent = new Intent(ChildrenActivity.this, BlockPasswordActivity.class);
				intent.putExtra("block_state", block);
				intent.putExtra("password", pass);
				startActivityForResult(intent, 1);
				}else{
					parent_button.setEnabled(false);
					block_unblock_button.setText(R.string.unblock);
				}

			}
		}
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			switch (resultCode) {
			case RESULT_OK:
					parent_button.setEnabled(true);
					block_unblock_button.setText(R.string.block);
				break;
			case RESULT_CANCELED:
				parent_button.setEnabled(false);
					block_unblock_button.setText(R.string.unblock);
				break;

			default:
				break;
			}
			
			break;

		default:
			break;
		}
//		setInterface();

	}
	
	public void setInterface(){
		block = false;
		try {
			Password p = Password.getPassword(getApplicationContext());
			System.out.println("mdp bdd : "+p.getPassword());
			if (p != null) {
				pass = p.getPassword();
			}
		} catch (Exception ex) {
		}
		if (pass==null){
			Log.e(TagDebug.TAG, "password NULL");
		}else{
			Log.e(TagDebug.TAG, pass.toString());
		}
		if (pass!=null && !pass.equals("")){
			block = true;
		}
		parent_button.setEnabled(!block);
		if (block){
			block_unblock_button.setText(R.string.unblock);
		}
	}

}
