package fr.um2.miradoresclave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fr.um2.model.Parent;

public class UpdateParentActivity extends Activity{
	
	EditText nomEditText;
	EditText prenomEditText;
	EditText numeroEditText;
	Parent parent;
	int index;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.update_parent);
		
		nomEditText = (EditText) findViewById(R.id.editTextNom);
		prenomEditText = (EditText) findViewById(R.id.editTextPrenom);
		numeroEditText = (EditText) findViewById(R.id.editTextNumero);
		
		Bundle bundle = getIntent().getExtras();
		index = bundle.getInt("index");
		int idParent = bundle.getInt("id");
		String nomParent = bundle.getString("nom");
		String prenomParent = bundle.getString("prenom");
		String numParent = bundle.getString("telephone");
		
//		System.out.println("idParent "+idParent);
		
		parent = new Parent(idParent, nomParent, prenomParent, numParent);
		
		nomEditText.setText(parent.nom);
		prenomEditText.setText(parent.prenom);
		numeroEditText.setText(parent.numero);

	}
	
	
	
	public void cancel(View v){
		Toast.makeText(this, R.string.mise_a_jour_annule, Toast.LENGTH_SHORT).show();
		setResult(RESULT_CANCELED);
		finish();
	}
	
	public void updateChildren(View v){
		
		Boolean champValide = true;
		
		parent.nom = nomEditText.getText().toString();
		parent.prenom = prenomEditText.getText().toString();
		parent.numero = numeroEditText.getText().toString();
		
		EditText nom = (EditText) findViewById(R.id.editTextNom);
		EditText prenom = (EditText) findViewById(R.id.editTextPrenom);
		EditText numero = (EditText) findViewById(R.id.editTextNumero);
		
		if (nom.getText().toString().matches("")){
			Toast.makeText(this, R.string.nom, Toast.LENGTH_SHORT).show();
			champValide = false;
		}             
		
		if (prenom.getText().toString().matches("")){
			Toast.makeText(this, R.string.prenom, Toast.LENGTH_SHORT).show();
			champValide = false;
		}
		
//		if (!numero.getText().toString().matches("[+](\\+[0-9][0-9][0-9]( [0-9][0-9])+)|([0-9]+)")){
//			Toast.makeText(this, R.string.numero_tel, Toast.LENGTH_SHORT).show();
//			champValide = false; 
//		}
		
		if(champValide){
		parent.save(getApplicationContext());
		
		Toast.makeText(this, R.string.mise_a_jour, Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent();
		intent.putExtra("id", String.valueOf(parent.id));
		intent.putExtra("nom", parent.nom);
		intent.putExtra("prenom", parent.prenom);
		intent.putExtra("telephone", parent.numero);
		intent.putExtra("index", index);
		setResult(RESULT_OK, intent);
		
		finish();}
	}
	
//	@Override
//	public void onBackPressed(){
//		Intent intent = new Intent(this, DisplayParentActivity.class);
//		startActivity(intent);//demare une activity sans attendre de retour	
//	}

}
