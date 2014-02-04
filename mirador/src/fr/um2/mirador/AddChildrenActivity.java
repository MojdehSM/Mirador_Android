package fr.um2.mirador;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fr.um2.model.Children;

public class AddChildrenActivity extends Activity{
	
	static SQLiteDatabase db;
	Cursor cursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.add_enfant);
	}
			
	public void addChildren(View view){
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
		
//		if (!numero.getText().toString().matches("(\\+[0-9][0-9][0-9]( [0-9][0-9])+)|([0-9]+)")){
//			Toast.makeText(this, R.string.numero_tel, Toast.LENGTH_SHORT).show();
//			champValide = false;
//		}
		
		if(champValide){
		Children child = new Children(nom.getText().toString(), prenom.getText().toString(), numero.getText().toString());
		child.create(getApplicationContext());
		
		Toast.makeText(this, prenom.getText().toString()+" "+R.string.ajoute, Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent(this, DisplayChildrenActivity.class);
		startActivity(intent);
		finish();//ferme l'activité
		}
	}
	
	public void cancel(View v){
		finish();
	}
	
}
