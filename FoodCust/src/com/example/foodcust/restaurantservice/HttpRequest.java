package com.example.foodcust.restaurantservice;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class HttpRequest {
//	String URL = "http://foodrool.com/Restaurent.asmx";
	String URL = "http://192.168.1.225:108/Restaurent.asmx";
	String NAMESPACE = "http://tempuri.org/";

	public String getDataFromServer(ContentValues cv, String methodName) throws SocketException, SocketTimeoutException, XmlPullParserException, IOException {
		String response = null;

		SoapObject request = new SoapObject(NAMESPACE, methodName);
		request = addRequestParameter(request, cv);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 2 * 60 * 1000);
		// AuthTransportSE androidHttpTransport = new AuthTransportSE(URL, 2 * 60 * 1000,USERNAME,PASSWORD);

		androidHttpTransport.call(NAMESPACE + methodName, envelope);
		Log.i("Data", envelope.getResponse().toString());
		response = envelope.getResponse().toString();

		return response;
	}

	public SoapObject getSoapObjectFromServer(ContentValues cv, String methodName) throws SocketException, SocketTimeoutException, XmlPullParserException, IOException {
		SoapObject response = null;

		SoapObject request = new SoapObject(NAMESPACE, methodName);
		if (cv != null)
			request = addRequestParameter(request, cv);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 5 * 60 * 1000);
		// AuthTransportSE androidHttpTransport = new AuthTransportSE(URL,5* 60
		// * 1000,USERNAME,PASSWORD);
		androidHttpTransport.call(NAMESPACE + methodName, envelope);
		// Log.i("Data", envelope.getResponse().toString());

		response = (SoapObject) envelope.getResponse();

		return response;
	}

	public SoapObject parseLoginResponse(SoapObject response) {

		SoapObject newDataSetObject = (SoapObject) response.getProperty("NewDataSet");

		SoapObject tableObject = (SoapObject) newDataSetObject.getProperty(0);

		return tableObject;
	}

	public String uploadFile(String METHOD_NAME, ContentValues cv, byte[] byteArr) throws SocketException, SocketTimeoutException, XmlPullParserException, IOException {

		// String METHOD_NAME = "SaveProfilePicture";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request = addRequestParameter(request, cv);

		request.addProperty("file", byteArr);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		new MarshalBase64().register(envelope);
		envelope.setOutputSoapObject(request);

		// Says that the soap webservice is a .Net service
		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 2 * 60 * 1000);
		// AuthTransportSE androidHttpTransport = new AuthTransportSE(URL,5* 60
		// * 1000,USERNAME,PASSWORD);
		androidHttpTransport.call(NAMESPACE + METHOD_NAME, envelope);
		String response = envelope.getResponse().toString();
		Log.i("Data", response);
		byteArr = null;
		return response;
		

	}

	@SuppressLint("NewApi")
	private SoapObject addRequestParameter(SoapObject request, ContentValues cv) {
		if (cv != null) {
			for (int i = 0; i < cv.size(); i++) {
				Iterator<String> keys = cv.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					request.addProperty(key, cv.get(key));
				}
			}
		}
		return request;
	}

	public static boolean hasConnection(Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			return true;
		}

		NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected()) {
			return true;
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		}

		return false;
	}
}
