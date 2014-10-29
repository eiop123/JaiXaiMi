package com.example.jiaxiami;


import java.util.Arrays;
import java.util.Comparator;

import com.example.jiaxiami.StoreList.FoodCompareMoney;
import com.example.jiaxiami.data.Food;
import com.example.jiaxiami.data.FoodDAO;
import com.example.jiaxiami.data.FoodDAOImpl;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


public class MainActivity extends Activity {
	Food[] data;
	Context context;
	
	ImageButton bt1;
	ImageButton bt2;
	ImageButton bt3;
	ImageView iv ;
	ImageView iv2;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
      bt1 = (ImageButton) findViewById(R.id.imagebutton1);
      bt2 = (ImageButton) findViewById(R.id.imagebutton2);
      bt3 = (ImageButton) findViewById(R.id.imagebutton3);
      iv2 = (ImageView) findViewById (R.id.imageView2);
      Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.table2);
		 android.graphics.Bitmap.Config bitmapConfig =
   	      bitmap.getConfig();
		 if(bitmapConfig == null) {
   	    bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
   	  }
		
		 bitmap = bitmap.copy(bitmapConfig, true);
		  Canvas canvas = new Canvas(bitmap);
	       Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	       paint.setColor(Color.rgb(61, 61, 61));
	       paint.setTextSize((int) (55));
	paint.setTypeface(Typeface.DEFAULT_BOLD);
	 canvas.save();
     
     canvas.rotate(150,250,250);
     canvas.drawText("餐廳1" , 150,330, paint);
     canvas.save();
     canvas.rotate(-60,250,250);
     canvas.drawText("餐廳2" , 310,400, paint);
     canvas.save();
     canvas.rotate(-60,250,250);
     canvas.drawText("餐廳3" , 350,550, paint);
     canvas.save();
     canvas.rotate(-60,250,250);
     canvas.drawText("餐廳4" , 230,680, paint);
     canvas.save();
     canvas.rotate(-60,250,250);
     canvas.drawText("餐廳5" , 50,630, paint);
     canvas.save();
     canvas.rotate(-60,250,250);
     canvas.drawText("餐廳6" , 0,450, paint);
     canvas.save();iv =(ImageView) findViewById(R.id.imageView1);
     iv.setImageBitmap(bitmap);
    bt2.setOnClickListener(new ImageButton.OnClickListener(){

		@Override
		public void onClick(View v) {
			int [] aa = {30,90,150,210,270,330};
			 int random;

			  
			  random =  (int) ((Math.random()*7-1));
			  int b = aa[random];
			  
	      int stopturnnum = b +1800;
			
			Animation am = new RotateAnimation(0,stopturnnum, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);



           am.setDuration(stopturnnum);


   

            am.setFillAfter(true);


      

            iv.setAnimation(am);
            



            am.startNow(); 

     
			
		}});
//		Button bt = (Button) findViewById(R.id.buttonEdit);
//		Button test = (Button) findViewById(R.id.button1);
	bt1.setOnClickListener(new ImageButton.OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this,StoreList.class);
			startActivity(intent);
			
			
		}});
				
		
//		test.setOnClickListener(new Button.OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				 FoodDAO dao = new FoodDAOImpl(MainActivity.this);
//			     data = dao.getAll();
//			     try{
//			     Arrays.sort(data, new RandomFoodCompareMoney());
//			     TextView txtrandom =(TextView) findViewById(R.id.textViewlocation);
//			     txtrandom.setText(data[0].Name);
//			     }catch(Exception e){
//			    	 e.getStackTrace();
//			     }
//			}});
		bt3.setOnClickListener(new ImageButton.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				int [] aa = {30,90,150,210,270,330};
				 int random;

				  
				  random =  (int) ((Math.random()*7-1));
				  int b = aa[random];
				  
		      int stopturnnum = b +1800;
				
				Animation am = new RotateAnimation(0,stopturnnum, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);



	           am.setDuration(stopturnnum);


	   

	            am.setFillAfter(true);


	      

	            iv.setAnimation(am);
	            



	            am.startNow(); 
				
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
		if (id == R.id.action_explain) {
			Intent it = new Intent(MainActivity.this, HelpActivity.class);
			startActivity(it);
		}
		return super.onOptionsItemSelected(item);
	}

	
	
}