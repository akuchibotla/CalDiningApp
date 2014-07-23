package com.example.caldiningapp;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.caldiningapp.MainActivity.SelectFragment;


import android.app.ActionBar;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	
	Syncer sync = new Syncer(this);
	static Document parsedDoc = new Document(new String());
	static ArrayList<String>[] menu = new ArrayList[12]; // The menu array that will contain all menus for all places at all meals
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	public static String[] ArrayListToArray (ArrayList<String> list) {
		String[] sList = new String[list.size()];
		for (int index = 0; index < list.size(); index++) {
			sList[index] = list.get(index);
		}
		return sList;
	}

	public static boolean CheckInternet(Context context) 
	{
	    ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

	    return wifi.isConnected() || mobile.isConnected();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_LONG).show();
		//ActionBar bar = getActionBar();
		//bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("RED")));
		//bar.setLogo(R.drawable.breakfast); // API Change
		//bar.setTitle("CAL DINING");
		try {
			if(CheckInternet(this)){
				// Start your AsyncTask
				sync.execute().get();

			} else{
			  // Show internet not available alert
				Toast.makeText(this, "No Internet Connection", 1000).show();
				finish();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int meal) {
						actionBar.setSelectedNavigationItem(meal);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < 3; i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_settings) {
			Intent i = new Intent("com.example.caldiningapp.SETTINGS");
			startActivity(i);
		}
		return false;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			if (position == 0) {
				return new Breakfast();
			} 
			if (position == 1) {
				return new Lunch();
			}
			if (position == 2) {
				return new Dinner();
			}
			throw new IllegalArgumentException("Invalid position reached.");
		}

		@Override
		public CharSequence getPageTitle(int meal) {
			switch (meal) {
			case 0:
				return "Breakfast";
			case 1:
				return "Lunch";
			case 2:
				return "Dinner";
			}
			return null;
		}
		
		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public class SelectFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public SelectFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			
			int meal_num = getArguments().getInt(ARG_SECTION_NUMBER);
			if (meal_num == 0){
				return inflater.inflate(R.layout.activity_breakfast, null);
			}
			if (meal_num == 1){
				return inflater.inflate(R.layout.activity_lunch, null);
			}
			if (meal_num == 2){
				return inflater.inflate(R.layout.activity_dinner, null);
			}
		    return null;
		}
	}
	
	public static class Item {
		
		String name;
		String type;
		int calories;
		String ingredients;
		String allergens;
		
		public Item (String item_name, String item_type) {
			this.name = item_name;
			this.type = item_type;
		}
		
		public String toString() {
			return name + " (" + type + ")";
		}
		
	}

}
