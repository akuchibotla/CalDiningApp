package com.example.caldiningapp;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.example.caldiningapp.R;

public class Dinner extends android.support.v4.app.Fragment{
	static int calorie = 0;
    HashSet<Long> selected = new HashSet<Long>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_dinner, null);
		//v.setBackgroundColor(Color.BLACK);
		Resources res = getResources();
		Drawable drawable = res.getDrawable(R.drawable.dinner);
		v.setBackground(drawable); // Have to change target
	    ExpandableListView DinnerItems = (ExpandableListView)v.findViewById(R.id.dinnerView);
	    DinnerItems.setAdapter(new DinnerItemsAdapter());
	    
	    DinnerItems.setOnGroupClickListener(new OnGroupClickListener() {
	        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) 
	        {
	        	selected.clear();
	        	return false;
	        }
	        });
	    DinnerItems.setOnChildClickListener(new OnChildClickListener() {
	    
	    
			@Override
			public boolean onChildClick(final ExpandableListView parent, View v,
					final int groupposition, final int childposition, long id) {
				v.setSelected(true);
				/*int index = parent.getFlatListPosition(ExpandableListView
		                   .getPackedPositionForChild(groupposition, childposition));
				parent.setItemChecked(index, true);*/
				/*Syncer2 sync = new Syncer2();
				int itemcalorie = 0;
            	try {
					itemcalorie = sync.execute(MainActivity.ArrayListToArray(MainActivity.links[groupposition])[childposition]).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	*/
                
				if (v != null) {
			    	if (v.isSelected())
			    	{
			    		if (selected.contains(parent.getPackedPositionForChild(groupposition,childposition)))
			    		{
			    			v.setBackground(null);
			    			selected.remove(parent.getPackedPositionForChild(groupposition,childposition));
			    			//calorie -= itemcalorie;
			    		}
			    		else
			    		{	
			    			v.setBackgroundColor(Color.RED);
			    			selected.add(parent.getPackedPositionForChild(groupposition,childposition));
			    			//calorie +=itemcalorie;
			    		}
			    	}

			    }
				//Toast.makeText(Dinner.this.getActivity(),Integer.toString(calorie),
                       // Toast.LENGTH_SHORT).show();
				final Button p1_button = (Button) Dinner.this.getActivity().findViewById(R.id.CalorieCount3);
				p1_button.setOnClickListener(new View.OnClickListener() {
		             public void onClick(View v) {
		                 // Perform action on click
		            	calorie = 0;
		            	for (long i : selected)
		            	{

		            		Syncer2 sync = new Syncer2();
		            		int child = parent.getPackedPositionChild(i);
		            		int group = parent.getPackedPositionGroup(i);
		            		int itemcalorie = 0;
		            		try {
		            			if (MainActivity.ArrayListToArray(MainActivity.links[group+8])[child].contains("Closed"))
		            			{
		            				itemcalorie = 0;
		            				Toast.makeText(Dinner.this.getActivity(),"Please Select Valid Items Only",Toast.LENGTH_SHORT).show();
		            			}
		            			else
		            				itemcalorie = sync.execute(MainActivity.ArrayListToArray(MainActivity.links[group+8])[child]).get();
			 				} catch (InterruptedException e) {
			 					// TODO Auto-generated catch block
			 					e.printStackTrace();
			 				} catch (ExecutionException e) {
			 					// TODO Auto-generated catch block
			 					e.printStackTrace();
			 				}
		            		//Toast.makeText(Dinner.this.getActivity(),Integer.toString(itemcalorie),Toast.LENGTH_SHORT).show();
		            		calorie += itemcalorie;
		            	}
		             	
		 				p1_button.setText("Click for Calories of Selected Items: " + Integer.toString(calorie));

		             }
		         });
			    return true;
			}
	    	
	    });
	    return v;
	}
	
	public class DinnerItemsAdapter extends BaseExpandableListAdapter {
		
		private String[] places = {"Crossroads", "Cafe 3", "Foothill", "Clark Kerr"};
		
		private String[][] items = {
				MainActivity.ArrayListToArray(MainActivity.menu[8]),
				MainActivity.ArrayListToArray(MainActivity.menu[9]),
				MainActivity.ArrayListToArray(MainActivity.menu[10]),
				MainActivity.ArrayListToArray(MainActivity.menu[11])
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
			TextView textView = new TextView(Dinner.this.getActivity());
            textView.setText(getChild(arg0, arg1).toString());
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
			TextView textView = new TextView(Dinner.this.getActivity());
            textView.setText(getGroup(arg0).toString().toUpperCase());
            textView.setPadding(65, 10, 10, 0);
            //textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextSize(25);
            if (textView.getText().toString().equals("CROSSROADS")) {
            	textView.setBackgroundColor(Color.parseColor("#4262e3"));
            }
            if (textView.getText().toString().equals("CAFE 3")) {
            	textView.setBackgroundColor(Color.parseColor("#4280e3"));
            }
            if (textView.getText().toString().equals("FOOTHILL")) {
            	textView.setBackgroundColor(Color.parseColor("#42a0e3"));
            }
            if (textView.getText().toString().equals("CLARK KERR")) {
            	textView.setBackgroundColor(Color.parseColor("#42c0e3"));
            }
            /*if (textView.getText().toString().equals("CROSSROADS")) {
            	textView.setBackgroundColor(Color.parseColor("#00baff"));
            }
            if (textView.getText().toString().equals("CAFE 3")) {
            	textView.setBackgroundColor(Color.parseColor("#00e4ff"));
            }
            if (textView.getText().toString().equals("FOOTHILL")) {
            	textView.setBackgroundColor(Color.parseColor("#00ffc3"));
            }
            if (textView.getText().toString().equals("CLARK KERR")) {
            	textView.setBackgroundColor(Color.parseColor("#77ffb7"));
            }*/
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
