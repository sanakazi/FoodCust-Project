
/*
 This activity generates Tabs which have swipe views.
 Each tab generated has a associated Fragment which is SelectCuisineFragment which generates an expandable listview.
 The SelectCuisineFragment has CusineSubMenuModel and CusineSubMenuAdapter.
 CusineSubmenuItemModel is for the childviews inside the expandable listview
 */

package com.example.foodcust;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodcust.adapter.CusineSubmenuAdapter;
import com.example.foodcust.models.CusineSubMenuItemModel;
import com.example.foodcust.models.CusineSubmenuModel;
import com.example.foodcust.models.database.DatabaseHelper;
import com.example.foodcust.restaurantservice.HttpRequest;

public class ViewPagerSelectCusineByRestaurantIdActivity extends FragmentActivity implements ActionBar.TabListener,OptionsDialogFrag.OptionsDialogListener {
	  AppSectionsPagerAdapter mAppSectionsPagerAdapter;
      ViewPager mViewPager;
      TextView txtnumber,txtrupee; 
      Button img4;
      RelativeLayout layout;
  	String RestaurantId,IsActive;
	static String FoodMasterId;
	String FoodMenuName;
	String FoodSubMenuName;
	String FoodSubMenuId;
	String HotelId;
	String FoodItemName;
	String foodDesc;
	String OptionId;
	String Price;
	String OptionsValue = "0";
	double sum=0.0;
	 DatabaseHelper db;
  	public boolean isSuccess = false;
      static ArrayList<String> arr;
      ArrayList<String> arrPrice;
      ArrayList<String> arrdesc;
     
     
     ArrayList<CusineSubmenuModel> arr1;
     ArrayList<CusineSubMenuItemModel> arr1_child;
     CusineSubmenuAdapter adapter;
     public static String restaurant_id;
      static Resources res;

    
      String text=null;
  
      SelectCuisineFragment frag2 = new SelectCuisineFragment();
      static JSONArray jsonArray;
      JSONObject jsonObject;
      ActionBar actionBar;
      
    
      HashMap<String, Double> hmap = new HashMap<String, Double>();
      
   // hashMap with multiple values with default size and load factor
      HashMap<String, ArrayList<String>> multiMap = new HashMap<String, ArrayList<String>>();
      
      boolean blnExists = false;
      boolean visibility=true;
      
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager_select_cusine_by_restaurant_id);
		 restaurant_id=getIntent().getStringExtra("restaurantid").toString();
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
        actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mViewPager = (ViewPager) findViewById(R.id.pager);
         db = new DatabaseHelper(this);
        layout=(RelativeLayout)findViewById(R.id.layout);
        txtnumber=(TextView)findViewById(R.id.txtnumber);
        txtrupee=(TextView)findViewById(R.id.txtrupee);
	   //    txtnumber.setText(String.valueOf(cartItem));
	     
	       arr1 = new ArrayList<CusineSubmenuModel>();
	      
	       res =getResources();
	       img4=(Button)findViewById(R.id.img4);
	       img4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				db.getWritableDatabase().delete(DatabaseHelper.Food.TABLE_FOOD, null,null);
		 for (Entry<String, ArrayList<String>> entry : multiMap.entrySet()) {
	         String key = entry.getKey();
			 ContentValues values = new ContentValues();
			values.put(DatabaseHelper.Food.COL_ID, key);
			 List<String> values1 = entry.getValue();
			 System.out.println("Key = " + key);
			System.out.println("Values = " + values1 + "n");
			            
		values.put(DatabaseHelper.Food.NAME, values1.get(0));
	 values.put(DatabaseHelper.Food.DESC, values1.get(1));
		 values.put(DatabaseHelper.Food.PRICE, values1.get(2));
		values.put(DatabaseHelper.Food.TYPE, values1.get(3));
		values.put(DatabaseHelper.Food.COUNT,values1.get(4));
		
		Cursor c = db.getReadableDatabase().query(DatabaseHelper.Food.TABLE_FOOD, null,  
		DatabaseHelper.Food.COL_ID + "=" + key, null, null, null, null);
		 
		if(c.getCount() <= 0)//record doesnot exists
		{
          Log.d("Record doesnot exists", "add");
          db.getWritableDatabase().insertOrThrow(DatabaseHelper.Food.TABLE_FOOD, null, values);
			
        }
		else
			 Log.d("Record  exists", "update");
			db.getWritableDatabase().update(DatabaseHelper.Food.TABLE_FOOD, values, DatabaseHelper.Food.COL_ID + "=" + key,null); 
		 }
				 
				
				Intent intent = new Intent(ViewPagerSelectCusineByRestaurantIdActivity.this,Checkout1Activity.class);
				startActivity(intent);
			}
		});
	       
	     AsyncWS task = new AsyncWS();
		 task.execute();
		 
       
		  mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
	            @Override
	            public void onPageSelected(int position) {
	                // When swiping between different app sections, select the corresponding tab.
	                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
	                // Tab.
	                actionBar.setSelectedNavigationItem(position);
	            }
	        });
	  
	       
	}
	
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(
				R.menu.view_pager_select_cusine_by_restaurant_id, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		 mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	class AsyncWS extends AsyncTask
	{

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			setListData();

			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			   mViewPager.setAdapter(mAppSectionsPagerAdapter);
			   
			     
			     if(arr.size()==0){
			    
			    	 text = " ";
			    		actionBar.addTab(
			                    actionBar.newTab()
			                            .setText(text)
			                            .setTabListener(ViewPagerSelectCusineByRestaurantIdActivity.this));
			     }
			     else{
				 // For each of the sections in the app, add a tab to the action bar.
		        for (int i = 0; i < arr.size(); i++) {
		            // Create a tab with text corresponding to the page title defined by the adapter.
		            // Also specify this Activity object, which implements the TabListener interface, as the
		            // listener for when this tab is selected.
		        	
		        	text= arr.get(i);
		        	actionBar.addTab(
		                    actionBar.newTab()
		                            .setText(text)
		                            .setTabListener(ViewPagerSelectCusineByRestaurantIdActivity.this));
		        }

			}
	 }
			
}
	
	
	
	public void setListData()
	{
		arr = new ArrayList<String>();
		HttpRequest hr = new HttpRequest();
		try {

			ContentValues cv = new ContentValues();
			// vp.onPageSelected(position);
			
			//cv.put("RestaurantId", "4");
			cv.put("RestaurantId", restaurant_id);
			
			String res = hr.getDataFromServer(cv, "selectCusineByRestaurantId");
			Log.d("res", res);
			isSuccess = true;
		//	return res;
			
			if(isSuccess)
			{
				JSONObject  jsonRootObject = new JSONObject(res);
				jsonArray = jsonRootObject.optJSONArray("Table");
				//JSONArray jsonArray = new JSONArray(res);
				
				 for(int i=0; i < jsonArray.length(); i++)
				 {
					 jsonObject = jsonArray.getJSONObject(i);
			         RestaurantId = jsonObject.optString("RestaurantId").toString();
		             FoodMenuName = jsonObject.optString("FoodMenuName").toString();
		             FoodMasterId = jsonObject.optString("FoodMasterId").toString(); 
		          
		             IsActive = jsonObject.optString("IsActive").toString();
		             arr.add(FoodMenuName);
		             
		            
		           
		             
				 }
			}
			else
			{
				Toast.makeText(this, "No associated Restaurants", Toast.LENGTH_SHORT).show();
			}
			  
			 
		} 
		catch (SocketTimeoutException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (SocketException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		} 
		catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		 catch (JSONException e) {e.printStackTrace();}
	}
	
	
	
	
	
	
	
	
	

	
	/**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public  static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
        	 
            super(fm);
          
        }
      

        @Override
        public Fragment getItem(int i) {
        	
        	 Fragment fragment = new SelectCuisineFragment();
        	 Log.d("Inside Fragment", "Inside Fragment");
        	 Log.d("Inside Fragment int",""+ i);
        	 Bundle  bundle = new Bundle();
     		 bundle.putString("rest",restaurant_id);
     		 Log.d("restaurant_id",restaurant_id);
     		try {
    			 
     		
       		    if(arr.size()==0)
       		    	
       			 bundle.putString("cusine_id","0");
       		 else
           		 
				{bundle.putString("cusine_id",jsonArray.optJSONObject(i).getString("FoodMasterId") );
    		    Log.d("cusine_id",""+FoodMasterId);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     		
     		 
        	
        	 fragment.setArguments(bundle);
             return fragment;
        }

        @Override
        public int getCount() {
              if(arr.size()>0)
        	return  arr.size();
              else
            	  return 1;
        }

    }
    

    
    public void onOptionsClick(String foodItemId) {
		// TODO Auto-generated method stub
    	
		 Bundle bundle= new Bundle();
		 bundle.putString("foodItemId",foodItemId);
		OptionsDialogFrag frag  =new OptionsDialogFrag();
		frag.setArguments(bundle);
 		frag.show(getFragmentManager(), " ");
 		
 	//	Toast.makeText(ViewPagerSelectCusineByRestaurantIdActivity.this, "FoodItem id is "+ foodItemId.toString() , Toast.LENGTH_SHORT).show();
	}

	public void addToCart(int quantity, String price2, String _id, String fname, String fdes, String ftype) {
		// TODO Auto-generated method stub
		 double price1 = Double.parseDouble(price2);
		
	/*	 ContentValues values = new ContentValues();
		 values.put(DatabaseHelper.Food.KEY_ID, Integer.parseInt(foodItemIdTopass));
		 values.put(DatabaseHelper.Food.NAME, fname);
		 values.put(DatabaseHelper.Food.DESC, fdes);
		 values.put(DatabaseHelper.Food.PRICE, price2);
		 values.put(DatabaseHelper.Food.TYPE, ftype);
		 values.put(DatabaseHelper.Food.COUNT, quantity);
		
		*/
		blnExists = hmap.containsKey(_id);
		if(!blnExists)				// if foodItemId is not present it will add it in the hashmap
    	{
    		 hmap.put(_id, quantity*price1 );
    		 Log.d(_id, quantity + "");
    		
    		 ArrayList<String> listOne = new ArrayList<String>();
    		 listOne.add(fname);
    		 listOne.add(fdes);
    		 listOne.add(price2);
    		 listOne.add(ftype);
    		 listOne.add(String.valueOf(quantity));
    		
    		 multiMap.put(_id, listOne);
    	       
    	 //       db.getWritableDatabase().insertOrThrow(DatabaseHelper.Food.TABLE_FOOD, null, values);
    		
    	}						// if foodItemId is already present it will update its value in the hashmap
		else
		{   
			
			hmap.remove(_id);
			multiMap.remove(_id);
			hmap.put(_id,  quantity*price1 );
			 Log.d(_id, quantity + "");
			
			 ArrayList<String> listOne = new ArrayList<String>();
    		 listOne.add(fname);
    		 listOne.add(fdes);
    		 listOne.add(price2);
    		 listOne.add(ftype);
    		 listOne.add(String.valueOf(quantity));
    	        multiMap.put(_id, listOne);  
			 
			// db.getWritableDatabase().update(DatabaseHelper.Food.TABLE_FOOD, values, DatabaseHelper.Food.KEY_ID + "=" + Integer.parseInt(foodItemIdTopass),null);
		}
		

		Log.d("values", hmap.values()+"");
		double sum=0.0;
		for (double d : hmap.values()) {
		    sum += d;
		    Log.d("sum plus", sum+"");
		}
		
		
		
		
		
		txtnumber.setText(String.valueOf(hmap.size()));
		 txtrupee.setText(String.valueOf(sum));
		if(hmap.size()==1 && visibility)
		{ layout.setVisibility(View.VISIBLE);  
			// Animation bottomUp = AnimationUtils.loadAnimation(SelectCusineByRestaurantId.this, R.anim.bottom_up);
			    TranslateAnimation slide = new TranslateAnimation(0, 0, 100,0 );   
			   slide.setDuration(500);   
			    slide.setFillAfter(true);   
			    layout.startAnimation(slide);  
			    visibility= false;
			    
		}
		
	}

	public void minusFromCart(int quantity, String price2, String _id, String fname, String fdes, String ftype) {
		// TODO Auto-generated method stub
		double price1 = Double.parseDouble(price2);
	/*	ContentValues values = new ContentValues();
		 values.put(DatabaseHelper.Food.KEY_ID, Integer.parseInt(_id));
		 values.put(DatabaseHelper.Food.PRICE, price2);
		 values.put(DatabaseHelper.Food.COUNT, quantity);
		*/ 
		   
		 blnExists = hmap.containsKey(_id);
		if(blnExists)
    	{
			hmap.remove(_id);
			multiMap.remove(_id);
			
			hmap.put(_id, quantity*price1 );
			 ArrayList<String> listOne = new ArrayList<String>();
			 listOne.add(fname);
    		 listOne.add(fdes);
    		 listOne.add(price2);
    		 listOne.add(ftype);
    		 listOne.add(String.valueOf(quantity));
    	        multiMap.put(_id, listOne);  
			
			
		//	 db.getWritableDatabase().update(DatabaseHelper.Food.TABLE_FOOD, values, DatabaseHelper.Food.KEY_ID + "=" + Integer.parseInt(foodItemIdTopass),null);
			if(quantity==0)
			{
				hmap.remove(_id);
				multiMap.remove(_id);
		//		db.getWritableDatabase().delete(DatabaseHelper.Food.TABLE_FOOD, DatabaseHelper.Food.KEY_ID + "=" +Integer.parseInt(foodItemIdTopass), null);
			}
    	}
		
		double sum=0.0;
		for (double d : hmap.values()) {
		    sum += d;
		    Log.d("sum minus", sum+"");
		}
		
		txtnumber.setText(String.valueOf(hmap.size()));
		 txtrupee.setText(String.valueOf(sum));
		if(hmap.size()==0 && !visibility)
		{     
			// Animation bottomdown = AnimationUtils.loadAnimation(SelectCusineByRestaurantId.this, R.anim.bottom_down);
			 TranslateAnimation slide = new TranslateAnimation(0, 0, 0,100 );
			  slide.setDuration(500);   
			    slide.setFillAfter(true);   
			    layout.startAnimation(slide);
			    layout.setVisibility(View.GONE);
			    visibility=true;
			// layout.startAnimation(bottomdown);
		}
	}
    
    
   
    @SuppressLint("ValidFragment")
	public static class SelectCuisineFragment extends Fragment {

    	private String mText;
    	String RestaurantId,IsActive,FoodMasterId,FoodMenuName,FoodSubMenuName,FoodSubMenuId,HotelId,FoodItemName
    	,foodDesc,OptionId,Price,FoodItemId,OptionCount,Type;
    	
    	
        ArrayList<String> arr;
        ArrayList<String> arrPrice;
        ArrayList<String> arrdesc;
        ExpandableListView lvExp;
       ArrayList<CusineSubmenuModel> arr1;
       ArrayList<CusineSubMenuItemModel> arr1_child;
        CusineSubmenuAdapter adapter;
        public String restaurant_id, cusine_id;
        Resources res;
        String[] var=null;
        String[] varDesc = null;
        String[] varPrice = null;
        public int count1 ;
        CusineSubMenuItemModel child ;
        JSONArray jsonArrayitem ;
        JSONObject jsonObjectitem;
    	
        
        
    	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    			Bundle savedInstanceState) {
    		// TODO Auto-generated method stub
    		
    		 View v = inflater.inflate(R.layout.cusine_submenu_expandable, container, false);
    		 lvExp = (ExpandableListView)v.findViewById(R.id.lvExp);
    	       arr1 = new ArrayList<CusineSubmenuModel>();
    	      res =getResources();
                 
    	         // restaurant_id = getArguments().getString("rest","0");
                   restaurant_id = getArguments().getString("rest"); 
    		     //  Toast.makeText(getActivity(), " restaurant_id   "+ restaurant_id, Toast.LENGTH_SHORT).show();
    	        
    		 cusine_id = getArguments().getString("cusine_id");
    		
    		     
    	   AsyncSubMenu task1 = new AsyncSubMenu();
    	   task1.execute();
    	  
    	 /*  lvExp.setOnChildClickListener(new OnChildClickListener() {
    			
    			@Override
    			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
    					int childPosition, long id) {
    				// TODO Auto-generated method stub
    				  Toast.makeText(getActivity(),
    							"Child"+ arr1.get(childPosition).toString(), Toast.LENGTH_LONG).show();
    					Log.d("On click i want",""+ childPosition);
    				//OptionsDialogFrag frag  =new OptionsDialogFrag();
    				//frag.show(getFragmentManager(), " ");
    				return false;
    			}
    		});
    	 */
    		return v;
            }
    	
    	class  AsyncSubMenu extends AsyncTask{

    		@Override
    		protected Object doInBackground(Object... arg0) {
    			// TODO Auto-generated method stub
    			setSubMenuData();
    			return null;
    		}
        	
    		@Override
    		protected void onPostExecute(Object result) {
    			// TODO Auto-generated method stub
    			super.onPostExecute(result);
    			  adapter=new CusineSubmenuAdapter( getActivity(), arr1);
    			  lvExp.setAdapter( adapter );
    			  
    		}
        }
    	
    	 
        public void setSubMenuData()
    	{
    		HttpRequest hr = new HttpRequest();
    		try {

    			ContentValues cv = new ContentValues();
    			// vp.onPageSelected(position);
    			
    			//cv.put("RestaurantId", "4");
    		//	cv.put("CusineId", "2");
    			//cv.put("RestaurantId", "20");
    			//cv.put("CusineId", "0");
    			cv.put("RestaurantId",restaurant_id);
    			cv.put("CusineId",cusine_id);
    			Log.d("CusineId cv", cusine_id.toString());
    			String res = hr.getDataFromServer(cv, "SelectFoodSubMenubyRestaurantandCusineId_ForApp");
    			
    			
    		//	return res;
    			
    			
    			
    				JSONObject  jsonRootObject = new JSONObject(res);
    				JSONArray jsonArray = jsonRootObject.optJSONArray("Table");
    				 
    				
    				
    				 for(int i=0; i < jsonArray.length(); i++)
    				 {
    					 JSONObject jsonObject = jsonArray.getJSONObject(i);
    			
    			        FoodSubMenuId = jsonObject.optString("FoodSubMenuId").toString();
    		            Log.d(" FoodSubMenuId", FoodSubMenuId.toString());
    		          
    		             FoodSubMenuName = jsonObject.optString("FoodSubMenuName").toString();
    		             Log.d("FoodSubMenuName",FoodSubMenuName.toString());
    		           
    		             
    		           /* FoodItemName= jsonObject.optString("FoodItemName").toString();
    			            Log.d("FoodItemName",  FoodItemName.toString());
    			         var= FoodItemName.split(",");
    			        
    			       foodDesc= jsonObject.optString("foodDesc").toString();
    			        Log.d(" foodDesc",   foodDesc.toString());
    			        varDesc= foodDesc.split(",");
    			          OptionId=jsonObject.optString("OptionId").toString();
    			           Log.d("OptionId", OptionId.toString());
    			            Price=jsonObject.optString("Price").toString();
    			            Log.d("Price",   Price.toString());
    			           varPrice= Price.split(",");
    			     */    
    			         CusineSubmenuModel sched = new CusineSubmenuModel();
    		             sched.setFoodSubMenuName( FoodSubMenuName);
    		             arr1_child = new ArrayList<CusineSubMenuItemModel>();
    		            
    		           
    		             setSubMenuItemData();
    		             
    		         /*    for(int j=0;j<var.length;j++)
    		             {  CusineSubMenuItemModel child = new CusineSubMenuItemModel();
    		            	 Log.d("FoodItem", var[j]);
    		            	//Log.d("FoodDesc", varDesc[j]);
    		            	// Log.d("Price", varPrice[j]);
    		            	 child.setFoodItemName(var[j]);
    		            	 child.setFoodDesc(varDesc[j]);
    		            	 child.setPrice(varPrice[j]);
    		            	 child.setOptionId(OptionId);
    		            	// Log.d("OptionId", OptionId.toString());
    		            
    		            	 arr1_child.add(child);
    		            	
    		             }
    		             
    		       */   
    		            
    		             
    		             sched.setItems(arr1_child);
    					 arr1.add(sched);
    					 
    					 		}
    		
    			 
    			 
    		} 
    		catch (SocketTimeoutException e) {
    			
    			e.printStackTrace();
    		} catch (SocketException e) {
    			
    			e.printStackTrace();
    		} catch (IOException e) {
    			
    			e.printStackTrace();
    		} 
    		catch (XmlPullParserException e) {
    			e.printStackTrace();
    		}
    		 catch (JSONException e) {e.printStackTrace();}
    	}
        
        public void setSubMenuItemData()
        {
        	HttpRequest hr1 = new HttpRequest();
    		try {

    			ContentValues cv1 = new ContentValues();
    			
    			cv1.put("RestaurantId",restaurant_id);
    			Log.d("RestaurantId",restaurant_id);
    			cv1.put("CuisineId", cusine_id);
    			Log.d("CuisineId", cusine_id);
    			cv1.put("FoodCategoryId",  FoodSubMenuId);
    			Log.d("FoodCategoryId is ",  FoodSubMenuId);
    			String res1 = hr1.getDataFromServer(cv1,"SelectFoodItembyRestaurantandFoodSubMenu");
    		//	Log.d("Res" , res1);
    			
    		//	return res;
    		
    			
    				JSONObject  jsonRootObject = new JSONObject(res1);
    				Log.d(" jsonRootObject" ,  jsonRootObject.toString());
    			 jsonArrayitem = jsonRootObject.optJSONArray("Table");
    				 
    				
    			
    			
    				 for(int i=0; i <jsonArrayitem .length(); i++)
    				 { 
    					 child = new CusineSubMenuItemModel();
    				   
    					  jsonObjectitem = jsonArrayitem.getJSONObject(i);
    			
    					 FoodItemId = jsonObjectitem.optString("FoodItemId").toString();
    		            Log.d(" FoodSubMenuId", FoodSubMenuId.toString());
    		          
    		           FoodItemName= jsonObjectitem.optString("FoodItemName").toString();
    			            Log.d("FoodItemName",  FoodItemName.toString());
    			     //    var= FoodItemName.split(",");
    			        
    			       foodDesc= jsonObjectitem.optString("FoodDesc").toString();
    			        Log.d(" foodDesc",   foodDesc.toString());
    			      
    			        OptionCount=jsonObjectitem.optString("OptionCount").toString();
    			           Log.d("OptionCount",OptionCount.toString());
    			            Price=jsonObjectitem.optString("Price").toString();
    			            Log.d("Price",   Price.toString());
    			       Type = jsonObjectitem.optString("Type").toString();
			            Log.d("Type",  Type.toString());
    			           // CusineSubMenuItemModel child = new CusineSubMenuItemModel();
    		            	
    		            	//Log.d("FoodDesc", varDesc[j]);
    		            	// Log.d("Price", varPrice[j]);
    			             child.setFoodItemId(FoodItemId);
    		            	 child.setFoodItemName(FoodItemName);
    		            	 child.setFoodDesc( foodDesc);
    		            	 child.setPrice( Price);
    		            	 child.setOptionId(OptionCount);
    		            	 child.setType(Type);
    		            	 arr1_child.add(child);
    		            	 
    							}
    				
    			 
    				
    			
    			 
    			 
    		} 
    		catch (SocketTimeoutException e) {
    			
    			e.printStackTrace();
    		} catch (SocketException e) {
    			
    			e.printStackTrace();
    		} catch (IOException e) {
    			
    			e.printStackTrace();
    		} 
    		catch (XmlPullParserException e) {
    			e.printStackTrace();
    		}
    		 catch (JSONException e) {e.printStackTrace();}
        }



    }



	
    
    @Override
	public void setOnOk(String name, String price, int selectedPosition) {
		// TODO Auto-generated method stub
		 Log.d("Inside Activity values are ", name.toString() + "and price is " + price.toString() + "and position is " + selectedPosition);
	}
    
   
   
}
