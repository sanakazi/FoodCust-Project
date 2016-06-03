package com.example.foodcust.adapter;

import java.util.ArrayList;

import com.example.foodcust.OptionsDialogFrag;
import com.example.foodcust.R;
import com.example.foodcust.models.OptionsModel;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsAdapter extends ArrayAdapter<OptionsModel>{

	private final Activity context;
	private ArrayList<OptionsModel> names;
	  int selectedPosition = 0;
	public OptionsAdapter(Activity context,ArrayList<OptionsModel>names ) {
		super(context, R.layout.cusine_submenu_inner_options_item,names);
		this.context=context;
		this.names=names;
		// TODO Auto-generated constructor stub
	}
	

	   /********* Create a holder Class to contain inflated xml file elements *********/
static class ViewHolder{
	
	public TextView txt1,txt3;
	RadioButton cb;

}
	
public View getView(int position, View convertView, ViewGroup parent) {
	final ViewHolder holder;
	View rowView=convertView;
	if(rowView==null){
		 LayoutInflater inflater = context.getLayoutInflater();
		rowView = inflater.inflate(R.layout.cusine_submenu_inner_options_item, null, true);
		            	holder = new ViewHolder();
			            holder.txt1 = (TextView) rowView.findViewById(R.id.txt1);
			            holder.txt3 = (TextView) rowView.findViewById(R.id.txt3);
			            holder.cb = (RadioButton) rowView.findViewById(R.id.cb);
			          
			            
			            rowView.setTag(holder);
			            
			            
			            
	}else{
		holder=(ViewHolder)rowView.getTag();
	}
	
	
	
	class MyTag{
		String name,price;
		int pos1;
		public MyTag(){
			name=null;
			price=null;
			pos1=0;
		}
	public MyTag(String name,String price,int pos1)
	{
		this.name=name;
		this.price=price;
		this.pos1=pos1;
	}
		
	}
	
	 // holder.cb.setTag(position);
	OptionsModel blipVar=names.get(position);
	if(blipVar!=null)
	{
		
		MyTag myTag= new MyTag(blipVar.getOptionName(),blipVar.getCost(),position);
		holder.cb.setTag(myTag);
		holder.txt1.setText(blipVar.getOptionName());
		 holder.txt3.setText(blipVar.getCost());
		//holder.txt2.setText(blipVar.getType());
		// rowView.setOnClickListener((OnClickListener) new OnItemClickListener( position )); 
		holder.cb.setChecked(position == selectedPosition);
	   holder.cb.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			MyTag myTag=(MyTag)v.getTag();
			String name = myTag.name;
			String price = myTag.price;
			selectedPosition= myTag.pos1;
			// selectedPosition = (Integer)v.getTag();
			
			 notifyDataSetChanged();
			 
			 OptionsDialogFrag sct = new OptionsDialogFrag();
			sct.addBtn(name,price,selectedPosition);	 
			//sct.setOnOk(name,price,selectedPosition);
		}
		
	});
		
	   
	}
	
	OptionsDialogFrag sct = new OptionsDialogFrag();
	//sct.addBtn();
	
	return rowView;
	
}
	
}
