package fr.um2.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import fr.um2.db.ParentContrat.ParentEntry;
import fr.um2.db.ParentDbHelper;

public class Parent {
	public int id;
	public String nom;
	public String prenom;
	public String numero;

	public Parent(String nom, String prenom, String numero) {
		this.nom = nom;
		this.prenom = prenom;
		this.numero = numero;
	}

	public Parent(int id, String nom, String prenom, String numero) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.numero = numero;
	}

	public static void pootTest(Context ctx) {
		Parent child1 = new Parent("Soltan", "Mojdeh", "+336753343889");
		Parent child2 = new Parent("Petitdemange", "Franck", "+33782837473");
		child1.create(ctx);
		child2.create(ctx);
	}

	public void save(Context ctx) {
		SQLiteDatabase db;

		db = ParentDbHelper.getInstance(ctx).getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ParentEntry.COLUMN_NAME_PARENT_NAME, this.nom);
		values.put(ParentEntry.COLUMN_NAME_PARENT_FIRSTNAME, this.prenom);
		values.put(ParentEntry.COLUMN_NAME_PARENT_TEL, this.numero);

		db.update(ParentEntry.TABLE_NAME, values, ParentEntry._ID + "=" + this.id, null);
		db.close();
	}

	public void create(Context ctx) {
		SQLiteDatabase db;

		db = ParentDbHelper.getInstance(ctx).getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(ParentEntry.COLUMN_NAME_PARENT_NAME, this.nom);
		values.put(ParentEntry.COLUMN_NAME_PARENT_FIRSTNAME, this.prenom);
		values.put(ParentEntry.COLUMN_NAME_PARENT_TEL, this.numero);

		db.insert(ParentEntry.TABLE_NAME, null, values);

		Log.println(Log.INFO, "Insert table " + ParentEntry.TABLE_NAME, values.toString());

		db.close();
	}
	
	public static List<Parent> getAllParent(Context ctx) {

		List<Parent> parents = new ArrayList<Parent>();
		Cursor cursor;
		SQLiteDatabase db;
		// recupere db en LECTURE
		db = ParentDbHelper.getInstance(ctx).getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { ParentEntry._ID, ParentEntry.COLUMN_NAME_PARENT_NAME, ParentEntry.COLUMN_NAME_PARENT_FIRSTNAME,
				ParentEntry.COLUMN_NAME_PARENT_TEL };

		// Get cursor
		cursor = db.query(ParentEntry.TABLE_NAME, projection, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {

			String phone = cursor.getString(cursor.getColumnIndex(ParentEntry.COLUMN_NAME_PARENT_TEL));
			String nom = cursor.getString(cursor.getColumnIndex(ParentEntry.COLUMN_NAME_PARENT_NAME));
			String prenom = cursor.getString(cursor.getColumnIndex(ParentEntry.COLUMN_NAME_PARENT_FIRSTNAME));
			int id = cursor.getInt(cursor.getColumnIndex(ParentEntry._ID));

			parents.add(new Parent(id, nom, prenom, phone));
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return parents;
	}

	public static boolean testNumero(Context ctx, String numero){
		boolean retour = false;
		Cursor cursor;
		SQLiteDatabase db;
		// recupere db en LECTURE //
		db = ParentDbHelper.getInstance(ctx).getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { ParentEntry.COLUMN_NAME_PARENT_TEL };

		// Define 'where' part of query.
		String selection = ParentEntry.COLUMN_NAME_PARENT_TEL + " = ?";

		// Specify arguments in placeholder order.
		String[] selectionArgs = { numero };

		// Get cursor
		cursor = db.query(ParentEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

		retour = cursor.moveToFirst();

		cursor.close();
		db.close();

		
		
		return retour;
		
	}
	
	
	
	public static Parent getPrentById(int id, Context ctx){

		Cursor cursor;
		SQLiteDatabase db;
		// recupere db en LECTURE //
		db = ParentDbHelper.getInstance(ctx).getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { ParentEntry._ID, ParentEntry.COLUMN_NAME_PARENT_NAME, ParentEntry.COLUMN_NAME_PARENT_FIRSTNAME,
				ParentEntry.COLUMN_NAME_PARENT_TEL };

		// Define 'where' part of query.
		String selection = ParentEntry._ID + " = ?";

		// Specify arguments in placeholder order.
		String[] selectionArgs = { Integer.toString(id) };

		// Get cursor
		cursor = db.query(ParentEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

		cursor.moveToFirst();
		String nom = cursor.getString(cursor.getColumnIndex(ParentEntry.COLUMN_NAME_PARENT_NAME));
		String prenom = cursor.getString(cursor.getColumnIndex(ParentEntry.COLUMN_NAME_PARENT_FIRSTNAME));
		String telephone = cursor.getString(cursor.getColumnIndex(ParentEntry.COLUMN_NAME_PARENT_TEL));

		cursor.close();
		db.close();

		return new Parent(id, nom, prenom, telephone);
	}

	public static List<Parent> GetAllParents(Context ctx) {

		Cursor cursor;
		SQLiteDatabase db;
		// recupere db en LECTURE
		db = ParentDbHelper.getInstance(ctx).getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { ParentEntry._ID, ParentEntry.COLUMN_NAME_PARENT_NAME, ParentEntry.COLUMN_NAME_PARENT_FIRSTNAME , ParentEntry.COLUMN_NAME_PARENT_TEL};

		// Define 'where' part of query.
		String selection;
		// Specify arguments in placeholder order.
		String[] selectionArgs;

		// Get cursor
		cursor = db.query(ParentEntry.TABLE_NAME, projection, null, null, null, null, null);
		
		List<Parent> parents = new ArrayList<Parent>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {

			int id = cursor.getInt(cursor.getColumnIndex(ParentEntry._ID));
			String nom = cursor.getString(cursor.getColumnIndex(ParentEntry.COLUMN_NAME_PARENT_NAME));
			String prenom = cursor.getString(cursor.getColumnIndex(ParentEntry.COLUMN_NAME_PARENT_FIRSTNAME));
			String telephone = cursor.getString(cursor.getColumnIndex(ParentEntry.COLUMN_NAME_PARENT_TEL));

			parents.add(new Parent(id, nom, prenom, telephone));
			
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return parents;
	}

	public static void delete(int id, Context ctx) {
		SQLiteDatabase db;

		db = ParentDbHelper.getInstance(ctx).getWritableDatabase();

		db.delete(ParentEntry.TABLE_NAME, ParentEntry._ID + " = " + id, null);

	}

	public static String[] parentToString(Context ctx) {

		Cursor cursor;
		SQLiteDatabase db;
		// recupere db en LECTURE
		db = ParentDbHelper.getInstance(ctx).getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { ParentEntry._ID, ParentEntry.COLUMN_NAME_PARENT_NAME, ParentEntry.COLUMN_NAME_PARENT_FIRSTNAME };

		// Define 'where' part of query.
		String selection;
		// Specify arguments in placeholder order.
		String[] selectionArgs;

		// Get cursor
		cursor = db.query(ParentEntry.TABLE_NAME, projection, null, null, null, null, null);

		int nbEntry = cursor.getCount();
		// System.out.println("nb of entry "+nbEntry);

		String[] retour = new String[nbEntry];

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {

			retour[cursor.getPosition()] = cursor.getString(cursor.getColumnIndex(ParentEntry._ID)) + " "
					+ cursor.getString(cursor.getColumnIndex(ParentEntry.COLUMN_NAME_PARENT_FIRSTNAME)) + " "
					+ cursor.getString(cursor.getColumnIndex(ParentEntry.COLUMN_NAME_PARENT_NAME));

			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return retour;
	}

}
