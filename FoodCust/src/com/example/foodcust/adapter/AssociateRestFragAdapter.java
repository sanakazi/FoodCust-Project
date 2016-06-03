package com.example.foodcust.adapter;

import java.io.InputStream;
import java.util.ArrayList;

import com.example.foodcust.R;
import com.example.foodcust.models.AssociateRestFragModel;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AssociateRestFragAdapter extends ArrayAdapter<AssociateRestFragModel>{
	private final Activity context;
	private ArrayList<AssociateRestFragModel> names;
	
	public AssociateRestFragAdapter(Activity context,ArrayList<AssociateRestFragModel>names ) {
		super(context, R.layout.ass_rest_list,names);
		this.context=context;
		this.names=names;
		// TODO Auto-generated constructor stub
	}
	

	   /********* Create a holder Class to contain inflated xml file elements *********/
static class ViewHolder{
	public ImageView img1;
	public TextView txt1;

}
	
public View getView(int position, View convertView, ViewGroup parent) {
	final ViewHolder holder;
	View rowView=convertView;
	if(rowView==null){
		 LayoutInflater inflater = context.getLayoutInflater();
		rowView = inflater.inflate(R.layout.ass_rest_list, null, true);
		            	holder = new ViewHolder();
			            holder.txt1 = (TextView) rowView.findViewById(R.id.txt1);
			            holder.img1 = (ImageView) rowView.findViewById(R.id.img1);
			            rowView.setTag(holder);
	}else{
		holder=(ViewHolder)rowView.getTag();
	}
	
	
	AssociateRestFragModel blipVar=names.get(position);
	if(blipVar!=null)
	{
		 holder.txt1.setText(blipVar.getRestaurantName());
		holder.img1.setImageResource(R.drawable.ic_launcher);
	//	new DownloadImageTask(holder.img1).execute(blipVar.getRestaurantImage());	 
		
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
		//	Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mIcon;
	}

	protected void onPostExecute(Bitmap result) {
		bmImage.setImageBitmap(result);
	}
}
}
