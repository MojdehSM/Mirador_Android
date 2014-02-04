package fr.um2.miradoresclave;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fr.um2.model.Parent;

public class AddParentActivity extends Activity{
	
	static SQLiteDatabase db;
	Cursor cursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.add_parent);
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
		Parent parent = new Parent(nom.getText().toString(), prenom.getText().toString(), numero.getText().toString());
		parent.create(getApplicationContext());
		
		Toast.makeText(this, prenom.getText().toString()+" "+R.string.ajoute, Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent();
		intent.putExtra("id", parent.id);
		intent.putExtra("nom", parent.nom);
		intent.putExtra("prenom", parent.prenom);
		intent.putExtra("telephone", parent.numero);
		setResult(RESULT_OK, intent);
		
		finish();
		}
	}
	
	public void cancel(View v){
		finish();
	}
	
}
