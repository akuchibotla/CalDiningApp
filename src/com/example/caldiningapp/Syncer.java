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


public class Syncer extends AsyncTask<Void, Void, ArrayList<String>[]>{

	public static ArrayList<String> cloneList(ArrayList<String> list) {
		ArrayList<String> clone = new ArrayList<String>(list.size());
		for(String item: list) clone.add(new String(item));
		return clone;
	}

	@Override
	protected ArrayList<String>[] doInBackground(Void... params) {
		Document doc = null;
		ArrayList<String>[] menu = new ArrayList[12];
		ArrayList[] links = new ArrayList[12];

		try {
			doc = Jsoup.connect("http://services.housing.berkeley.edu/FoodPro/dining/static/todaysentrees.asp").get();
			Elements scraped = doc.select("table table td");
			Object[] tables = scraped.toArray();
			ArrayList<String> currcal = new ArrayList<String>(); // The current menu for a place at a time (e.g. Crossroads Lunch)
			ArrayList linksforone = new ArrayList();
			//if you had a ui element, you could display the title
			ArrayList<String> curr = new ArrayList<String>(); // The current menu for a place at a time (e.g. Crossroads Lunch)
			int MenuIn = 0;
			for (int i = 15; i <= 26; i++) {
				String blocks = (String) tables[i].toString().split("<hr />")[1]; // All items for a specific meal (e.g. all dinner items)
				if (!blocks.contains("Closed")) { // Only do the following if it's not closed
					String [] item = blocks.split("<br />"); // Get each item
					for (int j = 0; j < item.length - 3; j++) {
						String name = (item[j].split(">")[2].split("</")[0]).replace("&amp;", "&");
						String type = item[j].substring(9).split("#")[1].split(">")[0];
						type = type.substring(0,type.length()-1);
						if (type.equals("800040") || type.equals("800000")){
							type = "**";
						} else if (type.equals("008000")){
							type = "*";
						} else if (type.equals("000000") || type.equals("0F0F0F")){
							type = "";
						}
						
						
						
						
						
						
						String h = item[j].substring(9);
						h = h.split(">")[0];
						h = h.substring(1,h.length()-1);
						
						String [] harr = h.split("amp;");
						String h1 = harr[0];
						String h2 = harr[1];
						String h3 = harr[2];
						String h4 = harr[3];
						
						//System.out.println(h1+h2+h3+h4);
						String link = "http://services.housing.berkeley.edu/FoodPro/dining/static/" + h1 +h2+h3+h4;
						linksforone.add(link);
						
						/*
						Document doc1 = null;
						try {
							doc1 = Jsoup.connect("http://services.housing.berkeley.edu/FoodPro/dining/static/" + h1 +h2+h3+h4).get();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							
							System.out.println("dsafsdfas");
						}
					//	System.out.println(doc1.toString());
						Elements scraped1 = doc1.select("table table b");
						String x1 = "";
						if (!doc1.toString().contains("utritional")) {
						
						Object[] tables1 = scraped1.toArray();
						x1 = tables1[1].toString();
						x1 = x1.split(";")[1].split("<")[0];			
			//			System.out.println(x1);
						}
						else{
							x1 = "Unknown";
			//				System.out.println(x1);
						}					*/
						curr.add(name + type);
						//System.out.println("Name: " + name + " type: " + type + " calories: " + x1);
					}
				}
				ArrayList<String> insert = cloneList(curr);
				if (insert.size() == 0){
					insert.add("Closed");
				}
				menu[MenuIn] = insert; // Put the current list of items in the menu for a specific place and time (e.g. all Crossroads dinner items)
				curr.clear();
				ArrayList<String> duplicate = cloneList(linksforone);
				if (duplicate.size() == 0)
				{
					duplicate.add("Closed");
				}
				links[MenuIn] = duplicate;
				linksforone.clear();
				MenuIn++;

				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		MainActivity.links = links;
		return menu;
	} 


	@Override
	protected void onPostExecute(ArrayList<String>[] result) {
		
		MainActivity.menu = result;
	}
}
