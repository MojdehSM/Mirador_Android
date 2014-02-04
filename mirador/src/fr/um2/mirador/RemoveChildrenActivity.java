package fr.um2.mirador;




import java.util.ArrayList;

import fr.um2.db.ControleParentalDbHelper;
import fr.um2.db.ControleParentalContrat.SlaveEntry;
import fr.um2.model.Children;
import fr.um2.model.ChildrenAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RemoveChildrenActivity extends Activity {
	
	private final static int MENU_ADD_CHILD = 0;
	private final static int MENU_DEL_CHILD = 1;
	private static ChildrenAdapter adapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//enregistre le layout
		setContentView(R.layout.display_enfant);
		
		ArrayList<Children> childrens = (ArrayList<Children>) Children.getAllChildren(getApplicationContext());
		adapter = new ChildrenAdapter(getApplicationContext(), childrens);
		final ListView listview = (ListView) findViewById(R.id.listView1);
		
		listview.setAdapter(adapter);
		
		OnItemClickListener listener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
				TextView tv_Id = (TextView)arg1.findViewById(R.id.TextView_Id);
				TextView tv_Nom = (TextView)arg1.findViewById(R.id.TextView_Nom);
				TextView tv_Prenom = (TextView)arg1.findViewById(R.id.TextView_Prenom);
				
				adapter.removeItem(arg2);
				listview.setAdapter(adapter);
				
				Children.delete(Integer.parseInt(tv_Id.getText().toString()), getApplicationContext());
				
				arg0.removeViews(arg2,1);
				
				
				Toast.makeText(getApplicationContext(),tv_Nom.getText().toString()+" "+tv_Prenom.getText().toString()+" "+R.string.supprime, Toast.LENGTH_SHORT).show();
			}
		};
		
		listview.setOnItemClickListener(listener);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(0,MENU_ADD_CHILD, Menu.NONE, R.string.ajouter_enfant);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case MENU_ADD_CHILD:
			Intent intent = new Intent(this, AddChildrenActivity.class);
			startActivity(intent);//demare une activity sans attendre de retour			
			break;

		default:
			break;
		}
		
		return true;
	}
	
/*	@Override
	public void onBackPressed(){
		Intent intent = new Intent(this, DisplayChildrenActivity.class);
		startActivity(intent);//demare une activity sans attendre de retour	
	}*/

}
