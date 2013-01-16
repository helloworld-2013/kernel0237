package mylab.hdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.*;

public class HdbCache extends SQLiteOpenHelper {
	
	private Map<String,String> monthLookup = new HashMap<String,String>();
	
	private SQLiteDatabase db = null;

	private static HdbCache ourInstance;

    public static HdbCache getInstance(final Context context) {
		if (ourInstance == null) {
			ourInstance = new HdbCache(context);
		}
		return ourInstance;
	}
	
	public static HdbCache getInstance() {
		return ourInstance;
	}

	private HdbCache(final Context context) {
		super(context, "HDB_DB", null, 1);
		
		monthLookup.put("Jan", "01");
		monthLookup.put("Feb", "02");
		monthLookup.put("Mar", "03");
		monthLookup.put("Apr", "04");
		monthLookup.put("May", "05");
		monthLookup.put("Jun", "06");
		monthLookup.put("Jul", "07");
		monthLookup.put("Aug", "08");
		monthLookup.put("Sep", "09");
		monthLookup.put("Oct", "10");
		monthLookup.put("Nov", "11");
		monthLookup.put("Dec", "12");
	}
	
	public void initDb() {
        if (db == null) {
            db = this.getWritableDatabase();
            deleteOldCache();
        }
	}
	
	public void closeDb() {
		db.close();
        db = null;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		HdbTable.onCreate(db);
		HdbConnectionTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		HdbTable.onUpgrade(db, oldVersion, newVersion);
		HdbConnectionTable.onUpgrade(db, oldVersion, newVersion);
	}

	public void deleteOldCache() {
		String currentPeriod = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
        currentPeriod += "-01";
		
		// delete all recs if the system is not used more than 2 months...
		db.execSQL("DELETE FROM hdb WHERE (SELECT JULIANDAY(" + currentPeriod + ") - JULIANDAY(a.end_period) FROM hdb_connection a WHERE (SELECT MAX(b.end_period) FROM hdb_connection b) = a.end_period ) > 30");
		
		// delete all recs if the system is not used more than 2 months...
		db.execSQL("DELETE FROM hdb_connection WHERE (SELECT JULIANDAY(" + currentPeriod + ") - JULIANDAY(a.end_period) FROM hdb_connection a WHERE (SELECT MAX(b.end_period) FROM hdb_connection b) = a.end_period ) > 30");
		
		// delete all recs that are more than one year older...
		db.execSQL("DELETE FROM hdb WHERE (SELECT JULIANDAY(" + currentPeriod + ") - JULIANDAY(a.resale_approval_date) FROM hdb a WHERE (SELECT MIN(b.resale_approval_date) FROM hdb b) = a.resale_approval_date AND a.id = hdb.id ) > 365");
	}
	
	public PeriodDTO delta(String town, String flatType) {
		PeriodDTO period = new PeriodDTO();

		String currentPeriod = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
        currentPeriod += "-01";

        String _startPeriod = "";
		String _endPeriod = "";
        String _hdb_connection_endPeriod = "";
		int count = 0, between = 0;
        int year = Integer.parseInt(currentPeriod.substring(0,4));
        int month = Integer.parseInt(currentPeriod.substring(5,7));
		
		Cursor c = db.rawQuery("SELECT COUNT(*),JULIANDAY(?) - JULIANDAY(end_period),end_period FROM hdb_connection WHERE town=? AND flat_type=?", new String[] {currentPeriod, town, flatType});
		if (c.moveToFirst()) {
			count = c.getInt(0);
			if (count>0) {
				between = c.getInt(1);
                _hdb_connection_endPeriod = c.getString(2);
			}
		}
		if (!c.isClosed()) c.close();
		
		if (count == 0) {
			// if no period found for town flatType combination then insert new rec with period 1yr ending current month
			period.endPeriod = String.format("%d%02d",year,month);
            _endPeriod = String.format("%d-%02d-01",year,month);

            year--;

            period.startPeriod = String.format("%d%02d",year,month);
            _startPeriod = String.format("%d-%02d-01",year,month);

            db.execSQL("INSERT INTO hdb_connection (town,flat_type,start_period,end_period) VALUES (?, ?, ?, ?)", new String[] {town, flatType, _startPeriod, _endPeriod});
		} else {
			// if period found for town flatType combination then update rec with delta ending current month
			if (between > 360) {
                // if delta between end_period and current month is more than 360 days then delta = 1yr ending current month
                period.endPeriod = String.format("%d%02d",year,month);
                _endPeriod = String.format("%d-%02d-01",year,month);

                year--;

                period.startPeriod = String.format("%d%02d",year,month);
                _startPeriod = String.format("%d-%02d-01",year,month);

                db.execSQL("DELETE FROM hdb WHERE town=? AND flat_Type=?", new String[] {town, flatType});
            } else if (between > 0) {
                // if delta between end_period and current month is less than 360 days then delta = end_period to current month

                // tricks applied to endPeriod below is to make request to hdb always have different startPeriod and endPeriod...
                _endPeriod = String.format("%d%02d",year,month);
                month++;
                if(month==13){month=1;year++;}
                period.endPeriod = String.format("%d%02d",year,month);

                int _month = Integer.parseInt(_hdb_connection_endPeriod.substring(5,7));
                int _year = Integer.parseInt(_hdb_connection_endPeriod.substring(0,4));

                _month++;
                if(_month==13){_month=1;_year++;}

                period.startPeriod = String.format("%d%02d",_year,_month);
                _startPeriod = String.format("%d-%02d-01",_year,_month);
            }

            if (between > 0) {
                db.execSQL("UPDATE hdb_connection SET start_period=?, end_period=? WHERE town=? AND flat_type=?", new String[] {_startPeriod, _endPeriod, town, flatType});
            }
            // if delta between end_period and current month is zero then do nothing...
		}
		
		return period;
	}
	
	public void cache(HdbDTO data) {
		ContentValues values = new ContentValues();
		values.put(HdbTable.HdbColumns.TOWN, data.town);
		values.put(HdbTable.HdbColumns.FLAT_TYPE, data.flatType);
		values.put(HdbTable.HdbColumns.BLOCK, data.block);
		values.put(HdbTable.HdbColumns.STREET, data.street);
		values.put(HdbTable.HdbColumns.STOREY, data.storey);
		values.put(HdbTable.HdbColumns.FLAT_MODEL, data.flatModel);
		values.put(HdbTable.HdbColumns.LEASE_COMMENCE_DATE, data.leaseCommenceDate);
		values.put(HdbTable.HdbColumns.RESALE_PRICE, data.resalePrice);
		values.put(HdbTable.HdbColumns.RESALE_APPROVAL_DATE, data.resaleApprovalDate);
		
		db.insert(HdbTable.TABLE_NAME, null, values);
	}
	
	public List<HdbDTO> getSummary(String hdbRoom, String hdbTown, int minPrice, int maxPrice) {
		List<HdbDTO> results = new ArrayList<HdbDTO>();

        // TODO: totalTransactions still buggy, we must take resale_price and resale_approval_date into account.
        String sql = "SELECT A.* FROM (SELECT block,street,MIN(resale_price) as minPrice,COUNT(*) as totalTransactions,MAX(resale_price) as maxPrice FROM hdb WHERE town=? AND flat_type=? GROUP BY block,street) A";

        Cursor c = null;

        if (minPrice == -1 && maxPrice == -1)
            c = db.rawQuery(sql + " ORDER BY A.street", new String[] {hdbTown,hdbRoom});
        else if (minPrice != -1 && maxPrice == -1)
            c = db.rawQuery(sql + " WHERE A.minPrice > " + minPrice + " ORDER BY A.street", new String[] {hdbTown,hdbRoom});
        else if (minPrice == -1 && maxPrice != -1)
            c = db.rawQuery(sql + " WHERE A.maxPrice < " + maxPrice + " ORDER BY A.street", new String[] {hdbTown,hdbRoom});
        else if (minPrice != -1 && maxPrice != -1)
            c = db.rawQuery(sql + " WHERE A.minPrice > " + minPrice + " AND A.maxPrice < " + maxPrice + " ORDER BY A.street", new String[] {hdbTown,hdbRoom});

		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				HdbDTO dto = new HdbDTO();
				dto.block = c.getString(0);
				dto.street = c.getString(1);
                dto.minPrice = c.getInt(2);
                dto.totalTransactions = c.getInt(3);
                dto.maxPrice = c.getInt(4);
				
				results.add(dto);
				c.moveToNext();
			}
		}
		
		return results;
	}
    
    public List<HdbDTO> getDetail(String block, String street) {
        List<HdbDTO> results = new ArrayList<HdbDTO>();

        String sql = "SELECT storey, flat_model, lease_commence_date, resale_price, resale_approval_date FROM hdb WHERE block=? AND street=?";

        Cursor c = null;
        c = db.rawQuery(sql, new String[] {block, street});
        
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                HdbDTO dto = new HdbDTO();
                dto.storey = c.getString(0);
                dto.flatModel = c.getString(1);
                dto.leaseCommenceDate = c.getString(2);
                dto.resalePrice = c.getInt(3);
                dto.resaleApprovalDate = c.getString(4);
                
                results.add(dto);
                c.moveToNext();
            }
        }

        return results;
    }
	
}
