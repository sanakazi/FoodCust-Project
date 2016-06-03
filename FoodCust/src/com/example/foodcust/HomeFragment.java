package com.example.foodcust;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;
import com.example.foodcust.restaurantservice.HttpRequest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class HomeFragment extends Fragment {
	public int currentimageindex=0 ;
	String RestaurentImage;
	ImageView img1;
   
	
	 ViewFlipper flipper;
	 ArrayList<String> arr =  new ArrayList<String>();
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		AsyncWS task= new AsyncWS();
		task.execute();
		 View v = inflater.inflate(R.layout.ass_rest_list, container, false);
		 
		 img1 = (ImageView)v.findViewById(R.id.img1);
		
     
		return v;
        }
	
	class AsyncWS extends AsyncTask{

		ProgressDialog dialog;

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
			showImage();
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
		
			dialog.dismiss();
	
			}
		
	}
	
	public void showImage()
	{
		HttpRequest hr = new HttpRequest();
		try {

			ContentValues cv = new ContentValues();
	
			//cv.put("PageIndex", "1");
			 
			String res = hr.getDataFromServer(cv, "AssociateRestaurantMasterList");
			Log.d("Res" , res);
		
				JSONArray jsonArray = new JSONArray(res);
				Log.d("Json Array", jsonArray.toString());
				 for(int i=0; i < jsonArray.length(); i++)
				 {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
			        Log.d("Json object:", jsonObject.toString());
			        String RestaurantId = jsonObject.optString("RestaurantId").toString();
			        Log.d("Restaurent id:", RestaurantId );
			        RestaurentImage= jsonObject.optString("RestaurantImage").toString();
			       new DownloadImageTask(img1).execute(RestaurentImage);
			        Log.d("Restaurent Image:", RestaurentImage );
			        arr.add(RestaurentImage);
			  
			        
				 }
			    Log.d("ARR", arr.toString());
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
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}
		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon;
		}

		protected void onPostExecute(Bitmap result) {
	       // Log.d("Bitmap Result", result.toString());
			bmImage.setImageBitmap(result);
	
			
		
		}
	}
	
	
	
	
}
	

	