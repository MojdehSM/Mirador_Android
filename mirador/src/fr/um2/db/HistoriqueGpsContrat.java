package fr.um2.db;

import android.provider.BaseColumns;

public class HistoriqueGpsContrat {
	public static abstract class GpsEntry implements BaseColumns{
		public static final String TABLE_NAME = "gps";
		public static final String FULL_ID =  TABLE_NAME + "." + _ID;
		public static final String COLUMN_NAME_ID_CHILD = "id_child";
		public static final String COLUMN_NAME_LAT = "lat";
		public static final String COLUMN_NAME_LONG = "long";	
		public static final String COLUMN_NAME_DATE = "date";
	}
}
