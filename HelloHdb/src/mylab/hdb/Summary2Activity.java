package mylab.hdb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Summary2Activity extends Activity implements Handler.Callback {

    public enum Summary2GroupBy {
        HDB_TOWN_GROUP("HDB_TOWN_GROUP"),
        HDB_FLAT_GROUP("HDB_FLAT_GROUP"),
        NONE_GROUP("NONE_GROUP");

        String groupBy;

        Summary2GroupBy(String groupBy) {
            this.groupBy = groupBy;
        }

        public String getGroupBy() { return groupBy; }
        public void setGroupBy(String groupBy) { this.groupBy = groupBy; }
    }

    public Summary2GroupBy howToGroup = Summary2GroupBy.NONE_GROUP;

    HdbDataProvider dataProvider = new HdbDataProvider();
    
    ExpandableListView elv = null;
    List<HdbDTO> results = new ArrayList<HdbDTO>();
    
    List<Map<String,String>> groupItems = new ArrayList<Map<String, String>>();
    List<List<Map<String,String>>> childItems = new ArrayList<List<Map<String, String>>>();

    ProgressDialog progressDialog = null;
    
    int totalTowns = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary2);

        HelloHdbActivity.activityNotStop = true;

        HdbCache.getInstance(this);
        HdbCache.getInstance().initDb();

        Bundle params = this.getIntent().getExtras();
        ArrayList<String> paramRooms = params.getStringArrayList(String.valueOf(HdbRoomTypeActivity.HDB_ROOM_ACTIVITY));
        ArrayList<String> paramTowns = params.getStringArrayList(String.valueOf(HdbTownActivity.HDB_TOWN_ACTIVITY));
        ArrayList<String> paramPrices = params.getStringArrayList(String.valueOf(HdbPriceActivity.HDB_PRICE_ACTIVITY));
        
        howToGroup.setGroupBy(params.getString("howToGroup"));

        totalTowns = 0;

        dataProvider.rooms = paramRooms;
        dataProvider.towns = paramTowns;
        dataProvider.prices = paramPrices;
        dataProvider.requestHdbData(new Handler(this));

        ExpandableListAdapter adapter = new SimpleExpandableListAdapter(this,
                groupItems, R.layout.summary2_group_items, new String[]{"town_group"}, new int[] { R.id.summary2_group_name },
                childItems, R.layout.summary2_child_items, new String[]{"town_value1","town_value2","town_value3","town_value4"}, new int[] { R.id.summary2_text1,R.id.summary2_text2,R.id.summary2_text3,R.id.summary2_text4 });
        elv = (ExpandableListView)findViewById(R.id.summary2_list);
        // to move group indicator to the right side...
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        elv.setIndicatorBounds(width - getDipsFromPixel(50), width - getDipsFromPixel(10));

        elv.setAdapter(adapter);
        elv.setOnChildClickListener( new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                Map<String,String> childItem = childItems.get(groupPosition).get(childPosition);
                String townValue1 = childItem.get("town_value1");
                String block = townValue1.split("-")[0];
                block = block.trim();
                String street = townValue1.split("-")[1];
                street = street.trim();

                Bundle params = new Bundle();
                params.putString("block", block);
                params.putString("street", street);

                Intent target = new Intent(Summary2Activity.this, DetailActivity.class);
                target.putExtras(params);
                Summary2Activity.this.startActivity(target);

                return true;
            }
        });
        
        Button button1 = (Button)findViewById(R.id.summary2_button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progressDialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
    }

    private int getDipsFromPixel(float pixels) {
        float scale = getResources().getDisplayMetrics().density;
        return (int)(pixels * scale + .5f);
    }

    private void populateGroupItems(String town) {
        Map<String,String> groupItem = new HashMap<String, String>();
        groupItem.put("town_group",town);
        groupItems.add(groupItem);
    }

    private void populateChildItems() {
        List<Map<String,String>> _childItems = new ArrayList<Map<String, String>>();
        
        for ( HdbDTO dto : results ) {
            Map<String,String> childItem = new HashMap<String, String>();
            childItem.put("town_value1",dto.block + " - " + dto.street);
            childItem.put("town_value2","Min: " + formatPrice(dto.minPrice));
            childItem.put("town_value3","Max: " + formatPrice(dto.maxPrice));
            childItem.put("town_value4","#Tx: " + dto.totalTransactions);
            
            _childItems.add(childItem);
        }
        
        childItems.add(_childItems);
    }

    public String formatPrice(int price) {
        double value = price;

        int power = 0;
        String suffix = "kmbt";
        String formattedNumber = "";

        NumberFormat formatter = new DecimalFormat("#,###.#");
        power = (int)StrictMath.log10(price);
        value = value / (Math.pow(10,(power/3)*3));

        formattedNumber=formatter.format(value);
        formattedNumber = formattedNumber + suffix.charAt(power/3 - 1);

        return formattedNumber.length()>4 ? formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
    }

    @Override
    protected void onResume() {
        super.onResume();

        HelloHdbActivity.activityNotStop = true;

        HdbCache.getInstance(this);
        HdbCache.getInstance().initDb();
    }

    @Override
    protected void onPause() {
        super.onPause();

        HelloHdbActivity.activityNotStop = false;

        HdbCache.getInstance().closeDb();
    }

    @Override
    public synchronized boolean handleMessage(Message message) {
        if (HelloHdbActivity.activityNotStop == false) { progressDialog.dismiss();return true; }

        Bundle data = message.getData();
        String hdbRoom = data.getString("hdbRoom");
        String hdbTown = data.getString("hdbTown");

        int minPrice = -1;
        int maxPrice = -1;

        if (dataProvider.prices!=null && dataProvider.prices.size() == 2) {
            String _minPrice = dataProvider.prices.get(0);
            if (!"Any".equalsIgnoreCase(_minPrice)) {
                _minPrice = HdbPriceActivity.prices.get(_minPrice);
                minPrice = Integer.parseInt(_minPrice);
            }

            String _maxPrice = dataProvider.prices.get(1);
            if (!"Any".equalsIgnoreCase(_maxPrice)) {
                _maxPrice = HdbPriceActivity.prices.get(_maxPrice);
                maxPrice = Integer.parseInt(_maxPrice);
            }
        }

        if (HelloHdbActivity.activityNotStop == false) { progressDialog.dismiss();return true; }

        results = HdbCache.getInstance().getSummary(hdbRoom, hdbTown, minPrice, maxPrice);
        if (results != null && results.size() > 0) {
            String _hdbTown = "";
            for ( String key : HdbTownActivity.towns.keySet() ) {
                String value = HdbTownActivity.towns.get(key);
                if (value.equals(hdbTown)) _hdbTown = key;
            }

            populateGroupItems(_hdbTown);
            populateChildItems();
        }

        //we only support multiple towns selections for now...
        totalTowns++;
        int totalRequestedTowns = dataProvider.towns.size();
        if (totalRequestedTowns == totalTowns) {
            progressDialog.dismiss();

            ExpandableListAdapter adapter = new SimpleExpandableListAdapter(this,
                    groupItems, R.layout.summary2_group_items, new String[]{"town_group"}, new int[] { R.id.summary2_group_name },
                    childItems, R.layout.summary2_child_items, new String[]{"town_value1","town_value2","town_value3","town_value4"}, new int[] { R.id.summary2_text1,R.id.summary2_text2,R.id.summary2_text3,R.id.summary2_text4 });

            elv.setAdapter(adapter);
        }
        
        return true;
    }
}