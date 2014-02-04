package fr.um2.db;

import android.provider.BaseColumns;

public final class ParentContrat {
	

	public static abstract class ParentEntry implements BaseColumns{
		public static final String TABLE_NAME = "parent";
		public static final String FULL_ID =  TABLE_NAME + "." + _ID;
		public static final String COLUMN_NAME_PARENT_NAME= "name";
		public static final String COLUMN_NAME_PARENT_FIRSTNAME = "firstname";
		public static final String COLUMN_NAME_PARENT_TEL = "telephone";		
	}



}
