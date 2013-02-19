/* ========================================================
 * HdbTable.java
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

public final class HdbTable {
	
	public static final String TABLE_NAME = "hdb";
	
	public static class HdbColumns {
		public static final String ID = "id";
		public static final String TOWN = "town";
		public static final String FLAT_TYPE = "flat_type";
		public static final String BLOCK = "block";
		public static final String STREET = "street";
		public static final String STOREY = "storey";
		public static final String FLAT_MODEL = "flat_model";
		public static final String LEASE_COMMENCE_DATE = "lease_commence_date";
		public static final String RESALE_PRICE = "resale_price";
		public static final String RESALE_APPROVAL_DATE = "resale_approval_date";
	}
	
	public static void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + TABLE_NAME + " (");
		sb.append(HdbColumns.ID + " INTEGER PRIMARY KEY, ");
		sb.append(HdbColumns.TOWN + " TEXT, ");
		sb.append(HdbColumns.FLAT_TYPE + " TEXT, ");
		sb.append(HdbColumns.BLOCK + " TEXT, ");
		sb.append(HdbColumns.STREET + " TEXT, ");
		sb.append(HdbColumns.STOREY + " TEXT, ");
		sb.append(HdbColumns.FLAT_MODEL + " TEXT, ");
		sb.append(HdbColumns.LEASE_COMMENCE_DATE + " TEXT, ");
		sb.append(HdbColumns.RESALE_PRICE + " INTEGER, ");
		sb.append(HdbColumns.RESALE_APPROVAL_DATE + " TEXT");
		sb.append(");");
		db.execSQL(sb.toString());
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + HdbTable.TABLE_NAME);
		HdbTable.onCreate(db);
	}

}
