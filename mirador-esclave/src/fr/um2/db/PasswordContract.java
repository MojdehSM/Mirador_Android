package fr.um2.db;

import android.provider.BaseColumns;

public final class PasswordContract {
	

	public static abstract class PasswordEntry implements BaseColumns{
		public static final String TABLE_NAME = "password";
		public static final String FULL_ID =  TABLE_NAME + "." + _ID;
		public static final String COLUMN_NAME_PASSWORD= "password";		
	}



}
