package fr.um2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import fr.um2.db.ParentContrat.ParentEntry;
import fr.um2.db.PasswordContract.PasswordEntry;

public class PasswordDbHelper extends SQLiteOpenHelper {
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String DATE_TYPE = " DATE";
	private static final String COMMA_SEP = ",";
	
	
	private static final String SQL_CREATE_PASSWORD_TABLE = "CREATE TABLE "+PasswordEntry.TABLE_NAME + 
			" (" +
			PasswordEntry._ID + " INTEGER PRIMARY KEY," +
			PasswordEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE+
			" )";
	
	private static final String SQL_DELETE_PASSWORD_TABLE = "DROP TABLE IF EXISTS " + PasswordEntry.TABLE_NAME;

	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Password.db";
    
    public static final String DESC = " DESC";
    public static final String ASC = " ASC";
    
    private static PasswordDbHelper instance = null;

	public PasswordDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	public static PasswordDbHelper getInstance(Context context) {
		if(instance == null){
			instance = new PasswordDbHelper(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_PASSWORD_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_CREATE_PASSWORD_TABLE);
		this.onCreate(db);		
	}

}
