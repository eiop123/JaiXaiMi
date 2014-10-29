package com.example.jiaxiami;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import com.example.jiaxiami.data.Food;
import com.example.jiaxiami.data.FoodDAO;
import com.example.jiaxiami.data.FoodDAOImpl;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StoreList extends Activity implements LocationListener {
	
	Food[] data;
	Context context;
	ArrayList<Boolean> list ;
	ListAdapter adapter;
	LocationManager lm;
	String location;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_list);
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
		location = lm.getBestProvider(criteria, true);
		
		
		context = this;
	    list = new ArrayList<Boolean>();
		MyTest();
//		restartTAble();
		SpinView();
	}
	
	void SpinView(){
		
		Spinner condition_fliter = (Spinner) findViewById(R.id.spinner1); 
		ArrayAdapter spinAdapter = ArrayAdapter.createFromResource(StoreList.this,R.array.conditions,android.R.layout.simple_spinner_item);
		spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		condition_fliter.setAdapter(spinAdapter);
		condition_fliter.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				
				switch(position) {
				case 0:
					break;
				case 1:
					Arrays.sort(data, new FoodCompareMoney());
					adapter.notifyDataSetChanged();
					break;
				case 2:
					break;
				case 3:
					break;
				}
			}


			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	});}
	class FoodCompareMoney implements Comparator<Food>{

		@Override
		public int compare(Food f1, Food f2) {
			return f1.money - f2.money;
		}
		
	}

	void MyTest() {
		FoodDAO dao = new FoodDAOImpl(this);
		dao.add(new Food(0, "Rice", "ABCD", "123",220));
		dao.add(new Food(0, "Noodles", "ABCD", "123",100));
		dao.add(new Food(0, "Soup", "ABCD", "123",300));
		Food[] data = dao.getAll();
		for(int i = 0; i<data.length; i++) {
			Log.d("FOOD", data[i].Name + "," + data[i].Addr + "," + data[i].Tel + "," + data[i].money);
		}
//		dao.removeAll();

	    }

	
	
	 @Override
	    protected void onResume()
	    {
	    	super.onResume();
	        FoodDAO dao = new FoodDAOImpl(this);
	        data = dao.getAll();
	        for (int i=0;i<data.length;i++)
	        {
	        	list.add(false);
	        }
	        ListView lv = (ListView) findViewById(R.id.listView1);
	        adapter = new ListAdapter(this, data);
	        lv.setAdapter(adapter);

	        lv.setOnItemClickListener(new ListView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View v, int position,
						long id) {
					// TODO Auto-generated method stub
					int ID = data[position].ID;
					Intent it = new Intent(StoreList.this, Food_detail.class);
					it.putExtra("ID", ID);
					startActivity(it);
					// Toast.makeText(context, "" + position, Toast.LENGTH_LONG).show();
				}});

	        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)||lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
	        	lm.requestLocationUpdates(location, 1000, 1, this);
	        }
	        else {
	        	Toast.makeText(this, "請開啟定位服務 以方便使用更多服務", Toast.LENGTH_LONG).show();
	        }
	    }

    @Override
	protected void onPause() {
		super.onPause();
		lm.removeUpdates(this);
	}
    

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.store_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_add)
        {
        	Intent it = new Intent(StoreList.this, AddDataActivity.class);
        	startActivity(it);
        }
        
        if (id == R.id.action_delete)
        {
        	AlertDialog.Builder alert = new AlertDialog.Builder(context);
        	alert.setTitle("Please ensure");

        	alert.setPositiveButton("comfirm", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					FoodDAO dao = new FoodDAOImpl(context);
			        Log.d("PHONE", "Befor for loop");
					for (int i=0;i<list.size();i++)
					{
						if (list.get(i))
						{   
					        dao.delete(data[i].ID);
						}
					}
			        Log.d("PHONE", "Before data re-getAll");
			        data = dao.getAll();
			        Log.d("PHONE", "Before list Clear");
			        list.clear();
			        for (int i=0;i<data.length;i++)
			        {
			        	list.add(false);
			        }
			        Log.d("PHONE", "list Size:" + list.size() + ", data size:" + data.length);
			        adapter.data = data;
			        adapter.notifyDataSetChanged();
					
				}});
        	alert.setNegativeButton("Negative", null);
        	alert.show();
        	
        }
        
        if (id == R.id.action_search)
        {
        	
        	AlertDialog.Builder alert = new AlertDialog.Builder(this);
        	alert.setTitle("Input searching string:");
            final EditText input = new EditText(this);
            alert.setView(input);        	
        	alert.setPositiveButton("Comfirm", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
		            FoodDAO dao = new FoodDAOImpl(context);
		            data = dao.search(input.getText().toString());
		            for (int i=0;i<data.length;i++)
		            {
		            	list.add(false);
		            }
		            adapter.data = data;		            
		        	adapter.notifyDataSetChanged();
				}});
        	alert.setNegativeButton("Negative", null);
        	alert.show();
        	
	
        }
        return super.onOptionsItemSelected(item);
    }
    
    class ListAdapter extends BaseAdapter {

    	Food[] data;
    	Activity act;
    	private LayoutInflater inflater = null;
    	
    	public ListAdapter(Activity a, Food[] data) {
    		this.act = a;
    		this.data = data;
    		inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	}
    	
		@Override
		public int getCount() {
			
			return data.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View arg1, ViewGroup arg2) {
			View v = inflater.inflate(R.layout.mylist, null);
    		TextView tvname = (TextView) v.findViewById(R.id.textViewName);
    		TextView tvmoney = (TextView) v.findViewById(R.id.textViewMoney);
    		RatingBar rb = (RatingBar) v.findViewById(R.id.ratingBar1); // TODO uncompleted
    		tvname.setText(data[position].Name);
    		tvmoney.setText(String.valueOf(data[position].money));
    		
    		ImageView iv = (ImageView) v.findViewById(R.id.imageView1);
    		
    		
    		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "p" + data[position].ID + ".jpg");
	        
	        if (file.exists())
	        {
	        	Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + "p" + data[position].ID + ".jpg");
	        	Log.d("abc",Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+"p" + data[position].ID + ".jpg");
	        	iv.setImageBitmap(bm);
	        }
	        else{
    			iv.setImageResource(R.drawable.ic_launcher);
    		}

			CheckBox chk = (CheckBox) v.findViewById(R.id.checkBox1);
			chk.setChecked(list.get(position));
			chk.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					list.set(position, arg1);
				}});
			return v;
		}
    	
    }
    
	@Override
	public void onLocationChanged(Location location) {
		String x = Double.toString(location.getLongitude());
		String y = Double.toString(location.getLongitude());
		LatLng point = new LatLng(location.getLatitude(),location.getLongitude());
		Toast.makeText(StoreList.this, "open", Toast.LENGTH_SHORT);
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
    
}
