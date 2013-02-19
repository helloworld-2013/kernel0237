/* ========================================================
 * DetailActivity.java
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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DetailActivity extends Activity {

    ListView lv = null;
    DetailAdapter adapter = null;
    List<HdbDTO> results = new ArrayList<HdbDTO>();
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        HelloHdbActivity.activityNotStop = true;

        HdbCache.getInstance(this);
        HdbCache.getInstance().initDb();

        Bundle params = this.getIntent().getExtras();
        String block = params.getString("block");
        String street = params.getString("street");
        
        if (HelloHdbActivity.activityNotStop == false) return;
        
        results = HdbCache.getInstance().getDetail(block,street);

        adapter = new DetailAdapter(this);
        lv = (ListView)findViewById(R.id.detail_list);
        lv.setAdapter(adapter);

        Button button1 = (Button)findViewById(R.id.detail_button1);
        button1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        setTitle(block + " - " + street);
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

    class DetailAdapter extends ArrayAdapter<HdbDTO> {
        
        Activity context;
        DetailAdapter(Activity context) {
            super(context, R.layout.detail_items, results);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;

            if (convertView == null) {
                LayoutInflater inflator = context.getLayoutInflater();
                view = inflator.inflate(R.layout.detail_items, null);

                DetailViewHolder viewHolder = new DetailViewHolder();
                viewHolder.textView2 = (TextView)view.findViewById(R.id.detail_text2);
                viewHolder.textView4 = (TextView)view.findViewById(R.id.detail_text4);
                viewHolder.textView6 = (TextView)view.findViewById(R.id.detail_text6);
                viewHolder.textView8 = (TextView)view.findViewById(R.id.detail_text8);
                viewHolder.textView10 = (TextView)view.findViewById(R.id.detail_text10);

                view.setTag(viewHolder);
            } else {
                view = convertView;
            }
            
            DetailViewHolder holder = (DetailViewHolder)view.getTag();
            holder.textView2.setText(results.get(position).storey);
            holder.textView4.setText(results.get(position).flatModel);
            holder.textView6.setText(results.get(position).leaseCommenceDate);

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            nf.setCurrency(Currency.getInstance("SGD"));
            holder.textView8.setText("" + nf.format(results.get(position).resalePrice));

            holder.textView10.setText(results.get(position).resaleApprovalDate);

            return view;
        }

    }

    class DetailViewHolder {
        public TextView textView2;
        public TextView textView4;
        public TextView textView6;
        public TextView textView8;
        public TextView textView10;
    }
    
}