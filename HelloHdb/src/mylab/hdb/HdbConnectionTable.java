/* ========================================================
 * HdbConnectionTable.java
 *
 * Copyright 2012 Indra Gunawan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * ======================================================== */
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
