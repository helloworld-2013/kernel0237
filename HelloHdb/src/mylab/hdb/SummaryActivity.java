/* ========================================================
 * SummaryActivity.java
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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends Activity implements Handler.Callback {

    HdbDataProvider dataProvider = new HdbDataProvider();

    ListView lv = null;
    SummaryAdapter adapter = null;
    List<HdbDTO> results = new ArrayList<HdbDTO>();

    ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);

        HelloHdbActivity.activityNotStop = true;

        HdbCache.getInstance(this);
        HdbCache.getInstance().initDb();

        Bundle params = this.getIntent().getExtras();
        ArrayList<String> paramRooms = params.getStringArrayList(String.valueOf(HdbRoomTypeActivity.HDB_ROOM_ACTIVITY));
        ArrayList<String> paramTowns = params.getStringArrayList(String.valueOf(HdbTownActivity.HDB_TOWN_ACTIVITY));
        ArrayList<String> paramPrices = params.getStringArrayList(String.valueOf(HdbPriceActivity.HDB_PRICE_ACTIVITY));
        
        dataProvider.rooms = paramRooms;
        dataProvider.towns = paramTowns;
        dataProvider.prices = paramPrices;
        dataProvider.requestHdbData(new Handler(this));

        adapter = new SummaryAdapter(this);
        lv = (ListView)findViewById(R.id.summary_list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener( new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View b, int position, long d) {
                HdbDTO result = results.get(position);

                Bundle params = new Bundle();
                params.putString("block", result.block);
                params.putString("street", result.street);

                Intent target = new Intent(SummaryActivity.this, DetailActivity.class);
                target.putExtras(params);
                SummaryActivity.this.startActivity(target);
            }
        });

        Button button1 = (Button)findViewById(R.id.summary_button1);
        button1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }            
        });

        progressDialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
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
    public boolean handleMessage(Message message) {
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
        adapter = new SummaryAdapter(this);
        lv.setAdapter(adapter);

        Log.d("SummaryActivity", "Total : " + results.size());
        Log.d("SummaryActivity", "Finished Room : " + hdbRoom);
        Log.d("SummaryActivity", "Finished Town : " + hdbTown);

        progressDialog.dismiss();
        
        return true;
    }

    class SummaryViewHolder {
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;
    }

    class SummaryAdapter extends ArrayAdapter<HdbDTO> {
        
        Activity context;
        SummaryAdapter(Activity context) {
            super(context,R.layout.summary_items,results);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;

            if (convertView == null) {
                LayoutInflater inflator = context.getLayoutInflater();
                view = inflator.inflate(R.layout.summary_items, null);

                SummaryViewHolder viewHolder = new SummaryViewHolder();
                viewHolder.textView1 = (TextView)view.findViewById(R.id.summary_text1);
                viewHolder.textView2 = (TextView)view.findViewById(R.id.summary_text2);
                viewHolder.textView3 = (TextView)view.findViewById(R.id.summary_text3);
                viewHolder.textView4 = (TextView)view.findViewById(R.id.summary_text4);

                view.setTag(viewHolder);
            } else {
                view = convertView;
            }

            SummaryViewHolder holder = (SummaryViewHolder) view.getTag();

            HdbDTO result = results.get(position);
            holder.textView1.setText(result.block + " - " + result.street);
            holder.textView2.setText("Min: " + formatPrice(result.minPrice));
            holder.textView3.setText("Max: " + formatPrice(result.maxPrice));
            holder.textView4.setText("#Tx: " + result.totalTransactions);

            return view;
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
    }

}
