package com.example.foodcust.adapter;

import java.util.ArrayList;

import com.example.foodcust.Checkout1Activity;
import com.example.foodcust.R;
import com.example.foodcust.ViewPagerSelectCusineByRestaurantIdActivity;
import com.example.foodcust.models.Checkout1ViewHolder;
import com.example.foodcust.models.database.DatabaseHelper;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Checkout1ActivityAdapter extends CursorAdapter{
	 private LayoutInflater cursorInflater;
	 String fcount;
		Context context1;

	public Checkout1ActivityAdapter(Context context, Cursor c, int flags) {
		super(context, c, 0);
		// TODO Auto-generated constructor stub
		 context1 = context;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		View root = LayoutInflater.from(context).inflate(R.layout.checkout1_proceed, parent, false);
		  Checkout1ViewHolder holder = new  Checkout1ViewHolder();
		  holder.inner = (TextView)root.findViewById(R.id.inner);
		  holder.inner_txt2 = (TextView)root.findViewById(R.id.inner_txt2);
		  holder.inner_txt4 = (TextView)root.findViewById(R.id.inner_txt4);
		  holder.inner_txtresult = (TextView)root.findViewById(R.id.inner_txtresult);
		  holder.inner_btnplus=(Button)root.findViewById(R.id.inner_btnplus);
		  holder.inner_btnminus=(Button)root.findViewById(R.id.inner_btnminus);
		  holder.inner_img = (ImageView)root.findViewById(R.id.inner_img);
		  root.setTag(holder);
		return root;
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		/* TextView inner,inner_txt2,inner_txt4,inner_txtresult;
		 ImageView inner_img;
		 Button inner_btnplus, inner_btnminus;
		*/
		
		  Checkout1ViewHolder holder = (Checkout1ViewHolder) view.getTag();
		  String key = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.Food.COL_ID));
		  String fname = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.Food.NAME));
		  String fdesc = cursor.getString(cursor.getColumnIndexOrThrow( DatabaseHelper.Food.DESC));
		  String fprice=cursor.getString(cursor.getColumnIndexOrThrow( DatabaseHelper.Food.PRICE));
		  String ftype = cursor.getString(cursor.getColumnIndexOrThrow( DatabaseHelper.Food.TYPE));
		  fcount=cursor.getString(cursor.getColumnIndexOrThrow( DatabaseHelper.Food.COUNT));
		  
		  holder.inner.setText(fname);
		  holder.inner_txt2.setText(fdesc);
		  holder.inner_txt4.setText(fprice);
		  holder.inner_txtresult.setText(fcount);
		  
		
		  
		  if(ftype.equals("Veg"))
		    {
		    	
		    	holder.inner_img.setImageResource(R.drawable.veg);
		    	 
		    }
		    else
		    {  
		    	holder.inner_img.setImageResource(R.drawable.nonveg);
		    	
		    }
		  
		  class MyTag{
			  String key1,fname1,fdesc1,fprice1,ftype1,fcount1;
			  
			  public MyTag(){
				  
				  key1= null;
				  fname1=null;
				  fdesc1 = null;
				  fprice1=null;
				  ftype1=null;
				  fcount1=null;
			  }
			  
			  public MyTag(String key1, String fname1,String fdesc1, String fprice1,String ftype1, String fcount1)
			  {
				  this.key1=key1;
				  this.fname1 = fname1;
				  this.fdesc1=fdesc1;
				  this.fprice1=fprice1;
				  this.ftype1=ftype1;
				  this.fcount1=fcount1;
			  }
			  
			  public MyTag(String key1, String fcount1)
			  {
				  this.key1=key1;
				  this.fcount1=fcount1;
			  }
		  }
		  
		  MyTag myTag = new MyTag(key,fcount);
		// String countVal=String.valueOf(Double.parseDouble((String) holder.inner_txt4.getText()));
		  holder.inner_btnminus.setTag(myTag);
		  holder.inner_btnminus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyTag myTag=(MyTag)v.getTag();
				String count =myTag.fcount1;
				String key = myTag.key1;
                
				  Checkout1Activity sct = (Checkout1Activity)context1;
				  sct.minusFromCart(count,key);

			}
		});
		  
		  holder.inner_btnplus.setTag(myTag);
		  holder.inner_btnplus.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					MyTag myTag=(MyTag)v.getTag();
					String count =myTag.fcount1;
					String key = myTag.key1;

					  Checkout1Activity sct = (Checkout1Activity)context1;
					  sct.addToCart(count,key);

				}
			});
		  
		  
	}



	
	
	
}


/*public class Checkout1ActivityAdapter extends ArrayAdapter<String>
{
	private  Activity context;
	
	DatabaseHelper db= new DatabaseHelper(context);;
	//Cursor cursor;
	Cursor cursor = db.getReadableDatabase().query(DatabaseHelper.Food.TABLE_FOOD, null, null, null, null, null, null);
	private ArrayList<String> names;
	 String fcount;
	public Checkout1ActivityAdapter(Activity context, ArrayList<String> names) {
		super(context, R.layout.checkout1_proceed); 
		this.context=context;
		this.names = names;
		
		// TODO Auto-generated constructor stub
	}
static class ViewHolder{
	 TextView inner,inner_txt2,inner_txt4,inner_txtresult;
	 ImageView inner_img;
	 Button inner_btnplus, inner_btnminus;
	
}
public View getView(int position, View convertView, ViewGroup parent) {
	final ViewHolder holder;
	View rowView=convertView;
	if(rowView==null){
		 LayoutInflater inflater = context.getLayoutInflater();
		rowView = inflater.inflate(R.layout.checkout1_proceed, null, true);
		            	holder = new ViewHolder(); 
			          
			            holder.inner = (TextView)rowView.findViewById(R.id.inner);
			            holder.inner_txt2 = (TextView)rowView.findViewById(R.id.inner_txt2);
			            holder.inner_txt4 = (TextView)rowView.findViewById(R.id.inner_txt4);
			            holder.inner_txtresult = (TextView)rowView.findViewById(R.id.inner_txtresult);
			            holder.inner_btnplus=(Button)rowView.findViewById(R.id.inner_btnplus);
			            holder.inner_btnminus=(Button)rowView.findViewById(R.id.inner_btnminus);
			            holder.inner_img = (ImageView)rowView.findViewById(R.id.inner_img);
			            rowView.setTag(holder);
	}else{
		holder=(ViewHolder)rowView.getTag();
	}
	
		
			 
			        
		 String fname = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.Food.NAME));
		  String fdesc = cursor.getString(cursor.getColumnIndexOrThrow( DatabaseHelper.Food.DESC));
		  String fprice=cursor.getString(cursor.getColumnIndexOrThrow( DatabaseHelper.Food.PRICE));
		  String ftype = cursor.getString(cursor.getColumnIndexOrThrow( DatabaseHelper.Food.TYPE));
		  fcount=cursor.getString(cursor.getColumnIndexOrThrow( DatabaseHelper.Food.COUNT));
		  
		  holder.inner.setText(fname);
		  holder.inner_txt2.setText(fdesc);
		  holder.inner_txt4.setText(fprice);
		  holder.inner_txtresult.setText(fcount);
		  
		
		  
		  if(ftype.equals("Veg"))
		    {
		    	
			  holder.inner_img.setImageResource(R.drawable.veg);
		    	 
		    }
		    else
		    {  
		    	holder.inner_img.setImageResource(R.drawable.nonveg);
		    	
		    }
		 
	
	return rowView;
	
}
}*/
