package com.android.cassandra.droidbargain.profile;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.android.cassandra.droidbargain.R;
import com.android.cassandra.droidbargain.feed.DealFactory;
import com.android.cassandra.droidbargain.feed.FeedActivity;
import com.android.cassandra.droidbargain.input.PhotoActivity;
import com.android.cassandra.droidbargain.stores.StoreList;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Profile extends FragmentActivity implements ActionBar.TabListener {

	private AppSectionsPagerAdapter mAppSectionsPagerAdapter;
	private static ViewPager mViewPager;
	private User bargain_user;
	protected static ArrayList<DealFactory> user_deal_data;
	protected static ArrayList<DealFactory> user_like_data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		bargain_user = (User) getIntent().getSerializableExtra("USER_PROFILE");
		user_deal_data = FeedActivity.user_deal_data;
		user_like_data = FeedActivity.user_like_data;



		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			public void onPageSelected(int position){
				actionBar.setSelectedNavigationItem(position);
			}
		});



		//naming the tabs of the ActionBar
		actionBar.addTab(actionBar.newTab()
				.setText(R.string.title_profile)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab()
				.setText(R.string.title_liked_deals)
				.setTabListener(this));	
		actionBar.addTab(actionBar.newTab()
				.setText(R.string.my_deals)
				.setTabListener(this));	
		
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feed_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			break;
		case R.id.open_profile:
			Intent profileIntent = new Intent(this, Profile.class);
			profileIntent.putExtra("USER_PROFILE", bargain_user);
			startActivity(profileIntent);
			return true;
		case R.id.open_stores:
			Intent storeIntent = new Intent(this, StoreList.class);
			storeIntent.putExtra("USER_PROFILE", bargain_user);
			startActivity(storeIntent);
			return true;
		case R.id.open_camera:
			Intent postIntent = new Intent(this, PhotoActivity.class);
			postIntent.putExtra("USER_PROFILE", bargain_user);
			startActivity(postIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		private static final int NUM_SECTIONS = 3;

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {

			switch(i) {

			case 0:
				return new ProfileFragment();

			case 1:
				return new MyFavDealsFragment();
				
			case 2:
				return new MyDealsFragment();

			default:
				return null;
			}
		}

		@Override
		public int getCount() {
			return NUM_SECTIONS;
		}
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		// When the given tab is selected, switch to the corresponding page in the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}



}
