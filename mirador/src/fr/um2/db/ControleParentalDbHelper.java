package fr.um2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import fr.um2.db.ControleParentalContrat.SlaveEntry;





public class ControleParentalDbHelper extends SQLiteOpenHelper {
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String DATE_TYPE = " DATE";
	private static final String COMMA_SEP = ",";
	
	
	private static final String SQL_CREATE_SLAVE_TABLE = "CREATE TABLE "+SlaveEntry.TABLE_NAME + 
			" (" +
			SlaveEntry._ID + " INTEGER PRIMARY KEY," +
			SlaveEntry.COLUMN_NAME_SLAVE_NAME + TEXT_TYPE + COMMA_SEP +
			SlaveEntry.COLUMN_NAME_SLAVE_FIRSTNAME + TEXT_TYPE + COMMA_SEP +
			SlaveEntry.COLUMN_NAME_SLAVE_TEL + TEXT_TYPE +
			" )";
	
	private static final String SQL_DELETE_Slave = "DROP TABLE IF EXISTS " + SlaveEntry.TABLE_NAME;

	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ConstroleParental1.db";
    
    public static final String DESC = " DESC";
    public static final String ASC = " ASC";
    
    private static ControleParentalDbHelper instance = null;

	public ControleParentalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	public static ControleParentalDbHelper getInstance(Context context) {
		if(instance == null){
			instance = new ControleParentalDbHelper(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_SLAVE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_CREATE_SLAVE_TABLE);
		this.onCreate(db);		
	}

}
