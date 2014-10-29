package com.example.jiaxiami;

import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Food_set_Location extends Activity implements LocationListener{
	

	
	LocationManager lm;
	String bestProv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		bestProv = lm.getBestProvider(criteria, true);
	}
	@Override
	protected void onResume() {
		super.onResume();
		 if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)||lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
	        	lm.requestLocationUpdates(bestProv, 1000, 1, this);
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
	public void onLocationChanged(Location location) {
		String x = Double.toString(location.getLongitude());
		String y = Double.toString(location.getLongitude());
		LatLng point = new LatLng(location.getLatitude(),location.getLongitude());
		Log.d("location","open");
		Toast.makeText(this, "open", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Criteria criteria = new Criteria();
		bestProv = lm.getBestProvider(criteria, true);
	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}
	
}
