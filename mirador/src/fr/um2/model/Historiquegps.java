package fr.um2.model;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import fr.um2.db.ControleParentalContrat.SlaveEntry;
import fr.um2.db.HistoriqueGpsContrat.GpsEntry;
import fr.um2.db.HistoriqueGpsDbHelper;

public class Historiquegps {

	int id_child;
	String latitude;
	String longitude;
	Date datelocalisation;

	public Historiquegps(int id_child, String latitude, String longitude, Date datelocalisation) {
		super();
		this.id_child = id_child;
		this.latitude = latitude;
		this.longitude = longitude;
		this.datelocalisation = datelocalisation;
	}

	// ACCESSEUR
	public int getId_child() {
		return id_child;
	}

	public void setId_child(int id_child) {
		this.id_child = id_child;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDatelocalisation() {
		return datelocalisation.toString();
	}

	public void setDatelocalisation(Date datelocalisation) {
		this.datelocalisation = datelocalisation;
	}


	public void save(Context ctx) {
		SQLiteDatabase db;

		db = HistoriqueGpsDbHelper.getInstance(ctx).getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(GpsEntry.COLUMN_NAME_ID_CHILD, getId_child());
		values.put(GpsEntry.COLUMN_NAME_LAT, getLatitude());
		values.put(GpsEntry.COLUMN_NAME_LONG, getLongitude());
		values.put(GpsEntry.COLUMN_NAME_DATE, getDatelocalisation());

		db.insert(GpsEntry.TABLE_NAME, null, values);

		Log.println(Log.INFO, "Insert table " + SlaveEntry.TABLE_NAME, values.toString());
		db.close();
	}

	
	//METHODE STATIC

	public static Historiquegps getLastTraceFrom(Context ctx, int id_child) {

		Cursor cursor;
		SQLiteDatabase db;

		db = HistoriqueGpsDbHelper.getInstance(ctx).getReadableDatabase();

		String[] projection = { GpsEntry._ID, GpsEntry.COLUMN_NAME_ID_CHILD, GpsEntry.COLUMN_NAME_LAT, GpsEntry.COLUMN_NAME_LONG, GpsEntry.COLUMN_NAME_DATE };

		String selection = GpsEntry.COLUMN_NAME_ID_CHILD + " = ?";
		String[] selectionArgs = { Integer.toString(id_child) };

		cursor = db.query(GpsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, GpsEntry.COLUMN_NAME_DATE + " DESC");

		if (cursor.moveToFirst()) {
			String lat = cursor.getString(cursor.getColumnIndex(GpsEntry.COLUMN_NAME_LAT));
			String longi = cursor.getString(cursor.getColumnIndex(GpsEntry.COLUMN_NAME_LONG));
			String datelocalisation = cursor.getString(cursor.getColumnIndex(GpsEntry.COLUMN_NAME_DATE));

			cursor.close();
			db.close();

			return new Historiquegps(id_child, lat, longi, new Date(datelocalisation));
		}

		return null;

	}

}
