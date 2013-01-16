package mylab.hdb;

import android.database.sqlite.SQLiteDatabase;

public final class HdbConnectionTable {

	public static final String TABLE_NAME = "hdb_connection";
	
	public static class HdbConnectionColumns {
		public static final String ID = "id";
		public static final String TOWN = "town";
		public static final String FLAT_TYPE = "flat_type";
		public static final String START_PERIOD = "start_period";
		public static final String END_PERIOD = "end_period";
	}
	
	public static void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + TABLE_NAME + " (");
		sb.append(HdbConnectionColumns.ID + " INTEGER PRIMARY KEY, ");
		sb.append(HdbConnectionColumns.TOWN + " TEXT, ");
		sb.append(HdbConnectionColumns.FLAT_TYPE + " TEXT, ");
		sb.append(HdbConnectionColumns.START_PERIOD + " TEXT, ");
		sb.append(HdbConnectionColumns.END_PERIOD + " TEXT");
		sb.append(");");
		db.execSQL(sb.toString());
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + HdbConnectionTable.TABLE_NAME);
		HdbConnectionTable.onCreate(db);
	}
	
}
