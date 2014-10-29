package com.example.jiaxiami;

import com.example.jiaxiami.data.Food;
import com.example.jiaxiami.data.FoodDAO;
import com.example.jiaxiami.data.FoodDAOImpl;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Food_detail extends Activity {

	TextView tvName, tvAddr, tvTel, tvMoney , tvlocation;
	int ID;
	float distance;
	String bestProv;
	LocationManager lm;
	Location location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_detail);
		
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		bestProv = lm.getBestProvider(criteria, true);
		
		Intent intent = getIntent();
		ID = intent.getIntExtra("ID", 0);
		distance = intent.getFloatExtra("distance", 0);
//		x = intent.getDoubleExtra("x", 0);
//		y = intent.getDoubleExtra("y",0);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		FoodDAO dao = new FoodDAOImpl(this);
		Food f = dao.getFood(ID);
		
		tvName = (TextView) findViewById(R.id.textViewname);
		tvAddr = (TextView) findViewById(R.id.textViewAddr);
		tvTel = (TextView) findViewById(R.id.textViewTel);
		tvMoney = (TextView) findViewById(R.id.textViewmoney);
		tvlocation = (TextView) findViewById(R.id.textViewlocation);
		tvName.setText(f.Name);
		tvAddr.setText(f.Addr);
		tvTel.setText(f.Tel);
		tvMoney.setText("" + f.money);
		tvlocation.setText("("+distance+")");

//		Geocoder gc = new Geocoder(this, Locale.TRADITIONAL_CHINESE);
//        try {
//			List<Address> isAddress = gc.getFromLocation(25.0432481302915,121.5436671, 1);
//			Address = isAddress.get(0).getAddressLine(0);
//			Log.d("address",""+Address);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//        catch(java.lang.IndexOutOfBoundsException e){
//			e.printStackTrace();
//		}

	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_edit) {
			Intent intent = new Intent(Food_detail.this, Food_edit.class);
			intent.putExtra("ID", ID);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
		
