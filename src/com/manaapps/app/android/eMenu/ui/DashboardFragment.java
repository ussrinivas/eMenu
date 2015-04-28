package com.appsfactory.app.android.eMenu.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsfactory.app.android.eMenu.R;
import com.appsfactory.app.android.eMenu.models.Category;
import com.appsfactory.app.android.eMenu.network.ContentService;
import com.appsfactory.app.android.eMenu.utils.Constants;
import com.appsfactory.libraries.android.interfaces.IListViewCb;
import com.appsfactory.libraries.android.lib.AfListAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class DashboardFragment extends Fragment {
	DisplayImageOptions mImageOptions;
	GridView mGridView;
	ImageLoader mImageLoader = null;
	ArrayList<Category> mCategoriesList;
	GridItemImpl mItemDisplayCb;
	AfListAdapter<Category> mAdapter;
	String mCurrentParent;
	Category mCurrentCategory;
	Stack<Category> mCategoryStack;
	boolean mMainScreen = false;
	ProgressDialog mProgressDialog = null;
	LinearLayout mLlInfoHeader = null;

	public interface Callbacks {
		public void onItemClick();
	}

	private void configureResources() {
		if (mImageLoader == null) {
			mImageOptions = new DisplayImageOptions.Builder()
					.showStubImage(R.drawable.food)
					.showImageForEmptyUri(R.drawable.food)
					.showImageOnFail(R.drawable.food).cacheInMemory()
					.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					getActivity()).defaultDisplayImageOptions(mImageOptions)
					.build();
			ImageLoader.getInstance().init(config);
			mImageLoader = ImageLoader.getInstance();
		} else {
			mImageLoader = ImageLoader.getInstance();
		}

		if(mItemDisplayCb == null)
			mItemDisplayCb = new GridItemImpl();
		if(mCategoryStack == null)
			mCategoryStack = new Stack<Category>();
		mProgressDialog = new ProgressDialog(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_dashboard, container);

		configureResources();
		
		mGridView = (GridView) root.findViewById(R.id.grid_view);
		mLlInfoHeader = (LinearLayout) root.findViewById(R.id.ll_header_info);

		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity()
						.getApplicationContext());
		Toast.makeText(
				getActivity(),
				"Server address "
						+ prefs.getString("server_address_text", "dummy"),
				Toast.LENGTH_LONG).show();
		Constants.setServerAddress(prefs.getString("server_address_text",
				"dummy"));

		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCurrentParent = mCategoriesList.get(position).get_id();
				mCurrentCategory = mCategoriesList.get(position);
				LoadCategories loadAsync = new LoadCategories();
				loadAsync.execute(Boolean.valueOf(true));
			}

		});

		LoadCategories loadAsync = new LoadCategories();
		loadAsync.execute(Boolean.valueOf(false));

		return root;
	}

	public boolean isStackEmpty() {
		return (mCategoryStack.isEmpty() & mMainScreen);
	}

	public void popBackStack() {
		if (!mCategoryStack.isEmpty()) {
			mCategoryStack.pop();
			if (!mCategoryStack.isEmpty()) {
				Category category = mCategoryStack.lastElement();
				mCurrentParent = category.get_id();
				LoadCategories loadAsync = new LoadCategories();
				loadAsync.execute(Boolean.valueOf(true));
			} else {
				// mMainScreen = true;
				LoadCategories loadAsync = new LoadCategories();
				loadAsync.execute(Boolean.valueOf(false));
			}
		} else {
			mMainScreen = true;
		}
	}

	private class LoadCategories extends
			AsyncTask<Boolean, Void, List<Category>> {

		boolean localFlag;

		@Override
		protected void onPreExecute() {
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		@Override
		protected List<Category> doInBackground(Boolean... flag) {
			List<Category> categoriesList = null;
			localFlag = flag[0];
			if (!flag[0]) {
				categoriesList = ContentService.getMainCategories();
			} else {
				categoriesList = ContentService
						.getSubCategories(mCurrentParent);
			}
			return categoriesList;
		}

		@Override
		protected void onPostExecute(List<Category> categoriesList) {
			if (mProgressDialog != null && mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}

			if (categoriesList == null) {
			} else if (categoriesList.size() == 0) {
				Intent intent = new Intent(getActivity().getBaseContext(),
						FoodItemsActivity.class);
				intent.putExtra(Constants.CATEGORY_ID, mCurrentParent);
				// DashboardFragment.this.startActivity(intent);
				startActivity(intent);
			} else {
				mCategoriesList = (ArrayList<Category>) categoriesList;
				mAdapter = new AfListAdapter<Category>(getActivity(),
						R.layout.layout_item_display, mCategoriesList,
						mItemDisplayCb);

				if (localFlag) {
					mCategoryStack.add(mCurrentCategory);
				}
				mGridView.setAdapter(mAdapter);
			}
		}

	}

	private class GridItemImpl implements IListViewCb {

		@Override
		public void inflateCb(View view, Object object) {
			TextView tv = (TextView) view
					.findViewById(R.id.tv_layout_item_display);
			ImageView iv = (ImageView) view
					.findViewById(R.id.iv_layout_item_food_item);
			Category category = (Category) object;

			tv.setText(category.getName());
			mImageLoader.displayImage(
					Constants.getImage(category.getAndroidImage()), iv);
			// ImageSize targetSize = new ImageSize(80, 80); // result Bitmap
			// will
			// // be fit to this
			// // size
			// DisplayImageOptions displayOptions = DisplayImageOptions
			// .createSimple();
			// imageLoader.displayImage(Constants.getImage(category.getAndroidImage()),
			// iv);
			// mImageLoader.loadImage(category.getName(), targetSize,
			// displayOptions, new SimpleImageLoadingListener() {
			// @Override
			// public void onLoadingComplete(String imageUri,
			// View view, Bitmap loadedImage) {
			// }
			// });

		}

	}
}
