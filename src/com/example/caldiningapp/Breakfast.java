package com.example.caldiningapp;

import java.io.IOException;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.example.caldiningapp.R;

public class Breakfast extends android.support.v4.app.Fragment{
	
	private Object diet;

	public Breakfast(Context context) {
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		this.diet = getPrefs.getString("diet", "1");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_breakfast, null);
		//v.setBackgroundColor(Color.BLACK);
		Resources res = getResources();
		Drawable drawable = res.getDrawable(R.drawable.breakfast);
		v.setBackground(drawable); // Have to change target
	    ExpandableListView BreakfastItems = (ExpandableListView)v.findViewById(R.id.breakfastView);
	    BreakfastItems.setAdapter(new BreakfastItemsAdapter(this.diet));
	    return v;
	}
	
	public class BreakfastItemsAdapter extends BaseExpandableListAdapter {
		
		private String[] places = {"Crossroads", "Cafe 3", "Foothill", "Clark Kerr"};
		private Object diet;
		
		public BreakfastItemsAdapter(Object diet) {
			// TODO Auto-generated constructor stub
			this.diet = diet;
		}
		
		private String[][] items = {
				MainActivity.ArrayListToArray(MainActivity.menu[0]),
				MainActivity.ArrayListToArray(MainActivity.menu[1]),
				MainActivity.ArrayListToArray(MainActivity.menu[2]),
				MainActivity.ArrayListToArray(MainActivity.menu[3])
		};

		@Override
		public Object getChild(int arg0, int arg1) {
			return items[arg0][arg1];
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			return arg0;
		}

		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {
			TextView textView = new TextView(Breakfast.this.getActivity());
			textView.setText((CharSequence) diet);
            //textView.setText(getChild(arg0, arg1).toString());
            textView.setPadding(30, 0, 0, 0);
            textView.setTextSize(17);
            /*if (arg0 == 0) {
            	textView.setTextColor(Color.parseColor("#f2673a"));
            }
            if (arg0 == 1) {
            	textView.setTextColor(Color.parseColor("#f28a3a"));
            }
            if (arg0 == 2) {
            	textView.setTextColor(Color.parseColor("#f2ac3a"));
            }
            if (arg0 == 3) {
            	textView.setTextColor(Color.parseColor("#d9bb2b"));
            }*/
            return textView;
		}

		@Override
		public int getChildrenCount(int arg0) {
			return items[arg0].length;
		}

		@Override
		public Object getGroup(int arg0) {
			return places[arg0];
		}

		@Override
		public int getGroupCount() {
			return places.length;
		}

		@Override
		public long getGroupId(int arg0) {
			return arg0;
		}

		@Override
		public View getGroupView(int arg0, boolean arg1, View arg2,
				ViewGroup arg3) {
			TextView textView = new TextView(Breakfast.this.getActivity());
            textView.setText(getGroup(arg0).toString().toUpperCase());
            textView.setPadding(65, 10, 10, 0);
            //textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextSize(25);
            if (textView.getText().toString().equals("CROSSROADS")) {
            	textView.setBackgroundColor(Color.parseColor("#f2673a"));
            }
            if (textView.getText().toString().equals("CAFE 3")) {
            	textView.setBackgroundColor(Color.parseColor("#f28a3a"));
            }
            if (textView.getText().toString().equals("FOOTHILL")) {
            	textView.setBackgroundColor(Color.parseColor("#f2ac3a"));
            }
            if (textView.getText().toString().equals("CLARK KERR")) {
            	textView.setBackgroundColor(Color.parseColor("#f2d23a"));
            }
            return textView;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			return true;
		}
	}
}
