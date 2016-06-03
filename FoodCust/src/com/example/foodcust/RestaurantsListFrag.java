package com.example.foodcust;
/*
  This is a Fragment containing custom listview which shows a list of restaturants.
   It uses  RestaurantListAdapter and RestaurantListModel
 */
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import com.example.foodcust.adapter.RestaurantListAdapter;
import com.example.foodcust.models.RestaurantListModel;
import com.example.foodcust.webservices.HttpRequest;

import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class RestaurantsListFrag extends Fragment {
 String RestaurantName,RestaurentImage,Distance,RestaurantTimeStatus,LongAddress,
 MorningStartTime,MorningEndTime,EveningStartTime,EveningEndTime,AverageRating;
 ListView list;
 String RestaurantId;
 public boolean isSuccess = false;
// AssociateRestFrag loc = new AssociateRestFrag();
  RestaurantListAdapter adapter;
  String resp,strJson;
  Resources res;
  ArrayList<RestaurantListModel> arr;
  double latitude, longitude;
  AssociateRestFrag loc;
  JSONArray jsonArray;
  JSONObject jsonObject;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		 View v = inflater.inflate(R.layout.main, container, false);
		 
			arr = new ArrayList<RestaurantListModel>();
			  list= ( ListView )v.findViewById( R.id.list );
			  res =getResources();
			  
			  loc = new AssociateRestFrag();
			  latitude= loc.latitude;
			  longitude = loc.longitude;
			 
	               Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
	          
	        AsyncWS task = new AsyncWS();
	        task.execute();
	        list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View v,
						int position, long id) {
					 
					// TODO Auto-generated method stub
					long data = list.getItemIdAtPosition(position);
					String idx=String.valueOf(data);
				//	Intent intent = new Intent(getActivity(),SelectCusineByRestaurantId.class);
					Intent intent = new Intent(getActivity(),ViewPagerSelectCusineByRestaurantIdActivity.class);
					try {
						intent.putExtra("restaurantid", jsonArray.optJSONObject(position).getString("RestaurantId"));
						Log.d("restaurant id is" , jsonArray.optJSONObject(position).getString("RestaurantId").toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        
					 startActivity(intent);
				}
			});
			return v;
	}
	
	class AsyncWS extends AsyncTask
	{	ProgressDialog dialog;

	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog = new ProgressDialog(getActivity());
		//dialog.setIndeterminate(true);
	    dialog.setCancelable(false);
		dialog.setMessage("Please wait...");

		dialog.show();

	}
		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			setListData();
			
			return list;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			

			dialog.dismiss();
			  adapter=new RestaurantListAdapter(getActivity(), arr );
	          list.setAdapter( adapter );
	          Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
	         
			}
			
	}
	
	public void setListData()
	{
		HttpRequest hr = new HttpRequest();
		try {

			ContentValues cv = new ContentValues();
			// vp.onPageSelected(position);
			
			cv.put("PageIndex", "1");
			cv.put("PageSize", "10");
			cv.put("Latitude", "28.613939");
			cv.put("Longitude", "77.209021"); 
			//cv.put("Latitude", latitude);
			//cv.put("Longitude",longitude); 
			String res = hr.getDataFromServer(cv, "RestuarentSearch");
			Log.d("Res" , res);
			isSuccess = true;
		//	return res;
			
			if(isSuccess)
			{
				 jsonArray = new JSONArray(res);
				Log.d("Json Array", jsonArray.toString());
				 for(int i=0; i < jsonArray.length(); i++)
				 {
					 jsonObject = jsonArray.getJSONObject(i);
			       
			         RestaurantId = jsonObject.optString("RestaurantId").toString();
		             Log.d("RestaurantId",RestaurantId.toString());
		             RestaurantName = jsonObject.optString("RestaurantName").toString();
		             Distance = jsonObject.optString("Distance").toString();
		             RestaurantTimeStatus = jsonObject.optString("RestaurantTimeStatus").toString();
		             LongAddress= jsonObject.optString("LongAddress").toString();
		             MorningStartTime = jsonObject.optString("MorningStartTime").toString();
		             MorningEndTime = jsonObject.optString("MorningEndTime").toString();
		             EveningStartTime = jsonObject.optString("EveningStartTime").toString();
		             EveningEndTime = jsonObject.optString("EveningEndTime").toString();
		             AverageRating = jsonObject.optString("AverageRating").toString();
		             RestaurentImage= jsonObject.optString("RestaurentImage").toString();
		             
		             RestaurantListModel sched = new RestaurantListModel();
		             sched.setRestaurantName(RestaurantName);
		             sched.setDistance(Distance);
		             sched.setRestaurantTimeStatus(RestaurantTimeStatus);
		             sched.setLongAddress(LongAddress);
		             sched.setMorningStartTime(MorningStartTime);
		             sched.setMorningEndTime(MorningEndTime);
		             sched.setEveningStartTime(EveningStartTime);
		             sched.setEveningEndTime(EveningEndTime);
		             sched.setAverageRating(AverageRating);
		             sched.setRestaurentImage(RestaurentImage);
		             arr.add( sched);
		        //     Conf.arrayList.add(sched);
		            
				 }
			}
			else
			{
				Toast.makeText(getActivity(), "No associated Restaurants", Toast.LENGTH_SHORT).show();
			}
			  Log.d("Elements", arr.toString());
			 
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

	
	
}
