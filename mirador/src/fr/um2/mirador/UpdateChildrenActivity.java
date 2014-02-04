package fr.um2.mirador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fr.um2.model.Children;

public class UpdateChildrenActivity extends Activity{
	
	EditText nomEditText;
	EditText prenomEditText;
	EditText numeroEditText;
	Children child;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.update_enfant);
		
		nomEditText = (EditText) findViewById(R.id.editTextNom);
		prenomEditText = (EditText) findViewById(R.id.editTextPrenom);
		numeroEditText = (EditText) findViewById(R.id.editTextNumero);
		
		//TODO : recuperer informations enfants depuis bundle
		Bundle bundle = getIntent().getExtras();
		child = Children.getChild(bundle.getInt("id"), getApplicationContext());
		
		nomEditText.setText(child.nom);
		prenomEditText.setText(child.prenom);
		numeroEditText.setText(child.numero);

	}
	
	
	
	public void cancel(View v){
		Toast.makeText(this, R.string.mise_a_jour_annule, Toast.LENGTH_SHORT).show();
		finish();
	}
	
	public void updateChildren(View v){
	Boolean champValide = true;
		
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
		
//		if (!numero.getText().toString().matches("([+][0-9][0-9][0-9]( [0-9][0-9])+)|([0-9]+)")){
//			Toast.makeText(this, R.string.numero_tel, Toast.LENGTH_SHORT).show();
//			champValide = false;
//		}
		
		
		child.nom = nomEditText.getText().toString();
		child.prenom = prenomEditText.getText().toString();
		child.numero = numeroEditText.getText().toString();
		
		if(champValide){
		child.save(getApplicationContext());
		
		Toast.makeText(this, R.string.mise_a_jour, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, DisplayChildrenActivity.class);
		startActivity(intent);
		finish();}
	}
	
	@Override
	public void onBackPressed(){
		Intent intent = new Intent(this, DisplayChildrenActivity.class);
		startActivity(intent);//demare une activity sans attendre de retour
		this.finish();
	}

}
