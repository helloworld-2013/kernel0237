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

public class HdbTownActivity extends Activity {
	
	public static int HDB_TOWN_ACTIVITY = 100;
	
	public static Map<String,String> towns = new LinkedHashMap<String,String>();
    private List<HdbTownModel> townsModel = new ArrayList<HdbTownModel>();
	
	public ArrayList<String> getSelectedTowns() {
		ArrayList<String> results = new ArrayList<String>();
		
		for (HdbTownModel result : townsModel) {
			if (result.isSelected) results.add(result.key);
		}
		
		return results;
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.town);

    	prepareTownsModel();
    	
    	final ListView lv = (ListView)findViewById(R.id.town_list);
    	lv.setAdapter(new HdbTownAdapter(this));
    	lv.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> a, View b, int position,
					long d) {
                boolean isSelected = townsModel.get(position).isSelected;

				townsModel.get(position).isSelected = !isSelected;
				
				((HdbTownAdapter)lv.getAdapter()).notifyDataSetChanged();
			}
        	
        });
    	
    	Button button1 = (Button)findViewById(R.id.town_button1);
    	button1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				Intent resultIntent = new Intent();
				resultIntent.putStringArrayListExtra(String.valueOf(HDB_TOWN_ACTIVITY), getSelectedTowns());
				setResult(HDB_TOWN_ACTIVITY, resultIntent);
				
				finish();
			}
        	
        });
    	
    }
    
    private void prepareTownsModel() {
    	towns.put("Ang Mo Kio", "AMK     Ang Mo Kio");
    	towns.put("Bedok", "BD      Bedok");
    	towns.put("Bishan", "BH      Bishan");
    	towns.put("Bukit Batok", "BB      Bukit Batok");
    	towns.put("Bukit Merah", "BM      Bukit Merah");
    	towns.put("Bukit Panjang", "BP      Bukit Panjang");
    	towns.put("Bukit Timah", "BT      Bukit Timah");
    	towns.put("Central Area", "CT      Central Area");
    	towns.put("Choa Chu Kang", "CCK     Choa Chu Kang");
    	towns.put("Clementi", "CL      Clementi");
    	towns.put("Geylang", "GL      Geylang");
    	towns.put("Hougang", "HG      Hougang");
    	towns.put("Jurong East", "JE      Jurong East");
    	towns.put("Jurong West", "JW      Jurong West");
    	towns.put("Kallang/Whampoa", "KWN     Kallang/Whampoa");
    	towns.put("Marine Parade", "MP      Marine Parade");
    	towns.put("Pasir Ris", "PRC     Pasir Ris");
    	towns.put("Punggol", "PG      Punggol");
    	towns.put("Queenstown", "QT      Queenstown");
    	towns.put("Sembawang", "SB      Sembawang");
    	towns.put("Sengkang", "SK      Sengkang");
    	towns.put("Serangoon", "SGN     Serangoon");
    	towns.put("Tampines", "TAP     Tampines");
    	towns.put("Toa Payoh", "TP      Toa Payoh");
    	towns.put("Woodlands", "WL      Woodlands");
    	towns.put("Yishun", "YS      Yishun");

    	for ( String key : towns.keySet() ) {
    		HdbTownModel model = new HdbTownModel();
    		model.key = key;
    		model.value = towns.get(key);
    		model.isSelected = false;
    		
    		townsModel.add(model);
    	}
    }

    class HdbTownAdapter extends ArrayAdapter<HdbTownModel> {
    	
    	private Activity context;
    	
    	public HdbTownAdapter(Activity context) {
    		super(context, R.layout.town_items, townsModel);
    		this.context = context;
    	}
    	
    	@Override
		public View getView(int position, View convertView, ViewGroup parent) {
    		View view = null;
			if (convertView == null) {
				LayoutInflater inflator = context.getLayoutInflater();
				view = inflator.inflate(R.layout.town_items, null);
				
				HdbTownViewHolder viewHolder = new HdbTownViewHolder();
				viewHolder.textView1 = (CheckedTextView)view.findViewById(R.id.town_text1);
				
				view.setTag(viewHolder);
			} else {
				view = convertView;
			}
			
			HdbTownViewHolder holder = (HdbTownViewHolder)view.getTag();
			holder.textView1.setText(townsModel.get(position).key);
			holder.textView1.setChecked(townsModel.get(position).isSelected);
			
			return view;
    	}
    	
    }
    
    class HdbTownViewHolder {
    	public CheckedTextView textView1;
    }
    
    class HdbTownModel {
    	public String key;
    	public String value;
    	public boolean isSelected;
    }
	
}
