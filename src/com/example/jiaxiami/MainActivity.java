package com.example.jiaxiami;


import java.util.Arrays;
import java.util.Comparator;

import com.example.jiaxiami.StoreList.FoodCompareMoney;
import com.example.jiaxiami.data.Food;
import com.example.jiaxiami.data.FoodDAO;
import com.example.jiaxiami.data.FoodDAOImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
	Food[] data ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button bt = (Button) findViewById(R.id.buttonEdit);
		Button test = (Button) findViewById(R.id.button1);
		bt.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,StoreList.class);
				startActivity(intent);
				
			}});
		test.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 FoodDAO dao = new FoodDAOImpl(MainActivity.this);
			     data = dao.getAll();
			     try{
			     Arrays.sort(data, new RandomFoodCompareMoney());
			     TextView txtrandom =(TextView) findViewById(R.id.textViewlocation);
			     txtrandom.setText(data[0].Name);
			     }catch(Exception e){
			    	 e.getStackTrace();
			     }
			}});
	}
	
	class RandomFoodCompareMoney implements Comparator<Food>{

		@Override
		public int compare(Food f1, Food f2) {
			
			return (int) ((Math.random()*(f1.ID+100)) - (Math.random()*(f2.ID+100)));
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
}