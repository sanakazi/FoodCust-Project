package com.example.foodcust;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import com.example.foodcust.webservices.HttpRequest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends Activity {
 EditText et1,et2,et3,et4,et5,et6,et7,et8;
 TextView  txt1;
 CheckBox cbox;
 Button btn_register;
 String fname,lname;
 ProgressDialog dialog;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		et1 = (EditText)findViewById(R.id.et1);
		et2 = (EditText)findViewById(R.id.et2);
		et3 = (EditText)findViewById(R.id.et3);
		et4 = (EditText)findViewById(R.id.et4);
		et5 = (EditText)findViewById(R.id.et5);
		et6 = (EditText)findViewById(R.id.et6);
		et7 = (EditText)findViewById(R.id.et7);
		et8 = (EditText)findViewById(R.id.et8);
		txt1 = (TextView)findViewById(R.id.txt1);
		cbox = (CheckBox)findViewById(R.id.cbox);
		btn_register = (Button)findViewById(R.id.btn_register);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}
	
	public void btn_register(View v)
	{
		AsyncWS task = new AsyncWS();
	    task.execute();
	}
	
	class AsyncWS extends AsyncTask<String, Void, String>
	{
        @Override
        protected void onPreExecute() {
        	// TODO Auto-generated method stub
        	dialog = new ProgressDialog(SignUpActivity.this);
			dialog.setCancelable(false);
			dialog.setMessage("Please wait...");
			dialog.show();
        	super.onPreExecute();
        }
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpRequest hr = new HttpRequest();
			try {

				ContentValues cv = new ContentValues();
				cv.put("FirstName", et1.getText().toString());
				cv.put("LastName", et2.getText().toString());
				cv.put("UserName", "abcd");
				cv.put("MobileNo", et3.getText().toString());
				cv.put("EmailId", et4.getText().toString());
				cv.put("Latitude", "28.613939");
				cv.put("Longitude", "77.209021");
				cv.put("Address", et5.getText().toString());
				cv.put("Zipcode", et6.getText().toString());
				cv.put("Password", et7.getText().toString());
				cv.put("SignUpFrom","IPHONE");
				

				String res = hr.getDataFromServer(cv, "CustomerMasterRegister");
				Log.d("Res" , res);
				//isSuccess = true;
				return res;
			} 
			catch (SocketTimeoutException e) {
			
				e.printStackTrace();
			} catch (SocketException e) {
		
				e.printStackTrace();
			} catch (IOException e) {
			
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		dialog.dismiss();
			try {
				Log.d("result", result);
				
				 if(result.equals("-5"))
					 Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
				 else
					 Toast.makeText(getApplicationContext(), "User not Registered", Toast.LENGTH_SHORT).show();

				}
				 
			catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "Something went wrong, Please try again", Toast.LENGTH_LONG)
						.show();
			}
	
		super.onPostExecute(result);
	}
	}

}
