package fr.um2.model;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.um2.mirador.R;

public class ChildrenAdapter extends BaseAdapter{

	private List<Children> childrens;
	    	
	//Le contexte dans lequel est présent notre adapter
	private Context mContext;
	    	
	//Un mécanisme pour gérer l'affichage graphique depuis un layout XML
	private LayoutInflater mInflater;
	
	

	public ChildrenAdapter(Context context, List<Children> aListP) {
	  mContext = context;
	  childrens = aListP;
	  mInflater = LayoutInflater.from(mContext);
	}
	
	
 
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return childrens.size();
	}



	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return childrens.get(arg0);
	}



	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  LinearLayout layoutItem;
		  if (convertView == null) {
		    layoutItem = (LinearLayout) mInflater.inflate(R.layout.enfant_layout, parent, false);
		  } else {
		  	layoutItem = (LinearLayout) convertView;
		  }
		  
		  TextView tv_Id = (TextView)layoutItem.findViewById(R.id.TextView_Id);
		  TextView tv_Nom = (TextView)layoutItem.findViewById(R.id.TextView_Nom);
		  TextView tv_Prenom = (TextView)layoutItem.findViewById(R.id.TextView_Prenom);
		  TextView tv_PhoneNumber = (TextView)layoutItem.findViewById(R.id.TextView_phonenumber);
		  
		  
		  tv_Id.setText(childrens.get(position).id+"");
		  tv_Nom.setText(childrens.get(position).nom);
		  tv_Prenom.setText(childrens.get(position).prenom); 
		  tv_PhoneNumber.setText(childrens.get(position).numero); 

		  return layoutItem;
	}



	public void removeItem(int arg2) {
		childrens.remove(arg2);
	}

}
