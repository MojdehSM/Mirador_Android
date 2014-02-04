package fr.um2.mirador;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.um2.model.Children;
import fr.um2.model.Historiquegps;

public class MapActivity extends FragmentActivity  {
	/**
	 * Note that this may be null if the Google Play services APK is not
	 * available.
	 */
	private GoogleMap mMap;
	private int id = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		getIntent().getIntExtra("id", -1);
		setUpMapIfNeeded();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				if (id != -1) {
					setUpMap(id);
				} else {
					setUpMap();
				}

			}
		}
	}

	private void setUpMap(int id2) {
		Children child = Children.getChild(id2, this);
		if (child != null) {
			Historiquegps his = Historiquegps.getLastTraceFrom(this, child.id);
			if (his != null) {
				MarkerOptions marker = new MarkerOptions();
				double lat = Double.parseDouble(his.getLatitude());
				double log = Double.parseDouble(his.getLongitude());
				LatLng position = new LatLng(lat, log);
				marker.position(position);
				marker.title(child.nom + " " + child.prenom);
				mMap.addMarker(marker);
				mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 8.0f));
			}
		}
	}

	private void setUpMap() {
		List<Children> childrens = Children.getAllChildren(this);

		for (Children childs : childrens) {

			Historiquegps his = Historiquegps.getLastTraceFrom(this, childs.id);
			if (his != null) {
				MarkerOptions marker = new MarkerOptions();

				double lat = Double.parseDouble(his.getLatitude());
				double log = Double.parseDouble(his.getLongitude());

				LatLng position = new LatLng(lat, log);
				marker.position(position);

				marker.title(childs.nom + " " + childs.prenom);
				mMap.addMarker(marker);
				mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 12.0f));
			}

		}

	}

//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		while (true) {
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			if (mMap != null) {
//				if (id != -1) {
//					setUpMap(id);
//				} else {
//					setUpMap();
//				}
//			}
//
//		}
//	}
	

}
