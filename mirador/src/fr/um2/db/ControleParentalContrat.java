package fr.um2.db;

import android.provider.BaseColumns;

public final class ControleParentalContrat {
	

	public static abstract class SlaveEntry implements BaseColumns{
		public static final String TABLE_NAME = "slave";
		public static final String FULL_ID =  TABLE_NAME + "." + _ID;
		public static final String COLUMN_NAME_SLAVE_NAME= "name";
		public static final String COLUMN_NAME_SLAVE_FIRSTNAME = "firstname";
		public static final String COLUMN_NAME_SLAVE_TEL = "telephone";		
	}



}
