package com.example.foodcust.models;

public class CusineSubMenuItemModel {
 String FoodItemId,FoodItemName, foodDesc,Price,OptionId,Count,Type;


public String getType() {
	return Type;
}

public void setType(String type) {
	Type = type;
}

public String getFoodItemId() {
	return FoodItemId;
}

public void setFoodItemId(String foodItemId) {
	FoodItemId = foodItemId;
}

public String getCount() {
	return Count;
}

public void setCount(String count) {
	Count = count;
}

public String getFoodDesc() {
	return foodDesc;
}

public void setFoodDesc(String foodDesc) {
	this.foodDesc = foodDesc;
}

public String getPrice() {
	return Price;
}

public void setPrice(String price) {
	Price = price;
}

public String getOptionId() {
	return OptionId;
}

public void setOptionId(String optionId) {
	OptionId = optionId;
}

public String getFoodItemName() {
	return FoodItemName;
}

public void setFoodItemName(String foodItemName) {
	FoodItemName = foodItemName;
}



public void setCount1(String valueOf) {
	// TODO Auto-generated method stub
	Count = valueOf;
}


}
