
/*This DialogFrag gets opened as a popup menu from ViewPagerSelectCusineByRestaurantIdActivity on click of
  options button onOptionsClick(String foodItemId) method.
  This is similar to custom listview using OptionsAdapter and OptionsModel
 * */

package com.example.foodcust;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import com.example.foodcust.adapter.OptionsAdapter;
import com.example.foodcust.models.OptionsModel;
import com.example.foodcust.restaurantservice.HttpRequest;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class OptionsDialogFrag extends DialogFragment {

	ProgressDialog dialog;
	Button btn1;
	ListView list_options;
	OptionsAdapter adapter1;
	ArrayList<OptionsModel> arr;
	OptionsDialogListener mListener;
	 public boolean isSuccess = false;
	 JSONArray jsonArray;
	 JSONObject jsonObject;
	 String OptionId,ItemId,OptionName,Cost,IsActive,SortOrder,Type;
	 String foodItemId;
	static int Position1;
	static String OptionName1;
	static String Cost1;
	 public interface OptionsDialogListener{
		// public void onDialogPositiveClick(DialogFragment dialog);
		 void setOnOk(String name,String price,int selectedPosition);
	}
	
	// Use this instance of the interface to deliver action events
		
/*		
	@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
    final Dialog dialog = new Dialog(getActivity()); 
     
    dialog.setContentView(R.layout.cusine_submenu_inner_options);  
    dialog.setTitle("Please Select Your Option");
    dialog.show();  
    list_options = (ListView)dialog.findViewById(R.id.list_options);
   
	btn1 = (Button)dialog.findViewById(R.id.btn1);
	btn1.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mListener.setOnOk();
			dismiss();
		}
	});
	
	 arr= new  ArrayList<OptionsModel>();
	 AsyncWS task = new AsyncWS();
     task.execute();
   return dialog;
   }
	*/
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.cusine_submenu_inner_options, null, false);
		   btn1 = (Button)view.findViewById(R.id.btn1);
		   list_options = (ListView)view.findViewById(R.id.list_options);
		  // getDialog().getWindow().setTitle("Please Select Your Option");
		   getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		   list_options.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		   list_options.setItemsCanFocus(false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Log.d(" on click",  OptionName1.toString() +" and cost is " + Cost1.toString() + "and position" + Position1);
			mListener.setOnOk(OptionName1,Cost1,Position1);
			dismiss();
			}
		});
		
		 arr= new  ArrayList<OptionsModel>();
		 AsyncWS task = new AsyncWS();
	     task.execute();
	     
	}
	
	 class AsyncWS extends AsyncTask{
	    	
			@Override
			protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			}
			@Override
			protected Object doInBackground(Object... arg0) {
				// TODO Auto-generated method stub
				showDetails();
				return null;
			}
			
			
			@Override
			protected void onPostExecute(Object result) {
				// TODO Auto-generated method stub
				  adapter1=new OptionsAdapter(getActivity(), arr );
		          list_options.setAdapter( adapter1 );
				  
				}
	    
	    }
	    
	 private void showDetails() {
			// TODO Auto-generated method stub
	    	HttpRequest hr = new HttpRequest();
	    	try{
	    		 
	    		ContentValues cv = new ContentValues();
				// vp.onPageSelected(position);
	    		foodItemId = getArguments().getString("foodItemId");
				cv.put("foodItemId", foodItemId);
				
				String res = hr.getDataFromServer(cv, "SelectFoodItemOptionByFoodItemId");
				isSuccess = true;
				if(isSuccess)
				{
				jsonArray = new JSONArray(res);
					Log.d("Json Array", jsonArray.toString());
					 for(int i=0; i < jsonArray.length(); i++)
					 {
						 jsonObject = jsonArray.getJSONObject(i);
				         Log.d("Json object:", jsonObject.toString());
				         OptionId = jsonObject.optString("OptionId").toString();
			             Log.d("OptionId",OptionId.toString());
			             OptionName = jsonObject.optString("OptionName").toString();
			             Log.d("OptionName",OptionName.toString());
			             Cost= jsonObject.optString("Cost").toString();
			             Log.d("Cost",Cost.toString());
			             Type=jsonObject.optString("Type").toString();
			             OptionsModel sched = new OptionsModel();
			             sched.setOptionName(OptionName);
			             sched.setCost(Cost);
			             sched.setType(Type);
			             arr.add( sched);
					 
					 } 
					 }
	                   
		              else
		           {
			Toast.makeText(getActivity(), "No associated Options", Toast.LENGTH_SHORT).show();
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

	public void addBtn(String name, String price, int selectedPosition) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), "You selected " + selectedPosition, Toast.LENGTH_SHORT).show();
		OptionName1=name;
		Cost1=price;
		Position1=selectedPosition;
		Log.d("name", name);
		Log.d("price", price);
		
	
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			  mListener = (OptionsDialogListener) activity;
			 } catch (ClassCastException e) {
			  throw new ClassCastException(activity.toString()
			       + " must implement OptionsDialogListener");
			 }
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		//mListener = null;
	}
}

