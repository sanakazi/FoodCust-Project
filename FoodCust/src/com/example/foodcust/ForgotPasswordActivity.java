package com.example.foodcust;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import com.example.foodcust.webservices.HttpRequest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends Activity {
	 EditText edtEmailId;
 Button btnSend;
 private String strEmailId;
 ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		 edtEmailId = (EditText)findViewById(R.id.edtEmail);
		btnSend = (Button)findViewById(R.id.btnSend);
		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isValidEmail(edtEmailId.getText().toString())) {

					strEmailId = edtEmailId.getText().toString();
					new AsyncForgotPassword().execute(strEmailId);
				} else {
					Toast.makeText(getApplicationContext(), "Please enter valid Email Id!", Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgot_password, menu);
		return true;
	}

	private boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		}
	}
	
	private class AsyncForgotPassword extends AsyncTask<String, Void, String> {
		private ProgressDialog dialog;
		private boolean isSuccess = false;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(ForgotPasswordActivity.this);
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.setMessage("Please wait...");
			dialog.show();
		}

		
		@Override
		protected String doInBackground(String... params) {
			HttpRequest hr = new HttpRequest();
			try {

				ContentValues cv = new ContentValues();
				cv.put("EmailId", params[0]);

				String res = hr.getDataFromServer(cv, "CustomerMasterWebForgotPassword");
                  Log.d("Forgot" , res.toString());
				isSuccess = true;
				return res;
			} catch (SocketTimeoutException e) {
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
		
		@Override
		
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d("Forgot password result string", result);
			dialog.dismiss();

			if (isSuccess) {
				// if (result != -2) {

				try {
					
					 JSONObject  jsonRootObject = new JSONObject(result);
					  Log.d("Json object", jsonRootObject.toString());
					 JSONArray jObj = jsonRootObject.optJSONArray("Table");	
					  Log.d("Json array", jObj.toString());
				        
					if (jObj.getJSONObject(0).has("Invaliduser") && jObj.getJSONObject(0).getString("Invaliduser").equals("3")) {
					Toast.makeText(ForgotPasswordActivity.this, "Invalid credentials, Please try again",
								Toast.LENGTH_LONG).show();
					}else if (jObj.getJSONObject(0).has("Invaliduser") && jObj.getJSONObject(0).getString("Invaliduser").equals("2")) {
					Toast.makeText(ForgotPasswordActivity.this, "User is blocked.Please contact to administrator",
								Toast.LENGTH_LONG).show();
					}
					 else if (jObj.getJSONObject(0).has("Invaliduser") && jObj.getJSONObject(0).getString("Invaliduser").equals("1")) {
						 Toast.makeText(ForgotPasswordActivity.this, "Password sent to your email address",
									Toast.LENGTH_LONG).show();
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

	

		
	}
}
