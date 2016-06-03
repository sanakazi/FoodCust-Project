package com.example.foodcust;
/*
 This activity detects user location . Also, it allows 
 user to enter location manually
 */

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import com.example.foodcust.adapter.AssociateRestFragAdapter;
import com.example.foodcust.location.GPSTracker;
import com.example.foodcust.location.LocationAddress;
import com.example.foodcust.models.AssociateRestFragModel;
import com.example.foodcust.restaurantservice.HttpRequest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AssociateRestFrag extends Fragment implements AbsListView.OnScrollListener{
	
	ListView list_rest;
    TextView textView;
    Button btnEdit;
    GPSTracker gps;
   public double latitude,longitude;
   JSONArray  jsonArray ;
   JSONObject jsonObject;
   String RestaurantId,RestaurantName,RestaurantImage;
   AssociateRestFragAdapter adapter;
   ArrayList<AssociateRestFragModel> arr;
   String strtext;
   String res;
   public boolean isSuccess = false;
    int currentPage=1;
    View footer;
    String getValue;
    boolean loadingMore = false;
    String locationAddress;
    ProgressDialog dialog;
   public interface CallBack{
	   
	   public void getLocResult();
   }
   
   
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 View v = inflater.inflate(R.layout.activity_location, container, false);
		
		 textView = (TextView)v.findViewById(R.id.textView);
	
	//	 getValue = getArguments().getString("text");
	//	 textView.setText(getValue);
	
	     list_rest = (ListView)v.findViewById(R.id.list_rest) ;
	    
	     footer = ((LayoutInflater)getActivity()
	    		    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
	    		    .inflate(R.layout.progress, null, false);
	  //   list_rest.addFooterView(footer);
	     
	     btnEdit = (Button)v.findViewById(R.id.btnEdit);
	     btnEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				/*if(getActivity() instanceof CallBack)
					((CallBack)getActivity()).getLocResult();
				else
					throw new RuntimeException("Error is there");*/
				
				Intent intent1 = new Intent(getActivity(),EditLocationActivity.class);
				startActivityForResult(intent1, 1);
			}
		});
	     arr= new  ArrayList<AssociateRestFragModel>();
	    
          gps = new GPSTracker(getActivity());
          
          if(gps.canGetLocation()){
              
              latitude = gps.getLatitude();
              longitude = gps.getLongitude();
             LocationAddress locationAddress = new LocationAddress();
             locationAddress.getAddressFromLocation(latitude, longitude,
                     getActivity(), new GeocoderHandler());
             dialog = ProgressDialog.show(getActivity(), "","Please wait..", true);
          
           //  Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
         }else{
             // can't get location
             // GPS or Network is not enabled
             // Ask user to enable GPS/network in settings
             gps.showSettingsAlert();
         }
	
           list_rest.setOnScrollListener(this);
  
		 return v;
         }
	
	

    private class GeocoderHandler extends Handler {
    	 
    
    	
        @Override
        public void handleMessage(Message message) {
          
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
          textView.setText(locationAddress);
          dialog.dismiss();
        //   Toast.makeText(getActivity(), locationAddress, Toast.LENGTH_SHORT).show();
        }
    }

    class AsyncWS extends AsyncTask{
    	
		
		@Override
		protected Object doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			
			showDetails();
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			/*   try {
	                Thread.sleep(2000);
	 
	            } catch (InterruptedException e) {
	            }
	            
	            */
			int position = list_rest.getFirstVisiblePosition();
			  adapter=new AssociateRestFragAdapter(getActivity(), arr );
	          list_rest.setAdapter( adapter );
	        
	        
	      	list_rest.setSelectionFromTop(position, 0);
	        list_rest.removeFooterView(footer);
	          adapter.notifyDataSetChanged();
	        
	         
			}
    
    }
   
    private void showDetails() {
		// TODO Auto-generated method stub
    	HttpRequest hr = new HttpRequest();
    	try{
    		
    		 loadingMore = true;
    		
    		ContentValues cv = new ContentValues();
			// vp.onPageSelected(position);
			
			cv.put("PageIndex", currentPage);
    		//cv.put("PageIndex", "1");
			Log.d("Page Index",""+ currentPage);
			cv.put("PageSize", "6");
			currentPage++;
			 res = hr.getDataFromServer(cv, "AssociateRestaurantMasterListByPageSize");
			isSuccess = true;
			
		
			if(isSuccess)
			{
			jsonArray = new JSONArray(res);
				Log.d("Json Array", jsonArray.toString());
				 for(int i=0; i < jsonArray.length(); i++)
				 {
					 loadingMore = false;
					 jsonObject = jsonArray.getJSONObject(i);
			    //     Log.d("Json object:", jsonObject.toString());
			         RestaurantId = jsonObject.optString("RestaurantId").toString();
		           //  Log.d("RestaurantId",RestaurantId.toString());
		             RestaurantName = jsonObject.optString("RestaurantName").toString();
		           //  Log.d("RestaurantName",RestaurantName.toString());
		             RestaurantImage = jsonObject.optString("RestaurantImage").toString();
		          //   Log.d("RestaurantImage",RestaurantImage.toString());
		             AssociateRestFragModel sched = new AssociateRestFragModel();
		             sched.setRestaurantName(RestaurantName);
		         //    sched.setRestaurantImage(RestaurantImage);
		             arr.add( sched);
				 //    adapter.add(sched);
		            
				 } 
				 }
                   
	              else
	           {
		Toast.makeText(getActivity(), "No associated Restaurants", Toast.LENGTH_SHORT).show();
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
    
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	switch(requestCode)
    	{
    	case 0 :  textView.setText(strtext);
    			break;
    	case 1 : 
    		 //String strtext = getArguments().getString("location");  
    		 strtext= data.getStringExtra("location");
    		 textView.setText(strtext);
    		Log.d("AssRestFrag value is", strtext);
    		break;
    		
    	}
    }


    @Override
	public void onScroll(AbsListView absListView, int firstVisible, int visibleCount, int totalCount) {
		// TODO Auto-generated method stub
	int lastItem = firstVisible + visibleCount;
	//Log.d("Inside scroll", firstVisible +","+ visibleCount +","+ totalCount );
	
	 //is the bottom item visible & not loading more already? Load more!
	
	if(lastItem==totalCount && !loadingMore)
	  {
		Log.d("inside scroll true condition", "lastitem = "+lastItem + "   totalcount= "+ totalCount + "  loadingmore= " + !loadingMore);
		list_rest.addFooterView(footer);
		// loadingMore = true;
		  AsyncWS task = new AsyncWS();
  	      task.execute();
	  }
	
	
	
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int arg1) {
		// TODO Auto-generated method stub
		
	}
	

}

	
