package com.appsfactory.app.android.eMenu.ui;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.appsfactory.app.android.eMenu.R;
import com.appsfactory.app.android.eMenu.utils.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeActivity extends BaseActivity implements
		DashboardFragment.Callbacks {
	DashboardFragment dbFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		FragmentManager fm = getFragmentManager();
		dbFragment = (DashboardFragment) fm
				.findFragmentById(R.id.fragment_dashboard);
	}

	@Override
	public void onBackPressed() {
		dbFragment.popBackStack();
		if (dbFragment.isStackEmpty()) {
			ImageLoader.getInstance().destroy();
			Constants.setServerAddress("http://");
			super.onBackPressed();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		// dbFragment.popBackStack();
	}

	// When fragment Item been clicked, add it to back stack
	@Override
	public void onItemClick() {

	}
}
