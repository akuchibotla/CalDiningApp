package com.example.caldiningapp;

import java.io.IOException;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
public class Food {
	
	public Food(){
		
	}
	
	public ArrayList[] populate() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://services.housing.berkeley.edu/FoodPro/dining/static/todaysentrees.asp").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements scraped = doc.select("table table td");
		Object[] tables = scraped.toArray();

		ArrayList[] menu = new ArrayList[12]; // The menu array that will contain all menus for all places at all meals
		ArrayList<String> curr = new ArrayList<String>(); // The current menu for a place at a time (e.g. Crossroads Lunch)
		int MenuIn = 0;
		
		for (int i = 15; i <= 26; i++) {
			String blocks = (String) tables[i].toString().split("<hr />")[1]; // All items for a specific meal (e.g. all dinner items)
			if (!blocks.contains("Closed")) { // Only do the following if it's not closed
				String [] item = blocks.split("<br />"); // Get each item
				for (int j = 0; j < item.length - 3; j++) {
					curr.add(item[j].split(">")[2].split("</")[0]);
				}
			}
			
			menu[MenuIn] = curr; // Put the current list of items in the menu for a specific place and time (e.g. all Crossroads dinner items)
			curr.clear();
			MenuIn++;
		}
		return menu;
	}
}