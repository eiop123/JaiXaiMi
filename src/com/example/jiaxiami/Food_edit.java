package com.example.jiaxiami;

import com.example.jiaxiami.data.Food;
import com.example.jiaxiami.data.FoodDAO;
import com.example.jiaxiami.data.FoodDAOImpl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class Food_edit extends Activity {

	int ID;
	EditText etName, etAddr, etTel, etMoney; //TODO Note
	Button btEdit, btCancel;
	RatingBar rb1;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_edit);
		
		ID = getIntent().getIntExtra("ID", 0);
		context = this;
		
		FoodDAO dao = new FoodDAOImpl(this);
		Food f = dao.getFood(ID);
		
		etName = (EditText) findViewById(R.id.editTextName);
		etAddr = (EditText) findViewById(R.id.editTextAddr);
		etTel = (EditText) findViewById(R.id.editTextTel);
		etMoney = (EditText) findViewById(R.id.editTextMoney);
		
		etName.setText(f.Name);
		etAddr.setText(f.Addr);
		etTel.setText(f.Tel);
		etMoney.setText("" + f.money);
		
		btEdit = (Button) findViewById(R.id.buttonadd);
		btCancel = (Button) findViewById(R.id.buttonCancel);
		
		btEdit.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				Food f = new Food(ID, etName.getText().toString(), etAddr.getText().toString(), etTel.getText().toString(), Integer.parseInt(etMoney.getText().toString()));
				FoodDAO dao = new FoodDAOImpl(context);
				dao.edit(f);
				finish();
			}});
		
		btCancel.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
