package com.appsfactory.app.android.eMenu.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsfactory.app.android.eMenu.R;
import com.appsfactory.app.android.eMenu.utils.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FoodDetails extends Activity {

	private String mName;
	private String mDescription;
	private int mPrice;
	private String mImage1;
	private String mImage2;
	private String mImage3;
	private String mImage4;
	private ImageLoader mImageLoader;

	private TextView mTvName;
	private TextView mTvDescription;
	private TextView mTvPrice;
	private LinearLayout mLlImageGallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_details);

		mImageLoader = ImageLoader.getInstance();

		mTvName = (TextView) findViewById(R.id.tv_item_name);
		mTvDescription = (TextView) findViewById(R.id.tv_item_description);
		mTvPrice = (TextView) findViewById(R.id.tv_item_price);
		mLlImageGallery = (LinearLayout) findViewById(R.id.ll_food_items_gallery);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mName = extras.getString(Constants.FOOD_ITEM_NAME);
			mDescription = extras.getString(Constants.FOOD_ITEM_DESC);
			mPrice = extras.getInt(Constants.FOOD_ITEM_PRICE);
			mImage1 = extras.getString(Constants.FOOD_ITEM_IMAGE1);
			mImage2 = extras.getString(Constants.FOOD_ITEM_IMAGE2);
			mImage3 = extras.getString(Constants.FOOD_ITEM_IMAGE3);
			mImage4 = extras.getString(Constants.FOOD_ITEM_IMAGE4);

			mTvName.setText(mName);
			mTvDescription.setText(mDescription);
			mTvPrice.setText("Price: " + mPrice);

			LayoutParams layoutParams = new LayoutParams(140, 140);
		
			if (!TextUtils.isEmpty(mImage1)) {
				LinearLayout layout = new LinearLayout(getApplicationContext());
				layout.setLayoutParams(new LayoutParams(180, 140));
				layout.setGravity(Gravity.CENTER);
				
				ImageView imageView1 = new ImageView(getApplicationContext());
				imageView1.setLayoutParams(layoutParams);
//				imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
				layout.addView(imageView1);
				mLlImageGallery.addView(layout);
				mImageLoader.displayImage(Constants.getImage(mImage1),
						imageView1);
			}

			if (!TextUtils.isEmpty(mImage2)) {
				LinearLayout layout = new LinearLayout(getApplicationContext());
				layout.setLayoutParams(new LayoutParams(180, 140));
				layout.setGravity(Gravity.CENTER);
				
				ImageView imageView2 = new ImageView(getApplicationContext());
				imageView2.setLayoutParams(layoutParams);
//				imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
				layout.addView(imageView2);
				mLlImageGallery.addView(layout);
				mImageLoader.displayImage(Constants.getImage(mImage2),
						imageView2);
			}

			if (!TextUtils.isEmpty(mImage3)) {
				LinearLayout layout = new LinearLayout(getApplicationContext());
				layout.setLayoutParams(new LayoutParams(180, 140));
				layout.setGravity(Gravity.CENTER);
				
				ImageView imageView3 = new ImageView(getApplicationContext());
				imageView3.setLayoutParams(layoutParams);
//				imageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);
				layout.addView(imageView3);
				mLlImageGallery.addView(layout);
				mImageLoader.displayImage(Constants.getImage(mImage3),
						imageView3);
			}

			if (!TextUtils.isEmpty(mImage4)) {
				LinearLayout layout = new LinearLayout(getApplicationContext());
				layout.setLayoutParams(new LayoutParams(180, 140));
				layout.setGravity(Gravity.CENTER);
				
				ImageView imageView4 = new ImageView(getApplicationContext());
				imageView4.setLayoutParams(layoutParams);
//				imageView4.setScaleType(ImageView.ScaleType.CENTER_CROP);
				layout.addView(imageView4);
				mLlImageGallery.addView(layout);
				mImageLoader.displayImage(Constants.getImage(mImage4),
						imageView4);
			}

		}
	}

}
