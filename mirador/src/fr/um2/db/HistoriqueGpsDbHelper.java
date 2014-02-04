package fr.um2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import fr.um2.db.HistoriqueGpsContrat.GpsEntry;

public class HistoriqueGpsDbHelper extends SQLiteOpenHelper{
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String DATE_TYPE = " DATE";
	private static final String COMMA_SEP = ",";
	
	
	private static final String SQL_CREATE_HISTORIQUEGPS_TABLE = "CREATE TABLE "+GpsEntry.TABLE_NAME + 
			" (" +
			GpsEntry._ID + " INTEGER PRIMARY KEY," +
			GpsEntry.COLUMN_NAME_ID_CHILD + TEXT_TYPE + COMMA_SEP +
			GpsEntry.COLUMN_NAME_LAT + TEXT_TYPE + COMMA_SEP +
			GpsEntry.COLUMN_NAME_LONG + TEXT_TYPE + COMMA_SEP +
			GpsEntry.COLUMN_NAME_DATE + TEXT_TYPE + 
			" )";
	
	private static final String SQL_DELETE_HISTORIQUEGPS_TABLE = "DROP TABLE IF EXISTS " + GpsEntry.TABLE_NAME;
	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HistoriqueGps.db";
    
    public static final String DESC = " DESC";
    public static final String ASC = " ASC";
    
    private static HistoriqueGpsDbHelper instance = null;

	public HistoriqueGpsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	public static HistoriqueGpsDbHelper getInstance(Context context) {
		if(instance == null){
			instance = new HistoriqueGpsDbHelper(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_HISTORIQUEGPS_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_CREATE_HISTORIQUEGPS_TABLE);
		this.onCreate(db);		
	}

}
