package com.appsfactory.app.android.eMenu.utils;

public class Constants {
	// private static String BASE_URL = "http://192.168.2.2:3000";
	private static boolean isBaseSet = false;
	private static String BASE_URL = "http://";
	private static String IMAGE_URL = "/api/image/";
	private static String CATEGORIES_URL = "/api/android/categories/";
	private static String FOOD_ITEMS_URL = "/api/android/food_items/";
	public static String CATEGORY_ID = "category_id";
	public static String FOOD_ITEM_NAME = "food_item_name";
	public static String FOOD_ITEM_DESC = "food_item_description";
	public static String FOOD_ITEM_PRICE = "food_item_price";
	public static String FOOD_ITEM_IMAGE1 = "food_item_image1";
	public static String FOOD_ITEM_IMAGE2 = "food_item_image2";
	public static String FOOD_ITEM_IMAGE3 = "food_item_image3";
	public static String FOOD_ITEM_IMAGE4 = "food_item_image4";

	public static void setServerAddress(String serverAddress) {
		if (!isBaseSet) {
			isBaseSet = true;
			BASE_URL = BASE_URL + serverAddress;
		}
	}

	public static String getCategoriesUrl() {
		return BASE_URL + CATEGORIES_URL;
	}

	public static String getSubCategoriesUrl(String parentId) {
		return BASE_URL + CATEGORIES_URL + parentId;
	}

	public static String getImage(String id) {
		return BASE_URL + IMAGE_URL + id;
	}

	public static String getFoodItemsUrl(String categoryId) {
		return BASE_URL + FOOD_ITEMS_URL + categoryId;
	}
}
