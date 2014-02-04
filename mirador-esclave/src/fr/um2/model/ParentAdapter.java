package fr.um2.model;

import java.util.List;



import fr.um2.miradoresclave.R;



import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;



public class ParentAdapter extends BaseAdapter{

	private List<Parent> parents;
	    	
	//Le contexte dans lequel est présent notre adapter
	private Context mContext;
	    	
	//Un mécanisme pour gérer l'affichage graphique depuis un layout XML
	private LayoutInflater mInflater;
	
	

	public ParentAdapter(Context context, List<Parent> aListP) {
	  mContext = context;
	  parents = aListP;
	  mInflater = LayoutInflater.from(mContext);
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return parents.size();
	}



	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return parents.get(arg0);
	}



	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	public void removeItem(int index){
		parents.remove(index);
		this.notifyDataSetChanged();
	}
	
	public void addItem(Parent p ){
		parents.add(p);
	}

	public void setItem(Parent p , int index){
		parents.set(index, p);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  LinearLayout layoutItem;
		  if (convertView == null) {
		    layoutItem = (LinearLayout) mInflater.inflate(R.layout.parent_layout, parent, false);
		  } else {
		  	layoutItem = (LinearLayout) convertView;
		  }
		  
		  TextView tv_Id = (TextView)layoutItem.findViewById(R.id.TextView_Id);
		  TextView tv_Nom = (TextView)layoutItem.findViewById(R.id.TextView_Nom);
		  TextView tv_Prenom = (TextView)layoutItem.findViewById(R.id.TextView_Prenom);
		        
		  tv_Id.setText(String.valueOf(parents.get(position).id));
		  tv_Nom.setText(parents.get(position).nom);
		  tv_Prenom.setText(parents.get(position).prenom); 

		  return layoutItem;
	}

}
