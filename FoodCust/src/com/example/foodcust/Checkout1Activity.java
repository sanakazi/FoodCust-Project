package com.example.foodcust;
/*
  This is checkout activity.
  when user selects the items of his choice and clicks on checkout,selected items gets stored in database.
  This class is used to create a custom listview and populate listview using data from database.
  It uses Checkout1ActivityAdapter and Checkout1ViewHolder.java.
  Also you can change value of quantity of items of database from this listview.
  
  */
import java.util.ArrayList;

import com.example.foodcust.adapter.Checkout1ActivityAdapter;
import com.example.foodcust.models.database.DatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Checkout1Activity extends Activity {
ListView list;
DatabaseHelper db;
ArrayList<String> arr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout1);
		list=(ListView)findViewById(R.id.list);
		db = new DatabaseHelper(this);
		Cursor c = db.getReadableDatabase().query(DatabaseHelper.Food.TABLE_FOOD, null, null, null, null, null, null);
	
		/*	
		 THE DESIRED COLUMNS TO BE BOUND
        String[] columns = new String[] {DatabaseHelper.Food.NAME, DatabaseHelper.Food.DESC,DatabaseHelper.Food.PRICE,DatabaseHelper.Food.TYPE, DatabaseHelper.Food.COUNT};
         THE XML DEFINED VIEWS WHICH THE DATA WILL BE BOUND TO
        int[] to = new int[] { R.id.inner,R.id.inner_txt2 ,R.id.inner_txt4,R.id.inner_img,R.id.inner_txtresult };
	
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.cusine_submenu_inner, c,
				columns, to,0);
		*/
        Checkout1ActivityAdapter adapter =  new Checkout1ActivityAdapter(this, c,0);
	//	arr = new ArrayList<String>();
	//	Checkout1ActivityAdapter adapter =  new Checkout1ActivityAdapter(this, arr);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> myAdapter, View v, int position,
					long id) {
				// TODO Auto-generated method stub
		        
		        Log.d("selected", "");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkout1, menu);
		return true;
	}

	public void addToCart(String count,String key) {
		// TODO Auto-generated method stub
		 ContentValues values = new ContentValues();
		// values.put(DatabaseHelper.Food.COL_ID, 894);
		 
		 int key1 = Integer.parseInt(count);
		 if(key1<10)
		 {
			 key1=key1+1;
		 }
		 String key2 =String.valueOf(key1);
	
		 values.put(DatabaseHelper.Food.COUNT, key2);
		 Log.d("Add to cart ", count + "and " + key );
		db.getWritableDatabase().update(DatabaseHelper.Food.TABLE_FOOD, values, DatabaseHelper.Food.COL_ID + "=" + key,null);
		Cursor c = db.getReadableDatabase().query(DatabaseHelper.Food.TABLE_FOOD, null, null, null, null, null, null);
        Checkout1ActivityAdapter adapter =  new Checkout1ActivityAdapter(this, c,0);
        list.setAdapter(adapter);
	}

	public void minusFromCart(String count, String key) {
		// TODO Auto-generated method stub
		 ContentValues values = new ContentValues();
			// values.put(DatabaseHelper.Food.COL_ID, 894);
		 int key1 = Integer.parseInt(count);
		 if(key1>0)
		 {
			 key1=key1-1;
		 }
		 String key2 =String.valueOf(key1);
			 values.put(DatabaseHelper.Food.COUNT, key2);
			 Log.d("Subtract from cart ", count + "and " + key );
			db.getWritableDatabase().update(DatabaseHelper.Food.TABLE_FOOD, values, DatabaseHelper.Food.COL_ID + "=" + key,null);
			Cursor c = db.getReadableDatabase().query(DatabaseHelper.Food.TABLE_FOOD, null, null, null, null, null, null);
	        Checkout1ActivityAdapter adapter =  new Checkout1ActivityAdapter(this, c,0);
	        list.setAdapter(adapter);
	}

	
	
}
