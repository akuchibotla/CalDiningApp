package com.example.caldiningapp;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import android.R;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;


public class Syncer2 extends AsyncTask<String, Void, Integer>{

	public static ArrayList<String> cloneList(ArrayList<String> list) {
		ArrayList<String> clone = new ArrayList<String>(list.size());
		for(String item: list) clone.add(new String(item));
		return clone;
	}

	@Override
	protected Integer doInBackground(String... link) {
		Document doc1 = null;
		try {
			doc1 = Jsoup.connect(link[0]).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
			System.out.println("dsafsdfas");
		}
	//	System.out.println(doc1.toString());
		Elements scraped1 = doc1.select("table table b");
		String x1 = "";
		if (doc1.toString().contains("Calories&nbsp")) {
		
		Object[] tables1 = scraped1.toArray();
		x1 = tables1[1].toString();
		x1 = x1.split(";")[1].split("<")[0];			
//			System.out.println(x1);
		if (x1.isEmpty())
			return 0;
		
		}
		else{
			x1 = "Unknown";
//				System.out.println(x1);
		}	
		if (x1 == "Unknown")
		{
			return 0;
		}
		return Integer.parseInt(x1);
	} 

/*
	@Override
	protected void onPostExecute(String result) {
		
		Breakfast.calorie = result;
	}*/
}
