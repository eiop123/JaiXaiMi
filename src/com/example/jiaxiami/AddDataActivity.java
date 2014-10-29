package com.example.jiaxiami;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

public class AddDataActivity extends Activity {

	double lon, lat;
	EditText etName, etAddr, etTel, etMoney; //TODO Note
	Button btAdd, btCancel;
	ImageView img;
	RatingBar rb1; //TODO incomplete
	Context context;
	ImageButton btnPhoto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_data);
		
		
		context = this;
		etName = (EditText) findViewById(R.id.editTextName);
		etAddr = (EditText) findViewById(R.id.editTextAddr);
		etTel = (EditText) findViewById(R.id.editTextTel);
		etMoney = (EditText) findViewById(R.id.editTextMoney);
		btnPhoto = (ImageButton) findViewById(R.id.imageButton1);
		img = (ImageView) findViewById(R.id.imageView1);
//		btAdd = (Button) findViewById(R.id.buttonEdit);
//		btCancel = (Button) findViewById(R.id.buttonCancel);
		
//		btAdd.setOnClickListener(new Button.OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				FoodDAO dao = new FoodDAOImpl(context);
//				dao.add(new Food(0, etName.getText().toString(), etAddr.getText().toString(), etTel.getText().toString(), etMoney.getText().toString()));
//				finish();
//			}});
//		
//		btCancel.setOnClickListener(new Button.OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				finish();
//			}});
//		
//		iv1.setOnClickListener(new ImageView.OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Incomplete
//			}});
	}
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
		    	Uri uri = Uri.parse(mCurrentPhotoPath);
		    	img.setImageURI(uri);
		    }
	}
	

	static final int REQUEST_TAKE_PHOTO = 1;
	
	private void dispatchTakePictureIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        // Create the File where the photo should go
	        File photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException ex) {
	            // Error occurred while creating the File

	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	                    Uri.fromFile(photoFile));
	            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
	        }
	    }
	}
	
String mCurrentPhotoPath;
	
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    File image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	    return image;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_data, menu);
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
