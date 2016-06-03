package com.example.foodcust.models.database;

import java.util.ArrayList;
import java.util.List;

import com.example.foodcust.models.CusineSubMenuItemModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	 // Database Version
    public static final int DATABASE_VERSION = 1;
 
    // Database Name
    public static final String DATABASE_NAME = "foodManager";
 
  

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		Log.d("Inside helper constructor","YES");
	}
	
	
	
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		 Log.d("Inside oncreate helper","YES");
		 db.execSQL(Food.CREATE_TABLE);
		
	}

	





	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d("Inside helper upgrade","YES");
		 db.execSQL("Drop Table if exists " + Food.TABLE_FOOD);
		 
	        // Create tables again
	        onCreate(db);
	        
	}
	
	
	



public static class Food{
	
	  // Contacts table name
    public static final String TABLE_FOOD = "food";
 
    // Contacts Table Columns names
    public static final String COL_ID = "_id";
  //  public static final String KEY_ID = "foodItemIdTopass";		
    public static final String NAME = "fname";
    public static final String DESC = "fdes";
    public static final String PRICE = "price2";
    public static final String OPTION_ID = "optionId";
    public static final String COUNT = "quantity";
    public static final String TYPE= "ftype";
    
	
	/*public static String CREATE_TABLE = "CREATE TABLE " + TABLE_FOOD + "("
             + KEY_ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
             + DESC + " TEXT" + PRICE + " TEXT,"
             + OPTION_ID + " TEXT,"
             + COUNT + " TEXT,"
             + TYPE + " TEXT," +")";
    */
    
    
   /* public static String CREATE_TABLE = "Create Table " + TABLE_FOOD + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_ID + " Text not null,"
            + NAME + " Text not null,"
            + DESC + " Text not null,"
            + PRICE + " Text not null,"
            + TYPE + " Text not null,"
            + COUNT + " Text not null)";
    */
 
    public static String CREATE_TABLE = "Create Table " + TABLE_FOOD + "("
            + COL_ID + " STRING PRIMARY KEY ,"
           
            + NAME + " Text not null,"
            + DESC + " Text not null,"
            + PRICE + " Text not null,"
            + TYPE + " Text not null,"
            + COUNT + " Text not null)";
            
}

	

}
