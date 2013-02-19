/* ========================================================
 * HdbPriceActivity.java
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
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HdbPriceActivity extends Activity {
    
    public static int HDB_PRICE_ACTIVITY = 300;

    public static Map<String,String> prices = new LinkedHashMap<String,String>();
    private List<PriceDialogModel> pricesModel = new ArrayList<PriceDialogModel>();
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price);

        preparePricesModel();

        InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        final EditText text1 = (EditText)findViewById(R.id.price_edit1);
        mgr.hideSoftInputFromWindow(text1.getWindowToken(), 0); // hide soft keyboard
        text1.setOnClickListener( new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = createDialog(text1);
                dialog.show();
            }
        });

        final EditText text2 = (EditText)findViewById(R.id.price_edit2);
        mgr.hideSoftInputFromWindow(text2.getWindowToken(), 0); // hide soft keyboard
        text2.setOnClickListener( new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = createDialog(text2);
                dialog.show();
            }
        });
        
        Button button1 = (Button)findViewById(R.id.price_button1);
        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String _text1 = text1.getText().toString();
                if (_text1 == null || "".equals(_text1)) _text1 = "Any";
                String _text2 = text2.getText().toString();
                if (_text2 == null || "".equals(_text2)) _text2 = "Any";

                ArrayList<String> results = new ArrayList<String>();
                results.add(_text1);
                results.add(_text2);

                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra(String.valueOf(HDB_PRICE_ACTIVITY), results);
                setResult(HDB_PRICE_ACTIVITY, resultIntent);
                
                finish();
            }

        });
    }

    private Dialog createDialog(final EditText caller) {
        LayoutInflater inflater = LayoutInflater.from(HdbPriceActivity.this);
        View view = inflater.inflate(R.layout.price_dialog, null);

        final ListView lv = (ListView)view.findViewById(R.id.price_list);
        lv.setAdapter(new PriceDialogAdapter(this));
        lv.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View b, int position,
                                    long d) {
                for (int i = 0;i < pricesModel.size();i++) 
                    pricesModel.get(i).isSelected = false;

                pricesModel.get(position).isSelected = true;

                ((PriceDialogAdapter)lv.getAdapter()).notifyDataSetChanged();
            }
            
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(HdbPriceActivity.this);
        builder.setTitle("Select Price");
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (int j = 0;j < pricesModel.size();j++)
                    if (pricesModel.get(j).isSelected) {
                        caller.setText(pricesModel.get(j).key);
                    }
            }
            
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }

        });
        
        AlertDialog dialog = builder.create();

        return dialog;
    }

    private void preparePricesModel() {
        prices.put("Any", "Any");
        prices.put("100,000", "100000");
        prices.put("200,000", "200000");
        prices.put("300,000", "300000");
        prices.put("400,000", "400000");
        prices.put("500,000", "500000");
        prices.put("600,000", "600000");
        prices.put("700,000", "700000");
        prices.put("800,000", "800000");
        prices.put("900,000", "900000");
        prices.put("1m", "1000000");

        for ( String key : prices.keySet() ) {
            PriceDialogModel model = new PriceDialogModel();
            model.key = key;
            model.value = prices.get(key);
            model.isSelected = false;

            pricesModel.add(model);
        }
    }

    class PriceDialogAdapter extends ArrayAdapter<PriceDialogModel> {
        
        Activity context;
        PriceDialogAdapter(Activity context) {
            super(context, R.layout.price_items, pricesModel);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;

            if (convertView == null) {
                LayoutInflater inflator = context.getLayoutInflater();
                view = inflator.inflate(R.layout.price_items, null);

                PriceDialogViewHolder viewHolder = new PriceDialogViewHolder();
                viewHolder.textView1 = (CheckedTextView)view.findViewById(R.id.price_text3);

                view.setTag(viewHolder);
            } else {
                view = convertView;
            }

            PriceDialogViewHolder holder = (PriceDialogViewHolder)view.getTag();
            holder.textView1.setText(pricesModel.get(position).key);
            holder.textView1.setChecked(pricesModel.get(position).isSelected);

            return view;
        }
    }

    class PriceDialogViewHolder {
        public CheckedTextView textView1;
    }
    
    class PriceDialogModel {
        public String key;
        public String value;
        public boolean isSelected;
    }

}