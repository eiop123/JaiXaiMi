package com.example.jiaxiami;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.os.StrictMode;
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
public class StoreList extends Activity implements LocationListener{
	
	Food[] data;
	Context context;
	ArrayList<Boolean> list ;
	ListAdapter adapter;
	LocationManager lm;
	String bestProv;
	Location location;
	double x,y;         //經緯度
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_list);

		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		bestProv = lm.getBestProvider(criteria, true);
		context = this;
	    list = new ArrayList<Boolean>();
		MyTest();
		SpinView();
		
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
        .detectDiskReads()  
        .detectDiskWrites()  
        .detectNetwork()  
        .penaltyLog()  
        .build());  
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
		dao.add(new Food(0, "Rice", "忠孝東路三段", "123",220));
		dao.add(new Food(0, "Noodles", "台灣", "123",100));
		dao.add(new Food(0, "Soup", "中國", "123",300));
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
					
			        FoodDAO dao = new FoodDAOImpl(StoreList.this);
			        int ID = data[position].ID;
			        Food f = dao.getFood(ID);
			        
					try {
			        String addr = java.net.URLEncoder.encode(f.Addr, "utf-8");
					HttpGet httpget = new HttpGet("http://maps.googleapis.com/maps/api/geocode/json?address="+addr);
					try {
						HttpClient client = new DefaultHttpClient();
						HttpResponse response = client.execute(httpget);
						HttpEntity entity = response.getEntity();
						String res = EntityUtils.toString(entity, "UTF-8");
						Log.d("NETWORK", res);
						
						JSONArray resArr;
						JSONObject resObj;
						
						resObj = new JSONObject(res);
						JSONArray obj2 = resObj.getJSONArray("results");
						JSONObject obj3 = obj2.getJSONObject(0);
						double lat = obj3.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
						double lng = obj3.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
						Log.d("NETWORK", "lat:" + lat);
						/*
						resArr = new JSONArray(res);
						resObj = resArr.getJSONObject(0);
						Log.d("NETWORK", resObj.getString("Riverside_Park"));
						*/
						Intent it = new Intent(StoreList.this, Food_detail.class);
						it.putExtra("ID", ID);
						it.putExtra("lag", lat);
						it.putExtra("lng", lng);
						it.putExtra("x", x);
						it.putExtra("y", y);
						startActivity(it);
					 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					

				}});

	        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)||lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
	        	lm.requestLocationUpdates(bestProv, 1000, 1, this);
	        	Log.d("setlocat","定位");

	        	location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	        	 if(location == null){
	            location=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	        }else {
	        	Toast.makeText(this, "請開啟定位服務 以方便使用更多服務", Toast.LENGTH_LONG).show();
	        }
	        	 try{
	        		 x = location.getLongitude();
	        		 y = location.getLongitude();

	 	            }catch(java.lang.NullPointerException e){
	 	            	e.getStackTrace();
	 	            }
	        	 }
	        Log.d("locat",x+","+y);
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
    		iv.setOnClickListener(new ImageView.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					int ID = data[position].ID;
					int Position = position;
					Intent intent = new Intent(StoreList.this,Food_picture.class);
					intent.putExtra("ID", ID);
					intent.putExtra("Position", Position);

					startActivity(intent);
					
				}});
    		
    		
    		
    		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "p" + data[position].ID + ".jpg");
	        if (file.exists())
	        {
	        	Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + "p" + data[position].ID + ".jpg");
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
		this.location = location;
		x = location.getLongitude();
		y = location.getLongitude();
//		LatLng point = new LatLng(location.getLatitude(),location.getLongitude());
		Log.d("location","open");
		Toast.makeText(this, "open", Toast.LENGTH_SHORT).show();
		
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
		Criteria criteria = new Criteria();
		bestProv = lm.getBestProvider(criteria, true);
		
	}
    
}
