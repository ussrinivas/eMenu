package com.appsfactory.app.android.eMenu.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsfactory.app.android.eMenu.R;
import com.appsfactory.app.android.eMenu.models.FoodItem;
import com.appsfactory.app.android.eMenu.network.ContentService;
import com.appsfactory.app.android.eMenu.utils.Constants;
import com.appsfactory.libraries.android.interfaces.IListViewCb;
import com.appsfactory.libraries.android.lib.AfListAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FoodItemsActivity extends BaseActivity {

	FoodItemsFragment mFoodItemsFragment;
	String mCategoryId;

	GridView mGridView;
	ImageLoader mImageLoader;
	ArrayList<FoodItem> mFoodItemsList;
	GridItemImpl mItemDisplayCb;
	AfListAdapter<FoodItem> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_items_display);
		// setContentView(R.layout.activity_food_items);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mCategoryId = extras.getString(Constants.CATEGORY_ID);
		}

		mImageLoader = ImageLoader.getInstance();

		mGridView = (GridView) findViewById(R.id.grid_view_items);

		mItemDisplayCb = new GridItemImpl();

		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FoodItem foodItem = mFoodItemsList.get(position);
				Intent intent = new Intent(getApplicationContext(),
						FoodDetails.class);
				intent.putExtra(Constants.FOOD_ITEM_NAME, foodItem.getName());
				intent.putExtra(Constants.FOOD_ITEM_DESC,
						foodItem.getDescription());
				intent.putExtra(Constants.FOOD_ITEM_PRICE, foodItem.getPrice());
				intent.putExtra(Constants.FOOD_ITEM_IMAGE1,
						foodItem.getAndroidImage1());
				intent.putExtra(Constants.FOOD_ITEM_IMAGE2,
						foodItem.getAndroidImage2());
				intent.putExtra(Constants.FOOD_ITEM_IMAGE3,
						foodItem.getAndroidImage3());
				intent.putExtra(Constants.FOOD_ITEM_IMAGE4,
						foodItem.getAndroidImage4());
				startActivity(intent);
				// Take user to description screen
				// LoadCategories loadAsync = new LoadCategories();
				// loadAsync.execute(Boolean.valueOf(true));
			}

		});

		LoadCategories loadAsync = new LoadCategories();
		loadAsync.execute(mCategoryId);

		// mFoodItemsFragment = new FoodItemsFragment();
		// FragmentManager fm = getFragmentManager();
		//
		// Bundle args = new Bundle();
		// args.putString(Constants.CATEGORY_ID, mCategoryId);
		// mFoodItemsFragment.setArguments(args);
		//
		// fm.beginTransaction()
		// .add(R.id.fl_fooditems_display, mFoodItemsFragment).commit();

	}

	private class LoadCategories extends
			AsyncTask<String, Void, List<FoodItem>> {

		@Override
		protected List<FoodItem> doInBackground(String... parms) {
			List<FoodItem> foodItemsList = null;
			foodItemsList = ContentService.getFoodItems(parms[0]);
			return foodItemsList;
		}

		@Override
		protected void onPostExecute(List<FoodItem> foodItemsList) {
			if (foodItemsList == null) {
			} else if (foodItemsList.size() == 0) {
				// Intent intent = new Intent(getActivity(),
				// FoodItemsActivity.class);
				// intent.putExtra(Constants.CATEGORY_ID, mCurrentParent);
				// startActivity(intent);
			} else {
				mFoodItemsList = (ArrayList<FoodItem>) foodItemsList;
				if (mFoodItemsList.size() >= 1) {
					mAdapter = new AfListAdapter<FoodItem>(getActivity(),
							R.layout.layout_food_items_display, mFoodItemsList,
							// R.layout.temp, mFoodItemsList,
							mItemDisplayCb);

					mGridView.setAdapter(mAdapter);
				} else {
					// Show there are no food items available in this category
				}
			}
		}

	}

	private class GridItemImpl implements IListViewCb {

		@Override
		public void inflateCb(View view, Object object) {
			// TextView tv = (TextView) view
			// .findViewById(R.id.tv_layout_item_name_display);
			TextView tv = (TextView) view
					.findViewById(R.id.tv_layout_item_name_display);
			ImageView iv = (ImageView) view
					.findViewById(R.id.iv_layout_item_food_item_display);
			FoodItem foodItem = (FoodItem) object;
			tv.setText(foodItem.getName());
			mImageLoader.displayImage(
					Constants.getImage(foodItem.getAndroidImage1()), iv);
		}
	}
}
