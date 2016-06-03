package com.example.foodcust.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.foodcust.MainActivity;
import com.example.foodcust.OptionsDialogFrag;
import com.example.foodcust.OptionsDialogFrag.OptionsDialogListener;
import com.example.foodcust.R;
import com.example.foodcust.R.drawable;

import com.example.foodcust.ViewPagerSelectCusineByRestaurantIdActivity;
import com.example.foodcust.models.CusineSubMenuItemModel;
import com.example.foodcust.models.CusineSubmenuModel;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

public class CusineSubmenuAdapter extends BaseExpandableListAdapter {
	CheckedTextView outer;
	Context context;
	  public LayoutInflater inflater;
	 public ArrayList<CusineSubmenuModel>groups;
 int count;
 String count1;
 int position;
 int b;
 int a[] ;
 String foodItemId, foodItemIdTopass;
 String type;
 String price;
 String fname,fdes,ftype;
 
     Button inner_btnplus,inner_btnminus,inner_btnOption;
     TextView inner_txtresult;
     CusineSubMenuItemModel child;
     ArrayList<CusineSubMenuItemModel> chList;
    
     
     
      /*************  CustomAdapter Constructor *****************/
      public CusineSubmenuAdapter (Context context, ArrayList<CusineSubmenuModel> groups ) {
           
             /********** Take passed values **********/
    	     this.context = context;
    	        this.groups = groups;
           
      }
 
   

      public CusineSubmenuAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Object getChild(int groupPosition, int childPosition) {
          chList = groups.get(groupPosition).getItems();
          return chList.get(childPosition);
      }



@Override
public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
}


@Override
public int getChildrenCount(int groupPosition) {
    ArrayList<CusineSubMenuItemModel> chList = groups.get(groupPosition).getItems();
  
    return chList.size();
}


@Override
public View getChildView(int groupPosition, int childPosition,
        boolean isLastChild, View convertView, ViewGroup parent) {
	 TextView inner,inner_txt2,inner_txt4;
	 ImageView inner_img;
	 
	 b=getChildrenCount(groupPosition);
	
   child = (CusineSubMenuItemModel) getChild(groupPosition, childPosition);
 
    if (convertView == null) {
        LayoutInflater infalInflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.cusine_submenu_inner, null);
    }
    
  /*  if (imageLoader == null)
        imageLoader = MyApplication.getInstance().getImageLoader();

   
    NetworkImageView iv = (NetworkImageView) convertView
            .findViewById(R.id.flag);*/
  
    
    inner = (TextView) convertView.findViewById(R.id.inner);
    inner_txt2 = (TextView) convertView.findViewById(R.id.inner_txt2);
    inner_txt4 = (TextView) convertView.findViewById(R.id.inner_txt4);
    inner_btnplus = (Button) convertView.findViewById(R.id.inner_btnplus);
    inner_btnminus = (Button) convertView.findViewById(R.id.inner_btnminus);
    inner_txtresult = (TextView) convertView.findViewById(R.id.inner_txtresult);
    inner_btnOption = (Button) convertView.findViewById(R.id.inner_btnOption);
    inner_img = (ImageView)convertView.findViewById(R.id.inner_img);
    inner.setText(child.getFoodItemName().toString());
   // iv.setImageUrl(child.getImage(), imageLoader);
    inner.setText(child.getFoodItemName().toString());
    inner_txt2.setText(child.getFoodDesc().toString());
    inner_txt4.setText(child.getPrice().toString());
    inner_txtresult.setText(String.valueOf(a[childPosition]));
    type = child.getType().toString();
    int OptionCount =Integer.parseInt(child.getOptionId());
    if(type.equals("Veg"))
    {
    	
    	inner_img.setImageResource(R.drawable.veg);
    	  notifyDataSetChanged();
    }
    else
    {  
    	inner_img.setImageResource(R.drawable.nonveg);
    	  notifyDataSetChanged();
    }

   
    class MyTag
    {
       String  price, foodItemIdTopass,foodItemNameTopass, foodDescTopass,typeTopass;
       int pos;
      

      public MyTag()
        {
         price=null;
         pos=0;
         foodItemIdTopass=null;
         foodItemNameTopass=null;
         foodDescTopass=null;
         typeTopass=null;
        }

        public MyTag(String price,int pos,String foodItemIdTopass,String foodItemNameTopass,String foodDescTopass,String typeTopass)
        {
         this.price =price;
         this.pos=pos;
         this.foodItemIdTopass= foodItemIdTopass;
         this.foodItemNameTopass= foodItemNameTopass;
         this.foodDescTopass= foodDescTopass;
         this.typeTopass=typeTopass;
        }

    }
    
    
    
    MyTag myTag=new MyTag(child.getPrice(),childPosition,child.getFoodItemId(),child.getFoodItemName(),child.getFoodDesc(),child.getType());
    
   // inner_btnminus.setTag(childPosition);
    inner_btnminus.setTag(myTag);
     inner_btnminus.setOnClickListener(new OnClickListener() {
      
     public void onClick(View v) {
    	
     //  position=(Integer)v.getTag();
    	MyTag myTag=(MyTag)v.getTag();
     	position=myTag.pos;
     	price=myTag.price;
     	fname=myTag.foodItemNameTopass;
     	fdes=myTag.foodDescTopass;
     	ftype=myTag.typeTopass;
     	foodItemIdTopass=myTag.foodItemIdTopass;
       if(a[position]>0)
       a[position]--;
     Log.d("adapter position minus is ", ""+a[position]);
  
    notifyDataSetChanged();
    ViewPagerSelectCusineByRestaurantIdActivity sct =  (ViewPagerSelectCusineByRestaurantIdActivity)context;
    sct.minusFromCart(a[position],price,foodItemIdTopass,fname,fdes,ftype);
       }
     });
    
     
     
     
     
  //   inner_btnplus.setTag(childPosition);
   
     inner_btnplus.setTag(myTag);
     inner_btnplus.setOnClickListener(new OnClickListener() {
    
     public void onClick(View v) {
      // position=(Integer)v.getTag();
    	MyTag myTag=(MyTag)v.getTag();
    	position=myTag.pos;
    	price=myTag.price;
    	foodItemIdTopass=myTag.foodItemIdTopass;
    	fname=myTag.foodItemNameTopass;
     	fdes=myTag.foodDescTopass;
     	ftype=myTag.typeTopass;
     	
       if(a[position]<10)
     a[position]++;
       Log.d("adapter position plus is ", ""+a[position]);
       Log.d("adapter price ", ""+ price);
      notifyDataSetChanged();
       ViewPagerSelectCusineByRestaurantIdActivity sct =  ( ViewPagerSelectCusineByRestaurantIdActivity)context;
      sct.addToCart(a[position],price,foodItemIdTopass,fname,fdes,ftype);
       }
   
     });
  
 
     if(OptionCount>0 &&  Integer.parseInt(inner_txtresult.getText().toString())!=0)
    	 {Log.d("result adapter is ", Integer.parseInt(inner_txtresult.getText().toString()) + "" );
    	 inner_btnOption.setVisibility(View.VISIBLE);
    	 }     
    	 notifyDataSetChanged();
    	 
    	 if(OptionCount>0 &&  Integer.parseInt(inner_txtresult.getText().toString())==0)
    	 {Log.d("result adapter is ", Integer.parseInt(inner_txtresult.getText().toString()) + "" );
    	 inner_btnOption.setVisibility(View.INVISIBLE);
    	 }  
    	 notifyDataSetChanged();
     
  //   inner_btnOption.setTag(childPosition);
     inner_btnOption.setTag(child.getFoodItemId());
     inner_btnOption.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// position=(Integer)v.getTag();
			foodItemId= (String) v.getTag();
			Log.d("foodItemId adapter",""+ foodItemId.toString());
			  ViewPagerSelectCusineByRestaurantIdActivity sct = ( ViewPagerSelectCusineByRestaurantIdActivity)context;
		     // sct.onOptionsClick(position);
			 sct.onOptionsClick(foodItemId);
		}
	});
    return convertView;
}






@Override
public Object getGroup(int groupPosition) {
    return groups.get(groupPosition);
}



@Override
public int getGroupCount() {
    return groups.size();
}

public void onGroupCollapsed(int groupPosition) {
    super.onGroupCollapsed(groupPosition);
   //  a = new int[b];
//a=new int [1000];
  }

public void onGroupExpanded(int groupPosition) {
    super.onGroupExpanded(groupPosition);
  //  b=getChildrenCount(groupPosition);
    // a= new int[b];
 a = new int[getChildrenCount(groupPosition)];
  }


@Override
public long getGroupId(int groupPosition) {
    return groupPosition;
}

@Override
public View getGroupView(int groupPosition, boolean isExpanded,
        View convertView, ViewGroup parent) {
    CusineSubmenuModel group = (CusineSubmenuModel) getGroup(groupPosition);
    if (convertView == null) {
        LayoutInflater inf = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inf.inflate(R.layout.cusine_submenu, null);
        
    }
    TextView outer = (TextView) convertView.findViewById(R.id.outer);
    outer.setText(group.getFoodSubMenuName());
  
    return convertView;
}

@Override
public boolean hasStableIds() {
	// TODO Auto-generated method stub
	return true;
}



@Override
public boolean isChildSelectable(int arg0, int arg1) {
	// TODO Auto-generated method stub
	return true;
}






}