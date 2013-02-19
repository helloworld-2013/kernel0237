/* ========================================================
 * HdbRoomTypeActivity.java
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
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HdbRoomTypeActivity extends Activity {
	
	public static int HDB_ROOM_ACTIVITY = 200;
	
	public static Map<String,String> rooms = new LinkedHashMap<String,String>();
	private List<HdbRoomModel> roomsModel = new ArrayList<HdbRoomModel>();

	public ArrayList<String> getSelectedRooms() {
		ArrayList<String> results = new ArrayList<String>();
		
		for (HdbRoomModel result : roomsModel) {
			if (result.isSelected) results.add(result.key);
		}
		
		return results;
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.room);
    	
    	prepareRoomsModel();
    	
    	final ListView lv = (ListView)findViewById(R.id.room_list);
    	lv.setAdapter(new HdbRoomAdapter(this));
    	lv.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> a, View b, int position,
					long d) {
				for (int i = 0;i < roomsModel.size();i++)
					roomsModel.get(i).isSelected = false;

				roomsModel.get(position).isSelected = true;
				
				((HdbRoomAdapter)lv.getAdapter()).notifyDataSetChanged();
			}
        	
        });
    	
    	Button button1 = (Button)findViewById(R.id.room_button1);
    	button1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				Intent resultIntent = new Intent();
				resultIntent.putStringArrayListExtra(String.valueOf(HDB_ROOM_ACTIVITY), getSelectedRooms());
				setResult(HDB_ROOM_ACTIVITY, resultIntent);
				
				finish();
			}
        	
        });
    }

    private void prepareRoomsModel() {
    	rooms.put("1 Room", "01");
    	rooms.put("2 Room", "02");
    	rooms.put("3 Room", "03");
    	rooms.put("4 Room", "04");
    	rooms.put("5 Room", "05");
    	rooms.put("Executive", "06");
    	rooms.put("HUDC", "07");
    	rooms.put("Multi-Generation", "08");
    	
    	for ( String key : rooms.keySet() ) {
    		HdbRoomModel model = new HdbRoomModel();
    		model.key = key;
    		model.value = rooms.get(key);
    		model.isSelected = false;
    		
    		roomsModel.add(model);
    	}
    }
    
    class HdbRoomAdapter extends ArrayAdapter<HdbRoomModel> {
    	
    	private Activity context;
    	
    	public HdbRoomAdapter(Activity context) {
    		super(context, R.layout.room_items, roomsModel);
    		this.context = context;
    	}
    	
    	@Override
		public View getView(int position, View convertView, ViewGroup parent) {
    		View view = null;
			if (convertView == null) {
				LayoutInflater inflator = context.getLayoutInflater();
				view = inflator.inflate(R.layout.room_items, null);
				
				HdbRoomViewHolder viewHolder = new HdbRoomViewHolder();
				viewHolder.textView1 = (CheckedTextView)view.findViewById(R.id.room_text1);
				
				view.setTag(viewHolder);
			} else {
				view = convertView;
			}
			
			HdbRoomViewHolder holder = (HdbRoomViewHolder)view.getTag();
			holder.textView1.setText(roomsModel.get(position).key);
			holder.textView1.setChecked(roomsModel.get(position).isSelected);
			
			return view;
    	}
    	
    }
    
    class HdbRoomViewHolder {
    	public CheckedTextView textView1;
    }
    
    class HdbRoomModel {
    	public String key;
    	public String value;
    	public boolean isSelected;
    }
	
}
