package fr.um2.miradoresclave;

import java.util.ArrayList;
import java.util.Date;

import fr.um2.model.Parent;
import fr.um2.model.ParentAdapter;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayParentActivity extends Activity implements OnItemClickListener, OnLongClickListener {
	
	private final static int CODE_UpdateParentActivity  = 4;
	private final static int CODE_AddParentActivity  = 5;
	private final static int MENU_ADD_CHILD = 0;
	private final static int MENU_DEL_CHILD = 1;
	private static boolean ACTION_DEL = false;
	private static ParentAdapter adapter;
	private static ListView listview;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ACTION_DEL = false;
		//enregistre le layout
		setContentView(R.layout.display_parent);
	
		
		
		ArrayList<Parent> childrens = (ArrayList<Parent>) Parent.getAllParent(getApplicationContext());
		adapter = new ParentAdapter(getApplicationContext(), childrens);
		listview = (ListView) findViewById(R.id.listView1);
		
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(this);
		
		// for on click listner
		listview.setOnCreateContextMenuListener(this);
		listview.setOnLongClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(0,MENU_ADD_CHILD, Menu.NONE, R.string.ajouter_enfant);
		menu.add(0,MENU_DEL_CHILD, Menu.NONE, R.string.supprimer_enfant);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		if (ACTION_DEL){
			menu.getItem(1).setTitle(R.string.mise_a_jour_enfant);
		}else{
			menu.getItem(1).setTitle(R.string.supprimer_enfant);
		}
		return super.onPrepareOptionsMenu(menu);		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch (item.getItemId()) {
		case MENU_ADD_CHILD:
			Intent intent = new Intent(DisplayParentActivity.this, AddParentActivity.class);
			startActivityForResult(intent, CODE_AddParentActivity);
			break;
		case MENU_DEL_CHILD:
			if(ACTION_DEL) {
				ACTION_DEL = false;
			}else{
				ACTION_DEL = true;
			}
			break;

		default:
			break;
		}
		
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		Parent p = (Parent) adapter.getItem(arg2);

		TextView tv_Id = (TextView)arg1.findViewById(R.id.TextView_Id);
		TextView tv_Nom = (TextView)arg1.findViewById(R.id.TextView_Nom);
		TextView tv_Prenom = (TextView)arg1.findViewById(R.id.TextView_Prenom);
		
		if(ACTION_DEL){
			//SUPPRESSION 
			listview.removeViewInLayout(arg1);
			listview.refreshDrawableState();
			
			adapter.removeItem(arg2);
			listview.setAdapter(adapter);
			
			Parent.delete(Integer.parseInt(tv_Id.getText().toString()), getApplicationContext());
			
			ACTION_DEL = false;
			Toast.makeText(getApplicationContext(), p.nom+" "+R.string.supprime, Toast.LENGTH_SHORT).show();
		
			
		}else{
			//DEMARE UpdateParentActivity
		
		Toast.makeText(getApplicationContext(), R.string.mise_a_jour+" : nom - "+tv_Nom.getText().toString(), Toast.LENGTH_SHORT).show();
	
		Intent intent = new Intent(this, UpdateParentActivity.class);
		intent.putExtra("index", arg2);
		intent.putExtra("id", p.id);
		intent.putExtra("nom", p.nom);
		intent.putExtra("prenom", p.prenom);
		intent.putExtra("telephone", p.numero);
		
	
		startActivityForResult(intent, CODE_UpdateParentActivity);}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case CODE_UpdateParentActivity:
			switch (resultCode) {
			case RESULT_OK:
				int index = data.getIntExtra("index", 1);
				int idParent = Integer.parseInt(data.getStringExtra("id"));
				String nomParent = data.getStringExtra("nom");
				String prenomParent = data.getStringExtra("prenom");
				String numParent =data.getStringExtra("telephone");
				Parent p = new Parent(idParent, nomParent, prenomParent, numParent);
				System.out.println("index : "+index);
				adapter.setItem(p, index);
				listview.setAdapter(adapter);
				break;

			default:
				break;
			}
			
			break;
		case CODE_AddParentActivity :
			if (resultCode == RESULT_OK){
				int idParent = data.getIntExtra("id", 1);
				String nomParent = data.getStringExtra("nom");
				String prenomParent = data.getStringExtra("prenom");
				String numParent =data.getStringExtra("telephone");
				Parent p = new Parent(idParent, nomParent, prenomParent, numParent);
				adapter.addItem(p);
				listview.setAdapter(adapter);
			}
			break;
		default:
			break;
		}

		}

	@Override
	public boolean onLongClick(View arg0) {
		arg0.showContextMenu();
		return false;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {	
		 menu.setHeaderTitle("Options");
         //menu.setHeaderIcon(android.R.drawable.ic_delete);
         menu.add(0, 1, 0, "TEST");
    }
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			
			break;

		default:
			break;
		}
		
		return super.onContextItemSelected(item);
	}
	
	
//	@Override
//	public void onBackPressed(){
//		Intent intent = new Intent(this, ChildrenActivity.class);
//		startActivity(intent);//demare une activity sans attendre de retour	
//	}
}
