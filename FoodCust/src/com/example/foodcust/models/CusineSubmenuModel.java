package com.example.foodcust.models;

import java.util.ArrayList;
import java.util.List;

public class CusineSubmenuModel {
	
	String FoodSubMenuName;
	private ArrayList<CusineSubMenuItemModel> Items;
	
	public String getFoodSubMenuName() {
		return FoodSubMenuName;
	}
	public void setFoodSubMenuName(String foodSubMenuName) {
		FoodSubMenuName = foodSubMenuName;
	}
	public ArrayList<CusineSubMenuItemModel> getItems() {
		return Items;
	}
	public void setItems(ArrayList<CusineSubMenuItemModel> items) {
		Items = items;
	}


	
}
