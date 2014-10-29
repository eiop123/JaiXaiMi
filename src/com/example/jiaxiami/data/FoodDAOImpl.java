package com.example.jiaxiami.data;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FoodDAOImpl implements FoodDAO {

	Context context;
	
	public FoodDAOImpl(Context context) {
		this.context = context;
	}
	
	@Override
	public int add(Food f) {
		int i;
		FoodDBHelper helper = new FoodDBHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("Insert into listdata (Name, Addr, Tel, money) values ('" + f.Name +  "', '" + f.Addr + "', '" + f.Tel + "', '" + f.money + "')");
		Cursor cursor = db.rawQuery("Select last_insert_rowid()",null);
		cursor.moveToFirst();
		i = cursor.getInt(0);
		db.close();
		return i;
	}

	@Override
	public Food[] getAll() {
		ArrayList<Food> list = new ArrayList();
		FoodDBHelper helper = new FoodDBHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("Select * From listdata", null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			list.add(new Food(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4)));
			cursor.moveToNext();
		}
		db.close();
		
		Food[] rtValue = new Food[list.size()];
		list.toArray(rtValue);
		return rtValue;
	}

	@Override
	public Food getFood(int ID) {
		FoodDBHelper helper = new FoodDBHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("Select * From listdata where ID=" + ID, null);
		cursor.moveToFirst();
		Food f = new Food(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
		db.close();
		return f;
	}

	@Override
	public void removeAll() {
		FoodDBHelper helper = new FoodDBHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from listdata");
		db.close();
	}

	@Override
	public void delete(int ID) {
		FoodDBHelper helper = new FoodDBHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from listdata where ID= " + ID);
		db.close();
		
	}
	
	

	@Override
	public Food[] search(String keyword) {
		ArrayList<Food> list = new ArrayList();
		FoodDBHelper helper = new FoodDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From listdata where Name like '%" + keyword +"%'", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
        	list.add(new Food(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4)));
        	cursor.moveToNext();
        }
        db.close();
        
        Food[] rtValue = new Food[list.size()];
        list.toArray(rtValue);
        return rtValue;
}

	@Override
	public void edit(Food f) {
		FoodDBHelper helper = new FoodDBHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("Update listdata Set Name = '" + f.Name + "', Addr = '" + f.Addr + "', Tel = '" + f.Tel + "', Money = '" + f.money + "'Where ID = " + f.ID);
	}
}
