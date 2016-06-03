package com.example.foodcust.adapter;

import java.io.InputStream;
import java.util.ArrayList;

import com.example.foodcust.MainActivity;
import com.example.foodcust.R;
import com.example.foodcust.RestaurantsListFrag;
import com.example.foodcust.models.RestaurantListModel;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantListAdapter extends ArrayAdapter<RestaurantListModel> {
	private final Activity context;
	private ArrayList<RestaurantListModel> names;
	
	public RestaurantListAdapter(Activity context,ArrayList<RestaurantListModel>names ) {
		super(context, R.layout.restaurant_list_item,names);
		this.context=context;
		this.names=names;
		// TODO Auto-generated constructor stub
	}
	

	   /********* Create a holder Class to contain inflated xml file elements *********/
static class ViewHolder{
	public ImageView img1;
	public TextView txt_restName,txt_morning_time,txt_morning_time_start,info,
	address,txt_morning_time_end,txt_rest_time_status,txt_distance,
	txt_evening_time,txt_evening_time_start,txt_evening_time_end,txt_rating;
	
}
public View getView(int position, View convertView, ViewGroup parent) {
	final ViewHolder holder;
	View rowView=convertView;
	if(rowView==null){
		 LayoutInflater inflater = context.getLayoutInflater();
		rowView = inflater.inflate(R.layout.restaurant_list_item, null, true);
		            	holder = new ViewHolder();
			            holder.txt_restName = (TextView) rowView.findViewById(R.id.txt_restName);
			            holder.txt_morning_time_start = (TextView) rowView.findViewById(R.id.txt_morning_time_start);
			            holder.txt_morning_time_end = (TextView) rowView.findViewById(R.id.txt_morning_time_end);
			            holder.txt_evening_time_start = (TextView) rowView.findViewById(R.id.txt_evening_time_start);
			            holder.txt_evening_time_end = (TextView) rowView.findViewById(R.id.txt_evening_time_end);
			            holder.txt_rating = (TextView) rowView.findViewById(R.id.txt_rating);
			            holder.txt_rest_time_status = (TextView) rowView.findViewById(R.id.txt_rest_time_status);
			            holder.txt_distance = (TextView) rowView.findViewById(R.id.txt_distance);
			            holder.img1 = (ImageView) rowView.findViewById(R.id.img1);
			            rowView.setTag(holder);
	}else{
		holder=(ViewHolder)rowView.getTag();
	}
	
	
	RestaurantListModel blipVar=names.get(position);
	if(blipVar!=null)
	{
		 holder.txt_restName.setText(blipVar.getRestaurantName());
		 holder.txt_morning_time_start.setText(blipVar.getMorningStartTime());
		 holder.txt_morning_time_end.setText(blipVar.getMorningEndTime());
		 holder.txt_evening_time_start.setText(blipVar.getEveningStartTime());
		 holder.txt_evening_time_end.setText(blipVar.getEveningEndTime());
		 holder.txt_rating.setText(blipVar.getAverageRating());
		 holder.txt_rest_time_status.setText(blipVar.getRestaurantTimeStatus());
		 holder.txt_distance.setText(blipVar.getDistance());
	//	holder.img1.setImageDrawable(Conf.loadImageFromURL(blipVar.getRestaurentImage()));

		holder.img1.setImageResource(R.drawable.ic_launcher);
		new DownloadImageTask(holder.img1).execute(blipVar.getRestaurentImage());	 
		
		// rowView.setOnClickListener((OnClickListener) new OnItemClickListener( position )); 
	
		
	}
	
	return rowView;
	
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
		bmImage.setImageBitmap(result);
	}
}

}  



