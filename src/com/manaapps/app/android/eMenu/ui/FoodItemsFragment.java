package com.appsfactory.app.android.eMenu.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsfactory.app.android.eMenu.R;
import com.appsfactory.app.android.eMenu.models.FoodItem;
import com.appsfactory.app.android.eMenu.network.ContentService;
import com.appsfactory.app.android.eMenu.utils.Constants;
import com.appsfactory.libraries.android.interfaces.IListViewCb;
import com.appsfactory.libraries.android.lib.AfListAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class FoodItemsFragment extends Fragment {

	GridView mGridView;
	ImageLoader mImageLoader;
	ArrayList<FoodItem> mFoodItemsList;
	GridItemImpl mItemDisplayCb;
	AfListAdapter<FoodItem> mAdapter;

	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        setUserVisibleHint(true);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_items_display, container, false);
		
		mImageLoader = ImageLoader.getInstance();

		mGridView = (GridView) root.findViewById(R.id.grid_view_items);

		mItemDisplayCb = new GridItemImpl();

		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Take user to description screen
				// LoadCategories loadAsync = new LoadCategories();
				// loadAsync.execute(Boolean.valueOf(true));
			}

		});

		Bundle arguments = getArguments();
		String categoryId = arguments.getString(Constants.CATEGORY_ID);

		LoadCategories loadAsync = new LoadCategories();
		loadAsync.execute(categoryId);

		return root;
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
				mAdapter = new AfListAdapter<FoodItem>(getActivity(),
						R.layout.layout_food_items_display, mFoodItemsList,
						mItemDisplayCb);

				mGridView.setAdapter(mAdapter);
			}
		}

	}

	private class GridItemImpl implements IListViewCb {

		@Override
		public void inflateCb(View view, Object object) {
			TextView tv = (TextView) view
					.findViewById(R.id.tv_layout_item_name_display);
			ImageView iv = (ImageView) view
					.findViewById(R.id.iv_layout_item_food_item_display);
			FoodItem foodItem = (FoodItem) object;
			Toast.makeText(getActivity(), "Food Item Name: " + foodItem.getName(), Toast.LENGTH_LONG).show();
//			tv.setText("asdkajk;aja ;dja;dja;djasd;");
			tv.setText(foodItem.getName());
//			mImageLoader.displayImage(
//					Constants.getImage(foodItem.getAndroidImage1()), iv);
		}
	}
}
