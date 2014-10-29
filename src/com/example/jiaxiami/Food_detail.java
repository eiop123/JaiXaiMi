package com.example.jiaxiami;

import com.example.jiaxiami.data.Food;
import com.example.jiaxiami.data.FoodDAO;
import com.example.jiaxiami.data.FoodDAOImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Food_detail extends Activity {

	TextView tvName, tvAddr, tvTel, tvMoney;
	int ID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_detail);
		
		Intent intent = getIntent();
		ID = intent.getIntExtra("ID", 0);
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
		tvName.setText(f.Name);
		tvAddr.setText(f.Addr);
		tvTel.setText(f.Tel);
		tvMoney.setText(f.money);
		
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
