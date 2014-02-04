package fr.um2.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import fr.um2.db.ControleParentalContrat.SlaveEntry;
import fr.um2.db.ControleParentalDbHelper;

//http://a-renouard.developpez.com/tutoriels/android/sqlite/
public class Children {
	public int id;
	public String nom;
	public String prenom;
	public String numero;

	public Children(String nom, String prenom, String numero) {
		this.nom = nom;
		this.prenom = prenom;
		this.numero = numero;
	}

	public Children(int id, String nom, String prenom, String numero) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.numero = numero;
	}

	public static void pootTest(Context ctx) {
		Children child1 = new Children("Soltan", "Mojdeh", "+336753343889");
		Children child2 = new Children("Soltan", "Mahshad", "+33782837473");
		child1.create(ctx);
		child2.create(ctx);
	}

	public void save(Context ctx) {
		SQLiteDatabase db;

		db = ControleParentalDbHelper.getInstance(ctx).getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(SlaveEntry.COLUMN_NAME_SLAVE_NAME, this.nom);
		values.put(SlaveEntry.COLUMN_NAME_SLAVE_FIRSTNAME, this.prenom);
		values.put(SlaveEntry.COLUMN_NAME_SLAVE_TEL, this.numero);

		db.update(SlaveEntry.TABLE_NAME, values, SlaveEntry._ID + "=" + this.id, null);
		db.close();
	}

	public void create(Context ctx) {
		SQLiteDatabase db;

		db = ControleParentalDbHelper.getInstance(ctx).getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(SlaveEntry.COLUMN_NAME_SLAVE_NAME, this.nom);
		values.put(SlaveEntry.COLUMN_NAME_SLAVE_FIRSTNAME, this.prenom);
		values.put(SlaveEntry.COLUMN_NAME_SLAVE_TEL, this.numero);

		db.insert(SlaveEntry.TABLE_NAME, null, values);

		Log.println(Log.INFO, "Insert table " + SlaveEntry.TABLE_NAME, values.toString());

		db.close();
	}

	public static boolean testNumero(Context ctx, String numero){
		boolean retour = false;
		Cursor cursor;
		SQLiteDatabase db;
		// recupere db en LECTURE //
		db = ControleParentalDbHelper.getInstance(ctx).getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { SlaveEntry.COLUMN_NAME_SLAVE_TEL };

		// Define 'where' part of query.
		String selection = SlaveEntry.COLUMN_NAME_SLAVE_TEL + " = ?";

		// Specify arguments in placeholder order.
		String[] selectionArgs = { numero };

		// Get cursor
		cursor = db.query(SlaveEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

		retour = cursor.moveToFirst();

		cursor.close();
		db.close();

		
		
		return retour;
		
	}
	public static Children getChild(int id, Context ctx) {

		Cursor cursor;
		SQLiteDatabase db;
		// recupere db en LECTURE
		db = ControleParentalDbHelper.getInstance(ctx).getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { SlaveEntry._ID, SlaveEntry.COLUMN_NAME_SLAVE_NAME, SlaveEntry.COLUMN_NAME_SLAVE_FIRSTNAME,
				SlaveEntry.COLUMN_NAME_SLAVE_TEL };

		// Define 'where' part of query.
		String selection = SlaveEntry._ID + " = ?";

		// Specify arguments in placeholder order.
		String[] selectionArgs = { Integer.toString(id) };

		// Log.println(Log.INFO, "selected child", selection);

		// Get cursor
		cursor = db.query(SlaveEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

		// System.out.println(cursor.getCount());

		cursor.moveToFirst();
		// int id =
		// Integer.parseInt(cursor.getString(cursor.getColumnIndex(SlaveEntry._ID)));
		String nom = cursor.getString(cursor.getColumnIndex(SlaveEntry.COLUMN_NAME_SLAVE_NAME));
		String prenom = cursor.getString(cursor.getColumnIndex(SlaveEntry.COLUMN_NAME_SLAVE_FIRSTNAME));
		String telephone = cursor.getString(cursor.getColumnIndex(SlaveEntry.COLUMN_NAME_SLAVE_TEL));

		cursor.close();
		db.close();

		return new Children(id, nom, prenom, telephone);
	}

	/**
	 * Get Children by phone number
	 * 
	 * @param phonenumber
	 * @param ctx
	 * @return
	 */
	public static Children getChildByPhone(String phonenumber, Context ctx) {

		Cursor cursor;
		SQLiteDatabase db;
		// recupere db en LECTURE
		db = ControleParentalDbHelper.getInstance(ctx).getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { SlaveEntry._ID, SlaveEntry.COLUMN_NAME_SLAVE_NAME, SlaveEntry.COLUMN_NAME_SLAVE_FIRSTNAME,
				SlaveEntry.COLUMN_NAME_SLAVE_TEL };

		// Define 'where' part of query.
		String selection = SlaveEntry.COLUMN_NAME_SLAVE_TEL + " = ?";

		// Specify arguments in placeholder order.
		String[] selectionArgs = { phonenumber };

		// Log.println(Log.INFO, "selected child", selection);

		// Get cursor
		cursor = db.query(SlaveEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

		System.out.println(cursor.getCount());

		cursor.moveToFirst();
		// int id =
		// Integer.parseInt(cursor.getString(cursor.getColumnIndex(SlaveEntry._ID)));
		String nom = cursor.getString(cursor.getColumnIndex(SlaveEntry.COLUMN_NAME_SLAVE_NAME));
		String prenom = cursor.getString(cursor.getColumnIndex(SlaveEntry.COLUMN_NAME_SLAVE_FIRSTNAME));
		int id = cursor.getInt(cursor.getColumnIndex(SlaveEntry._ID));

		cursor.close();
		db.close();

		return new Children(id, nom, prenom, phonenumber);
	}

	/**
	 * Get All Children
	 * 
	 * @param ctx
	 * @return
	 */
	public static List<Children> getAllChildren(Context ctx) {

		List<Children> childs = new ArrayList<Children>();
		Cursor cursor;
		SQLiteDatabase db;
		// recupere db en LECTURE
		db = ControleParentalDbHelper.getInstance(ctx).getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { SlaveEntry._ID, SlaveEntry.COLUMN_NAME_SLAVE_NAME, SlaveEntry.COLUMN_NAME_SLAVE_FIRSTNAME,
				SlaveEntry.COLUMN_NAME_SLAVE_TEL };

		// Get cursor
		cursor = db.query(SlaveEntry.TABLE_NAME, projection, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {

			String phone = cursor.getString(cursor.getColumnIndex(SlaveEntry.COLUMN_NAME_SLAVE_TEL));
			String nom = cursor.getString(cursor.getColumnIndex(SlaveEntry.COLUMN_NAME_SLAVE_NAME));
			String prenom = cursor.getString(cursor.getColumnIndex(SlaveEntry.COLUMN_NAME_SLAVE_FIRSTNAME));
			int id = cursor.getInt(cursor.getColumnIndex(SlaveEntry._ID));

			childs.add(new Children(id, nom, prenom, phone));
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return childs;
	}

	public static String[] childrenToString(Context ctx) {

		Cursor cursor;
		SQLiteDatabase db;
		// recupere db en LECTURE
		db = ControleParentalDbHelper.getInstance(ctx).getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { SlaveEntry._ID, SlaveEntry.COLUMN_NAME_SLAVE_NAME, SlaveEntry.COLUMN_NAME_SLAVE_FIRSTNAME };

		// Define 'where' part of query.
		String selection;
		// Specify arguments in placeholder order.
		String[] selectionArgs;

		// Get cursor
		cursor = db.query(SlaveEntry.TABLE_NAME, projection, null, null, null, null, null);

		int nbEntry = cursor.getCount();
		// System.out.println("nb of entry "+nbEntry);

		String[] retour = new String[nbEntry];

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {

			retour[cursor.getPosition()] = cursor.getString(cursor.getColumnIndex(SlaveEntry._ID)) + " "
					+ cursor.getString(cursor.getColumnIndex(SlaveEntry.COLUMN_NAME_SLAVE_FIRSTNAME)) + " "
					+ cursor.getString(cursor.getColumnIndex(SlaveEntry.COLUMN_NAME_SLAVE_NAME)) + " "
					/*+ cursor.getString(cursor.getColumnIndex(SlaveEntry.COLUMN_NAME_SLAVE_TEL))*/;

			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return retour;
	}

	public static void delete(int id, Context ctx) {
		SQLiteDatabase db;

		db = ControleParentalDbHelper.getInstance(ctx).getWritableDatabase();

		db.delete(SlaveEntry.TABLE_NAME, SlaveEntry._ID + " = " + id, null);

	}

}
