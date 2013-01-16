package mylab.hdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class HelloHdbActivity extends Activity {

    public static boolean activityNotStop;

    HdbDataProvider dataProvider = new HdbDataProvider();
    
    ListView lv = null;
    HelloHdbAdapter adapter = null;
    List<String> selections = new ArrayList<String>();
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        activityNotStop = true;

        // prepare for 3 inputs from 3 activities...
        selections.add("empty");
        selections.add("empty");
        selections.add("empty");
        
        adapter = new HelloHdbAdapter(this);
        lv = (ListView)findViewById(R.id.main_list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> a, View b, int position,
                                    long d) {
                Intent target = null;
                int activityCode = -1;

                if (position == 0) {
                    target = new Intent(HelloHdbActivity.this, HdbTownActivity.class);
                    activityCode = HdbTownActivity.HDB_TOWN_ACTIVITY;
                } else if (position == 1) {
                    target = new Intent(HelloHdbActivity.this, HdbRoomTypeActivity.class);
                    activityCode = HdbRoomTypeActivity.HDB_ROOM_ACTIVITY;
                } else if (position == 2) {
                    target = new Intent(HelloHdbActivity.this, HdbPriceActivity.class);
                    activityCode = HdbPriceActivity.HDB_PRICE_ACTIVITY;
                }

                HelloHdbActivity.this.startActivityForResult(target, activityCode);
            }
        });

        Button button1 = (Button)findViewById(R.id.main_button1);
        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (dataProvider.towns != null && dataProvider.towns.size() > 0 &&
                    dataProvider.rooms != null && dataProvider.rooms.size() > 0 && 
                    dataProvider.prices != null && dataProvider.prices.size() > 0) {

                    Bundle params = new Bundle();
                    params.putStringArrayList(String.valueOf(HdbRoomTypeActivity.HDB_ROOM_ACTIVITY), dataProvider.rooms);
                    params.putStringArrayList(String.valueOf(HdbTownActivity.HDB_TOWN_ACTIVITY), dataProvider.towns);
                    params.putStringArrayList(String.valueOf(HdbPriceActivity.HDB_PRICE_ACTIVITY), dataProvider.prices);

                    Class summaryClass = SummaryActivity.class;

                    //we only support multiple towns selections for now...
                    if (dataProvider.towns.size() > 1) {
                        Summary2Activity.Summary2GroupBy howToGroup = Summary2Activity.Summary2GroupBy.NONE_GROUP;
                        howToGroup = Summary2Activity.Summary2GroupBy.HDB_TOWN_GROUP;
                        summaryClass=Summary2Activity.class;
                        params.putString("howToGroup",howToGroup.getGroupBy());
                    }

                    Intent target = new Intent(HelloHdbActivity.this, summaryClass);
                    target.putExtras(params);
                    HelloHdbActivity.this.startActivity(target);

                }
                
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == HdbTownActivity.HDB_TOWN_ACTIVITY && data != null) {
            dataProvider.towns = data.getStringArrayListExtra(String.valueOf(HdbTownActivity.HDB_TOWN_ACTIVITY));

            if (dataProvider.towns != null && dataProvider.towns.size() > 0) {
                // there can be multiple towns selections
                String towns = "";
                for (String town : dataProvider.towns) {
                    towns += town + ", ";
                }
                if (towns.endsWith(", ")) towns = towns.substring(0,towns.length() - 2);
                if (towns.length() > 40) { towns = towns.substring(0,40);towns += "..."; }

                selections.set(0, towns);
            }

            Log.d("HelloHdbActivity", "Towns : " + dataProvider.towns);
        } else if (requestCode == HdbRoomTypeActivity.HDB_ROOM_ACTIVITY && data != null) {
            dataProvider.rooms = data.getStringArrayListExtra(String.valueOf(HdbRoomTypeActivity.HDB_ROOM_ACTIVITY));

            if (dataProvider.rooms != null && dataProvider.rooms.size() > 0) {
                // for now there is only one room can be selected at once...
                selections.set(1, dataProvider.rooms.get(0));
            }
            
            Log.d("HelloHdbActivity", "Rooms : " + dataProvider.rooms);
        } else if (requestCode == HdbPriceActivity.HDB_PRICE_ACTIVITY && data != null) {
            dataProvider.prices = data.getStringArrayListExtra(String.valueOf(HdbPriceActivity.HDB_PRICE_ACTIVITY));

            String priceRange = "empty";
            if (dataProvider.prices != null && dataProvider.prices.size() == 2) {
                String price1 = dataProvider.prices.get(0);
                String price2 = dataProvider.prices.get(1);

                if ("Any".equals(price1) && "Any".equals(price2)) {
                    priceRange = "Any";
                } else {
                    priceRange = price1 + " - " + price2;
                }

                selections.set(2, priceRange);
            }
            
            Log.d("HelloHdbActivity", "Prices : " + priceRange);
        }
        
        adapter = new HelloHdbAdapter(this);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        activityNotStop = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        activityNotStop = false;
    }

    class HelloHdbAdapter extends ArrayAdapter<String> {
        
        Activity context;
        HelloHdbAdapter(Activity context) {
            super(context, R.layout.main_items, selections);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            
            if (convertView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                view = inflater.inflate(R.layout.main_items, null);
                
                HelloHdbViewHolder _holder = new HelloHdbViewHolder();
                _holder.textView1 = (TextView)view.findViewById(R.id.text1);
                _holder.textView2 = (TextView)view.findViewById(R.id.text2);
                
                view.setTag(_holder);
            } else {
                view = convertView;
            }
            
            HelloHdbViewHolder holder = (HelloHdbViewHolder)view.getTag();
            switch (position) {
                case 0:
                    holder.textView1.setText("Hdb Town");
                    break;
                case 1:
                    holder.textView1.setText("Room Type");
                    break;
                case 2:
                    holder.textView1.setText("Resale Price");
                    break;
            }
            
            holder.textView2.setText( selections.get(position) );
            
            return view;
        }
    }
    
    class HelloHdbViewHolder {
        public TextView textView1;
        public TextView textView2;
    }
    
}