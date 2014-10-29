package com.example.jiaxiami;

import java.io.File;

import com.example.jiaxiami.data.Food;
import com.example.jiaxiami.data.FoodDAO;
import com.example.jiaxiami.data.FoodDAOImpl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class Food_picture extends Activity {
	
	ImageView iv;
	int ID,Position;
	Food[] data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_picture);
		
		iv = (ImageView) findViewById(R.id.imageView1);
		Intent intent = getIntent();
		ID = intent.getIntExtra("ID", 0);
		Position = intent.getIntExtra("Position", Position);
		FoodDAO dao = new FoodDAOImpl(this);
		Food f = dao.getFood(ID);
		
		Log.d("abc",ID+""+Position );
		
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "p" + f.ID + ".jpg");
		 if (file.exists())
	        {
	        	Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + "p" + f.ID + ".jpg");
	        	iv.setImageBitmap(bm);
	        }
	        else{
 			iv.setImageResource(R.drawable.ic_launcher);
 		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_picture, menu);
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}
	
	
}
