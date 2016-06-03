package com.example.foodcust.adapter;

import java.util.ArrayList;

import com.example.foodcust.EditLocationActivity;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
	private ArrayList resultList;
	public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	
	@Override
	public int getCount() {
		return resultList.size();
	}

	@Override
	public String getItem(int index) {
		return (String) resultList.get(index);
	}
	
	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if (constraint != null) {
					// Retrieve the autocomplete results.
					EditLocationActivity sc = new EditLocationActivity();
					resultList = EditLocationActivity.autocomplete(constraint.toString());
Log.e("Constraints",constraint.toString());
					// Assign the data to the FilterResults
					filterResults.values = resultList;
					filterResults.count = resultList.size();
				}
				Log.e("Filter Results", filterResults.toString());
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				if (results != null && results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}
	
}
