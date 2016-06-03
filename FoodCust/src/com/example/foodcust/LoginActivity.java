package com.example.foodcust;
/*
  Login Activity to validate the user.
  User information is stored in shared preference if user is authentic and is cleared when user logout.
  Check  private void displayView(int position){Case 3:} to clear data from sharedpref.
 
 * */

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;
import com.example.foodcust.webservices.HttpRequest;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button btnLogin;
	private EditText edtUsername, edtPassword;
	private TextView txtForgotPassword, tvRegister;
	private final int REQUEST_FROM_LOGIN = 1 ;
	private int FROM = 0;
	private String strEmailId;
	String id;
 SharedPreferences sp ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btnLogin = (Button) findViewById(R.id.btn1);
		edtUsername = (EditText) findViewById(R.id.et1);
		edtPassword = (EditText) findViewById(R.id.et2);
		txtForgotPassword = (TextView) findViewById(R.id.textView2);
		
	
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (edtUsername.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Please enter a valid email id or mobile no",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (edtUsername.getText().toString().length() < 10) {
					// Toast.makeText(getApplicationContext(),
					// "Please enter valid mobile no !",
					// Toast.LENGTH_LONG).show();
					// return;
				}
				if (edtPassword.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Please enter a valid password", Toast.LENGTH_LONG).show();
					return;
				}

				new AsyncApp().execute(edtUsername.getText().toString(), edtPassword.getText().toString());

			}
		});
		
		txtForgotPassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
				startActivity(intent);
			}
		});
		
		tvRegister =(TextView)findViewById(R.id.tvRegister);
	    tvRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
				startActivity(intent);
			}
		});
	    
	    
	   getUserId();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private class AsyncApp extends AsyncTask<String, Void, String> {
		ProgressDialog dialog;
		private boolean isSuccess = false;
		boolean j1,j2,j3;
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			dialog.dismiss();

			if (isSuccess) {
				// if (result != -2) {

				try {
					
					JSONArray jObj = new JSONArray(result);
					Log.d("Json Array", jObj.toString());
					if (jObj.getJSONObject(0).has("Invaliduser") && jObj.getJSONObject(0).getString("Invaliduser").equals("3")) {
						
					Toast.makeText(getApplicationContext(), "User is blocked.Please contact to administrator",
								Toast.LENGTH_LONG).show();
					}else if (jObj.getJSONObject(0).has("Invaliduser") && jObj.getJSONObject(0).getString("Invaliduser").equals("2")) {
						Log.d("credentials are", jObj.getJSONObject(0).toString());
					Toast.makeText(getApplicationContext(), "Invalid credentials, Please try again.",
								Toast.LENGTH_LONG).show();
					}
					 else if (jObj.getJSONObject(0).has("Invaliduser") && jObj.getJSONObject(0).getString("Invaliduser").equals("1")) {
						
						savePreferences(result);
	                     finish();
						 Intent i = new Intent(LoginActivity.this, MainActivity.class);
						 startActivity(i);
					 
					}
					 
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Invalid credentials, Please try again", Toast.LENGTH_LONG)
							.show();
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Something went wrong, Please try again", Toast.LENGTH_LONG)
							.show();
				}
			} 
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(LoginActivity.this);
			//dialog.setIndeterminate(true);
			// dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.progress_dialog_anim));
			dialog.setCancelable(false);
			dialog.setMessage("Please wait...");
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			HttpRequest hr = new HttpRequest();
			try {

				FROM = REQUEST_FROM_LOGIN;
				ContentValues cv = new ContentValues();
				cv.put("UserName", edtUsername.getText().toString());
				cv.put("Password", edtPassword.getText().toString());

				String res = hr.getDataFromServer(cv, "CustomerMasterLogin");
				Log.d("Res" , res);
				isSuccess = true;
				return res;
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
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
			return null;
		}

	}
	
	
	public void savePreferences(String response) throws JSONException {
		JSONArray jArr = new JSONArray(response);
		JSONObject jPreferences = jArr.getJSONObject(0);
        Log.d("Shared prf", jPreferences.toString());
        sp =getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		
		
		editor.putString("user_name", edtUsername.getText().toString());
		editor.putString("password", edtPassword.getText().toString());
		editor.putString("invalid_user", jPreferences.getString("Invaliduser"));
        editor.putString("cust_id", jPreferences.getString("CustId"));
		editor.putString("fname", jPreferences.getString("FirstName"));
		editor.putString("lname", jPreferences.getString("LastName"));
		editor.putString("email", jPreferences.getString("EmailId"));
		editor.commit();
		
		int custId=Integer.parseInt(sp.getString("cust_id", "-1"));
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
	  if(id!=null)
		finish();
		super.onResume();
	}
	
	public void getUserId()
	{
		 // Restore preferences
        SharedPreferences settings = getSharedPreferences("data", Context.MODE_PRIVATE);
         id= settings.getString("cust_id",null);
        //Log.d("cust_id is ", id);
        if(id!=null)
        {
        	Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        	startActivity(intent);
        }
	}
}
