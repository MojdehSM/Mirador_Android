package fr.um2.mirador;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import fr.um2.model.Children;
import fr.um2.model.ChildrenAdapter;
import fr.um2.orders.OrderResponse;
import fr.um2.orders.OrderType;

public class DisplayChildrenActivity extends Activity implements OnItemClickListener, OnLongClickListener {

	private final static int MENU_ADD_CHILD = 0;
	private final static int MENU_DEL_CHILD = 1;

	List<Children> childrens;
	ListView listview;
	ChildrenAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// enregistre le layout
		setContentView(R.layout.display_enfant);

		if (childrens == null) {
			childrens = Children.getAllChildren(getApplicationContext());
			adapter = new ChildrenAdapter(getApplicationContext(), childrens);

			listview = (ListView) findViewById(R.id.listView1);

			listview.setAdapter(adapter);

			listview.setOnItemClickListener(this);

			// for on click listner
			listview.setOnCreateContextMenuListener(this);
			listview.setOnLongClickListener(this);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ADD_CHILD, Menu.NONE, R.string.ajouter_enfant);
		menu.add(0, MENU_DEL_CHILD, Menu.NONE, R.string.supprimer_enfant);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ADD_CHILD:
			Intent intent = new Intent(this, AddChildrenActivity.class);
			startActivity(intent);// demare une activity sans attendre de retour
			finish();
			break;
		case MENU_DEL_CHILD:
			Intent intent1 = new Intent(this, RemoveChildrenActivity.class);
			startActivity(intent1);// demare une activity sans attendre de
			break;

		default:
			break;
		}

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Intent intent = new Intent(this, AdminActivity.class);
		intent.putExtra("id", childrens.get(arg2).id);
		startActivity(intent);
	}

	@Override
	public boolean onLongClick(View arg0) {
		arg0.showContextMenu();
		return false;
	}

	class Action {
		public static final int MAP = 1;
		public static final int FORCE = 2;
		public static final int UPDATE = 3;
		public static final int REMOVE = 4;

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("Options");
		menu.add(0, Action.UPDATE, 0, R.string.update_children);
		menu.add(0, Action.REMOVE, 0, R.string.remove_children);

	}

	// @Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Children selected = childrens.get(info.position);

		switch (item.getItemId()) {
		case Action.MAP: {
			// afficher sur la carte
			Intent intent = new Intent(this, MapActivity.class);
			intent.putExtra("id", selected.id);
			startActivity(intent);
			break;
		}
		case Action.FORCE: {
			// force to get the last location
			OrderResponse.SendBySms(selected.numero, new OrderResponse(OrderType.LOCATION));
			break;
		}

		case Action.UPDATE: {
			// Update Children
			Intent intent = new Intent(this, UpdateChildrenActivity.class);
			intent.putExtra("id", selected.id);
			startActivity(intent);
			this.finish();
			break;
		}

		case Action.REMOVE: {
			// Remove children
			if (childrens.remove(selected)) {
				adapter.notifyDataSetChanged();
				/// TODO  delete from database
				//Children.delete(selected.id, this);
				
				Toast.makeText(this, "deleted children " + selected.id + " " + selected.nom, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Not DELETE " + selected.id + " " + selected.nom, Toast.LENGTH_SHORT).show();
			}

			break;
		}

		default:
			break;
		}

		return super.onContextItemSelected(item);
	}

	
}
