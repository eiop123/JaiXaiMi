package com.example.jiaxiami.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDBHelper extends SQLiteOpenHelper {

	final static String DB_NAME = "Food";
	final static int DB_VERSION = 1;
	
	public FoodDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE listdata(ID integer Primary Key AUTOINCREMENT, Name varchar(30), Addr varchar(30), Tel varchar(20), money varchar(10))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int ov, int nv) {

	}

}



