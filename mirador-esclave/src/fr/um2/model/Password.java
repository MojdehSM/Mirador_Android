package fr.um2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import fr.um2.db.PasswordContract.PasswordEntry;
import fr.um2.db.PasswordDbHelper;

public class Password {
	int id;
	String password;
	
	public Password(int id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	
	public Password(String password) {
		super();
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int i) {
		this.id = i;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

	public void create(Context ctx) {
		SQLiteDatabase db;

		db = PasswordDbHelper.getInstance(ctx).getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(PasswordEntry.COLUMN_NAME_PASSWORD, this.password);
		db.insert(PasswordEntry.TABLE_NAME, null, values);

		Log.println(Log.INFO, "Insert table " + PasswordEntry.TABLE_NAME, values.toString());

		db.close();
	}
	
	
	public static Password getPassword(Context ctx){

		Cursor cursor;
		SQLiteDatabase db;
		// recupere db en LECTURE
		db = PasswordDbHelper.getInstance(ctx).getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { PasswordEntry._ID, PasswordEntry.COLUMN_NAME_PASSWORD};

		// Get cursor
		cursor = db.query(PasswordEntry.TABLE_NAME, projection, null, null, null, null, null);

		cursor.moveToFirst();
		String id = cursor.getString(cursor.getColumnIndex(PasswordEntry._ID));
		String password = cursor.getString(cursor.getColumnIndex(PasswordEntry.COLUMN_NAME_PASSWORD));

		cursor.close();
		db.close();

		return new Password(Integer.valueOf(id), password);
		
	}
	

}
