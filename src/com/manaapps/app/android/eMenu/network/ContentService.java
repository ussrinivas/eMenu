package com.appsfactory.app.android.eMenu.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.appsfactory.app.android.eMenu.models.Category;
import com.appsfactory.app.android.eMenu.models.FoodItem;
import com.appsfactory.app.android.eMenu.utils.Constants;
import com.appsfactory.libraries.android.utils.HttpClientUtil;

public class ContentService {
	final static String TAG = "ContentService";

	private ContentService() {
	}

	private static Category convertJsonToCategory(JSONObject json)
			throws JSONException {
		Category category = new Category();

		category.set_id(json.getString("_id"));
		category.setName(json.getString("name"));
		category.setDescription(json.getString("description"));
		JSONArray tempArray = new JSONArray(json.getString("androidImage"));
		category.setAndroidImage(tempArray.optString(0, ""));
		category.setDisplayOrder(json.getInt("displayOrder"));
		if (json.has("parentId")) {
			category.setParentId(json.getString("parentId"));
		}
		if (json.has("typeId")) {
			category.setDisplayOrder(json.getInt("typeId"));
		}

		return category;
	}

	private static List<Category> fetchCategories(String url) {
		List<Category> categoryList = null;
		HttpClient httpClient = HttpClientUtil.getHttpClient();

		try {
			String response = HttpClientUtil.execute(httpClient, url);
			Log.i(TAG, response);
			JSONObject rootJson;
			rootJson = new JSONObject(response);
			int result = rootJson.getInt("result");
			if (result == 1) {
				categoryList = new ArrayList<Category>();
				JSONArray jsonArray = rootJson.getJSONArray("categories");
				for (int i = 0; i < jsonArray.length(); ++i) {
					categoryList.add(convertJsonToCategory(jsonArray
							.getJSONObject(i)));
				}
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return categoryList;
	}

	public static List<Category> getMainCategories() {
		Log.i(TAG, "URL: " + "===========================================");
		Log.i(TAG, "URL: " + Constants.getCategoriesUrl());
		Log.i(TAG, "URL: " + "-------------------------------------------");
		return fetchCategories(Constants.getCategoriesUrl());
	}

	public static List<Category> getSubCategories(String parentId) {
		return fetchCategories(Constants.getSubCategoriesUrl(parentId));
	}

	public static List<FoodItem> getFoodItems(String categoryId) {
		List<FoodItem> foodItemsList = null;
		HttpClient httpClient = HttpClientUtil.getHttpClient();

		try {
			String response = HttpClientUtil.execute(httpClient,
					Constants.getFoodItemsUrl(categoryId));
			Log.i(TAG, response);
			JSONObject rootJson;
			rootJson = new JSONObject(response);
			int result = rootJson.getInt("result");
			if (result == 1) {
				foodItemsList = new ArrayList<FoodItem>();
				JSONArray jsonArray = rootJson.getJSONArray("food_items");
				for (int i = 0; i < jsonArray.length(); ++i) {
					JSONObject object = jsonArray.getJSONObject(i);
					FoodItem item = new FoodItem();

					item.setName(object.getString("name"));
					item.setDescription(object.getString("description"));
					item.setPrice(object.getInt("price"));
					JSONArray tempArray;
					if (object.has("androidImage1")) {
						tempArray = new JSONArray(
								object.getString("androidImage1"));
						item.setAndroidImage1(tempArray.optString(0, ""));
					}
					if (object.has("androidImage2")) {
						tempArray = new JSONArray(
								object.getString("androidImage2"));
						item.setAndroidImage2(tempArray.optString(0, ""));
					}
					if (object.has("androidImage3")) {
						tempArray = new JSONArray(
								object.getString("androidImage3"));
						item.setAndroidImage3(tempArray.optString(0, ""));
					}
					if (object.has("androidImage4")) {
						tempArray = new JSONArray(
								object.getString("androidImage4"));
						item.setAndroidImage4(tempArray.optString(0, ""));
					}

					foodItemsList.add(item);
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return foodItemsList;
	}
}
